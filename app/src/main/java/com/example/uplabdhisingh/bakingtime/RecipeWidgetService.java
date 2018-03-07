package com.example.uplabdhisingh.bakingtime;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.uplabdhisingh.bakingtime.Details.Ingredient;
import com.example.uplabdhisingh.bakingtime.data.WidgetContract;

import java.util.ArrayList;

import static com.example.uplabdhisingh.bakingtime.data.WidgetContract.BASE_CONTENT_URI;
import static com.example.uplabdhisingh.bakingtime.data.WidgetContract.PATH;

/**
 * Created by uplabdhisingh on 06/03/18.
 */

public class RecipeWidgetService extends IntentService
{

  //  public static final String ACTION_BAKING_SERVICE = "com.example.uplabdhisingh.bakingtime.action.baking_service";

    public static String FROM_ACTIVITY_INGREDIENTS_LIST ="FROM_ACTIVITY_INGREDIENTS_LIST";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RecipeWidgetService(String name) {
        super(name);
    }

    public static void startActionBakingService(Context context,ArrayList<String> fromActivityIngredientList) {
        Intent intent = new Intent(context,RecipeWidgetService.class);
       intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientList);
       context.startService(intent);

        // intent.setAction(ACTION_BAKING_SERVICE);
       // context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null)
        {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionBakingService(fromActivityIngredientsList);
        }

    }

    private void handleActionBakingService(ArrayList<String> fromActivityIngredientsList)
    {
       // Uri BAKING_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
       // ContentValues contentValues = new ContentValues();
       // contentValues.put(WidgetContract.WidgetDataEntry.COLUMN_INGREDIENTS,);
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}
