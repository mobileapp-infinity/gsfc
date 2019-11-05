package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.LecturePlanEmployee;
import com.infinity.infoway.gsfc.model.LectureplanEmployeeSubTopics;
import com.infinity.infoway.gsfc.model.Methods;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lectureplan_Detail extends AppCompatActivity
{

    int positiontopic;
    RecyclerView listView;
    // ListView listView;
    int subtopics;
    ArrayList<LecturePlanEmployee> lectureplanlist;
    ArrayList<String> a1;
    CustomAdapter adapter;
    ArrayList<String> lecturestatic;
    DataStorage storage;
    ArrayList<Methods> sub;
    ArrayList<String> a12;
    Context ctx;
    TextView tvcoursename, tvsemester, tvdivision, tvsubjectname, tvLecture_Per_Week, refbook1;
    TextView t1;
    String name;
    ArrayList<Methods> lec1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letureplan_detail);
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

        t1 = (TextView) findViewById(R.id.txtd);
        storage = new DataStorage("Login_Detail", Lectureplan_Detail.this);
        a12 = new ArrayList<String>();

        tvcoursename = (TextView) findViewById(R.id.tvcoursename);
        tvsemester = (TextView) findViewById(R.id.tvsemester);
        tvdivision = (TextView) findViewById(R.id.tvdivision);
        tvsubjectname = (TextView) findViewById(R.id.tvsubjectname);
        tvLecture_Per_Week = (TextView) findViewById(R.id.tvLecture_Per_Week);
        refbook1 = (TextView) findViewById(R.id.refbook);

        listView = (RecyclerView) findViewById(R.id.listview);
        a1 = new ArrayList<String>();
        a12 = new ArrayList<String>();
        lecturestatic = new ArrayList<>();

        Intent b = getIntent();
        positiontopic = b.getIntExtra("position", 0);

        ArrayList<? extends LecturePlanEmployee> testing1 = b.getParcelableArrayListExtra("Name");

        String coursename = testing1.get(positiontopic).getCourse_Name();
        String semster = testing1.get(positiontopic).getSemester();
        String div = testing1.get(positiontopic).getdivision();
        String subname = testing1.get(positiontopic).getSubject();
        String lecture = testing1.get(positiontopic).getLecture_Per_Week();
        String refbook = testing1.get(positiontopic).getref_book_name();


        tvcoursename.setText(coursename);
        tvsemester.setText(semster);
        tvdivision.setText(div);
        tvsubjectname.setText(subname);
        tvLecture_Per_Week.setText(lecture + " lectures per week");
        refbook1.setText(refbook);

        ArrayList<? extends LectureplanEmployeeSubTopics> testing = b.getParcelableArrayListExtra("Topic");

        //String name=testing.get(i).gettopic_Name();
        for (int i = 0; i < testing.size(); i++)
        {
            a1.add("Sr.no:- " + testing.get(i).getSrno());
            a1.add("Aid:- " + testing.get(i).getaid());
            a1.add("Topic:- " + testing.get(i).gettopic_Name());
        }

        final ProgressDialog progressDialog = new ProgressDialog(Lectureplan_Detail.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        //progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<LecturePlanEmployee>> call;
        if (storage.CheckLogin("emp_id", Lectureplan_Detail.this))
        {
            call = apiservice.getEmpLecturePlan(String.valueOf(storage.read("emp_id", 3)));
        }
        else
            {
            call = apiservice.getStudentLecturePlan(String.valueOf(storage.read("stud_id", 3)));
        }

        // Log.d("Emp_id", String.valueOf(storage.read("emp_id", 3)));
        call.enqueue(new Callback<ArrayList<LecturePlanEmployee>>()
        {

            @Override
            public void onResponse(Call<ArrayList<LecturePlanEmployee>> call, Response<ArrayList<LecturePlanEmployee>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body().size() >= 1)
                    {
                        lectureplanlist = response.body();
                        Log.d("lectplandetail", String.valueOf(lectureplanlist));
/*
                        for (int i = 0; i < lectureplanlist.size(); i++)
                        {

                        }*/
                        ArrayList<LectureplanEmployeeSubTopics> lec1 = lectureplanlist.get(positiontopic).getdetails();
                        adapter = new CustomAdapter(lec1);
                        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        listView.setAdapter(adapter);
                        listView.setNestedScrollingEnabled(false);
                    }
                    else
                        {
                        Toast.makeText(Lectureplan_Detail.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    //  Toast.makeText(Lectureplan_Detail.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LecturePlanEmployee>> call, Throwable t)
            {
                progressDialog.dismiss();
                // Log.e("Error1", t.toString());
                // Toast.makeText(Lectureplan_Detail.this, "Error in Response", Toast.LENGTH_LONG).show();
            }
        });

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

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
    {
        ArrayList<LectureplanEmployeeSubTopics> a1;

        CustomAdapter(ArrayList<LectureplanEmployeeSubTopics> a1)
        {
            this.a1 = a1;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_lectureplan_topic, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.srno.setText(a1.get(position).getSrno());
            holder.topicname.setText(a1.get(position).gettopic_Name());
            holder.aidname.setText(a1.get(position).getaid());


            ArrayList<Methods> m1 = a1.get(position).getmethods();

            TextView methood = new TextView(Lectureplan_Detail.this);
            methood.setTextColor(getResources().getColor(R.color.fees));
            methood.setPadding(5, 0, 0, 0);

            // tvtopics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_forward, 0, 0, 0);
            // tvtopics.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_arrow_forward),null,null,null);
            //   tvtopics.setText("Topic Name: "+a1.get(position).gettopic_Name());
            //  tvadd.setText("Aid: "+a1.get(position).getaid());
            // tvsrno.setText("Sr No: "+a1.get(position).getSrno());
            for (int i = 0; i < m1.size(); i++)
            {
                methood.setText("Method -> " + m1.get(i).getmethod_name());
            }

            holder.lltopics.addView(methood);

        }

        @Override
        public int getItemCount()
        {
            return a1.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;

            TextView srno, topicname, aidname;
            LinearLayout lltopics;

            ViewHolder(View view)
            {
                super(view);
                mView = view;
                lltopics = (LinearLayout) mView.findViewById(R.id.linear11);
                srno = (TextView) mView.findViewById(R.id.txtsrno);
                topicname = (TextView) mView.findViewById(R.id.txttopicname);
                aidname = (TextView) mView.findViewById(R.id.txtaid);

            }

        }


    }


}
