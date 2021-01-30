package com.example.rakutentv.service;

import android.content.Context;

import com.example.rakutentv.service.serializer.BooleanTypeAdapter;
import com.example.rakutentv.service.serializer.DateTypeAdapter;
import com.example.rakutentv.service.serializer.DoubleTypeAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Converter;

public class GenericRestAdapter<T> {

    private static final Integer TIMEOUT = 30;
    public static final String SESSION_ID = "session_id";
    private static final String ENDPOINT = "https://api.themoviedb.org/";

    private Class<T> serviceInterface;
    private Context context;

    public GenericRestAdapter(Class<T> serviceInterface, Context context) {
        this.serviceInterface = serviceInterface;
        this.context = context;
    }

    public T getImplementedServiceInterface(RestAdapterOption restAdapterOptions, final RestAdapterHeader restAdapterHeader) {
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setClient(getUnsafeOkHttpCliente(context))
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(getJSONConverter());
        if (restAdapterOptions != null) {
            restAdapterOptions.addOptions(restAdapterBuilder);
        }
        return restAdapterBuilder.build().create(serviceInterface);
    }

    private GsonConverter getJSONConverter() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
                .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        return new GsonConverter(gson);
    }

    private Client getUnsafeOkHttpCliente(Context context) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return new InterceptingOkClient(okHttpClient, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
