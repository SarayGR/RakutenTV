package com.example.rakutentv.ui;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.rakutentv.ui.activities.LoginActivity;
import com.example.rakutentv.ui.activities.MainActivity;
import com.example.rakutentv.ui.activities.SplashActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                LoginActivity.class,
                SplashActivity.class
        },
        complete = false,
        library = true
)
public class UIModule {

    @Provides
    @Singleton

    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

}
