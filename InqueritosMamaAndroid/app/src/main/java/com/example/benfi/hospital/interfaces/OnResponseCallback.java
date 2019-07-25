package com.example.benfi.hospital.interfaces;

import com.android.volley.VolleyError;

public interface OnResponseCallback {
    void onSuccessResponse(String response);
    void onErrorResponse(VolleyError response);
}
