package com.example.rakutentv.service;

import retrofit.RequestInterceptor.RequestFacade;

public interface RestAdapterHeader {
    void addRequestHeader(RequestFacade requestFacade);
}
