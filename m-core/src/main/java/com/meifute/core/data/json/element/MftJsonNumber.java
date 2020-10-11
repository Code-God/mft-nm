package com.meifute.core.data.json.element;

import com.meifute.core.data.json.FromJsonStringAble;
import com.meifute.core.data.json.JsonUtil;
import com.meifute.core.data.json.MftJsonException;

import java.util.Objects;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
public class MftJsonNumber extends AbstractJsonElement implements FromJsonStringAble,Comparable<MftJsonNumber> {

    Number number = 0;
    final static String DOT=".";

    public MftJsonNumber(){

    }

    public MftJsonNumber(Number value){
        if(value instanceof Integer){
            this.number=value.longValue();
        } else if(value instanceof Float){
            this.number= value.doubleValue();
        } else{
            this.number = value;
        }
    }



    @Override
    public void parse(String jsonString) throws MftJsonException {
        if(JsonUtil.isJsonNumber(jsonString)){
            if(jsonString.contains(DOT)){
                number = Double.parseDouble(jsonString);
            } else{
                number= Long.parseLong(jsonString);
            }
        } else{
            throw new MftJsonException(jsonString +" is not a parsable number");
        }
    }

    @Override
    public String toJsonString() {
        return String.valueOf(number);
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MftJsonNumber)) {
            return false;
        }
        MftJsonNumber that = (MftJsonNumber) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }


    @Override
    public int compareTo(MftJsonNumber o) {
       if(this.number.doubleValue()-o.number.doubleValue()==0){
           return 0;
       } else if(this.number.doubleValue()-o.number.doubleValue()>0){
           return 1;
       } else{
           return -1;
       }
    }
}
