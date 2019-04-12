package com.infinity.infoway.agriculture.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.ExpandableListAdapter;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.app.MonthYearPicker;
import com.infinity.infoway.agriculture.model.AttendanceInoutTime;
import com.infinity.infoway.agriculture.model.PunchData;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.infinity.infoway.agriculture.rest.Api_client_emp_attendance;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeAttendance extends AppCompatActivity implements View.OnClickListener
{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataLateby;
    List<String> listDataEarlyby;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChildName;
    LinearLayout llToDate, llFromDate;
    TextView tvFromDate, tvToDate;
    Button btnviewattendance;
    Calendar myCalendar = Calendar.getInstance();
    Context ctx;
    EditText edtodate, edfromdate;
    DataStorage storage;
    String Key_attendance = "v8llRrQaDng=";
    Toolbar toolbar;
    private com.infinity.infoway.agriculture.app.MonthYearPicker myp;
    private com.infinity.infoway.agriculture.app.MonthYearPicker myp1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_attendance);

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        findviews();
//        findviews();
//        findviews();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
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
        edfromdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(EmployeeAttendance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        edtodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EmployeeAttendance.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        myp = new MonthYearPicker(this);
//        myp.build(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                tvFromDate.setText(myp.getSelectedMonthName() + "-" + myp.getSelectedYear());
//            }
//        }, null);
//
//
//        myp1=new MonthYearPicker(this);
//        myp1.build(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                tvToDate.setText(myp1.getSelectedMonthName() + "-"+myp1.getSelectedYear());
//            }
//        },null);
        prepareListData();

    }

    private void updateLabel()
    {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edfromdate.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel1()
    {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtodate.setText(sdf.format(myCalendar.getTime()));

    }


    public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataLateby = new ArrayList<String>();
        listDataEarlyby = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChildName = new HashMap<String, List<String>>();

        if (storage.CheckLogin("stud_id", this))
        {
            Toast.makeText(EmployeeAttendance.this, "No Records Found", Toast.LENGTH_LONG).show();
        }
        else
            {

            final ProgressDialog progressDialog = new ProgressDialog(EmployeeAttendance.this, R.style.MyTheme1);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            ApiInterface apiService = Api_client_emp_attendance.getClient().create(ApiInterface.class);
            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("KEY", Key_attendance);
            //Log.d("school_id", String.valueOf(storage.read("emp_main_school_id", 3)));
            mParams.put("Contact_ID", String.valueOf(storage.read("emp_number", 3)));
            // Log.d("emp_id", String.valueOf(storage.read("emp_id", 3)));
            mParams.put("Company_ID", String.valueOf(storage.read("ac_code", 3)));
//            mParams.put("FromDate", String.valueOf(myp1.getSelectedMonth()+1));
//            mParams.put("ToDate", String.valueOf(myp.getSelectedMonth() + 1));
            mParams.put("FromDate", String.valueOf(edfromdate.getText().toString()));
            mParams.put("ToDate", String.valueOf(edtodate.getText().toString()));


//            mParams.put("Emp_number",String.valueOf(storage.read("id",3)));

//
//            mParams.put("FromDate","2017,11,25");
//            mParams.put("ToDate","2017,12,26");

            Call<ArrayList<PunchData>> call = apiService.get_emp_punch(mParams);
            call.enqueue(new Callback<ArrayList<PunchData>>()
            {
                @Override
                public void onResponse(Call<ArrayList<PunchData>> call, Response<ArrayList<PunchData>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful())
                    {

                        if (response.body().size() >= 1)
                        {
                            for (int i = 0; i < response.body().size(); i++)
                            {
                                //Adding Header
                                listDataHeader.add(response.body().get(i).getAtt_date());
                                listDataEarlyby.add(response.body().get(i).getstatus());
                                listDataLateby.add(response.body().get(i).getday());

                                // Adding child data
                                List<String> childlist = new ArrayList<String>();
                                childlist.add(response.body().get(i).getTotal_Minute());
                                childlist.add(response.body().get(i).getTotal_Hour());
                                childlist.add(response.body().get(i).getearly_by());
                                childlist.add(response.body().get(i).getlate_by());
                                Log.d("child", String.valueOf(childlist.size()));

//                                 Adding child data Name
                                List<String> childlistname = new ArrayList<String>();
                                childlistname.add("Total Minute");
                                childlistname.add("Total Hour");
                                childlistname.add("Early By");
                                childlistname.add("Late By");


//                                childlistname.add("student attendance");
//                                childlistname.add("Total lecture");
//                                childlistname.add("Employee Attendance");


                                //Addind List inout time
                                ArrayList<AttendanceInoutTime> listinout = response.body().get(i).getInout_Array();

                                //                   Log.d("inout_size", String.valueOf(listinout.size()));

                                for (int j = 0; j < listinout.size(); j++)
                                {
                                    childlist.add(listinout.get(j).getintime());
                                    // childlist.add(listinout.get(j).getouttime());
                                    //childlistname.add("Intime :");
                                    childlistname.add(listinout.get(j).getouttime());
                                }

                                listDataChild.put(listDataHeader.get(i), childlist);
                                Log.d("childdata", String.valueOf(listDataChild));
                                listDataChildName.put(listDataHeader.get(i), childlistname);
                                Log.d("childname", String.valueOf(listDataChildName));
                            }

                            listAdapter = new ExpandableListAdapter(EmployeeAttendance.this, listDataHeader, listDataEarlyby, listDataLateby, listDataChild, listDataChildName);
                            expListView.setAdapter(listAdapter);
                        }
                        else
                            {
                            listAdapter = new ExpandableListAdapter(EmployeeAttendance.this, listDataHeader, listDataEarlyby, listDataLateby, listDataChild, listDataChildName);
                            expListView.setAdapter(listAdapter);
                            Toast.makeText(EmployeeAttendance.this, "No Records Found", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {
                        // Toast.makeText(Employee_Attendance.this, "Error in Response", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PunchData>> call, Throwable t)
                {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    //     Log.e("emplotteattendancerespo", t.toString());
                }
            });

        }
    }

    public void findviews() {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        Calendar newDate = Calendar.getInstance();
        newDate.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        edfromdate = (EditText) findViewById(R.id.tvFromDate);
        edtodate = (EditText) findViewById(R.id.tvtodate);
        edfromdate.setText(dateFormat.format(myCalendar.getTime()));
        edtodate.setText(dateFormat.format(myCalendar.getTime()));

//        tvFromDate = (TextView)findViewById(R.id.tvFromDate);
//        tvFromDate = (TextView)findViewById(R.id.tvtodate);
//        tvFromDate.setOnClickListener(this);
        // tvFromDate.setText(String.valueOf(tvFromDate));
        //tvToDate=(TextView)findViewById(R.id.tvToDate);
        //tvToDate.setOnClickListener(this);
//        tvFromDate.setText(dateFormat.format(newDate.getTime()));
        Calendar newDate2 = Calendar.getInstance();

        //tvToDate.setText(dateFormat.format(newDate2.getTime()));
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        btnviewattendance = (Button) findViewById(R.id.btnviewattendance);
        btnviewattendance.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == tvFromDate) {
            myp.show();
            prepareListData();
            //showAndSetDateDialog(tvFromDate);
            //createDialogWithoutDateField(tvFromDate);
        }

        if (view == tvToDate) {
            myp1.show();
            prepareListData();

        }
       /* if (v == tvToDate)
        {
            showAndSetDateDialog(tvToDate, tvFromDate.getText().toString());
        }*/
        if (view == btnviewattendance) {
            prepareListData();
        }

    }
}
