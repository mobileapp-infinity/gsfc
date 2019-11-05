package com.infinity.infoway.gsfc.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.ExpandableListAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MonthYearPicker;
import com.infinity.infoway.gsfc.model.AttendanceInoutTime;
import com.infinity.infoway.gsfc.model.EmployeeAttendanceResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.text.ParseException;
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

public class Employee_Attendance extends AppCompatActivity implements View.OnClickListener
{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataLateby;
    List<String> listDataEarlyby;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChildName;
    LinearLayout llToDate, llFromDate;
    TextView tvFromDate;
    Button btnviewattendance;
    Context ctx;
    DataStorage storage;
    Toolbar toolbar;
    private MonthYearPicker myp;
    private MonthYearPicker myp1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__attendance);
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

        findviews();

        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                tvFromDate.setText(myp.getSelectedMonthName() + "-" + myp.getSelectedYear());
            }
        }, null);

        prepareListData();

    }

    /*
     * Preparing the list data
     */

    public void prepareListData()
    {
        listDataHeader = new ArrayList<String>();
        listDataLateby = new ArrayList<String>();
        listDataEarlyby = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChildName = new HashMap<String, List<String>>();

        if (storage.CheckLogin("stud_id", this))
        {
            Toast.makeText(Employee_Attendance.this, "No Records Found", Toast.LENGTH_LONG).show();
        }
        else
        {

            final ProgressDialog progressDialog = new ProgressDialog(Employee_Attendance.this, R.style.MyTheme1);
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("school_id", String.valueOf(storage.read("emp_main_school_id", 3)));
            //Log.d("school_id", String.valueOf(storage.read("emp_main_school_id", 3)));
            mParams.put("emp_id", String.valueOf(storage.read("emp_id",3)));
            // Log.d("emp_id", String.valueOf(storage.read("emp_id", 3)));
            mParams.put("year_id", "1");
            mParams.put("year", String.valueOf(myp.getSelectedYear()));
            mParams.put("month", String.valueOf(myp.getSelectedMonth() + 1));


            Call<ArrayList<EmployeeAttendanceResponse>> call = apiService.getEmployeeAttendance(mParams);
            call.enqueue(new Callback<ArrayList<EmployeeAttendanceResponse>>()
            {
                @Override
                public void onResponse(Call<ArrayList<EmployeeAttendanceResponse>> call, Response<ArrayList<EmployeeAttendanceResponse>> response) {
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
                                ArrayList<AttendanceInoutTime> listinout = response.body().get(i).getinout_array();

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

                            listAdapter = new ExpandableListAdapter(Employee_Attendance.this, listDataHeader, listDataEarlyby, listDataLateby, listDataChild, listDataChildName);
                            expListView.setAdapter(listAdapter);
                        } else {
                            listAdapter = new ExpandableListAdapter(Employee_Attendance.this, listDataHeader, listDataEarlyby, listDataLateby, listDataChild, listDataChildName);
                            expListView.setAdapter(listAdapter);
                            Toast.makeText(Employee_Attendance.this, "No Records Found", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        // Toast.makeText(Employee_Attendance.this, "Error in Response", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<EmployeeAttendanceResponse>> call, Throwable t) {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    //     Log.e("emplotteattendancerespo", t.toString());
                }
            });
        }
    }

    public void findviews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);

        Calendar newDate = Calendar.getInstance();
        newDate.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvFromDate.setOnClickListener(this);
        // tvFromDate.setText(String.valueOf(tvFromDate));
        //tvToDate=(TextView)findViewById(R.id.tvToDate);
        //tvToDate.setOnClickListener(this);
        tvFromDate.setText(dateFormat.format(newDate.getTime()));
        Calendar newDate2 = Calendar.getInstance();

        //tvToDate.setText(dateFormat.format(newDate2.getTime()));
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        btnviewattendance = (Button) findViewById(R.id.btnviewattendance);
        btnviewattendance.setOnClickListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if (v == tvFromDate) {
            myp.show();
            prepareListData();
            //showAndSetDateDialog(tvFromDate);
            //createDialogWithoutDateField(tvFromDate);
        }
       /* if (v == tvToDate)
        {
            showAndSetDateDialog(tvToDate, tvFromDate.getText().toString());
        }*/
        if (v == btnviewattendance) {
            prepareListData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void showAndSetDateDialog(final TextView textView, String minDate) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        try {
            fromDatePickerDialog.getDatePicker().setMinDate(dateFormatter.parse(minDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fromDatePickerDialog.show();
    }

    private void createDialogWithoutDateField(final TextView textView) {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR) + 4;
        int m = c.get(Calendar.MONTH) - 2;
        int d = c.get(Calendar.DAY_OF_MONTH);
        final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        DatePickerDialog dp = new DatePickerDialog(Employee_Attendance.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        String erg = "";
                        erg = String.valueOf(dayOfMonth);
                        erg += "." + String.valueOf(monthOfYear + 1);
                        erg += "." + year;
                        textView.setText(erg);

                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.setMessage("Select Your Graduation date Please?");
        dp.show();
    }

    public void showAndSetDateDialog(final TextView textView) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            // java.lang.reflect.Field[] datePickerDialogFields = fromDatePickerDialog.getClass().getDeclaredFields();

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
    }
}

