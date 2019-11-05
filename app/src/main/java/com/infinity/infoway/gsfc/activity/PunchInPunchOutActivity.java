package com.infinity.infoway.gsfc.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;

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
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.InternshipAttendanceAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.GPSTracker;
import com.infinity.infoway.gsfc.model.DisplayLeave;
import com.infinity.infoway.gsfc.model.PunchInOutDisplayPojo;
import com.infinity.infoway.gsfc.model.PunchInPunchOutpojo;
import com.infinity.infoway.gsfc.model.PunchPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PunchInPunchOutActivity extends AppCompatActivity implements View.OnClickListener
{

    CustomBoldTextView tv_current_time;
    CustomTextView title;
    Toolbar toolbar;
    CustomBoldTextView tvIn;
    CustomBoldTextView tvout;
    DataStorage storage;
    RequestQueue queue;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    public static final int ALL_PERMISSIONS_RESULT = 101;
    String Latitude, Longitude;
    private TextClock txt_add_time;
    LinearLayout ll_punch_in_put;

    CustomTextView tv_punch_in, tv_punch_out;
    Calendar myCalendar = Calendar.getInstance();
    CustomEditText tvfromDate_punchIn;
    CardView card_punch_out, card_punch_in;

    LinearLayout ll_calaendar;
    InternshipAttendanceAdapter internshipAttendanceAdapter;

    CustomBoldTextView tv_msg_internship;
    CustomButton btn_view_punch_inout;
    String date;
        ListView lv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_in_punch_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        Date currentTime = Calendar.getInstance().getTime();

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        //************ chnage api format for api response ******nirali 01 nov**********
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(currentTime.getTime());


        System.out.println("date of current system time:::::::::" + date);

        System.out.println("currentTime of system::::::::::::" + currentTime);


        initView();

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

        };

        tvfromDate_punchIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(PunchInPunchOutActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);


    }

    private void updateLabel()
    {
//        String myFormat = "dd-MM-yyyy"; //In which you need put here
//        String myFormat = "dd-MM-yyyy"; //In which you need put here
        //****************
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvfromDate_punchIn.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        InOutCountAPI();//count

    }

    boolean CALL_API = false;

    private void InOutCountAPI()
    {


        DialogUtils.showProgressDialog(PunchInPunchOutActivity.this, "");

        String URLs = URl.Internship_Attendance_count_api + "&student_id=" + String.valueOf(storage.read("stud_id", 3)) + "";
//        String URLs = URl.Internship_Attendance_count_api + "&student_id=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Internship_Attendance_count_api calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();

                        response = response + "";

                        response = "{\"Data\":" + response + "}";


                        System.out.println("THIS IS Internship_Attendance_count_api RESPONSE    " + response + "");


                        if (response.length() > 5)
                        {
//                           if (CALL_API) {
//                                    Student_In_Out_API_Call(Latitude, Longitude);
//                            }
                            Gson gson = new Gson();

                            PunchInPunchOutpojo punchInPunchOutpojo = gson.fromJson(response, PunchInPunchOutpojo.class);

                            if (punchInPunchOutpojo != null && punchInPunchOutpojo.getData().size() > 0)
                            {
                                System.out.println("punchInPunchOutpojo.getData().get(0).getCount_login() " + punchInPunchOutpojo.getData().get(0).getIn_out_count() + "");


                               if (punchInPunchOutpojo.getData().get(0).getMsg().compareToIgnoreCase("No internship allocated.")==0)
                               {
                                   ll_calaendar.setVisibility(View.GONE);
                                   tv_msg_internship.setVisibility(View.VISIBLE);
                                   tv_msg_internship.setText(punchInPunchOutpojo.getData().get(0).getMsg()+"");
                                   card_punch_in.setVisibility(View.GONE);
                                   card_punch_out.setVisibility(View.GONE);
                                   lv_main.setVisibility(View.GONE);
                               }


                               if (punchInPunchOutpojo.getData().get(0).getMsg().compareToIgnoreCase("You can not fill attendance before or after internship.")==0)
                               {
                                   ll_calaendar.setVisibility(View.VISIBLE);
                                   tv_msg_internship.setVisibility(View.VISIBLE);
                                   tv_msg_internship.setText(punchInPunchOutpojo.getData().get(0).getMsg()+"");
                                   card_punch_in.setVisibility(View.GONE);
                                   card_punch_out.setVisibility(View.GONE);
                                   lv_main.setVisibility(View.VISIBLE);
                                   DisplayListing(punchInPunchOutpojo);
                               }



                                if (punchInPunchOutpojo.getData().get(0).getIn_out_count().contentEquals("0")&&punchInPunchOutpojo.getData().get(0).getMsg().contentEquals(""))
                                {
                                    ll_calaendar.setVisibility(View.VISIBLE);
                                    System.out.println("call count 0 from apiiiiiiiiiii");
                                    card_punch_in.setVisibility(View.VISIBLE);
                                    card_punch_out.setVisibility(View.GONE);
                                    lv_main.setVisibility(View.VISIBLE);
                                    DisplayListing(punchInPunchOutpojo);

//                                    tvIn.setVisibility(View.VISIBLE);
//                                    tvout.setVisibility(View.GONE);
                                }
                                else if (punchInPunchOutpojo.getData().get(0).getIn_out_count().contentEquals("1")&&punchInPunchOutpojo.getData().get(0).getMsg().contentEquals(""))
                                {
                                    ll_calaendar.setVisibility(View.VISIBLE);
                                    System.out.println("call count 1 from apiiiiiiiiiii");
                                    card_punch_in.setVisibility(View.GONE);
                                    card_punch_out.setVisibility(View.VISIBLE);
                                    lv_main.setVisibility(View.VISIBLE);
                                    DisplayListing(punchInPunchOutpojo);
//
//                                    tvIn.setVisibility(View.GONE);
//                                    tvout.setVisibility(View.VISIBLE);
                                }
                                else if (punchInPunchOutpojo.getData().get(0).getIn_out_count().contentEquals("2"))
                                {
                                    ll_calaendar.setVisibility(View.VISIBLE);
                                    System.out.println("call count 2 from api***********************************");
                                    card_punch_in.setVisibility(View.GONE);
                                    card_punch_out.setVisibility(View.GONE);
                                    txt_add_time.setVisibility(View.GONE);

                                    ll_punch_in_put.setVisibility(View.VISIBLE);
                                    DisplayListing(punchInPunchOutpojo);

                                    Api_Call_Date_In_Out_Display(date);
//
//                                    tvIn.setVisibility(View.GONE);
//                                    tvout.setVisibility(View.GONE);
                                }
                            }


                        }
                        else
                        {
                            //  DialogUtils.Show_Toast(ViewLeaveApplication.this,"No")
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("error in count:::::::::::::::::" + error.getMessage());
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }

    private void DisplayListing(PunchInPunchOutpojo punchInPunchOutpojo)
    {

        internshipAttendanceAdapter = new InternshipAttendanceAdapter(PunchInPunchOutActivity.this,punchInPunchOutpojo);
        lv_main.setAdapter(internshipAttendanceAdapter);
    }

    private void Api_Call_Date_In_Out_Display(String Date)
    {
        DialogUtils.showProgressDialog(PunchInPunchOutActivity.this, "");

        String URLs = URl.Get_intership_student_inout_time_API + "&student_id=" + String.valueOf(storage.read("stud_id", 3)) + "&date=" + Date + "";
//        String URLs = URl.Internship_Attendance_count_api + "&student_id=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_intership_student_inout_time_API calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";

                        response = "{\"Data\":" + response + "}";


                        System.out.println("THIS IS Get_intership_student_inout_time_API RESPONSE    " + response + "");


                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            PunchInOutDisplayPojo punchInOutDisplayPojo = gson.fromJson(response, PunchInOutDisplayPojo.class);

                            System.out.println("punchInOutDisplayPojo::::::::::::" + punchInOutDisplayPojo.getData().size());

                            if (punchInOutDisplayPojo != null && punchInOutDisplayPojo.getData().size() > 0)
                            {
                                ll_punch_in_put.setVisibility(View.VISIBLE);
                                tv_punch_in.setText("  " + punchInOutDisplayPojo.getData().get(0).getIntime() + "");

                                tv_punch_out.setText("  " + punchInOutDisplayPojo.getData().get(0).getOuttime());
                            }
                            else
                            {
                                ll_punch_in_put.setVisibility(View.GONE);
//                                tv_punch_in.setVisibility(View.GONE);
//                                tv_punch_out.setVisibility(View.GONE);

                            }

                        } else {
                            //  DialogUtils.Show_Toast(ViewLeaveApplication.this,"No")
                        }


                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private void initView() {

        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(this);
        lv_main =(ListView)findViewById(R.id.lv_main);
        tv_msg_internship = (CustomBoldTextView)findViewById(R.id.tv_msg_internship);
        btn_view_punch_inout = (CustomButton) findViewById(R.id.btn_view_punch_inout);
        btn_view_punch_inout.setOnClickListener(this);
        tvfromDate_punchIn = (CustomEditText) findViewById(R.id.tvfromDate_punchIn);
        ll_punch_in_put = (LinearLayout) findViewById(R.id.ll_punch_in_put);
        tv_punch_in = (CustomTextView) findViewById(R.id.tv_punch_in);
        tv_punch_out = (CustomTextView) findViewById(R.id.tv_punch_out);
        ll_calaendar =(LinearLayout)findViewById(R.id.ll_calaendar);
        txt_add_time = (TextClock) findViewById(R.id.txt_add_time);


        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/PoppinsRegular.otf");
        txt_add_time.setTypeface(face);
        title = (CustomTextView) findViewById(R.id.title);
        card_punch_out = (CardView) findViewById(R.id.card_punch_out);
        card_punch_out.setOnClickListener(this);
        card_punch_in = (CardView) findViewById(R.id.card_punch_in);
        card_punch_in.setOnClickListener(this);
//        tvIn = (CustomBoldTextView) findViewById(R.id.tv_In);
//        tvIn.setOnClickListener(PunchInPunchOutActivity.this);
//        tvout = (CustomBoldTextView) findViewById(R.id.tv_out);
//        tvout.setOnClickListener(PunchInPunchOutActivity.this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.card_punch_in) {

            GPSTracker gpsTracker = new GPSTracker(PunchInPunchOutActivity.this);

            if (!gpsTracker.canGetLocation())
            {
                gpsTracker.showSettingsAlert();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {

                    if (permissionsToRequest.size() > 0)
                    {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                    }

                }
                Latitude = String.valueOf(gpsTracker.getLatitude());
                System.out.println("Latitude is :::" + Latitude);

                Longitude = String.valueOf(gpsTracker.getLongitude());
                System.out.println("Longitude is :::" + Longitude);
            }
            CALL_API = true;
            System.out.println("IN API");
//            InOutCountAPI();
            Student_In_Out_API_Call(Latitude, Longitude);

        }
        else if (view.getId() == R.id.card_punch_out)
        {
            GPSTracker gpsTracker = new GPSTracker(PunchInPunchOutActivity.this);

            if (!gpsTracker.canGetLocation())
            {
                gpsTracker.showSettingsAlert();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {

                    if (permissionsToRequest.size() > 0)
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
                Latitude = String.valueOf(gpsTracker.getLatitude());
                System.out.println("Latitude is :::" + Latitude);

                Longitude = String.valueOf(gpsTracker.getLongitude());
                System.out.println("Longitude is :::" + Longitude);
            }
            CALL_API = true;
            System.out.println("OUT API");
//            InOutCountAPI();
            Student_In_Out_API_Call(Latitude, Longitude);

        } else if (view.getId() == R.id.btn_view_punch_inout)
        {
            Api_Call_Date_In_Out_Display(tvfromDate_punchIn.getText().toString());
        }
    }

    private void Student_In_Out_API_Call(String Latitude, String Longitude)
    {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        System.out.println("android_id::::::::::::" + android_id);

        DialogUtils.showProgressDialog(PunchInPunchOutActivity.this, "");


        String URLs = URl.InsertStudent_Attendance_In_Time_API + "&student_id=" + String.valueOf(storage.read("stud_id", 3)) + "&latitude=" + Latitude + "&longitute=" + Longitude + "&created_by=" + String.valueOf(storage.read("stud_id", 3)) + "&created_ip=" + android_id + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("InsertStudent_Attendance_In_Time_API calls faculty   " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();
                        response = response + "";

                        System.out.println("response from punch in out without data:::::" + response);
                        response = "{\"Data\":" + response + "}";

                        System.out.println("THIS IS InsertStudent_Attendance_In_Time_API RESPONSE    " + response + "");


                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();
                            PunchPojo punchPojo = gson.fromJson(response, PunchPojo.class);

                            if (punchPojo != null && punchPojo.getData().size() > 0)
                            {
                                DialogUtils.Show_Toast(PunchInPunchOutActivity.this, punchPojo.getData().get(0).getMsg() + "");
                                InOutCountAPI();//counter

                            }

                        }
                        else
                            {
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
}
