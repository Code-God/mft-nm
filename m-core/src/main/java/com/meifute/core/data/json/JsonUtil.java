package com.meifute.core.data.json;


import com.meifute.core.constant.MftSymbol;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonUtil {

	public final static char[] WHITE_SPACE_IN_JSON =new char[]{' ','\n','\r','\t'};
	public final  static char OBJECT_START_CHAR= MftSymbol.OPENING_CURLY_BRACKET;
	public final static char OBJECT_END_CHAR=MftSymbol.CLOSING_CURLY_BRACKET;
	public final static char ARRAY_START_CHAR=MftSymbol.OPENING_SQUARE_BRACKET;
	public final static char ARRAY_END_CHAR=MftSymbol.CLOSING_SQUARE_BRACKET;
	public final static String NULL="null";
	public final static String TRUE="true";
	public final static String FALSE="false";

	final static Pattern IS_NUMBER_PATTERN=Pattern.compile("(-?)(0|([1-9]\\d*))(\\.\\d+)?([Ee][-+]?\\d+)?");
    final static Map<Character,Character> CHAR_TO_CONTROLLER_CHAR=Map.of('"','\"'
			,'\\','\\','b','\b','f','\f','n'
			,'\n','r','\r','t','\t');
    final  static Map<Character,Character> CONTROLLER_CHAR_TO_CHAR= new TreeMap<>();
    static{
    	CHAR_TO_CONTROLLER_CHAR.forEach((k,v)->CONTROLLER_CHAR_TO_CHAR.put(v,k));
	}

	public final static boolean isWhiteSpace(char c) {
		for(char w: WHITE_SPACE_IN_JSON ){
			if(w==c){
				return true;
			}
		}
		return false;
	}

	public static boolean isObjectEnd(char charAt) {
		return charAt==OBJECT_END_CHAR;
	}
	
	public static boolean isObjectBegin(char charAt) {
		return charAt==OBJECT_START_CHAR;
	}

	public static boolean isArrayBegin(char charAt){
		return charAt == ARRAY_START_CHAR;
	}

	public static boolean isArrayEnd(char charAt){
		return charAt==ARRAY_END_CHAR;
	}




	public final static boolean isJsonNumber(String s){
		Matcher m = IS_NUMBER_PATTERN.matcher(s);
		if(m.find()){
			return m.group(0).equals(s);
		} else{
			return false;
		}
}
}
