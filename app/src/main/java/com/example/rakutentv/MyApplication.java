package com.example.rakutentv;

import android.app.Application;
import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;

import dagger.ObjectGraph;

public class MyApplication extends Application {

    private ObjectGraph objectGraph;
    private static MyApplication myApplication;

    /*public enum TrackerName{
        APP_TRACKER,
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();*/

    public MyApplication() {
        myApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildObjectGraphAndInject();
    }

    public void buildObjectGraphAndInject() {
        objectGraph = ObjectGraph.create((Arrays.<Object>asList(new MyApplicationModule(this))).toArray());
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    /*public ObjectGraph getObjectGraph() {
        return objectGraph;
    }*/

}
