package com.meifute.core.data.json.fsm;

import com.meifute.core.data.json.JsonUtil;
import com.meifute.core.data.json.element.MftJsonArray;
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
public class JsonArrayFsm extends AbstractFsmStringParser<MftJsonArray> {


    public JsonArrayFsm(ParserStateObject stateObject, Consumer<MftJsonArray> consumer) {
        super(stateObject,consumer);
    }


    @Override
    public void initial() throws MftException {

       this.fsmObject = new  MftJsonArray();

       this.addStateMove(FSMStandardStatus.BEGIN,ArrayParse.ARRAY_START,
               ()->compareFirstChar(false,false,true,JsonUtil.ARRAY_START_CHAR));

       this.addStateMove(ArrayParse.ARRAY_START,ArrayParse.ARRAY_WHITESPACE,
               ()-> {
           var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
           if(var.isPresent()){
               var nextChar=MftStrings.findNextPrintableChar(stateObject.getStringForParse(), var.get().getSecond()+1);
               if(nextChar.isPresent() && JsonUtil.isArrayEnd(nextChar.get().getFirst())){
                   stateObject.setIndex(nextChar.get().getSecond());
                   return true;
               }
           }
           return false;
               });

       this.addStateMove(ArrayParse.ARRAY_WHITESPACE,ArrayParse.ARRAY_END,
               ()-> compareFirstChar(false,false,true,JsonUtil.ARRAY_END_CHAR));

       this.addStateMove(ArrayParse.ARRAY_END,FSMStandardStatus.END,()->{
           stateObject.setIndex(stateObject.getIndex()+1);
           return true;
       });

       this.addStateMove(ArrayParse.ARRAY_START, ArrayParse.ARRAY_VALUE,()->{
           var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex()+1);
           if(v.isPresent() && !JsonUtil.isArrayEnd(v.get().getFirst())){
               stateObject.setIndex(stateObject.getIndex()+1);
               return true;
           } else{
               return false;
           }
       },new JsonValueFsm(stateObject,p->fsmObject.add(p)));


       this.addStateMove(ArrayParse.ARRAY_VALUE,ArrayParse.ARRAY_COMMA,()->{
           var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
           if(v.isPresent() && v.get().getFirst()==','){
               stateObject.setIndex(v.get().getSecond());
               return true;
           } else{
               return false;
           }
       });

        this.addStateMove(ArrayParse.ARRAY_COMMA,ArrayParse.ARRAY_VALUE,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && v.get().getFirst()==','){
                stateObject.setIndex(v.get().getSecond()+1);
                return true;
            } else{
                return false;
            }
        },new JsonValueFsm(stateObject,p->fsmObject.add(p)));

        this.addStateMove(ArrayParse.ARRAY_VALUE,ArrayParse.ARRAY_END,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent() && JsonUtil.isArrayEnd(v.get().getFirst())){
                return true;
            } else{
                return false;
            }
        });
    }


}

enum ArrayParse{
    ARRAY_START,ARRAY_WHITESPACE,ARRAY_VALUE,ARRAY_COMMA,ARRAY_END
}