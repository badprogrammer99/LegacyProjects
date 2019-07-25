package com.example.benfi.hospital.wrappers;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.utils.AppUtils;

import java.util.HashMap;

public class StringRequestWrapper {
    private String requestURL;
    private int requestMethod;
    private HashMap<String, String> requestHeaders = new HashMap<>();
    private HashMap<String, String> requestParameters = new HashMap<>();
    private RequestQueue requestQueue = Volley.newRequestQueue(AppUtils.getAppContext());
    private OnResponseCallback onResponseCallback;

    public StringRequestWrapper(String requestURL) {
        this.requestURL = requestURL;
    }

    public void setRequestMethod(String requestMethod) {
        switch (requestMethod.toUpperCase()) {
            case "GET":
                this.requestMethod = Request.Method.GET;
                break;
            case "POST":
                this.requestMethod = Request.Method.POST;
                break;
            case "PATCH":
                this.requestMethod = Request.Method.PATCH;
                break;
            case "PUT":
                this.requestMethod = Request.Method.PUT;
                break;
            case "DELETE":
                this.requestMethod = Request.Method.DELETE;
                break;
            default:
                break;
        }
    }

    public void addRequestHeaders(String requestHeaderName, String requestHeaderValue) {
        this.requestHeaders.put(requestHeaderName, requestHeaderValue);
    }

    public void addRequestParameters(String requestParameterName, String requestParameterValue) {
        this.requestParameters.put(requestParameterName, requestParameterValue);
    }

    public void setOnResponseCallback(OnResponseCallback onResponseCallback) {
        this.onResponseCallback = onResponseCallback;
    }

    public void start() {
        if (this.onResponseCallback != null) {
            this.requestQueue.add(this.constructNewStringRequest());
        } else {
            Log.e("StringRequestWrapper", "A callback was not specified!");
        }
    }

    private StringRequest constructNewStringRequest() {
        final StringRequestWrapper stringRequestWrapperInstance = this;
        return new StringRequest(this.requestMethod, this.requestURL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    stringRequestWrapperInstance.onResponseCallback.onSuccessResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError response) {
                    try {
                        stringRequestWrapperInstance.onResponseCallback.onErrorResponse(response);
                    } catch (NullPointerException e) {
                        Log.e("ResponseError", response.toString());
                    }
                }
        }) {
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                return stringRequestWrapperInstance.requestParameters;
            }

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                return stringRequestWrapperInstance.requestHeaders;
            }
        };
    }
}
