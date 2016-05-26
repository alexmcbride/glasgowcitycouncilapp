package com.alexmcbride.glasgowcitycouncilapp;

public class User {
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private Login mLogin;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }
}
