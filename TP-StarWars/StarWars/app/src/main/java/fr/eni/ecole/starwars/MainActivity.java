package fr.eni.ecole.starwars;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.eni.ecole.starwars.adapters.PlanetAdapter;
import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.dao.ConnexionDao;
import fr.eni.ecole.starwars.dao.PlanetDAO;
import fr.eni.ecole.starwars.dao.SWContract;
import fr.eni.ecole.starwars.services.ListPlanetService;


public class MainActivity extends AppCompatActivity {

    private Intent service;
    private ListReceiver receiver;
    private static List<Planet> planets;
    private static List<Planet> savePlanets;
    private static PlanetAdapter adapter;
    private TextView chargement;
    private RecyclerView recycler;
    private Toolbar mToolbar;
    private LinearLayout liste;
    private EditText searchEdit;

    private View main;
    private MenuItem update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.ic_empire);
        }

        chargement = findViewById(R.id.txtChargement);
        liste = findViewById(R.id.layout_planets);

        searchEdit = findViewById(R.id.edtSearch);

        main = findViewById(R.id.layout_main);

        receiver = new ListReceiver();
        IntentFilter intentFilter = new IntentFilter(SWContract.BROADCAST_LIST);
        registerReceiver(receiver, intentFilter);

        recycler = findViewById(R.id.listPlanets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recycler.setLayoutManager(layoutManager);

        MainActivity.planets = new ArrayList<>();

        MainActivity.adapter = new PlanetAdapter(MainActivity.planets, new PlanetAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, Planet planet) {
                Intent intent = new Intent(MainActivity.this, PlanetDetailActivity.class);
                intent.putExtra("planet", planet);
                startActivity(intent);
            }
        });

        recycler.setAdapter(MainActivity.adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchEdit.setText("");
        ListTask listTask = new ListTask(new MyHandler());
        listTask.execute();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(service != null){
            stopService(service);
            service = null;
            ActionMenuItemView menu = findViewById(R.id.updateList);
            menu.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    /**
     * Filtre des planètes
     * @param view
     */
    public void onSearchPlanet(View view) {
        //enlève le focus
        searchEdit.clearFocus();
        //ferme le clavier si présent
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm != null ) {
            imm.hideSoftInputFromWindow(main.getApplicationWindowToken(), 0);
        }

        String s = searchEdit.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            if(!s.trim().isEmpty()) {

                final Pattern pattern = Pattern.compile("^" + s.trim().toLowerCase() + ".*");
                //File > Project Structure -> 1.8
                List<Planet> matchsPlanets = savePlanets.stream()
                        .filter(p -> pattern.matcher(p.getName().toLowerCase()).find())
                        .collect(Collectors.toList());

                Log.i("TAG_SW", "list : " + matchsPlanets);
                chargeListe(matchsPlanets);
            }
            else{
                chargeListe(savePlanets);
            }
        }
        else
        {
            //Recharge la liste si API < 24
            if(!s.trim().isEmpty()) {
                ListTask listTask = new ListTask(new MyHandler());
                listTask.execute(s.trim());
            }
            else{
                ListTask listTask = new ListTask(new MyHandler());
                listTask.execute();
            }
        }

    }

    //BroadCast Receiver
    private class ListReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ListTask listTask = new ListTask(new MyHandler());
            listTask.execute();

            update.setEnabled(true);

            chargement.setVisibility(TextView.GONE);
            liste.setVisibility(LinearLayout.VISIBLE);

            Toast.makeText(MainActivity.this, "Liste mise à jour", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.updateList :

                if(update == null){
                    update = item;
                }

                Log.i("TAG_SW", "Menu update liste");

                Log.i("TAG_SW", "launch service");
                if(service != null) {
                    stopService(service);
                }
                service = new Intent(MainActivity.this, ListPlanetService.class);
                startService(service);
                update.setEnabled(false);
                chargement.setVisibility(TextView.VISIBLE);
                liste.setVisibility(LinearLayout.GONE);


                return true;
            case R.id.menuSources :
                Intent i = new Intent(MainActivity.this, SourcesActivity.class);
                startActivity(i);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private static void chargeListe(List<Planet> planets){
        MainActivity.planets.clear();
        MainActivity.planets.addAll(planets);
        MainActivity.adapter.notifyDataSetChanged();
    }


    private static class ListTask extends AsyncTask<String,Void,List<Planet>>{

        private Handler handler;

        ListTask(Handler handler){
            this.handler = handler;
        }

        @Override
        protected List<Planet> doInBackground(String... searchs) {

            String search = null;
            if(searchs.length > 0 && !searchs[0].isEmpty()){
                search = searchs[0];
            }

            PlanetDAO dao = ConnexionDao.getPlanetDao();
            List<Planet> list = new ArrayList<>();
            if(search == null){
                list = dao.getPlanets();
            }
            else{
                list = dao.getPlanets(search);
            }

            Log.i("TAG_SW","Liste planet main " + list.size());
            return list;
        }

        @Override
        protected void onPostExecute(List<Planet> planets) {
            super.onPostExecute(planets);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = planets;
            handler.sendMessage(msg);
        }
    }

    private static class MyHandler extends Handler{

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 1){
                Object o = msg.obj;
                if(o != null) {
                    List<Planet> planets = (List<Planet>) o;

                    //clone la liste pour le filtre
                    MainActivity.savePlanets = new ArrayList<>(Arrays.asList(new Planet[planets.size()]));
                    Collections.copy(savePlanets, planets);

                    chargeListe(planets);
                }
            }

        }
    }


}
