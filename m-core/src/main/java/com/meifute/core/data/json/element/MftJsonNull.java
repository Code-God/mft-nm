package com.meifute.core.data.json.element;

import com.meifute.core.data.json.FromJsonStringAble;
import com.meifute.core.data.json.JsonUtil;
import com.meifute.core.data.json.MftJsonException;
import com.meifute.core.lang.MftStrings;

/**
 * @author wzeng
 * @date 2020/8/5
 * @Description
 */
public class MftJsonNull extends AbstractJsonElement implements FromJsonStringAble {

    @Override
    public void parse(String jsonString) throws MftJsonException {
       if(!JsonUtil.NULL.equals(jsonString)){
           throw new MftJsonException("Illegal json string for null:"+ MftStrings.abbreviate(jsonString,"...",20));
       }
    }

    @Override
    public String toJsonString() {
        return JsonUtil.NULL;
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
