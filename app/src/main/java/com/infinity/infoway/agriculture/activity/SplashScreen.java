package com.infinity.infoway.agriculture.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;

public class SplashScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            String value = bundle.getString("title");
            if (value != null)
            {
                DataStorage cstorage = new DataStorage("Login_Detail", this);
                if (cstorage.CheckLogin("stud_id", this) || cstorage.CheckLogin("emp_id", this))
                {
                    Intent openMainActivity = new Intent(SplashScreen.this, Notification_Activity.class);
                    openMainActivity.putExtra("type", "notification");
                    startActivity(openMainActivity);
                    finish();
                }
            }
            //startActivity(new Intent(SplashScreen.this, Notification_Activity.class));
        }

        ImageView ivlogo = (ImageView)findViewById(R.id.ivlogo);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

// Start animation
        ivlogo.startAnimation(slide_up);
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent openMainActivity = new Intent(SplashScreen.this, Login.class);
                    startActivity(openMainActivity);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }


}
