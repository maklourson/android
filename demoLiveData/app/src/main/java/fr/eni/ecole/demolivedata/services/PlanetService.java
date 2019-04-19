package fr.eni.ecole.demolivedata.services;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.bo.Planets;
import fr.eni.ecole.demolivedata.repository.PlanetRepository;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PlanetService extends IntentService {

    private Application mApplication;

    public PlanetService() {
        super("PlanetService");

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mApplication = (Application) getApplicationContext();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SWAPIService webService = ConnexionWeb.getWebService();
        List<Planet> lst = new ArrayList<>();
        Planets planets = null;
        int page = 1;
        int tour = 15;
        do {

            try {
                planets = webService.getPlanets(page).execute().body();
            } catch (IOException e) {
                Log.i("TAG_SW", "IOException " + e.getMessage());
            }

            if (planets != null) {
                lst.addAll(planets.getResults());
                int i = 0;
                if(planets.getNext() != null){
                    i = LinkService.getPagePlanets(planets.getNext());
                }

                page = i;
            }

            //pour éviter la boucle infinie
            tour--;

        }while(page > 0 && tour > 0);

        Log.i("TAG_SW", "liste " + lst.size());

        PlanetRepository repo = new PlanetRepository(mApplication);

        //Tester si presence de la planet dans la table et sinon update ou insert
        for(Planet p : lst){
            int id = LinkService.getIdPlanet(p.getUrl());
            p.setId(id);
            Planet planet = repo.getPlanetById(id);

            if(planet == null){
                //insert
                repo.insert(p);
            }
            else if(p.getEdited() != null && p.getEdited().compareTo(planet.getEdited()) == 1){
                //update
                repo.update(p);
            }

        }

    }


}
