package com.example.benfi.hospital.abstracts;

import com.example.benfi.hospital.wrappers.StringRequestWrapper;

public abstract class AbstractRequest {
    private StringRequestWrapper stringRequestWrapper;

    public AbstractRequest(String requestURL) {
        this.stringRequestWrapper = new StringRequestWrapper(requestURL);
    }

    protected StringRequestWrapper getStringRequestWrapper() { return this.stringRequestWrapper; }
}
