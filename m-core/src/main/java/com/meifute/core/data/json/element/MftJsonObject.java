package com.meifute.core.data.json.element;

import com.meifute.core.constant.MftSymbol;

import java.util.Map;
import java.util.TreeMap;


/**
 * @author wzeng
 * @date 2020/08/17
 */
public class MftJsonObject extends AbstractJsonElement {
    TreeMap<MftJsonString, AbstractJsonElement> map =new TreeMap<>();

	@Override
	public String toJsonString() {
		StringBuilder sb = new StringBuilder();
		sb.append(MftSymbol.OPENING_CURLY_BRACKET);
		if (map.size() > 0) {
			for (Map.Entry<MftJsonString, AbstractJsonElement> entry : map.entrySet()) {
				sb.append(entry.getKey().toJsonString())
						.append(MftSymbol.COLON)

				.append(entry.getValue()==null?null:entry.getValue().toJsonString())
						.append(MftSymbol.COMMA);
			}
			sb.setLength(sb.length() - 1);
			sb.append(MftSymbol.CLOSING_CURLY_BRACKET);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	public  AbstractJsonElement get(String key) {
		return map.get(new MftJsonString(key));
	}
	
	public boolean containsKey(MftJsonString key) {
		return map.containsKey(key);
	}
	public boolean containsKey(String key) {
		return containsKey(new MftJsonString(key));
	}

	public int size(){
		return this.map.size();
	}
	
	public void put(MftJsonString first, AbstractJsonElement second) {
		this.map.put(first,second);
	}

	public void put(String first,AbstractJsonElement second){
		this.put(new MftJsonString(first),second);
	}

	public TreeMap<MftJsonString, AbstractJsonElement> getMap() {
		return map;
	}
}
