package com.meifute.nm.others.business.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.AmoyActivityEnroll;
import com.meifute.nm.others.business.training.entity.AmoyActivitySignin;
import com.meifute.nm.others.business.training.mapper.AmoyActivitySigninMapper;
import com.meifute.nm.others.business.training.service.AmoyActivityEnrollService;
import com.meifute.nm.others.business.training.service.AmoyActivitySigninService;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname AmoyActivitySigninServiceImpl
 * @Description
 * @Date 2020-08-20 11:08
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class AmoyActivitySigninServiceImpl extends ServiceImpl<AmoyActivitySigninMapper, AmoyActivitySignin> implements AmoyActivitySigninService {

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private AmoyActivityEnrollService amoyActivityEnrollService;

    @Override
    public List<AmoyActivitySignin> getMySigninRecord(String enrollId) {
        MallUser currentUser = currentUserService.getCurrentUser();
        return this.list(new QueryWrapper<AmoyActivitySignin>()
                .eq("user_id", currentUser.getId())
                .eq("enroll_id", enrollId)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
    }

    @RedisLock(key = "enrollId")
    @Override
    public boolean signIn(Integer signInPlace, String longitudeAndLatitude, String enrollId) {
        if (LocalDateTime.of(2020, 9, 7, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException("10003", "当前时间不支持签到哦");
        }

        if (!Arrays.asList(1, 2, 3, 4).contains(signInPlace)) {
            throw new BusinessException("10001", "无效二维码");
        }

        AmoyActivityEnroll enroll = amoyActivityEnrollService.getByEnrollId(enrollId);
        if (enroll.getEnrollStatus() != 2) {
            throw new BusinessException(SysErrorEnums.NOT_ENROLL);
        }

        List<AmoyActivitySignin> list = this.list(new QueryWrapper<AmoyActivitySignin>()
                .eq("enroll_id", enrollId)
                .eq("sign_in_place", signInPlace)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("10001", "您已经签到过了");
        }


        if (Arrays.asList(1, 3, 4).contains(signInPlace)) {
            if (StringUtils.isEmpty(longitudeAndLatitude)) {
                throw new BusinessException("10000", "定位失败");
            }
            //校验经纬度
            String[] lal = longitudeAndLatitude.split(",");
            double longitude = Double.parseDouble(lal[0]);
            double latitude = Double.parseDouble(lal[1]);


            boolean inRange = isInRange(1000, 24.537767, 118.172755, latitude, longitude);
            if (!inRange) {
                throw new BusinessException("10000", "您当前再不签到范围内");
            }
        }

        AmoyActivitySignin amoyActivitySignin = new AmoyActivitySignin();
        amoyActivitySignin.setId(IDUtils.genId());
        amoyActivitySignin.setEnrollId(enrollId);
        amoyActivitySignin.setUserId(enroll.getUserId());
        amoyActivitySignin.setSignInPlace(signInPlace);
        amoyActivitySignin.setLongitudeAndLatitude(longitudeAndLatitude);
        this.save(amoyActivitySignin);

        return true;
    }

    @Override
    public List<AmoyActivitySignin> querySignByEnrollId(String enrollId) {
        return this.list(new QueryWrapper<AmoyActivitySignin>()
                .eq("enroll_id", enrollId)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
    }


    /**
     * 判断经纬度是否在范围内， 计算到圆点的距离是否大于半径
     *
     * @param raduis 圆的半径
     * @param lat    点的纬度
     * @param lng    点的经度
     * @param lat1   圆的纬度
     * @param lng1   圆的经度
     * @return
     */
    private boolean isInRange(int raduis, double lat, double lng, double lat1, double lng1) {
        double R = 6378137.0; //地球半径 米
        double dLat = (lat1 - lat) * Math.PI / 180;
        double dLng = (lng1 - lng) * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat * Math.PI / 180) * Math.cos(lat1 * Math.PI / 180) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        double dis = Math.round(d);
        //点在圆内
        return dis <= raduis;
    }
}
