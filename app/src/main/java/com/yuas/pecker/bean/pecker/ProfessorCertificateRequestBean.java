package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

public class ProfessorCertificateRequestBean extends BaseBean {

    /**
     * teachName : 1
     * certificate : 1
     * experience : 1
     * age : 1
     * territory : 1 擅长领域
     * sex : 1   / 1 男
     * title : 1  职称
     * uid : 1
     * price : 1
     * bankAcc : 1
     * sum : 1 账户余额
     */

    private String teachName;
    private String certificate;
    private String experience;
    private String age;
    private String territory;
    private String sex;
    private String title;
    private String uid;
    private String price;
    private String bankAcc;
    private String sum;

    public String getTeachName() {
        return teachName;
    }

    public void setTeachName(String teachName) {
        this.teachName = teachName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
