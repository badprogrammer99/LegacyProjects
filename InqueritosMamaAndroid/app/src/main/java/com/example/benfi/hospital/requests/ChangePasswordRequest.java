package com.example.benfi.hospital.requests;

import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.abstracts.AbstractRequest;
import com.example.benfi.hospital.utils.AppUtils;

public final class ChangePasswordRequest extends AbstractRequest {
    private String password;

    public ChangePasswordRequest(String password, OnResponseCallback onResponseCallback) {
        super(AppUtils.getUrl() + "/api/user/change-password");
        this.password = password;
        this.changePassword(onResponseCallback);
    }

    private void changePassword(OnResponseCallback onResponseCallback) {
        this.getStringRequestWrapper().setRequestMethod("POST");

        this.getStringRequestWrapper().addRequestHeaders("Authorization", "Bearer " + AppUtils.getAppToken());
        this.getStringRequestWrapper().addRequestHeaders("Content-Type", "application/x-www-form-urlencoded");

        this.getStringRequestWrapper().addRequestParameters("password", this.password);

        this.getStringRequestWrapper().setOnResponseCallback(onResponseCallback);
        this.getStringRequestWrapper().start();
    }
}
