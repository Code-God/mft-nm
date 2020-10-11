package com.meifute.core.data.json;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
public interface FromJsonStringAble {

    /**
     * 将json 解析为该具体对象。
     * @param jsonString  json 风格的字符串
     * @throws MftJsonException 无法解析则应抛出异常。
     */
    void parse(String jsonString) throws MftJsonException;

}
