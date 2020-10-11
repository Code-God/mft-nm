package com.meifute.nm.others.config.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.meifute.nm.otherscommon.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * @Classname CustomIdGenerator
 * @Description 自定义id生成器
 * @Date 2020-07-06 14:49
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        String name = (String) metaObject.getValue("name");
        Long id = IDUtils.genLongId();
        log.info("为{}生成主键值->:{}", name, id);
        return id;
    }

}
