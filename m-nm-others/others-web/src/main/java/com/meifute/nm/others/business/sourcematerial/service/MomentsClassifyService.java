package com.meifute.nm.others.business.sourcematerial.service;

import com.meifute.nm.others.business.sourcematerial.entity.MomentsClassification;
import com.meifute.nm.othersserver.domain.vo.moments.MomentsClassificationVO;
import com.meifute.nm.othersserver.domain.vo.moments.SortClassification;

import java.util.List;

/**
 * @Classname MomentClassificationService
 * @Description
 * @Date 2020-08-13 11:08
 * @Created by MR. Xb.Wu
 */
public interface MomentsClassifyService {

    boolean createClassification(MomentsClassificationVO momentsClassificationVO);

    boolean editClassification(MomentsClassificationVO momentsClassificationVO);

    boolean deleteClassification(Long id);

    boolean sortClassification(SortClassification sortClassification);

    List<MomentsClassificationVO> queryClassification();

    MomentsClassification queryById(Long id);
}
