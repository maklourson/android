package fr.eni.ecole.demoretrofit.interfaces;

import java.util.List;

import fr.eni.ecole.demoretrofit.bo.Repo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {
    public static final String URL = "https://api.github.com";

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("/search/repositories")
    Call<List<Repo>> searchRepos(@Query("q") String query);

    /*
    @FormUrlEncoded
    @POST("user/edit")
    Call<List<Repo>> searchReposPost(@Field("q") String query);
    */
}
