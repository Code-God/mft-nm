package com.meifute.core.data.json.fsm;

import com.meifute.core.data.json.*;
import com.meifute.core.data.json.element.MftJsonObject;
import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import com.meifute.core.tool.fsm.FSMStandardStatus;

import java.util.function.Consumer;

/**
 * @author wzeng
 * @date 2020/8/16
 * @Description
 */
public class JsonObjectFsm
        extends AbstractFsmStringParser<MftJsonObject> {

    public JsonObjectFsm(ParserStateObject stateObject, Consumer<MftJsonObject> resultConsumer) {
        super(stateObject, resultConsumer);
    }

    @Override
    protected void initial() throws MftException {
        this.fsmObject = new MftJsonObject();

        this.addStateMove(FSMStandardStatus.BEGIN,ParseObjectStatus.OBJECT_START,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && JsonUtil.isObjectBegin(v.get().getFirst())){
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(ParseObjectStatus.OBJECT_START,ParseObjectStatus.WHITESPACE,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex()+1);
            if(v.isPresent() && JsonUtil.isObjectEnd(v.get().getFirst())){
                stateObject.setIndex(v.get().getSecond());
                return true;
            }else{
                return false;
            }
        });

        this.addStateMove(ParseObjectStatus.WHITESPACE,ParseObjectStatus.OBJECT_END,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && JsonUtil.isObjectEnd(v.get().getFirst())){
                stateObject.setIndex(v.get().getSecond());
                return true;
            }else{
                return false;
            }
        });

        this.addStateMove(ParseObjectStatus.OBJECT_START,ParseObjectStatus.NAME_VALUE_PAIR,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex()+1);
            if(v.isPresent() && !JsonUtil.isObjectEnd(v.get().getFirst())){
                stateObject.setIndex(v.get().getSecond());
                return true;
            } else{
                return false;
            }
        },new JSonNameValuePairFsm(stateObject,p->this.fsmObject.put(p.getFirst(),p.getSecond())));


        this.addStateMove(ParseObjectStatus.NAME_VALUE_PAIR,ParseObjectStatus.COMMA,()->{
            var v =MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && v.get().getFirst()==','){
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(ParseObjectStatus.NAME_VALUE_PAIR,ParseObjectStatus.OBJECT_END,()->{
            var v =MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && JsonUtil.isObjectEnd(v.get().getFirst())){
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(ParseObjectStatus.COMMA,ParseObjectStatus.NAME_VALUE_PAIR,()->{
            var v =MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && v.get().getFirst()==','){
               stateObject.setIndex(v.get().getSecond()+1);
               return true;
            } else{
                return false;
            }
        },new JSonNameValuePairFsm(stateObject,p->this.fsmObject.put(p.getFirst(),p.getSecond())));

        this.addStateMove(ParseObjectStatus.OBJECT_END,FSMStandardStatus.END,()->{
            var v =MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && JsonUtil.isObjectEnd(v.get().getFirst())){
                stateObject.setIndex(v.get().getSecond()+1);
                return true;
            } else{
                return false;
            }
        });
    }
}

enum  ParseObjectStatus{
    OBJECT_START,WHITESPACE,NAME_VALUE_PAIR,COMMA,OBJECT_END
}
