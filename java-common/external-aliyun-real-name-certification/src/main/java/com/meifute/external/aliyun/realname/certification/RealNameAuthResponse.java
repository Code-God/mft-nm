package com.meifute.external.aliyun.realname.certification;

/**
 * @Classname RealNameAuthReponse
 * @Description
 * @Date 2020-07-24 15:19
 * @Created by MR. Xb.Wu
 */
public class RealNameAuthResponse {

    /**
     * 姓名
     */
    private String name;
    /**
     * 证件正面图片
     */
    private String idCardFrontPic;
    /**
     * 证件反面图片
     */
    private String idCardBackPic;
    /**
     * 身份证号
     */
    private String identificationNumber;
    /**
     * 有效期起始时间
     */
    private String idCardStartDate;
    /**
     * 有效期结束时间
     */
    private String idCardExpiry;
    /**
     * 性别 m男f女
     */
    private String sex;
    /**
     * 人面像
     */
    private String userPhoto;
    /**
     * 地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardFrontPic() {
        return idCardFrontPic;
    }

    public void setIdCardFrontPic(String idCardFrontPic) {
        this.idCardFrontPic = idCardFrontPic;
    }

    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdCardStartDate() {
        return idCardStartDate;
    }

    public void setIdCardStartDate(String idCardStartDate) {
        this.idCardStartDate = idCardStartDate;
    }

    public String getIdCardExpiry() {
        return idCardExpiry;
    }

    public void setIdCardExpiry(String idCardExpiry) {
        this.idCardExpiry = idCardExpiry;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
