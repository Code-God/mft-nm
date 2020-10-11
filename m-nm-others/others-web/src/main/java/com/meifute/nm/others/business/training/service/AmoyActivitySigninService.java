package com.meifute.nm.others.business.training.service;

import com.meifute.nm.others.business.training.entity.AmoyActivitySignin;

import java.util.List;

/**
 * @Classname AmoyActivitySigninService
 * @Description
 * @Date 2020-08-19 17:42
 * @Created by MR. Xb.Wu
 */
public interface AmoyActivitySigninService {

    List<AmoyActivitySignin> getMySigninRecord(String enrollId);

    boolean signIn(Integer signInPlace, String longitudeAndLatitude, String enrollId);

    List<AmoyActivitySignin> querySignByEnrollId(String enrollId);
}
