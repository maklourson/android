package fr.eni.ecole.demoroom;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fr.eni.ecole.demoroom.bo.AppDatabase;
import fr.eni.ecole.demoroom.bo.Utilisateur;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(MainActivity.this,
                        AppDatabase.class, "database_room").build();

                Utilisateur u1 = new Utilisateur();
                u1.setNom("Bouvet");
                u1.setPrenom("Laurent");

                Utilisateur u2 = new Utilisateur();
                u2.setNom("Martin");
                u2.setPrenom("Bruno");

                db.utilisateurDao().insertAll(u1,u2);



            }
        }).start();
    }

    public void onClickUtilisateur(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(MainActivity.this,
                        AppDatabase.class, "database_room").build();

                Utilisateur u = db.utilisateurDao().getUtilisateurById(1);

                Log.i("TAG_USER", "ROOM " + u);
            }
        }).start();

    }

    private void alert(Utilisateur u){
        Toast.makeText(MainActivity.this,
                "User : " + u,
                Toast.LENGTH_LONG).show();
    }
}
