package fr.eni.ecole.activityintentdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fr.eni.ecole.activityintentdemo.bo.Client;

public class FirstActivity extends AppCompatActivity {

    final static int REQUEST_ACTIVITY = 120;
    final static int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    final static String TAG="TAG_DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Methode permet de tester si on a les droits
        if (ContextCompat.checkSelfPermission(FirstActivity.this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            //La demande a déjà eu lieu
            if (ActivityCompat.shouldShowRequestPermissionRationale(FirstActivity.this,
                    Manifest.permission.WRITE_CONTACTS)) {
                //Il faut lui redemander s'il veut l'accepter
                Log.i(TAG, "Redemander permission");

            } else {
                //Juste la demande de permission
                ActivityCompat.requestPermissions(FirstActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);

            }
        } else {
            // Continuer notre application
            Log.i(TAG, "Permission donnée");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //executer notre application
                    Log.i(TAG, "Permission accepter");
                }
                else{
                    //Message à l'utilisateur
                    finish();
                }
                break;
        }

    }

    public void onClickActivity(View view) {
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        intent.putExtra("nom", "Laurent");

        Client c = new Client("Dupont", "Alain");

        intent.putExtra("client", c);

        startActivity(intent);
    }

    public void onClickActivityResult(View view) {
        Intent i = new Intent(FirstActivity.this, ThirdActivity.class);

        startActivityForResult(i, REQUEST_ACTIVITY);

    }

    //Récupère le retour des activités
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ACTIVITY){
            if(resultCode == RESULT_OK){
                String r = data.getStringExtra("retour");
                Toast.makeText(FirstActivity.this,"Retour de l'activité :" + r, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclickSMS(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:0612000010"));
        //On test s'il y a une activité valide dans le téléphone
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void onClickPDF(View view) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("application/pdf");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
