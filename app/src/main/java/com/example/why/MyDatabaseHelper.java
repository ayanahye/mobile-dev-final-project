package com.example.why;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    //private static final String COLUMN_AUTHOR = "book_author";
    public static final String RATING_COLUMN = "rating"; // new column


    public MyDatabaseHelper (@Nullable Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + RATING_COLUMN + " REAL DEFAULT 0.0); ";

        db.execSQL(query);

    }


    @Override
    public void onUpgrade (SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addBook(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(RATING_COLUMN, 0.0);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }

    }

    public boolean updateRating(String title, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RATING_COLUMN, rating);
        int result = db.update(TABLE_NAME, contentValues, COLUMN_TITLE + "=?", new String[]{title});
        return result != -1;
    }



    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "failed to delete", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Book deleted", Toast.LENGTH_LONG).show();
        }
    }


}
