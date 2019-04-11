package fr.eni.ecole.activityintentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Intent de retour
        Intent retour = new Intent();
        retour.putExtra("retour", "ThirdActivity");

        //Indique un résultat OK
        setResult(RESULT_OK, retour);
    }
}
