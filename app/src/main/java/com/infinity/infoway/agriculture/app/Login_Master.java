package com.infinity.infoway.agriculture.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login_Master
{
    public static final int DIALOG_ERROR_CONNECTION = 1;
    private String FileName;
    private Context ctx;
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";

    SharedPreferences pref = null;
    SharedPreferences.Editor editor = null;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int STRING = 3;
    public static final int BOOLEAN = 4;
    public static final String LOGIN = null;
    public static final String PACKAGE_NAME = "com.infinity.infoway.krishna";


    public Login_Master()
    {

    }

    public Login_Master(String FileName, Context ctx)
    {
        this.FileName = FileName;
        this.ctx = ctx;
        pref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean CheckLogin(String keyname, Context ctx)
    {
        this.ctx = ctx;
        pref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return pref.contains(keyname);
    }

    public void write(String key, int value)
    {
        editor.putInt(key, value);
        editor.commit();
    }


    public void write(String key, float value)
    {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void write(String key, String value)
    {
        editor.putString(key, value);
        editor.commit();
    }

    public void write(String key, boolean value)
    {
        editor.putBoolean(key, value);
        editor.commit();
    }


    public void clear(String s)
    {
       /* editor.clear();
        editor.commit();*/

        editor.remove(s);

        editor.commit();
    }

    public Object read(String key, int DataType)
    {
        Object temp = new Object();
        if (DataType == INTEGER)
            temp = pref.getInt(key,0);
        else if (DataType == FLOAT)
            temp = pref.getFloat(key, 0.0f);
        else if (DataType == STRING)
            temp = pref.getString(key, "");
        else if (DataType == BOOLEAN)
            temp = pref.getBoolean(key, false);

        return temp;
    }

    public String ReadFromIntenalStorage(int FileId)
    {
        StringBuffer content = new StringBuffer();
        InputStream is = ctx.getResources().openRawResource(FileId);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                content.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }


    public boolean isOnline(Context c)
    {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }
}
