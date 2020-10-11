package com.meifute.nm.others.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MftUser
 * @Description
 * @Date 2020-08-13 14:24
 * @Created by MR. Xb.Wu
 */
@Data
public class MftUser implements Serializable {

    private Long id;

    private String username;

    private String phone;
}
