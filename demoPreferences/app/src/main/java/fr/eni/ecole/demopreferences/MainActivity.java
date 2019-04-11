package fr.eni.ecole.demopreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickPreferences(View view) {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("demo", "Preférences d'activity");
        editor.putInt("age", 44);
        editor.apply();

    }

    public void clickSharedPreferences(View view) {
        SharedPreferences sp = getSharedPreferences("Fichier.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("demo", "Ceci est une préférence globale");

        //Mode synchrone qui renvoir vrai ou faux
        //editor.commit();

        //Mode asynchrone
        editor.apply();
    }

    public void clickLire(View view) {

        SharedPreferences sp = getSharedPreferences("Fichier.txt", MODE_PRIVATE);

        Toast.makeText(MainActivity.this,
                "Données : " + sp.getString("demo", "default"),
                Toast.LENGTH_LONG).show();

    }
}
