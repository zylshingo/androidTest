package com.jikexueyuan.notes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class MyProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://com.jikexueyuan.notes");
    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        database = getContext().openOrCreateDatabase("mycp", Context.MODE_PRIVATE, null);

        database.execSQL("create table if not exists mytab(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "time INTEGER(2),event TEXT NOT NULL)");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = database.query("mytab", null, null, null, null, null, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database.insert("mytab", "_id", values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        database.delete("mytab",selection,selectionArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        database.update("mytab",values,selection,selectionArgs);
        return 0;
    }
}
