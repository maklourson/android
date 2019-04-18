package fr.eni.ecole.demostyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        getSupportActionBar().setTitle("Animation");

        img = findViewById(R.id.imgEni);

        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.mon_animation);
        img.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i("TAG_ANIM", "Animation start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i("TAG_ANIM", "Animation end");
                anim.reset();

                img.startAnimation(anim);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("TAG_ANIM", "Animation repeat");
            }
        });
    }
}
