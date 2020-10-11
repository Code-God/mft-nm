package com.meifute.nm.others.business.overseasstatistics.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * @Classname ActivitiesStaticsModel
 * @Description
 * @Date 2020-08-07 13:56
 * @Created by MR. Xb.Wu
 */
@Data
public class ActivitiesStaticsModel extends BaseRowModel {

    @ExcelProperty(index = 0, value = "代理姓名")
    private String agentName;

    @ExcelProperty(index = 1, value = "代理手机号")
    private String agentPhone;

    @ExcelProperty(index = 2, value = "参与活动名称")
    private String activityName;

    @ExcelProperty(index = 3, value = "活动开始时间")
    private Date activityDates;

    @ExcelProperty(index = 4, value = "活动结束时间")
    private Date activityEndDates;

    @ExcelProperty(index = 5, value = "参会人手机号")
    private String participants;

    @ExcelProperty(index = 6, value = "达标人手机号")
    private String qualifiedPerson;

    @ExcelProperty(index = 7, value = "付费金额")
    private String paymentAmount;

    @ExcelProperty(index = 8, value = "达标名额退代手机号")
    private String retrogression;

    @ExcelProperty(index = 9, value = "扣除活动费用金额")
    private String deductedAmount;

    @ExcelProperty(index = 10, value = "抵扣名额人手机号")
    private String deductedPerson;

    @ExcelProperty(index = 11, value = "备注")
    private String remark;
}
