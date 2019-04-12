package fr.eni.ecole.democontentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.democontentprovider.bo.Personne;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_PERM_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermissions();
    }

    private void getPermissions(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED){

            //Demande juste la permission si on ne l'a pas
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.READ_CONTACTS},REQUEST_PERM_READ_CONTACTS );

        }
        else{
            binSpinner();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERM_READ_CONTACTS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                binSpinner();
            }
            else{
                finish();
            }
        }
    }

    private void binSpinner(){
        Spinner spinner = findViewById(R.id.spinner_contact);
        //Permet d'accéder à un content Provider
        ContentResolver cr = getContentResolver();

        List<Personne> lst = new ArrayList<>();

        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        while(c.moveToNext()){
           String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
           String nom = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

           lst.add(new Personne(id, nom));

        }

        c.close();

        ArrayAdapter ad = new ArrayAdapter<Personne>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                lst
        );

        spinner.setAdapter(ad);

    }
}
