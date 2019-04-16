package com.infinity.infoway.agriculture.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.agriculture.CommonCls.CustomEditText;
import com.infinity.infoway.agriculture.CommonCls.CustomTextView;
import com.infinity.infoway.agriculture.CommonCls.URl;
import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.BenefitsAdapter;
import com.infinity.infoway.agriculture.adapter.StudentDisplayfillAttendanceAdapter;
import com.infinity.infoway.agriculture.adapter.StudentListAdapter;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.FacultyPojo;
import com.infinity.infoway.agriculture.model.StudentsDisplyaFillPojo;

public class EmployeeFillAttendanceActivity extends AppCompatActivity {
    DataStorage storage;
    RequestQueue queue;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView title;
    private android.support.v7.widget.Toolbar toolbar;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView tvcoursename;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView tvlectname;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView tvclassname;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView tvdeptname;
    private com.infinity.infoway.agriculture.CommonCls.CustomTextView tvsubname;
    private android.widget.ImageView ivdownarrow;
    private android.widget.LinearLayout llmethod1;
    private ExpandableHeightListView lvexpanstud;
    private android.widget.ImageView ivdownarrowmethod2;
    private android.widget.LinearLayout llmethod2;
    private com.infinity.infoway.agriculture.CommonCls.CustomEditText edtrollno;

    private android.widget.LinearLayout llfillatt;
    private android.widget.Spinner spintopic;
    private android.widget.RelativeLayout relbottom;
    private android.widget.RelativeLayout llmain;
    private android.widget.Switch cbcheckfillattendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_fill_attendance);
        this.cbcheckfillattendance = (Switch) findViewById(R.id.cb_check_fill_attendance);
        this.llmain = (RelativeLayout) findViewById(R.id.ll_main);
        this.relbottom = (RelativeLayout) findViewById(R.id.rel_bottom);
        this.spintopic = (Spinner) findViewById(R.id.spin_topic);
        this.llfillatt = (LinearLayout) findViewById(R.id.ll_fill_att);
        this.llmethod2 = (LinearLayout) findViewById(R.id.ll_method_2);
        this.edtrollno = (CustomEditText) findViewById(R.id.edt_roll_no);
        this.llmethod2 = (LinearLayout) findViewById(R.id.ll_method2);
        this.ivdownarrowmethod2 = (ImageView) findViewById(R.id.iv_down_arrow_method2);
        this.lvexpanstud = (ExpandableHeightListView) findViewById(R.id.lv_expan_stud);
        this.llmethod1 = (LinearLayout) findViewById(R.id.ll_method1);
        this.ivdownarrow = (ImageView) findViewById(R.id.iv_down_arrow);
        this.tvsubname = (CustomTextView) findViewById(R.id.tv_sub_name);
        this.tvdeptname = (CustomTextView) findViewById(R.id.tv_dept_name);
        this.tvclassname = (CustomTextView) findViewById(R.id.tv_class_name);
        this.tvlectname = (CustomTextView) findViewById(R.id.tv_lect_name);
        this.tvcoursename = (CustomTextView) findViewById(R.id.tv_course_name);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);

        Bundle bundle = getIntent().getExtras();
        bean = (FacultyPojo.TableBean) bundle.getSerializable("data");
        queue = Volley.newRequestQueue(this);
        storage = new DataStorage("Login_Detail", EmployeeFillAttendanceActivity.this);
        lvexpanstud.setExpanded(true);
    }

    static StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    BenefitsAdapter adapter;
    FacultyPojo.TableBean bean;
    public static Boolean b = false;

    public void Api_call_student_display() {
        String URLs = URl.StudentsDisplay_fill_attendance + "&batch_id=" + bean.getBatch_id() + "&div_id=" + bean.getDiv_id() + "&sem_id=" + bean.getSm_id() + "&lect_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&sub_id=" + bean.getSub_id() + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("StudentsDisplay_fill_attendance calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE     " + response + "");
                        if (response.length() > 1) {


                            Gson gson = new Gson();

                            studentsDisplyaFillPojo = gson.fromJson(response, StudentsDisplyaFillPojo.class);
                            if (studentsDisplyaFillPojo != null && studentsDisplyaFillPojo.getTable().size() > 0) {

                                if (cbcheckfillattendance.isChecked() == true) {
                                    b = true;
                                } else {
                                    b = false;
                                }

                              //  StudentListAdapter studentDisplayfillAttendanceAdapter = new StudentDisplayfillAttendanceAdapter(EmployeeFillAttendanceActivity.this, studentsDisplyaFillPojo, b);
                                StudentListAdapter studentDisplayfillAttendanceAdapter = new StudentListAdapter(EmployeeFillAttendanceActivity.this, studentsDisplyaFillPojo, b);
                                //    recstudentdisplay.setLayoutManager(new LinearLayoutManager(EmployeeFillAttendanceActivity.this, LinearLayoutManager.VERTICAL, false));

                                //     recstudentdisplay.setAdapter(studentDisplayfillAttendanceAdapter);
                                lvexpanstud.setExpanded(true);

                                adapter = new BenefitsAdapter(studentsDisplyaFillPojo, EmployeeFillAttendanceActivity.this);
                                lvexpanstud.setAdapter(adapter);

                                for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                                    if (!adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {
                                        adapter.selected.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
                                    }
                                }

                         //       refresh();
                                adapter.notifyDataSetChanged();

                            //ppppppppppppppppppp    check_chek_status(recstudentdisplay, studentDisplayfillAttendanceAdapter);
                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }
}
