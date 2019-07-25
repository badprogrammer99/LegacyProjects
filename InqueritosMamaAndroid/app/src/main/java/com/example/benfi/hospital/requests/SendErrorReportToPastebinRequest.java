package com.example.benfi.hospital.requests;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.abstracts.AbstractRequest;
import com.example.benfi.hospital.interfaces.OnResponseCallback;

public class SendErrorReportToPastebinRequest extends AbstractRequest implements OnResponseCallback {
    private final String API_DEV_KEY = "38d000d18e43d2f7c6c5727652a15aae";
    private final String API_USER_KEY = "84c1b1e59010b28564b9e2595c03502e";

    public SendErrorReportToPastebinRequest(String error) {
        super("https://pastebin.com/api/api_post.php");
        this.sendErrorReport(error);
    }

    public void sendErrorReport(String error) {
        this.getStringRequestWrapper().setRequestMethod("POST");

        this.getStringRequestWrapper().addRequestParameters("api_option", "paste");
        this.getStringRequestWrapper().addRequestParameters("api_dev_key", API_DEV_KEY);
        this.getStringRequestWrapper().addRequestParameters("api_user_key", API_USER_KEY);
        this.getStringRequestWrapper().addRequestParameters("api_paste_code", error);
        this.getStringRequestWrapper().addRequestParameters("api_paste_private", "2");
        this.getStringRequestWrapper().addRequestParameters("api_paste_expire_date", "N");
        this.getStringRequestWrapper().addRequestParameters("api_paste_format", "text");

        this.getStringRequestWrapper().setOnResponseCallback(this);
        this.getStringRequestWrapper().start();
    }

    @Override
    public void onSuccessResponse(String response) {
        //
    }

    @Override
    public void onErrorResponse(VolleyError response) {
        //
    }
}
