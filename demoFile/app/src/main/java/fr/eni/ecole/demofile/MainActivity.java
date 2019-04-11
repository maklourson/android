package fr.eni.ecole.demofile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String monFichier = "monFichier.txt";
    private String msg = "ceci est un texte du fichier";
    private StringBuffer content = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onWriteClick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(monFichier, Context.MODE_PRIVATE);
                    fos.write(msg.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void onReadClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                byte[] buffer = new byte[1024];

                try {
                    fis = openFileInput(monFichier);
                    while(fis.read(buffer) != -1){
                        content.append(new String(buffer));
                    }
                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        Toast.makeText(MainActivity.this, "Lecture : " + content.toString(),
                Toast.LENGTH_LONG).show();
    }

}
