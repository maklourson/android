package fr.eni.ecole.democontentprovidermodule;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fr.eni.ecole.mycontent.MemberContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void displayOneContentProvider(){
        String columns[] = new String[] {
                MemberContract.Contract.ID,
                MemberContract.Contract.INTITULE,
                MemberContract.Contract.SALAIRE };

        Uri mContacts = Uri.parse(MemberContract.Contract.URI + "/2");

        String s = getContentResolver().getType(mContacts);

        Cursor cur = getContentResolver().query(mContacts, columns, null,
                null, null);

        Toast.makeText(MainActivity.this, cur.getCount() + "",
                Toast.LENGTH_LONG).show();

        if (cur.moveToFirst()) {
            String name = null;
            do {
                name = cur.getString(cur.getColumnIndex(MemberContract.Contract.ID)) + " " +
                        cur.getString(cur.getColumnIndex(MemberContract.Contract.INTITULE)) + " " +
                        cur.getString(cur.getColumnIndex(MemberContract.Contract.SALAIRE));
                Toast.makeText(this, name + " ", Toast.LENGTH_LONG).show();
            } while (cur.moveToNext());
        }

        cur.close();
    }

    private void displayContentProvider() {
        String columns[] = new String[] {
                MemberContract.Contract.ID,
                MemberContract.Contract.INTITULE,
                MemberContract.Contract.SALAIRE };
        Uri mContacts = MemberContract.Contract.URI;

        String s = getContentResolver().getType(mContacts);

        Cursor cur = getContentResolver().query(mContacts, columns, null, null, null);
        Toast.makeText(MainActivity.this, cur.getCount() + "",
                Toast.LENGTH_LONG).show();

        StringBuilder sb = new StringBuilder();

        if (cur.moveToFirst()) {
            String name = null;
            do {
                name = cur.getString(cur.getColumnIndex(MemberContract.Contract.ID)) + " " +
                        cur.getString(cur.getColumnIndex(MemberContract.Contract.INTITULE)) + " " +
                        cur.getString(cur.getColumnIndex(MemberContract.Contract.SALAIRE));
                sb.append(name).append("\n");
            } while (cur.moveToNext());

            Toast.makeText(this, sb.toString() + " ", Toast.LENGTH_LONG).show();
        }
        cur.close();
    }



    private void insertRecords() {
        ContentValues contact = new ContentValues();
        contact.put(MemberContract.Contract.INTITULE, "Dev Android");
        contact.put(MemberContract.Contract.SALAIRE, "3500");
        getContentResolver().insert( MemberContract.Contract.URI  , contact);

        contact.clear();
        contact.put(MemberContract.Contract.INTITULE, "Dev Java");
        contact.put(MemberContract.Contract.SALAIRE, "3600");
        getContentResolver().insert(MemberContract.Contract.URI, contact);

        contact.clear();
        contact.put(MemberContract.Contract.INTITULE, "Dev Objectif C");
        contact.put(MemberContract.Contract.SALAIRE, "3800");
        getContentResolver().insert(MemberContract.Contract.URI, contact);
    }

    public void onInsertData(View view) {
        insertRecords();
        Toast.makeText(MainActivity.this, "Les données ont été insérées.",
                Toast.LENGTH_LONG).show();
    }

    public void onReadOneData(View view) {
        displayOneContentProvider();
    }

    public void onReadAllData(View view) {
        displayContentProvider();
    }
}
