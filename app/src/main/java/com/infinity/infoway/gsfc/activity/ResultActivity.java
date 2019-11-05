package com.infinity.infoway.gsfc.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.result_response;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity
{
    RecyclerView result_recycle_view;
    Toolbar toolbar;
    DataStorage storage;
    ResultAdapter resultAdapter;
    Context ctx;
    RecyclerView.LayoutManager layoutManager;
    List<result_response> listMain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        findViews();
        API_call_Result();

    }


    public void findViews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        result_recycle_view = (RecyclerView) findViewById(R.id.result_recycle_view);

    }


    public void API_call_Result()
    {
        final ProgressDialog progressDialog = new ProgressDialog(ResultActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
      //  progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();


        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> maps;
        maps = new HashMap<String, String>();
        maps.put("stud_id",String.valueOf(storage.read("stud_id",3)));
        maps.put("sem_id",String.valueOf(storage.read("sm_id",3)));
        maps.put("div_id",String.valueOf(storage.read("swd_division_id",3)));
        maps.put("year_id",String.valueOf(storage.read("swd_year_id",3)));


        Call<List<result_response>> call = apiService.get_result_student(maps);
        call.enqueue(new Callback<List<result_response>>()
        {
            @Override
            public void onResponse(Call<List<result_response>> call, Response<List<result_response>> response)
            {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body() != null && response.body().size() > 0)
                    {
                        listMain = response.body();
                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        result_recycle_view.setLayoutManager(layoutManager);
                        resultAdapter = new ResultAdapter(getApplicationContext(), listMain);
                        result_recycle_view.setAdapter(resultAdapter);

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ResultActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(ResultActivity.this, "Please try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<result_response>> call, Throwable t) {

            }
        });


    }


    class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

        private List<result_response> result_resp;

        Context ctx;
        DataStorage storage;


        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv_subject_exam, tv_date_result,tv_exam_name;
            RecyclerView iner_rec;
            RecultInnerAdapter result_inner_adapter;

            public MyViewHolder(View view)
            {
                super(view);
                tv_exam_name = (TextView) view.findViewById(R.id.tv_exam_name);
                tv_date_result = (TextView) view.findViewById(R.id.tv_date_result);
                iner_rec = (RecyclerView) view.findViewById(R.id.rec_inner_result);
//
            }


        }

        public ResultAdapter(Context ctx, List<result_response> result_resp) {
            this.ctx = ctx;
            this.result_resp = result_resp;
            storage = new DataStorage("Login_Detail", ctx);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.result_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            result_response result = result_resp.get(position);


            holder.iner_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
            holder.iner_rec.setHasFixedSize(true);
            holder.result_inner_adapter = new RecultInnerAdapter(ctx, result.getSubjectsarrays());
            holder.iner_rec.setAdapter(holder.result_inner_adapter);
            holder.iner_rec.setNestedScrollingEnabled(false);


            holder.tv_exam_name.setText(result.getMain_ex_name());
            holder.tv_date_result.setText(result.getMain_re_date());


        }


        @Override
        public int getItemCount() {
            return result_resp.size();
        }

    }


    public class RecultInnerAdapter extends RecyclerView.Adapter<RecultInnerAdapter.MyViewHolder> {

        List<result_response.Subjects_array> inner_list;
        Context context;
        Dialog dialog;
        result_response.Subjects_array dataTemp;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView sub_result, total_marks_result, obtain_marks_result, weightage_result;

            public MyViewHolder(View view) {
                super(view);

                sub_result = (TextView) view.findViewById(R.id.sub_r1esult);
                total_marks_result = (TextView) view.findViewById(R.id.total_marks_result);
                obtain_marks_result = (TextView) view.findViewById(R.id.obtain_marks_result);
                weightage_result = (TextView) view.findViewById(R.id.weightage_result);

            }
        }


        public RecultInnerAdapter(Context context, List<result_response.Subjects_array> inner_list) {
            this.context = context;
            this.inner_list = inner_list;

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inner_result, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)

        {

            dataTemp = inner_list.get(position);


            holder.sub_result.setText(dataTemp.getSub_name());
            holder.total_marks_result.setText(dataTemp.getSub_tot_mark());
            holder.obtain_marks_result.setText(dataTemp.getSub_obt_mark());
            holder.weightage_result.setText(dataTemp.getSub_weight());


        }


        @Override
        public int getItemCount() {
            return inner_list.size();
        }

    }
}