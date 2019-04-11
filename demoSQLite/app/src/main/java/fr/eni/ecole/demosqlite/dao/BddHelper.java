package fr.eni.ecole.demosqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.eni.ecole.demosqlite.contracts.PersonneContract;

public class BddHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String BDD_NAME = "demo.db";

    public BddHelper(Context context) {
        super(context, BDD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonneContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Il faut gérer les updates de chaque différence de version
        db.execSQL(PersonneContract.DROP_TABLE);
        onCreate(db);
    }
}
