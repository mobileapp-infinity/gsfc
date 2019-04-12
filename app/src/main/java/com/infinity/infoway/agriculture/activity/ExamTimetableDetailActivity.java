package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.ExamTimeTableAdapter;
import com.infinity.infoway.agriculture.adapter.ExamTimeTableDetailAdapter;
import com.infinity.infoway.agriculture.model.ExamDetailPOJO;
import com.infinity.infoway.agriculture.model.ExamTTPojo;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamTimetableDetailActivity extends AppCompatActivity
{
    ListView lv_exam_tt_list_detail;
    ExamTimeTableDetailAdapter examTimeTableDetailAdaptera;
    String ID;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_timetable_detail);
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

        Intent intent = getIntent();
        ID = intent.getStringExtra("EXAM_ID") + "";

        findViews();

        API_call_exam_detail();
    }

    public  void findViews()
    {
        this.lv_exam_tt_list_detail=(ListView)findViewById(R.id.lv_exam_tt_list_detail);
    }

    public void API_call_exam_detail(){
        final ProgressDialog progressDialog = new ProgressDialog(ExamTimetableDetailActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ExamDetailPOJO>> call;
        call = apiInterface.get_exam_time_table_display_detail(ID);

        call.enqueue(new Callback<ArrayList<ExamDetailPOJO>>() {
            @Override
            public void onResponse(Call<ArrayList<ExamDetailPOJO>> call, Response<ArrayList<ExamDetailPOJO>> response)
            {
                //Log.d("Er",response.toString());

                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().size() >= 1)
                    {
                        ArrayList<ExamDetailPOJO> a1 = response.body();
                        examTimeTableDetailAdaptera = new ExamTimeTableDetailAdapter(ExamTimetableDetailActivity.this,a1);
                        lv_exam_tt_list_detail.setAdapter(examTimeTableDetailAdaptera);
                        //   Log.d("Count", String.valueOf(listView.getAdapter().getCount()));


                    } else {
                        Toast.makeText(ExamTimetableDetailActivity.this, "No records found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ExamTimetableDetailActivity.this, "pleasetry again later", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ExamDetailPOJO>> call, Throwable t) {

                progressDialog.dismiss();

                //Log.e("Error",t.toString());
                Toast.makeText(ExamTimetableDetailActivity.this, "Plese try again later", Toast.LENGTH_LONG).show();

            }
        });
    }
}
