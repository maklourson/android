package fr.eni.ecole.demolivedata.services;

import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.bo.Planets;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SWAPIService {

    public static final String ENDPOINT = "https://swapi.co/api/";

    @GET("planets/{id}/")
    Call<Planet> getPlanet(@Path("id") int id);

    @GET("planets/")
    Call<Planets> getPlanets(@Query("page") int page);
}
