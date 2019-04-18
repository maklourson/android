package fr.eni.ecole.demoretrofit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fr.eni.ecole.demoretrofit.bo.Repo;
import fr.eni.ecole.demoretrofit.interfaces.GithubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MyHandle handle = new MyHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void afficheRepo(List<Repo> repos){
        Toast.makeText(this,
                "Repos : " + repos.size(),
                Toast.LENGTH_LONG).show();
    }

    public void clickSynchrone(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GithubService githubService = new Retrofit.Builder()
                            .baseUrl(GithubService.URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(GithubService.class);

                    List<Repo> repos = githubService.listRepos("picasso").execute().body();

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = repos;
                    handle.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void clickAsynchrone(View view) {
        GithubService githubService = new Retrofit.Builder()
                .baseUrl(GithubService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);

        githubService.listRepos("picasso").enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    List<Repo> repos = response.body();

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = repos;
                    handle.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

    }

    class MyHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                List<Repo> repos = (List<Repo>) msg.obj;
                afficheRepo(repos);
            }
        }
    }
}
