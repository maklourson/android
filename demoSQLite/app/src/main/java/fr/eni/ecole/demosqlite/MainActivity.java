package fr.eni.ecole.demosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fr.eni.ecole.demosqlite.bo.Personne;
import fr.eni.ecole.demosqlite.manager.PersonneManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickInsert(View view) {

        Personne p = new Personne(0, "Bouvet", "Laurent");

        PersonneManager.insert(this, p);

    }

    public void onClickRead(View view) {

        Personne p = PersonneManager.getPersonneById(this, 1);

        Toast.makeText(this, "Personne" + p, Toast.LENGTH_LONG).show();

    }
}
