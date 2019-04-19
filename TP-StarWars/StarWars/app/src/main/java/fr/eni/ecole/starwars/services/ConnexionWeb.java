package fr.eni.ecole.starwars.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ConnexionWeb {

    public static SWAPIService getWebService(){
        SWAPIService swService = new Retrofit.Builder()
                .baseUrl(SWAPIService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SWAPIService.class);

        return swService;
    }

}
