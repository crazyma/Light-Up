package com.beibeilab.lightup;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by david on 2017/6/3.
 */

public class LightUpProvider extends AppWidgetProvider {

    public static final String ACTION_CLICK = "ACTION_CLICK";

    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, LightUpProvider.class);
            intent.setAction(ACTION_CLICK);

            //  set broadcast type PendingIntent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            views.setOnClickPendingIntent(R.id.image_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null) {    //	by broadcast
            if(intent.getAction().equals(ACTION_CLICK)) {
                Log.d("crazyma", "widget click!");
                getBrightness(context);
            }
        }
        super.onReceive(context, intent);
    }

    private void getBrightness(Context context){
        //Get the content resolver
        cResolver = context.getContentResolver();

        try {
            // To handle the auto
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);

            Log.d("crazyma", "brightness : " + brightness);

            changeBrightness(context);
        } catch (Settings.SettingNotFoundException e) {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            Toast.makeText(context, R.string.light_up_fail, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void changeBrightness(Context context){
        PrefHelper prefHelper = PrefHelper.getInstance(context);
        if(brightness != 255) {
            prefHelper.setBrightness(brightness);
            brightness = 255;
            Toast.makeText(context, R.string.light_up, Toast.LENGTH_SHORT).show();
        }else{
            brightness = prefHelper.getBrightness();
        }
        //Set the system brightness using the brightness variable value
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

}
