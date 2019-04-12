package fr.eni.ecole.demothread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb_demo);
        pb.setProgress(0);

    }

    public void onClickHello(View view) {

        Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();

    }

    public void onClickUI(View view) {

        for (int i =0; i <= 10; i++){
            pb.setProgress(i);
            //Pause 1seconde entre chaque boucle
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void clearProgress(){
        pb.setProgress(0);
    }

    private void progress(int i){
        pb.setProgress(i);
    }

    public void onClickThread(View view) {
        //Ne pas oubliez le start()
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i <= 10; i++){
                    progress(i);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    public void onClickAsync(View view) {

        Worker worker = new Worker();
        worker.execute("param1","param2");

    }

    //<Params,Progress,Return>
    class Worker extends AsyncTask<String,Integer,String>{

        //Traitement
        @Override
        protected String doInBackground(String... voids) {

            Log.i("TAG_ASYNC", voids[0]);

            for(int i = 0; i <= 10; i++){

                //Appelle le onProgressUpdate
                publishProgress(i);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "fin";
        }

        //Appeler avant le doInBackground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clearProgress();
        }

        //Lier à publishProgress
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress(values[0]);
        }

        //Appeler à la fin du doInBackground
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            clearProgress();
        }
    }

}
