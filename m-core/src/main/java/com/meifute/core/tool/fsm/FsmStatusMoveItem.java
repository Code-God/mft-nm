package com.meifute.core.tool.fsm;

import java.util.concurrent.Callable;

/**
 * @author wzeng
 * @date 2020/8/11
 * @Description
 */
public class FsmStatusMoveItem {
    Enum<?> upStreamStatus;
    Enum<?> downstreamStatus;
    /**
     * 事件处理， 如果是状态迁移事件，则返回true， 如果不是， 返回false， 返回false的情况下， 该方法的执行没有副作用。
     */
    Callable<Boolean> caller;
    AbstractFsm subFsm;


    public FsmStatusMoveItem(Enum<?> upStreamStatus, Enum<?> downstreamStatus, Callable<Boolean> moveAble) {
        this.upStreamStatus = upStreamStatus;
        this.downstreamStatus = downstreamStatus;
        this.caller = moveAble;

    }

    public FsmStatusMoveItem(Enum<?> upStreamStatus, Enum<?> downstreamStatus, Callable<Boolean> moveAble,AbstractFsm subFsm) {
        this.upStreamStatus = upStreamStatus;
        this.downstreamStatus = downstreamStatus;
        this.caller = moveAble;
        this.subFsm = subFsm;
    }

    public boolean move() throws Exception {
       return  caller.call();
    }

    public Enum<?> getUpStreamStatus() {
        return upStreamStatus;
    }

    public Enum<?> getDownstreamStatus() {
        return downstreamStatus;
    }

    public AbstractFsm getSubFsm() {
        return subFsm;
    }
}
