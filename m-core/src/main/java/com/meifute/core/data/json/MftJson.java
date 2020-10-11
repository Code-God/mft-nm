package com.meifute.core.data.json;

import com.meifute.core.data.json.element.AbstractJsonElement;
import com.meifute.core.data.json.fsm.JsonArrayFsm;
import com.meifute.core.data.json.fsm.JsonObjectFsm;
import com.meifute.core.lang.MftStrings;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;

import java.util.Objects;

/**
 * @author  wzeng
 * @date 2020/08/17
 */
public class MftJson {

	@SuppressWarnings("unchecked")
	public final static <T extends AbstractJsonElement> T parse(String jsonString) throws Exception {
		Objects.requireNonNull(jsonString);
		var var=MftStrings.findNextPrintableChar(jsonString,0);
		AbstractFsmStringParser<? extends  AbstractJsonElement> stringParser;
		if(var.isPresent() && JsonUtil.isObjectBegin(var.get().getFirst())){
			stringParser = new JsonObjectFsm(new AbstractFsmStringParser.ParserStateObject(
					jsonString,0),null);
		} else if(var.isPresent() && JsonUtil.isArrayBegin(var.get().getFirst())){
			stringParser = new JsonArrayFsm(new AbstractFsmStringParser.ParserStateObject(
					jsonString,0),null);
		} else{
			throw new MftJsonException("json string should be an array or an object:"
					+MftStrings.abbreviate(jsonString,"...",20));
		}
		stringParser.parse();
		return (T) stringParser.getFsmObject();

	}
	
}
