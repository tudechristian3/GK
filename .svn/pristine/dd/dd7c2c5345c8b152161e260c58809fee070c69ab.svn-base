package com.goodkredit.myapplication.application;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.goodkredit.myapplication.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class GKApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        Shortbread.create(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        super.attachBaseContext(newBase);
    }
}
