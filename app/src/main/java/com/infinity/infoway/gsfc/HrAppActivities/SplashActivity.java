package com.infinity.infoway.gsfc.HrAppActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.infinity.infoway.gsfc.CommonCls.MySharedPrefereces;
import com.infinity.infoway.gsfc.R;


public class SplashActivity extends AppCompatActivity {

    MySharedPrefereces mySharedPrefereces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mySharedPrefereces = new MySharedPrefereces(SplashActivity.this);

        FirebaseApp.initializeApp(SplashActivity.this);

        ImageView ivlogo = (ImageView) findViewById(R.id.ivlogo);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

// Start animation
        ivlogo.startAnimation(slide_up);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (mySharedPrefereces.isUserLogin())
                    {
                        Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(openMainActivity);
                        finish();
                    }
                    else
                    {
                        Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(openMainActivity);
                        finish();
                    }

                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
