package com.meifute.nm.others.business.training.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname ExportMeetingEnroll
 * @Description
 * @Date 2020-07-13 14:58
 * @Created by MR. Xb.Wu
 */
@Data
public class ExportMeetingEnroll extends BaseRowModel {

    @ExcelProperty(index = 0, value = "报名人姓名")
    private String name;

    @ExcelProperty(index = 1, value = "报名人手机号")
    private String phone;

    @ExcelProperty(index = 2, value = "报名人等级")
    private String agentLevel;

    @ExcelProperty(index = 3, value = "商务编号")
    private String adminCode;

    @ExcelProperty(index = 4, value = "报名时间")
    private LocalDateTime enrollTime;

    @ExcelProperty(index = 5, value = "上级姓名")
    private String leaderName;

    @ExcelProperty(index = 6, value = "上级手机号")
    private String leaderPhone;

    @ExcelProperty(index = 7, value = "上级等级")
    private String leaderAgentLevel;

    @ExcelProperty(index = 8, value = "所在城市")
    private String city;

    @ExcelProperty(index = 9, value = "签到状态")
    private String status;

    @ExcelProperty(index = 10, value = "费用退回")
    private String costBacked;

    @ExcelProperty(index = 11, value = "报名人招总代数")
    private Integer number;

    private String userId;

    private String leaderUserId;
}
