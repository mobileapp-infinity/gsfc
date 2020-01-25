package com.infinity.infoway.gsfc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.infinity.infoway.gsfc.CommonCls.PathUtil;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.LeaveLectureAdapter;
import com.infinity.infoway.gsfc.adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.CheckLeavePojo;
import com.infinity.infoway.gsfc.model.FileUpload;
import com.infinity.infoway.gsfc.model.LeaveApply;
import com.infinity.infoway.gsfc.model.LeaveDocumentPojo;
import com.infinity.infoway.gsfc.model.LeaveTypes;
import com.infinity.infoway.gsfc.model.Lectures;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LeaveStudentApply extends AppCompatActivity {
    Context ctx;
    LinearLayout lin_lec_header;
    ArrayList<String> leave_Type;
    private CustomTextView title;
    private Toolbar toolbar;
    private CustomBoldTextView tvgpname;
    private Spinner spinleavekind;
    private CustomBoldTextView tvgptype;
    private Spinner spinleavetype;
    private CustomEditText edtfromdat;
    private CustomEditText edttodat;
    private ExpandableHeightListView lvlectures;
    private CustomBoldTextView tvgrptype;
    private CustomButton btnuploadfileassign;
    private CustomBoldTextView tvasgnname;
    private CustomEditText edtassignname;
    private CustomBoldTextView tvdesc;
    private CustomEditText edtdescelerning;
    private LinearLayout llelerning;
    private CustomBoldTextView tvsave;
    private FloatingActionButton fab;
    RequestQueue queue;
    private LinearLayout linfileupload;
    private ImageView filecomplsory;
    String intitute_id = "";
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    String LEAVE_TYPE_ID = "";
    String KIND_OF_LEAVE_TYPE_ID = "";
    String STUDENT_ID = "", YEAR_ID = "", REMARKS = "", CREATED_BY = "", LEAVE_TAKEN_BY = "student", INSTITUTE_ID = "", SEM_ID = "", DIV_ID = "", TODAY_DATE = "";
    Date FROM_DATE, TO_DATE;
    private LinearLayout linlecheader;
    private CustomEditText edfileupload;


    /*1-Partially Leave
2-Full Leave*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_student_actiivty);
        this.edfileupload = (CustomEditText) findViewById(R.id.file_upload);
        this.linlecheader = (LinearLayout) findViewById(R.id.lin_lec_header);
        this.filecomplsory = (ImageView) findViewById(R.id.file_complsory);
        this.linfileupload = (LinearLayout) findViewById(R.id.lin_fileupload);
        this.lin_lec_header = (LinearLayout) findViewById(R.id.lin_lec_header);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.tvsave = (CustomBoldTextView) findViewById(R.id.tv_save);
        this.llelerning = (LinearLayout) findViewById(R.id.ll_elerning);
        this.edtdescelerning = (CustomEditText) findViewById(R.id.edt_desc_e_lerning);
        this.tvdesc = (CustomBoldTextView) findViewById(R.id.tv_desc);
        this.edtassignname = (CustomEditText) findViewById(R.id.edt_assign_name);
        this.tvasgnname = (CustomBoldTextView) findViewById(R.id.tv_asgn_name);
        this.btnuploadfileassign = (CustomButton) findViewById(R.id.btn_upload_file_assign);
        this.tvgrptype = (CustomBoldTextView) findViewById(R.id.tv_grp_type);
        this.lvlectures = (ExpandableHeightListView) findViewById(R.id.lv_lectures);
        lvlectures.setExpanded(true);
        this.edttodat = (CustomEditText) findViewById(R.id.edt_to_dat);
        this.edtfromdat = (CustomEditText) findViewById(R.id.edt_from_dat);
        this.spinleavetype = (Spinner) findViewById(R.id.spin_leavetype);
        this.tvgptype = (CustomBoldTextView) findViewById(R.id.tv_gp_type);
        this.spinleavekind = (Spinner) findViewById(R.id.spin_leave_kind);
        this.tvgpname = (CustomBoldTextView) findViewById(R.id.tv_gp_name);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.title = (CustomTextView) findViewById(R.id.title);
        linfileupload.setVisibility(View.GONE);
        ctx = getApplicationContext();
        queue = Volley.newRequestQueue(ctx);
        leave_Type = new ArrayList<>();
        leave_Type.add("Select Leave Type");
        leave_Type.add("Partially Leave");
        leave_Type.add("Full Leave");
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
        edfileupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
        btnuploadfileassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        TODAY_DATE = format.format(today);
        System.out.println("TODAY_DATE      " + TODAY_DATE + "");

        DataStorage cstorage = new DataStorage("Login_Detail", this);
        if (cstorage.CheckLogin("stud_id", this)) {
            intitute_id = cstorage.read("intitute_id", 3) + "";
            STUDENT_ID = cstorage.read("stud_id", 3) + "";
            CREATED_BY = cstorage.read("stud_id", 3) + "";
            YEAR_ID = cstorage.read("swd_year_id", 3) + "";
            INSTITUTE_ID = cstorage.read("intitute_id", 3) + "";
            SEM_ID = cstorage.read("sm_id", 3) + "";
            DIV_ID = cstorage.read("swd_division_id", 3) + "";
            getKindOfLeaveType(intitute_id);
        }
        SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(ctx, leave_Type);
        spinleavetype.setAdapter(spinnerSimpleAdapter);
        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    CheckLeaveIsExistsOrNot();
                }

              /*  if (LEAVE_TYPE_ID.compareToIgnoreCase("2") == 0) {
                    if (validateData()) {

                        if (TO_DATE.after(FROM_DATE) || TO_DATE.equals(FROM_DATE)) {
                            System.out.println("1111111111111111111111 ");
                            insert_student_leave_API();
                        } else {
                            DialogUtils.Show_Toast(LeaveStudentApply.this, " To-Date Should Be Greater Than From-Date ");
                        }
                    }
                } else {

                    if (validateData()) {
                        System.out.println("2222222222222222222222222222222 ");
                        insert_student_leave_API();
                    }


                }*/

            }

        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();

            }

        };
        edtfromdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Date result = myCalendar.getTime();

                DatePickerDialog d=  new DatePickerDialog(LeaveStudentApply.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                /*disable all dates older than current*/
                d.getDatePicker().setMinDate(result.getTime());
                d.show();

              /*  new DatePickerDialog(LeaveStudentApply.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });
        edttodat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Date result1 = myCalendar2.getTime();

                DatePickerDialog datePickerDialog =   new DatePickerDialog(LeaveStudentApply.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH));

                if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0 && (!edtfromdat.getText().toString().isEmpty() && !edtfromdat.getText().toString().contentEquals(""))) {
                    edttodat.setText(edtfromdat.getText().toString());
                    edttodat.setFocusable(false);
                    getLectures();
                } else {
                    /*disable all dates older than current*/
                    datePickerDialog.getDatePicker().setMinDate(result1.getTime());
                    datePickerDialog.show();
                }




/*                if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0 && (!edtfromdat.getText().toString().isEmpty() && !edtfromdat.getText().toString().contentEquals(""))) {
                    edttodat.setText(edtfromdat.getText().toString());
                    edttodat.setFocusable(false);
                    getLectures();
                } else {
                    new DatePickerDialog(LeaveStudentApply.this, date2, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
                }*/


            }
        });
      /*  SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(ctx, leave_Type);
        spinleavetype.setAdapter(spinnerSimpleAdapter);*/
        spinleavetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LEAVE_TYPE_ID = 0 + "";
                lvlectures.setVisibility(View.GONE);
                lin_lec_header.setVisibility(View.GONE);
                if (i > 0) {

                    LEAVE_TYPE_ID = i + "";

                    // ****************** file is compusory or not 01 nov 2019 nirali ************************
                    // get_leave_type_file_upload(i + "");

                    if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0) {
//                        getLectures();
                        if (lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0) {
                            lvlectures.setVisibility(View.VISIBLE);
                            lin_lec_header.setVisibility(View.VISIBLE);
                        }

                    } else {
                        lvlectures.setVisibility(View.GONE);
                        lin_lec_header.setVisibility(View.GONE);
                    }

                }
                System.out.println("LEAVE_TYPE_ID ::::::::::::: " + LEAVE_TYPE_ID + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinleavekind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                KIND_OF_LEAVE_TYPE_ID = 0 + "";
                if (i > 0) {
                    KIND_OF_LEAVE_TYPE_ID = leaveTypes.getTable().get(i - 1).getLt_id();
                    get_leave_type_file_upload(KIND_OF_LEAVE_TYPE_ID);
                }
                System.out.println("KIND_OF_LEAVE_TYPE_ID ::::::::::::: " + KIND_OF_LEAVE_TYPE_ID + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        getLectures();
    }

    private void CheckLeaveIsExistsOrNot()
    {

        String lectureNo = "", lecture_date = "";
        System.out.println("click :::::::::::::::::::::: ");
//        if (adapter != null && adapter.getSelected() != null) {
//            lectureNo = adapter.getSelected().toString();
//        }
        if ((lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0) && (adapter != null && adapter.getSelected() != null) && lvlectures.getVisibility() == View.VISIBLE)
        {
            for (int i = 0; i < lectures.getTable().size(); i++)
            {
                if (!adapter.getSelected().contains(lectures.getTable().get(i).getDL_LEC_NO() + ""))
                {
                    lectureNo = lectureNo + "," + lectures.getTable().get(i).getDL_LEC_NO() + "";
                }
            }
            System.out.println("THIS ARE LECTURE NOS CheckLeaveIsExistsOrNot !!!!!!!!!!!!!!!!!!!!!!!!" + lectureNo + "");
            lectureNo = lectureNo.replaceFirst(",", "");
            System.out.println("THIS ARE LECTURE NOS CheckLeaveIsExistsOrNot :::::  " + lectureNo + "");

        }

        System.out.println("LEAVE_TYPE_ID in check leave exists or not !!!!!!!!!!!!!!!!!!!!!!!! " + LEAVE_TYPE_ID);
        String url = URl.Check_Student_Leave_Exist_API + "&stud_id=" + STUDENT_ID + "&year_id=" + YEAR_ID + "&leave_from_date=" + edtfromdat.getText().toString() + "&leave_to_date=" + edttodat.getText().toString() + "&leave_day_type=" + LEAVE_TYPE_ID + "&lect_no=" + lectureNo + "";

        url = url.replace(" ", "%20");
        System.out.println("this is final URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                System.out.println("insert leave response :::::::::::::" + response + "");

                Gson gson = new Gson();
                CheckLeavePojo checkLeavePojo1 = gson.fromJson(response, CheckLeavePojo.class);

                if (checkLeavePojo1 != null)
                {
                    if (checkLeavePojo1.getTable() != null)
                    {
                        if (checkLeavePojo1.getTable().size() > 0)
                        {
                            if (checkLeavePojo1.getTable().get(0).getExist_status().compareToIgnoreCase("0") == 0)
                            {

                                if (LEAVE_TYPE_ID.compareToIgnoreCase("2") == 0)
                                {
                                    if (TO_DATE != null && FROM_DATE != null)
                                    {
                                        if (TO_DATE.after(FROM_DATE) || TO_DATE.equals(FROM_DATE))
                                        {
                                            System.out.println("1111111111111111111111 ");
                                            insert_student_leave_API();
                                        }
                                        else
                                            {
                                            DialogUtils.Show_Toast(LeaveStudentApply.this, " To-Date Should Be Greater Than From-Date ");
                                        }

                                    }


                                } else {
                                    System.out.println("2222222222222222222222222222222 ****************** #####################");
                                    insert_student_leave_API();


                                }


                            }
                            else
                                {
                                DialogUtils.Show_Toast(LeaveStudentApply.this, "Leave Alredy Exists");
                            }
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("this is errror in insert leave ::::::::::: ");
                DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Try again!");
            }
        });
        queue.add(request);


    }

    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

    private void opendialog() {
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        } else {
//            final CharSequence[] options = {"Choose from Gallery", "Upload Pdf", "Cancel"};
            final CharSequence[] options = {"Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(LeaveStudentApply.this);
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

                    }
                   /* else if (options[item].equals("Upload Pdf")) {
                        browseDocuments(2);


                    } */
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });

            builder.show();
        }
    }

    String EXTENTION = "";

    private void insert_student_leave_API() {
        String lectureNo = "", lecture_date = "";
        System.out.println("click :::::::::::::::::::::: ");
//        if (adapter != null && adapter.getSelected() != null) {
//            lectureNo = adapter.getSelected().toString();
//        }
        if ((lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0) && (adapter != null && adapter.getSelected() != null) && lvlectures.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < lectures.getTable().size(); i++) {
                if (!adapter.getSelected().contains(lectures.getTable().get(i).getDL_LEC_NO() + "")) {
                    lectureNo = lectureNo + "," + lectures.getTable().get(i).getDL_LEC_NO() + "";
                }
            }
            System.out.println("THIS ARE LECTURE NOS " + lectureNo + "");
            lectureNo = lectureNo.replaceFirst(",", "");
            System.out.println("THIS ARE LECTURE NOS:::::  " + lectureNo + "");

        }
        if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0 && lectureNo.contentEquals("") && lvlectures.getVisibility() == View.VISIBLE) {
            //   if (lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0 &&lvlectures.getVisibility()==View.VISIBLE) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Lectures");
            //  }

            //   String myFormat = "yyyy-MM-dd"; //In which you need put here
            //   SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            //      FROM_DATE = sdf.format(myCalendar.getTime());

//        try {
//            FROM_DATE = sdf.parse(sdf.format(myCalendar.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

            // edtfromdat.setText(sdf.format(myCalendar.getTime()));

        }


//        String url = URl.insert_student_leave_API + "&student_id=" + STUDENT_ID + "&created_by=" + STUDENT_ID + "&year_id=" + YEAR_ID + "&Remarks=" + REMARKS + "&leave_type_id=" + LEAVE_TYPE_ID + "&from_date=" + FROM_DATE + "&to_date=" + TO_DATE + "&leave_day_type=" + KIND_OF_LEAVE_TYPE_ID + "&lecture_date=" + FROM_DATE + "&lecture_no=" + lectureNo + "&Remarks=" + REMARKS + "";
//        System.out.println("this is final URL " + url + "");

        else {
            lecture_date = edtfromdat.getText().toString();

            String temp[] = lecture_date.split("-");
            lecture_date = temp[2] + "-" + temp[1] + "-" + temp[0];

            System.out.println("all is OK calling API  !!!!!!!!!!! ");
            /* String url = URl.insert_student_leave_API + "&student_id=" + STUDENT_ID + "&created_by=" + STUDENT_ID + "&year_id=" + YEAR_ID + "&Remarks=" + REMARKS + "&leave_type_id=" + LEAVE_TYPE_ID + "&from_date=" + edtfromdat.getText().toString() + "&to_date=" + edttodat.getText().toString() + "&leave_day_type=" + KIND_OF_LEAVE_TYPE_ID + "&lecture_date=" + edtfromdat.getText().toString() + "&lecture_no=" + lectureNo + "&created_ip=1" + "&leave_taken_by=" + LEAVE_TAKEN_BY + "";*/

            String url = URl.insert_student_leave_API + "&student_id=" + STUDENT_ID + "&created_by=" + STUDENT_ID + "&year_id=" + YEAR_ID + "&Remarks=" + REMARKS + "&leave_type_id=" + KIND_OF_LEAVE_TYPE_ID + "&from_date=" + edtfromdat.getText().toString() + "&to_date=" + edttodat.getText().toString() + "&leave_day_type=" + LEAVE_TYPE_ID + "&lecture_date=" + lecture_date + "&lecture_no=" + lectureNo + "&created_ip=1" + "&leave_taken_by=" + LEAVE_TAKEN_BY + "";

//            String url = URl.insert_student_leave_API + "&student_id=" + STUDENT_ID + "&created_by=" + STUDENT_ID + "&year_id=" + YEAR_ID + "&Remarks=" + REMARKS + "&leave_type_id=" + LEAVE_TYPE_ID + "&from_date=" + edtfromdat.getText().toString() + "&to_date=" + edttodat.getText().toString() + "&leave_day_type=" + KIND_OF_LEAVE_TYPE_ID + "&lecture_date=" + lecture_date + "&lecture_no=" + lectureNo + "&created_ip=1" + "&leave_taken_by=" + LEAVE_TAKEN_BY + "";

            url = url.replace(" ", "%20");
            System.out.println("this is final URL " + url + "");
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("insert leave response " + response + "");
                    LeaveApply leave = new LeaveApply();
                    Gson gson = new Gson();
                    leave = gson.fromJson(response, LeaveApply.class);
                    if (Integer.parseInt(leave.getTable().get(0).getInsert_status()) > 0) {
                        sendPhotoToServer(imgByteArray, leave.getTable().get(0).getInsert_status(), leave.getTable().get(0).getInsert_status() + EXTENTION + "");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("this is errror in insert leave ::::::::::: ");
                    DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Try again!");
                }
            });
            queue.add(request);
        }
    }


    private boolean validate() {
        //  boolean flag = false;
        boolean flag = true;
        REMARKS = edtassignname.getText().toString().trim();
        int kindofleave = Integer.parseInt(KIND_OF_LEAVE_TYPE_ID + "");
        if (KIND_OF_LEAVE_TYPE_ID.contentEquals("0") || KIND_OF_LEAVE_TYPE_ID.contentEquals("")) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Kind of Leave ");
            flag = false;
            return false;
        } else if (LEAVE_TYPE_ID.contentEquals("0") || LEAVE_TYPE_ID.contentEquals("")) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Leave Type ");
            flag = false;
            return false;
        } else if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0 && adapter.getSelected().size() == 0) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select lectures ");
            flag = false;
            return false;
        } else if (edtfromdat.getText().toString().trim().length() < 3 || edttodat.getText().toString().trim().length() < 3) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please select From-Date and To-Date");
            flag = false;
            return false;
        }
        // else if (!TO_DATE.after(FROM_DATE) || !TO_DATE.equals(FROM_DATE))
        else if ((lt_is_doc_attachment.contentEquals("1") || lt_is_doc_compulsory.contentEquals("1")) && !FILE_UPLOADED_OR_NOT) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Document");
            flag = false;
            return false;
        } else if (REMARKS.length() < 5) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please fill remarks ");
            flag = false;
            return false;
        }
//        else if (TO_DATE.after(FROM_DATE)||TO_DATE.equals(FROM_DATE))
//        {
//            System.out.println("TO_DATE in validateData:::::"+TO_DATE);
//            System.out.println("FROM_DATE in validateData:::::"+FROM_DATE);
//            DialogUtils.Show_Toast(LeaveStudentApply.this, " To-Date Should Be Greater Than From-Date ");
//            flag = false;
//            return false;
//        }
        return flag;
    }

    private boolean validateData() {
        //  boolean flag = false;
        boolean flag = true;
        REMARKS = edtassignname.getText().toString().trim();
        int kindofleave = Integer.parseInt(KIND_OF_LEAVE_TYPE_ID + "");
        if (KIND_OF_LEAVE_TYPE_ID.contentEquals("0") || KIND_OF_LEAVE_TYPE_ID.contentEquals("")) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Kind of Leave ");
            flag = false;
            return false;
        } else if (LEAVE_TYPE_ID.contentEquals("0") || LEAVE_TYPE_ID.contentEquals("")) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Leave Type ");
            flag = false;
            return false;
        } else if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0 && adapter != null && adapter.getSelected().size() == 0) {
            System.out.println(" lectures.getTable().size()  " + lectures.getTable().size() + "");
            if (lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0 && lvlectures.getVisibility() == View.VISIBLE) {
                DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select lectures ");
                flag = false;
                return false;
            }
        } else if (edtfromdat.getText().toString().trim().length() < 3 || edttodat.getText().toString().trim().length() < 3) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please select From-Date and To-Date");
            flag = false;
            return false;
        }
        // else if (!TO_DATE.after(FROM_DATE) || !TO_DATE.equals(FROM_DATE))
        else if ((lt_is_doc_attachment.contentEquals("1") || lt_is_doc_compulsory.contentEquals("1")) && !FILE_UPLOADED_OR_NOT) {

            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Select Document");
            flag = false;
            return false;
        } else if (REMARKS.length() < 5) {
            DialogUtils.Show_Toast(LeaveStudentApply.this, "Please fill remarks ");
            flag = false;
            return false;
        }
//        else if (TO_DATE.after(FROM_DATE)||TO_DATE.equals(FROM_DATE))
//        {
//            System.out.println("TO_DATE in validateData:::::"+TO_DATE);
//            System.out.println("FROM_DATE in validateData:::::"+FROM_DATE);
//            DialogUtils.Show_Toast(LeaveStudentApply.this, " To-Date Should Be Greater Than From-Date ");
//            flag = false;
//            return false;
//        }
        return flag;
    }

    private void updateLabel1() {
//        String myFormat = "yyyy/MM/dd"; //In which you need put here
        String myFormat1 = "dd-MM-yyyy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        //      FROM_DATE = sdf.format(myCalendar.getTime());

        try {
            FROM_DATE = sdf1.parse(sdf1.format(myCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("error in FROM_DATE::::::::::::::" + e.getMessage());
        }

        edtfromdat.setText(sdf.format(myCalendar.getTime()));


//        if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0) {
//            edttodat.setText(sdf.format(myCalendar.getTime()));
//            edttodat.set
//        }
    }

    private void updateLabel2() {
//        String myFormat = "yyyy/MM/dd"; //In which you need put here
        //   String myFormat = "dd/MM/yyyy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        // TO_DATE = sdf.format(myCalendar.getTime());
        try {
//            TO_DATE = sdf.parse(sdf.format(Calendar.getInstance()));
            TO_DATE = sdf.parse(sdf.format(myCalendar2.getTime()));
            System.out.println("TO_DATE:::::::::::::::::" + TO_DATE);
        } catch (ParseException e) {
            System.out.println("error in TO_DATE :::::::::::::::" + e.getMessage());
            e.printStackTrace();
        }


        edttodat.setText(sdf.format(myCalendar2.getTime()));

    }

    String lt_is_doc_compulsory = "0";
    String lt_is_doc_attachment = "0";

    //    private void getKindOfLeave(String leave_type_id) {
    private void get_leave_type_file_upload(String leave_type_id) {
        //    DialogUtils.showProgressDialog(LeaveStudentApply.this, "");
        String url = URl.get_leave_type_file_upload + "leave_type_id=" + leave_type_id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                System.out.println("response getKindOfLeave " + response + "");
                FileUpload fileupload = gson.fromJson(response, FileUpload.class);
                //   DialogUtils.hideProgressDialog();
                lt_is_doc_compulsory = fileupload.getTable().get(0).getLt_is_doc_compulsory() + "";
                lt_is_doc_attachment = fileupload.getTable().get(0).getLt_is_doc_attachment() + "";
                if (lt_is_doc_attachment.contentEquals("0")) {
                    linfileupload.setVisibility(View.GONE);
                } else {
                    if (lt_is_doc_attachment.contentEquals("1")) {
                        filecomplsory.setVisibility(View.VISIBLE);
                    } else {
                        filecomplsory.setVisibility(View.INVISIBLE);
                    }
                    linfileupload.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  DialogUtils.hideProgressDialog();
                System.out.println("response getKindOfLeave ERROR " + "");
            }
        });


        queue.add(request);
    }

    SpinnerSimpleAdapter spinnerKindOfLeaveAdapter;
    ArrayList<String> kindOfLeave;
    LeaveTypes leaveTypes;

    private void getKindOfLeaveType(String institute_id) {
        //    DialogUtils.showProgressDialog(LeaveStudentApply.this, "");
        String url = URl.get_leave_type_institute_wise_for_student + "institute_id=" + institute_id;
        System.out.println("leave type display :::::::::::::::::::" + url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                System.out.println("response get_leave_type_institute_wise_for_student " + response + "");
                leaveTypes = gson.fromJson(response, LeaveTypes.class);
                //     DialogUtils.hideProgressDialog();
                kindOfLeave = new ArrayList<>();
                kindOfLeave.add("Select Kind of Leave");
                for (int i = 0; i < leaveTypes.getTable().size(); i++) {
                    kindOfLeave.add(leaveTypes.getTable().get(i).getLt_name() + "");
                }
                spinnerKindOfLeaveAdapter = new SpinnerSimpleAdapter(ctx, kindOfLeave);
                spinleavekind.setAdapter(spinnerKindOfLeaveAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    DialogUtils.hideProgressDialog();
                System.out.println("response get_leave_type_institute_wise_for_student ERROR " + "");
            }
        });


        queue.add(request);
    }

    LeaveLectureAdapter adapter;
    Lectures lectures;

    private void getLectures() {

        String date_to = edttodat.getText().toString();
        System.out.println("date_to @@@@@@@@@@@@ " + date_to);
//        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        // String inputDateStr="2013-06-24";
        Date dt = null;
        try {
            dt = inputFormat.parse(date_to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(dt);


      /*  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        date_to = format.format(edttodat.getText().toString());
*/
        System.out.println("date_to in get lectures :::::: " + outputDateStr);
//        DialogUtils.showProgressDialog(LeaveStudentApply.this, "");
        /* String url = URl.Get_Date_Wise_Lecture_List_for_student_leave + "&institute_id=" + INSTITUTE_ID + "&sem_id=" + SEM_ID + "&div_id=" + DIV_ID + "&stud_id=" + STUDENT_ID + "&from_date=" + TODAY_DATE + "&year_id=" + YEAR_ID + ""; */

        String url = URl.Get_Date_Wise_Lecture_List_for_student_leave + "&institute_id=" + INSTITUTE_ID + "&sem_id=" + SEM_ID + "&div_id=" + DIV_ID + "&stud_id=" + STUDENT_ID + "&from_date=" + outputDateStr + "&year_id=" + YEAR_ID + "";
        System.out.println("getLectures url " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                System.out.println("response Get_Date_Wise_Lecture_List_for_student_leave " + response + "");
                //   DialogUtils.hideProgressDialog();
                //NIRALI **** 01 NOV 2019 FOR CHANGE IN LECTURE WISE LEAVE
               /* SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(ctx, leave_Type);
                spinleavetype.setAdapter(spinnerSimpleAdapter);*/
                lectures = gson.fromJson(response, Lectures.class);

                if (lectures != null && lectures.getTable() != null && lectures.getTable().size() > 0) {
                    adapter = new LeaveLectureAdapter(lectures, ctx);
                    lvlectures.setAdapter(adapter);
                    if (LEAVE_TYPE_ID.compareToIgnoreCase("1") == 0) {
                        lvlectures.setVisibility(View.VISIBLE);
                        lin_lec_header.setVisibility(View.VISIBLE);

                        //      System.out.println("this is adpter settttttttttttttttttttttttttttttt:::::::::::::::::::: ");

                    } else {
                        lvlectures.setVisibility(View.GONE);
                        lin_lec_header.setVisibility(View.GONE);
                    }


                } else {
                    System.out.println("error in data:::::::::::::::::::::::::::::");
                }

                //   Lectures lectures=gson

                //    DialogUtils.hideProgressDialog();
//                kindOfLeave = new ArrayList<>();
//                kindOfLeave.add("Select Kind Of Leave");
//                for (int i = 0; i < leaveTypes.getTable().size(); i++) {
//                    kindOfLeave.add(leaveTypes.getTable().get(i).getLt_name() + "");
//                }
//                spinnerKindOfLeaveAdapter = new SpinnerSimpleAdapter(ctx, kindOfLeave);
//                spinleavekind.setAdapter(spinnerKindOfLeaveAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
                System.out.println("response Get_Date_Wise_Lecture_List_for_student_leave ERROR " + "");
            }
        });


        queue.add(request);
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private String extensionfile;
    boolean FILE_UPLOADED_OR_NOT = false;
    private String imgByteArray;
    String TAG = "FILE UPLOAD ::::::::::::::: ";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        FILE_UPLOADED_OR_NOT = false;
        EXTENTION = "";
        if (resultCode == RESULT_OK) {
            //   Uri uri_test = data.getData();
//            System.out.println("THIS IS PATH ...................... >>>>>>>>>>>> " +        getPath(LeaveStudentApply.this,uri_test));
            /*gallery image ================================================>*/
            if (requestCode == 1 && data != null) {

                Uri uri = data.getData();
//                System.out.println("THIS IS PATH ...................... >>>>>>>>>>>> " +        getPath(LeaveStudentApply.this,uri));
                try {
                    Bitmap bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    //System.out.println("bitmap iv ur pic in gallery:::::::::::"+bitmaps);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bitmaps.compress(Bitmap.CompressFormat.PNG, 90, stream);

                    byte[] byteArray = stream.toByteArray();

                    System.out.println("byarrayyyyyyyyyy:::::" + byteArray.toString());

                    // String encodeded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    // byte[] decodedString = Base64.decode(encodeded, Base64.DEFAULT);

                    // Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    //  iv_ur_pic.setImageBitmap(bitmaps);
                    String filePath = getRealPathFromURIPath(uri, LeaveStudentApply.this);
                    System.out.println("file name::::::::::" + filePath);

                    File file = new File(filePath);

                    System.out.println("filepath multipart:::::::::::" + filePath);
                    //Api_call_multipart_retrofit(fileToUploadPassport);
                    uri = Uri.parse(data.getData().getPath());

                    String fileUrl = FileUtils.getPath(LeaveStudentApply.this, data.getData());
                    Log.d(TAG, "onActivityResult: " + data.getData().getPath() + " file url:" + fileUrl);
                    file = new File(fileUrl);
                    Log.d(TAG, "onActivityResult: " + "file is exist:" + file.exists() + " file absolute Path" + file.getAbsolutePath());
                    if (file.exists() && file.length() > 0) {
                        byte[] byteImage = readByteFromFile(file);
                        imgByteArray = Base64.encodeToString(byteImage, Base64.DEFAULT);
                        edfileupload.setText(file.getName());
                        //if (isProfilePhotoUpload) {
                        FILE_UPLOADED_OR_NOT = true;
                        String file_name = file.getName() + "";
                        EXTENTION = file_name.substring(file_name.lastIndexOf("."), file_name.length()) + "";
                        System.out.println("this is extention !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + EXTENTION + "");
                        edfileupload.setText(file.getName());
//               ppppppppppppppppppp             sendPhotoToServer(imgByteArray);
                        // } else {
                        // edtuploadlic.setText(file.getName());
                        // }
                        Log.d("SaveExpensesFragment", imgByteArray);
                    } else {
                        Log.d("SaveExpensesFragment", "image not exists or file size 0");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    DialogUtils.Show_Toast(LeaveStudentApply.this, "Please Try Again!");
                }

            } else if (requestCode == 2 && data != null) {
                //   if (requestCode == 192 && data != null) {//9th file

                Uri uri = data.getData();
                String fileUrl = FileUtils.getPath(LeaveStudentApply.this, data.getData());
                File file1 = new File(fileUrl);
                System.out.println("nmae " + file1.getName() + "");
//                  String path= RealPathUtil.getRealPath(UploadActivityChanges.this,uri);
//                String path= getPath(UploadActivityChanges.this,uri);
//                String path = FileUtils.getPath(LeaveStudentApply.this, uri);
//                String path = FileUtils.getPath(LeaveStudentApply.this, uri);
                String path = null;

                try {
//                    path = PathUtil.getPath(LeaveStudentApply.this, uri);
                    path = PathUtil.getPath(LeaveStudentApply.this, uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    System.out.println("exception getting file :::::" + e);
                }

//                path = FileUtils.getPath(LeaveStudentApply.this, uri);
                System.out.println("filepath multipart:::::::::::" + path);
                File file = new File(path + "");//create path from uri


                MultipartBody.Part fileToUploadPassport;
                //  File file = new File(filePath);
                if (file.exists() && file.length() > 0) {
                    System.out.println("YESSS FILE EXISTS!!!!!!!!!!!!!!!" + file);

                    extensionfile = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), file);
                    FILE_UPLOADED_OR_NOT = true;
                    //fileToUploadPassport = MultipartBody.Part.createFormData("postedFile", file.getName(), mFile);
                    System.out.println("CHECK !!!!!!!!!!!!!!!!!! ");


                    byte[] byteImage = new byte[0];
                    try {
                        byteImage = readByteFromFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();


                    }
                    imgByteArray = Base64.encodeToString(byteImage, Base64.DEFAULT);
                    // imgByteArray = convertFileToByteArray(file);
                    edfileupload.setText(file.getName());
                    //if (isProfilePhotoUpload) {
                    FILE_UPLOADED_OR_NOT = true;
                    String file_name = file.getName() + "";
                    String ex = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                    System.out.println("extension of file from googledrive using mimetype :::::::::::::" + ex);
                    if (file_name.contains(".")) {
                        EXTENTION = file_name.substring(file_name.lastIndexOf("."), file_name.length()) + "";
                        System.out.println("this is extention !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + EXTENTION + "");

                    }

                    edfileupload.setText(file.getName());
//               ppppppppppppppppppp             sendPhotoToServer(imgByteArray);
                    // } else {
                    // edtuploadlic.setText(file.getName());
                    // }
                    Log.d("SaveExpensesFragment", imgByteArray);


                } else {
                    System.out.println("FILE NOT FOUND!!!!!!!!!!!!!!");
                }

            }
        }
    }

    LeaveDocumentPojo leaveDocumentPojo;

    private void sendPhotoToServer(final String data, final String stud_leave_id, final String name) {

        DialogUtils.showProgressDialog(LeaveStudentApply.this, "");
        String Url = URl.Upload_Student_Leave_Document;
        queue = Volley.newRequestQueue(ctx);
        //String url = URLS.UPDATE_MANAGER_PROFILE_DETAIL;
        Log.d(TAG, "sendProfileDataToServer: URL: " + Url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        Log.d(TAG, "sendProfileDataToServer: Response: " + response + "");
                        // response
                        Log.d("Response", response);

                        if (response.length() > 5) {
                            Gson gson = new Gson();
                            leaveDocumentPojo = gson.fromJson(response, LeaveDocumentPojo.class);

                            if (leaveDocumentPojo != null) {
                                if (leaveDocumentPojo.getStatus().contentEquals("1")) {
                                    System.out.println("CALLEDDDDD leaveDocumentPojo NOT NULL STSATUS ONE >>>> ");
//                                    DialogUtils.Show_Toast(LeaveStudentApply.this, leaveDocumentPojo.getMsg() + "");
                                    DialogUtils.Show_Toast(LeaveStudentApply.this, "Leave Added Successfully");
                                    //  Intent intent = new Intent(LeaveStudentApply.this, ViewLeaveApplication.class);
                                    //startActivity(intent);
                                    finish();
                                }

                            }

                        }
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            int id = jsonObject.getInt("status");
//                            if (id > 0) {
//                                //getProfileDetailFromaApi();
//                                imgByteArray = "";
//                                //    getPersonalDetails();
//                                DialogUtils.Show_Toast(LeaveStudentApply.this, ctx.getResources().getString(R.string.app_name));
//                            } else {
//                                DialogUtils.Show_Toast(LeaveStudentApply.this, ctx.getResources().getString(R.string.app_name));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //        progressDialog.dismiss();
                        DialogUtils.hideProgressDialog();
                        System.out.println("errrrrrrrrrrrrrrrrrrr" + error.getMessage());
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("stud_leave_id", stud_leave_id + "");
                params.put("image", data);

                for (String s : params.keySet()) {
                    System.out.println("key ::::" + s);
                    System.out.println("value:::::" + params.get(s));
                    Log.d(TAG, "getParams: request parameters" + params.toString());
                }
                return params;
            }
        };
        queue.add(postRequest);

    }


    private void browseDocuments(int code) {

      /*  String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};*/

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

    public byte[] readByteFromFile(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }


    public static String convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

}

