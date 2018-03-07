package com.example.uplabdhisingh.bakingtime.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by uplabdhisingh on 04/03/18.
 */

public class WidgetContentProvider extends ContentProvider
{
    public static final int BAKINGTIME = 100; //this is the whole table or directory
    public static final int BAKING_TIME_ID = 101; //this is the specific row of data.

    private static final String TAG = WidgetContentProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private WidgetDbHelper widgetDbHelper;
    public static UriMatcher buildUriMatcher()
    {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //firstly I'll be adding uri for my whole table with its id :
        uriMatcher.addURI(WidgetContract.CONTENT_AUTHORITY,WidgetContract.PATH,BAKINGTIME);
        //and now I'll be adding uri for my specific row data :
        uriMatcher.addURI(WidgetContract.CONTENT_AUTHORITY, WidgetContract.PATH + "/#", BAKING_TIME_ID );

        return  uriMatcher;
    }
    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        widgetDbHelper = new WidgetDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        final SQLiteDatabase db = widgetDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match)
        {
            case BAKINGTIME:
                retCursor=db.query(WidgetContract.WidgetDataEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case BAKING_TIME_ID:
            {
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor=db.query(WidgetContract.WidgetDataEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Uri not found : " +uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case BAKINGTIME:
                // directory
                return "vnd.android.cursor.dir" + "/" + WidgetContract.CONTENT_AUTHORITY + "/" + WidgetContract.PATH;
            case BAKING_TIME_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + WidgetContract.CONTENT_AUTHORITY + "/" + WidgetContract.PATH;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = widgetDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch(match)
        {
            case BAKINGTIME:
                long id = db.insert(WidgetContract.WidgetDataEntry.TABLE_NAME,null,values);
                if(id > 0)
                {
                    returnUri= ContentUris.withAppendedId(WidgetContract.WidgetDataEntry.CONTENT_URI,id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert data: " +uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("No uri found: " +uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = widgetDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        int recipeDeleted;
        switch (match)
        {
            case BAKING_TIME_ID:
                String id = uri.getPathSegments().get(1);
                recipeDeleted = db.delete(WidgetContract.WidgetDataEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Not yet Implemented");
        }
        if(recipeDeleted!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return recipeDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //Keep track of if an update occurs

        final SQLiteDatabase db = widgetDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int ingUpdated;

        switch (match)
        {
            case BAKINGTIME:
                ingUpdated = db.update(WidgetContract.WidgetDataEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case BAKING_TIME_ID:
                //update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                //using selections
                ingUpdated = widgetDbHelper.getWritableDatabase().update(WidgetContract.WidgetDataEntry.TABLE_NAME,
                        values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (ingUpdated != 0)
        {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return ingUpdated;
    }
}
