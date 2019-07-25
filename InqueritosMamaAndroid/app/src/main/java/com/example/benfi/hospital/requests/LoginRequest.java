package com.example.benfi.hospital.requests;

import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.abstracts.AbstractRequest;
import com.example.benfi.hospital.utils.AppUtils;

public final class LoginRequest extends AbstractRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password, OnResponseCallback onResponseCallback) {
        super(AppUtils.getUrl() + "/api/user/login");
        this.username = username;
        this.password = password;
        this.attemptLogin(onResponseCallback);
    }

    private void attemptLogin(OnResponseCallback onResponseCallBack) {
        this.getStringRequestWrapper().setRequestMethod("POST");

        this.getStringRequestWrapper().addRequestHeaders("Content-Type", "application/x-www-form-urlencoded");

        this.getStringRequestWrapper().addRequestParameters("patient_id", this.username);
        this.getStringRequestWrapper().addRequestParameters("password", this.password);

        this.getStringRequestWrapper().setOnResponseCallback(onResponseCallBack);
        this.getStringRequestWrapper().start();
    }
}
