package com.example.rakutentv;

import android.app.Application;

import com.example.rakutentv.service.BusModule;
import com.example.rakutentv.ui.UIModule;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                UIModule.class,
                BusModule.class
        },
        injects = {
                MyApplication.class
        },
        complete = false,
        library = true
)

public final class MyApplicationModule {
    private final MyApplication application;

    public MyApplicationModule(MyApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    MyApplication provideApplication() {return application;}
}
