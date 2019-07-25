package com.example.benfi.hospital.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.benfi.hospital.interfaces.OnJsonResponseCallback;
import com.example.benfi.hospital.utils.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class SendUserQuestionsRequest {
    private String url;
    private OnJsonResponseCallback onResponseCallback;

    public SendUserQuestionsRequest(JSONObject userAnswers, OnJsonResponseCallback onResponseCallback) {
        this.url = AppUtils.getUrl() + "/api/user/send-user-answers";
        this.onResponseCallback = onResponseCallback;
        this.sendUserAnswers(userAnswers);
    }

    private void sendUserAnswers(JSONObject userAnswers) {
        RequestQueue requestQueue = Volley.newRequestQueue(AppUtils.getAppContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, this.url, userAnswers, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onResponseCallback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResponseCallback.onErrorResponse(error);
            }
        }) {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + AppUtils.getAppToken());
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
