package com.infinity.infoway.gsfc.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.MonthYearPicker;

import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.StudentAttendance;
import com.infinity.infoway.gsfc.model.Methods;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Student_Attendance extends AppCompatActivity implements View.OnClickListener {
    private MonthYearPicker myp;
    TableLayout tableLayout;
    TableRow tbrow1;
    String attstatus, lecstatus, date;
    TextView tvFromDate, tvToDate;
    Button btnviewattendance;
    LinearLayout llToDate, llFromDate;
    DataStorage storage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);

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
        init();

    }

    public void findviews()
    {
        storage = new DataStorage("Login_Detail", Student_Attendance.this);
        Calendar newDate = Calendar.getInstance();
        newDate.set(Calendar.DAY_OF_MONTH, 1);
//    pppppppppppppp    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/yyyy", Locale.US);
        dateFormat.format(newDate.getTime());

        tbrow1 = (TableRow) findViewById(R.id.tbrow1);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvFromDate.setText(dateFormat.format(newDate.getTime()));
        tvFromDate.setOnClickListener(this);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvToDate.setOnClickListener(this);
        Calendar newDate2 = Calendar.getInstance();

        //tvToDate.setText(dateFormat.format(newDate2.getTime()));
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        btnviewattendance = (Button) findViewById(R.id.btnviewattendance);
        btnviewattendance.setOnClickListener(this);
        tableLayout = (TableLayout) findViewById(R.id.table1);

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
    public void onClick(View v)
    {

        if (v == btnviewattendance)
        {
            init();
        }

        if (v == tvToDate)
        {
            //ppppppppppppppppppppppppp   showAndSetDateDialog(tvToDate, tvFromDate.getText().toString());
        }


        if (v == tvFromDate)
        {
            //ppppppppppppppp  myp.show();
            //showAndSetDateDialog(tvFromDate);
            // createDialogWithoutDateField(tvFromDate);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void showAndSetDateDialog(final TextView textView, String minDate)
    {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        try
        {
            fromDatePickerDialog.getDatePicker().setMinDate(dateFormatter.parse(minDate).getTime());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        fromDatePickerDialog.show();
    }

    private void createDialogWithoutDateField(final TextView textView)
    {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR) + 4;
        int m = c.get(Calendar.MONTH) - 2;
        int d = c.get(Calendar.DAY_OF_MONTH);
        final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        DatePickerDialog dp = new DatePickerDialog(Student_Attendance.this,
                new DatePickerDialog.OnDateSetListener()
                {

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

    public void showAndSetDateDialog(final TextView textView)
    {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            // java.lang.reflect.Field[] datePickerDialogFields = fromDatePickerDialog.getClass().getDeclaredFields();

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
    }

    public void init()
    {
        tableLayout.removeAllViews();
        HashMap<String, String> params = new HashMap<>();
        params.put("year", String.valueOf(myp.getSelectedYear()));
        Log.d("Year", String.valueOf(myp.getSelectedYear()));

        params.put("month", String.valueOf(myp.getSelectedMonth()+1));
        Log.d("Month", String.valueOf(myp.getSelectedMonth()+1));

        params.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
        Log.d("Stud_id", String.valueOf(storage.read("stud_id", 3)));

        params.put("course_id", String.valueOf(storage.read("course_id", 3)));
        Log.d("course_id", String.valueOf(storage.read("course_id", 3)));

        params.put("div_id", String.valueOf(storage.read("swd_division_id", 3)));
        Log.d("Div_id", String.valueOf(storage.read("swd_division_id", 3)));

        params.put("shift_id", String.valueOf(storage.read("shift_id", 3)));
        Log.d("Shift_id", String.valueOf(storage.read("shift_id", 3)));

        params.put("batch_id", String.valueOf(storage.read("swd_batch_id", 3)));
        Log.d("Batch_id", String.valueOf(storage.read("swd_batch_id", 3)));

        params.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));
        Log.d("Year_id", String.valueOf(storage.read("swd_year_id", 3)));


        final ProgressDialog progressDialog = new ProgressDialog(Student_Attendance.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
       // progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<StudentAttendance>> call = apiService.getStudAttendance(params);

        call.enqueue(new Callback<ArrayList<StudentAttendance>>()
        {
            @Override
            public void onResponse(Call<ArrayList<StudentAttendance>> call, retrofit2.Response<ArrayList<StudentAttendance>> response)
            {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {



                    System.out.println("student attendance display :::::::"+call.request());

                    //Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1)
                    {

                        System.out.println("stud data size ::::::::::"+response.body().size());
                        TableRow tbrow2 = new TableRow(Student_Attendance.this);
                        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        tbrow2.setLayoutParams(lp2);
                        TextView t1v1 = new TextView(Student_Attendance.this);
                        t1v1.setText("Date/Lect");
                        t1v1.setTextColor(getResources().getColor(R.color.white));
                        t1v1.setTypeface(null, Typeface.BOLD);
                        t1v1.setGravity(Gravity.CENTER);
                        t1v1.setBackground(getResources().getDrawable(R.drawable.table_cell_header_bg));
                        t1v1.setPadding(5,10,5,10);
                        t1v1.setBackgroundColor(getResources().getColor(R.color.blueattendance));
//                        t1v1.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                        tbrow2.addView(t1v1);
                        tbrow2.setGravity(Gravity.CENTER);
                        tbrow2.setBackgroundColor(getResources().getColor(R.color.blueattendance));

                        for (int j = 0; j <response.body().get(0).getall_lecture().size(); j++)
                        {
                            TextView t1v2 = new TextView(Student_Attendance.this);
                            t1v2.setTextColor(getResources().getColor(R.color.white));
                            t1v2.setTypeface(null, Typeface.BOLD);
                            t1v2.setGravity(Gravity.CENTER);
                            t1v2.setText(String.valueOf(j + 1));
                            t1v2.setPadding(5, 10, 5, 10);
                            t1v2.setBackground(getResources().getDrawable(R.drawable.table_cell_header_bg));
                            tbrow2.setGravity(Gravity.CENTER);
                            tbrow2.addView(t1v2);
                        }

                        tableLayout.addView(tbrow2, 0);

                        for (int i = 0; i < response.body().size(); i++)
                        {
                            ArrayList<Methods> lectures = response.body().get(i).getall_lecture();
                            TableRow tbrow = new TableRow(Student_Attendance.this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tbrow.setLayoutParams(lp);

                            date = response.body().get(i).getdate();
                            Log.d("date", date);
                            String stringa;
                            String stringb = "";
                            if (date.contains("_"))
                            {
                                String[] str_array = date.split("_");
                                Log.d("str_ary", String.valueOf(str_array));
                                stringa = str_array[0];
                                Log.d("ga", stringa);
                                stringb = str_array[1];
                                Log.d("gb", stringb);
                            }
                            else
                            {
                                stringa = date;
                            }

                            TextView t1v = new TextView(Student_Attendance.this);
                            t1v.setText(stringa);
                            t1v.setTextSize(17);
                            t1v.setTextColor(getResources().getColor(R.color.lightgreen));
                            t1v.setGravity(Gravity.CENTER);
                            //t1v.setTypeface(null, Typeface.BOLD);
                            t1v.setLayoutParams(lp);
                            t1v.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                            tbrow.addView(t1v);
                            tbrow.setGravity(Gravity.CENTER);

                            for (int j = 0; j < lectures.size(); j++)
                            {
                                attstatus = lectures.get(j).getatt_status();
                                Log.d("Att_status", attstatus);
                                lecstatus = lectures.get(j).getlec_status();
                                Log.d("Lecture_Status", lecstatus);

                                TextView t1v4 = new TextView(Student_Attendance.this);
                                t1v4.setTextSize(17);


                                switch (attstatus)
                                {
                                    case "R":
                                        t1v4.setTextColor(getResources().getColor(R.color.remaining));
                                        t1v4.setTypeface(null, Typeface.BOLD);
                                        break;
                                    case "null":
                                        t1v4.setTextColor(getResources().getColor(R.color.red));
                                        t1v4.setTypeface(null, Typeface.BOLD);
                                        break;
                                    case "A":
                                        t1v4.setTextColor(getResources().getColor(R.color.absent));
                                        t1v4.setTypeface(null, Typeface.BOLD);
                                        break;
                                    case "P":
                                        t1v4.setTextColor(getResources().getColor(R.color.present));
                                        t1v4.setTypeface(null, Typeface.BOLD);
                                        break;
                                    case "S":
                                        t1v4.setTextColor(getResources().getColor(R.color.suspend));
                                        t1v4.setTypeface(null, Typeface.BOLD);
                                        break;
                                }

                                t1v4.setText(attstatus);
                                t1v4.setLayoutParams(lp);
                                // t1v4.setPadding(5, 0, 5, 0);
                                t1v4.setGravity(Gravity.CENTER);

                                if (lectures.get(j).getlec_status().equals("2") || lectures.get(j).getlec_status().equals("3"))
                                {
                                    if (lectures.get(j).getlec_status().equals("3"))
                                    {
                                        t1v4.setText("");
                                    }
                                    else
                                        {
                                        t1v4.setGravity(Gravity.END);
                                    }

                                    t1v4.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                                }
                                else if (j > 0 && j != 5)
                                {
                                    String lecstatus3 = lectures.get(j - 1).getlec_status();
                                    Log.d("Lectures3", lecstatus3);
                                    String attstatus3 = lectures.get(j - 1).getatt_status();
                                    Log.d("AttStatus3", attstatus3);

                                    if (lecstatus3.equals("2") || lecstatus3.equals("3"))
                                    {
                                        if (lecstatus3.equals("3"))
                                        {
                                            switch (attstatus3)
                                            {
                                                case "R":
                                                    t1v4.setTextColor(getResources().getColor(R.color.remaining));
                                                    t1v4.setTypeface(null, Typeface.BOLD);
                                                    break;
                                                case "null":
                                                    t1v4.setTextColor(getResources().getColor(R.color.red));
                                                    t1v4.setTypeface(null, Typeface.BOLD);
                                                    break;
                                                case "A":
                                                    t1v4.setTextColor(getResources().getColor(R.color.absent));
                                                    t1v4.setTypeface(null, Typeface.BOLD);
                                                    break;
                                                case "P":
                                                    t1v4.setTextColor(getResources().getColor(R.color.present));
                                                    t1v4.setTypeface(null, Typeface.BOLD);
                                                    break;
                                                case "S":
                                                    t1v4.setTextColor(getResources().getColor(R.color.suspend));
                                                    t1v4.setTypeface(null, Typeface.BOLD);
                                                    break;
                                            }

                                            t1v4.setText(attstatus3);

                                        }
                                        else
                                            {
                                            t1v4.setText("");
                                        }

                                        if (attstatus3.equals(""))
                                        {
                                            t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                        }
                                        else
                                            {
                                            t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                        }
                                    }
                                    else
                                        {
                                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                                    }

                                    if (j > 1)
                                    {
                                        String lecstatus4 = lectures.get(j - 2).getlec_status();
                                        Log.d("Lectures4", lecstatus4);
                                        String attstatus4 = lectures.get(j - 2).getatt_status();
                                        Log.d("AttStatus4", attstatus4);

                                        if (lecstatus4.equals("3"))
                                        {
                                            t1v4.setText("");
                                            if (attstatus4.equals(""))
                                            {
                                                t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                            }
                                            else
                                                {
                                                t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                            }
                                        }
                                    }
                                }
                                else if (j == 5)
                                {

                                    String lecstatus3 = lectures.get(4).getlec_status();

                                    if (lecstatus3.equals("2") || lecstatus3.equals("3")) {
                                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_last_cell_batch));
                                    } else

                                    {
                                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_last_cell));
                                    }
                                    // Log.d("last_row","inside");
                                } else {
                                    t1v4.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                                }

                                /* if(!lecstatus.equals("2"))
                                    {
                                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                    }*/

                                if (attstatus.equals("") && (lecstatus.equals("2") || lecstatus.equals("3"))) {
                                    //t1v4.setBackgroundResource(R.color.list_divider);
                                    // t1v4.setBackgroundColor(getResources().getColor(R.color.list_divider));
                                    //String myHexColor = "#d9d9d9";

                                    // t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_bg_null));
                                    /*if(!lecstatus.equals("2"))
                                    {
                                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                    }*/
                                    // t1v4.setBackgroundColor(Color.parseColor("#E0E0E0"));
                                }
                                if (stringb.contains("H"))
                                {
                                    t1v4.setTextColor(getResources().getColor(R.color.holiday));
                                    t1v4.setTypeface(null, Typeface.BOLD);
                                    t1v4.setText("H");
                                    // t1v.setTextColor(getResources().getColor(R.color.news));
                                }
                                tbrow.setGravity(Gravity.CENTER);
                                tbrow.addView(t1v4);

                            }
                            tableLayout.addView(tbrow, i + 1);
                        }
                    } else {
                        Toast.makeText(Student_Attendance.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (String.valueOf(storage.read("swd_division_id", 3)).equals("0") || String.valueOf(storage.read("swd_batch_id", 3)).equals("0")) {
                        Toast.makeText(Student_Attendance.this, "You have not allocated Batch/Division", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Student_Attendance.this, "No Records Found", Toast.LENGTH_LONG).show();
//                        Toast.makeText(Student_Attendance.this,"null",Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(Student_Attendance.this, "You have not allocated Batch/Division", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StudentAttendance>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
                Toast.makeText(Student_Attendance.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
