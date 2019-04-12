package fr.eni.ecole.mycontent.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import fr.eni.ecole.mycontent.helper.MemberDatabaseHelper;


public class MonProvider extends ContentProvider {

    private MemberDatabaseHelper dbHelper;

    public MonProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if (id < 0)
                return db.delete(
                        MemberDatabaseHelper.Metier.TABLE_NAME,
                        selection, selectionArgs);
            else
                return db.delete(
                        MemberDatabaseHelper.Metier.TABLE_NAME,
                        MemberDatabaseHelper.Metier._ID + "=" + id, selectionArgs);
        } finally {
            db.close();
        }
    }

    @Override
    public String getType(Uri uri) {

        String type = dbHelper.getType(uri);

        Log.d("URI_TYPE", type);

        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            long id = db.insertOrThrow(
                    MemberDatabaseHelper.Metier.TABLE_NAME, null,
                    values);

            if (id == -1) {
                throw new RuntimeException(String.format(
                        "%s : Failed to insert [%s] for unknown reasons.",
                        "TutosAndroidProvider", values, uri));
            } else {
                return ContentUris.withAppendedId(uri, id);
            }

        } finally {
            db.close();
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MemberDatabaseHelper(getContext());
        return((dbHelper == null) ? false : true);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (id < 0) {
            return db
                    .query(MemberDatabaseHelper.Metier.TABLE_NAME,
                            projection, selection, selectionArgs, null, null,
                            sortOrder);
        } else {
            return db.query(MemberDatabaseHelper.Metier.TABLE_NAME,
                    projection, MemberDatabaseHelper.Metier._ID + "=" + id, null, null, null,
                    null);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            if (id < 0)
                return db.update(
                        MemberDatabaseHelper.Metier.TABLE_NAME,
                        values, selection, selectionArgs);
            else
                return db.update(
                        MemberDatabaseHelper.Metier.TABLE_NAME,
                        values, MemberDatabaseHelper.Metier._ID + "=" + id, null);
        } finally {
            db.close();
        }
    }

    private long getId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            try {
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {
                Log.e("MonProvider", "Number Format Exception : " + e);
            }
        }
        return -1;
    }
}
