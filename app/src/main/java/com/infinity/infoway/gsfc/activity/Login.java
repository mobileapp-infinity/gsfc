package com.infinity.infoway.gsfc.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.Login_Master;
import com.infinity.infoway.gsfc.app.StorageLogin;
import com.infinity.infoway.gsfc.model.AuthenticationPojo;
import com.infinity.infoway.gsfc.model.LoginResponse;
import com.infinity.infoway.gsfc.model.Login_Email_Pojo;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    Context ctx;
    DataStorage storage;
    EditText etusername, etpassword;
    TextView tvforgotpassword;
    String addmision;
    String is_director, emp_id;
    ListView lv_login;
    StorageLogin storageLogin;
    Login_Master login_master;
    String[] s1_array;
    String[] s_array;
    LoginListAdapter adapter_login;
    String final_username, final_Password;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findviews();

        DataStorage cstorage = new DataStorage("Login_Detail", this);
        if (cstorage.CheckLogin("stud_id", this)) {
            Intent intent = new Intent(Login.this, Main3Activity.class);
            startActivity(intent);
            finish();
        }


        if (String.valueOf(login_master.read("Master_String", 3)).equals("") || String.valueOf(login_master.read("Master_String", 3)).equals(null)) {

            lv_login.setVisibility(View.GONE);
        } else {
            lv_login.setVisibility(View.VISIBLE);


            s1_array = new String[1000];
            s_array = new String[1000];

            String s = String.valueOf(login_master.read("Master_name", 3));
            String s1 = String.valueOf(login_master.read("Master_String", 3));


            //   System.out.println("s1_Master_String:::::" + s1_array.length);
            //   System.out.println("s1_Master_String:::::" + s1_array[0]);
            //  if (s.contains(",")) {

            s_array = s.split(",");
           /* } else {
                s_array[0] = s;
            }*/


            // if (s1.contains(",")) {
            s1_array = s1.split(",");

           /* } else {
                s1_array[0] = s;
            }*/

            ArrayList<String> sList = new ArrayList(Arrays.asList(s1_array));
            ArrayList<String> sList_ = new ArrayList(Arrays.asList(s_array));


            adapter_login = new LoginListAdapter(sList_, sList, this);
            lv_login.setAdapter(adapter_login);


            /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_array);
            spin_login.setAdapter(dataAdapter);*/


           /* spin_login.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    int position = i;

                    String arr = s1_array[i];
                    System.out.println("arrrrrrrrrrrrr" + arr);
                    System.out.println("position OF name:::" + position);

                    final_username = String.valueOf(login_master.read("UserName_" + arr, 3));
                    System.out.println("final_username::::" + final_username);
                    final_Password = String.valueOf(login_master.read("Password_" + arr, 3));
                    System.out.println("final_Password::::" + final_Password);


                    etusername.setText(final_username);
                    etpassword.setText(final_Password);

                    LOGIN(final_username, final_Password);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/
        }


//        if (cstorage.CheckLogin("emp_id", this))
//        {
//
//            if (storage.read("emp_id", 3).equals("1716"))
//            {
//                startActivity(new Intent(Login.this, DirectivePageActivity.class));
//                finish();
//            }
//            else
//            {
//
//                Intent intent = new Intent(Login.this, Main3Activity.class);
//                startActivity(intent);
//                finish();
//            }
//
//        }
        if (cstorage.CheckLogin("emp_id", this))
        {
            if (cstorage.read("is_director", 3).equals("1"))
            {
                startActivity(new Intent(Login.this, DirectivePageActivity.class));
                finish();
            }
            else
                {

                Intent intent = new Intent(Login.this, Main3Activity.class);
                startActivity(intent);
                finish();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!hasPermissions(Login.this, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(Login.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
            }
        }
    }

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;
    private final String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        System.out.println("onRequestPermissionsResult!!!!!!!!!!!!!!!!!!!");

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //  checkVersionInfoApiCall();
            } else {

                try {
                    alertAlert(getResources().getString(R.string.permissions_has_not_grant));
                } catch (Exception e) {

                }
            }
        }

    }

    private void alertAlert(String msg) {
        new android.app.AlertDialog.Builder(Login.this)
                .setTitle(getResources().getString(R.string.permission_request))
                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.drawable.launcher_main)
                .show();
    }

    public void findviews() {
        ctx = this;
        queue = Volley.newRequestQueue(this);
        storage = new DataStorage("Login_Detail", ctx);
        storageLogin = new StorageLogin("Login", ctx);
        login_master = new Login_Master("Login_Master", ctx);
        login = (Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(this);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        tvforgotpassword = (TextView) findViewById(R.id.tvforgotpassword);
        tvforgotpassword.setOnClickListener(this);
        lv_login = (ListView) findViewById(R.id.lv_login);
    }


    AuthenticationPojo authenticationPojo;

    public void Api_call_Authenticate_user_by_Api(final ProgressDialog progressDialog) {


        String URLs = URl.Authenticate_user_by_validation + "&user_id=" + etusername.getText().toString() + "&user_pass=" + etpassword.getText().toString() + "&secretkey=" + "b10ee967b8014c69569b5b907a73f882" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Authenticate_user_by_validation calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Authenticate_user_by_validation RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Authenticate_user_by_validation RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();


                            authenticationPojo = gson.fromJson(response, AuthenticationPojo.class);
                            if (authenticationPojo != null) {
                                progressDialog.dismiss();

                                DialogUtils.Show_Toast(Login.this, authenticationPojo.getError_desc());

                                if (authenticationPojo.getError_desc().contentEquals("User is Valid.")) {

                                    Api_call_user_data_fetch_by_login(authenticationPojo.getUser_id());
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


    Login_Email_Pojo login_email_pojo;

    public void Api_call_user_data_fetch_by_login(String user_Id) {

        String URLs = URl.Login_user_by_email_Api + "&username=" + user_Id + "&password=" + etpassword.getText().toString() + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Login_user_by_email_Api calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Login_user_by_email_Api RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Login_user_by_email_Api RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();


                            login_email_pojo = gson.fromJson(response, Login_Email_Pojo.class);
                            if (login_email_pojo != null) {
                                storage.write("emp_id", login_email_pojo.getEmp_id());
                                storage.write("emp_main_school_id", login_email_pojo.getEmp_main_school_id());
                                storage.write("emp_username", login_email_pojo.getEmp_username());
                                storage.write("emp_password", login_email_pojo.getEmp_password());
                                storage.write("ac_full_name", login_email_pojo.getAc_full_name());
                                storage.write("ac_logo", login_email_pojo.getAc_logo());
                                storage.write("name", login_email_pojo.getName());
                                storage.write("stud_photo", login_email_pojo.getStud_photo());
                                storage.write("emp_number", login_email_pojo.getEmp_number());
                                storage.write("ac_code", login_email_pojo.getAc_code());
                                //storage.write("is_director", login_email_pojo.getI());
                                storage.write("seen", "0");
                                storage.write("emp_year_id", login_email_pojo.getEmp_year_id());
                                storage.write("intitute_id", login_email_pojo.getInstitute_id());
                                storage.write("im_domain_name", login_email_pojo.getIm_domain_name());
                                storage.write("emp_permenant_college", login_email_pojo.getEmp_permenant_college());
                                storage.write("emp_department_id", login_email_pojo.getEmp_department_id());

                                emp_id = login_email_pojo.getEmp_id();


                                Intent intent = new Intent(Login.this, Main3Activity.class);
                                startActivity(intent);
                                finish();


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


    public void LOGIN(String final_username, String final_Password) {
        if (!storage.isOnline(this)) {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        } else {
            if (TextUtils.isEmpty(etusername.getText().toString()) || TextUtils.isEmpty(etpassword.getText().toString())) {
                if (TextUtils.isEmpty(etusername.getText().toString())) {
                    etusername.setError("enter USername");
                }
                if (TextUtils.isEmpty(etpassword.getText().toString())) {
                    etpassword.setError("enter Password");
                }
            } else {
                final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.MyTheme1);
                progressDialog.setCancelable(true);
                //progressDialog.setMessage("Please Wait");
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.show();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Map<String, String> mParams;
                mParams = new HashMap<String, String>();
               /* mParams.put("username", etusername.getText().toString());
                mParams.put("password", etpassword.getText().toString());
*/
                mParams.put("username", final_username);
                mParams.put("password", final_Password);
                System.out.println("username final_username:::" + final_username);
                System.out.println("username:::" + etusername.getText().toString());
                System.out.println("password::: final_Password" + final_Password);
                System.out.println("password:::" + etpassword.getText().toString());
                Call<LoginResponse> call = apiService.StudentLogin(mParams);
//                Call<LoginResponse> call = apiService.student_login_for_sims(mParams);

                System.out.println("LOGIN FACULTY *************" + call.request());
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.isSuccessful()) {

                            if (response.body().getstatus().equals("1")) {
                                progressDialog.dismiss();


                                storageLogin.write("UserName", etusername.getText().toString());
                                storageLogin.write("Password", etpassword.getText().toString());

                                String ID = response.body().getstud_id();
                                String NAME_USER = response.body().getname();
                                String user_ID = "";
                                String user_NAME = "";
                                String user_ID_storage;
                                String user_name_storage;

                                if (String.valueOf(login_master.read("Master_String", 3)).equals("") || String.valueOf(login_master.read("Master_String", 3)).equals(null)) {

                                    user_ID = ID;
                                    login_master.write("Master_String", user_ID);
                                    //login_master.write("Master_String", user_ID);
                                } else {

                                    user_ID_storage = String.valueOf(login_master.read("Master_String", 3));
//arraylist
                                    String str[] = user_ID_storage.split(",");
                                    List<String> al;
                                    al = Arrays.asList(str);
                                    for (String s : al) {
                                        if (al.contains(ID)) {

                                            System.out.println("s ID Value:::" + s);
                                        } else {
                                            user_ID = ID + "," + user_ID_storage;
                                            login_master.write("Master_String", user_ID);
                                        }
                                    }
                                    System.out.println("user_ID:::" + user_ID);
                                }

//                                        login_master.write("Master_String", user_ID);


                                System.out.println("user_ID aftre storage:::" + user_ID);

                                if (String.valueOf(login_master.read("Master_name", 3)).equals("") || String.valueOf(login_master.read("Master_name", 3)).equals(null)) {
                                    user_NAME = NAME_USER;
                                    login_master.write("Master_name", user_NAME);
                                } else {
                                    user_name_storage = String.valueOf(login_master.read("Master_name", 3));
                                    // user_ID = String.valueOf(login_master.read("Master_String",3));


                                    String str[] = user_name_storage.split(",");
                                    List<String> al;
                                    al = Arrays.asList(str);
                                    for (String s : al) {
                                        if (al.contains(NAME_USER)) {
                                            System.out.println("s NMAE Value:::" + s);

                                        } else {
                                            user_NAME = NAME_USER + "," + user_name_storage;
                                            login_master.write("Master_name", user_NAME);


                                        }
                                    }

                                    System.out.println("user_NAME:::" + user_NAME);


                                }
                                login_master.write("UserName" + "_" + response.body().getstud_id(), etusername.getText().toString());
                                login_master.write("Password" + "_" + response.body().getstud_id(), etpassword.getText().toString());

                                //   login_master.write("Master_name", user_NAME);
                                System.out.println("user_NAME after storage:::" + user_NAME);


                                System.out.println("userId::::" + user_ID);

                                storage.write("stud_id", response.body().getstud_id());
                                storage.write("dm_id", response.body().getdm_id());
                                storage.write("dm_full_name", response.body().getdm_full_name());
                                storage.write("course_id", response.body().getcourse_id());
                                storage.write("course_fullname", response.body().getcourse_fullname());
                                storage.write("sm_id", response.body().getsm_id());
                                storage.write("sm_name", response.body().getsm_name());
                                storage.write("ac_id", response.body().getac_id());
                                storage.write("ac_full_name", response.body().getac_full_name());
                                storage.write("swd_year_id", response.body().getswd_year_id());
                                storage.write("name", response.body().getname());
                                storage.write("stud_admission_no", response.body().getstud_admission_no());
                                storage.write("stud_photo", response.body().getstud_photo());
                                storage.write("swd_batch_id", response.body().getswd_batch_id());
                                storage.write("shift_id", response.body().getshift_id());
                                storage.write("swd_division_id", response.body().getswd_division_id());
                                storage.write("im_domain_name", response.body().getim_domain_name());
                                storage.write("fc_file", response.body().getfc_file());
                                storage.write("intitute_id", response.body().getinstitute_id());

                                storage.write("seen", "0");


                                // Toast.makeText(Login.this, "You are succesfully login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, Main3Activity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                // *** only for SIMS
//                                Api_call_Authenticate_user_by_Api(progressDialog);


                                //Employee Login
                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Map<String, String> mParams;
                                mParams = new HashMap<String, String>();
                                mParams.put("username", etusername.getText().toString());
                                mParams.put("password", etpassword.getText().toString());
                                final Call<LoginResponse> empcall = apiService.EmployeeLogin(mParams);

                                empcall.enqueue(new Callback<LoginResponse>() {
                                    @Override
                                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                        progressDialog.dismiss();
                                        if (response.isSuccessful()) {
                                            if (response.body().getstatus().equals("1")) {
                                                if (response.body().getIs_director().equals("0")) {
                                                    progressDialog.dismiss();

                                                    storage.write("emp_id", response.body().getemp_id());
                                                    storage.write("emp_main_school_id", response.body().getemp_main_school_id());
                                                    storage.write("emp_username", response.body().getemp_username());
                                                    storage.write("emp_password", response.body().getemp_password());
                                                    storage.write("ac_full_name", response.body().getac_full_name());
                                                    storage.write("ac_logo", response.body().getac_logo());
                                                    storage.write("name", response.body().getname());
                                                    storage.write("stud_photo", response.body().getstud_photo());
                                                    storage.write("emp_number", response.body().getEmp_number());
                                                    storage.write("ac_code", response.body().getAc_code());
                                                    storage.write("is_director", response.body().getIs_director());
                                                    storage.write("seen", "0");
                                                    storage.write("emp_year_id", response.body().getemp_year_id());
                                                    storage.write("intitute_id", response.body().getinstitute_id_());
                                                    storage.write("im_domain_name", response.body().getim_domain_name());
                                                    storage.write("emp_permenant_college", response.body().getemp_permenant_college());
                                                    storage.write("emp_department_id", response.body().getemp_department_id());

                                                    emp_id = response.body().getemp_id();

                                                    //Log.d("emp_id", emp_id);
//
                                                    Intent intent = new Intent(Login.this, Main3Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (response.body().getIs_director().equals("1")) {

                                                    if (response.body().getstatus().equals("1")) {

                                                        storage.write("emp_id", response.body().getemp_id());
                                                        storage.write("emp_main_school_id", response.body().getemp_main_school_id());
                                                        storage.write("emp_username", response.body().getemp_username());
                                                        storage.write("emp_password", response.body().getemp_password());
                                                        storage.write("ac_full_name", response.body().getac_full_name());
                                                        storage.write("ac_logo", response.body().getac_logo());
                                                        storage.write("name", response.body().getname());
                                                        storage.write("stud_photo", response.body().getstud_photo());
                                                        storage.write("emp_number", response.body().getEmp_number());
                                                        storage.write("ac_code", response.body().getAc_code());
                                                        storage.write("is_director", response.body().getIs_director());
                                                        storage.write("intitute_id", response.body().getinstitute_id_());
                                                        storage.write("seen", "0");
                                                        storage.write("im_domain_name", response.body().getim_domain_name());
                                                        storage.write("emp_year_id", response.body().getemp_year_id());
                                                        is_director = response.body().getIs_director();
                                                        //Log.d("is_director", is_director);


//
                                                        Intent intent = new Intent(Login.this, DirectivePageActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }

                                            } else {
                                                Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                                            }


                                        }
//                                                else
//                                                    {
//                                                    Toast.makeText(Login.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
//                                                }


                                    }

                                    @Override
                                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                                        progressDialog.dismiss();
                                        // Log error here since request failed

                                        //Log.e("Profile", t.toString());
                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Log error here since request failed
                        progressDialog.dismiss();
                        // Log.e("Profile", t.toString());
                    }
                });
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                LOGIN(etusername.getText().toString(), etpassword.getText().toString());
                /*if (!storage.isOnline(this)) {
                    showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
                } else {
                    if (TextUtils.isEmpty(etusername.getText().toString()) || TextUtils.isEmpty(etpassword.getText().toString())) {
                        if (TextUtils.isEmpty(etusername.getText().toString())) {
                            etusername.setError("enter USername");
                        }
                        if (TextUtils.isEmpty(etpassword.getText().toString())) {
                            etpassword.setError("enter Password");
                        }
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.MyTheme1);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                        progressDialog.show();
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Map<String, String> mParams;
                        mParams = new HashMap<String, String>();
                        mParams.put("username", etusername.getText().toString());
                        mParams.put("password", etpassword.getText().toString());
                        Call<LoginResponse> call = apiService.StudentLogin(mParams);

                        call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                                if (response.isSuccessful()) {

                                    if (response.body().getstatus().equals("1")) {
                                        progressDialog.dismiss();
                                        storage.write("stud_id", response.body().getstud_id());
                                        storage.write("dm_id", response.body().getdm_id());
                                        storage.write("dm_full_name", response.body().getdm_full_name());
                                        storage.write("course_id", response.body().getcourse_id());
                                        storage.write("course_fullname", response.body().getcourse_fullname());
                                        storage.write("sm_id", response.body().getsm_id());
                                        storage.write("sm_name", response.body().getsm_name());
                                        storage.write("ac_id", response.body().getac_id());
                                        storage.write("ac_full_name", response.body().getac_full_name());
                                        storage.write("swd_year_id", response.body().getswd_year_id());
                                        storage.write("name", response.body().getname());
                                        storage.write("stud_admission_no", response.body().getstud_admission_no());
                                        storage.write("stud_photo", response.body().getstud_photo());
                                        storage.write("swd_batch_id", response.body().getswd_batch_id());
                                        storage.write("shift_id", response.body().getshift_id());
                                        storage.write("swd_division_id", response.body().getswd_division_id());
                                        storage.write("im_domain_name", response.body().getim_domain_name());
                                        storage.write("fc_file", response.body().getfc_file());
                                        storage.write("intitute_id", response.body().getinstitute_id());

                                        storage.write("seen", "0");


                                        // Toast.makeText(Login.this, "You are succesfully login", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Login.this, Main3Activity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        //Employee Login
                                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                        Map<String, String> mParams;
                                        mParams = new HashMap<String, String>();
                                        mParams.put("username", etusername.getText().toString());
                                        mParams.put("password", etpassword.getText().toString());
                                        final Call<LoginResponse> empcall = apiService.EmployeeLogin(mParams);

                                        empcall.enqueue(new Callback<LoginResponse>() {
                                            @Override
                                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                                progressDialog.dismiss();
                                                if (response.isSuccessful()) {
                                                    if (response.body().getstatus().equals("1")) {
                                                        if (response.body().getIs_director().equals("0")) {
                                                            progressDialog.dismiss();

                                                            storage.write("emp_id", response.body().getemp_id());
                                                            storage.write("emp_main_school_id", response.body().getemp_main_school_id());
                                                            storage.write("emp_username", response.body().getemp_username());
                                                            storage.write("emp_password", response.body().getemp_password());
                                                            storage.write("ac_full_name", response.body().getac_full_name());
                                                            storage.write("ac_logo", response.body().getac_logo());
                                                            storage.write("name", response.body().getname());
                                                            storage.write("stud_photo", response.body().getstud_photo());
                                                            storage.write("emp_number", response.body().getEmp_number());
                                                            storage.write("ac_code", response.body().getAc_code());
                                                            storage.write("is_director", response.body().getIs_director());
                                                            storage.write("seen", "0");
                                                            storage.write("emp_year_id", response.body().getemp_year_id());
                                                            storage.write("intitute_id", response.body().getemp_year_id());
                                                            storage.write("im_domain_name", response.body().getim_domain_name());
                                                            emp_id = response.body().getemp_id();

                                                            Log.d("emp_id", emp_id);
//
                                                            Intent intent = new Intent(Login.this, Main3Activity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else if (response.body().getIs_director().equals("1")) {

                                                            if (response.body().getstatus().equals("1")) {

                                                                storage.write("emp_id", response.body().getemp_id());
                                                                storage.write("emp_main_school_id", response.body().getemp_main_school_id());
                                                                storage.write("emp_username", response.body().getemp_username());
                                                                storage.write("emp_password", response.body().getemp_password());
                                                                storage.write("ac_full_name", response.body().getac_full_name());
                                                                storage.write("ac_logo", response.body().getac_logo());
                                                                storage.write("name", response.body().getname());
                                                                storage.write("stud_photo", response.body().getstud_photo());
                                                                storage.write("emp_number", response.body().getEmp_number());
                                                                storage.write("ac_code", response.body().getAc_code());
                                                                storage.write("is_director", response.body().getIs_director());
                                                                storage.write("seen", "0");
                                                                storage.write("emp_year_id", response.body().getemp_year_id());
                                                                storage.write("im_domain_name", response.body().getim_domain_name());
                                                                storage.write("intitute_id", response.body().getinstitute_id_());

                                                                is_director = response.body().getIs_director();
                                                                Log.d("is_director", is_director);


//
                                                                Intent intent = new Intent(Login.this, DirectivePageActivity.class);
                                                                startActivity(intent);
                                                                finish();

                                                            }
                                                        }

                                                    } else {
                                                        Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                                                    }


                                                }
//                                                else
//                                                    {
//                                                    Toast.makeText(Login.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
//                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                                progressDialog.dismiss();
                                                // Log error here since request failed

                                                //Log.e("Profile", t.toString());
                                            }
                                        });
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                // Log error here since request failed
                                progressDialog.dismiss();
                                // Log.e("Profile", t.toString());
                            }
                        });
                    }

                }*/
                break;

            case R.id.tvforgotpassword:
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);


//          ppppppppppppppppppppppppppppppppp      Intent intent = new Intent(Login.this, ForgotPassword.class);
//                startActivity(intent);


              /*  DialogUtils.showDialog4YNo(Login.this, "Forgot Password ?", "You can get the password from Online HRD / Tour Programme Login.\n",
                        "http://online.jau.in/", new DialogUtils.DailogCallBackOkButtonClick() {
                            @Override
                            public void onDialogOkButtonClicked() {
//                        try {
//                            try {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            }
//                        } catch (Exception e) {
//                            //	System.out.println("");
//                        }
                            }
                        }, new DialogUtils.DailogCallBackCancelButtonClick() {
                            @Override
                            public void onDialogCancelButtonClicked() {

                            }
                        });*/
                break;

            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DataStorage.DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("No Connection");
                errorDialog.setMessage("No Internet Connnection Found.....!");
                errorDialog.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }


    public class LoginListAdapter extends BaseAdapter {

        /* String[] data;
         String[] data_id;*/
        ArrayList<String> list1;
        ArrayList<String> list2;
        Context context;
        LayoutInflater layoutInflater;


        // public LoginListAdapter(String[] data, String[] data_id,Context context)
        public LoginListAdapter(ArrayList<String> list1, ArrayList<String> list2, Context context) {
            super();
            /*this.data = data;
            this.data_id=data_id;*/

            this.list1 = list1;
            this.list2 = list2;
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (list1.get(0) != null) {
                if (list1.get(0).trim().length() > 0) {
                    System.out.println("list1 in getCount :::::::::" + list1.size());
                    return list1.size();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            convertView = layoutInflater.inflate(R.layout.login_adapter_lv, null);

            TextView txt = (TextView) convertView.findViewById(R.id.txt_name_user);
            ImageView iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete_login);

            System.out.println("txt::::" + list1.get(position));
            txt.setText(list1.get(position));
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String arr = list2.get(position);
                    System.out.println("arrrrrrrrrrrrr" + arr);
                    System.out.println("position OF name:::" + position);

                    final_username = String.valueOf(login_master.read("UserName_" + arr, 3));
                    System.out.println("final_username::::" + final_username);
                    final_Password = String.valueOf(login_master.read("Password_" + arr, 3));
                    System.out.println("final_Password::::" + final_Password);


                    etusername.setText(final_username);
                    etpassword.setText(final_Password);

                    LOGIN(final_username, final_Password);


                }
            });


            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("list size:::::::" + list2.size());
                    System.out.println("list pos:::::::" + list2.get(position));
                    if (list2.size() >= position) {
                        if (list2.get(position) != null) {


                            String id_main = list2.get(position);
                            System.out.println("id_main:::" + id_main);


                            String id_string_storage = String.valueOf(login_master.read("Master_String", 3));
                            System.out.println("id_string_storage:::" + id_string_storage);
                            if (id_string_storage.contains(id_main)) {

                                String user_name = String.valueOf(login_master.read("UserName_" + id_main, 3));
                                System.out.println("username from delete::::" + user_name);
                                login_master.clear("UserName_" + id_main);
                                System.out.println("username from delete aftre remove:::" + login_master.read("UserName_" + id_main, 3));
                                String password = String.valueOf(login_master.read("Password_" + id_main, 3));
                                System.out.println("password from delete::::" + password);
                                login_master.clear("Password_" + id_main);
                                System.out.println("id_string_storage after remove::: in if");

                                String id_string_storage_after_replace = id_string_storage.replace(id_main + "", "");
                                System.out.println("id_string_storage_after_replace after remove blank::::" + id_string_storage_after_replace);

                                if (!id_string_storage.contains(",")) {
                                    System.out.println("caleddddddddddddddddddddddddddddddddddddd");
                                    login_master.clear("Master_String");
                                }

                                if (id_string_storage_after_replace.contains(",,")) {

                                    id_string_storage_after_replace = id_string_storage_after_replace.replace(",,", ",");
                                    System.out.println("id_string_storage_after_replace after remove with double comma :::" + id_string_storage_after_replace + "");
                                    login_master.write("Master_String", id_string_storage_after_replace);
                                }
                                if (id_string_storage_after_replace.length() > 0) {


                                    String s = id_string_storage_after_replace.charAt(0) + "";

                                    System.out.println("value of s::" + s + "");
                                    if (s.contentEquals(",")) {
                                        id_string_storage_after_replace = id_string_storage_after_replace.substring(1, id_string_storage_after_replace.length());
                                        //  id_string_storage_after_replace = id_string_storage.replaceFirst(",", "");
                                        System.out.println("id_string_storage_after_replace after remove:::" + id_string_storage_after_replace + "");
                                        login_master.write("Master_String", id_string_storage_after_replace);

                                    }

                                    //int s1 = id_string_storage_after_replace.lastIndexOf(",");
                                    String s2 = id_string_storage_after_replace.charAt(id_string_storage_after_replace.length() - 1) + "";
                                    //  String s3 =id_string_storage_after_replace.charAt(s1)+"";

                                    if (s2.contentEquals(",")) {
                                        id_string_storage_after_replace = id_string_storage_after_replace.substring(0, id_string_storage_after_replace.length() - 1);
                                        login_master.write("Master_String", id_string_storage_after_replace);
                                        System.out.println("id_string_storage_after_replace last char " + id_string_storage_after_replace);
                                    }

                                }
                            }

                            String id_name = list1.get(position);
                            System.out.println("id_name::::" + id_name);
                            String id_string_storage_name = String.valueOf(login_master.read("Master_name", 3));

                            if (id_string_storage_name.contains(id_name)) {

                                if (!id_string_storage_name.contains(",")) {
                                    login_master.clear("Master_name");
                                }
                                System.out.println("id_string_storage after remove::: in if name");

                                String id_string_storage_after_replace_name = id_string_storage_name.replace(id_name + "", "");
                                System.out.println("id_string_storage_after_replace after remove blank name::::" + id_string_storage_after_replace_name);


                                if (id_string_storage_after_replace_name.contains(",,")) {

                                    id_string_storage_after_replace_name = id_string_storage_after_replace_name.replace(",,", ",");
                                    System.out.println("id_string_storage_after_replace after remove with double comma name:::" + id_string_storage_after_replace_name + "");
                                    login_master.write("Master_name", id_string_storage_after_replace_name);
                                }
                                if (id_string_storage_after_replace_name.length() > 0) {
                                    String s = id_string_storage_after_replace_name.charAt(0) + "";
                                    System.out.println("value of s name::" + s + "");
                                    if (s.contentEquals(",")) {
                                        id_string_storage_after_replace_name = id_string_storage_after_replace_name.substring(1, id_string_storage_after_replace_name.length());
                                        //  id_string_storage_after_replace = id_string_storage.replaceFirst(",", "");
                                        System.out.println("id_string_storage_after_replace after remove name:::" + id_string_storage_after_replace_name + "");
                                        login_master.write("Master_name", id_string_storage_after_replace_name);

                                    }

                                    String s2 = id_string_storage_after_replace_name.charAt(id_string_storage_after_replace_name.length() - 1) + "";

                                    if (s2.contentEquals(",")) {
                                        id_string_storage_after_replace_name = id_string_storage_after_replace_name.substring(0, id_string_storage_after_replace_name.length() - 1);
                                        System.out.println("id_string_storage_after_replace last char name" + id_string_storage_after_replace_name);
                                        login_master.write("Master_name", id_string_storage_after_replace_name);
                                    }
                                }


                            }


                            System.out.println("id_string_storage after remove:::" + id_string_storage);
                            System.out.println("print id of clicked position::::" + id_string_storage);


                            String s = String.valueOf(login_master.read("Master_name", 3));
                            String s1 = String.valueOf(login_master.read("Master_String", 3));

                            System.out.println("s::::::::::::::::" + s);
                            System.out.println("s1::::::::::::::::" + s1);

                            String[] name_array_;
                            String[] id_array;
                            ArrayList<String> sList_new_1 = new ArrayList<>();
                            ArrayList<String> sList_new = new ArrayList<>();

                            if (s.contains(",") && s1.contains(",")) {
                                name_array_ = s.split(",");
                                id_array = s1.split(",");


                                sList_new_1 = new ArrayList(Arrays.asList(id_array));
                                sList_new = new ArrayList(Arrays.asList(name_array_));
                            } else {
                                sList_new.add(s + "");
                                sList_new_1.add(s1 + "");
                            }


                            adapter_login = new LoginListAdapter(sList_new, sList_new_1, Login.this);
                            lv_login.setAdapter(adapter_login);
                        }

                    }
                }
            });


            return convertView;
        }
    }

}
