package fr.eni.ecole.demopicasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private ImageView imgLocale;
    private final static String IMAGE = "https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgPica);
        imgLocale = findViewById(R.id.imgLocale);

        int orient = getResources().getConfiguration().orientation;

        Log.i("TAG_ORIENT", "orientation : " + orient);

        int orientation = 90;




    }
}
