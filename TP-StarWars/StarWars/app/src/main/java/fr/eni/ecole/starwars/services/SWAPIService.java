package fr.eni.ecole.starwars.services;

import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.bo.Planets;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SWAPIService {

    public static final String ENDPOINT = "https://swapi.co/api/";

    @GET("planets/{id}/")
    Call<Planet> getPlanet(@Path("id") int id);

    @GET("people/{id}/")
    Call<People> getPeople(@Path("id") int id);

    @GET("planets/")
    Call<Planets> getPlanets(@Query("page") int page);
}
