package com.english.onlineenglishteacher.ui.login.register;

public class ModelUser {

    private String name;
    private String uid;
    private String logo;
    private Boolean isProfileDone;
    private String status;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelUser() {
    }

    public ModelUser(String name, String logo, Boolean isProfileDone, String uid) {
        this.name = name;
        this.logo = logo;
        this.isProfileDone = isProfileDone;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getProfileDone() {
        return isProfileDone;
    }

    public void setProfileDone(Boolean profileDone) {
        isProfileDone = profileDone;
    }
}
