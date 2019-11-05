package com.infinity.infoway.gsfc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomButton;
import com.infinity.infoway.gsfc.CommonCls.CustomEditText;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.FileUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.InternShipSavePojo;
import com.infinity.infoway.gsfc.model.InternshipNamePojo;
import com.infinity.infoway.gsfc.model.MultipartResponsePojo;
import com.infinity.infoway.gsfc.model.PunchInPunchOutpojo;
import com.infinity.infoway.gsfc.model.PunchPojo;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiClientMultipart;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.io.File;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class InternshipWorkReport extends AppCompatActivity implements View.OnClickListener {

    protected CustomTextView title;
    protected Toolbar toolbar;
    protected CustomBoldTextView tvNameInternship;
    protected Spinner spin_internship_name;
    protected CustomBoldTextView tvReportTitle;
    protected CustomEditText edtReportTitle;
    protected CustomBoldTextView tvDescReport;
    protected CustomEditText edtDescReport;
    protected CustomBoldTextView tvGrpType;
    protected ImageView fileComplsory;
    protected CustomEditText fileUpload;
    protected CustomButton btnUploadFileAssign;
    protected LinearLayout linFileupload;
    protected CustomBoldTextView tvSave;
    protected FloatingActionButton fab;
    protected CustomBoldTextView tvDesc;
    protected CustomEditText edtDescELerning;
    protected LinearLayout llElerning;
    protected ScrollView scroll;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    private ApiInterface apiService;
    RequestQueue queue;
    DataStorage storage;
    ArrayList<String> InternshipList;
    ArrayList<String> InternshipIDList;
    MultipartBody.Part file_document;

    boolean FILE_UPLOADED_OR_NOT = false;

    String Internship_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_internship_work_report);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        initView();
        InternshipnameBindApi();
    }

    private void InternshipnameBindApi() {

        DialogUtils.showProgressDialog(InternshipWorkReport.this, "");
        String URLs = URl.Bind_assign_intenship_to_student_API + "&student_id=" + String.valueOf(storage.read("stud_id", 3)) + "";
//        String URLs = URl.Bind_assign_intenship_to_student_API + "&student_id=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Bind_assign_intenship_to_student_API calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";

                        response = "{\"Data\":" + response + "}";

                        System.out.println("THIS IS Bind_assign_intenship_to_student_API RESPONSE    " + response + "");

                        if (response.length() >5) {
                            Gson gson = new Gson();
                            InternshipList = new ArrayList<>();
                            InternshipIDList = new ArrayList<>();
                            InternshipNamePojo internshipNamePojo = gson.fromJson(response, InternshipNamePojo.class);

                            if (internshipNamePojo != null && internshipNamePojo.getData().size() > 0)
                            {

                                InternshipList.add("Select Internship Name");
                                InternshipIDList.add("0");


                                for (int i = 0; i < internshipNamePojo.getData().size(); i++)
                                {
                                    InternshipList.add(internshipNamePojo.getData().get(i).getInts_name() + "");
                                    InternshipIDList.add(internshipNamePojo.getData().get(i).getInts_id() + "");
                                }
                                SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(InternshipWorkReport.this, InternshipList);

                                spin_internship_name.setAdapter(spinnerSimpleAdapter);

                                spin_internship_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Internship_ID = InternshipIDList.get(i);

                                        System.out.println("Internship_ID::::::::::::::::::::::" + Internship_ID);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }


                        } else {
                            //  DialogUtils.Show_Toast(ViewLeaveApplication.this,"No")
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }


    public boolean ISVALID() {

        if (Internship_ID.compareToIgnoreCase("0") == 0 || Internship_ID.contentEquals("")) {
            DialogUtils.Show_Toast(InternshipWorkReport.this, "Select Internship");

            return false;
        } else if (edtReportTitle.getText().toString().trim().contentEquals("") || edtReportTitle.getText().toString().trim().isEmpty() || edtReportTitle.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(InternshipWorkReport.this, "Enter Report Title");
            return false;

        } else if (edtDescReport.getText().toString().trim().contentEquals("") || edtDescReport.getText().toString().trim().isEmpty() || edtDescReport.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(InternshipWorkReport.this, "Enter Report Description");
            return false;
        } else if (!FILE_UPLOADED_OR_NOT) {
            DialogUtils.Show_Toast(InternshipWorkReport.this, "Upload Document");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_save) {
            if (ISVALID()) {

                Api_call_multipart_retrofit();

//                Form_Data_Save_API();
            }

        }
        if (view.getId() == R.id.btn_upload_file_assign) {
            opendialog();
        }


    }

    private void Form_Data_Save_API() {

        DialogUtils.showProgressDialog(InternshipWorkReport.this, "");
        String URLs = URl.Insert_Student_Work_Report_API + "&ints_id=" + Internship_ID + "&report_title=" + edtReportTitle.getText().toString() + "&inst_student_id=" + String.valueOf(storage.read("stud_id", 3)) + "&ints_description=" + edtDescReport.getText().toString() + "&ints_document=" + fileUpload.getText().toString().trim() + "&ints_created_by=" + String.valueOf(storage.read("stud_id", 3)) + "&ints_created_ip=" + "1" + "";
//        String URLs = URl.Bind_assign_intenship_to_student_API + "&student_id=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Insert_Student_Work_Report_API calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";

                        response = "{\"Data\":" + response + "}";

                        System.out.println("THIS IS Insert_Student_Work_Report_API RESPONSE    " + response + "");

                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            InternShipSavePojo internShipSavePojo = gson.fromJson(response, InternShipSavePojo.class);

                            if (internShipSavePojo != null && internShipSavePojo.getData().size() > 0) {

                                DialogUtils.Show_Toast(InternshipWorkReport.this, internShipSavePojo.getData().get(0).getMsg() + "");

                                Intent intent = new Intent(InternshipWorkReport.this, ViewInternshipWorkReportActivity.class);
                                startActivity(intent);
                                finish();

                            }


                        } else {
                            //  DialogUtils.Show_Toast(ViewLeaveApplication.this,"No")
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    private void opendialog() {
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        } else {
            final CharSequence[] options = {"Choose from Gallery", "Upload Pdf", "Cancel"};
//            final CharSequence[] options = {"Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(InternshipWorkReport.this);
            builder.setTitle("Add Document");

            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Choose from Gallery")) {
                        String[] mimeTypes =
                                {
                                        "image/jpeg", "image/jpg", "image/png"
                                };
                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");
                        // intent.setType("image/jpeg");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                            if (mimeTypes.length > 0) {
                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            }
                        } else {
                            String mimeTypesStr = "";
                            for (String mimeType : mimeTypes) {
                                mimeTypesStr += mimeType + "|";
                            }
                            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                        }
                        startActivityForResult(intent, 1);//2=> 12,22,32,42

                    } else if (options[item].equals("Upload Pdf")) {
                        browseDocuments(2);


                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });

            builder.show();
        }
    }

    private void browseDocuments(int code) {

        String[] mimeTypes =
                {
                        "application/pdf",
                };

        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
//        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQUEST_CODE_DOC);

        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);


        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), code);

    }


    public void Api_call_multipart_retrofit() {


//        System.out.println("STUD_ID in multipart ::::::" + STUD_ID);
//        System.out.println("Internship_ID in multipart ::::::" + Internship_ID);


        DialogUtils.showProgressDialog(InternshipWorkReport.this, "");

//
        RequestBody STUD_ID_api = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(storage.read("stud_id", 3)));
        RequestBody Internship_ID_ = RequestBody.create(MediaType.parse("text/plain"), Internship_ID);
        RequestBody KEY_api = RequestBody.create(MediaType.parse("text/plain"), "7D1K97ms4A");
        RequestBody report_title = RequestBody.create(MediaType.parse("text/plain"), edtReportTitle.getText().toString());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), edtDescReport.getText().toString());
        RequestBody ints_created_by = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(storage.read("stud_id", 3)));
        RequestBody ints_created_ip = RequestBody.create(MediaType.parse("text/plain"), "");


        Call<MultipartResponsePojo> call = apiService.multipartImageUpload(file_document, STUD_ID_api, Internship_ID_, KEY_api, report_title, desc, ints_created_by, ints_created_ip);

        call.enqueue(new Callback<MultipartResponsePojo>() {
            @Override
            public void onResponse(Call<MultipartResponsePojo> call, final retrofit2.Response<MultipartResponsePojo> response)
            {
                DialogUtils.hideProgressDialog();


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful())
                {

                    System.out.println("response " + response + "");

                    if (response.body().getStatus().contentEquals("1"))
                    {

//                        FILE_UPLOADED_OR_NOT = true;
                        DialogUtils.Show_Toast(InternshipWorkReport.this, response.body().getMsg());

                       // Intent intent = new Intent(InternshipWorkReport.this, ViewInternshipWorkReportActivity.class);
                       // startActivity(intent);
                        finish();
                    }
                    else
                        {
                       // Intent intent = new Intent(InternshipWorkReport.this, ViewInternshipWorkReportActivity.class);
                        //startActivity(intent);
                        finish();
                        DialogUtils.Show_Toast(InternshipWorkReport.this, response.body().getMsg());
                    }

                } else {


                    System.out.println("error in response :::::::::::::::::" + response.toString());
                    System.out.println("error in response :::::::::::::::::" + response.body());
                }

            }

            @Override
            public void onFailure(Call<MultipartResponsePojo> call, Throwable t)
            {
                System.out.println("exception in response :::::::::::::::::" + t);
                DialogUtils.hideProgressDialog();
//
            }
        });


    }


    String EXTENTION = "";

    String TAG = "FILE UPLOAD ::::::::::::::: ";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        FILE_UPLOADED_OR_NOT = false;
        EXTENTION = "";
        if (resultCode == RESULT_OK) {

            if (requestCode == 1 && data != null) {

                Uri uri = data.getData();
                try {

                    String filePath = FileUtils.getPath(InternshipWorkReport.this, uri);
                    System.out.println("filepath multipart:::::::::::" + filePath);
//                    MultipartBody.Part file_document;
                    File file = new File(filePath);
                    if (file.exists()) {

                        System.out.println("YESSS FILE EXISTS!!!!!!!!!!!!!!!");
                        EXTENTION = file.getName().substring(file.getName().lastIndexOf(".") + 1);

                        fileUpload.setText(file.getName() + "");
                        FILE_UPLOADED_OR_NOT = true;
                        RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), file);
                        file_document = MultipartBody.Part.createFormData("postedFile", file.getName(), mFile);
                        System.out.println("CHECK !!!!!!!!!!!!!!!!!! ");

                        // API_Call_Multipart_Volley(file_document);
                        // Api_call_multipart_retrofit(file_document, String.valueOf(storage.read("stud_id", 3)), Internship_ID, "7D1K97ms4A");

                    } else {
                        System.out.println("FILE NOT FOUND!!!!!!!!!!!!!!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    DialogUtils.Show_Toast(InternshipWorkReport.this, "Something Went Wrong");
                }

            } else if (requestCode == 2 && data != null) {

                Uri uri = data.getData();
                try {

//                    String filePath = PathUtil.getPath(InternshipWorkReport.this, uri);
                    String filePath = FileUtils.getPath(InternshipWorkReport.this, uri);

                    System.out.println("filepath multipart:::::::::::" + filePath);
//                    MultipartBody.Part file_document;
                    File file = new File(filePath);
                    if (file.exists()) {
                        System.out.println("YESSS FILE EXISTS!!!!!!!!!!!!!!!");
                        EXTENTION = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        fileUpload.setText(file.getName() + "");

                        FILE_UPLOADED_OR_NOT = true;

                        RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), file);
                        file_document = MultipartBody.Part.createFormData("postedFile", file.getName(), mFile);
                        System.out.println("CHECK !!!!!!!!!!!!!!!!!! ");

                        // Api_call_multipart_retrofit(file_document, String.valueOf(storage.read("stud_id", 3)), Internship_ID, "7D1K97ms4A");

                    } else {
                        System.out.println("FILE NOT FOUND!!!!!!!!!!!!!!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    DialogUtils.Show_Toast(InternshipWorkReport.this, "Something Went Wrong");
                }

            }
        }
    }


    private void initView() {
        queue = Volley.newRequestQueue(this);
        storage = new DataStorage("Login_Detail", this);
//        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService = ApiClientMultipart.getClient().create(ApiInterface.class);
        title = (CustomTextView) findViewById(R.id.title);

        tvNameInternship = (CustomBoldTextView) findViewById(R.id.tv_name_internship);
        spin_internship_name = (Spinner) findViewById(R.id.spin_internship_name);
        tvReportTitle = (CustomBoldTextView) findViewById(R.id.tv_report_title);
        edtReportTitle = (CustomEditText) findViewById(R.id.edt_report_title);
        tvDescReport = (CustomBoldTextView) findViewById(R.id.tv_desc_report);
        edtDescReport = (CustomEditText) findViewById(R.id.edt_desc_report);
        tvGrpType = (CustomBoldTextView) findViewById(R.id.tv_grp_type);
        fileComplsory = (ImageView) findViewById(R.id.file_complsory);
        fileUpload = (CustomEditText) findViewById(R.id.file_upload);
        btnUploadFileAssign = (CustomButton) findViewById(R.id.btn_upload_file_assign);
        btnUploadFileAssign.setOnClickListener(this);
        linFileupload = (LinearLayout) findViewById(R.id.lin_fileupload);
        tvSave = (CustomBoldTextView) findViewById(R.id.tv_save);
        tvSave.setOnClickListener(InternshipWorkReport.this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvDesc = (CustomBoldTextView) findViewById(R.id.tv_desc);
        edtDescELerning = (CustomEditText) findViewById(R.id.edt_desc_e_lerning);
        llElerning = (LinearLayout) findViewById(R.id.ll_elerning);
        scroll = (ScrollView) findViewById(R.id.scroll);
    }
}
