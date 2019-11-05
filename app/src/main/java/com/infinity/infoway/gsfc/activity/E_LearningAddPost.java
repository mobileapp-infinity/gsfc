package com.infinity.infoway.gsfc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
import com.infinity.infoway.gsfc.CommonCls.FileUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.DeletePojo;
import com.infinity.infoway.gsfc.model.GrpNamePojo;
import com.infinity.infoway.gsfc.model.InsertPostPojo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class E_LearningAddPost extends AppCompatActivity {
    private Toolbar toolbar;
    private CustomTextView title;
    private CustomBoldTextView tvgpname;
    private Spinner spinelerning;
    private CustomBoldTextView tvgrptype;
    private CustomEditText edtuploadfile;
    private CustomButton btnuploadfile;
    private CustomBoldTextView tvdesc;
    private CustomEditText edtdescelerning;
    private CustomBoldTextView tvdate;
    private CustomEditText edtdate;
    private ImageView ivdate;
    private CustomBoldTextView tvsave;
    private LinearLayout llelerning;
    Calendar myCalendar = Calendar.getInstance();
    DataStorage storage;
    RequestQueue queue;
    ArrayList<String> GROUPNAME;
    ArrayList<String> GROUPID;
    String FILE_ID, FILE_GRP_ID, DESCRIPTION, DATE, GROUP, FILE_URL, FILE_NAME;
    Boolean IS_EDIT_POST;
    String ID_GROUP;
    Boolean FILE_UPLOADED_OR_NOT = false;
    String EXTENTION;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_learning_addpost);

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

        FILE_ID = intent.getStringExtra("FILE_ID");
        System.out.println("FILE_ID::::::::::" + FILE_ID);
        FILE_GRP_ID = intent.getStringExtra("FILE_GRP_ID");
        System.out.println("FILE_GRP_ID::::::::::" + FILE_GRP_ID);
        DESCRIPTION = intent.getStringExtra("DESCRIPTION");
        System.out.println("DESCRIPTION::::::::::" + DESCRIPTION);
        DATE = intent.getStringExtra("DATE");
        System.out.println("DATE::::::::::" + DATE);
        GROUP = intent.getStringExtra("GROUP");
        System.out.println("GROUP::::::::::" + GROUP);
        IS_EDIT_POST = intent.getExtras().getBoolean("IS_EDIT_POST");
        FILE_URL = intent.getStringExtra("FILE_URL") + "";
        System.out.println("FILE_URL::::::::" + FILE_URL);
        FILE_NAME = intent.getStringExtra("FILE_NAME") + "";
        System.out.println("FILE_NAME::::::::::::::" + FILE_NAME);

        init();

        BindGroupName();

        if (IS_EDIT_POST) {
            BindEDITData();
        }

    }

    private void BindEDITData() {

        if (DATE != null && !DATE.contentEquals("")) {
//            updateLabel1(DATE);
            edtdate.setText(DATE);
        }

        if (DESCRIPTION != null && !DESCRIPTION.contentEquals("")) {
            edtdescelerning.setText(DESCRIPTION);
        }


    }

    private void updateLabel1(String DATE) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d = null;
        try {
            d = sdf.parse(DATE);
            System.out.println("date::::::::::::" + d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee in date" + e);
        }

        sdf.applyPattern(myFormat);
        edtdate.setText(sdf.format(d));
    }


    private void UpdatePost() {
        DialogUtils.showProgressDialog(E_LearningAddPost.this, "");
        String URLs = URl.Update_E_Learning_File_Master_API + "&el_grp_file_id=" + FILE_ID + "&el_grp_file_grp_id=" + FILE_GRP_ID + "&el_grp_file_desc=" + edtdescelerning.getText().toString().trim() + "&el_grp_file_date=" + edtdate.getText().toString().trim() + "&el_grp_file_modify_ip=" + "1" + "&el_grp_file_modify_by=" + String.valueOf(storage.read("emp_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Update_E_Learning_File_Master_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Update_E_Learning_File_Master_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                            if (deletePojo != null && deletePojo.getTable().size() > 0)

                            {
                                if (deletePojo.getTable().get(0).getError_code().compareToIgnoreCase("1") == 0) {
                                    DialogUtils.Show_Toast(E_LearningAddPost.this, "Post Update Sucessfully");
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

    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

    private void opendialog() {
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        } else {
            final CharSequence[] options = {"Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(E_LearningAddPost.this);
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

                /*    else if (options[item].equals("Upload Pdf")) {
                        browseDocuments(2);


                    }*/
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });

            builder.show();
        }
    }

    private void BindGroupName() {
        DialogUtils.showProgressDialog(E_LearningAddPost.this, "");
        String URLs = URl.get_e_learning_group_name_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("get_e_learning_group_name_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS get_e_learning_group_name_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();
                            GROUPNAME = new ArrayList<>();
                            GROUPID = new ArrayList<>();
                            GrpNamePojo grpNamePojo = gson.fromJson(response, GrpNamePojo.class);

                            if (grpNamePojo != null && grpNamePojo.getTable().size() > 0)

                            {

                                GROUPNAME.add("Select Group");
                                GROUPID.add("0");
                                for (int i = 0; i < grpNamePojo.getTable().size(); i++) {
                                    GROUPNAME.add(grpNamePojo.getTable().get(i).getEl_grp_name() + "");
                                    GROUPID.add(grpNamePojo.getTable().get(i).getEl_grp_id() + "");
                                }
                                SpinnerSimpleAdapter spinnerSimpleAdapter = new SpinnerSimpleAdapter(E_LearningAddPost.this, GROUPNAME);

                                spinelerning.setAdapter(spinnerSimpleAdapter);
                                if (IS_EDIT_POST) {
                                    if (FILE_GRP_ID != null && !FILE_GRP_ID.contentEquals("")) {
                                        spinelerning.setSelection(GROUPID.indexOf(FILE_GRP_ID));
                                    }
                                }

                                spinelerning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        ID_GROUP = GROUPID.get(i);

                                        System.out.println("ID_GROUP:::::::::" + ID_GROUP);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

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

    private void init() {

        storage = new DataStorage("Login_Detail", E_LearningAddPost.this);
        queue = Volley.newRequestQueue(this);
        this.llelerning = (LinearLayout) findViewById(R.id.ll_elerning);
        this.tvsave = (CustomBoldTextView) findViewById(R.id.tv_save);
        this.ivdate = (ImageView) findViewById(R.id.iv_date);
        this.edtdate = (CustomEditText) findViewById(R.id.edt_date);
        this.tvdate = (CustomBoldTextView) findViewById(R.id.tv_date);
        this.edtdescelerning = (CustomEditText) findViewById(R.id.edt_desc_e_lerning);
        this.tvdesc = (CustomBoldTextView) findViewById(R.id.tv_desc);
        this.btnuploadfile = (CustomButton) findViewById(R.id.btn_upload_file);
        this.edtuploadfile = (CustomEditText) findViewById(R.id.edt_upload_file);
        this.tvgrptype = (CustomBoldTextView) findViewById(R.id.tv_grp_type);
        this.spinelerning = (Spinner) findViewById(R.id.spin_e_lerning_adpost);
        this.tvgpname = (CustomBoldTextView) findViewById(R.id.tv_gp_name);
        this.title = (CustomTextView) findViewById(R.id.title);

        btnuploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
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
                updateLabel();


            }

        };
        ivdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(E_LearningAddPost.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate())

                {

                    if (IS_EDIT_POST) {
                        UpdatePost();
                    } else {
                        InsertPostByAPI();
                    }
                }
            }
        });

    }

    private void InsertPostByAPI() {

        DialogUtils.showProgressDialog(E_LearningAddPost.this, "");
        String URLs = URl.Insert_E_Learning_File_Master_API + "&el_grp_file_year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&el_grp_file_grp_id=" + ID_GROUP + "&el_grp_file_desc=" + edtdescelerning.getText().toString().trim() + "&el_grp_file_date=" + edtdate.getText().toString().trim() + "&el_grp_file_created_ip=" + "1" + "&el_grp_file_created_by=" + String.valueOf(storage.read("emp_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Insert_E_Learning_File_Master_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS Insert_E_Learning_File_Master_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            InsertPostPojo insertPostPojo = gson.fromJson(response, InsertPostPojo.class);

                            if (insertPostPojo != null && insertPostPojo.getTable().size() > 0)

                            {
                                if (insertPostPojo.getTable().get(0).getError_code().compareToIgnoreCase("1") == 0)
                                {
                                    DialogUtils.Show_Toast(E_LearningAddPost.this, "Post Add Sucessfully");


                                    /*Intent intent = new Intent(E_LearningAddPost.this , E_Learning_PostList.class);
                                    startActivity(intent);
                                    finish();*/
//                                    UploadFileToServer(insertPostPojo.getTable().get(0).getInsert_id());
                                    // call api of file upload and then redirect listing activity
                                }

                            }


                        }

//                        else
//                        {
//                            DialogUtils.Show_Toast(FacultyAttendance.this,"No Pending Attendance");
//                        }
                    }
                }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    private void UploadFileToServer(String ID) {

    }


    private boolean isValidate()
    {

        if (ID_GROUP.compareToIgnoreCase("0") == 0 || ID_GROUP.contentEquals("0"))
        {
            DialogUtils.Show_Toast(E_LearningAddPost.this, "Select Group");
            return false;
        }
       /* else if (!FILE_UPLOADED_OR_NOT) {
            DialogUtils.Show_Toast(E_LearningAddPost.this, "Upload Document");
            return false;

        } */
        else if (edtdescelerning.getText().toString().trim().isEmpty() || edtdescelerning.getText().toString().trim().length() < 0 || edtdescelerning.getText().toString().trim().contentEquals("")) {
            DialogUtils.Show_Toast(E_LearningAddPost.this, "Enter Description");
            return false;
        } else if (edtdate.getText().toString().trim().isEmpty() || edtdate.getText().toString().trim().length() < 0 || edtdate.getText().toString().trim().contentEquals("")) {
            DialogUtils.Show_Toast(E_LearningAddPost.this, "Enter Date");
            return false;
        }

        return true;

    }

    private void updateLabel()
    {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtdate.setText(sdf.format(myCalendar.getTime()));


    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity)
    {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
        {
            return contentURI.getPath();
        }
        else
            {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public byte[] readByteFromFile(File file) throws IOException
    {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try
        {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1)
            {
                ous.write(buffer, 0, read);
            }
        }
        finally
        {
            try
            {
                if (ous != null)
                    ous.close();
            }
            catch (IOException e)
            {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    String TAG = "FILE UPLOAD ::::::::::::::: ";
    private String imgByteArray;

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

                    String filePath = getRealPathFromURIPath(uri, E_LearningAddPost.this);
                    System.out.println("file name::::::::::" + filePath);

                    File file = new File(filePath);

                    System.out.println("filepath multipart:::::::::::" + filePath);
                    //Api_call_multipart_retrofit(fileToUploadPassport);
                    uri = Uri.parse(data.getData().getPath());

                    String fileUrl = FileUtils.getPath(E_LearningAddPost.this, data.getData());
                    Log.d(TAG, "onActivityResult: " + data.getData().getPath() + " file url:" + fileUrl);
                    file = new File(fileUrl);
                    Log.d(TAG, "onActivityResult: " + "file is exist:" + file.exists() + " file absolute Path" + file.getAbsolutePath());
                    if (file.exists() && file.length() > 0)
                    {
                        byte[] byteImage = readByteFromFile(file);
                        imgByteArray = Base64.encodeToString(byteImage, Base64.DEFAULT);
                        edtuploadfile.setText(file.getName());
                        //if (isProfilePhotoUpload) {
                        FILE_UPLOADED_OR_NOT = true;
                        String file_name = file.getName() + "";
                        EXTENTION = file_name.substring(file_name.lastIndexOf("."), file_name.length()) + "";
                        System.out.println("this is extention !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + EXTENTION + "");
                        edtuploadfile.setText(file.getName());
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
                    DialogUtils.Show_Toast(E_LearningAddPost.this, "Please Try Again!");
                }

            }
//            else if (requestCode == 2 && data != null) {
//                //   if (requestCode == 192 && data != null) {//9th file
//
//                Uri uri = data.getData();
//
//
//                //  String path= RealPathUtil.getRealPath(UploadActivityChanges.this,uri);
////                String path= getPath(UploadActivityChanges.this,uri);
////                String path = FileUtils.getPath(LeaveStudentApply.this, uri);
////                String path = FileUtils.getPath(LeaveStudentApply.this, uri);
//                String path = null;
//                try {
//                    path = PathUtil.getPath(LeaveStudentApply.this, uri);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("filepath multipart:::::::::::" + path);
//                File file = new File(path + "");//create path from uri
//
//
//                MultipartBody.Part fileToUploadPassport;
//                //  File file = new File(filePath);
//                if (file.exists() && file.length() > 0) {
//                    System.out.println("YESSS FILE EXISTS!!!!!!!!!!!!!!!");
//                    extensionfile = file.getName().substring(file.getName().lastIndexOf(".") + 1);
//                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), file);
//                    FILE_UPLOADED_OR_NOT = true;
//                    //fileToUploadPassport = MultipartBody.Part.createFormData("postedFile", file.getName(), mFile);
//                    System.out.println("CHECK !!!!!!!!!!!!!!!!!! ");
//
//
//                    byte[] byteImage = new byte[0];
//                    try {
//                        byteImage = readByteFromFile(file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//
//                    }
//                    imgByteArray = Base64.encodeToString(byteImage, Base64.DEFAULT);
//                    // imgByteArray = convertFileToByteArray(file);
//                    edfileupload.setText(file.getName());
//                    //if (isProfilePhotoUpload) {
//                    FILE_UPLOADED_OR_NOT = true;
//                    String file_name = file.getName() + "";
//                    EXTENTION = file_name.substring(file_name.lastIndexOf("."), file_name.length()) + "";
//                    System.out.println("this is extention !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + EXTENTION + "");
//                    edfileupload.setText(file.getName());
////               ppppppppppppppppppp             sendPhotoToServer(imgByteArray);
//                    // } else {
//                    // edtuploadlic.setText(file.getName());
//                    // }
//                    Log.d("SaveExpensesFragment", imgByteArray);
//
//
//                } else {
//                    System.out.println("FILE NOT FOUND!!!!!!!!!!!!!!");
//                }
//
//            }
        }
    }

}
