package com.fahmi.simadesav1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fahmi.simadesav1.penduduk.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private static String SHARED_NAME = "SPLASH";
    private static String FIRST_TIME = "FIRST_TIME";
    ImageView imgV;

    public static boolean checkFirstTime(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SHARED_NAME, 0);
        Boolean first = settings.getBoolean(FIRST_TIME, false);
        if (!first) {
            settings = ctx.getSharedPreferences(SHARED_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_TIME, true);
            editor.commit();
        }
        return first;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkFirstTime(this)) {
            setContentView(R.layout.activity_splash_screen);

            imgV = (ImageView) findViewById(R.id.logo);
            Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);

            imgV.startAnimation(myanim);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();
        }

    }
}
