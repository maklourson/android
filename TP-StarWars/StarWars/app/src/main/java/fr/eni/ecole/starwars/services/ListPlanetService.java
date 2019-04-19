package fr.eni.ecole.starwars.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.starwars.Application.SWApplication;
import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.bo.Planets;
import fr.eni.ecole.starwars.dao.ConnexionDao;
import fr.eni.ecole.starwars.dao.PlanetDAO;
import fr.eni.ecole.starwars.dao.SWContract;
import fr.eni.ecole.starwars.manager.PlanetManager;

public class ListPlanetService extends Service {
    public ListPlanetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ListTask lst = new ListTask();
        lst.execute();


        return super.onStartCommand(intent, flags, startId);
    }

    private static class ListTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.i("TAG_SW", "liste service start" );
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

            //Tester si presence de la planet dans la table et sinon update ou insert
            for(Planet p : lst){
                int id = LinkService.getIdPlanet(p.getUrl());
                p.setId(id);
                Planet planet = PlanetManager.getPlanetById(id);

                if(planet == null){
                    //insert
                    PlanetManager.insert(p);
                }
                else if(p.getEdited() != null && p.getEdited().compareTo(planet.getEdited()) == 1){
                    //update
                    PlanetManager.update(p);
                }

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Intent intent = new Intent(SWContract.BROADCAST_LIST);
            SWApplication.getAppContext().sendBroadcast(intent);
            //sendBroadcast
        }
    }
}
