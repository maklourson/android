package fr.eni.ecole.demoactionbar;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBar);

        //Récupérer action bar
        //ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setTitle(R.string.app_name_autre);

            actionBar.setNavigationIcon(R.drawable.fleche_32);
            actionBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //fini l'activité et appelle le parent déclarer dans le manifest
                    // - ne sert pas pour l'activité de démarrage -
                    //NavUtils.navigateUpFromSameTask(MainActivity.this);

                    //appel l'action du bouton retour
                    onBackPressed();

                    //Ferme l'activité
                    //finish();
                }
            });

            //Active la flèche de retour actionBar de base
            //actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                //Toast.makeText(MainActivity.this, "Flèche de retour", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
