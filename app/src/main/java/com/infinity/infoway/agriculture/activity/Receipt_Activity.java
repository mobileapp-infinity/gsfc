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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.app.MarshMallowPermission;
import com.infinity.infoway.agriculture.model.Fee_Receipt_List;
import com.infinity.infoway.agriculture.model.Fees;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.infinity.infoway.agriculture.rest.Api_Rec_Client;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Receipt_Activity extends AppCompatActivity {

    ListView listviewrec;
    DataStorage storage;
    Toolbar toolbar;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_);

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(Receipt_Activity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();


        ApiInterface apiInterface = Api_Rec_Client.getClient().create(ApiInterface.class);
        Map<String, String> maps;
        maps = new HashMap<String, String>();
        maps.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
        Call<ArrayList<Fee_Receipt_List>> call = apiInterface.getreceiptlist(maps);
        call.enqueue(new Callback<ArrayList<Fee_Receipt_List>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Fee_Receipt_List>> call, Response<ArrayList<Fee_Receipt_List>> response) {

                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    ArrayList<Fee_Receipt_List> resp = response.body();
                    Log.d("resp", String.valueOf(resp));
                    CustomAdapter adapter = new CustomAdapter(resp);
                    listviewrec.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Fee_Receipt_List>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    public void findviews() {

        listviewrec = (ListView) findViewById(R.id.listviewrec);
        storage = new DataStorage("Login_Detail", this);
    }


    public class CustomAdapter extends BaseAdapter
    {
        ArrayList<Fee_Receipt_List> resp;
        LayoutInflater inflater;

        public CustomAdapter(ArrayList<Fee_Receipt_List> resp)
        {
            this.resp = resp;
        }


        @Override
        public int getCount() {
            return resp.size();
        }

        @Override
        public Object getItem(int position) {
            return position;

        }

        @Override
        public long getItemId(int position) {
            return position;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            View vi = convertView;

            if (convertView == null)
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.fee_rec_list, null);

            TextView recno = (TextView) vi.findViewById(R.id.txtrecno);
            TextView date = (TextView) vi.findViewById(R.id.txtdate);
            TextView course = (TextView) vi.findViewById(R.id.txtcoursename);
            TextView sem = (TextView) vi.findViewById(R.id.txtsem);
            TextView amount = (TextView) vi.findViewById(R.id.txtamounnt);
//            TextView semesetr=(TextView)viewGroup.findViewById(R.id.txtsem);

            recno.setText(resp.get(position).getFeereceiptno());
            date.setText(resp.get(position).getFeerecepitdate());
            course.setText(resp.get(position).getFeecoursename());
            sem.setText(resp.get(position).getFeeclassname());
            amount.setText(resp.get(position).getFeeamount());

            TextView receipt = (TextView) vi.findViewById(R.id.Receipt);

            receipt.setOnClickListener(new View.OnClickListener()
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
                        final ProgressDialog progressDialog = new ProgressDialog(Receipt_Activity.this, R.style.MyTheme1);
                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                        progressDialog.show();
                        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                        Call<Fees> call = apiService.Download_Fee_Receipt(String.valueOf(storage.read("stud_id", 3)), resp.get(position).getFeereceiptno());

                        call.enqueue(new Callback<Fees>()
                        {
                            @Override
                            public void onResponse(Call<Fees> call, Response<Fees> response)
                            {
                                progressDialog.dismiss();
                                if (response.isSuccessful())
                                {
                                    // Log.d("responseBodyvalue",String.valueOf(response.body()));
                                    if (response.body().getstatus().equals("1"))
                                    {
                                        //String responseBody=response.body().getbytearr();
                                        // Log.d("responseBody",String.valueOf(response.body().getbytearr()));

                                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + response.body().getFilename());

                                        if (file.exists())
                                        {
                                            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + response.body().getFilename());

                                            Intent target = new Intent(Intent.ACTION_VIEW);

                                            if (Build.VERSION.SDK_INT > 24)
                                            {
                                                Uri uri = FileProvider.getUriForFile(Receipt_Activity.this,getPackageName()+".fileprovider", file11);


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
                                                    Toast.makeText(Receipt_Activity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
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
                                                    Toast.makeText(Receipt_Activity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                                }


                                            }
                                        }

                                        else
                                            {
                                            try
                                            {
                                                byte data[] = new byte[1024];

                                                long total = 0;
                                                data = response.body().getbytearr().getBytes("UTF-8");
                                                //data = response.body().getbytearr();
                                                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture");
                                                dir.mkdirs();
                                                //File outputFile = new File(createfile, "Sample.pdf");
//                                      FileOutputStream fos = new FileOutputStream(dir);

                                     /* //Writing into the PDF File
                                      strByte = (xpp.getText().toString());
                                      bytes = Base64.decode(strByte.toString(),Base64.DEFAULT);//Converting Base64 to byte
                                     */

                                                String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture/" + response.body().getFilename();
                                                byte[] pdfAsBytes = Base64.decode(response.body().getbytearr(), 0);
                                                FileOutputStream os;
                                                os = new FileOutputStream(filepath, false);
                                                os.write(pdfAsBytes);
                                                os.flush();
                                                os.close();
                                                File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "agriculture/" + response.body().getFilename());

                                                Intent target = new Intent(Intent.ACTION_VIEW);

                                                if (Build.VERSION.SDK_INT > 24)
                                                {
                                                    Uri uri = FileProvider.getUriForFile(Receipt_Activity.this,getPackageName()+".fileprovider", file11);

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
                                                        Toast.makeText(Receipt_Activity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
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
                                                        Toast.makeText(Receipt_Activity.this,"No Apps can performs This acttion",Toast.LENGTH_LONG).show();
                                                    }


                                                }


                                                }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                                // Log.d("erordownloadpdffee", String.valueOf(e));
                                            }

                                        }
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Receipt_Activity.this, "Try again later", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Fees> call, Throwable t)
                            {
                                progressDialog.dismiss();
                                //Log error here since request failed
                                //  Log.e("employeeattendancerespo", t.toString());
                            }
                        });
                    }
                }

            });
            return vi;
        }
    }


}
