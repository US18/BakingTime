package com.example.uplabdhisingh.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider
{

String ingredientsWidget="";

     void  updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
     {
        // Construct the RemoteViews object
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//
        views.setTextViewText(R.id.appwidget_text,ingredientsWidget);
         Toast.makeText(context, ingredientsWidget, Toast.LENGTH_SHORT).show();

   //      Instruct the widget manager to update the widget
         appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.appwidget_text);
           appWidgetManager.updateAppWidget(appWidgetId, views);
     }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        SharedPreferences mySharedPref;

        mySharedPref = context.getSharedPreferences("myPref",context.MODE_PRIVATE);
        for(int i =0;i<DetailFragment.sizeOfList;i++)
        {
            ingredientsWidget = ingredientsWidget+" "+mySharedPref.getString(i+ "step", "");
            Log.d("Widget Info", mySharedPref.getString(i+"step", ""));
        }
        updateAppWidget(context,appWidgetManager,appWidgetIds[0]);


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        super.onReceive(context, intent);
    }
}

