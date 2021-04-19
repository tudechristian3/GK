package com.goodkredit.myapplication.utilities;

import android.content.Context;

import com.scottyab.rootbeer.RootBeer;

public class DeviceRoot {

    public static boolean isDeviceRooted(Context context) {
        RootBeer rootBeer = new RootBeer(context);
        rootBeer.isRootedWithoutBusyBoxCheck();
        return rootBeer.isRooted();
    }
}
