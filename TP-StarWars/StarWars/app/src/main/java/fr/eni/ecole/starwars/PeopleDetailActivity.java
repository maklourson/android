package fr.eni.ecole.starwars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import fr.eni.ecole.starwars.bo.People;

public class PeopleDetailActivity extends AppCompatActivity {

    private People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_empire);
        }


        Intent intent = getIntent();
        people = intent.getParcelableExtra("people");

        bindPeople();

    }

    private void bindPeople(){
        TextView name = findViewById(R.id.txtPeopleName);
        TextView birth = findViewById(R.id.txtPeopleBirth);
        TextView eye = findViewById(R.id.txtPeopleEye);
        TextView gender = findViewById(R.id.txtPeopleGender);
        TextView hair = findViewById(R.id.txtPeopleHair);
        TextView height = findViewById(R.id.txtPeopleHeight);
        TextView mass = findViewById(R.id.txtPeopleMass);
        TextView skin = findViewById(R.id.txtPeopleSkin);


        name.setText(people.getName());
        birth.setText(people.getBirth_year());
        eye.setText(people.getEye_color());
        gender.setText(people.getGender());
        hair.setText(people.getHair_color());
        height.setText(getString(R.string.people_height, people.getHeight()));
        mass.setText(getString(R.string.people_mass, people.getMass()));
        skin.setText(people.getSkin_color());



    }

}
