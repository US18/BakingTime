package com.example.uplabdhisingh.bakingtime.widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.uplabdhisingh.bakingtime.R;

import java.util.List;

import static com.example.uplabdhisingh.bakingtime.widgets.BakingAppWidget.REMOTEVIEW_BUNDLE;
import static com.example.uplabdhisingh.bakingtime.widgets.BakingAppWidget.REMOTEVIEW_INGREDIENT_LIST;
import static com.example.uplabdhisingh.bakingtime.widgets.BakingAppWidget.ingredientsList;

/**
 * Created by uplabdhisingh on 03/03/18.
 */

public class GridWidgetService extends RemoteViewsService
{
    List<String> remoteViewingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
    {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteViewingredientsList=ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget_item);

            views.setTextViewText(R.id.baking_app_widget_item, remoteViewingredientsList.get(position));

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.baking_app_widget_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
