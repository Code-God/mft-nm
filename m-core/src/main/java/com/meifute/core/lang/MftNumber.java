package com.meifute.core.lang;

/**
 * @author wzeng
 * @date 2020/8/9
 * @Description
 */
public class MftNumber {



    public final static boolean isHexChar(char c){
        if(Character.isDigit(c)){
            return true;
        } else{

                if(c>='a' && c<='f'
                || c>='A' && c<='F'){
                    return  true;
                }

        }
        return false;
    }

}
