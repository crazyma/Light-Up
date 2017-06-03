package com.beibeilab.lightup;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by david on 2017/6/3.
 */

public class LightUpProvider extends AppWidgetProvider {

    public static final String ACTION_CLICK = "ACTION_CLICK";

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
            }
        }
        super.onReceive(context, intent);
    }

}
