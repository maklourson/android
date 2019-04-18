package fr.eni.ecole.demojson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.demojson.bo.Vehicule;

public class MainActivity extends AppCompatActivity {

    private final static String TAG_LOG ="TAG_JSON";

    private String tableauObjets =
            "[{\"Designation\":\"ROADSTER\",\"ModeleCommercial\":\"ROADSTER\",\"CNIT\":\"M10TSLVP000C002\""
            +",\"Id\":41124,\"ModeleDossier\":\"ROADSTER\"},{\"Designation\":\"ROADSTER\",\"ModeleCommercial\":\"ROADSTER\",\"CNIT\":\"M10TSLVP000C003\""
            +",\"Id\":41125,\"ModeleDossier\":\"ROADSTER\"}]"
            ;

    private String json =
            "{\"Pays\": ["
            + "\"France\", \"Afrique du sud\", \"Burkina Faso\", \"Irlande\", \"Palestine\", \"Portugal\", \"Suisse\""
            + "]}"
            ;

    private String forGson = "{\"Designation\":\"ROADSTER\",\"ModeleCommercial\":\"ROADSTER\",\"CNIT\":\"M10TSLVP000C002\",\"Id\":41124,\"ModeleDossier\":\"ROADSTER\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * tableau json
     * @param view
     */
    public void clickJsonArray(View view) {
        try {
            JSONObject object = new JSONObject(json);

            JSONArray array = object.getJSONArray("Pays");

            for(int i = 0; i < array.length(); i++){
                Log.i(TAG_LOG, "Valeur " + i + "-" + array.get(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * tableau d'objets json
     * @param view
     */
    public void clickJsonObject(View view) {
        try {
            JSONArray vehicules = new JSONArray(tableauObjets);

            for(int i = 0; i < vehicules.length(); i++){
                JSONObject v = vehicules.getJSONObject(i);

                Log.i(TAG_LOG, "Désignation "+ v.getString("Designation"));
                Log.i(TAG_LOG, "Id "+ v.getInt("Id"));
                Log.i(TAG_LOG, "\n ");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utilisation de la librairie GSON
     * @param view
     */
    public void clickGson(View view) {
        Vehicule v = new GsonBuilder().create().fromJson(forGson, Vehicule.class);
        Log.i(TAG_LOG, "Véhicule : " + v);
    }

    /**
     * Accès à un service distant
     * @param view
     */
    public void clickServiceDistant(View view) {
        DistantTask task = new DistantTask();
        task.execute();
    }

    private void chargeResult(String s){
        TextView txt = findViewById(R.id.resultServiceDistant);
        txt.setText(s);
    }


    private class DistantTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            String urlString = "https://geo.api.gouv.fr/communes?codePostal=44000";
            HttpURLConnection connection = null;
            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(urlString);

                connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() == 200){
                    InputStream in = connection.getInputStream();
                    InputStreamReader rd = new InputStreamReader(in);

                    int unChar;
                    while( (unChar = rd.read()) != -1){
                        sb.append((char) unChar);
                    }
                }

                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            chargeResult(s);
        }
    }
}
