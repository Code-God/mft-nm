package com.meifute.core.data.json.fsm;

import com.meifute.core.data.json.*;
import com.meifute.core.data.json.element.*;
import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import com.meifute.core.tool.fsm.FSMStandardStatus;

import java.util.function.Consumer;

/**
 * @author wzeng
 * @date 2020/8/12
 * @Description
 */
public class JsonValueFsm extends AbstractFsmStringParser<AbstractJsonElement> {

    public JsonValueFsm(ParserStateObject stateObject, Consumer<AbstractJsonElement> resultConsumer) {
        super(stateObject, resultConsumer);
    }

    @Override
    public void initial() throws MftException {

        this.addStateMove(FSMStandardStatus.BEGIN,ReadValueSate.VALUE_WHITESPACE_FIRST,
                ()-> compareFirstChar(true,false,true,null));

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_ARRAY
                ,()-> JsonUtil.isArrayBegin(stateObject.getStringForParse().charAt(stateObject.getIndex()))
                ,new JsonArrayFsm(stateObject, p->this.fsmObject= p));

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_OBJECT
                ,()-> JsonUtil.isObjectBegin(stateObject.getStringForParse().charAt(stateObject.getIndex()))
                ,new JsonArrayFsm(stateObject, p->this.fsmObject= p));

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_STRING
                ,()-> stateObject.getStringForParse().charAt(stateObject.getIndex()) == '"');

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_BOOLEAN
                ,()-> stateObject.getStringForParse().startsWith("true", stateObject.getIndex()) ||
                stateObject.getStringForParse().startsWith("false", stateObject.getIndex()));

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_NUMBER,()->{
            char c1= stateObject.getStringForParse().charAt(stateObject.getIndex());
            char c2= stateObject.getStringForParse().charAt(stateObject.getIndex()+1);
            return c1 >= '1' && c1 <= '9' || c1 == '-' && c2 >= '0' && c2 <= '9';
        });

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_FIRST,ReadValueSate.VALUE_NULL,
                ()-> stateObject.getStringForParse().startsWith("null", stateObject.getIndex()));

        this.addStateMove(ReadValueSate.VALUE_STRING,ReadValueSate.VALUE_WHITESPACE_SECOND,()->{
            var endStr=MftStrings.findNearest(stateObject.getStringForParse(),stateObject.getIndex()+1,'"');
            if(endStr.isPresent()){
                this.fsmObject =new MftJsonString(stateObject.getStringForParse().substring(stateObject.getIndex()+1, endStr.get().getSecond()));
                stateObject.setIndex(endStr.get().getSecond()+1);
            } else{
                throw new MftJsonException("Not find end \" in json string"+MftStrings.abbreviate(
                        stateObject.getStringForParse(),"...",stateObject.getIndex(),30));
            }
            return true;
        });

        this.addStateMove(ReadValueSate.VALUE_NUMBER,ReadValueSate.VALUE_WHITESPACE_SECOND,()->{
            var numberEnd=MftStrings.findNearest(stateObject.getStringForParse(),stateObject.getIndex(),',',']','}');
            if(numberEnd.isPresent()){
                String numberStr=stateObject.getStringForParse().substring(stateObject.getIndex(),numberEnd.get().getSecond());
                MftJsonNumber number = new MftJsonNumber();
                number.parse(numberStr);
                this.fsmObject = number;
                stateObject.setIndex(numberEnd.get().getSecond());
                return true;
            } else{
                throw new MftJsonException("Not find end \" in json number "+MftStrings.abbreviate(
                        stateObject.getStringForParse(),"...",stateObject.getIndex(),30));
            }
        });

        this.addStateMove(ReadValueSate.VALUE_OBJECT,ReadValueSate.VALUE_WHITESPACE_SECOND,()-> true);

        this.addStateMove(ReadValueSate.VALUE_ARRAY,ReadValueSate.VALUE_WHITESPACE_SECOND,()-> true);

        this.addStateMove(ReadValueSate.VALUE_BOOLEAN,ReadValueSate.VALUE_WHITESPACE_SECOND,()->{
            if(stateObject.getStringForParse().startsWith(JsonUtil.TRUE,stateObject.getIndex())){
                this.fsmObject = new MftJsonBoolean(true);
                stateObject.setIndex(stateObject.getIndex()+4);
            } else{
                this.fsmObject = new MftJsonBoolean(false);
                stateObject.setIndex(stateObject.getIndex()+5);
            }
            return true;
        });

        this.addStateMove(ReadValueSate.VALUE_NULL,ReadValueSate.VALUE_WHITESPACE_SECOND,()->{
            this.fsmObject = new MftJsonNull();
            this.stateObject.setIndex(stateObject.getIndex()+4);
            return true;
        });

        this.addStateMove(ReadValueSate.VALUE_WHITESPACE_SECOND,FSMStandardStatus.END,()-> true);
    }
}

enum ReadValueSate{
    /**
     *
     */
    VALUE_WHITESPACE_FIRST,VALUE_STRING,VALUE_NUMBER,VALUE_OBJECT,VALUE_ARRAY,VALUE_BOOLEAN,VALUE_NULL,VALUE_WHITESPACE_SECOND
}