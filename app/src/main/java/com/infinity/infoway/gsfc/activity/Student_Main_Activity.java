package com.infinity.infoway.gsfc.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.Response_Activity;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_Main_Activity extends AppCompatActivity
{
    Toolbar toolbar;
    DataStorage storage;
    Context ctx;
    RecyclerView act_list;
    TableLayout tableLayout;
    ActivityAdapter activityAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Response_Activity> listMain;
    String string = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__main_);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);

            }
        });


        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });


        findViews();
        Student_Activities_api_call();

    }

    public void findViews()
    {
        ctx = this;
        act_list = (RecyclerView) findViewById(R.id.listactivity);
        storage = new DataStorage("Login_Detail", ctx);

    }

    public void Student_Activities_api_call()
    {

//        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please Wait");
//        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Response_Activity>> call = apiService.get_student_activity_list_temp(String.valueOf(storage.read("stud_id", 3)),String.valueOf(storage.read("im_domain_name",3)));
        call.enqueue(new Callback<List<Response_Activity>>()
        {
            @Override
            public void onResponse(Call<List<Response_Activity>> call, Response<List<Response_Activity>> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null && response.body().size() > 0)
                    {
                        listMain = response.body();
                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        act_list.setLayoutManager(layoutManager);
                        activityAdapter = new ActivityAdapter(getApplicationContext(), listMain);
                        act_list.setAdapter(activityAdapter);

                    }
                    else
                        {
                        Toast.makeText(Student_Main_Activity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    Toast.makeText(Student_Main_Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Response_Activity>> call, Throwable t)
            {

            }
        });


    }

    public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder>
    {


        private List<Response_Activity> moviesList;
        private Context context;


        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView date_act, desc_act;
            RecyclerView horizontal_rec;
            StudActivity_horizontal_rec horizontalAdapter;

            public MyViewHolder(View view)
            {
                super(view);
                date_act = (TextView) view.findViewById(R.id.date_activities);
                desc_act = (TextView) view.findViewById(R.id.description_activities);
                horizontal_rec = (RecyclerView) view.findViewById(R.id.horizontal_rec);


            }
        }


        public ActivityAdapter(Context context, List<Response_Activity> moviesList)
        {
            this.context = context;
            this.moviesList = moviesList;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_list_act, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Response_Activity movie = moviesList.get(position);

            holder.horizontal_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
            holder.horizontal_rec.setHasFixedSize(true);
            holder.horizontalAdapter = new StudActivity_horizontal_rec(context, movie.getActivityFile());
            holder.horizontal_rec.setAdapter(holder.horizontalAdapter);
            holder.horizontal_rec.setNestedScrollingEnabled(false);

            holder.desc_act.setText(movie.getDaDescription());
            holder.date_act.setText(movie.getActivityDate());

        }

        @Override
        public int getItemCount()
        {
            return moviesList.size();
        }
    }


    public class StudActivity_horizontal_rec extends RecyclerView.Adapter<StudActivity_horizontal_rec.MyViewHolder>
    {

        List<Response_Activity.ActivityFile> moviesList;
        Context context;
        Dialog dialog;
        Response_Activity.ActivityFile dataTemp;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img_horizontal;

            public MyViewHolder(View view) {
                super(view);
                img_horizontal = (ImageView) view.findViewById(R.id.horizontal_img);

            }
        }


        public StudActivity_horizontal_rec(Context context, List<Response_Activity.ActivityFile> moviesList) {
            this.context = context;
            this.moviesList = moviesList;

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_rec_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            dataTemp = moviesList.get(position);
            if (!TextUtils.isEmpty(dataTemp.getActivityFile()))
            {
                string = dataTemp.getActivityFile();

                Glide.with(context).load(string).into(holder.img_horizontal);
                holder.img_horizontal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog = new Dialog(Student_Main_Activity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.show();


                        dialog.setContentView(R.layout.act_popup);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.img1_popup);

                        Glide.with(context).load(moviesList.get(position).getActivityFile()).fitCenter().error(R.drawable.no_image).into(imageView);
                        Log.d("final_img", string);
                        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                        lp.dimAmount = 0f;
                        dialog.getWindow().setAttributes(lp);
                        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }

}
