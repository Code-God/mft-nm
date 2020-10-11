package com.meifute.core.tool.fsm;

import com.meifute.core.collection.MftStack;
import com.meifute.core.data.json.MftJsonException;
import com.meifute.core.lang.MftCollections;
import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;


/**
 * S 表示一个状态对象， 通常由该状态对象确定状态机的迁移。
 * T 表示一个状态机所对应的一个对象， 该对象通常用于表示状态迁移过程中产生的作用， 比如一个生产流程完成后的产品。
 *
 * @author wzeng
 * @date 2020/8/10
 * @Description
 */
public abstract class AbstractFsm<S,T> {

    private Enum<?> currentStatus=FSMStandardStatus.BEGIN;
    protected S stateObject = null;
    protected T fsmObject = null;
    Map<Enum,Set<FsmStatusMoveItem>> status2MoveItemsMap=new HashMap<>();
    AtomicBoolean initialed=new AtomicBoolean((false));
    MftStack<AbstractFsm> hierarchicalStack= new MftStack<>();
    Consumer<T> resultConsumer = null;

    final static Logger LOG= LoggerFactory.getLogger(AbstractFsm.class);

    /**
     *
     * @param stateObject 这个对象用来表示当前情况， 通常用来检测当前的状态，或当前事件， 是否发生状态迁移等。
     */
    public AbstractFsm(S stateObject){
        this.stateObject =stateObject;
    }

    /**
     *
     * @param stateObject 这个对象用来表示当前情况， 通常用来检测当前的状态，或当前事件， 是否发生状态迁移等。
     * @param resultConsumer 当FSM 到达终结状态{@link FSMStandardStatus}时, 触发该consumer。
     */
    public AbstractFsm(S stateObject,Consumer<T> resultConsumer){
        this(stateObject);
        this.resultConsumer = resultConsumer;
    }

    /**
     * 在此装配状态机的状态迁移，以及对应的时间检测和行为。
     */
    protected abstract void  initial() throws MftException;

    public void addStateMove(Enum<?> upStream, Enum<?> downStream, Callable<Boolean> moveAble,AbstractFsm fsm) throws MftException {
        FsmStatusMoveItem item =new FsmStatusMoveItem(upStream,downStream,moveAble,fsm);
        addStateMove(item);
    }

    public void addStateMove(Enum<?> upStream, Enum<?> downStream, Callable<Boolean> moveAble) throws MftException {
        FsmStatusMoveItem item =new FsmStatusMoveItem(upStream,downStream,moveAble);
        addStateMove(item);
    }

    public void addStateMove(FsmStatusMoveItem statusMoveItem){
        var set=status2MoveItemsMap.get(statusMoveItem.getUpStreamStatus());
        if(set==null){
            set = new HashSet<>();
            status2MoveItemsMap.put(statusMoveItem.getUpStreamStatus(),set);
        }
        set.add(statusMoveItem);
    }

    protected void setCurrentStatus(Enum<?> curStatus){
        this.currentStatus = curStatus;
    }

    protected Set<FsmStatusMoveItem> getMoveItemSetByStutus(Enum status){
        return this.status2MoveItemsMap.get(status);
    }

    public boolean isDone(){
        return currentStatus == FSMStandardStatus.END;
    }

    private Enum moveByFsm(AbstractFsm fsm) throws Exception {
        if(fsm.initialed.compareAndSet(false,true)){
            fsm.initial();
            fsm.validate();
        }

        Enum startStatus=fsm.getCurrentStatus();
        Set<FsmStatusMoveItem> moveItems = fsm.getMoveItemSetByStutus(startStatus);
        if(!MftCollections.isEmpty(moveItems)){
            for(FsmStatusMoveItem item:moveItems){
                boolean moved=item.move();
                if(moved){
                    AbstractFsm subFsm=item.getSubFsm();
                    if(subFsm!=null){
                        subFsm.reset(stateObject);
                        this.hierarchicalStack.push(subFsm);
                        LOG.debug("push sub fsm to stack when status:{} " ,item.getDownstreamStatus());
                    }
                    fsm.setCurrentStatus(item.getDownstreamStatus());
                    LOG.debug("moved from {} to {} " ,startStatus,fsm.currentStatus);
                    break;
                }
            }
            return fsm.getCurrentStatus();
        } else{
            throw new MftException("No path find from status:"+startStatus.name());
        }
    }

    protected void validate() throws MftJsonException {
        var paths= getAllStateTransferPath();
        for(var path:paths){
            if(path.getLast()==FSMStandardStatus.ERROR){
                throw new MftJsonException("A path in FSM is not completable:"+ MftStrings.toReadableString(path));
            }
        }
        return;
    }

    public Enum<?> move() throws Exception {
       if(this.hierarchicalStack.isEmpty()){
           return moveByFsm(this);
       } else{
           AbstractFsm fsm = this.hierarchicalStack.peek();
           if(fsm.getCurrentStatus()==FSMStandardStatus.END){
               this.hierarchicalStack.pop();
               LOG.debug("FSM:{} reached END status, poped from stack.",fsm.getClass().getSimpleName());
               if(fsm.resultConsumer!=null){
                   fsm.resultConsumer.accept(fsm.getFsmObject());
               }

               return this.move();
           } else {
               moveByFsm(fsm);
               return fsm.getCurrentStatus();
           }
       }
    }

    public void moveUntilDone() throws Exception {
        while(!this.isDone()){
            move();
        }
    }

    public void moveUntilBlocked() throws Exception {
        while(!this.isDone()){
            String preStatus=this.getPreciseStatusString();
            move();
            String endState = this.getPreciseStatusString();
            if(preStatus.equals(endState)){
                move();
                return;
            }
        }
    }

    public Enum<?> getCurrentStatus() {
        if(this.hierarchicalStack.isEmpty()) {
            return currentStatus;
        } else{
            return this.hierarchicalStack.peek().getCurrentStatus();
        }
    }

    public T getFsmObject() {
        return fsmObject;
    }

    public void reset(S stateObject){
        this.stateObject =stateObject;
        this.currentStatus = FSMStandardStatus.BEGIN;

    }

    public String getPreciseStatusString(){
        if(this.hierarchicalStack.isEmpty()){
            return this.currentStatus.name();
        } else{
            return this.hierarchicalStack.size()+":"+this.hierarchicalStack.peek().getCurrentStatus();
        }
    }

    /**
     * 返回fsm 中定义的所有路径， 从 FSMStandardStatus.BEGIN 开始 到 FSMStandardStatus.END 结束，
     * 如果没有到达结束节点，将以 FSStandardStatus.ERROR 结束
     * @return
     */
    public List<LinkedList<Enum>> getAllStateTransferPath(){
        List<LinkedList<Enum>> list =new LinkedList<>();
        boolean allEndWithEND=false;
        LinkedList<Enum> firstList= new LinkedList<>();
        firstList.add(FSMStandardStatus.BEGIN);
        list.add(firstList);
        Set<Enum> testedStatus=new TreeSet<>();
        while(!allEndWithEND){
            List<LinkedList<Enum>> newPaths =new LinkedList<>();
            allEndWithEND = true;
           for(LinkedList<Enum> path:list){
               Enum lastOne=path.getLast();
              if(!lastOne.equals(FSMStandardStatus.END)
              && !lastOne.equals(FSMStandardStatus.ERROR)){
                  allEndWithEND = false;
                  var nextItems=status2MoveItemsMap.get(path.getLast());
                  if(MftCollections.isEmpty(nextItems)){
                        path.add(FSMStandardStatus.ERROR);
                  }else{
                      int count=0;
                      Enum firstDown = null;
                      for(var item: nextItems){
                          if(path.contains(item.getDownstreamStatus()) && nextItems.size()>1){

                              continue;
                          }
                          if(count==0) {
                              firstDown = item.getDownstreamStatus();
                          }else{
                             var newPath= (LinkedList<Enum>)path.clone();
                             newPath.add(item.getDownstreamStatus());
                             newPaths.add(newPath);
                          }

                          count++;
                      }
                      if(firstDown!=null) {
                          path.add(firstDown);
                      }
                  }
              }
           }
           list.addAll(newPaths);
           newPaths.clear();
        }
        return list;
    }

}
