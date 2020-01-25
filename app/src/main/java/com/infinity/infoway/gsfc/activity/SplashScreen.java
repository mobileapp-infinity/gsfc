package com.infinity.infoway.gsfc.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.BuildConfig;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomButton;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.Validations;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.GetLatestVesionResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends Activity {
    ApiInterface apiInterface;
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;
    private final String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    Context ctx;
    private String android_id = "", str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ctx = getApplicationContext();

        try {
            android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
        }


        // packageName = getPackageName();

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            //  versionname = pInfo.versionName;
            //  versioncode = pInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            String appOsVer = Build.MODEL + " :: " + Build.VERSION.RELEASE;
            //    getSharePref.saveAppCommonData(pInfo.versionCode, pInfo.versionName, android_id, appOsVer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        /*for update app code from web side */
        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String value = bundle.getString("title");
            if (value != null) {
                DataStorage cstorage = new DataStorage("Login_Detail", this);
                if (cstorage.CheckLogin("stud_id", this)) {
//                    Intent openMainActivity = new Intent(SplashScreen.this, Notification_Activity.class);
                    Intent openMainActivity = new Intent(SplashScreen.this, AnnouncementStudentActiivty.class);
                    openMainActivity.putExtra("type", "notification");
                    startActivity(openMainActivity);
                    finish();
                } else {
                    Intent openMainActivity = new Intent(SplashScreen.this, AnnouncementFaculty.class);
                    openMainActivity.putExtra("type", "notification");
                    startActivity(openMainActivity);
                    finish();

                }
            }
            //startActivity(new Intent(SplashScreen.this, Notification_Activity.class));
        }*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!hasPermissions(SplashScreen.this, RunTimePerMissions)) {
                loginScreenCall();
                //ActivityCompat.requestPermissions(SplashScreen.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
            } else {

                checkVersionInfoApiCall();
            }
        } else {
            checkVersionInfoApiCall();
        }

        ImageView ivlogo = (ImageView) findViewById(R.id.ivlogo);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

// Start animation
        ivlogo.startAnimation(slide_up);

        /*Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openMainActivity = new Intent(SplashScreen.this, Login.class);
                    startActivity(openMainActivity);
                    finish();
                }
            }
        };
        timer.start();*/
    }

    private static boolean hasPermissions(Context context, String... permissions)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        System.out.println("onRequestPermissionsResult!!!!!!!!!!!!!!!!!!!");

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE)
        {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                checkVersionInfoApiCall();
            } else {

                try {
                    alertAlert(getResources().getString(R.string.permissions_has_not_grant));
                } catch (Exception e) {

                }
            }
        }

    }


    private void alertAlert(String msg)
    {
        new AlertDialog.Builder(SplashScreen.this)
                .setTitle(getResources().getString(R.string.permission_request))
                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setIcon(R.drawable.launcher_main)
                .show();
    }

    PackageInfo pInfo = null;

    //    private ProgressDialog progDialog;
    public void checkVersionInfoApiCall()
    {

        try {

            DialogUtils.showProgressDialogNotCancelable(SplashScreen.this, "");
        } catch (Exception ex) {

        }


        System.out.println("this is test of vertion!!!!!!!!!!!! " + pInfo.versionCode + "");
        Call<GetLatestVesionResponse> call = apiInterface.checkversionupdate(pInfo.versionCode);
        call.enqueue(new Callback<GetLatestVesionResponse>() {
            @Override
            public void onResponse(Call<GetLatestVesionResponse> call, final Response<GetLatestVesionResponse> response) {
                // if (progDialog != null && progDialog.isShowing())
                // {
                DialogUtils.hideProgressDialog();
                // }
                System.out.println("this is response!!!!!!!!!!! " + response + "");
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {


                    try {

                        if (response.body().getAPK_URL() != null && !TextUtils.isEmpty(response.body().getAPK_URL())) {


                            LayoutInflater inflater = LayoutInflater.from(SplashScreen.this);
                            final View dialogView = inflater.inflate(R.layout.aommoncls_dialogbox, null);
                            CustomBoldTextView titileTextView = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
                            CustomTextView msgTextView = (CustomTextView) dialogView.findViewById(R.id.tv_msg);
                            CustomTextView tv_msg1 = (CustomTextView) dialogView.findViewById(R.id.tv_msg1);
                            CustomButton dialogButtonOKButton = (CustomButton) dialogView.findViewById(R.id.dialogButtonOK);
                            final CustomButton dialogButtonCancel = (CustomButton) dialogView.findViewById(R.id.dialogButtonCancel);
                            dialogButtonCancel.setTypeface(Validations.setTypeface(SplashScreen.this));
                            tv_msg1.setTypeface(Validations.setTypeface(SplashScreen.this));
                            dialogButtonCancel.setTypeface(Validations.setTypeface(SplashScreen.this));
//                            dialogButtonCancel.setVisibility(View.INVISIBLE);
                            titileTextView.setTypeface(Validations.setTypeface(SplashScreen.this));
                            titileTextView.setText("Update");
                            msgTextView.setText("Please Update Application!!");
                            msgTextView.setTypeface(Validations.setTypeface(SplashScreen.this));

                            final AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);

                            final AlertDialog b = builder.create();
                            builder.setCancelable(false);
                            builder.setView(dialogView);
                            b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                            final AlertDialog show = builder.show();


                            // Button confirmButton = dialogSuccess.findViewById(R.id.confirm_button);
                            dialogButtonOKButton.setText("OK");


                           /* dialogButtonOKButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //  dialogSuccess.dismissWithAnimation();
                                    //  dialogSuccess.cancel();
                                    show.dismiss();
//                                    force update
                                    if (response.body().getStatus() != null && response.body().getStatus().contentEquals("2"))// forcefully
                                    {
                                        dialogButtonCancel.setVisibility(View.INVISIBLE);
                                       *//* try {
                                            registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                        } catch (Exception ex) {
                                        }*//*


                                        UpdateApp atualizaApp = new UpdateApp();
                                        atualizaApp.setContext(SplashScreen.this);
//                                        atualizaApp.execute(response.body().getAPK_URL() + "");
                                        atualizaApp.execute("http://cms.swarrnim.edu.in/APK/Application_file/15/SSIU.apk");


                                    }
                                }
                            });*/

                            /*optional update */
                            if (response.body().getStatus().contentEquals("1"))//not forcefully
                            {

                                dialogButtonOKButton.setText("OK");

                                dialogButtonCancel.setVisibility(View.VISIBLE);
                                dialogButtonOKButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v) {
                                        show.dismiss();
                                        System.out.println("in to 44444444444>>>!!!!!!!!!!!!!!!!!!!!");
                                        UpdateApp atualizaApp = new UpdateApp();
                                        atualizaApp.setContext(SplashScreen.this);
                                        atualizaApp.execute(response.body().getAPK_URL() + "");
//                                        atualizaApp.execute("http://cms.swarrnim.edu.in/APK/Application_file/15/SSIU.apk");
//                                        atualizaApp.execute("http://etrack.ierp.co.in/upload/AndroidApk/etrack.apk");

                                    }
                                });
                                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        show.dismiss();
                                        System.out.println("in to 44444444444!!!!!!!!!!!!!!!!!!!!");
                                        loginScreenCall();

                                    }
                                });

                            }

                            /*force update*/
                            if (response.body().getStatus() != null && response.body().getStatus().contentEquals("2"))// forcefully
                            {
                                dialogButtonCancel.setVisibility(View.INVISIBLE);
                                       /* try {
                                            registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                        } catch (Exception ex) {
                                        }*/

                                dialogButtonOKButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        show.dismiss();
                                        UpdateApp atualizaApp = new UpdateApp();
                                        atualizaApp.setContext(SplashScreen.this);
                                        atualizaApp.execute(response.body().getAPK_URL() + "");
//                                            atualizaApp.execute("http://cms.swarrnim.edu.in/APK/Application_file/15/SSIU.apk");
                                    }
                                });


                            }


                        } else {
                            System.out.println("in to 3333333333333!!!!!!!!!!!!!!!!!!!!");
                            loginScreenCall();
                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println("in to 2222222222222!!!!!!!!!!!!!!!!!!!!");
                        loginScreenCall();
                    }


                } else {
                    // Log.e("Error in res",response.message());
                    System.out.println("in to 1111111111111!!!!!!!!!!!!!!!!!!!!");
                    loginScreenCall();
                }

            }

            @Override
            public void onFailure(Call<GetLatestVesionResponse> call, Throwable t) {
//                if (progDialog != null && progDialog.isShowing()) {
////                    progDialog.dismiss();
////                }
                System.out.println("in to error!!!!!!!!!!!!!!!!!!!!");
                t.printStackTrace();
                DialogUtils.hideProgressDialog();
                loginScreenCall();
            }
        });


    }


    private class UpdateApp extends AsyncTask<String, String, String> {
        private Context context;

        String filename = "";

        void setContext(Context contextf) {
            this.context = contextf;
        }

        @Override
        protected void onPreExecute() {

            try {

                DialogUtils.showProgressDialogNotCancelable(SplashScreen.this, "");
            } catch (Exception ignored) {

            }

        }

        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            try {
                DialogUtils.hideProgressDialog();
            } catch (Exception ex) {
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            int count;
            try {
                URL url = new URL(arg0[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 64000);

                // Output stream to write file

//                String root = Environment.getExternalStorageDirectory().toString();
                String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GSFC";
                File myDir = new File(root + "");

                myDir.mkdirs();

                final String file22 = arg0[0];
                String[] file23 = file22.split("/");
                String result24 = file23[file23.length - 1];
                String nameoffile25 = result24;

                System.out.println("file name of apk ::::::::::: " + nameoffile25);

                filename = nameoffile25;
                File file = new File(myDir, filename);

                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

//                download_apk();
            } catch (Exception e) {
                System.out.println("Error!!!!!!!!!!!! ");
                e.printStackTrace();
                try {
                    Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                } catch (Exception e1) {
                    System.out.println("error 222222222");
                    //  e1.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            try {
                DialogUtils.hideProgressDialog();
            } catch (Exception ignored) {

            }

            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GSFC/" + filename;

            File toInstall = new File(filepath);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                   /*  Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
                     Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                     intent.setData(apkUri);*/


                    Intent intent;

                    Uri apkUri = FileProvider.getUriForFile(SplashScreen.this, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
                    intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setData(apkUri);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                    List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //                    context.startActivity(intent);
                    startActivityForResult(intent, 200);


                } catch (Exception ex) {
                    //Log.e("error",ex.getMessage());
                    System.out.println("ERROR111111111111111 ");
                    ex.printStackTrace();
                }
            } else {
                try {
                    Uri apkUri = Uri.fromFile(toInstall);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex) {
                    System.out.println("ERROR222222222222222222 ");
                    ex.printStackTrace();
                }
            }
        }
    }


    private void loginScreenCall() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String value = bundle.getString("title");
            if (value != null) {
                DataStorage cstorage = new DataStorage("Login_Detail", this);
                if (cstorage.CheckLogin("stud_id", this)) {
                    //                    Intent openMainActivity = new Intent(SplashScreen.this, Notification_Activity.class);
                    Intent openMainActivity = new Intent(SplashScreen.this, AnnouncementStudentActiivty.class);
                    openMainActivity.putExtra("type", "notification");
                    startActivity(openMainActivity);
                    finish();
                } else {
                    Intent openMainActivity = new Intent(SplashScreen.this, AnnouncementFaculty.class);
                    openMainActivity.putExtra("type", "notification");
                    startActivity(openMainActivity);
                    finish();

                }
            } else {
                Intent openMainActivity = new Intent(SplashScreen.this, Login.class);
                startActivity(openMainActivity);
                finish();
            }
            //startActivity(new Intent(SplashScreen.this, Notification_Activity.class));
        } else {
            Intent openMainActivity = new Intent(SplashScreen.this, Login.class);
            startActivity(openMainActivity);
            finish();
        }

//        Intent openMainActivity = new Intent(SplashScreen.this, Login.class);
//        startActivity(openMainActivity);
//        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("on activity result!!!!!!!!!!!!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
