package com.example.rakutentv.service;

import android.app.Application;
import android.content.Context;

import com.example.rakutentv.R;
import com.example.rakutentv.model.ErrorDTO;
import com.example.rakutentv.service.events.CommunicationErrorEvent;
import com.example.rakutentv.service.events.ConnectionNotAvailableEvent;
import com.example.rakutentv.service.events.GenericErrorEvent;
import com.example.rakutentv.service.events.StrongErrorEvent;
import com.example.rakutentv.service.events.WeakErrorEvent;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.path.android.jobqueue.network.NetworkUtil;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.sql.SQLException;

import retrofit.RetrofitError;
import retrofit2.Retrofit;
import timber.log.Timber;

public abstract class BaseJob<T> extends Job {

    private final Application application;
    private Bus bus;
    private NetworkUtil netWorkUtil;

    public BaseJob(Params params, Application application, Bus bus, NetworkUtil netWorkUtil) {
        super(params);
        this.application = application;
        this.bus = bus;
        this.netWorkUtil = netWorkUtil;
    }

    @Override
    public void onRun() throws Throwable {
        if (!netWorkUtil.isConnected(application)) {
            if(isNetworkRequired()) {
                postConnectionNotAvailableEvent();
                return;
            }
        } try {
            run();
        } catch (IOException e) {
            Timber.e(e, "IOException executing job");
            postCommunicationErrorEvent();
        }
    }

    public boolean hasIternetConnection() {return netWorkUtil.isConnected(application);}

    protected abstract void run() throws SQLException, IOException;

    protected void onErrorEvent(RetrofitError error) {
        if (error != null && error.getResponse() != null) {
            ErrorDTO errorDTO = null;
            switch (error.getResponse().getStatus()) {
                case 400:
                    errorDTO = (ErrorDTO) error.getBodyAs(ErrorDTO.class);
                    if (errorDTO == null) {
                        errorDTO = new ErrorDTO();
                        errorDTO.setCode("400");
                        errorDTO.setMessage("No se ha encontrado ningún resultado con los datos introducidos");
                    }
                    postGenericErrorEvent(errorDTO);
            }
        }
    }

    private void postGenericErrorEvent(ErrorDTO errorDTO) {
        bus.post(new GenericErrorEvent(errorDTO));
    }

    private void postWeakErrorEvent(ErrorDTO errorDTO) {bus.post(new WeakErrorEvent(errorDTO));}

    private void postStrongErrorEvent(ErrorDTO errorDTO) {
        bus.post(new StrongErrorEvent(errorDTO));
    }

    private void postCommunicationErrorEvent() {bus.post(new CommunicationErrorEvent());}

    private void postConnectionNotAvailableEvent() {bus.post(new ConnectionNotAvailableEvent());}

    protected abstract boolean isNetworkRequired();

    @Override
    public void onAdded() {

    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }

    public void setNetWorkUtil(NetworkUtil netWorkUtil) {this.netWorkUtil = netWorkUtil;}

    public static class SuccessEvent<T> {
        public T result;
        public SuccessEvent(T result) {this.result = result;}
        public T getResult() {return result;}
    }

    protected void postSuccessfulEvent(T result) {
        bus.post(result);
    }

    public Context getContext() {
        return application;
    }

}
