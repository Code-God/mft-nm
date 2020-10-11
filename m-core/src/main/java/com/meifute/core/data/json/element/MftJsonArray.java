package com.meifute.core.data.json.element;

import com.meifute.core.constant.MftSymbol;
import com.meifute.core.data.json.*;

import java.util.*;
import java.util.ArrayList;

/**
 * @author  wzeng
 */
public class MftJsonArray extends AbstractJsonElement {

	List<AbstractJsonElement> elements = new ArrayList<>();

	@Override
	public String toJsonString() {
		StringBuilder sb = new StringBuilder();
		sb.append(MftSymbol.OPENING_SQUARE_BRACKET);
		if (elements.size() > 0) {
			for (ToJsonStringAble sde : elements) {
				sb.append(sde.toJsonString());
				sb.append(MftSymbol.COMMA);
			}
			sb.setLength(sb.length() - 1);
		}
		sb.append(MftSymbol.CLOSING_SQUARE_BRACKET);
		return sb.toString();
	}

	public int size(){
		return elements.size();
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	public List<AbstractJsonElement> getArray(){
		return elements;
	}

	public <T extends AbstractJsonElement> T get(int index){
		return (T) elements.get(index);
	}
	public void add(AbstractJsonElement ele) {
		elements.add(ele);
	}
}
