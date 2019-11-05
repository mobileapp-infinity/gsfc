package com.infinity.infoway.gsfc.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.SyllabusResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Syllabus extends AppCompatActivity {
    Context ctx;
    DataStorage storage;
    ListView listView;
    LazyAdapter syAdapter;
    ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    LinearLayout linearLayout1;
    DownloadManager downloadManager;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findviews();

        final ProgressDialog progressDialog = new ProgressDialog(Syllabus.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<SyllabusResponse>> call = apiService.getsyllabus(String.valueOf(storage.read("stud_id", 3)));
        call.enqueue(new Callback<ArrayList<SyllabusResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SyllabusResponse>> call, Response<ArrayList<SyllabusResponse>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1) {
                        ArrayList<SyllabusResponse> syllabus = response.body();
                        Log.d("syllabus", String.valueOf(syllabus));
                        syAdapter = new LazyAdapter(syllabus);
                        listView.setAdapter(syAdapter);
                        // Toast.makeText(EmployeeLeave.this, "Record found", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Syllabus.this, "No Records found", Toast.LENGTH_LONG).show();
                        //Toast.makeText(Syllabus.this, "You have not allocated batch or division", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Toast.makeText(Syllabus.this, "Error in Response", Toast.LENGTH_LONG).show();

                    if (String.valueOf(storage.read("swd_division_id", 3)).equals("0") || String.valueOf(storage.read("swd_batch_id", 3)).equals("0")) {
                        Toast.makeText(Syllabus.this, "You have not allocated Batch/Division", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Syllabus.this, "No Records Found", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SyllabusResponse>> call, Throwable t) {
                progressDialog.dismiss();
                //  Log.e("employeeattendancerespo", t.toString());
            }
        });

    }

    public void findviews() {
        ctx = this;
        storage = new DataStorage("Login_Detail", Syllabus.this);
        listView = (ListView) findViewById(R.id.listview);
    }

    public class LazyAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        ArrayList<SyllabusResponse> syllabusdetail;
        ImageView download;
        Context ctx;


        LazyAdapter(ArrayList<SyllabusResponse> syllabusdetail) {

            this.syllabusdetail = syllabusdetail;

        }

        @Override
        public int getCount() {
            return syllabusdetail.size();
        }

        @Override
        public Object getItem(int position) {
            return position;

        }

        @Override
        public long getItemId(int position) {
            return position;

        }

        @RequiresApi(api = Build.VERSION_CODES.FROYO)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View vi = convertView;
            if (convertView == null)
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.view_syllabus, null);

            download = (ImageView) vi.findViewById(R.id.image1);
            TextView subname = (TextView) vi.findViewById(R.id.subname1);
            // subname.setText(syllabusdetail.get(position).getSubname());
            RelativeLayout rlsubject = (RelativeLayout) vi.findViewById(R.id.rlsubject);
            LinearLayout llimage = (LinearLayout) vi.findViewById(R.id.llimage);

            rlsubject.setBackgroundResource(R.color.sy1);
            llimage.setBackgroundResource(R.color.darksy1);

            if (syllabusdetail.get(position).getPdf() != null) {
                final String file22 = syllabusdetail.get(position).getPdf();
//                Log.d("file", file22);
                String[] file23 = file22.split("/");
//                Log.d("file23", String.valueOf(file23.length));
                String result24 = file23[file23.length - 1];
//                Log.d("result24", result24);
                String nameoffile25 = result24;
//                Log.d("nameoffile", nameoffile25);

                 File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile25);
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SIMS/", nameoffile25);
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Syllabus/", nameoffile25);
                if (file.exists())
                {
                    download.setBackground(getResources().getDrawable(R.drawable.openpdf));
                }
                download.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        if (!marshMallowPermission.checkPermissionForExternalStorage())
                        {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        } else {
                            String file1 = syllabusdetail.get(position).getPdf();
                            Log.d("file1", file1);
                            String[] file2 = file1.split("/");
                            Log.d("filesecond", String.valueOf(file2));
                            String result1 = file2[file2.length - 1];
                            Log.d("result1", result1);
                            String nameoffile1 = result1;
                            Log.d("nameoffile2", nameoffile1);


                             File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile1);
//                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Syllabus/", nameoffile1);
                            if (file.exists()) {
                                File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile1);


//                                File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Syllabus/", nameoffile1);
                                Intent target = new Intent(Intent.ACTION_VIEW);
                                if (Build.VERSION.SDK_INT > 24) {
                                    Uri uri = FileProvider.getUriForFile(Syllabus.this, Syllabus.this.getPackageName() + ".fileprovider", file11);
                                    getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    target.setDataAndType(uri, "application/pdf");
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try
                                    {

                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e)
                                    {
                                        Toast.makeText(Syllabus.this, "No Apps can performs This acttion", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try {
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(Syllabus.this, "No Apps can performs This acttion", Toast.LENGTH_LONG).show();
                                    }
                                }


                            } else {
                                //String url = "https://" + syllabusdetail.get(position).getPdf();
                                String url = syllabusdetail.get(position).getPdf() + "";
                                if (url != null && url.length() > 5) {
                                    String extention = url.substring(url.lastIndexOf("."), url.length());
                                    System.out.println("EXTENSION:::::::::::::::::::::" + extention);
                                    //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                    new DownloadFileFromURL(extention).execute(url);
                                }


                            }
//                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    Uri uri = Uri.parse("http://" + syllabusdetail.get(position).getPdf());
//                    Log.d("response", syllabusdetail.get(position).getPdf());
//                    DownloadManager.Request request = new DownloadManager.Request(uri);
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    Long ref = downloadManager.enqueue(request);

                        }
                    }

                });


            }
            else {

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog;

                        dialog = new Dialog(Syllabus.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.syllabus_dialog);
                        dialog.show();

                    }
                });
            }

            subname.setText("(" + syllabusdetail.get(position).getSub_code() + ") " + syllabusdetail.get(position).getSubname());

            return vi;
        }
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting menubackground thread
         * Show Progress Bar Dialog
         */
        String Extension;
        String nameoffile;

        DownloadFileFromURL(String Extension) {
            this.Extension = Extension;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);

        }

        /**
         * Downloading file in menubackground thread
         */
        @RequiresApi(api = Build.VERSION_CODES.FROYO)
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                String urofysllabusl = f_url[0];
                String[] parts = urofysllabusl.split("/");
                String result = parts[parts.length - 1];
                nameoffile = result;
                URLConnection conection = url.openConnection();
                conection.connect();


//                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture");
                File dir = null;
                 dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GSFC");
              /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                    dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "/Syllabus");
                }*/
                dir.mkdir();


                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Output stream
                OutputStream output = new FileOutputStream("sdcard/GSFC/" + nameoffile);

//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Syllabus/" + nameoffile);
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

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing menubackground task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            syAdapter.notifyDataSetChanged();

             File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile);

           // File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Syllabus/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));

            Intent target = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT > 24) {
                Uri uri = FileProvider.getUriForFile(ctx, Syllabus.this.getPackageName() + ".fileprovider", file11);
                ctx.grantUriPermission(getApplicationContext().getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Extension.compareToIgnoreCase(".pdf") == 0 || Extension.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                } else if (Extension.compareToIgnoreCase(".png") == 0 || Extension.compareToIgnoreCase("png") == 0 || Extension.compareToIgnoreCase(".jpeg") == 0 || Extension.compareToIgnoreCase("jpeg") == 0 || Extension.compareToIgnoreCase(".jpg") == 0 || Extension.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(uri, "image/*");
                }


                Intent intent = Intent.createChooser(target, "Open File");
                try
                {
                    startActivity(intent);
                } catch (ActivityNotFoundException e)
                {
                    Toast.makeText(Syllabus.this, "No Apps can performs This acttion", Toast.LENGTH_LONG).show();
                }

            } else {

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                if (Extension.compareToIgnoreCase(".pdf") == 0 || Extension.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                } else if (Extension.compareToIgnoreCase(".png") == 0 || Extension.compareToIgnoreCase("png") == 0 || Extension.compareToIgnoreCase(".jpeg") == 0 || Extension.compareToIgnoreCase("jpeg") == 0 || Extension.compareToIgnoreCase(".jpg") == 0 || Extension.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "image/*");
                }
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Syllabus.this, "No Apps can performs This acttion", Toast.LENGTH_LONG).show();

                }
            }
        }

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

}
