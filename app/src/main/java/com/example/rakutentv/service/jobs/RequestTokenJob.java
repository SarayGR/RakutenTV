package com.example.rakutentv.service.jobs;

import android.app.Application;
import android.util.Log;

import com.example.rakutentv.R;
import com.example.rakutentv.StringsConstants;
import com.example.rakutentv.model.TokenDTO;
import com.example.rakutentv.service.ApiAdapter;
import com.example.rakutentv.service.ApiService;
import com.example.rakutentv.service.BaseJob;
import com.example.rakutentv.service.GenericRestAdapter;
import com.example.rakutentv.service.events.RequestTokenEvent;
import com.example.rakutentv.ui.activities.LoginActivity;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.Params;
import com.path.android.jobqueue.network.NetworkUtil;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

public class RequestTokenJob extends BaseJob<RequestTokenEvent> {

    public static final int PRIORITY = 4;

    private TokenDTO tokenDTO;
    private String requestToken;

    @Inject
    JobManager jobManager;

    @Inject
    public RequestTokenJob(Params params, Application application, Bus bus, NetworkUtil netWorkUtil, JobManager jobManager) {
        super(new Params(PRIORITY), application, bus, netWorkUtil);
    }

    @Override
    protected void run() throws SQLException, IOException {
        ApiService apiService = new GenericRestAdapter<ApiService>(ApiService.class, getContext()).getImplementedServiceInterface(null, null);
        apiService.getRequestToken(StringsConstants.API_KEY);

    }

    @Override
    protected boolean isNetworkRequired() {
        return false;
    }

    // ------------------------------------- START CALL TO REQUEST TOKEN -----------------------------------------//
   /* public void run() throws SQLException, IOException {
        Call<TokenDTO> call = ApiAdapter.getApiService().getRequestToken(StringsConstants.API_KEY);
        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                if (response.isSuccessful()) {
                    tokenDTO = response.body();
                    requestToken = tokenDTO.getRequestToken().toString();
                    Log.d("SARAY", tokenDTO.getRequestToken().toString());
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                if(t!=null) t.printStackTrace();
            }
        });
    }*/

    // ------------------------------------- END CALL TO REQUEST TOKEN -----------------------------------------//


}
