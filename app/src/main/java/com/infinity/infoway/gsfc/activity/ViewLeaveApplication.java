package com.infinity.infoway.gsfc.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.ViewLeaveAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.DisplayLeave;

public class ViewLeaveApplication extends AppCompatActivity
{
    private CustomTextView title;
    private Toolbar toolbar;
    private ListView list;
    private FloatingActionButton fab;
    private RelativeLayout llelerning;
    Context ctx;
    DataStorage storage;
    RequestQueue queue;
    CustomBoldTextView tv_no_records_leave;
    SwipeRefreshLayout swipe_refresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_leave_application);

        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(ViewLeaveApplication.this);
        this.llelerning = (RelativeLayout) findViewById(R.id.ll_elerning);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.tv_no_records_leave = (CustomBoldTextView) findViewById(R.id.tv_no_records_leave);
        this.list = (ListView) findViewById(R.id.list);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);
        ctx = getApplicationContext();
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
        swipe_refresh=  (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                shuffle();
                swipe_refresh.setRefreshing(false);
            }
        });

        Display_leave_API_CAll();
//        ViewLeaveAdapter adapter = new ViewLeaveAdapter(ctx);
//        list.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ViewLeaveApplication.this, LeaveStudentApply.class);
                startActivity(i);
            }
        });
    }

    public void shuffle()
    {
        Display_leave_API_CAll();
    }
    private void Display_leave_API_CAll()
    {
        DialogUtils.showProgressDialog(ViewLeaveApplication.this, "");

        String URLs = URl.get_student_leave_application_data_API + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) + "&year_id=" + String.valueOf(storage.read("swd_year_id", 3)) + "";


        URLs = URLs.replace(" ", "%20");
        System.out.println("get_student_leave_application_data_API calls faculty   " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";

                        System.out.println("THIS IS get_student_leave_application_data_API RESPONSE    " + response + "");

                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            DisplayLeave displayLeave = gson.fromJson(response,DisplayLeave.class);

                            if (displayLeave != null && displayLeave.getTable().size() > 0)

                            {
                                tv_no_records_leave.setVisibility(View.GONE);
                                ViewLeaveAdapter viewLeaveAdapter = new ViewLeaveAdapter(ViewLeaveApplication.this, displayLeave);

                                list.setAdapter(viewLeaveAdapter);


                            }
                            else
                            {
                                tv_no_records_leave.setVisibility(View.VISIBLE);
                                //DialogUtils.Show_Toast(ViewLeaveApplication.this,"No Records Found");
                            }


                        }
                        else
                            {
                                tv_no_records_leave.setVisibility(View.VISIBLE);
                             // DialogUtils.Show_Toast(ViewLeaveApplication.this,"No Records Found");
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }
}
