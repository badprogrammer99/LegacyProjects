package com.example.benfi.hospital.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface OnJsonResponseCallback {
    void onSuccessResponse(JSONObject response);
    void onErrorResponse(VolleyError response);
}
