package com.infinity.infoway.gsfc.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.BatchAdapterCheckBox;
import com.infinity.infoway.gsfc.adapter.BenefitsAdapter;
import com.infinity.infoway.gsfc.adapter.StudentDisplayfillAttendanceAdapter;
import com.infinity.infoway.gsfc.adapter.Teaching_method_adapter;
import com.infinity.infoway.gsfc.adapter.ViewpagerPendingattendance;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.FacultyPendingFragment;
import com.infinity.infoway.gsfc.fragment.FillAttendanceFrg;
import com.infinity.infoway.gsfc.model.AbsentPojo;
import com.infinity.infoway.gsfc.model.AidPojo;
import com.infinity.infoway.gsfc.model.BatchPojo;
import com.infinity.infoway.gsfc.model.FacultyPojo;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;
import com.infinity.infoway.gsfc.model.TeachingMethodPojo;
import com.infinity.infoway.gsfc.model.UnitPoJo;
import com.infinity.infoway.gsfc.model.UpdateAttpojo;

import java.util.ArrayList;
import java.util.List;

public class FillAttendanceActivityfaculty extends AppCompatActivity {

    Toolbar toolbar;
    DataStorage storage;
    RequestQueue queue;
    FacultyPojo.TableBean bean;
    TabLayout tabs;
    ViewPager viewPager;
    private TextView title;

    private EditText edtatendancedate;
    private EditText edtcollegename;
    private EditText edtdeptname;
    private EditText edtcourse;
    private EditText edtsem;
    private EditText edtsub;
    private EditText edtperiodlab;
    private EditText edtresourse;
    private EditText edtdiv;
    private TextView tvsname;
    private RecyclerView batchstudfr1;
    private LinearLayout llbatch;
    private RecyclerView recstudentdisplay;
    private TextView tv1;
    private Spinner spindist;
    private EditText edtattendancestudent;
    private TextView txttotalstud;
    private LinearLayout lltotalstud;
    private TextView txtpresentstud;
    private LinearLayout llpresentstud;
    private TextView txtabsentstud;
    private LinearLayout llabsentstud;
    private LinearLayout lltotalstudents;
    private Spinner spinunit;
    private EditText edttopic;
    private RecyclerView lvteachingmethod;
    private Spinner spinaid;
    private Spinner spinFlinnt;
    private TextView saveattendancetxt;
    ArrayAdapter<String> adapter_AID_spin;
    ArrayAdapter<String> adapter_method;
    ArrayAdapter<String> adapter_flint;
    ArrayAdapter<String> adapter_unit;
    List<String> spin_list_AID;
    public static CheckBox cb_check_fill_attendance;
    List<String> spin_list_AID_ID;
    List<String> spinUnit_name_list;
    List<String> spinUnit_ID_list;
    List<String> spin_list_method;
    StudentDisplayfillAttendanceAdapter studentDisplayfillAttendanceAdapter;
    Teaching_method_adapter teaching_method_adapter;
    RecyclerView batch_stud_fr1;
    String position_flint, position_aid = "0", position_unit = "0";
    public static Boolean b = false;
    ExpandableHeightListView listview;
    EditText edt_homework;
    LinearLayout ll_homework;
    int selected_method;

    String str_home_work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_attendance_activityfaculty);
        this.listview = (ExpandableHeightListView) findViewById(R.id.listview);

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

        //for set non editable data
        setData();

        //for binding teaching aid
        Api_call_display_AID();
        Api_call_Disply_batch_of_student();

        Api_call_student_display();

        flinit_Display_for_alternate_method();

        Alternate_MEthod_Display();


        Api_call_Teaching_method();
        Api_call_display_unit_detail();


        //setupViewPager(viewPager, 0.0);

        //applyFontedTab(FillAttendanceActivityfaculty.this, viewPager, tabs,view);
    }

    public boolean isEmpty() {
        System.out.println("selected_check_IDS ::::::::::::" + Teaching_method_adapter.selected_check_IDS.toString());

        if (edttopic.getText().toString().trim().isEmpty()) {
            DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please Enter Topic Detail");

            return false;
        } else if (position_flint.compareToIgnoreCase("Select No Of Posts on Flinnt") == 0) {
            DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please Select of Posts on Flinnt");
            return false;
        } else if (position_aid.compareToIgnoreCase("0") == 0) {
            DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please Select Teaching Aid");
            return false;
        } else if (Teaching_method_adapter.selected_check_IDS.size() == 0) {
            DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please select at least one method");
            return false;
        }


        return true;
    }

    public void Alterbate_method(String str_home_work) {


//        for (String s : studentDisplayfillAttendanceAdapter.ID_Check_status.keySet()) {
//            System.out.println("key of check uncheck status of check box and stud ID>>>>>>>>" + s);
//
//            System.out.println("value of check uncheck status of check box and stud ID>>>>>>>>" + studentDisplayfillAttendanceAdapter.ID_Check_status.get(s));
//        }


        System.out.println("selected_method==>>>>>>>>>>>>>>>>>> " + selected_method + "");

        // if (selected_method == 1 && edtattendancestudent.getText().toString().trim().isEmpty())
        if (selected_method == 1 && !edtattendancestudent.getText().toString().contentEquals("")) {//Absant


            System.out.println("absent method");
            if (edtattendancestudent.getText().toString().trim().contentEquals("")) {
                DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please Enter Roll Number");
            } else {
                if (isEmpty()) {

                    String selected_ID = Teaching_method_adapter.selected_check_IDS.toString();

                    String split_ID[] = selected_ID.split("\\[");

                    System.out.println("split_ID000:::::::::" + split_ID[0]);
                    System.out.println("split_ID0001111:::::::::" + split_ID[1]);

                    String ID_Zero = split_ID[1];
                    String split_brc[] = ID_Zero.split("]");

                    System.out.println("split_brc::::::::" + split_brc[0]);


                    String Teaching_method_ID = split_brc[0];

                    System.out.println("Teaching_method_ID::::::::::" + Teaching_method_ID);


                    api_call_absent_student_by_alternate_method(Teaching_method_ID, str_home_work);
                }
            }


        } else if (selected_method == 2 && !edtattendancestudent.getText().toString().trim().contentEquals("")) {//present

            System.out.println("present method");
            if (edtattendancestudent.getText().toString().trim().contentEquals("")) {
                DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, "Please Enter Roll Number");
            } else {
                if (isEmpty()) {

                    String selected_ID_present = Teaching_method_adapter.selected_check_IDS.toString();

                    System.out.println("selected_ID  in present::::::::::::" + selected_ID_present);

                    String split_ID_present[] = selected_ID_present.split("\\[");

                    System.out.println("split_ID000 present:::::::::" + split_ID_present[0]);
                    System.out.println("split_ID0001111 present:::::::::" + split_ID_present[1]);

                    String ID_Zero = split_ID_present[1];
                    String split_brc_present[] = ID_Zero.split("]");

                    System.out.println("split_brc::::::::" + split_brc_present[0]);


                    String Teaching_method_ID_present = split_brc_present[0];

                    System.out.println("Teaching_method_ID present::::::::::" + Teaching_method_ID_present);

                    api_call_present_student_by_alternate_method(Teaching_method_ID_present, str_home_work);

                }
            }

        } else if (selected_method == 0 && edtattendancestudent.getText().toString().contentEquals("")) {
            System.out.println("class method");
            if (isEmpty()) {
                String selected_ID_class_wise = Teaching_method_adapter.selected_check_IDS.toString();

                System.out.println("selected_ID  in split_ID_class_wise::::::::::::" + selected_ID_class_wise);

                String split_ID_class_wise[] = selected_ID_class_wise.split("\\[");

                System.out.println("split_ID000 split_ID_class_wise:::::::::" + split_ID_class_wise[0]);
                System.out.println("split_ID0001111 split_ID_class_wise:::::::::" + split_ID_class_wise[1]);

                String ID_Zeroclass_wise = split_ID_class_wise[1];
                String split_brc_class_wise[] = ID_Zeroclass_wise.split("]");

                System.out.println("split_brc_class_wise::::::::" + split_brc_class_wise[0]);


                String Teaching_method_ID_class_wise = split_brc_class_wise[0];


                System.out.println("Teaching_method_ID class_wise::::::::::" + Teaching_method_ID_class_wise);


               /* for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                    // for(int j=0;j< adapter.selected.size();j++)
                    //{
                    if (adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {

                        Api_call_of_class_wise_student_attendance_take(Teaching_method_ID_class_wise, "1", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), str_home_work);
                    } else {
                        Api_call_of_class_wise_student_attendance_take(Teaching_method_ID_class_wise, "0", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), str_home_work);
                    }
                    // }

                }*/
                //  for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                // for(int j=0;j< adapter.selected.size();j++)
                //{

                ArrayList<String> allId;
                allId = new ArrayList<>();
                String PRESENT_ID = "";
                String ABSENT_ID = "";
                for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                    allId.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
                }
                for (int i = 0; i < allId.size(); i++) {
                    if (adapter.selected.contains(allId.get(i)))
                    {
                        PRESENT_ID = PRESENT_ID + "," + allId.get(i) + "";
                    }
                    else{
                        ABSENT_ID = ABSENT_ID + "," + allId.get(i) + "";
                    }
                }
                System.out.println("PRESENT_ID  "+PRESENT_ID+"");
                System.out.println("ABSENT_ID  "+ABSENT_ID+"");




//                if (adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {
//
//                    Api_call_of_class_wise_student_attendance_take(Teaching_method_ID_class_wise, "1", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), str_home_work);
//                } else {
//                    Api_call_of_class_wise_student_attendance_take(Teaching_method_ID_class_wise, "0", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), str_home_work);
//                }
                // }

                //}

            }

        }
    }


    public void Api_call_of_class_wise_student_attendance_take(String Teaching_method_ID_class_wise, String status, String stud_ID, String homework) {


        System.out.println("homework in in class  ::::::::::" + homework);
        String URLs = URl.Insert_isrp_class_wise_attendance_API + "&college_id=" + bean.getCollege_id() + "&stud_id=" + stud_ID + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&att_intime=" + "" + "&att_status=" + status + "&att_topic=" + edttopic.getText().toString() + "&att_teaching_method=" + Teaching_method_ID_class_wise + "&att_aid=" + position_aid + "&no_of_flinnt=" + position_flint + "&prac_the_status=" + bean.getLec_type() + "&att_version_id=" + bean.getDL_VERSION_ID() + "&att_type=" + "By Selection Method Application" + "&att_emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&att_unit_id=" + position_unit + "&att_homework=" + homework + "&att_created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&att_created_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Insert_isrp_class_wise_attendance_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        // System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, absentPojo.getTable().get(0).getError_msg());


                                    Api_call_update_stud_record();

//                                    Intent intent = new Intent(FillAttendanceActivityfaculty.this, FacultyAttendance.class);
//                                    startActivity(intent);
                                    finish();
                                }


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


    public void Api_call_update_stud_record() {
        String URLs = URl.UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API + "&course_id=" + bean.getCourse_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "&att_date=" + bean.getDl_date() + "&lec_type=" + bean.getLec_type() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&sub_id=" + bean.getSub_id() + "&res_id=" + bean.getDL_RECOURSE_ID() + "&version_id=" + bean.getDL_VERSION_ID() + "&modify_by=" + String.valueOf(storage.read("emp_id", 3)) + "&modify_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API RESPONSE      " + response + "");
                        response = "{\"Update\":" + response + "}";


                        System.out.println("THIS IS UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API RESPONSE     " + response + "");
                        if (response.length() >5) {

                            Gson gson = new Gson();

                            UpdateAttpojo updateAttpojo = gson.fromJson(response, UpdateAttpojo.class);
                            if (updateAttpojo != null) {

                                if (updateAttpojo.getUpdate().get(0).getError_code().contentEquals("1")) {
                                    // DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, updateAttpojo.getUpdate().get(0).getError_msg());
                                }


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


    public void api_call_present_student_by_alternate_method(String Teaching_method_ID, String homework)
    {
        System.out.println("homework in present ::::::::::" + homework);
        String URLs = URl.Present_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&att_intime=" + "" + "&att_topic=" + edttopic.getText().toString() + "&att_method=" + Teaching_method_ID + "&att_aid=" + position_aid + "&att_flinnt=" + position_flint + "&roll_no=" + edtattendancestudent.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + position_unit + "&sub_id=" + bean.getSub_id() + "&att_homework=" + homework + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Present_student_record_save calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Present_student_record_save RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Present_student_record_save RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {

                                    DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();

//                                    Intent intent = new Intent(FillAttendanceActivityfaculty.this, FacultyAttendance.class);
//                                    startActivity(intent);
                                    finish();
                                }


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

    public void check_chek_status(final RecyclerView recstudentdisplay, final StudentDisplayfillAttendanceAdapter studentDisplayfillAttendanceAdapter) {
        cb_check_fill_attendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                @Override
                                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                                                    if (isChecked) {
//                                                                        b = true;
//
//
//                                                                        studentDisplayfillAttendanceAdapter.notifyDataSetChanged();
                                                                        if (adapter != null && studentsDisplyaFillPojo != null && studentsDisplyaFillPojo.getTable() != null) {
                                                                            for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                                                                                if (!adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {
                                                                                    adapter.selected.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
                                                                                }
                                                                            }

                                                                            adapter.notifyDataSetChanged();
                                                                        }

                                                                    } else {


                                                                        if (adapter != null && studentsDisplyaFillPojo != null && studentsDisplyaFillPojo.getTable() != null && adapter.selected.size() == studentsDisplyaFillPojo.getTable().size()) {
                                                                            adapter.selected = new ArrayList<>();

                                                                            System.out.println("this is blank!!!!!!!!!!!!!!!!!!!!!");
//                                                                            for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
//                                                                                if (!adapter.selected_name.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {
//                                                                                    adapter.selected_name.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
//                                                                                }
//                                                                            }

                                                                            adapter.notifyDataSetChanged();
                                                                        }
//                                                                        b = false;
//                                                                        System.out.println("value of b In onCheckedChanged false::::::::" + b);
//                                                                        recstudentdisplay.post(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                studentDisplayfillAttendanceAdapter.notifyDataSetChanged();
//                                                                            }
//                                                                        });

                                                                    }
                                                                }
                                                            }
        );
    }

    AbsentPojo absentPojo;

    public void api_call_absent_student_by_alternate_method(String Teaching_method_ID, String homework) {
        System.out.println("homework in absent ::::::::::" + homework);

        String URLs = URl.Absent_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&att_intime=" + "" + "&att_topic=" + edttopic.getText().toString() + "&att_method=" + Teaching_method_ID + "&att_aid=" + position_aid + "&att_flinnt=" + position_flint + "&roll_no=" + edtattendancestudent.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + position_unit + "&sub_id=" + bean.getSub_id() + "&att_homework=" + homework + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Absent_student_record_save calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Absent_student_record_save RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Absent_student_record_save RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();

//                                    Intent intent = new Intent(FillAttendanceActivityfaculty.this, FacultyAttendance.class);
//                                    startActivity(intent);
                                    finish();


                                }


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

    TeachingMethodPojo teachingMethodPojo;


    public void Api_call_Teaching_method() {

        String URLs = URl.Get_Teaching_Method_Details_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_Teaching_Method_Details_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Get_Teaching_Method_Details_API RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Get_Teaching_Method_Details_API RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();

                            teachingMethodPojo = gson.fromJson(response, TeachingMethodPojo.class);
                            if (teachingMethodPojo != null && teachingMethodPojo.getTable().size() > 0) {

                                Teaching_method_adapter.selected_check_IDS = new ArrayList<>();
                                teaching_method_adapter = new Teaching_method_adapter(FillAttendanceActivityfaculty.this, teachingMethodPojo);
                                lvteachingmethod.setLayoutManager(new LinearLayoutManager(FillAttendanceActivityfaculty.this, LinearLayoutManager.HORIZONTAL, false));

                                lvteachingmethod.setAdapter(teaching_method_adapter);


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


    UnitPoJo unitPoJo;

    public void Api_call_display_unit_detail() {
        String URLs = URl.Subject_wise_unit_Detail + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&div_id=" + bean.getDiv_id() + "&sub_id=" + bean.getSub_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Subject_wise_unit_Detail calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Subject_wise_unit_Detail RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Subject_wise_unit_Detail RESPONSE     " + response + "");
                        if (response.length() > 5)

                        {


                            Gson gson = new Gson();

                            spinUnit_name_list = new ArrayList<>();
                            spinUnit_ID_list = new ArrayList<>();
                            unitPoJo = gson.fromJson(response, UnitPoJo.class);
                            spinUnit_ID_list.add(0, "0");
                            spinUnit_name_list.add(0, "Select Unit");
                            if (unitPoJo != null && unitPoJo.getTable().size() > 0) {
                                for (int i = 0; i < unitPoJo.getTable().size(); i++) {
                                    spinUnit_name_list.add(unitPoJo.getTable().get(i).getUnit_name() + "");
                                    spinUnit_ID_list.add(unitPoJo.getTable().get(i).getUnit_id() + "");
                                }

                            }
                            adapter_unit = new ArrayAdapter<String>(FillAttendanceActivityfaculty.this, R.layout.spinner_item, spinUnit_name_list);
                            spinunit.setAdapter(adapter_unit);

                            spinunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    position_unit = spinUnit_ID_list.get(i);
                                    System.out.println("position_unit:::::::::::::::::" + position_unit);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


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

    public void flinit_Display_for_alternate_method() {
        final List<String> Month_list = new ArrayList<>();
        Month_list.add("Select No Of Posts on Flinnt");

        for (int i = 0; i <= 10; i++) {
            Month_list.add(String.valueOf(i));
        }

        adapter_flint = new ArrayAdapter<String>(FillAttendanceActivityfaculty.this, R.layout.spinner_item, Month_list);
        spinFlinnt.setAdapter(adapter_flint);

        //sel_month = Month_list.indexOf(String.valueOf(storage.read("PASSING_MONTH", 3) + ""));

        //spinmonth.setSelection(sel_month+1);
        //spinmonth.setSelection(sel_month);


        spinFlinnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position_flint = Month_list.get(i);
                System.out.println("positin_month In spin_month_oug_ou_pass_gd" + position_flint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Alternate_MEthod_Display() {

        spin_list_method = new ArrayList<>();
        spin_list_method.add(0, "Select Method");
        spin_list_method.add(1, "Absent");
        spin_list_method.add(2, "Present");

        System.out.println("select method array:::::::::::" + spin_list_method.toString());

        adapter_method = new ArrayAdapter<String>(FillAttendanceActivityfaculty.this, R.layout.spinner_item, spin_list_method);
        spindist.setAdapter(adapter_method);

        spindist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_method = i;

                System.out.println("selected_method for alternate method::::::::" + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void findViews() {
        queue = Volley.newRequestQueue(this);
        storage = new DataStorage("Login_Detail", FillAttendanceActivityfaculty.this);

        // tabs = (TabLayout) findViewById(R.id.tabs);
        // viewPager = (ViewPager) findViewById(R.id.vp_fill_attendance);
        Bundle bundle = getIntent().getExtras();
        bean = (FacultyPojo.TableBean) bundle.getSerializable("data");

        this.batch_stud_fr1 = (RecyclerView) findViewById(R.id.batch_stud_fr1);
        this.saveattendancetxt = (TextView) findViewById(R.id.save_attendance_txt);


        //for checking


        this.ll_homework = (LinearLayout) findViewById(R.id.ll_homework);
        this.spinFlinnt = (Spinner) findViewById(R.id.spin_Flinnt);
        this.edt_homework = (EditText) findViewById(R.id.edt_homework);
        this.spinaid = (Spinner) findViewById(R.id.spin_aid);
        this.lvteachingmethod = (RecyclerView) findViewById(R.id.lv_teaching_method);
        this.edttopic = (EditText) findViewById(R.id.edt_topic);
        this.spinunit = (Spinner) findViewById(R.id.spin_unit);
        this.lltotalstudents = (LinearLayout) findViewById(R.id.ll_total_students);
        this.llabsentstud = (LinearLayout) findViewById(R.id.ll_absent_stud);
        this.txtabsentstud = (TextView) findViewById(R.id.txt_absent_stud);
        this.llpresentstud = (LinearLayout) findViewById(R.id.ll_present_stud);
        this.txtpresentstud = (TextView) findViewById(R.id.txt_present_stud);
        this.lltotalstud = (LinearLayout) findViewById(R.id.ll_total_stud);
        this.txttotalstud = (TextView) findViewById(R.id.txt_total_stud);
        this.edtattendancestudent = (EditText) findViewById(R.id.edt_attendance_student);
        this.spindist = (Spinner) findViewById(R.id.spin_dist);
        this.tv1 = (TextView) findViewById(R.id.tv1);
        this.recstudentdisplay = (RecyclerView) findViewById(R.id.rec_student_display);
        this.llbatch = (LinearLayout) findViewById(R.id.ll_batch);
        this.batchstudfr1 = (RecyclerView) findViewById(R.id.batch_stud_fr1);
        this.tvsname = (TextView) findViewById(R.id.tv_sname);
        this.edtdiv = (EditText) findViewById(R.id.edt_div);
        this.edtresourse = (EditText) findViewById(R.id.edt_resourse);
        this.edtperiodlab = (EditText) findViewById(R.id.edt_period_lab);
        this.edtsub = (EditText) findViewById(R.id.edt_sub);
        this.edtsem = (EditText) findViewById(R.id.edt_sem);
        this.edtcourse = (EditText) findViewById(R.id.edt_course);
        this.edtdeptname = (EditText) findViewById(R.id.edt_dept_name);
        this.edtcollegename = (EditText) findViewById(R.id.edt_college_name);
        this.edtatendancedate = (EditText) findViewById(R.id.edt_atendance_date);
        this.title = (TextView) findViewById(R.id.title);
        System.out.println("bean " + bean.getBatch_name() + "");
        System.out.println("date " + bean.getDl_date() + "");


        cb_check_fill_attendance = (CheckBox) findViewById(R.id.cb_check_fill_attendance);
        cb_check_fill_attendance.setChecked(true);


        if (bean.getDl_is_homework().contentEquals("1")) {
            ll_homework.setVisibility(View.VISIBLE);
        } else {
            ll_homework.setVisibility(View.GONE);
        }


        saveattendancetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_home_work = edt_homework.getText().toString();
                System.out.println("str_home_work IN onclick>>>>>>>>>>>>>>>>>" + str_home_work);
                Alterbate_method(str_home_work);


            }
        });
    }


    BatchPojo batchPojo;

    public void Api_call_Disply_batch_of_student() {
        String URLs = URl.Display_batch_student_wise + "&dept_id=" + bean.getDept_id() + "&course_id=" + bean.getCourse_id() + "&sem_id=" + bean.getSm_id() + "&dvm_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Display_batch_student_wise calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Display_batch_student_wise RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Display_batch_student_wise RESPONSE     " + response + "");
                        if (response.length() > 5)

                        {


                            Gson gson = new Gson();


                            batchPojo = gson.fromJson(response, BatchPojo.class);
                            if (batchPojo != null && batchPojo.getTable().size() > 0) {

                                BatchAdapterCheckBox adapter = new BatchAdapterCheckBox(FillAttendanceActivityfaculty.this, batchPojo);
                                batch_stud_fr1.setAdapter(adapter);
                                batch_stud_fr1.setLayoutManager(new LinearLayoutManager(FillAttendanceActivityfaculty.this, LinearLayoutManager.HORIZONTAL, false));
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


    static StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    BenefitsAdapter adapter;

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
                        if (response.length() > 5) {


                            Gson gson = new Gson();

                            studentsDisplyaFillPojo = gson.fromJson(response, StudentsDisplyaFillPojo.class);
                            if (studentsDisplyaFillPojo != null && studentsDisplyaFillPojo.getTable().size() > 0) {

                                if (cb_check_fill_attendance.isChecked() == true) {
                                    b = true;
                                } else {
                                    b = false;
                                }

                                studentDisplayfillAttendanceAdapter = new StudentDisplayfillAttendanceAdapter(FillAttendanceActivityfaculty.this, studentsDisplyaFillPojo, b);
                                recstudentdisplay.setLayoutManager(new LinearLayoutManager(FillAttendanceActivityfaculty.this, LinearLayoutManager.VERTICAL, false));

                                recstudentdisplay.setAdapter(studentDisplayfillAttendanceAdapter);
                                listview.setExpanded(true);

                                adapter = new BenefitsAdapter(studentsDisplyaFillPojo, FillAttendanceActivityfaculty.this);
                                listview.setAdapter(adapter);

                                for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                                    if (!adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {
                                        adapter.selected.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
                                    }
                                }

                                refresh();
                                adapter.notifyDataSetChanged();

                                check_chek_status(recstudentdisplay, studentDisplayfillAttendanceAdapter);
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

    AidPojo aidPojo;

    public void Api_call_display_AID() {
        String URLs = URl.Get_Teaching_Aid_Detail_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Display_batch_student_wise calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Display_batch_student_wise RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";

                        System.out.println("THIS IS Display_batch_student_wise RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            spin_list_AID = new ArrayList<>();
                            spin_list_AID_ID = new ArrayList<>();
                            spin_list_AID.add(0, "Select Aid");
                            spin_list_AID_ID.add(0, "0");
                            Gson gson = new Gson();


                            aidPojo = gson.fromJson(response, AidPojo.class);
                            if (aidPojo != null && aidPojo.getTable().size() > 0) {


                                for (int i = 0; i < aidPojo.getTable().size(); i++) {
                                    spin_list_AID_ID.add(aidPojo.getTable().get(i).getTa_id() + "");
                                    spin_list_AID.add(aidPojo.getTable().get(i).getTa_name() + "");
                                }

                            }

                            System.out.println("spin_list_AID_ID:::::::::" + spin_list_AID_ID.size() + ">>>>>>>>>>>>>>>>>>>> " + spin_list_AID_ID.toString());
                            System.out.println("spin_list_AID:::::::::" + spin_list_AID.size() + ">>>>>>>>>>>>>> " + spin_list_AID.toString());
                            adapter_AID_spin = new ArrayAdapter<String>(FillAttendanceActivityfaculty.this, R.layout.spinner_item, spin_list_AID);
                            spinaid.setAdapter(adapter_AID_spin);

                            spinaid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    System.out.println("position spinaid onItemSelected::::::::::" + i);
                                    System.out.println("SELECT spin_list_AID_ID:::::::::" + spin_list_AID_ID.size() + ">>>>>>>>>>>>>>>>>>>> " + spin_list_AID_ID.toString());
                                    System.out.println("SELECT spin_list_AID:::::::::" + spin_list_AID.size() + ">>>>>>>>>>>>>> " + spin_list_AID.toString());

                                    position_aid = spin_list_AID_ID.get(i) + "";
                                    System.out.println("position_aid by selected::::" + position_aid);
                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

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

    public void setData() {
        edtatendancedate.setText(bean.getDl_date() + "");
        edtcollegename.setText(bean.getCollege_name() + "");
        edtdeptname.setText(bean.getDepartment() + "");
        edtcourse.setText(bean.getCourse_name() + "");
        edtsem.setText(bean.getSemester_name() + "");
        edtsub.setText(bean.getSub_name() + "");
        edtperiodlab.setText(bean.getLec_no() + "");
        edtresourse.setText(bean.getResourse_name() + "");
        edtdiv.setText(bean.getDivision_name() + "");

    }

    private void setupViewPager(ViewPager viewPager, Double progress) {
        ViewpagerPendingattendance adapter = new ViewpagerPendingattendance(getSupportFragmentManager());

        adapter.addFragment(new FacultyPendingFragment(bean), " " + "PendingAttendance" + " ");
        adapter.addFragment(new FillAttendanceFrg(bean), " " + "FillAttendance" + "  ");
        viewPager.setAdapter(adapter);
    }

    public static void refresh() {

        if (studentsDisplyaFillPojo != null && BenefitsAdapter.selected != null) {
            if (studentsDisplyaFillPojo.getTable().size() == BenefitsAdapter.selected.size()) {
                cb_check_fill_attendance.setChecked(true);


            } else {
                cb_check_fill_attendance.setChecked(false);
            }
        }
    }
}
