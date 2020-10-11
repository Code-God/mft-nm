package com.meifute.core.data.json.fsm;

import com.meifute.core.collection.Pair;
import com.meifute.core.data.json.element.AbstractJsonElement;
import com.meifute.core.data.json.element.MftJsonString;
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
public class JSonNameValuePairFsm
        extends AbstractFsmStringParser<Pair<MftJsonString, AbstractJsonElement>> {
    public JSonNameValuePairFsm(ParserStateObject stateObject, Consumer<Pair<MftJsonString, AbstractJsonElement>> resultConsumer) {
        super(stateObject, resultConsumer);
    }

    @Override
    protected void initial() throws MftException {
        this.fsmObject = new Pair<>();

        this.addStateMove(FSMStandardStatus.BEGIN, JsonParseNameValueStatus.WHITESPACE_BEFORE_STRING, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isPresent() && var.get().getFirst() == '"') {
                return true;
            } else {
                return false;
            }
        });

        this.addStateMove(JsonParseNameValueStatus.WHITESPACE_BEFORE_STRING, JsonParseNameValueStatus.NAME, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isPresent() && var.get().getFirst() == '"') {
                return true;
            } else {
                return false;
            }
        }, new JsonValueFsm(stateObject, p -> this.fsmObject.setFirst((MftJsonString) p)));

        this.addStateMove(JsonParseNameValueStatus.NAME, JsonParseNameValueStatus.WHITESPACE_AFTER_NAME, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isPresent() && var.get().getFirst() == ':') {
                return true;
            } else {
                return false;
            }
        });

        this.addStateMove(JsonParseNameValueStatus.WHITESPACE_AFTER_NAME, JsonParseNameValueStatus.COLON, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isPresent() && var.get().getFirst() == ':') {
                return true;
            } else {
                return false;
            }
        });

        this.addStateMove(JsonParseNameValueStatus.COLON, JsonParseNameValueStatus.VALUE, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isPresent() && var.get().getFirst() == ':') {
                stateObject.setIndex(var.get().getSecond() + 1);
                return true;
            } else {
                return false;
            }
        }, new JsonValueFsm(stateObject, p -> this.fsmObject.setSecond(p)));

        this.addStateMove(JsonParseNameValueStatus.VALUE, FSMStandardStatus.END, () -> {
            var var = MftStrings.findNextPrintableChar(stateObject.getStringForParse(), stateObject.getIndex());
            if (var.isEmpty()){
                return true;
            } else if (var.get().getFirst() == '}' || var.get().getFirst() == ',') {
                stateObject.setIndex(var.get().getSecond());
                return true;
            } else {
                return false;
            }
        });

    }
}

enum JsonParseNameValueStatus {
    WHITESPACE_BEFORE_STRING, NAME, WHITESPACE_AFTER_NAME, COLON, VALUE
}
