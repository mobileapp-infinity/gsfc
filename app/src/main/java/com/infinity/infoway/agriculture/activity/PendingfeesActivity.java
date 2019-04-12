package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.app.MarshMallowPermission;
import com.infinity.infoway.agriculture.model.Fee_Receipt_List;
import com.infinity.infoway.agriculture.model.Fees;
import com.infinity.infoway.agriculture.model.pendingfeesmodel;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.infinity.infoway.agriculture.rest.Api_Client;
import com.infinity.infoway.agriculture.rest.Api_Rec_Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingfeesActivity extends AppCompatActivity
{
    ListView listviewrec;
    DataStorage storage;
    Toolbar toolbar;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    TextView txtviewpendingfees;

    LinearLayout main_pendingfess_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingfees);

        findviews();

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
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        if (!marshMallowPermission.checkPermissionForExternalStorage())
//        {
//            marshMallowPermission.requestPermissionForExternalStorage();
//        }
//        else
//        {

//        }



    }

    public void findviews()
    {
        txtviewpendingfees=(TextView)findViewById(R.id.txtviewpendingfees);
        main_pendingfess_ll =(LinearLayout)findViewById(R.id.main_pendingfess_ll);
        txtviewpendingfees.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!marshMallowPermission.checkPermissionForExternalStorage())
                {
                    marshMallowPermission.requestPermissionForExternalStorage();
                }
                else
                {
                    APICAllPendingfees();
                }

            }
        });
//        listviewrec = (ListView) findViewById(R.id.list_pendinf_fees);
        storage = new DataStorage("Login_Detail", this);
    }


    public  void  APICAllPendingfees()
    {
        final ProgressDialog progressDialog = new ProgressDialog(PendingfeesActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<pendingfeesmodel> call = apiInterface.get_pending_fees(String.valueOf(storage.read("stud_id",3)));
        call.enqueue(new Callback<pendingfeesmodel>()
        {
            @Override
            public void onResponse(Call<pendingfeesmodel> call, Response<pendingfeesmodel> response)
            {

                if (response.isSuccessful())
                {
                    progressDialog.dismiss();

                    if (response.body().getStatus().equals("1"))
                    {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Agriculture/" + response.body().getFilename());

                       /* if (file.exists())
                        {
                            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Atmiya/" + response.body().getFilename());

                            Intent target = new Intent(Intent.ACTION_VIEW);

                            if (Build.VERSION.SDK_INT > 24)
                            {
                                Uri uri = FileProvider.getUriForFile(PendingfeesActivity.this,getPackageName()+".fileprovider", file11);


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
                                    Toast.makeText(PendingfeesActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(PendingfeesActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                }


                            }
                        }*/

//                        else
//                        {
                            try
                            {
                                byte data[] = new byte[1024];

                                long total = 0;
                                data = response.body().getbytearr().getBytes("UTF-8");
                                //data = response.body().getbytearr();
                                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Agriculture");
                                dir.mkdirs();
                                //File outputFile = new File(createfile, "Sample.pdf");
//                                      FileOutputStream fos = new FileOutputStream(dir);

                                     /* //Writing into the PDF File
                                      strByte = (xpp.getText().toString());
                                      bytes = Base64.decode(strByte.toString(),Base64.DEFAULT);//Converting Base64 to byte
                                     */

                                Date name_of_file = new Date();
                                String file_name = name_of_file.toString();

                                String name =  file_name.replace(" ","_");
                                System.out.println("file_name_new>>>>>>"+name);
                                System.out.println("name_of_date::::::::::"+name_of_file);


                                System.out.println("filePATHNAME In pending fees>>>>>>>>"+response.body().getFilename()+""+name_of_file);
                                String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Agriculture/" + response.body().getFilename()+"_"+name;

                                byte[] pdfAsBytes = Base64.decode(response.body().getbytearr(), 0);
                                FileOutputStream os;
                                os = new FileOutputStream(filepath, false);
                                os.write(pdfAsBytes);
                                os.flush();
                                os.close();

                                File file11 = new File(filepath);

//                                File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Atmiya/" + response.body().getFilename());

                                Intent target = new Intent(Intent.ACTION_VIEW);

                                if (Build.VERSION.SDK_INT > 24)
                                {
                                    Uri uri = FileProvider.getUriForFile(PendingfeesActivity.this,getPackageName()+".fileprovider", file11);

                                    target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    target.setDataAndType(uri,"application/pdf");
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try
                                    {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        Toast.makeText(PendingfeesActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                    }
                                }

                                else
                                {
                                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                                    target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Intent intent = Intent.createChooser(target, "Open File");
                                    try
                                    {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        Toast.makeText(PendingfeesActivity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                    }


                                }


                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                                // Log.d("erordownloadpdffee", String.valueOf(e));
                            }

//                        }
                    }

                    else
                    {
                        progressDialog.dismiss();
                        Date name_of_file = new Date();
                        System.out.println("name_of_date::::::::::"+name_of_file);

                        main_pendingfess_ll.setVisibility(View.GONE);

                        Toast.makeText(PendingfeesActivity.this,"No Pending fees Found",Toast.LENGTH_LONG).show();
                    }



                }

                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(PendingfeesActivity.this, "Try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<pendingfeesmodel> call, Throwable t)
            {
                progressDialog.dismiss();
            }
        });

    }





}
