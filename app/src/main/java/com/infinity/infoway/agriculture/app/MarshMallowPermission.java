package com.infinity.infoway.agriculture.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.infinity.infoway.agriculture.adapter.TempAdapter;

public class  MarshMallowPermission
{
    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 4;
    Context _context;
    Activity activity;

    public MarshMallowPermission(Activity activity)
    {
        this.activity = activity;
    }
    public  MarshMallowPermission(Context _context)
    {
        this._context=_context;
    }


    public boolean checkPermissionForRecord()
    {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            {
            return false;
        }
    }


    public boolean checkPermissionForExternalStorage()
    {

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            {
            return false;
        }
    }



    public boolean checkPermissionForCall()
    {
        int result = ContextCompat.checkSelfPermission(_context, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            {
            return false;
        }

    }

    public boolean checkpermissioncall()
    {
        if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
        {

            return true;
        }
        else
            {
            return false;
        }
    }

    public boolean checkPermissionForCamera()
    {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            {
            return false;
        }
    }

    public void requestPermissionForRecord()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO))
        {
            Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        }
        else
            {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        }
        else
            {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }


    public void requestPermissionForCamera()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForCall()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE))
        {
            Toast.makeText(activity, "Call permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        }
        else
            {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }


    }
}