package com.example.rakutentv.ui;

import android.app.Activity;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public interface AppContainer {

    ViewGroup get(Activity activity);

    AppContainer DEFAULT = new AppContainer() {
        @Override
        public ViewGroup get(Activity activity) {
            return ButterKnife.findById(activity, android.R.id.content);
        }
    };

}
