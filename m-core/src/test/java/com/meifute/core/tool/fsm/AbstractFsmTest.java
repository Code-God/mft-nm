package com.meifute.core.tool.fsm;

import com.meifute.core.collection.Pair;
import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/11
 * @Description
 */
class AbstractFsmTest {

    @Test
    void move() throws Exception {
        EqualsParser ss = new EqualsParser(new AbstractFsmStringParser.ParserStateObject("abc=1234",0),null);


        ss.move();
        assertEquals(EqualParseStatus.FIND_LEFT,ss.getCurrentStatus());
        ss.moveUntilBlocked();
        assertEquals("abc",ss.getFsmObject().getFirst());
        assertEquals("1234",ss.getFsmObject().getSecond());
        ss.reset(new AbstractFsmStringParser.ParserStateObject("abcd=123",0));
        ss.parse();
        assertEquals("abcd",ss.getFsmObject().getFirst());
        assertEquals("123",ss.getFsmObject().getSecond());
        var path = ss.getAllStateTransferPath();
        System.out.println(MftStrings.toReadableString(path));
    }

    @Test
    void hierTest() throws Exception {
        AbstractFsmStringParser.ParserStateObject pso = new AbstractFsmStringParser.ParserStateObject("abc=1234,abcd=123",0);
        MultiEqualsParser meps =new MultiEqualsParser(pso);
        meps.parse();

        assertEquals(2,meps.fsmObject.size());
        assertEquals("abc",meps.fsmObject.get(0).getFirst());
        assertEquals("1234",meps.fsmObject.get(0).getSecond());
        assertEquals("abcd",meps.fsmObject.get(1).getFirst());
        assertEquals("123",meps.fsmObject.get(1).getSecond());
        var path = meps.getAllStateTransferPath();
        System.out.println(MftStrings.toReadableString(path));
    }
}

class MultiEqualsParser extends AbstractFsmStringParser<List<Pair<String,String>>>{

    public MultiEqualsParser(ParserStateObject parserStateObject) {
        super(parserStateObject);
    }

    @Override
    public void initial() throws MftException {
        this.fsmObject = new ArrayList<>();
        this.addStateMove(FSMStandardStatus.BEGIN,MultiEqualParseStatus.FIND_EXPRESS,()->{
            var v= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(v.isPresent()){
                return true;
            } else{
                return false;
            }
        },new EqualsParser(this.stateObject,e->fsmObject.add(e)));

        this.addStateMove(MultiEqualParseStatus.FIND_EXPRESS,MultiEqualParseStatus.FIND_COMMA,()->{

            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(var.isPresent() && var.get().getFirst()==','){
                stateObject.setIndex(var.get().getSecond());
                return true;
            }
            return false;
        });

        this.addStateMove(MultiEqualParseStatus.FIND_EXPRESS,FSMStandardStatus.END,()->{
            var var= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(var.isPresent()){
                return false;
            } else{
                return true;
            }
        });

        this.addStateMove(MultiEqualParseStatus.FIND_COMMA,MultiEqualParseStatus.FIND_EXPRESS,()->{
            var var= MftStrings.findNextPrintableChar(stateObject.getStringForParse(),stateObject.getIndex());
            if(var.isPresent() && var.get().getFirst()==','){
                stateObject.setIndex(var.get().getSecond()+1);
                return true;
            } else{
               return false;
            }
        },new EqualsParser(this.stateObject,e->fsmObject.add(e)));
    }
}

class EqualsParser extends AbstractFsmStringParser<Pair<String,String>> {

    public EqualsParser(ParserStateObject object, Consumer<Pair<String,String>> consumer) {
        super(object,consumer);
    }

    @Override
    public void initial() throws MftException {
        this.fsmObject=new Pair();
        this.addStateMove(FSMStandardStatus.BEGIN,EqualParseStatus.FIND_LEFT,()->{
            var v= MftStrings.findNextPrintableChar(this.stateObject.getStringForParse(),this.stateObject.getIndex());
            if(v.isPresent()){
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(EqualParseStatus.FIND_LEFT,EqualParseStatus.FIND_EQUAL_SIGN,()->{
            var v =MftStrings.findNearest(stateObject.getStringForParse(),stateObject.getIndex(),'=');
            if(v.isPresent()){
                this.getFsmObject().setFirst(stateObject.getStringForParse().substring(stateObject.getIndex(),v.get().getSecond()));
                this.stateObject.setIndex(v.get().getSecond());
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(EqualParseStatus.FIND_EQUAL_SIGN,EqualParseStatus.FIND_RIGHT,()->{
            if(stateObject.getIndex()<stateObject.getStringForParse().length()){
                stateObject.setIndex(stateObject.getIndex()+1);
                return true;
            } else{
                return false;
            }
        });

        this.addStateMove(EqualParseStatus.FIND_RIGHT,FSMStandardStatus.END,()->{
            var v = MftStrings.findNearest(stateObject.getStringForParse(),stateObject.getIndex(),',');
            if(v.isPresent()){
                this.getFsmObject().setSecond(stateObject.getStringForParse().substring(stateObject.getIndex(),v.get().getSecond()));
                stateObject.setIndex(v.get().getSecond());
            } else {
                this.getFsmObject().setSecond(stateObject.getStringForParse().substring(stateObject.getIndex()));
                stateObject.setIndex(stateObject.getStringForParse().length());
            }
            return true;
        });
    }
}
enum MultiEqualParseStatus{
   FIND_EXPRESS,FIND_COMMA
}
enum EqualParseStatus{
    FIND_LEFT,FIND_EQUAL_SIGN,FIND_RIGHT;
}