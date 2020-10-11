package com.meifute.external.jdexpress;


/**
 * @Classname JdClient
 * @Description
 * @Date 2020-07-23 17:05
 * @Created by MR. Xb.Wu
 */
public interface MftJdClient {

    <T extends AbstractResponse> T execute(JdRequest<T> request);
}
