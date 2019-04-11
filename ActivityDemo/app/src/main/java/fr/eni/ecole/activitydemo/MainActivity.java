package fr.eni.ecole.activitydemo;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "TAG_DEMO";
    private EditText txtNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");

        txtNom = findViewById(R.id.editTextNom);
        Button valider = (Button) findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClickListener");
                //Autre façon de récupérer le context
                //v.getContext();
                //Text court pour des petits messages
                Toast.makeText(MainActivity.this,"Toast test " + txtNom.getText().toString(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Faite un choix !");
                //builder.setView();
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Click On Button");
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Click Non Button");
                    }
                });
                //Lancer l'alerte dialog
                builder.create().show();

            }
        });

        txtNom.setText("Bonjour");

        //Récupérer les données du saveInstance
        if(savedInstanceState != null){
            Log.i(TAG, "saveInstance " + savedInstanceState.getString("save_donnees"));
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Récupérer les données du saveInstance
        if(savedInstanceState != null){
            Log.i(TAG, "onRestoreInstance " + savedInstanceState.getString("save_donnees"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    //Sauvegarde d'information
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("save_donnees", "Sauvegarde");
    }

    //Lier au onClick dans le layout
    public void onClickValider(View view) {
        Log.i(TAG,"Click button");
    }
}
