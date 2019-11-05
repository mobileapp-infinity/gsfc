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
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.AssignmentViewAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.AssignView;

public class AssignmentEmployeeActivity extends AppCompatActivity {

    private CustomTextView title;
    private Toolbar toolbar;
    private ListView lvassignmentfact;

    RequestQueue queue;
    DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_employee);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
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
        INIT();

        Assignment_API_call();

    }

    private void Assignment_API_call()
    {
        DialogUtils.showProgressDialog(AssignmentEmployeeActivity.this, "");


        String URLs = URl.get_employee_wise_student_assignment_API + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&user_id=" + String.valueOf(storage.read("emp_id", 3)) + "&status=" + "0" + "";


        URLs = URLs.replace(" ", "%20");
        System.out.println("get_notification_new calls faculty   " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Assignment\":" + response + "}";
                        System.out.println("THIS IS get_employee_wise_student_assignment_API RESPONSE    faculty  " + response + "");


                        if (response.length() > 10)
                        {
                            Gson gson = new Gson();

                            AssignView assignView = gson.fromJson(response, AssignView.class);

                            if (assignView != null && assignView.getAssignment().size() > 0)

                            {


                                AssignmentViewAdapter assignmentViewAdapter = new AssignmentViewAdapter(AssignmentEmployeeActivity.this, AssignmentEmployeeActivity.this, assignView);

                                lvassignmentfact.setAdapter(assignmentViewAdapter);


                            }

                            else
                            {
                                DialogUtils.Show_Toast(AssignmentEmployeeActivity.this,"No Records Found");
                            }
                        }

                        else
                        {
                            DialogUtils.Show_Toast(AssignmentEmployeeActivity.this,"No Records Found");
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

    private void INIT() {
        queue = Volley.newRequestQueue(AssignmentEmployeeActivity.this);
        storage = new DataStorage("Login_Detail", this);
        this.lvassignmentfact = (ListView) findViewById(R.id.lv_assignment_fact);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);
    }
}
