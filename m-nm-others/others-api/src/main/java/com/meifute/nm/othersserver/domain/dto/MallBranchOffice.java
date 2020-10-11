package com.meifute.nm.othersserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: wuxb
 * @Date: 2019-06-24 20:02
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MallBranchOffice implements Serializable {

    private String id;

    private String companyName;

    private String userId;

    private String status;

    private String remark;

    private String isDel;

    private Date createDate;

    private String phone;

}
