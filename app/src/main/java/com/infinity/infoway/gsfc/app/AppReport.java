package com.infinity.infoway.gsfc.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppReport extends Application
{

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);

    }



    @Override

    public void onCreate()
    {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override

            public void uncaughtException(Thread thread, Throwable ex) {

                handleUncaughtException(thread, ex);



            }

        });

    }



    public void handleUncaughtException (Thread thread, Throwable e)

    {

        String stackTrace = Log.getStackTraceString(e);

        String message = e.getMessage();





        Intent intent = new Intent (Intent.ACTION_SEND);

        intent.setType("message/rfc822");

        intent.putExtra (Intent.EXTRA_EMAIL, new String[] {"scspl.nirali@gmail.com"});

        intent.putExtra (Intent.EXTRA_SUBJECT, "Agriculture Crash log file");

        intent.putExtra (Intent.EXTRA_TEXT, stackTrace);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application

        startActivity(intent);

    }
}
