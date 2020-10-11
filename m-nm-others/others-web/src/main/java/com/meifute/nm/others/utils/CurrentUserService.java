package com.meifute.nm.others.utils;

import cn.hutool.core.bean.BeanUtil;
import com.meifute.nm.others.feignclient.AuthFeignClient;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname UserUtils
 * @Description
 * @Date 2020-07-08 14:02
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Component
public class CurrentUserService {

    @Autowired
    private AuthFeignClient authFeignClient;

    /**
     * 获取当前用户
     *
     * @return
     */
    public MallUser getCurrentUser() {
        Map<String, Object> currentUsers = authFeignClient.getCurrentUsers();
        return BeanUtil.mapToBeanIgnoreCase(currentUsers, MallUser.class, false);
    }

    public static MftUser getCurrentMftUser() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String userStr = request.getHeader("MFT-USER");
        if (!StringUtils.isEmpty(userStr)){
            MftUser mftUser = BeanUtil.toBean(userStr, MftUser.class);
            if (mftUser == null || mftUser.getId() == null) {
                throw new BusinessException("4003", "非法用户请求");
            }
            return mftUser;
        }
        throw new BusinessException("4003", "非法用户请求");
    }
}
