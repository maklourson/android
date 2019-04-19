package fr.eni.ecole.starwars;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.starwars.adapters.PeopleAdapter;
import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.manager.PeopleManager;
import fr.eni.ecole.starwars.services.PeopleService;

public class PlanetDetailActivity extends AppCompatActivity {

    private Planet planet;
    private Toolbar mToolbar;
    private RecyclerView recycler;
    private static List<People> peoples;
    private static PeopleAdapter adapter;
    private PeopleReceiver receiver;
    private static AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_empire);
        }


        recycler = findViewById(R.id.listePeoples);
        LinearLayoutManager manager = new LinearLayoutManager(PlanetDetailActivity.this);
        recycler.setLayoutManager(manager);

        PlanetDetailActivity.peoples = new ArrayList<People>();
        PlanetDetailActivity.adapter = new PeopleAdapter(PlanetDetailActivity.peoples, new PeopleAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, People people) {
                Intent intent = new Intent(PlanetDetailActivity.this, PeopleDetailActivity.class);
                intent.putExtra("people", people);
                startActivity(intent);
            }
        });
        recycler.setAdapter(PlanetDetailActivity.adapter);

        planet = getIntent().getParcelableExtra("planet");

        bindPlanet();

        //AlertDialog Chargement
        alert = new AlertDialog.Builder(this)
                .setView(R.layout.chargement_layout)
                .setCancelable(false)
                .create();
        //cache le bord pour faire apparaitre le border radius de la CardView
        if( alert.getWindow() != null) {
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new PeopleReceiver();
        IntentFilter ri = new IntentFilter(PeopleManager.BROADCAST_PEOPLE);
        registerReceiver(receiver, ri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void bindPlanet(){

        TextView name = findViewById(R.id.txtPlanetName);
        TextView climate = findViewById(R.id.txtPlanetClimate);
        TextView diameter = findViewById(R.id.txtPlanetDiameter);
        TextView gravity = findViewById(R.id.txtPlanetGravity);
        TextView orbital = findViewById(R.id.txtPlanetOrbitalPeriod);
        TextView population = findViewById(R.id.txtPlanetPopulation);
        TextView rotation = findViewById(R.id.txtPlanetRotationPeriod);
        TextView water = findViewById(R.id.txtPlanetSurfaceWater);
        TextView terrain = findViewById(R.id.txtPlanetTerrains);

        name.setText(planet.getName());
        climate.setText(planet.getClimate());
        diameter.setText(getString(R.string.planet_diameter, planet.getDiameter()));
        gravity.setText(getString(R.string.planet_gravity, planet.getGravity()));
        orbital.setText(getString(R.string.planet_orbital_periode, planet.getOrbital_period()));
        rotation.setText(getString(R.string.planet_rotation_periode, planet.getRotation_period()));
        population.setText(planet.getPopulation());
        water.setText(getString(R.string.planet_water_surface, planet.getSurface_water()));
        terrain.setText(planet.getTerrain());

        ListPeople task = new ListPeople(new PeopleHandler(), planet.getIdsPeoples());
        task.execute();

    }


    @Override
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail_planet,menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.updatePeopleList){
            alert.show();
            Intent intent = new Intent(PlanetDetailActivity.this, PeopleService.class);
            intent.putIntegerArrayListExtra("ids", planet.getIdsPeoples());
            startService(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private static class ListPeople extends AsyncTask<Void,Void, List<People>>{

        private Handler handler;
        private ArrayList<Integer> datas;

        public ListPeople(Handler handler, ArrayList<Integer> datas) {
            this.handler = handler;
            this.datas = datas;
        }

        @Override
        protected List<People> doInBackground(Void... voids) {

            List<People> peoples = new ArrayList<>();

            peoples = PeopleManager.getPeoplesByIds(datas);
            Log.i("TAG_SW", "Liste peoples : " + peoples);
            return peoples;
        }


        @Override
        protected void onPostExecute(List<People> peoples) {
            super.onPostExecute(peoples);

            if(this.handler != null){
                Message msg = new Message();
                msg.what = 1;
                msg.obj = peoples;

                this.handler.sendMessage(msg);

            }

        }
    }

    private static class PeopleHandler extends Handler{

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("TAG_SW", "Has msg " + msg);
            if(msg.what == 1){
                Object o = msg.obj;

                if(o != null){
                    List<People> peoples = (List<People>) o;
                    chargePeoples(peoples);
                }
            }

        }
    }


    private static void chargePeoples(List<People> peoples){
        alert.hide();
        PlanetDetailActivity.peoples.clear();
        PlanetDetailActivity.peoples.addAll(peoples);

        Log.i("TAG_SW", "charge list " + peoples);
        PlanetDetailActivity.adapter.notifyDataSetChanged();
    }


    private class PeopleReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("TAG_SW", "Receiver people");
            ListPeople task = new ListPeople(new PeopleHandler(), planet.getIdsPeoples());
            task.execute();
        }
    }

}
