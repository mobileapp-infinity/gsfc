package com.infinity.infoway.gsfc.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomEditText;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.CheckGrpPojo;
import com.infinity.infoway.gsfc.model.DeletePojo;

import java.util.ArrayList;

public class E_Learning extends AppCompatActivity {
    private Toolbar toolbar;
    private CustomTextView title;
    private CustomBoldTextView tvgpname;
    private CustomEditText edgn;
    private CustomBoldTextView tvgrptype;
    private Spinner spinelerning;
    private CustomBoldTextView tvdesc;
    private CustomEditText edtdescelerning;
    private LinearLayout llelerning;
    private CustomBoldTextView tvsave;
    ArrayList<String> grp_type;
    String GRoup_Type_ID;
    DataStorage storage;
    RequestQueue queue;
    String Grp_ID, Grp_Name, Grp_Type, Grp_Desc;

    Boolean IS_EDIT;
    String TYPE_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_learning);

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


        Intent intent = getIntent();

        Grp_ID = intent.getStringExtra("Grp_ID") + "";
        System.out.println("Grp_ID:::::::::" + Grp_ID);
        Grp_Name = intent.getStringExtra("Grp_Name") + "";
        Grp_Type = intent.getStringExtra("Grp_Type") + "";
        System.out.println("Grp_Type::::::::::" + Grp_Type);
        Grp_Desc = intent.getStringExtra("Grp_Desc") + "";

        IS_EDIT = intent.getExtras().getBoolean("IS_Edit");

        System.out.println("IS_EDIT::::::" + IS_EDIT);
        init();
        GroupType();

        if (IS_EDIT) {
            BindEditData();
        }
    }

    private void BindEditData() {
        if (Grp_Name != null || !Grp_Name.contentEquals("")) {
            edgn.setText(Grp_Name + "");

        }

        if (Grp_Desc != null || !Grp_Desc.contentEquals("")) {
            edtdescelerning.setText(Grp_Desc + "");
        }
        if (Grp_Type != null || !Grp_Type.contentEquals("")) {
            if (Grp_Type.contentEquals("Optional")) {
                TYPE_ID = "1";
            } else {
                TYPE_ID = "2";
            }


        }
        if (TYPE_ID != null || !TYPE_ID.contentEquals("")) {
            System.out.println("Grp_Type:::::::::::" + Grp_Type);
            System.out.println("grp_type:::::::::::" + grp_type.toString() + "");
            System.out.println("type ID ::::::::::" + TYPE_ID);
//            spinelerning.setSelection(grp_type.indexOf(TYPE_ID));
            spinelerning.setSelection(grp_type.indexOf(Grp_Type));
            System.out.println("set selection::::::::::" + grp_type.indexOf(TYPE_ID));
        }

    }

    private void GroupType() {
        grp_type = new ArrayList<>();

        grp_type.add("Select Group Type");
        grp_type.add("Optional");
        grp_type.add("Compulsory");

        SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(E_Learning.this, grp_type);
        spinelerning.setAdapter(spinnerSimpleAdapter);


        spinelerning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GRoup_Type_ID = i + "";
                System.out.println("GRoup_Type_ID :::" + GRoup_Type_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public boolean isValidate() {
        if (edgn.getText().toString().trim().isEmpty() || edgn.getText().toString().trim().length() < 0 || edgn.getText().toString().trim().contentEquals("")) {
            DialogUtils.Show_Toast(E_Learning.this, "Enter Group Name");
            return false;
        } else if (GRoup_Type_ID.contentEquals("0") || GRoup_Type_ID.contentEquals("")) {
            DialogUtils.Show_Toast(E_Learning.this, "Select Group Type");
            return false;
        } else if (edtdescelerning.getText().toString().trim().isEmpty() || edtdescelerning.getText().toString().trim().length() < 0 || edtdescelerning.getText().toString().trim().contentEquals("")) {
            DialogUtils.Show_Toast(E_Learning.this, "Enter Description");
            return false;
        }

        return true;
    }

    private void init() {
        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(this);
        this.tvsave = (CustomBoldTextView) findViewById(R.id.tv_save);
        this.llelerning = (LinearLayout) findViewById(R.id.ll_elerning);
        this.edtdescelerning = (CustomEditText) findViewById(R.id.edt_desc_e_lerning);
        this.tvdesc = (CustomBoldTextView) findViewById(R.id.tv_desc);
        this.spinelerning = (Spinner) findViewById(R.id.spin_e_lerning);
        this.tvgrptype = (CustomBoldTextView) findViewById(R.id.tv_grp_type);
        this.edgn = (CustomEditText) findViewById(R.id.ed_gn);
        this.tvgpname = (CustomBoldTextView) findViewById(R.id.tv_gp_name);
        this.title = (CustomTextView) findViewById(R.id.title);


        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    //if (IS_EDIT.booleanValue()==true)
                    if (IS_EDIT != null && !IS_EDIT.booleanValue() == false)

                    {
                        CheckWhenUpdateRecord();
                    } else {
                        CheckGroupExistOrNot();

                    }

                }

            }
        });

    }

    private void CheckWhenUpdateRecord() {
        DialogUtils.showProgressDialog(E_Learning.this, "");
        String URLs = URl.check_exists_e_learning_group_by_id_API + "&el_grp_id=" + Grp_ID + "&grp_name=" + edgn.getText().toString().trim() + "&grp_type=" + GRoup_Type_ID + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("check_exists_e_learning_group_by_id_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS check_exists_e_learning_group_by_id_API RESPONSE      " + response + "");


                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            CheckGrpPojo checkGrpPojo = gson.fromJson(response, CheckGrpPojo.class);

                            if (checkGrpPojo != null && checkGrpPojo.getTable().size() > 0)

                            {

                                if (checkGrpPojo.getTable().get(0).getExist_status().compareToIgnoreCase("1") == 0)

                                {
                                    DialogUtils.Show_Toast(E_Learning.this, "Group Already Exist");
                                } else

                                {
                                    UpdateRecord(Grp_ID, GRoup_Type_ID);
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    private void CheckGroupExistOrNot() {
        DialogUtils.showProgressDialog(E_Learning.this, "");
        String URLs = URl.check_exists_e_learning_group_API + "&grp_name=" + edgn.getText().toString().trim() + "&grp_type=" + GRoup_Type_ID + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("check_exists_e_learning_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS check_exists_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            CheckGrpPojo checkGrpPojo = gson.fromJson(response, CheckGrpPojo.class);

                            if (checkGrpPojo != null && checkGrpPojo.getTable().size() > 0)

                            {

                                if (checkGrpPojo.getTable().get(0).getExist_status().compareToIgnoreCase("1") == 0)


                                {
                                    DialogUtils.Show_Toast(E_Learning.this, "Group Already Exist");
                                } else {
                                    InsertGroup();
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }


    private void UpdateRecord(String ID, String TYPEID) {
        DialogUtils.showProgressDialog(E_Learning.this, "");
        String URLs = URl.update_e_learning_group_API + "&el_grp_id=" + ID + "&grp_name=" + edgn.getText().toString().trim() + "&grp_type=" + TYPEID + "&description=" + edtdescelerning.getText().toString().trim() + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&modify_ip=" + "1" + "&modify_by=" + storage.read("emp_id", 3) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("update_e_learning_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS update_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                            if (deletePojo != null && deletePojo.getTable().size() > 0)

                            {

                                if (deletePojo.getTable().get(0).getError_code().compareToIgnoreCase("1") == 0)


                                {
                                    DialogUtils.Show_Toast(E_Learning.this, "Group Edit Sucessfully");
                                    Intent intent = new Intent(E_Learning.this, E_Learning_List.class);
                                    startActivity(intent);
                                    finish();
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    private void InsertGroup() {
        DialogUtils.showProgressDialog(E_Learning.this, "");
        String URLs = URl.insert_e_learning_group_API + "&grp_name=" + edgn.getText().toString().trim() + "&grp_type=" + GRoup_Type_ID + "&description=" + edtdescelerning.getText().toString().trim() + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&created_ip=" + "1" + "&created_by=" + storage.read("emp_id", 3) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("insert_e_learning_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS insert_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                            if (deletePojo != null && deletePojo.getTable().size() > 0)

                            {

                                if (deletePojo.getTable().get(0).getError_code().compareToIgnoreCase("1") == 0)


                                {
                                    DialogUtils.Show_Toast(E_Learning.this, "Group Add Sucessfully");
                                    Intent intent = new Intent(E_Learning.this, E_Learning_List.class);
                                    startActivity(intent);
                                    finish();
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }
}
