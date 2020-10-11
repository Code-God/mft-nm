package com.meifute.core.tool;

import java.util.Optional;
import java.util.StringTokenizer;

/**
 * @author wzeng
 * @date 2020/7/29
 * @Description
 */
public class PrefixNamer {

    public static final char DEFAULT_CONNECT_CHAR = '_';
    private final  String scenarios;
    private DeployEnvironment  deployEnvironment=DeployEnvironment.DEV;
    private String serviceName = "DEMO";

    /**
     * @param scenarios  用于区分不同的场景， 可以使用任意的名字用于明确表达意图，比如"SmokeTest"之类，
     * @param deployEnvironment  部署类型 {@link DeployEnvironment}
     * @param serviceName 产品名， 如（m-mall-agent， ms ，nm-order 等）
     */
    public PrefixNamer(String scenarios, DeployEnvironment deployEnvironment, String serviceName){
        if(scenarios==null){

            throw new NullPointerException("Scenarios should not be null");
        } else {
            this.scenarios = scenarios;
        }

        if(deployEnvironment!=null) {
            this.deployEnvironment = deployEnvironment;
        }
        this.serviceName =Optional.ofNullable(serviceName).orElse(this.serviceName);
    }

    /**
     * Full name 上包含前缀的名称
     * @param simpleName 一个需要添加前缀的名称
     * @return 返回的名称中包含前缀， 如果该名称已经包含前缀，则返回原来的名称,输入null 返回null
     */
    public String getEntityFullName(String simpleName){

       return getEntityFullName(simpleName,Optional.of(DEFAULT_CONNECT_CHAR));
    }

    public String getEntityFullName(String simpleName,Optional<Character> connectChar){
        char connect=connectChar.orElse(DEFAULT_CONNECT_CHAR);
        String prefix=getPrefix();

        if(simpleName==null){
            return null;
        }

        if(simpleName.startsWith(prefix)){
            return simpleName;
        } else {
            return getPrefix() + connect + simpleName;
        }
    }

    /**
     * 返回不包含前缀的名称
     * @param fullName  名称可以包含或者不包含前缀
     * @return 返回不包含前缀的名称, 输入如果为null 则返回null；
     */
    public String getSimpleName(String fullName){
        if(fullName==null){
            return null;
        }
        String prefix=getPrefix();
        String returnName=fullName;
        while(returnName.startsWith(prefix) && returnName.length()>prefix.length()+1){
            returnName=returnName.substring(prefix.length()+1);
        }
        return returnName;

    }

    public String getPrefix(){
        if(this.deployEnvironment==DeployEnvironment.PROD){
            return this.scenarios+DEFAULT_CONNECT_CHAR+this.serviceName;
        } else {
            return this.scenarios + "_" + this.deployEnvironment.name() + "_" + this.serviceName;
        }
    }
}
