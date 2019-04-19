package fr.eni.ecole.demolivedata;

import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.demolivedata.adapters.PlanetAdapter;
import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.repository.PlanetRepository;
import fr.eni.ecole.demolivedata.services.PlanetService;

public class MainActivity extends AppCompatActivity implements PlanetAdapter.CustomItemLongClickListener{

    private PlanetAdapter adapter;
    private List<Planet> datas;
    private PlanetListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recycler = findViewById(R.id.listPlanets);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        datas = new ArrayList<>();
        adapter = new PlanetAdapter(datas, new PlanetAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, Planet planet) {
                //Rien pour le moment
            }
        },
           this
        );

        recycler.setAdapter(adapter);

        /*
         * Création du viewModel grâce à la factory
         */
        viewModel = ViewModelProviders.of(MainActivity.this, new PlanetFactory(MainActivity.this.getApplication())).get(PlanetListViewModel.class);
        /*
         * Abonnement au LiveData de la liste qui permet de mettre à jour le RecyclerView
         */
        viewModel.getPlanets().observe(MainActivity.this, new Observer<List<Planet>>() {
            @Override
            public void onChanged(@Nullable List<Planet> planets) {
            if(planets != null) {
                datas.clear();
                datas.addAll(planets);
                adapter.notifyDataSetChanged();
            }
            }
        });

    }

    //Lier à l'implementation d'une interface de l'adapter
    @Override
    public boolean onItemLongClick(View v, final Planet planet, int Position) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Voulez-vous supprimer cette planète ?")
                .setTitle("Suppression")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete(planet);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                }).show();


        return true;
    }

    /**
     * ViewModel ou AndroidViewModel
     * Couche supplémentaire qui gère le repository/manager
     */
    public final class PlanetListViewModel extends AndroidViewModel {

        private final LiveData<List<Planet>> planetsList;

        private PlanetRepository repository;

        public PlanetListViewModel(Application application) {
            super(application);
            repository = new PlanetRepository(application);

            planetsList = repository.findAll();
        }


        public LiveData<List<Planet>> getPlanets() {
            return planetsList;
        }

        public void delete(Planet planet){
            repository.delete(planet);
        }


    }

     /**
      * Factory pour construire le ViewModel
      *
     */
    class PlanetFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;


        public PlanetFactory(@NonNull Application application) {
            this.application = application;
        }

        @NonNull
        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == PlanetListViewModel.class) {
                return (T) new PlanetListViewModel(application);
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.updateList){
            Intent intent = new Intent(MainActivity.this, PlanetService.class);
            startService(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
