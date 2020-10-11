package com.meifute.core.tool.fsm;
import com.meifute.core.collection.Pair;
import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author wzeng
 * @date 2020/8/11
 * @Description
 */
public abstract class AbstractFsmStringParser<T> extends AbstractFsm<AbstractFsmStringParser.ParserStateObject,T>{


    public AbstractFsmStringParser(ParserStateObject parserStateObject) {
        super(parserStateObject);
    }

    /**
     *
     * @param stateObject 这个对象用来表示当前情况， 通常用来检测当前的状态，或当前事件， 是否发生状态迁移等。
     * @param resultConsumer 当FSM 到达终结状态{@link FSMStandardStatus}时, 触发该consumer。
     */
    public AbstractFsmStringParser(ParserStateObject stateObject, Consumer<T> resultConsumer){
        super(stateObject,resultConsumer);
    }

    public void parse() throws Exception {
        this.moveUntilBlocked();
        if(!this.isDone()){
            Enum status=this.getCurrentStatus();
            throw new MftException("Parser stoped at status:"+status.getClass().getSimpleName()+":"+status.name()
                    +" index:"+this.stateObject.getIndex()
                    +":["+stateObject.getStringForParse().charAt(stateObject.getIndex())+"] string: "
                    + MftStrings.abbreviate(this.stateObject.getStringForParse(),"..."
                    ,this.stateObject.getIndex()-1,20));
        }
    }

    public Optional<Pair<Character, Integer>> nextNotBlankChar(){
        var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
        return v;
    }

    /**
     *
     * @param expects 希望匹配的字符,可以是一个或多个， 为null 的时候表示总是匹配。
     * @param move 是否移动索引
     * @param  match 是否匹配， false 表示第一个字符应该与expects 的字符不同。
     * @param moveToNext 是否移动索引到下一个字符
     * @return
     */
    protected boolean compareFirstChar(boolean move,boolean moveToNext,boolean match,char... expects){
        var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
        if(v.isPresent()) {
            boolean matched = compareChar(v.get().getFirst(), match, expects);
            if(match){
                if(moveToNext && move){
                    stateObject.setIndex(v.get().getSecond()+1);
                } else if(move){
                    stateObject.setIndex(v.get().getSecond());
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean compareChar(char achar,boolean match,char ...expected){
        if(expected==null){
            return true;
        }

        if(match){
            for(char c:expected){
                if(c==achar){
                    return true;
                }
            }
            return false;
        } else{
            for(char c: expected){
                if(c==achar){
                    return false;
                }
            }
            return true;
        }
    }

    public static class ParserStateObject {
        String stringForParse;
        int index;

        public ParserStateObject(String string, int index){
            this.stringForParse = string;
            this.index =index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getStringForParse() {
            return stringForParse;
        }

        public int getIndex() {
            return index;
        }

    }
}
