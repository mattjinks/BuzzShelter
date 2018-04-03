package com.example.hp.buzzshelter;

import java.io.Serializable;

/**
 * Created by HP on 3/20/2018.
 */

public abstract class User implements Serializable {

    private static final long serialVersionID = 1L;

    private String name;
    private String loginEmail;
    private String contactEmail;
    private String password;
    private Boolean accountState;

    public User(String name, String loginEmail, String password) {
        this.name = name;
        this.loginEmail = loginEmail;
        this.contactEmail = loginEmail;
        this.password = password;
        this.accountState = true;
    }



    public void setName(String username) {
        this.name = username;
    }

    public void setEmailLogin(String email) {
        this.loginEmail = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountState(Boolean accountState) {
        this.accountState = accountState;
    }

    public void setContactEmail(String email) { this.contactEmail = email; }

    public String getName() { return name; }

    public String getLoginEmail() { return loginEmail; }

    public String getContactEmail() { return contactEmail; }

    public String getPassword() { return password; }

    public Boolean getAccountState() { return accountState; }



}
