package com.example.rakutentv.service;

import com.example.rakutentv.model.CreateLoginSessionDTO;
import com.example.rakutentv.model.TokenDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/authentication/token/new")
    Call<TokenDTO> getRequestToken(@Query("api_key") String apiKey);

    @POST("3/authentication/token/validate_with_login")
    Call<TokenDTO> validateWithLogin(@Body CreateLoginSessionDTO createLoginSessionDTO, @Query("api_key") String apiKey);


}