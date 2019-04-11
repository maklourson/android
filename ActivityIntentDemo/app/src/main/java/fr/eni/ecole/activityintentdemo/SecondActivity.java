package fr.eni.ecole.activityintentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import fr.eni.ecole.activityintentdemo.bo.Client;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();

        String nom = intent.getStringExtra("nom");
        Client c = intent.getParcelableExtra("client");

        Toast.makeText(SecondActivity.this, c.getNom()+" " + c.getPrenom(), Toast.LENGTH_LONG).show();
    }
}
