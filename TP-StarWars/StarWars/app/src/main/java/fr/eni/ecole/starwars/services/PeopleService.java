package fr.eni.ecole.starwars.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.manager.PeopleManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * helper methods.
 */
public class PeopleService extends IntentService {


    public PeopleService() {
        super("PeopleService");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            List<Integer> listeIds = intent.getIntegerArrayListExtra("ids");
            SWAPIService webService = ConnexionWeb.getWebService();

            for(int id : listeIds){

                Log.i("TAG_SW", "people : " + id);

                People p = PeopleManager.getPeople(id);
                People people = null;
                int boucle = 5;
                do {
                    try {
                        people = webService.getPeople(id).execute().body();
                    } catch (IOException e) {
                        Log.i("TAG_SW", "IOException " + e.getMessage());
                    }

                    boucle--;

                }while(people == null && boucle > 0);

                if(people != null){
                    int idPeople = LinkService.getIdPeople(people.getUrl());
                    people.setId(idPeople);

                    if(p == null){
                        //insert
                        PeopleManager.insert(people);
                    }
                    else{
                        //update
                        PeopleManager.update(people);
                    }

                }

            }//end for

            Intent i = new Intent(PeopleManager.BROADCAST_PEOPLE);
            sendBroadcast(i);

        }
    }

}
