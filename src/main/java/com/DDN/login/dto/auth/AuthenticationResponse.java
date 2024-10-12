package com.DDN.login.dto.auth;

import com.DDN.login.model.User;

public class AuthenticationResponse {
    private String jwt;
    private String error;
    private User userInfo;

    public AuthenticationResponse(String jwt, String error, User userInfo){
        this.jwt = jwt;
        this.error = error;
        this.userInfo = userInfo;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public AuthenticationResponse() {}

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
