package com.example.rakutentv.service;

import android.os.Handler;
import android.os.Looper;

import com.example.rakutentv.service.jobs.RequestTokenJob;
import com.example.rakutentv.ui.activities.LoginActivity;
import com.example.rakutentv.ui.activities.MainActivity;
import com.example.rakutentv.ui.activities.SplashActivity;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                SplashActivity.class,
                LoginActivity.class,

                BaseJob.class,
                RequestTokenJob.class
        },
        complete = false
)

public class BusModule {

    @Provides
    @Singleton
    Bus provideBus() {return new MainThreadBus();}

    public static class MainThreadBus extends Bus {
        private final Handler mainThread = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {post(event);}
                });
            }
        }
    }

}
