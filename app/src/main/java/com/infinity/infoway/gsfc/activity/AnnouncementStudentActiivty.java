package com.infinity.infoway.gsfc.activity;

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
import com.infinity.infoway.gsfc.adapter.AnnuoncementAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.Login_Master;
import com.infinity.infoway.gsfc.model.AnnouncePojo;

public class AnnouncementStudentActiivty extends AppCompatActivity {

    private CustomTextView title;
    private Toolbar toolbar;
    private ImageView ivdownarrowmethod2;
    private LinearLayout llmethod2;
    private ExpandableHeightListView lvannouncementstud;
    private LinearLayout llfillatt;
    private LinearLayout llmain;
    private LinearLayout lll_lv;
    CustomTextView tv_title_ann;
    Login_Master login_master;

    DataStorage storage;
    RequestQueue queue;

    LinearLayout ll_no_records;
    Boolean b =false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_assignment_student_actiivty);
        setContentView(R.layout.activity_annocment);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        INITVIEWS();
        ANNOUNCEMENT();

        /*llmethod2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (lll_lv.getVisibility() == View.VISIBLE)
                {
                    lll_lv.setVisibility(View.GONE);
                } else {
                    lll_lv.setVisibility(View.VISIBLE);
                }
            }
        });*/
        /*llmethod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lll_lv.getVisibility() == View.VISIBLE) {
                    //   llmethod2inner.setVisibility(View.GONE);
                } else {
                    //    llmethod2inner.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }


    AnnouncePojo announcePojo;


    private void ANNOUNCEMENT()
    {
        DialogUtils.showProgressDialog(AnnouncementStudentActiivty.this, "");


        String URLs;

//        if (storage.CheckLogin("stud_id", AnnouncementStudentActiivty.this))
//        {
            //tv_title_ann.setText("Announcement For Student");
            URLs = URl.get_notification_new + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&notification_for=" + "2" + "&notif_college_id=" + String.valueOf(storage.read("ac_id", 3)) + "&notif_dept_id=" + String.valueOf(storage.read("dm_id", 3)) + "&notif_course_id=" + String.valueOf(storage.read("course_id", 3)) + "&notif_sem_id=" + String.valueOf(storage.read("sm_id", 3)) + "";

      /*  }
        else
            {
            tv_title_ann.setText("Announcement For Employee");
             b=true;
            URLs = URl.get_notification_new + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&notification_for=" + "1" + "&notif_college_id=" + String.valueOf(storage.read("emp_permenant_college", 3)) + "&notif_dept_id=" + String.valueOf(storage.read("emp_department_id", 3)) + "&notif_course_id=" + "0" + "&notif_sem_id=" + "0" + "";

        }*/


        URLs = URLs.replace(" ", "%20");
        System.out.println("get_notification_new calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Noty\":" + response + "}";
                        System.out.println("THIS IS get_notification_new RESPONSE      " + response + "");
                        Gson gson = new Gson();

                        announcePojo = gson.fromJson(response, AnnouncePojo.class);


                            llmethod2.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {

                                    if (announcePojo.getNoty().size()>0)
                                    {
                                        if (lll_lv.getVisibility() == View.VISIBLE)
                                        {
                                            lll_lv.setVisibility(View.GONE);
                                        } else {
                                            lll_lv.setVisibility(View.VISIBLE);
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


                        if (response.length() > 10)
                        {

                            if (announcePojo != null && announcePojo.getNoty().size() > 0)

                            {

                                lvannouncementstud.setExpanded(true);
                                AnnuoncementAdapter ad = new AnnuoncementAdapter(AnnouncementStudentActiivty.this, AnnouncementStudentActiivty.this,announcePojo, true, new AnnuoncementAdapter.managePostClick()
                                {
                                    @Override
                                    public void managepostClick(int postion)
                                    {

                                    }

                                    @Override
                                    public void manageEditClick(int postion)
                                    {

                                    }

                                    @Override
                                    public void manageDeleteClick(int postion)
                                    {

                                    }
                                });


                                lvannouncementstud.setAdapter(ad);
                                if (ad !=null)
                                {
                                    String num = String.valueOf(lvannouncementstud.getAdapter().getCount());
                                    login_master.write("Number", num);
                                    int count_stud_announcement = lvannouncementstud.getAdapter().getCount();
                                    login_master.write("count_stud_announcement", count_stud_announcement);
                                    login_master.write("seen", "1");

                                }


                            }
                            else
                            {
                                ll_no_records.setVisibility(View.VISIBLE);

                                System.out.println("call @@@@@@");
                                lvannouncementstud.setExpanded(true);
                                AnnuoncementAdapter ad = new AnnuoncementAdapter(AnnouncementStudentActiivty.this, AnnouncementStudentActiivty.this,announcePojo, true, new AnnuoncementAdapter.managePostClick()
                                {
                                    @Override
                                    public void managepostClick(int postion)
                                    {

                                    }

                                    @Override
                                    public void manageEditClick(int postion)
                                    {

                                    }

                                    @Override
                                    public void manageDeleteClick(int postion)
                                    {

                                    }
                                });


                                lvannouncementstud.setAdapter(ad);

                              //  DialogUtils.Show_Toast(AnnouncementStudentActiivty.this,"No Records Found");
                            }

                        }
                        else
                        {

                            ll_no_records.setVisibility(View.VISIBLE);
                            System.out.println("call *************");
                            lvannouncementstud.setExpanded(true);
                            AnnuoncementAdapter ad = new AnnuoncementAdapter(AnnouncementStudentActiivty.this, AnnouncementStudentActiivty.this,announcePojo, true, new AnnuoncementAdapter.managePostClick()
                            {
                                @Override
                                public void managepostClick(int postion)
                                {

                                }

                                @Override
                                public void manageEditClick(int postion)
                                {

                                }

                                @Override
                                public void manageDeleteClick(int postion)
                                {

                                }
                            });


                            lvannouncementstud.setAdapter(ad);

                            // DialogUtils.Show_Toast(AnnouncementStudentActiivty.this,"No Records Found");
                        }
//                        else
//                        {
//                            DialogUtils.Show_Toast(FacultyAttendance.this,"No Pending Attendance");
//                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    private void INITVIEWS()
    {
        ll_no_records =(LinearLayout)findViewById(R.id.ll_no_records);
        storage = new DataStorage("Login_Detail", this);
        login_master = new Login_Master("Login_Master", this);
        queue = Volley.newRequestQueue(this);

        this.tv_title_ann =(CustomTextView)findViewById(R.id.tv_title_ann);
        this.llmain = (LinearLayout) findViewById(R.id.ll_main);
        this.lll_lv = (LinearLayout) findViewById(R.id.lll_lv);
        this.llfillatt = (LinearLayout) findViewById(R.id.ll_fill_att);
        this.lvannouncementstud = (ExpandableHeightListView) findViewById(R.id.lv_announcement_stud);
        this.llmethod2 = (LinearLayout) findViewById(R.id.ll_method2);
        this.ivdownarrowmethod2 = (ImageView) findViewById(R.id.iv_down_arrow_method2);
        this.title = (CustomTextView) findViewById(R.id.title);


    }
}
