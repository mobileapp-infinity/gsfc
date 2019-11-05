package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.ExamTimeTableAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.ExamTTPojo;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamDisplayTimeTable extends AppCompatActivity {
    ListView lv_exam_tt_list;
    Toolbar toolbar;
    DataStorage storage;
    ExamTimeTableAdapter examTimeTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_display_time_table);
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


        findViews();

        API_Call_exam_display();
    }

    public void findViews() {
        storage = new DataStorage("Login_Detail", this);
        lv_exam_tt_list = (ListView) findViewById(R.id.lv_exam_tt_list);
    }

    public void API_Call_exam_display()
    {
        final ProgressDialog progressDialog = new ProgressDialog(ExamDisplayTimeTable.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ExamTTPojo>> call;
        call = apiInterface.get_exam_time_table_display(String.valueOf(storage.read("stud_id", 3)), String.valueOf(storage.read("swd_year_id", 3)));

        call.enqueue(new Callback<ArrayList<ExamTTPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ExamTTPojo>> call, Response<ArrayList<ExamTTPojo>> response) {
                //Log.d("Er",response.toString());

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().size() >= 1) {
                        ArrayList<ExamTTPojo> a1 = response.body();
                        examTimeTableAdapter = new ExamTimeTableAdapter(ExamDisplayTimeTable.this,a1);
                        lv_exam_tt_list.setAdapter(examTimeTableAdapter);
                        //   Log.d("Count", String.valueOf(listView.getAdapter().getCount()));


                    } else {
                        Toast.makeText(ExamDisplayTimeTable.this, "No records found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ExamDisplayTimeTable.this, "please try again later", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ExamTTPojo>> call, Throwable t) {

                progressDialog.dismiss();

                //Log.e("Error",t.toString());
                Toast.makeText(ExamDisplayTimeTable.this, "Plese try again later", Toast.LENGTH_LONG).show();

            }
        });

    }
}
