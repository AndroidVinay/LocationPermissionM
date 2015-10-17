package com.example.asus.LocationRuntimePermission;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by asus on 10/15/2015.
 */
public class AuthenticatorService extends Service {

private Authenticator mAthenticator;

    @Override
    public void onCreate() {
        mAthenticator = new Authenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAthenticator.getIBinder();
    }
}
