package com.example.uplabdhisingh.bakingtime.data;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by uplabdhisingh on 04/03/18.
 */

public class WidgetContract {

    public static final String CONTENT_AUTHORITY = "com.example.uplabdhisingh.bakingtime";
    public static final String SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + CONTENT_AUTHORITY);
    public static final String PATH = "baking_time";


    public static final class WidgetDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String TABLE_NAME = "bakingtime";
        public static final String COLUMN_INGREDIENTS="ingredientsList";
    }
}
