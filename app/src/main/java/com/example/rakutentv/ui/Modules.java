package com.example.rakutentv.ui;

import com.example.rakutentv.MyApplication;
import com.example.rakutentv.MyApplicationModule;

public class Modules {

    static Object[] list(MyApplication application) {
        return new Object[] {
                new MyApplicationModule(application)
        };
    }

}
