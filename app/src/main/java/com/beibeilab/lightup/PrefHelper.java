package com.beibeilab.lightup;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 2017/6/3.
 */

public class PrefHelper {
    private final String TAG = "light_up_pref";
    private final String PREF_BRIGHTNESS = "pref_brightness";

    static private PrefHelper instance;

    private SharedPreferences settings;

    private PrefHelper(Context context) {
        settings = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public static PrefHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (PrefHelper.class) {
                if (instance == null) {
                    instance = new PrefHelper(context);
                }
            }
        }
        return instance;
    }

    public void setBrightness(int brightness){
        settings.edit()
                .putInt(PREF_BRIGHTNESS, brightness)
                .apply();
    }

    public int getBrightness(){
        int brightness = settings.getInt(PREF_BRIGHTNESS, 128);
        return brightness;
    }
}
