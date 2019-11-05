package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.Fees;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fees_Activity extends AppCompatActivity {
    ActionBar actionBar;
    TextView tvfeedate, paytm_pay, axis_pay, tvfeetype, tvpendingmsg, tvtotalfee, tvpaidfee, tvpendingfee, tvpendingfeerefund, tvduedate, tvpaynow;
    DataStorage storage;
    Toolbar toolbar;
    String adminssion_no;
    LinearLayout mainlectlayout;
    String key = "v8llRrQaDng=";
    String Fee_Circular_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_);
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

        APi_call_load_fees();
    }


    public void APi_call_load_fees()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Fees_Activity.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        //progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Fees> call = apiService.getstudentfee(String.valueOf(storage.read("stud_id", 3)));

        call.enqueue(new Callback<Fees>() {
            @Override
            public void onResponse(Call<Fees> call, Response<Fees> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    //changes  for  demo agriculture 17 march
                    //tvfeedate.setText(response.body().getFee_Date());
                    tvfeedate.setText("0.0");
                   // tvfeetype.setText(response.body().getFee_Type());
                    tvfeetype.setText("0.0");
                   // tvtotalfee.setText(response.body().getTotal_Fee());
                    tvtotalfee.setText("0.0");
                   // tvpaidfee.setText(response.body().getPaid_Fee());
                    tvpaidfee.setText("0.0");
                   // tvpendingfee.setText(response.body().getPending_Fee());
                    tvpendingfee.setText("0.0");
                   // tvpendingfeerefund.setText(response.body().getPending_Fee_Refund());
                    tvpendingfeerefund.setText("0.0");

                    //tvduedate.setText(response.body().getDue_Date());
                    tvduedate.setText("");
                    //adminssion_no = response.body().getStudent_ID();



                    //chnages for demo agriculture 17 march
                    /*if (tvpendingfee.getText().toString().equals("0.0") || tvpendingfee.getText().toString().equals("0.00") || tvpendingfee.getText().toString().equals("00.00")) {
                        //tvpaynow.setVisibility(View.GONE);
                        mainlectlayout.setVisibility(View.INVISIBLE);
                        tvpendingmsg.setText("no pending fees");
                        tvpendingmsg.setBackgroundColor(getResources().getColor(R.color.sy1));
                        Fee_Circular_ID = response.body().Fee_Circular_ID;
                        System.out.println("Fee_Circular_ID>>>>" + Fee_Circular_ID);
                        storage.write("Fee_Circular_ID", Fee_Circular_ID);
//                        Log.d("Fee_Circular_ID", Fee_Circular_ID);

                        // tvpaynow.setText("Fee Receipt");
                        // TextView tv =new TextView(this);
                        //Toast.makeText(Fees_Activity.this,"No pending fees",Toast.LENGTH_LONG).show();

                        if (tvtotalfee.getText().toString().equals("0.0") || tvpaidfee.getText().toString().equals("0.00") || tvtotalfee.getText().toString().equals("00.00")) {
                            if (tvpaidfee.getText().toString().equals("0.0") || tvpaidfee.getText().toString().equals("0.00") || tvpaidfee.getText().toString().equals("00.00")) {
                                Fee_Circular_ID = response.body().Fee_Circular_ID;
                                storage.write("Fee_Circular_ID", Fee_Circular_ID);
//                                Log.d("Fee_Circular_ID", Fee_Circular_ID);
                                //tvpaynow.setVisibility(View.GONE);
                                //tvpaynow.setText("Fee Receipt");
                                tvpaynow.setVisibility(View.GONE);
                                paytm_pay.setVisibility(View.GONE);
                                axis_pay.setVisibility(View.GONE);

                            }

                        }

                    } else {
                        Fee_Circular_ID = response.body().Fee_Circular_ID;
                        storage.write("Fee_Circular_ID", Fee_Circular_ID);
//                        Log.d("Fee_Circular_ID", Fee_Circular_ID);
                        tvpaynow.setText("Pay With HDFC");
                        //tvpaynow.setVisibility(View.VISIBLE);
                    }*/

                } else {
                    Toast.makeText(Fees_Activity.this, "No Records Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Fees> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
            }
        });

    }

    public void findviews() {
        storage = new DataStorage("Login_Detail", Fees_Activity.this);
        tvfeedate = (TextView) findViewById(R.id.tvfeedate);
        tvpendingmsg = (TextView) findViewById(R.id.tvpendingmsg);
        paytm_pay = (TextView) findViewById(R.id.paytm_pay);
        axis_pay = (TextView) findViewById(R.id.axis_pay);
        tvfeetype = (TextView) findViewById(R.id.tvfeetype);
        tvtotalfee = (TextView) findViewById(R.id.tvtotalfee);
        tvpaidfee = (TextView) findViewById(R.id.tvpaidfee);
        tvpendingfee = (TextView) findViewById(R.id.tvpendingfee);
        mainlectlayout = (LinearLayout) findViewById(R.id.mainlectlayout);
        tvpaynow = (TextView) findViewById(R.id.tvpaynow);
        tvpaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvtotalfee.getText().toString().equals("0.0") || !tvtotalfee.getText().toString().equals("0.00") || !tvtotalfee.getText().toString().equals("00.00")) {


//                    if (tvtotalfee.getText().toString().equals("0.0") || tvtotalfee.getText().toString().equals("0.00") || tvtotalfee.getText().toString().equals("00.00")) {
                    final ProgressDialog progressDialog = new ProgressDialog(Fees_Activity.this, R.style.MyTheme1);
                    progressDialog.setCancelable(true);
                    progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    progressDialog.show();
                    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                    Call<ArrayList<InsertData>> call1 = apiService.insert_api_call(String.valueOf(storage.read("stud_id", 3)), String.valueOf(storage.read("stud_admission_no", 3)), String.valueOf(storage.read("ac_id", 3)), String.valueOf(storage.read("swd_year_id", 3)), key);
//                    call1.enqueue(new Callback<ArrayList<InsertData>>() {
//                        @Override
//                        public void onResponse(Call<ArrayList<InsertData>> call, Response<ArrayList<InsertData>> response) {
//                            progressDialog.dismiss();
//                            if (response.isSuccessful()) {
//                               // Toast.makeText(Fees_Activity.this, " Sucess", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ArrayList<InsertData>> call, Throwable t) {
//
//                        }
//                    });

                    Call<Fees> call = apiService.paynow(String.valueOf(storage.read("ac_id", 3)), adminssion_no, tvpendingfee.getText().toString(), String.valueOf(storage.read("name", 3)), String.valueOf(storage.read("Fee_Circular_ID", 3)));
                    call.enqueue(new Callback<Fees>() {
                        @Override
                        public void onResponse(Call<Fees> call, Response<Fees> response) {
                            progressDialog.dismiss();

                            if (response.isSuccessful()) {
                                if (response.body().getstatus().equals("1")) {
                                    Intent intent = new Intent(Fees_Activity.this, FeePayment.class);
                                    intent.putExtra("url", response.body().getresponse());
//                                    Log.d("Url",response.body().getresponse());
                                    startActivity(intent);

                                } else {

                                    Toast.makeText(Fees_Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Fees_Activity.this, "Try Again Later", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Fees> call, Throwable t) {
                            progressDialog.dismiss();
                            // Log error here since request failed
                            //Log.e("employeeattendancerespo", t.toString());
                        }
                    });
                }
                /*else {

                    final ProgressDialog progressDialog = new ProgressDialog(Fees_Activity.this, R.style.MyTheme1);
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    progressDialog.show();
                    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<Fees> call = apiService.Print_Fee_Receipt(String.valueOf(storage.read("stud_id", 3)));

                    call.enqueue(new Callback<Fees>() {
                        @Override
                        public void onResponse(Call<Fees> call, Response<Fees> response) {
                            progressDialog.dismiss();

                            if (response.isSuccessful()) {
                                // Log.d("responseBodyvalue",String.valueOf(response.body()));
                                if (response.body().getstatus().equals("1")) {
                                    //String responseBody=response.body().getbytearr();
                                    // Log.d("responseBody",String.valueOf(response.body().getbytearr()));

                                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Atmiya/" + response.body().getFilename());

                                    if (file.exists()) {

                                        File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Atmiya/" + response.body().getFilename());
                                        Intent target = new Intent(Intent.ACTION_VIEW);
                                        target.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                        target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        Intent intent = Intent.createChooser(target, "Open File");
                                        try {
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException e) {
                                            // Instruct the user to install a PDF reader here, or something
                                        }
                                    } else {
                                        try {
                                            byte data[] = new byte[1024];
                                            long total = 0;
                                            data = response.body().getbytearr().getBytes("UTF-8");
                                            //data = response.body().getbytearr();
                                            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Atmiya");
                                            dir.mkdirs();
                                            //File outputFile = new File(createfile, "Sample.pdf");
//                                      FileOutputStream fos = new FileOutputStream(dir);

                                     *//* //Writing into the PDF File
                                      strByte = (xpp.getText().toString());
                                      bytes = Base64.decode(strByte.toString(),Base64.DEFAULT);//Converting Base64 to byte
                                     *//*

                                            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Atmiya/" + response.body().getFilename();

                                            byte[] pdfAsBytes = Base64.decode(response.body().getbytearr(), 0);
                                            FileOutputStream os;
                                            os = new FileOutputStream(filepath, false);
                                            os.write(pdfAsBytes);
                                            os.flush();
                                            os.close();


                                            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Atmiya/" + response.body().getFilename());
                                            Intent target = new Intent(Intent.ACTION_VIEW);
                                            target.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                            target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            Intent intent = Intent.createChooser(target, "Open File");

                                            try
                                            {
                                                startActivity(intent);
                                            }
                                            catch (ActivityNotFoundException e)
                                            {
                                                // Instruct the user to install a PDF reader here, or something
                                            }
                                      *//*OutputStream pdffos = new FileOutputStream(filepath);
                                      pdffos.write(data);
                                      pdffos.flush();
                                      pdffos.close();*//*

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            // Log.d("erordownloadpdffee", String.valueOf(e));
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(Fees_Activity.this, "Try again later", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Fees> call, Throwable t) {
                            progressDialog.dismiss();
                            //Log error here since request failed
                            //  Log.e("employeeattendancerespo", t.toString());
                        }
                    });
                }*/

            }
        });

        paytm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String paytm_url = "https://paytm.com/education";
                String paytm_url = "m.p-y.tm/atmiyagrp_web";
                String url = "https://paytm.com/education/Gujarat/Rajkot/Atmiya%20Group&utm_medium=smarturl&utm_source=smarturl&utm_term=web&utm_campaign=web";
                Intent paytm = new Intent(Fees_Activity.this, FeePayment.class);
                paytm.putExtra("url", url);
                startActivity(paytm);
            }
        });

        axis_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvtotalfee.getText().toString().equals("0.0") || !tvtotalfee.getText().toString().equals("0.00") || !tvtotalfee.getText().toString().equals("00.00")) {
                    final ProgressDialog progressDialog = new ProgressDialog(Fees_Activity.this, R.style.MyTheme1);
                    progressDialog.setCancelable(true);
                    progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    progressDialog.show();
                    Map<String, String> mParams;
                    mParams = new HashMap<String, String>();
                    mParams.put("school_id", String.valueOf(storage.read("ac_id", 3)));
                    System.out.println("school_id>>>>>>>>>>" + String.valueOf(storage.read("ac_id", 3)));
                    mParams.put("addmission_no", adminssion_no);
                    System.out.println("addmission_no>>>>" + adminssion_no);
                    mParams.put("Stud_Order_id", String.valueOf(storage.read("Fee_Circular_ID", 3)));
                    System.out.println("Stud_Order_id::::::::" + String.valueOf(storage.read("Fee_Circular_ID", 3)));
                    mParams.put("amount", tvpendingfee.getText().toString());
                    System.out.println("amount>>>>" + tvpendingfee.getText().toString());
                    mParams.put("stud_name", String.valueOf(storage.read("name", 3)));
                    System.out.println("stud_name?????" + String.valueOf(storage.read("name", 3)));

                    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<Fees> call = apiService.pay_fees_with_axis(mParams);
                    call.enqueue(new Callback<Fees>() {
                        @Override
                        public void onResponse(Call<Fees> call, Response<Fees> response) {
                            progressDialog.dismiss();

                            if (response.isSuccessful()) {
                                if (response.body().getstatus().equals("1")) {
                                    Intent intent = new Intent(Fees_Activity.this, FeePayment.class);
                                    intent.putExtra("url", response.body().getresponse());
                                    System.out.println("url>>>>>>>>>>>>" + response.body().getresponse());
//                                    Log.d("Url",response.body().getresponse());
                                    startActivity(intent);

                                }
                            } else {
                                Toast.makeText(Fees_Activity.this, "Try Again Later", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Fees> call, Throwable t) {
                            progressDialog.dismiss();
                            // Log error here since request failed
                            //Log.e("employeeattendancerespo", t.toString());
                        }
                    });
                } else {

                }
            }
        });
        tvpendingfeerefund = (TextView) findViewById(R.id.tvpendingfeerefund);
        tvduedate = (TextView) findViewById(R.id.tvduedate);
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

}
