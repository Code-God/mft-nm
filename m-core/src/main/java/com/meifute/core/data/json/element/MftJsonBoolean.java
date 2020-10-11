package com.meifute.core.data.json.element;

import com.meifute.core.data.json.FromJsonStringAble;
import com.meifute.core.data.json.MftJsonException;

/**
 * @author wzeng
 * @date 2020/8/5
 * @Description
 */
public class MftJsonBoolean extends AbstractJsonElement implements FromJsonStringAble {

    final static String FALSE="false";
    final static String TRUE="true";
    boolean value;

    public MftJsonBoolean(boolean bool){
        this.value =bool;
    }

    @Override
    public void parse(String jsonString) throws MftJsonException {
        if(FALSE.equals(jsonString)){
            this.value=false;
        } else if(TRUE.equals(jsonString)){
            this.value=true;
        } else{
            throw  new MftJsonException("Can not convert "+jsonString+" to boolean type");
        }

    }

    @Override
    public String toJsonString() {
        return String.valueOf(this.value);
    }

    @Override
    public String toString() {
         return String.valueOf(value);
    }
}
