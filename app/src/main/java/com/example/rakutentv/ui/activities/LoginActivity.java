package com.example.rakutentv.ui.activities;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rakutentv.MyApplication;
import com.example.rakutentv.R;
import com.example.rakutentv.model.CreateLoginSessionDTO;
import com.example.rakutentv.model.TokenDTO;
import com.example.rakutentv.service.ApiAdapter;
import com.example.rakutentv.service.jobs.RequestTokenJob;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<TokenDTO> {

    @InjectView(R.id.etLoginUserLayout)
    EditText etLoginUser;

    @InjectView(R.id.etLoginPasswordLayout)
    EditText etLoginPassword;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @Inject
    JobManager jobManager;

    @Inject
    Bus bus;

    private RequestTokenJob requestTokenJob;

    private TokenDTO tokenDTO;
    private CreateLoginSessionDTO createLoginSessionDTO;
    private String requestToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        MyApplication.get(this).inject(this);

        ButterKnife.inject(this);
        //bus.register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //bus.unregister(this);
    }

    public void callRequestTokenJob() {

    }


    // ------------------------------------- START CALL TO VALIDATE TOKEN WITH LOGIN ---------------------------//

    public void callValidateWithLogin() {
        createLoginSessionDTO = new CreateLoginSessionDTO();
        createLoginSessionDTO.setUsername(etLoginUser.getText().toString());
        createLoginSessionDTO.setPassword(etLoginPassword.getText().toString());
        createLoginSessionDTO.setRequestToken(requestToken);
        Call<TokenDTO> call = ApiAdapter.getApiService().validateWithLogin(createLoginSessionDTO, getString(R.string.api_key));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {

    }

    @Override
    public void onFailure(Call<TokenDTO> call, Throwable t) {

    }
}
