package fr.eni.ecole.demosqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.eni.ecole.demosqlite.bo.Personne;
import fr.eni.ecole.demosqlite.contracts.PersonneContract;

public class PersonneDao {

    private BddHelper helper;

    public PersonneDao(Context context) {
        this.helper = new BddHelper(context);
    }

    public long insert(Personne p){

        SQLiteDatabase db = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonneContract.COL_NOM, p.getNom());
        values.put(PersonneContract.COL_PRENOM, p.getPrenom());

        long id = db.insert(PersonneContract.TABLE_NAME,
                null,values);

        if(id > -1){
            p.setId( (int) id);
        }

        db.close();

        return id;

    }

    public Personne getPersonneById (int id){
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Personne p = null;
        Cursor c = db.query(PersonneContract.TABLE_NAME,
                null,
                PersonneContract.COL_ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if(c.moveToFirst()){
            p = new Personne(
                    c.getInt(c.getColumnIndex(PersonneContract.COL_ID)),
                    c.getString(c.getColumnIndex(PersonneContract.COL_NOM)),
                    c.getString(c.getColumnIndex(PersonneContract.COL_PRENOM))
            );
        }

        return p;
    }
}
