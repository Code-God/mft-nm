package com.meifute.core.lang;

import com.meifute.core.collection.Pair;
import com.meifute.core.lang.Date.MftDates;
import com.meifute.core.lang.Date.MftDatetimeFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public final  class MftStrings {

    public static final String CR="\r";
    public static final String LF="\n";
    public static final String EMPTY="";
    public static final String SPACE=" ";

    /**
     *
     * @param string
     * @return  如果等于null 或空字符串("")，返回true
     */
    public final static boolean isEmpty(String string){
         if(string==null || string.isEmpty()){
             return true;
         } else{
             return false;

         }
    }

    public final static boolean isNotEmpty(String string){
       return !isEmpty(string);
    }

    /**
     *
     * @param string
     * @return  0 if the string is null.
     */
    public final static int length(String string){
        return string == null ? 0 : string.length();
    }

    /**
     * null ,空字符串， 空白字符(制表，换行，回车等），都返回true
     * @param string
     * @return
     */
    public final static boolean isBlank(String string){
        int strLen = length(string);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public final static boolean isNotBlank(String string){
        return !isBlank(string);
    }

    /**
     * 缩短长的字符串， 如果字符串太长， 超过的部分用 abbrMarker 代替， abbrMarker 默认为 "..."
     * @param string
     * @param abbrMarker 默认为"..."
     * @param maxLength 长度至少要大于 abbrMarker 长度
     * @return
     */
    public final static String abbreviate(String string,String abbrMarker,int maxLength){
        return abbreviate(string,abbrMarker,0,maxLength);
    }

    /**
     *缩短长的字符串， 如果字符串太长， 超过的部分用 abbrMarker 代替， abbrMarker 默认为 "..."
     * @param str
     * @param abbrevMarker
     * @param offset  从offset之前的字符将全部忽略,并使用 abbrevMarker代替
     * @param maxWidth
     * @return
     */
    public final static String abbreviate(String str, String abbrevMarker, int offset, int maxWidth) {

        abbrevMarker = abbrevMarker==null?"...":abbrevMarker;

        if (isEmpty(str) && isEmpty(abbrevMarker)) {
            return str;
        } else if(maxWidth <=0){
            return "";
        } else if(maxWidth<=abbrevMarker.length()){
            return abbrevMarker.substring(0,maxWidth);
        } else if (isNotEmpty(str) && "".equals(abbrevMarker) && maxWidth > 0) {
            return str.substring(0, maxWidth);
        } else if (!isEmpty(str) && !isEmpty(abbrevMarker)) {
            int abbrevMarkerLength = abbrevMarker.length();
            int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;
            if (str.length() <= maxWidth) {
                return str;
            } else {
                if (offset > str.length()) {
                    offset = str.length();
                }

                if (str.length() - offset < maxWidth - abbrevMarkerLength) {
                    offset = str.length() - (maxWidth - abbrevMarkerLength);
                }

                if (offset <= abbrevMarkerLength + 1) {
                    return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
                } else if (maxWidth < minAbbrevWidthOffset) {
                    return abbrevMarker+abbrevMarker.substring(0,maxWidth-abbrevMarker.length());
                } else {
                    return offset + maxWidth - abbrevMarkerLength < str.length()
                            ? abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength)
                            : abbrevMarker + str.substring(str.length() - (maxWidth - abbrevMarkerLength));
                }
            }
        } else {
            return str;
        }

    }

    public static String toReadableString(Object obj){
        if(obj==null){
            return null;
        }
        String delimit=", ";
        StringBuilder sb=new StringBuilder();
        if(obj instanceof Collection){
            sb.append("[");
            for(Object o: (Collection)obj){
                sb.append(toReadableString(o)).append(delimit);
            }
            sb.append("]\n");
        } else if (obj instanceof Date){
            LocalDateTime localDateTime = MftDates.toLocalDatetime((Date)obj);
            return toReadableString(localDateTime);
        } else if(obj instanceof  LocalDateTime){
            return MftDatetimeFormatter.formatCommonDatetime((LocalDateTime)obj);
        } else if (obj instanceof Map){
            sb.append("{");
            ((Map)obj).forEach((k,v)->{
                sb.append(toReadableString(k)).append(" : ").append(toReadableString(v)).append(delimit);
            });
            sb.append("}\n");
        } else if(obj.getClass().isArray()){
            int length= Array.getLength(obj);
            sb.append("[");
            for(int i=0;i<length;i++){
                Object o= Array.get(obj,i);
                sb.append(toReadableString(o)).append(delimit);
            }
            sb.append("]\n");

        } else {
            return String.valueOf(obj);
        }
       return sb.toString();
    }

    /**
     * 移除前缀， 如果前缀不匹配，则返回原字符串。
     * @param string
     * @param prefix
     * @return
     */
    public  final static String removePrefix(String string, CharSequence prefix){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 移除后缀， 如果后缀不匹配，则返回原字符串。
     * @param string
     * @param prefix
     * @return
     */
    public final static String removeSuffix(String string, CharSequence prefix){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 没有边界异常，意思是会做边界检查， 越出边界的将给子串起始位置值进行修正，按以下规则顺序：
     * if begin < 0  then begin=0
     * if begin > end then begin = end
     * if begin > str.length then begin=str.length
     *
     * if end < 0 then end =0
     * if end < begin then end = begin
     * if end > str.length then end = str.length
     *
     * 获取子字符串将改变subString 的逻辑， 应该谨慎的在确定的场景下使用
     * @param str  原始字符串
     * @param begin  开始位置（包括）
     * @param end  结束位置（不包括）
     * @return
     */
    public final static String subNoBoundsException(String str, int begin, int end){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 返回按UTF-8 编码的字节数组。
     * @return
     */
    public final static byte[] toUtf8Bytes(String string){
        return string.getBytes(StandardCharsets.UTF_8);
    }


    /**
     *
     * @param string 待查找的字符串
     * @param index  起始位置
     * @param findchars 需要查找的字符（一个或多个）
     * @return 无法查找或没有找到返回 Optional.empty()
     */
    public final  static Optional<Pair<Character,Integer>> findNearest(
            String string, int index, char... findchars){
        if(findchars==null || findchars.length==0){
            return Optional.empty();
        } else{
            for(int i=index;i<string.length();i++){
                char c= string.charAt(i);
                for(char find:findchars){
                    if(c==find){
                        return Optional.of(Pair.of(c,i));
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 跳过所有控制字符和空字符， 找到下一个可打印字符。
     * @param string
     * @param index
     * @return
     */
    public final static Optional<Pair<Character,Integer>> findNextPrintableChar(String string, int index){
        for(int i=index;i<string.length();i++){
            char c = string.charAt(i);
            if(Character.isISOControl(c) || Character.isWhitespace(c)){
                continue;
            } else{
                return Optional.of(Pair.of(c,i));
            }
        }
        return Optional.empty();
    }


    /**
     * 返回UTF-8编码的字符串
     * @return
     */
    public  final static String toUtf8String(byte[] bytes){
        if(bytes==null){
            return null;
        } else {
            return new String(bytes, StandardCharsets.UTF_8);
        }

    }

    public final static String readAllInputStreamToUtf8(InputStream inputStream,boolean closeAfterRead) throws IOException {
        try {
            byte[] bytes = inputStream.readAllBytes();
            return MftStrings.toUtf8String(bytes);
        }finally{
            if(closeAfterRead){
                inputStream.close();
            }
        }
    }




    public String javascriptEscape(String string){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public String javascriptUnescape(String escapedString){
        throw  new UnsupportedOperationException("Not implemented");
    }
}
