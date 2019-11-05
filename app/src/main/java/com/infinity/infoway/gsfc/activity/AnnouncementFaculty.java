package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.infinity.infoway.gsfc.adapter.AnnouncementFacultAdapter;
import com.infinity.infoway.gsfc.adapter.AnnuoncementAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.Login_Master;
import com.infinity.infoway.gsfc.model.AnnouncePojo;

public class AnnouncementFaculty extends AppCompatActivity {

    private CustomTextView title;
    private Toolbar toolbar;
    private CustomTextView tvtitleann;
    private ImageView ivdownarrowmethod2;
    private LinearLayout llfaculty;
    private ExpandableHeightListView lvannouncementfact;
    private LinearLayout lllvfaculty;
    private CustomTextView tvtitleannstud;
    private ImageView ivdownarrowmethod3;
    private LinearLayout llstud;
    private ExpandableHeightListView lvannouncementstud;
    private LinearLayout llstudann;
    private LinearLayout llfillatt;
    private LinearLayout llmain;
    DataStorage storage;
    RequestQueue queue;
    Login_Master login_master;
    LinearLayout ll_no_records, ll_no_records_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_faculty);
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
        init();
        AnoouncementFaculty();
        AnnouncementStudent();

    }

    AnnouncePojo announcePojo;

    private void AnoouncementFaculty() {
        DialogUtils.showProgressDialog(AnnouncementFaculty.this, "");

        String notify_clg_ID = "";

        if (notify_clg_ID == null || notify_clg_ID.compareToIgnoreCase("null") == 0 || notify_clg_ID.contentEquals("")) {
            notify_clg_ID = "0";
        } else {
            notify_clg_ID = String.valueOf(storage.read("emp_permenant_college", 3));
        }
        String URLs = URl.get_notification_new + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&notification_for=" + "1" + "&notif_college_id=" + notify_clg_ID + "&notif_dept_id=" + String.valueOf(storage.read("emp_department_id", 3)) + "&notif_course_id=" + "0" + "&notif_sem_id=" + "0" + "";


        URLs = URLs.replace(" ", "%20");
        System.out.println("get_notification_new calls faculty   " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Noty\":" + response + "}";
                        System.out.println("THIS IS get_notification_new RESPONSE    faculty  " + response + "");
                        Gson gson = new Gson();
                        announcePojo = gson.fromJson(response, AnnouncePojo.class);

                        llfaculty.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if (announcePojo.getNoty().size() > 0)
                                {
                                    if (lllvfaculty.getVisibility() == View.VISIBLE)
                                    {
                                        lllvfaculty.setVisibility(View.GONE);
                                    } else {
                                        lllvfaculty.setVisibility(View.VISIBLE);
                                    }
                                }
                                else
                                    {
                                    if (ll_no_records.getVisibility() == View.VISIBLE)
                                    {
                                        ll_no_records.setVisibility(View.GONE);
                                    }
                                    else
                                        {
                                        ll_no_records.setVisibility(View.VISIBLE);
                                    }
                                }

                            }
                        });
                        if (announcePojo.getNoty().size() == 0)
                        {
                            ll_no_records.setVisibility(View.VISIBLE);
                        }
                        else
                            {
                            ll_no_records.setVisibility(View.GONE);
                        }
                        if (response.length() > 10)
                        {


                            if (announcePojo != null && announcePojo.getNoty().size() > 0)
                            {

                                lvannouncementfact.setExpanded(true);

                                AnnouncementFacultAdapter announcementFacultAdapter = new AnnouncementFacultAdapter(AnnouncementFaculty.this, announcePojo, AnnouncementFaculty.this);
                               /* AnnuoncementAdapter ad = new AnnuoncementAdapter(AnnouncementFaculty.this, announcePojo, true, new AnnuoncementAdapter.managePostClick() {
                                    @Override
                                    public void managepostClick(int postion) {

                                    }

                                    @Override
                                    public void manageEditClick(int postion) {

                                    }

                                    @Override
                                    public void manageDeleteClick(int postion) {

                                    }
                                });*/
                                lvannouncementfact.setAdapter(announcementFacultAdapter);

                                if (announcementFacultAdapter != null)
                                {
                                    int count_emp_ann_emp = lvannouncementfact.getAdapter().getCount();
                                    System.out.println("count_emp_ann_emp::::" + count_emp_ann_emp);
                                    login_master.write("count_emp_ann_emp:::::", count_emp_ann_emp);

                                }


                            }
                            else
                                {
//                                DialogUtils.Show_Toast(AnnouncementFaculty.this, "No Records Found");
                            }
                        }
                        else
                            {
//                            DialogUtils.Show_Toast(AnnouncementFaculty.this, "No Records Found");
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

    private void AnnouncementStudent()
    {
        final ProgressDialog progressDialog = new ProgressDialog(AnnouncementFaculty.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
       // progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        //DialogUtils.showProgressDialog(AnnouncementFaculty.this, "");

        //******** only when display student announcement in faculty login ::: take para from login***********
        // sem_id =0
        //notif_course_id = 0
        //clg_id = emp_permenant_college
        //notif_dept_id = emp_department_id

        String notify_clg_ID = "";

        if (notify_clg_ID == null || notify_clg_ID.compareToIgnoreCase("null") == 0 || notify_clg_ID.contentEquals(""))
        {
            notify_clg_ID = "0";
        }
        else
            {
            notify_clg_ID = String.valueOf(storage.read("emp_permenant_college", 3));
        }

        String URLs = URl.get_notification_new + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&notification_for=" + "2" + "&notif_college_id=" + notify_clg_ID + "&notif_dept_id=" + String.valueOf(storage.read("emp_department_id", 3)) + "&notif_course_id=" + "0" + "&notif_sem_id=" + "0" + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("get_notification_new calls Student   " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        // DialogUtils.hideProgressDialog();
                        response = response + "";

                       /* if (response.length()>5)
                        {

                        }*/
                        response = "{\"Noty\":" + response + "}";
                        System.out.println("THIS IS get_notification_new RESPONSE    Student  " + response + "");

                        Gson gson = new Gson();

                        announcePojo = gson.fromJson(response, AnnouncePojo.class);

                        if (announcePojo.getNoty().size() > 0)
                        {
                            llstud.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    if (llstudann.getVisibility() == View.VISIBLE)
                                    {
                                        llstudann.setVisibility(View.GONE);
                                    }
                                    else
                                        {
                                        llstudann.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            llstud.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (ll_no_records_.getVisibility() == View.VISIBLE) {
                                        ll_no_records_.setVisibility(View.GONE);
                                    } else {
                                        ll_no_records_.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                        if (announcePojo.getNoty().size() == 0) {
                            ll_no_records_.setVisibility(View.VISIBLE);
                        } else {
                            ll_no_records_.setVisibility(View.GONE);
                        }


                        if (response.length() > 10) {


                            if (announcePojo != null && announcePojo.getNoty().size() > 0) {

                                lvannouncementstud.setExpanded(true);
                                AnnuoncementAdapter ad = new AnnuoncementAdapter(AnnouncementFaculty.this, AnnouncementFaculty.this, announcePojo, true, new AnnuoncementAdapter.managePostClick() {
                                    @Override
                                    public void managepostClick(int postion) {

                                    }

                                    @Override
                                    public void manageEditClick(int postion) {

                                    }

                                    @Override
                                    public void manageDeleteClick(int postion) {

                                    }
                                });
                                lvannouncementstud.setAdapter(ad);

                                if (ad != null) {
                                    int count_stud_ann_emp = lvannouncementstud.getAdapter().getCount();
                                    System.out.println("count_stud_ann_emp::::" + count_stud_ann_emp);
                                    login_master.write("count_stud_ann_emp", count_stud_ann_emp);

                                }


                            }


                        }

//                        else
//                        {
//                            DialogUtils.Show_Toast(FacultyAttendance.this,"No Pending Attendance");
//                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    private void init() {

        storage = new DataStorage("Login_Detail", AnnouncementFaculty.this);
        login_master = new Login_Master("Login_Master", this);
        queue = Volley.newRequestQueue(AnnouncementFaculty.this);

        this.ll_no_records = (LinearLayout) findViewById(R.id.ll_no_records);
        this.ll_no_records_ = (LinearLayout) findViewById(R.id.ll_no_records_);
        this.llmain = (LinearLayout) findViewById(R.id.ll_main);
        this.llfillatt = (LinearLayout) findViewById(R.id.ll_fill_att);
        this.llstudann = (LinearLayout) findViewById(R.id.ll_stud_ann);
        this.lvannouncementstud = (ExpandableHeightListView) findViewById(R.id.lv_announcement_stud);
        this.llstud = (LinearLayout) findViewById(R.id.ll_stud);
        this.ivdownarrowmethod3 = (ImageView) findViewById(R.id.iv_down_arrow_method3);
        this.tvtitleannstud = (CustomTextView) findViewById(R.id.tv_title_ann_stud);
        this.lllvfaculty = (LinearLayout) findViewById(R.id.ll_lv_faculty);
        this.lvannouncementfact = (ExpandableHeightListView) findViewById(R.id.lv_announcement_fact);
        this.llfaculty = (LinearLayout) findViewById(R.id.ll_faculty);
        this.ivdownarrowmethod2 = (ImageView) findViewById(R.id.iv_down_arrow_method2);
        this.tvtitleann = (CustomTextView) findViewById(R.id.tv_title_ann);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);

        /*llfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lllvfaculty.getVisibility() == View.VISIBLE)
                {
                    lllvfaculty.setVisibility(View.VISIBLE);
                } else {
                    lllvfaculty.setVisibility(View.VISIBLE);
                }
            }
        });*/

   /*     llstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llstudann.getVisibility() == View.VISIBLE) {
                    llstudann.setVisibility(View.VISIBLE);
                } else {
                    llstudann.setVisibility(View.VISIBLE);
                }
            }
        });
    }*/


    }
}
