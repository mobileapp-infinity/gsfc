package com.infinity.infoway.gsfc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomButton;
import com.infinity.infoway.gsfc.CommonCls.CustomEditText;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.ExpandableHeightGridView;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.BenefitsAdapter;
import com.infinity.infoway.gsfc.adapter.StudentListAdapter;
import com.infinity.infoway.gsfc.adapter.Teaching_method_adapter_expandable;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.AbsentPojo;
import com.infinity.infoway.gsfc.model.AidPojo;
import com.infinity.infoway.gsfc.model.CheckAteendancePojo;
import com.infinity.infoway.gsfc.model.ConfigurationAttPojo;
import com.infinity.infoway.gsfc.model.FacultyPojo;
import com.infinity.infoway.gsfc.model.MethodgetPojo;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;
import com.infinity.infoway.gsfc.model.TeachingMethodPojo;
import com.infinity.infoway.gsfc.model.TopicPojo;
import com.infinity.infoway.gsfc.model.UnitPoJo;
import com.infinity.infoway.gsfc.model.UpdateAttpojo;


import java.util.ArrayList;
import java.util.List;

public class EmployeeFillAttendanceActivity_configuration extends AppCompatActivity {

    public static Switch cbcheckfillattendance;
    public static Boolean b = false;
    public static int DISPLAY_FIELD = 1;//all
    static StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    public CheckBox check_enrollent;
    public CheckBox check_name_student;
    DataStorage storage;
    RequestQueue queue;
    String[] namesNH = new String[0];
    TopicPojo topic, topicTemp;
    RelativeLayout rl_bottom_ll;
    String METHOD_TOPIC_NAME, METHOD_TOPIC_METHOD, METHOD_TOPIC_AID, METHOD_UNIT_ID;
    /*pragna*/
    static Spinner spin_aid, spin_unit, spin_flint, spin_topic_configuration_2;
    String position_flint = "0", position_aid = "0", position_unit = "0";
    static CustomEditText ed_topic;
    //    unit
    List<String> spinUnit_name_list = new ArrayList<>();
    List<String> spinUnit_ID_list = new ArrayList<>();
    String TOPIC_ID = "";
    String TOPIC_NAME = "";
    static BenefitsAdapter adapter;
    static FacultyPojo.TableBean bean;
    StudentListAdapter studentDisplayfillAttendanceAdapter;
    AlertDialog show;
    String ID_Check;
    TeachingMethodPojo teachingMethodPojo;
    Teaching_method_adapter_expandable teaching_method_adapter;
    MethodgetPojo methodgetPojo;
    AbsentPojo absentPojo;
    String strDate = "";
    /*23-dec-Pragna for configuration attendance */
    CheckAteendancePojo checkAteendancePojo;
    AidPojo aidPojo;
    ArrayAdapter<String> adapter_AID_spin;
    List<String> spin_list_AID = new ArrayList<>();
    List<String> spin_list_AID_ID = new ArrayList<>();
    UnitPoJo unitPoJo;
    ArrayAdapter<String> adapter_unit;
    ArrayAdapter<String> adapter_flint;
    //    ExpandableHeightListView lvteachingmethod;
    ConfigurationAttPojo configurationAttPojo;
    static int CONFIGURATION = 0;
    String str_ed_topic = "";
    private CustomTextView title;
    private Toolbar toolbar;
    private CustomTextView tvcoursename;
    private CustomTextView tvlectname;
    private CustomTextView tvclassname;
    private CustomTextView tvdeptname;
    private CustomTextView tvsubname;
    private ImageView ivdownarrow;
    private LinearLayout llmethod1, ll_method1inner, ll_method_2_inner;
    // 2=>name hide
    // 3=>enroll hide
    // 4=> name enroll both hide

    //    public static CheckBox cb_check_fill_attendance;
    private ExpandableHeightListView lvexpanstud;
    static private ExpandableHeightGridView lv_teaching_method_att;//pragna
    private ImageView ivdownarrowmethod2;
    private LinearLayout llmethod2;
    private CustomEditText edtrollno;
    static private LinearLayout llfillatt, lin_config, ll_aid_parent, ll_topic;
    static private Spinner spintopic;
    private RelativeLayout relbottom;
    private RelativeLayout llmain;
    private CustomTextView namestudent;
    private CustomTextView enroll;
    private LinearLayout headerlin;
    static private LinearLayout llmethod1inner, ll_teching_method;
    private LinearLayout llmethod21;
    private LinearLayout llmethod2inner;
    private CustomBoldTextView tvcolumn;
    private CustomBoldTextView tvsave;
    private Switch switchmethod;
    static LinearLayout ll_topic_configuration_2;



    public static void refresh()
    {

        if (studentsDisplyaFillPojo != null && BenefitsAdapter.selected != null)
        {
            if (studentsDisplyaFillPojo.getTable().size() == BenefitsAdapter.selected.size())
            {
                cbcheckfillattendance.setChecked(true);

            }
            else
                {
                cbcheckfillattendance.setChecked(false);

            }
            /*all controls must be not visible if all students are absent 03 jan 2020 (NIrali)*/

            if (adapter.selected != null)
            {
                if (adapter.selected.size() == 0)
                {
                    System.out.println("call 1 refresh size is zero ");

                    if (CONFIGURATION == 1)
                    {
                        System.out.println("call 2 CONFIGURATION 1 refresh size is zero visible");
//                        lin_config.setVisibility(View.GONE);


                        if (bean.getLec_type().contentEquals("1")) {/**theory type*/
                           /* lv_teaching_method_att.setVisibility(View.GONE);
                            ll_aid_parent.setVisibility(View.GONE);
                            ll_teching_method.setVisibility(View.GONE);
                            ed_topic.setVisibility(View.GONE);
                            spin_topic_configuration_2.setVisibility(View.GONE);*/
                            lin_config.setVisibility(View.GONE);

                        } else {/**2=practical type*/
                            lin_config.setVisibility(View.GONE);
                            //  lv_teaching_method_att.setVisibility(View.GONE);
                          /*  ll_teching_method.setVisibility(View.GONE);
                            ll_aid_parent.setVisibility(View.GONE);
                            ed_topic.setVisibility(View.GONE);
                            spin_topic_configuration_2.setVisibility(View.GONE);*/
                        }


                    }

                    if (CONFIGURATION == 2) {
                        System.out.println("call 3 CONFIGURATION 2 refresh size is zero visible");
                        ll_topic_configuration_2.setVisibility(View.GONE);
                        spintopic.setVisibility(View.GONE);
                    }


                } else {

                    if (CONFIGURATION == 1) {//then dropdown of unit and topic will textbox

                        lin_config.setVisibility(View.VISIBLE);
                        System.out.println("this is visible!!!!!!!!!!!!!!!!!");
                        spintopic.setVisibility(View.GONE);
                        if (bean.getLec_type().contentEquals("1")) {/**theory type*/
                            lv_teaching_method_att.setVisibility(View.VISIBLE);
                            ll_aid_parent.setVisibility(View.VISIBLE);
                            ll_teching_method.setVisibility(View.VISIBLE);
                            ed_topic.setVisibility(View.VISIBLE);
                            spin_topic_configuration_2.setVisibility(View.GONE);


                        } else {/**2=practical type*/
                            lin_config.setVisibility(View.VISIBLE);
                            //  lv_teaching_method_att.setVisibility(View.GONE);
                            ll_teching_method.setVisibility(View.GONE);
                            ll_aid_parent.setVisibility(View.GONE);
                            ed_topic.setVisibility(View.VISIBLE);
                            spin_topic_configuration_2.setVisibility(View.GONE);
                        }


                    }

                    if (CONFIGURATION == 2) {//then topic is dropdown and not compulsory

                        System.out.println("this will gone!!!!!!!!!");
                        lin_config.setVisibility(View.GONE);
                        spintopic.setVisibility(View.VISIBLE);
                        ll_topic.setVisibility(View.VISIBLE);

                        //   ed_topic.setVisibility(View.GONE);
                        //     spin_topic_configuration_2.setVisibility(View.VISIBLE);


                    }



                   /* if (CONFIGURATION == 1) {
                        System.out.println("call 3 CONFIGURATION 1 refresh size is not zer0zero invisible");
                        lin_config.setVisibility(View.VISIBLE);
                    }
                    if (CONFIGURATION == 2) {
                        System.out.println("call 3 CONFIGURATION 2 refresh size is not zer0zero invisible");
                        ll_topic_configuration_2.setVisibility(View.VISIBLE);
                        spintopic.setVisibility(View.VISIBLE);
                    }*/
                }

            }

        }

        System.out.println("student pojo size in refresh !!!!!!!!!!!!!!! " + studentsDisplyaFillPojo.getTable().size());
        System.out.println("SELECTED ID OF STUDENT LIST refresh !!!!!!!!!! " + adapter.selected.size());


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_fill_attendance_nirali);
        this.switchmethod = (Switch) findViewById(R.id.switch_method);
        this.tvsave = (CustomBoldTextView) findViewById(R.id.tv_save);
        this.tvcolumn = (CustomBoldTextView) findViewById(R.id.tv_column);
        System.out.println("EmployeeFillAttendanceActivity!!!!!!!!!!>>>>>>>>>>>>> ");
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

        studentsDisplyaFillPojo = new StudentsDisplyaFillPojo();
        adapter.selected = new ArrayList<>();


        this.rl_bottom_ll = (RelativeLayout) findViewById(R.id.rl_bottom_ll);
        this.llmethod2inner = (LinearLayout) findViewById(R.id.ll_method_2_inner);
        this.llmethod21 = (LinearLayout) findViewById(R.id.ll_method_21);
        this.llmethod1inner = (LinearLayout) findViewById(R.id.ll_method1inner);
        this.ll_teching_method = (LinearLayout) findViewById(R.id.ll_teching_method);
        this.headerlin = (LinearLayout) findViewById(R.id.header_lin);
        this.enroll = (CustomTextView) findViewById(R.id.enroll);
        this.ll_aid_parent = (LinearLayout) findViewById(R.id.ll_aid_parent);
        this.namestudent = (CustomTextView) findViewById(R.id.name_student);
        this.cbcheckfillattendance = (Switch) findViewById(R.id.cb_check_fill_attendance);
        this.llmain = (RelativeLayout) findViewById(R.id.ll_main);
        this.relbottom = (RelativeLayout) findViewById(R.id.rel_bottom);
        this.spintopic = (Spinner) findViewById(R.id.spin_topic);
        this.llfillatt = (LinearLayout) findViewById(R.id.ll_fill_att);
        this.lin_config = (LinearLayout) findViewById(R.id.lin_config);
        this.ll_topic = (LinearLayout) findViewById(R.id.ll_topic);

        this.llmethod2 = (LinearLayout) findViewById(R.id.ll_method_21);
        this.edtrollno = (CustomEditText) findViewById(R.id.edt_roll_no);
        this.llmethod2 = (LinearLayout) findViewById(R.id.ll_method2);
        this.ivdownarrowmethod2 = (ImageView) findViewById(R.id.iv_down_arrow_method2);
        this.lvexpanstud = (ExpandableHeightListView) findViewById(R.id.lv_expan_stud);
        this.lv_teaching_method_att = (ExpandableHeightGridView) findViewById(R.id.lv_teaching_method_att);
        this.llmethod1 = (LinearLayout) findViewById(R.id.ll_method1);
        this.ll_method1inner = (LinearLayout) findViewById(R.id.ll_method1inner);
        this.ll_method_2_inner = (LinearLayout) findViewById(R.id.ll_method_2_inner);
        this.ivdownarrow = (ImageView) findViewById(R.id.iv_down_arrow);
        this.tvsubname = (CustomTextView) findViewById(R.id.tv_sub_name);
        this.tvdeptname = (CustomTextView) findViewById(R.id.tv_dept_name);
        this.tvclassname = (CustomTextView) findViewById(R.id.tv_class_name);
        this.tvlectname = (CustomTextView) findViewById(R.id.tv_lect_name);
        this.tvcoursename = (CustomTextView) findViewById(R.id.tv_course_name);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);


        /*        pragna*/
        this.spin_aid = (Spinner) findViewById(R.id.spin_aid);
        this.spin_unit = (Spinner) findViewById(R.id.spin_unit);
        this.spin_flint = (Spinner) findViewById(R.id.spin_flint);
        this.ed_topic = (CustomEditText) findViewById(R.id.ed_topic);
        this.spin_topic_configuration_2 = (Spinner) findViewById(R.id.spin_topic_configuration_2);
//        lvteachingmethod=(ExpandableHeightListView)findViewById(R.id.lv_teaching_method_att);
        lv_teaching_method_att.setExpanded(true);

        //  ll_method_2_inner.setVisibility(View.GONE);
//        ll_method1inner.setVisibility(View.GONE);




        edtrollno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                System.out.println("after text !!!!!!!!!!");
                if (CONFIGURATION == 1) {//then dropdown of unit and topic will textbox

                    lin_config.setVisibility(View.VISIBLE);
                    System.out.println("this is visible afterTextChanged!!!!!!!!!!!!!!!!!");
                    spintopic.setVisibility(View.GONE);
                    if (bean.getLec_type().contentEquals("1")) {/**theory type*/
                        lv_teaching_method_att.setVisibility(View.VISIBLE);
                        ll_aid_parent.setVisibility(View.VISIBLE);
                        ll_teching_method.setVisibility(View.VISIBLE);
                        ed_topic.setVisibility(View.VISIBLE);
                        spin_topic_configuration_2.setVisibility(View.GONE);


                    } else {/**2=practical type*/
                        lin_config.setVisibility(View.VISIBLE);
                        //  lv_teaching_method_att.setVisibility(View.GONE);
                        ll_teching_method.setVisibility(View.GONE);
                        ll_aid_parent.setVisibility(View.GONE);
                        ed_topic.setVisibility(View.VISIBLE);
                        spin_topic_configuration_2.setVisibility(View.GONE);
                    }


                }

                if (CONFIGURATION == 2) {//then topic is dropdown and not compulsory

                    System.out.println("this will gone!!!!!!!!!");
                    lin_config.setVisibility(View.GONE);
                    spintopic.setVisibility(View.VISIBLE);
                    ll_topic.setVisibility(View.VISIBLE);

                    //   ed_topic.setVisibility(View.GONE);
                    //     spin_topic_configuration_2.setVisibility(View.VISIBLE);


                }
            }
        });



        llmethod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_method1inner.getVisibility() == View.VISIBLE) {
                    ll_method1inner.setVisibility(View.GONE);
                } else {
                    ll_method1inner.setVisibility(View.VISIBLE);
                }
            }
        });
        llmethod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_method_2_inner.getVisibility() == View.VISIBLE) {
                    ll_method_2_inner.setVisibility(View.GONE);
                } else {
                    ll_method_2_inner.setVisibility(View.VISIBLE);
                }
            }
        });
        tvcolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog4YNo(EmployeeFillAttendanceActivity_configuration.this, "", "", new DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {

                    }
                }, new DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
            }
        });
        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if all students are absent then no need to check validation and no need to enter any other parameters*/


                System.out.println("selected IDS of student !!!!!!!!!!!!!!!!" + adapter.selected.toString());
                System.out.println("selected IDS of student SIZE !!!!!!!!!!!!!!!!" + adapter.selected.size());
                if (adapter.selected != null && adapter.selected.size() == 0 && TextUtils.isEmpty(edtrollno.getText().toString()) && edtrollno.getText().toString().contentEquals("")) {

                    CheckAttendanceExistOrNot();
                } else {


                    if (validation()) {
                        /* CHECK ATTENDANCE FILLED OR NOT  :::: NIRALI::: 26 DEC 2019*/

                        CheckAttendanceExistOrNot();

                    }
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        bean = (FacultyPojo.TableBean) bundle.getSerializable("data");
        tvcoursename.setText(bean.getCourse_name() + " (" + bean.getLecture_name() + ") (" + bean.getResourse_name() + ") " + bean.getDepartment() + "");
        tvsubname.setText(bean.getSub_name() + "");

        queue = Volley.newRequestQueue(this);
        storage = new DataStorage("Login_Detail", EmployeeFillAttendanceActivity_configuration.this);
        lvexpanstud.setExpanded(true);
        Api_call_student_display();
        Api_topic();
        set_layout();
        flinit_Display_for_alternate_method();
        /*23-dec PRAGNA for configration API changes*/
        Api_call_Configuration_AttedanceDisplay();
        //for binding teaching aid
        Api_call_display_AID();
        Api_call_display_unit_detail();
        Api_call_Teaching_method();
    }

    private void set_layout() {

        if (DISPLAY_FIELD == 1) {
            enroll.setVisibility(View.VISIBLE);
            namestudent.setVisibility(View.VISIBLE);
            headerlin.setWeightSum(4.2f);
            // headerlin.setWeightSum(4.4f);
        } else if (DISPLAY_FIELD == 2) {
//            namestudent.setVisibility(View.GONE);
            namestudent.setVisibility(View.VISIBLE);
            enroll.setVisibility(View.GONE);
            headerlin.setWeightSum(2.7f);
        } else if (DISPLAY_FIELD == 3) {
            enroll.setVisibility(View.VISIBLE);
            namestudent.setVisibility(View.GONE);
            headerlin.setWeightSum(2.7f);
        } else if (DISPLAY_FIELD == 4) {
            namestudent.setVisibility(View.GONE);
            enroll.setVisibility(View.GONE);
            headerlin.setWeightSum(1.2f);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void Api_topic()
    {
        String URLs = URl.Get_Lesson_Planning_Topic_Detail_Subject_and_Faculty_Wise_API + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&div_id=" + bean.getDiv_id() + "&sub_id=" + bean.getSub_id() + "" + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3));
        URLs = URLs.replace(" ", "%20");
        System.out.println("Api_topic calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  namesNH = new String[0];
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("Api_topic      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        // System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();

                            topic = gson.fromJson(response, TopicPojo.class);
//                            namesNH = new String[topic.getTable().size()];
                            //  namesNH[0] = "Select Topic";
                            List<TopicPojo.TableBean> listbean = new ArrayList<>();
                            topicTemp = new TopicPojo();
                            TopicPojo.TableBean bean = new TopicPojo.TableBean();
                            bean.setTopic_id("");
                            bean.setTopic_name("Select Topic");

                            listbean.add(bean);
                            // topicTemp.setTable(listbean);
                            for (int i = 0; i < topic.getTable().size(); i++) {


                                bean = new TopicPojo.TableBean();
                                bean.setTopic_id(topic.getTable().get(i).getTopic_id() + "");
                                bean.setTopic_name(topic.getTable().get(i).getTopic_name() + "");

                                listbean.add(bean);

                            }
                            topicTemp.setTable(listbean);
                            namesNH = new String[topicTemp.getTable().size()];
                            //  for (int i = 0; i < topic.getTable().size(); i++) {
                            for (int i = 0; i < topicTemp.getTable().size(); i++) {
//                                namesNH[i + 1] = topic.getTable().get(i).getTopic_name() + "";


                                namesNH[i] = topicTemp.getTable().get(i).getTopic_name() + "";
//                    namesNH[i] = states.getState().get(i).getState_name() + "";
                                //  System.out.println("alreadyselected_state states.getState().get(i).getState_id() " + states.getState().get(i).getState_id() + "");
//                    if (alreadyselected_state.compareToIgnoreCase(states.getState().get(i).getState_id()) == 0) {
//                        toselectedpos_State = i;
//                    }
                            }
                            // if (topic != null && topic.getTable().size() > 0) {

                            ArrayAdapter arrayAdapter1 = new ArrayAdapter(EmployeeFillAttendanceActivity_configuration.this, R.layout.spinner_adapter, namesNH);
                            spintopic.setAdapter(arrayAdapter1);
                            spin_topic_configuration_2.setAdapter(arrayAdapter1);


                            spintopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {
                                    // if (ii > 0) {
                                    TOPIC_ID = topicTemp.getTable().get(ii).getTopic_id() + "";
//                                        TOPIC_ID =namesNH[ii+1] + "";

                                    System.out.println("TOPIC ID :::::::::::::::::::: " + TOPIC_ID + "");
//                                        TOPIC_NAME = topic.getTable().get(ii+1).getTopic_name() + "";
                                    TOPIC_NAME = topicTemp.getTable().get(ii).getTopic_name() + "";
                                    // }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            spin_topic_configuration_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {
                                    // if (ii > 0) {
                                    TOPIC_ID = topicTemp.getTable().get(ii).getTopic_id() + "";
//                                        TOPIC_ID =namesNH[ii+1] + "";

                                    System.out.println("TOPIC ID :::::::::::::::::::: " + TOPIC_ID + "");
//                                        TOPIC_NAME = topic.getTable().get(ii+1).getTopic_name() + "";
                                    TOPIC_NAME = topicTemp.getTable().get(ii).getTopic_name() + "";
                                    // }

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

    public void Api_call_student_display() {
        DialogUtils.showProgressDialog(EmployeeFillAttendanceActivity_configuration.this, "");
        String URLs = URl.StudentsDisplay_fill_attendance + "&batch_id=" + bean.getBatch_id() + "&div_id=" + bean.getDiv_id() + "&sem_id=" + bean.getSm_id() + "&lect_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&sub_id=" + bean.getSub_id() + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("StudentsDisplay_fill_attendance calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        llfillatt.setVisibility(View.VISIBLE);
                        rl_bottom_ll.setVisibility(View.VISIBLE);
                        response = response + "";
                        System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        //System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();

                            studentsDisplyaFillPojo = gson.fromJson(response, StudentsDisplyaFillPojo.class);
                            if (studentsDisplyaFillPojo != null && studentsDisplyaFillPojo.getTable().size() > 0) {

                                if (cbcheckfillattendance.isChecked() == true) {
                                    b = true;
                                } else {
                                    b = false;
                                }


                                studentDisplayfillAttendanceAdapter = new StudentListAdapter(EmployeeFillAttendanceActivity_configuration.this, studentsDisplyaFillPojo, b);


                                lvexpanstud.setExpanded(true);

                                adapter = new BenefitsAdapter(studentsDisplyaFillPojo, EmployeeFillAttendanceActivity_configuration.this);
                                lvexpanstud.setAdapter(adapter);
//                                lvexpanstud.setAdapter(adapter);

                                for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                                    if (!adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {

                                        adapter.selected.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");

                                    }
                                }

                                refresh();
                                adapter.notifyDataSetChanged();

//                                 check_chek_status( studentDisplayfillAttendanceAdapter);
                                check_chek_status();
                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }

    //    public void check_chek_status( final StudentDisplayfillAttendanceAdapter studentDisplayfillAttendanceAdapter) {
    public void check_chek_status() {
        cbcheckfillattendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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

                                                                 }
                                                             }
                                                         }
        );
    }


    public void showDialog4YNo(final Context context, String title, String message, final DailogCallBackOkButtonClick dailogCallBackOkButtonClick, final DailogCallBackCancelButtonClick dailogCallBackCancelButtonClick) {
        title = title + "";

        if (title.contentEquals("")) {
            title = context.getResources().getString(R.string.app_name);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.aommoncls_dialogbox_option, null);
        CustomBoldTextView titileTextView = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        CustomTextView msgTextView = (CustomTextView) dialogView.findViewById(R.id.tv_msg);
        LinearLayout tv_msg1 = (LinearLayout) dialogView.findViewById(R.id.tv_msg1);
        CustomButton dialogButtonOKButton = (CustomButton) dialogView.findViewById(R.id.dialogButtonOK);
        CustomButton dialogButtonCancel = (CustomButton) dialogView.findViewById(R.id.dialogButtonCancel);

        check_enrollent = (CheckBox) dialogView.findViewById(R.id.check_enrollent);


        check_name_student = (CheckBox) dialogView.findViewById(R.id.check_name_student);


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        show = builder.show();


        check_enrollent.setChecked(true);
        check_name_student.setChecked(true);
        if (DISPLAY_FIELD == 1) {
            check_enrollent.setChecked(true);
            check_name_student.setChecked(true);

        } else if (DISPLAY_FIELD == 2) {
            check_enrollent.setChecked(false);
            check_name_student.setChecked(true);

        } else if (DISPLAY_FIELD == 3) {
            check_enrollent.setChecked(true);
            check_name_student.setChecked(false);

        } else if (DISPLAY_FIELD == 4) {
            check_enrollent.setChecked(false);
            check_name_student.setChecked(false);

        }

        System.out.println("ID_Check::::::::::::::::::" + ID_Check);

        dialogButtonOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                DISPLAY_FIELD = 1;
                if (check_name_student.isChecked() && check_enrollent.isChecked()) {
                    DISPLAY_FIELD = 1;
                } else if (!check_enrollent.isChecked() && check_name_student.isChecked()) {
                    DISPLAY_FIELD = 2;
                } else if (check_enrollent.isChecked() && !check_name_student.isChecked()) {
                    DISPLAY_FIELD = 3;
                } else if (!check_enrollent.isChecked() && !check_name_student.isChecked()) {
                    DISPLAY_FIELD = 4;
                }
                set_layout();

//                if (dailogCallBackOkButtonClick != null)
//                    dailogCallBackOkButtonClick.onDialogOkButtonClicked();


            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                if (dailogCallBackCancelButtonClick != null)
                    dailogCallBackCancelButtonClick.onDialogCancelButtonClicked();
            }
        });

    }

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


                        if (response.length() > 5) {


                            Gson gson = new Gson();

                            teachingMethodPojo = gson.fromJson(response, TeachingMethodPojo.class);
                            if (teachingMethodPojo != null && teachingMethodPojo.getTable().size() > 0) {

                                Teaching_method_adapter_expandable.selected_check_IDS = new ArrayList<>();
                                teaching_method_adapter = new Teaching_method_adapter_expandable(EmployeeFillAttendanceActivity_configuration.this, teachingMethodPojo);
                                //   lvteachingmethod.setLayoutManager(new LinearLayoutManager(EmployeeFillAttendanceActivity.this, LinearLayoutManager.HORIZONTAL, false));

                                lv_teaching_method_att.setAdapter(teaching_method_adapter);


                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.hideProgressDialog();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);


    }

    private void Api_call_method_Get(String emp_id, String div_id, String sub_id, String year_id, String topic_id) {

        if (topic_id == null || topic_id.contentEquals("")) {
            topic_id = "0";
        } else {
            topic_id = topic_id;
        }
        String URLs = URl.get_methods_from_api + "&emp_id=" + emp_id + "&div_id=" + div_id + "&sub_id=" + sub_id + "&year_id=" + year_id + "&topic_id=" + topic_id + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("get_methods_from_api calls    " + URLs + "");
        final String finalTopic_id = topic_id;
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS get_methods_from_api RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";

                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            methodgetPojo = gson.fromJson(response, MethodgetPojo.class);
//                            if (methodgetPojo != null && methodgetPojo.getTable().size() > 0)
                            if (methodgetPojo != null && methodgetPojo.getTable().size() > 0) {

                                METHOD_TOPIC_NAME = methodgetPojo.getTable().get(0).getTopic_name() + "";
                                METHOD_TOPIC_METHOD = methodgetPojo.getTable().get(0).getTopic_method() + "";
                                METHOD_TOPIC_AID = methodgetPojo.getTable().get(0).getTopic_aid() + "";
                                METHOD_UNIT_ID = methodgetPojo.getTable().get(0).getUnit_id() + "";
                                Alterbate_method();

                            } else if (finalTopic_id == null || finalTopic_id.contentEquals("0")) {
                                Alterbate_method();
                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.hideProgressDialog();
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);


    }

    public void Alterbate_method() {
        String date_oldformat = bean.getDl_date() + "";//
        if (date_oldformat != null && date_oldformat.length() > 4) {

            date_oldformat = date_oldformat + "-";
            date_oldformat = date_oldformat.replace("/", "-");
            System.out.println("date_oldformat " + date_oldformat + "");
            String temp[] = date_oldformat.split("-");
            strDate = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("New Format :) :   " + temp.length + "");
            System.out.println("New Format strDate :) :   " + strDate + "");


        }



        /* ABSENT STUDENT  ATTENDANCE FILL ON CONFIGURATION  :::: NIRALI::: 26 DEC 2019*/
        if (!switchmethod.isChecked() && !edtrollno.getText().toString().contentEquals("")) {//Absent by roll no method


            System.out.println("absent method");
            if (edtrollno.getText().toString().trim().contentEquals("")) {
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please Enter Roll Number");
            } else {
                if (isNotEmpty()) {
                    if (CONFIGURATION == 1) {

                        api_call_absent_student_by_alternate_method_CONFIGURATION_ONE();

                    } else if (CONFIGURATION == 2) {

                        api_call_absent_student_by_alternate_method();
                    }


                }
            }


        }
        /* PRESENT STUDENT  ATTENDANCE FILL ON CONFIGURATION  :::: NIRALI::: 26 DEC 2019*/
        else if (switchmethod.isChecked() && !edtrollno.getText().toString().trim().contentEquals("")) {//present by roll no method

            System.out.println("present method");
            if (edtrollno.getText().toString().trim().contentEquals("")) {
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please Enter Roll Number");
            } else {
                if (isNotEmpty()) {


                    if (CONFIGURATION == 1) {

                        api_call_Present_student_by_alternate_method_CONFIGURATION_ONE();

                    } else if (CONFIGURATION == 2) {

                        api_call_Present_student_by_alternate_method();
                    }


                    //  api_call_present_student_by_alternate_method( );

                }
            }

        }
        /* ABSENT PRESENT  STUDENT  ATTENDANCE FILL ON CONFIGURATION  CLASS WISE :::: NIRALI::: 26 DEC 2019*/
        else if (edtrollno.getText().toString().contentEquals("")) {//Absent present by class wise selection method
            System.out.println("class method");


            ArrayList<String> allId;
            allId = new ArrayList<>();
            String PRESENT_ID = "";
            String ABSENT_ID = "";
            for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                allId.add(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "");
            }
            for (int i = 0; i < allId.size(); i++) {
                if (adapter.selected.contains(allId.get(i))) {

                    PRESENT_ID = PRESENT_ID + "," + allId.get(i) + "";
                } else {
                    ABSENT_ID = ABSENT_ID + "," + allId.get(i) + "";
                }
            }
            PRESENT_ID = PRESENT_ID.replaceFirst(",", "");
            ABSENT_ID = ABSENT_ID.replaceFirst(",", "");
            System.out.println("PRESENT_ID  " + PRESENT_ID + "");
            System.out.println("ABSENT_ID  " + ABSENT_ID + "");


            if (CONFIGURATION == 1) {


                Api_call_of_class_wise_student_attendance_take_CONFIGURATION_ONE("1", "", ABSENT_ID, PRESENT_ID);

            } else if (CONFIGURATION == 2) {

                Api_call_of_class_wise_student_attendance_take(METHOD_TOPIC_METHOD, "1", "", ABSENT_ID, PRESENT_ID);
            }





/*

            for (int i = 0; i < studentsDisplyaFillPojo.getTable().size(); i++) {
                // for(int j=0;j< adapter.selected.size();j++)
                //{
                if (adapter.selected.contains(studentsDisplyaFillPojo.getTable().get(i).getStud_id() + "")) {

                    Api_call_of_class_wise_student_attendance_take(METHOD_TOPIC_METHOD, "1", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), "");
                } else {
                    Api_call_of_class_wise_student_attendance_take(METHOD_TOPIC_METHOD, "0", studentsDisplyaFillPojo.getTable().get(i).getStud_id(), "");
                }
                // }


            }
*/

        }
    }

    private void Api_call_of_class_wise_student_attendance_take_CONFIGURATION_ONE(String status, String homework, String ABSENT_ID, String PRESENT_ID) {


        String str_ed_topic = "";
        if (ed_topic != null && !ed_topic.getText().toString().isEmpty()) {
            str_ed_topic = ed_topic.getText().toString().trim();
        } else {

            str_ed_topic = "";
        }

        String selected_method = "";
        String AID = "0";
        String Unit = "0";
        String flint = "0";
        String topic = "";
        if (bean.getLec_type().contentEquals("1")) {

            System.out.println("teaching IDS in class method******** " + Teaching_method_adapter_expandable.selected_check_IDS);
            if (Teaching_method_adapter_expandable.selected_check_IDS.size() != 0) {
                selected_method = Teaching_method_adapter_expandable.selected_check_IDS.toString();

            }

            if (selected_method.contains("[")) {
                selected_method = selected_method.replace("[", "");

            }
            if (selected_method.contains("]")) {

                selected_method = selected_method.replace("]", "");

            }

            AID = position_aid;
            flint = position_flint;
            Unit = position_unit;
            topic = str_ed_topic;

        } else {
            selected_method = "";
            AID = "0";
            flint = "0";
            Unit = position_unit;
            topic = str_ed_topic;

        }


        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        String URLs = URl.Insert_isrp_class_wise_attendance_API + "&college_id=" + bean.getCollege_id() + "&present_stud_id=" + PRESENT_ID + "&absent_stud_id=" + ABSENT_ID + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_status=" + status + "&att_topic=" + topic + "&att_teaching_method=" + selected_method + "&att_aid=" + AID + "&no_of_flinnt=" + flint + "&prac_the_status=" + bean.getLec_type() + "&att_version_id=" + bean.getDL_VERSION_ID() + "&att_type=" + "By Selection Method Application" + "&att_emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&att_unit_id=" + Unit + "&att_homework=" + homework + "&att_created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&att_created_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Insert_isrp_class_wise_attendance_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        progressDialog.dismiss();
                        System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        //  System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());


                                    Api_call_update_stud_record();

//                            ppppppppppppp        Intent intent = new Intent(EmployeeFillAttendanceActivity.this, FacultyAttendance.class);
//                                    startActivity(intent);
//                                    finish();
                                }
                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());

                                    finish();
                                }


                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }

    private void api_call_Present_student_by_alternate_method_CONFIGURATION_ONE()
    {

        String str_ed_topic = "";
        if (ed_topic != null && !ed_topic.getText().toString().isEmpty())
        {
            str_ed_topic = ed_topic.getText().toString().trim();
        } else {

            str_ed_topic = "";
        }

        String selected_method = "";
        String AID = "0";
        String Unit = "0";
        String flint = "0";
        String topic = "";
        if (bean.getLec_type().contentEquals("1"))
        {
            if (Teaching_method_adapter_expandable.selected_check_IDS.size() != 0)
            {
                selected_method = Teaching_method_adapter_expandable.selected_check_IDS.toString();

            }

            if (selected_method.contains("["))
            {
                selected_method = selected_method.replace("[", "");

            }
            if (selected_method.contains("]"))
            {

                selected_method = selected_method.replace("]", "");

            }

            AID = position_aid;
            flint = position_flint;
            Unit = position_unit;
            topic = str_ed_topic;

        }
        else
            {
            selected_method = "";
            AID = "0";
            flint = "0";
            Unit = position_unit;
            topic = str_ed_topic;

        }

        System.out.println("teaching IDS in present ******** " + Teaching_method_adapter_expandable.selected_check_IDS);

        String URLs = URl.Present_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_topic=" + topic + "&att_method=" + selected_method + "&att_aid=" + AID + "&att_flinnt=" + flint + "&roll_no=" + edtrollno.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + Unit + "&sub_id=" + bean.getSub_id() + "&att_homework=" + "" + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";
        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        //progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        URLs = URLs.replace(" ", "%20");
        System.out.println("Present_student_record_save calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Present_student_record_save RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {

                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();


                                }

                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {

                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    finish();

                                }
                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    private void api_call_absent_student_by_alternate_method_CONFIGURATION_ONE() {


        String str_ed_topic = "";
        if (ed_topic != null && !ed_topic.getText().toString().isEmpty()) {
            str_ed_topic = ed_topic.getText().toString().trim();
        } else {

            str_ed_topic = "";
        }

        String selected_method = "";
        String AID = "0";
        String Unit = "0";
        String flint = "0";
        String topic = "";
        if (bean.getLec_type().contentEquals("1")) {
            if (Teaching_method_adapter_expandable.selected_check_IDS.size() != 0) {
                selected_method = Teaching_method_adapter_expandable.selected_check_IDS.toString();

            }

            if (selected_method.contains("[")) {
                selected_method = selected_method.replace("[", "");

            }
            if (selected_method.contains("]")) {

                selected_method = selected_method.replace("]", "");

            }

            AID = position_aid;
            flint = position_flint;
            Unit = position_unit;
            topic = str_ed_topic;

        } else {
            selected_method = "";
            AID = "0";
            flint = "0";
            Unit = position_unit;
            topic = str_ed_topic;

        }

        System.out.println("teaching IDS in absent  ******** " + Teaching_method_adapter_expandable.selected_check_IDS);
        String URLs = URl.Absent_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_topic=" + topic + "&att_method=" + selected_method + "&att_aid=" + AID + "&att_flinnt=" + flint + "&roll_no=" + edtrollno.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + Unit + "&sub_id=" + bean.getSub_id() + "&att_homework=" + "" + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";

        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        URLs = URLs.replace(" ", "%20");
        System.out.println("Absent_student_record_save calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        progressDialog.dismiss();
                        response = response + "";
                        //  System.out.println("THIS IS Absent_student_record_save RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        //  System.out.println("THIS IS Absent_student_record_save RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {

                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();


                                }
                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    finish();


                                }

                            }


                        } else {

                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                //   progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    //    public void Api_call_of_class_wise_student_attendance_take(String Teaching_method_ID_class_wise, String status, String stud_ID,String homework)
    public void Api_call_of_class_wise_student_attendance_take(String Teaching_method_ID_class_wise, String status, String homework, String ABSENT_ID, String PRESENT_ID) {
        if (METHOD_TOPIC_METHOD == null || METHOD_TOPIC_METHOD.contentEquals("")) {
            METHOD_TOPIC_METHOD = "0";
//            METHOD_TOPIC_METHOD = "";
        } else {
            METHOD_TOPIC_METHOD = METHOD_TOPIC_METHOD;
        }

        if (METHOD_TOPIC_AID == null || METHOD_TOPIC_AID.contentEquals("")) {
            METHOD_TOPIC_AID = "0";
//            METHOD_TOPIC_AID ="";
        } else {
            METHOD_TOPIC_AID = METHOD_TOPIC_AID;
        }

        if (METHOD_TOPIC_NAME == null || METHOD_TOPIC_NAME.contentEquals("")) {
            METHOD_TOPIC_NAME = "";
        } else {
            METHOD_TOPIC_NAME = METHOD_TOPIC_NAME;
        }

        if (METHOD_UNIT_ID == null || METHOD_UNIT_ID.contentEquals("")) {
            METHOD_UNIT_ID = "0";
//            METHOD_UNIT_ID ="";
        } else {
            METHOD_UNIT_ID = METHOD_UNIT_ID;
        }


        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        String URLs = URl.Insert_isrp_class_wise_attendance_API + "&college_id=" + bean.getCollege_id() + "&present_stud_id=" + PRESENT_ID + "&absent_stud_id=" + ABSENT_ID + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_status=" + status + "&att_topic=" + METHOD_TOPIC_NAME + "&att_teaching_method=" + Teaching_method_ID_class_wise + "&att_aid=" + METHOD_TOPIC_AID + "&no_of_flinnt=" + "0" + "&prac_the_status=" + bean.getLec_type() + "&att_version_id=" + bean.getDL_VERSION_ID() + "&att_type=" + "By Selection Method Application" + "&att_emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&att_unit_id=" + METHOD_UNIT_ID + "&att_homework=" + homework + "&att_created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&att_created_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Insert_isrp_class_wise_attendance_API calls configuration 2    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        progressDialog.dismiss();
                        System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE  configuration 2    " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        //  System.out.println("THIS IS Insert_isrp_class_wise_attendance_API RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {

                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());


                                    Api_call_update_stud_record();

                                }
                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());

                                    finish();
                                }


                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    public void api_call_absent_student_by_alternate_method() {

        if (METHOD_TOPIC_METHOD == null || METHOD_TOPIC_METHOD.contentEquals("")) {
            METHOD_TOPIC_METHOD = "0";
//            METHOD_TOPIC_METHOD = "";
        } else {
            METHOD_TOPIC_METHOD = METHOD_TOPIC_METHOD;
        }

        if (METHOD_TOPIC_AID == null || METHOD_TOPIC_AID.contentEquals("")) {
            METHOD_TOPIC_AID = "0";
//            METHOD_TOPIC_AID ="";
        } else {
            METHOD_TOPIC_AID = METHOD_TOPIC_AID;
        }

        if (METHOD_TOPIC_NAME == null || METHOD_TOPIC_NAME.contentEquals("")) {
            METHOD_TOPIC_NAME = "";
        } else {
            METHOD_TOPIC_NAME = METHOD_TOPIC_NAME;
        }

        if (METHOD_UNIT_ID == null || METHOD_UNIT_ID.contentEquals("")) {
            METHOD_UNIT_ID = "0";
//            METHOD_UNIT_ID ="";
        } else {
            METHOD_UNIT_ID = METHOD_UNIT_ID;
        }
//        System.out.println("homework in absent ::::::::::" + homework);

        /*     String URLs = URl.Absent_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&att_intime=" + "" + "&att_topic=" + edttopic.getText().toString() + "&att_method=" + Teaching_method_ID + "&att_aid=" + position_aid + "&att_flinnt=" + position_flint + "&roll_no=" + edtattendancestudent.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + position_unit + "&sub_id=" + bean.getSub_id() + "&att_homework=" + homework + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";*/

        String URLs = URl.Absent_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_topic=" + METHOD_TOPIC_NAME + "&att_method=" + METHOD_TOPIC_METHOD + "&att_aid=" + METHOD_TOPIC_AID + "&att_flinnt=" + "0" + "&roll_no=" + edtrollno.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + METHOD_UNIT_ID + "&sub_id=" + bean.getSub_id() + "&att_homework=" + "" + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";

        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        URLs = URLs.replace(" ", "%20");
        System.out.println("Absent_student_record_save calls  configuration 2  " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        progressDialog.dismiss();
                        response = response + "";
                        //  System.out.println("THIS IS Absent_student_record_save RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        //  System.out.println("THIS IS Absent_student_record_save RESPONSE     " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();
//
//                                    Intent intent = new Intent(EmployeeFillAttendanceActivity.this, FacultyAttendance.class);
//                                    startActivity(intent);
//                                    finish();


                                }
                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {
                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    finish();
                                    //Api_call_update_stud_record();
//
//                                    Intent intent = new Intent(EmployeeFillAttendanceActivity.this, FacultyAttendance.class);
//                                    startActivity(intent);
//                                    finish();


                                }

                            }


                        } else {

                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                //   progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    private void api_call_Present_student_by_alternate_method() {


        if (METHOD_TOPIC_METHOD == null || METHOD_TOPIC_METHOD.contentEquals("")) {
            METHOD_TOPIC_METHOD = "0";
//            METHOD_TOPIC_METHOD = "";
        } else {
            METHOD_TOPIC_METHOD = METHOD_TOPIC_METHOD;
        }

        if (METHOD_TOPIC_AID == null || METHOD_TOPIC_AID.contentEquals("")) {
            METHOD_TOPIC_AID = "0";
//            METHOD_TOPIC_AID ="";
        } else {
            METHOD_TOPIC_AID = METHOD_TOPIC_AID;
        }

        if (METHOD_TOPIC_NAME == null || METHOD_TOPIC_NAME.contentEquals("")) {
            METHOD_TOPIC_NAME = "";
        } else {
            METHOD_TOPIC_NAME = METHOD_TOPIC_NAME;
        }

        if (METHOD_UNIT_ID == null || METHOD_UNIT_ID.contentEquals("")) {
            METHOD_UNIT_ID = "0";
//            METHOD_UNIT_ID ="";
        } else {
            METHOD_UNIT_ID = METHOD_UNIT_ID;
        }
//        System.out.println("homework in present ::::::::::"+homework);
        /*   String URLs = URl.Present_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + bean.getDl_date() + "&att_intime=" + "" + "&att_topic=" + METHOD_TOPIC_NAME + "&att_method=" + METHOD_TOPIC_METHOD + "&att_aid=" + METHOD_TOPIC_AID + "&att_flinnt=" + "0" + "&roll_no=" + edtrollno.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + METHOD_UNIT_ID + "&sub_id=" + bean.getSub_id() + "&att_homework=" + "" + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";*/

        String URLs = URl.Present_student_record_save + "&college_id=" + bean.getCollege_id() + "&sem_id=" + bean.getSm_id() + "&div_id=" + bean.getDiv_id() + "&course_id=" + bean.getCourse_id() + "&batch_id=" + bean.getBatch_id() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&lecture_no=" + bean.getLec_no() + "&att_date=" + strDate + "&att_intime=" + "" + "&att_topic=" + METHOD_TOPIC_NAME + "&att_method=" + METHOD_TOPIC_METHOD + "&att_aid=" + METHOD_TOPIC_AID + "&att_flinnt=" + "0" + "&roll_no=" + edtrollno.getText().toString() + "&att_prac_the_status=" + bean.getLec_type() + "&version_id=" + bean.getDL_VERSION_ID() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&unit_id=" + METHOD_UNIT_ID + "&sub_id=" + bean.getSub_id() + "&att_homework=" + "" + "&created_by=" + String.valueOf(storage.read("emp_id", 3)) + "&created_ip=" + "1" + "";
        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFillAttendanceActivity_configuration.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
        //progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        URLs = URLs.replace(" ", "%20");
        System.out.println("Present_student_record_save calls  configuration 2  " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Present_student_record_save RESPONSE   configuration 2   " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Present_student_record_save RESPONSE  configuration 2   " + response + "");
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            absentPojo = gson.fromJson(response, AbsentPojo.class);
                            if (absentPojo != null && absentPojo.getTable().size() > 0) {


                                if (absentPojo.getTable().get(0).getError_code().contentEquals("1")) {

                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    Api_call_update_stud_record();


                                }

                                if (absentPojo.getTable().get(0).getError_code().contentEquals("2")) {

                                    DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, absentPojo.getTable().get(0).getError_msg());
                                    finish();

                                }
                            }


                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Please try again!");
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    private boolean isNotEmpty() {
        boolean flag = false;
        String str = edtrollno.getText() + "";
        if (str != null && !str.contentEquals("") && str.length() > 0) {
            flag = true;
            System.out.println("flag is true:::::::::::::::::::::::");

        } else {
            flag = false;
        }
        return flag;
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
                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            UpdateAttpojo updateAttpojo = gson.fromJson(response, UpdateAttpojo.class);
                            if (updateAttpojo != null && updateAttpojo.getUpdate().size() > 0) {

                                if (updateAttpojo.getUpdate().get(0).getError_code().contentEquals("1")) {
                                    // DialogUtils.Show_Toast(FillAttendanceActivityfaculty.this, updateAttpojo.getUpdate().get(0).getError_msg());

//                                    Intent intent = new Intent(EmployeeFillAttendanceActivity.this, FacultyAttendance.class);
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

    private void CheckAttendanceExistOrNot() {
        String date_oldformat = bean.getDl_date() + "";//
        if (date_oldformat != null && date_oldformat.length() > 4) {

            date_oldformat = date_oldformat + "-";
            date_oldformat = date_oldformat.replace("/", "-");
            System.out.println("date_oldformat " + date_oldformat + "");
            String temp[] = date_oldformat.split("-");
            strDate = temp[2] + "-" + temp[1] + "-" + temp[0];
            System.out.println("New Format :) :   " + temp.length + "");
            System.out.println("New Format strDate :) :   " + strDate + "");


        }

        String URLs = URl.Check_Attendance_Exists_Before_Fill_Attendance + "&course_id=" + bean.getCourse_id() + "&sem_id=" + bean.getSm_id() + "&division_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "&sub_id=" + bean.getSub_id() + "&emp_id=" + String.valueOf(storage.read("emp_id", 3)) + "&att_date=" + strDate + "&lec_no=" + bean.getLec_no() + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Check_Attendance_Exists_Before_Fill_Attendance calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";

                        System.out.println("THIS IS Check_Attendance_Exists_Before_Fill_Attendance RESPONSE     " + response + "");
                        if (response.length() > 10) {


                            Gson gson = new Gson();


                            checkAteendancePojo = gson.fromJson(response, CheckAteendancePojo.class);
                            if (checkAteendancePojo != null && checkAteendancePojo.getTable().size() > 0) {


                                //already filed attendance


                                DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "Attendance Already Taken ");


                            } else {
                                // go to fill attendance


                                /*check for configuration and validation*/
                                if (CONFIGURATION == 1) {


                                    Alterbate_method();


                                } else if (CONFIGURATION == 2) {
                                    Api_call_method_Get(String.valueOf(storage.read("emp_id", 3)), bean.getDiv_id() + "", bean.getSub_id() + "" + "", String.valueOf(storage.read("emp_year_id", 3)) + "", TOPIC_ID + "");

                                }


                            }

                        } else {
                            DialogUtils.Show_Toast(EmployeeFillAttendanceActivity_configuration.this, "No Records Found");
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

    public void Api_call_display_AID() {
        String URLs = URl.Get_Teaching_Aid_Detail_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Api_call_display_AID calls    " + URLs + "");
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
                            adapter_AID_spin = new ArrayAdapter<String>(EmployeeFillAttendanceActivity_configuration.this, R.layout.spinner_item, spin_list_AID);
                            spin_aid.setAdapter(adapter_AID_spin);

                            spin_aid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        if (response.length() > 5) {


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
                            adapter_unit = new ArrayAdapter<String>(EmployeeFillAttendanceActivity_configuration.this, R.layout.spinner_item, spinUnit_name_list);
                            spin_unit.setAdapter(adapter_unit);

                            spin_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        adapter_flint = new ArrayAdapter<String>(EmployeeFillAttendanceActivity_configuration.this, R.layout.spinner_item, Month_list);
        spin_flint.setAdapter(adapter_flint);

        //sel_month = Month_list.indexOf(String.valueOf(storage.read("PASSING_MONTH", 3) + ""));

        //spinmonth.setSelection(sel_month+1);
        //spinmonth.setSelection(sel_month);


        spin_flint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void Api_call_Configuration_AttedanceDisplay() {
//        String URLs = URl.Get_Attendance_Method_Configuration_For_Fill_Attendance + "&college_id=" + String.valueOf(storage.read("emp_permenant_college", 3)) + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";


        String URLs = URl.Get_Attendance_Method_Configuration_For_Fill_Attendance + "&college_id=" + bean.getCollege_id() + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";


        URLs = URLs.replace(" ", "%20");

        System.out.println("Get_Attendance_Method_Configuration_For_Fill_Attendance calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{" +
//                                "\"Table\": [" +
//                                "{" +
//                                "\"ac_id\": 2," +
//                                "\"ac_attendance_method\": 2" +
//                                "}" +
//                                "]" +
//                                "}" + "";
                        System.out.println("THIS IS Get_Attendance_Method_Configuration_For_Fill_Attendance RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";

                        if (response.length() > 5) {

                            Gson gson = new Gson();

                            configurationAttPojo = gson.fromJson(response, ConfigurationAttPojo.class);

                            if (configurationAttPojo != null) {

                                if (configurationAttPojo.getTable().size() > 0) {
                                    System.out.println("");
                                    if (configurationAttPojo.getTable().get(0).getAc_attendance_method().contentEquals("1")) {//then dropdown of unit and topic will textbox
                                        CONFIGURATION = 1;
                                        lin_config.setVisibility(View.VISIBLE);
                                        System.out.println("this is visible!!!!!!!!!!!!!!!!!");
                                        spintopic.setVisibility(View.GONE);
                                        if (bean.getLec_type().contentEquals("1")) {/**theory type*/
                                            lv_teaching_method_att.setVisibility(View.VISIBLE);
                                            ll_aid_parent.setVisibility(View.VISIBLE);
                                            ll_teching_method.setVisibility(View.VISIBLE);
                                            ed_topic.setVisibility(View.VISIBLE);
                                            spin_topic_configuration_2.setVisibility(View.GONE);


                                        } else {/**2=practical type*/
                                            lin_config.setVisibility(View.VISIBLE);
                                            //  lv_teaching_method_att.setVisibility(View.GONE);
                                            ll_teching_method.setVisibility(View.GONE);
                                            ll_aid_parent.setVisibility(View.GONE);
                                            ed_topic.setVisibility(View.VISIBLE);
                                            spin_topic_configuration_2.setVisibility(View.GONE);
                                        }


                                    } else {//then topic is dropdown and not compulsory
                                        CONFIGURATION = 2;
                                        System.out.println("this will gone!!!!!!!!!");
                                        lin_config.setVisibility(View.GONE);
                                        spintopic.setVisibility(View.VISIBLE);
                                        ll_topic.setVisibility(View.VISIBLE);

                                        //   ed_topic.setVisibility(View.GONE);
                                        //     spin_topic_configuration_2.setVisibility(View.VISIBLE);


                                    }


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

    boolean validation() {
        boolean flag = true;
        System.out.println("position_flint!!!!!!!!!! " + position_flint + "");
        System.out.println("position_aid!!!!!!!!!! " + position_aid + "");
        System.out.println("position_unit!!!!!!!!!! " + position_unit + "");
        str_ed_topic = ed_topic.getText().toString().trim();
        System.out.println("teaching method !!!!!!!!!! " + Teaching_method_adapter_expandable.selected_check_IDS.toString() + "");
        if (CONFIGURATION == 1) {


            if (bean.getLec_type().contentEquals("1")) {

                System.out.println("teaching method size :::::::::::::: " + Teaching_method_adapter_expandable.selected_check_IDS.size());
                System.out.println("teaching method :::::::::::::: " + Teaching_method_adapter_expandable.selected_check_IDS.toString());
                if (Teaching_method_adapter_expandable.selected_check_IDS.size() == 0) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Select Teaching Method", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;
                } else if (position_aid.contentEquals("") || position_aid.contentEquals("0")) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Select Aid", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;
                } else if (position_flint.contentEquals("") || position_flint.contentEquals("0") || position_flint.compareToIgnoreCase("Select No Of Posts on Flinnt") == 0) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Select Flinnt", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;
                } else if (str_ed_topic.contentEquals("") || str_ed_topic.length() < 4) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Enter Topic", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;


                }

               /* else if (position_aid.contentEquals("") || position_aid.contentEquals("0")) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Select Aid", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;
                }*/

            } else {
                if (position_flint.contentEquals("") || position_flint.contentEquals("0") || position_flint.compareToIgnoreCase("Select No Of Posts on Flinnt") == 0) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Select Flinnt", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;
                } else if (str_ed_topic.contentEquals("") || str_ed_topic.length() < 4) {
                    Toast.makeText(EmployeeFillAttendanceActivity_configuration.this, "Please Enter Topic", Toast.LENGTH_LONG).show();
                    flag = false;
                    return false;


                }
            }


        } else if (CONFIGURATION == 2) {


        }


        return flag;
    }

    public interface DailogCallBackOkButtonClick {
        public void onDialogOkButtonClicked();

    }

    public interface DailogCallBackCancelButtonClick {
        public void onDialogCancelButtonClicked();

    }
}
