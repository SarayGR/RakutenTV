package com.example.rakutentv.service;

import android.content.Context;

import com.example.rakutentv.data.GenericPreference;
import com.example.rakutentv.data.PreferenceConstant;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class InterceptingOkClient extends OkClient {

    private Context context;

    public InterceptingOkClient(OkHttpClient client, Context context) {
        super(client);
        this.context = context;
    }

    @Override
    public Response execute(Request request) throws IOException {
        Response response = super.execute(request);
        for (Header header : response.getHeaders()){
            if (header.getName().equalsIgnoreCase(GenericRestAdapter.SESSION_ID)) {
                GenericPreference.setPreferences(context, PreferenceConstant.PREF_UUID, header.getValue());
            }
        }
        return response;
    }
}
