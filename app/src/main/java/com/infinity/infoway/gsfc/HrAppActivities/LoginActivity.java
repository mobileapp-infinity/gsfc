package com.infinity.infoway.gsfc.HrAppActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.gson.Gson;

import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.MySharedPrefereces;
import com.infinity.infoway.gsfc.HrAppAPI.URLS;
import com.infinity.infoway.gsfc.HrAppAdapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.HrAppPojo.CPermissionPojo;
import com.infinity.infoway.gsfc.HrAppPojo.CpListPojo;
import com.infinity.infoway.gsfc.HrAppPojo.LoginPojo;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.judemanutd.autostarter.AutoStartPermissionHelper;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    RequestQueue queue;
    EditText edtuname;
    EditText edtpassword;
    ImageView ivshowpsw;
    CustomBoldTextView tvsignin;
    CustomBoldTextView tvsignup;
    CustomTextView tvfpsw;
    Spinner spin_company;
    LinearLayout ll_company;
    ArrayList<String> c_list;
    ArrayList<String> c_list_ID;
    String c_ID;

    DataStorage storage ;
    android.widget.CheckBox chk_show_password;

    MySharedPrefereces mySharedPrefereces;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hr);

        initView();
        //*************** thread policy for version pie *************
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // API_Call_Count_Of_Permission_Company();

        //*********** auto start for vivo,mmi,oppo mobile (push,notification)***********
        /*13 jan 2019*/
       /* try {
            AutoStartPermissionHelper.getInstance().getAutoStartPermission(LoginActivity.this);
        }
        catch (Exception e)
        {
            System.out.println("exception in auto start :::::::::::::"+e.getMessage());
            e.printStackTrace();
        }*/

        LoginAPICall(String.valueOf(storage.read("emp_username",3)),String.valueOf(storage.read("emp_password",3)));


    }


    public boolean Validate_Data() {
        if (edtuname.getText().toString().trim().isEmpty() && edtuname.getText().toString().trim().contentEquals("") && edtuname.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(LoginActivity.this, "Enter UserName");
            return false;
        }

        if (edtpassword.getText().toString().trim().isEmpty() && edtpassword.getText().toString().trim().contentEquals("") && edtpassword.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(LoginActivity.this, "Enter Password");
            return false;
        }
        return true;
    }

    //****** for bind company for selection *************
    private void BindCompany_API_Call() {

        String URLs = URLS.get_user_compny_permission_list + "&userid=" + "admin" + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("get_user_compny_permission_list calls    !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS get_user_compny_permission_list  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        if (response.length() > 10)
                        {
                            Gson gson = new Gson();
                            c_list = new ArrayList<>();
                            c_list_ID = new ArrayList<>();
                            CpListPojo cpListPojo = gson.fromJson(response, CpListPojo.class);

                            if (cpListPojo != null && cpListPojo.getData().size() > 0) {

                                c_list.add("Select Company");
                                c_list_ID.add("0");
                                for (int i = 0; i < cpListPojo.getData().size(); i++) {
                                    c_list.add(cpListPojo.getData().get(i).getCom_name() + "");
                                    c_list_ID.add(cpListPojo.getData().get(i).getId() + "");
                                }
                                SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(LoginActivity.this, c_list);

                                spin_company.setAdapter(spinnerSimpleAdapter);


                                spin_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        c_ID = c_list_ID.get(i);

                                        System.out.println("c_ID in api :::::::::" + c_ID);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }


                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);


    }


    private void API_Call_Count_Of_Permission_Company() {

        String url = URLS.get_multi_compny_permission_user + "&userid=" + "admin" + "";
        System.out.println("get_multi_compny_permission_user FINAL URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                System.out.println("response of API_Call_Count_Of_Permission_Company !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

                System.out.println("sucess response get_multi_compny_permission_user !!!!!!!!!!!!!!!!!!!" + response + "");
                Gson gson = new Gson();
                CPermissionPojo cPermissionPojo = gson.fromJson(response, CPermissionPojo.class);
                if (cPermissionPojo != null) {
                    if (cPermissionPojo.getData() != null) {
                        if (cPermissionPojo.getData().get(0) != null) {

                            if (cPermissionPojo.getData().size() > 0) {
                                spin_company.setVisibility(View.VISIBLE);
                                ll_company.setVisibility(View.VISIBLE);
                                BindCompany_API_Call();
                            } else {
                                spin_company.setVisibility(View.GONE);
                                ll_company.setVisibility(View.GONE);
                            }

                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {

        storage = new DataStorage("Login_Detail",LoginActivity.this);
        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());

        chk_show_password =(android.widget.CheckBox)findViewById(R.id.chk_show_password);
        chk_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtpassword.setSelection(edtpassword.getText().toString().length());
                } else {
                    // hide password
                    edtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtpassword.setSelection(edtpassword.getText().toString().length());
                }
            }
        });
        spin_company = (Spinner) findViewById(R.id.spin_company);
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        edtuname = (EditText) findViewById(R.id.edt_uname);
        edtpassword = (EditText) findViewById(R.id.edt_password);
//        ivshowpsw = (ImageView) findViewById(R.id.iv_show_psw);
        tvsignin = (CustomBoldTextView) findViewById(R.id.tv_sign_in);
        tvsignup = (CustomBoldTextView) findViewById(R.id.tv_sign_up);
        tvfpsw = (CustomTextView) findViewById(R.id.tv_f_psw);
        tvfpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotpasswordActivity.class);
                startActivity(intent);
            }
        });
       /* tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate_Data())
                {
                    LoginAPICall();
                }


//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });*/
    }

    //******* login user **********
    private void LoginAPICall(String USERNAME ,String PASSWORD)
    {


        String pssword = PASSWORD ;
        try {
            pssword = URLEncoder.encode(pssword,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DialogUtils.showProgressDialog(LoginActivity.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.LoginCheck + "&userName=" + USERNAME + "&passWord=" + pssword + "";
        url.replace(" ","%20");

        System.out.println("LoginCheck URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                DialogUtils.hideProgressDialog();

                System.out.println("response of LoginCheck !!!!!!!!!!! " + response);
                response = response + "";
                if (response.length()>5)
                {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response LoginCheck !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    LoginPojo loginPojo = gson.fromJson(response, LoginPojo.class);
                    if (loginPojo != null)
                    {
                        if (loginPojo.getData() != null)
                        {
                            if (loginPojo.getData().get(0) != null)
                            {
                                if (loginPojo.getData().size() > 0)
                                {
                                    if (loginPojo.getData().get(0).getStatus().contentEquals("1"))
                                    {
                                        //DialogUtils.Show_Toast(LoginActivity.this,"Login Sucessfully");
                                        //********* store login data of user ****************
                                        mySharedPrefereces.storeLoginData(loginPojo.getData().get(0).getStatus()+"", loginPojo.getData().get(0).getUsrm_id() + "", loginPojo.getData().get(0).getEmp_code() + "", loginPojo.getData().get(0).getUsrm_name()+"", loginPojo.getData().get(0).getUsrm_dis_name()+"", loginPojo.getData().get(0).getComp_id() + "", loginPojo.getData().get(0).getUsrm_brm_id() + "", loginPojo.getData().get(0).getCom_name()+"", loginPojo.getData().get(0).getFin_year()+"", loginPojo.getData().get(0).getFin_id()+"", loginPojo.getData().get(0).getFin_start_date() + "", loginPojo.getData().get(0).getFin_end_date() + "", loginPojo.getData().get(0).getEmp_id() + "",loginPojo.getData().get(0).getDepartment()+"",loginPojo.getData().get(0).getReportingto()+"",loginPojo.getData().get(0).getUserphoto()+"",loginPojo.getData().get(0).getDesignation()+"",loginPojo.getData().get(0).getBranch()+"",loginPojo.getData().get(0).getFullName()+"");


                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {
                                      //  DialogUtils.Show_Toast(LoginActivity.this,"Invalid UserName/Password");
                                    }
                                }


                            }
                        }
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(LoginActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
