package com.english.onlineenglishteacher.ui.login.register;

public class ModelUser {

    private String name;
    private String logo;
    private Boolean isProfileDone;

    public ModelUser() {
    }

    public ModelUser(String name, String logo, Boolean isProfileDone) {
        this.name = name;
        this.logo = logo;
        this.isProfileDone = isProfileDone;
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
