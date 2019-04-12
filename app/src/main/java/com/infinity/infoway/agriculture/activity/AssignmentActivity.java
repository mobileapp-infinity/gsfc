package com.infinity.infoway.agriculture.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.infinity.infoway.agriculture.BuildConfig;
import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.app.MarshMallowPermission;
import com.infinity.infoway.agriculture.model.SyllabusResponse;
import com.infinity.infoway.agriculture.model.assignment_response;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class AssignmentActivity extends AppCompatActivity
{
    Context ctx;
    DataStorage storage;
    ListView listView_assgnment;
    AssignmentActivity.AssignmentAdapter syAdapter;
    ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    LinearLayout linearLayout1;
    DownloadManager downloadManager;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    Toolbar toolbar;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        findviews();

        Api_call_assignment();


    }


    public void  findviews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", AssignmentActivity.this);
        mProgressDialog = new ProgressDialog(AssignmentActivity.this);
        mProgressDialog.setIndeterminate(true);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        mProgressDialog.setTitle("Download Image");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, downloading  file...");
        listView_assgnment = (ListView) findViewById(R.id.assignment_listview);
    }


    public  void  Api_call_assignment()
    {
        final ProgressDialog progressDialog = new ProgressDialog(AssignmentActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<assignment_response>> call = apiService.get_student_assignment_detail(String.valueOf(storage.read("stud_id", 3)),String.valueOf(storage.read("swd_year_id",3)));
        call.enqueue(new Callback<ArrayList<assignment_response>>()
        {
            @Override
            public void onResponse(Call<ArrayList<assignment_response>> call, Response<ArrayList<assignment_response>> response)
            {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size()>=1)
                    {
                        ArrayList<assignment_response> assignment = response.body();
                        Log.d("syllabus", String.valueOf(assignment));
                        syAdapter = new AssignmentActivity.AssignmentAdapter(assignment);
                        listView_assgnment.setAdapter(syAdapter);
                        // Toast.makeText(EmployeeLeave.this, "Record found", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AssignmentActivity.this, "No Records found", Toast.LENGTH_LONG).show();
                        //Toast.makeText(Syllabus.this, "You have not allocated batch or division", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(AssignmentActivity.this, "Please try again later", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<ArrayList<assignment_response>> call, Throwable t)
            {
                progressDialog.dismiss();
                //  Log.e("employeeattendancerespo", t.toString());
            }
        });

    }



    public class AssignmentAdapter extends BaseAdapter
    {

        private LayoutInflater inflater;
        ArrayList<assignment_response> assignment_response;
        ImageView download,image_final_ass;

        Context ctx;


        AssignmentAdapter(ArrayList<assignment_response> assignment_response)
        {

            this.assignment_response = assignment_response;

        }

        @Override
        public int getCount()
        {
            return assignment_response.size();
        }

        @Override
        public Object getItem(int position)
        {
            return position;

        }

        @Override
        public long getItemId(int position)
        {
            return position;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            View vi = convertView;
            if (convertView == null)
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.view_assignment, null);

            download = (ImageView) vi.findViewById(R.id.image_download_assign);
//            image_final_ass = (ImageView) vi.findViewById(R.id.image_final_ass);
            TextView subname = (TextView) vi.findViewById(R.id.subname1);

            // subname.setText(syllabusdetail.get(position).getSubname());
            RelativeLayout rlsubject = (RelativeLayout) vi.findViewById(R.id.rlsubject);
            LinearLayout llimage = (LinearLayout) vi.findViewById(R.id.llimage);

            rlsubject.setBackgroundResource(R.color.attendance);
//            llimage.setBackgroundResource(R.color.assignment);

            if (assignment_response.get(position).getPDF_URL()!= null)
            {
                final String file22 = assignment_response.get(position).getPDF_URL();
//                Log.d("file", file22);
                String[] file23 = file22.split("/");
//                Log.d("file23", String.valueOf(file23.length));
                String result24 = file23[file23.length - 1];
//                Log.d("result24", result24);
                String nameoffile25 = result24;
//                Log.d("nameoffile", nameoffile25);

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + nameoffile25);

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
                        }
                        else if (assignment_response.get(position).getPDF_URL().endsWith(".pdf"))
                        {
                            String file1 = assignment_response.get(position).getPDF_URL();
                            Log.d("file1", file1);
                            String[] file2 = file1.split("/");
                            Log.d("filesecond", String.valueOf(file2));
                            String result1 = file2[file2.length - 1];
                            Log.d("result1", result1);
                            String nameoffile1 = result1;
                            Log.d("nameoffile2", nameoffile1);
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + nameoffile1);
                            if (file.exists())
                            {

                                download.setBackground(getResources().getDrawable(R.drawable.openpdf));


                                    File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + nameoffile1);
                                    Log.d("pathoffile", String.valueOf(file11));

                                    Intent target = new Intent(Intent.ACTION_VIEW);
                                    target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    if (Build.VERSION.SDK_INT > 24)
                                    {


//
                                        Uri uri =FileProvider.getUriForFile(AssignmentActivity.this,BuildConfig.APPLICATION_ID+".fileprovider", file11);
                                        getApplicationContext().grantUriPermission(BuildConfig.APPLICATION_ID,uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        target.setDataAndType(uri,"application/pdf");
                                        Intent intent = Intent.createChooser(target, "Open File");
                                        try
                                        {
                                            startActivity(intent);
                                        }
                                        catch (ActivityNotFoundException e)
                                        {
                                            Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    else
                                    {
                                        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        Intent intent = Intent.createChooser(target, "Open File");
                                        try
                                        {
                                            startActivity(intent);
                                        }
                                        catch (ActivityNotFoundException e)
                                        {
                                            Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                        }

                                        }


//                                try
//                                {
//                                    startActivity(target);
//                                }
//                                catch (ActivityNotFoundException e)
//                                {
//                                    // Instruct the user to install a PDF reader here, or something
//                                }




                                }
                            else
                            {
                                String url =  "https://" + assignment_response.get(position).getPDF_URL();
                                //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                new AssignmentActivity.DownloadFileFromURL().execute(url);


                            }

//                            else
//                            {
//                                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                                Uri uri = Uri.parse("http://" + assignment_response.get(position).getPDF_URL());
//                                Log.d("response", assignment_response.get(position).getPDF_URL());
//                                DownloadManager.Request request = new DownloadManager.Request(uri);
//                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                Long ref = downloadManager.enqueue(request);
//
//                            }

                        }

                        else

                        {
                            String file1 = assignment_response.get(position).getPDF_URL();
                            Log.d("file1", file1);
                            String[] file2 = file1.split("/");
                            Log.d("filesecond", String.valueOf(file2));
                            String result1 = file2[file2.length - 1];
                            Log.d("result1", result1);
                            String nameoffile1 = result1;
                            Log.d("nameoffile2", nameoffile1);
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + nameoffile1);
                            if (file.exists())
                            {

                                download.setBackground(getResources().getDrawable(R.drawable.openpdf));

                                File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + nameoffile1);
                                Log.d("pathoffile", String.valueOf(file11));

                                Intent target = new Intent(Intent.ACTION_VIEW);
                                target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT > 24)
                                {
                                    Uri uri =FileProvider.getUriForFile(AssignmentActivity.this,getPackageName()+".fileprovider", file11);


                                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    target.setDataAndType(uri,"image/*");
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try
                                    {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                    }
                                }

                                else
                                {
                                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    target.setDataAndType(Uri.fromFile(file11), "image/*");
                                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try
                                    {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                    }


                                }



                                }

                            else
                            {
                                String url = "https://" + assignment_response.get(position).getPDF_URL();
                                Log.d("assignment_image_url", assignment_response.get(position).getPDF_URL());
                                new AssignmentActivity.DownloadImage().execute(stringToURL(url));


                            }


                        }

                    }

                });


            }


            else
            {

                download.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Dialog dialog;

                        dialog = new Dialog(AssignmentActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.syllabus_dialog);
                        dialog.show();

                    }
                });
            }

            subname.setText(assignment_response.get(position).getSub_fullname() +"                         "+assignment_response.get(position).getAm_name());

            return vi;
        }
    }



    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }



    private class DownloadFileFromURL extends AsyncTask<String, String, String>
    {


        String nameoffile;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showDialog(progress_bar_type);

        }

        /**
         * Downloading file in menubackground thread
         */
        @Override
        protected String doInBackground(String... f_url)
        {
            int count;
            try
            {
                URL url = new URL(f_url[0]);


                    String urofysllabusl = f_url[0];
                    String[] parts = urofysllabusl.split("/");
                    String result = parts[parts.length - 1];
                    nameoffile = result;
                    URLConnection conection = url.openConnection();
                    conection.connect();

                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture");
                    dir.mkdirs();

                    // this will be useful so that you can show a tipical 0-100% progress bar
                    int lenghtOfFile = conection.getContentLength();
                    //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());

                    // Output stream
                    OutputStream output = new FileOutputStream("sdcard/Agriculture/" + nameoffile);

                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1)
                    {
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


                    }
            catch (Exception e)
            {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress)
        {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            Toast.makeText(ctx,"Download Completed Successfully and file save to internal storage",Toast.LENGTH_LONG).show();
            syAdapter.notifyDataSetChanged();
            String imagePath = Environment.getExternalStorageDirectory().toString() + "Agriculture/" + nameoffile;




            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Agriculture/" + nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24)
            {


                Uri uri =FileProvider.getUriForFile(ctx,AssignmentActivity.this.getPackageName()+".fileprovider", file11);
                ctx.grantUriPermission(getApplicationContext().getPackageName(),uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                target.setDataAndType(uri,"application/pdf");
                Intent intent = Intent.createChooser(target, "Open File");
                try
                {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                }
            }

            else
            {
                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try
                {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                }


            }


        }

    }


    private class DownloadImage extends AsyncTask<URL,Void,Bitmap>
    {

        String nameoffile;
        // Before the tasks execution
        protected void onPreExecute()
        {
            // Display the progress dialog on async task start
            mProgressDialog.show();
        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL...urls)
        {
            URL url = urls[0];
            Log.d("url", String.valueOf(url));



            String urofysllabusl = url.toString();
            String[] parts = urofysllabusl.split("/");
            String result = parts[parts.length - 1];
            nameoffile = result;
            Log.d("name_of_final_image",nameoffile);
            HttpURLConnection connection = null;

            try
            {
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();


                // Initialize a new BufferedInputStream from InputStream


                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);

                Log.d("bitmap", String.valueOf(bmp));


                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Atmiya";
                File dir = new File(file_path);
                if(!dir.exists())
                {
                    dir.mkdirs();
                }

                File file = new File(dir, nameoffile);
                FileOutputStream fOut = new FileOutputStream(file);

                bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();





                return bmp;

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // Disconnect the http url connection
                connection.disconnect();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result)
        {
            // Hide the progress dialog
            mProgressDialog.dismiss();
            Toast.makeText(ctx,"Download Completed Successfully and file save to internal storage",Toast.LENGTH_LONG).show();


            if(result!=null)

            {
                File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Agriculture/" + nameoffile);


                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT > 24)
                {


                    Uri uri =FileProvider.getUriForFile(ctx,AssignmentActivity.this.getPackageName()+".fileprovider", file11);
                    ctx.grantUriPermission(getApplicationContext().getPackageName(),uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);


                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    target.setDataAndType(uri,"image/*");
                    Intent intent = Intent.createChooser(target, "Open File");
                    try
                    {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                    }
                }

                else
                {
                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    target.setDataAndType(Uri.fromFile(file11), "image/*");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent intent = Intent.createChooser(target, "Open File");
                    try
                    {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(AssignmentActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                    }


                }

            }
            else
                {
                // Notify user that an error occurred while downloading image
                Toast.makeText(AssignmentActivity.this,"Please try again later",Toast.LENGTH_LONG).show();
            }

        }
    }

    protected URL stringToURL(String urlString)
    {
        try
        {
            URL url = new URL(urlString);
            return url;
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

                    //Unused code
    protected Uri saveImageToInternalStorage(Bitmap bitmap)
    {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir(Environment.getExternalStorageState() + "Agriculture/",MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName"+".jpg");

        try{
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        }
        catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
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
