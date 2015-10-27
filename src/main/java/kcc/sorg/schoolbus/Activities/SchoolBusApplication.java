package kcc.sorg.schoolbus.Activities;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by ford-pro2 on 15/10/22.
 */
public class SchoolBusApplication extends Application {
    public void onCreate(){
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
