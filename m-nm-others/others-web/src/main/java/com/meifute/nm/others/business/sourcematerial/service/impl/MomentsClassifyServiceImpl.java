package com.meifute.nm.others.business.sourcematerial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.sourcematerial.entity.MomentsClassification;
import com.meifute.nm.others.business.sourcematerial.entity.MomentsSource;
import com.meifute.nm.others.business.sourcematerial.mapper.MomentsClassifyMapper;
import com.meifute.nm.others.business.sourcematerial.service.MomentsClassifyService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsSourceService;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.vo.moments.MomentsClassificationVO;
import com.meifute.nm.othersserver.domain.vo.moments.SortClassification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname MomentsClassifyServiceImpl
 * @Description
 * @Date 2020-08-13 12:22
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class MomentsClassifyServiceImpl extends ServiceImpl<MomentsClassifyMapper, MomentsClassification> implements MomentsClassifyService {

    @Autowired
    private MomentsSourceService momentsSourceService;


    @RedisLock(key = "classifyName")
    @Override
    public boolean createClassification(MomentsClassificationVO momentsClassificationVO) {
        MomentsClassification momentsClassification = new MomentsClassification();
        BeanUtils.copyProperties(momentsClassificationVO, momentsClassification);
        momentsClassification.setId(IDUtils.genLongId());
        List<MomentsClassification> list = this.list(new QueryWrapper<MomentsClassification>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .orderByAsc("sort"));
        if (CollectionUtils.isEmpty(list)) {
            momentsClassification.setSort(1);
        } else {
            momentsClassification.setSort(list.size() + 1);
        }
        return this.save(momentsClassification);
    }

    @Override
    public boolean editClassification(MomentsClassificationVO momentsClassificationVO) {
        MomentsClassification momentsClassification = new MomentsClassification();
        BeanUtils.copyProperties(momentsClassificationVO, momentsClassification);
        momentsClassification.setId(Long.parseLong(momentsClassificationVO.getId()));
        return this.updateById(momentsClassification);
    }

    @Transactional
    @Override
    public boolean deleteClassification(Long id) {
        //查询该分类下是否有素材
        List<MomentsSource> source = momentsSourceService.getMomentSourceByClassifyId(id);
        //如果有素材不允许删除
        if (!CollectionUtils.isEmpty(source)) {
            throw new BusinessException("00998", "注意！该分类下有资讯关联，暂不支持删除，如需删除分类，请保持该分类下无资讯信息哦～");
        }
        //删除
        this.removeById(id);
        //重新排序
        List<MomentsClassification> classifications = this.list(new QueryWrapper<MomentsClassification>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .orderByAsc("sort"));
        List<MomentsClassification> collect = classifications.stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            int index = 1;
            for (MomentsClassification c : collect) {
                c.setSort(index);
                ++index;
            }
            this.updateBatchById(collect);
        }
        return true;
    }

    @RedisLock(key = "id")
    @Override
    public boolean sortClassification(SortClassification sortClassification) {
        List<MomentsClassification> list = new ArrayList<>();
        MomentsClassification classification = this.getById(Long.parseLong(sortClassification.getId()));
        Integer oldSortNumber = classification.getSort();
        if (sortClassification.getUpOrDown() == 0) { //升
            List<MomentsClassification> classifications = this.list(new QueryWrapper<MomentsClassification>()
                    .eq("sort", classification.getSort() - 1).eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
            if (CollectionUtils.isEmpty(classifications)) {
                return true;
            }
            MomentsClassification c = classifications.get(0);
            int newSortNumber = c.getSort();
            c.setSort(oldSortNumber);
            list.add(c);
            classification.setSort(newSortNumber);
        }
        if (sortClassification.getUpOrDown() == 1) { //降
            List<MomentsClassification> classifications = this.list(new QueryWrapper<MomentsClassification>()
                    .eq("sort", classification.getSort() + 1).eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
            if (CollectionUtils.isEmpty(classifications)) {
                return true;
            }
            MomentsClassification c = classifications.get(0);
            int newSortNumber = c.getSort();
            c.setSort(oldSortNumber);
            list.add(c);
            classification.setSort(newSortNumber);
        }
        list.add(classification);
        return this.updateBatchById(list);
    }

    @Override
    public List<MomentsClassificationVO> queryClassification() {
        List<MomentsClassification> list = this.list(new QueryWrapper<MomentsClassification>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .orderByAsc("sort"));
        List<MomentsClassificationVO> vos = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return vos;
        }
        list.forEach(p -> {
            MomentsClassificationVO vo = new MomentsClassificationVO();
            BeanUtils.copyProperties(p, vo);
            vo.setId(String.valueOf(p.getId()));
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public MomentsClassification queryById(Long id) {
        return this.getById(id);
    }
}
