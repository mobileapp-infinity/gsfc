package com.infinity.infoway.gsfc.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.FacultyPendingAttendanceAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.FacultyPojo;

public class FacultyAttendance extends AppCompatActivity {
    Toolbar toolbar;
    ListView lv_faculty_attendance;
    DataStorage storage;

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_faculty_attendance);


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

        Api_call_Faculty_Pending_Attendance_bind();


    }

    public void findViews() {
        queue = Volley.newRequestQueue(this);

        storage = new DataStorage("Login_Detail", FacultyAttendance.this);

        lv_faculty_attendance = (ListView) findViewById(R.id.lv_faculty_attendance);
    }

    FacultyPojo facultyPojo;

    public void Api_call_Faculty_Pending_Attendance_bind()
    {

        String URLs = URl.faculty_bind_api + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("faculty_bind_api calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS faculty_bind_api RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS faculty_bind_api RESPONSE     " + response + "");
                        if (response.length() > 10)
                        {


                            Gson gson = new Gson();


                            facultyPojo = gson.fromJson(response, FacultyPojo.class);
                            if (facultyPojo != null && facultyPojo.getTable().size() > 0)
                            {

                                FacultyPendingAttendanceAdapter adapter = new FacultyPendingAttendanceAdapter(FacultyAttendance.this, facultyPojo);
                                lv_faculty_attendance.setAdapter(adapter);
                            }
                            else
                            {
                                DialogUtils.Show_Toast(FacultyAttendance.this,"No Records Found");
                            }

                        }

                        else
                        {
                            DialogUtils.Show_Toast(FacultyAttendance.this,"No Records Found");
                        }
                    }
                }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }
}
