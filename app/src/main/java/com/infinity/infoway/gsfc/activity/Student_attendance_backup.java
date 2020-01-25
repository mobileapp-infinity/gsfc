package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MonthYearPicker;
import com.infinity.infoway.gsfc.model.InternshipNamePojo;
import com.infinity.infoway.gsfc.model.Methods;
import com.infinity.infoway.gsfc.model.Std_att_pojo;
import com.infinity.infoway.gsfc.model.StudentAttendance;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Student_attendance_backup extends AppCompatActivity implements View.OnClickListener {

    private MonthYearPicker myp;
    TableLayout tableLayout;
    TableRow tbrow1;
    String attstatus, lecstatus, date;
    TextView tvFromDate, tvToDate;
    Button btnviewattendance;
    LinearLayout llToDate, llFromDate;
    DataStorage storage;
    Toolbar toolbar;

    ArrayList<String> arr_date;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_backup);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);

            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findviews();

        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvFromDate.setText(myp.getSelectedMonthName() + "-" + myp.getSelectedYear());
            }
        }, null);
        init();


    }


    public void findviews() {

        queue = Volley.newRequestQueue(Student_attendance_backup.this);
        storage = new DataStorage("Login_Detail", Student_attendance_backup.this);
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

    //*********** for show attendance only 15days in desc order ****** 06 nov 2019 **** NIRALI ****
    Std_att_pojo std_att_pojo;
    Std_att_pojo std_att_pojo_MASTER;
    Std_att_pojo std_att_pojo_MASTER_PREV;

    Std_att_pojo std_att_pojo_MASTER_CURRENRT;
    Std_att_pojo std_att_pojo_TEMP;
    ArrayList<Std_att_pojo.DataBean> listall;
    ArrayList<Std_att_pojo.DataBean> listallDATA_MASTER;
    ArrayList<String> listDATE_MASTER;
    int TO_DISPLAY_COUNT = 15;
    int NO_OF_DATA_FROM_PREVIOUS_MNTH = 0;

    public void init()
    {

        DialogUtils.showProgressDialog(Student_attendance_backup.this, "");
        String URLs = URl.get_student_attendance_api + "&year=" + String.valueOf(myp.getSelectedYear()) + "&month=" + String.valueOf(myp.getSelectedMonth() + 1) + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) + "&course_id=" + String.valueOf(storage.read("course_id", 3)) + "&div_id=" + String.valueOf(storage.read("swd_division_id", 3)) + "&shift_id=" + String.valueOf(storage.read("shift_id", 3)) + "&batch_id=" + String.valueOf(storage.read("swd_batch_id", 3)) + "&year_id=" + String.valueOf(storage.read("swd_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("get_student_attendance_api calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Data\":" + response + "}";

                        //System.out.println("THIS IS get_student_attendance_api RESPONSE    " + response + "");
                        listall = new ArrayList<>();
                        arr_date = new ArrayList<>();
                        std_att_pojo = new Std_att_pojo();
                        std_att_pojo_MASTER = new Std_att_pojo();
                        std_att_pojo_TEMP = new Std_att_pojo();
                        std_att_pojo_MASTER_CURRENRT = new Std_att_pojo();
                        std_att_pojo_MASTER_PREV = new Std_att_pojo();

                        listDATE_MASTER = new ArrayList<>();
                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            std_att_pojo = gson.fromJson(response, Std_att_pojo.class);

                            if (std_att_pojo != null && std_att_pojo.getData().size() > 0)
                            {

                                System.out.println("size of pojo ******" + std_att_pojo.getData().size());


                                listall.addAll(std_att_pojo.getData());

                                System.out.println("listall Size :::::::::::::::::::::::" + listall.size());


                                /*REVERSE CURRENT MONTH DATA*/
                                for (int j = listall.size() - 1; j >= 0; j--)
                                {

                                    listDATE_MASTER.add(listall.get(j).getDate() + "".trim());


                                }

                                /*FIND TODAY'S DATE*/
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = mdformat.format(calendar.getTime());

                                System.out.println("s Date ::::::: " + strDate);

                                //int cDate = listall.indexOf(strDate);
                                /*FIND CURRENT DATA INDEX FROM LIST*/
                                int cDate = listDATE_MASTER.indexOf(strDate);

                                System.out.println("cDate index  :::::::" + cDate);
                                System.out.println("cDate POS listDATE_MASTER  :::::::" + listDATE_MASTER.get(cDate) + "");

                                /*size to index remove element*/

                            //    List<String> arrlist2OfDateCNT = listDATE_MASTER.subList(cDate, listDATE_MASTER.size());

                               // System.out.println("final data!!!!! " + arrlist2OfDateCNT.toString());
                                List<Std_att_pojo.DataBean> list1 = new ArrayList<>();
                                for (int j = listall.size() - 1; j >= 0; j--)
                                {
                                    Std_att_pojo.DataBean bean = new Std_att_pojo.DataBean();
                                    bean = std_att_pojo.getData().get(j);
                                    list1.add(bean);
                                    std_att_pojo_TEMP.setData(list1);
                                    System.out.println("std_att_pojo_TEMP " + std_att_pojo_TEMP.getData().size());

                                }

                                /*ADD SELECTED CURRENT MONTH DATA TO POJO*/
                                List<Std_att_pojo.DataBean> list = new ArrayList<>();
                                for (int i = cDate; i < listDATE_MASTER.size(); i++)
                                {
                                    Std_att_pojo.DataBean bean = new Std_att_pojo.DataBean();
                                    bean = std_att_pojo_TEMP.getData().get(i);
                                    list.add(bean);
                                    std_att_pojo_MASTER_CURRENRT.setData(list);
                                    System.out.println("std_att_pojo_MASTER_CURRENRT******* " + std_att_pojo_MASTER_CURRENRT.getData().size() + "");
                                }
                          //      System.out.println("arrlist2OfDateCNT.size()********** " + arrlist2OfDateCNT.size() + "");

                                System.out.println("*****data is 15 already no need to call last month api*****");



                                /*CURRENT MONTH DATA IS ALREADY 15 OR  NOT CHECK*/
                                if (std_att_pojo_MASTER_CURRENRT != null && std_att_pojo_MASTER_CURRENRT.getData().size() >= TO_DISPLAY_COUNT)
                                {
                                    std_att_pojo_MASTER = std_att_pojo_MASTER_CURRENRT;


                                    /*IF DATA IS ALFREDY 15 THEN DISPLAY ALL DATA */
                                    DisplayData();
                                }
                                else
                                    {
                                      /*previous month data*/
                                        if (std_att_pojo_MASTER_CURRENRT!=null)
                                        {
                                            NO_OF_DATA_FROM_PREVIOUS_MNTH = TO_DISPLAY_COUNT - std_att_pojo_MASTER_CURRENRT.getData().size();

                                            System.out.println("COUNT OF PREVIOUS MONTH :::::: " + NO_OF_DATA_FROM_PREVIOUS_MNTH);

                                            //api
                                            //reverse
                                            // loop from NO_OF_DATA_FROM_PREVIOUS_MNTH to size

                                            /*CALL PREVIOUS MONTH API BECAUSE OF THERE IS NO 15 required  DATA*/
                                            LastMonthApiCall();
                                        }



                                }


                            }
                            else {

                                Toast.makeText(Student_attendance_backup.this, "No Records Found", Toast.LENGTH_LONG).show();

                            }
                        } else {

                            Toast.makeText(Student_attendance_backup.this, "No Records Found", Toast.LENGTH_LONG).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }

    private void DisplayData()
    {
            DialogUtils.hideProgressDialog();
        if (std_att_pojo_MASTER.getData().size() > 0)
        {

            //    System.out.println("stud data size ::::::::::"+std_att_pojo.getData().size());
            System.out.println("stud data size ::::::::::" + std_att_pojo_MASTER.getData().size());
            TableRow tbrow2 = new TableRow(Student_attendance_backup.this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tbrow2.setLayoutParams(lp2);
            TextView t1v1 = new TextView(Student_attendance_backup.this);
            t1v1.setText("Date/Lect");
            t1v1.setTextColor(getResources().getColor(R.color.white));
            t1v1.setTypeface(null, Typeface.BOLD);
            t1v1.setGravity(Gravity.CENTER);
            t1v1.setBackground(getResources().getDrawable(R.drawable.table_cell_header_bg));
            t1v1.setPadding(5, 10, 5, 10);
            t1v1.setBackgroundColor(getResources().getColor(R.color.blueattendance));
//                        t1v1.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
            tbrow2.addView(t1v1);
            tbrow2.setGravity(Gravity.CENTER);
            tbrow2.setBackgroundColor(getResources().getColor(R.color.blueattendance));

            for (int j = 0; j < std_att_pojo_MASTER.getData().get(0).getAll_lecture().size(); j++) {
                TextView t1v2 = new TextView(Student_attendance_backup.this);
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

//            for (int i = 0; i < std_att_pojo_MASTER.getData().size(); i++) {
            if (std_att_pojo_MASTER.getData().size() >= TO_DISPLAY_COUNT) {

            } else {
                TO_DISPLAY_COUNT = std_att_pojo_MASTER.getData().size();
            }
            for (int i = 0; i < TO_DISPLAY_COUNT; i++) {
                List<Std_att_pojo.DataBean.AllLectureBean> lectures = std_att_pojo_MASTER.getData().get(i).getAll_lecture();
                TableRow tbrow = new TableRow(Student_attendance_backup.this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tbrow.setLayoutParams(lp);

                date = std_att_pojo_MASTER.getData().get(i).getDate();
                Log.d("date", date);
                String stringa;
                String stringb = "";
                if (date.contains("_")) {
                    String[] str_array = date.split("_");
                    Log.d("str_ary", String.valueOf(str_array));
                    stringa = str_array[0];
                    Log.d("ga", stringa);
                    stringb = str_array[1];
                    Log.d("gb", stringb);
                } else {
                    stringa = date;
                }

                TextView t1v = new TextView(Student_attendance_backup.this);
                t1v.setText(stringa);
                t1v.setTextSize(17);
                t1v.setTextColor(getResources().getColor(R.color.lightgreen));
                t1v.setGravity(Gravity.CENTER);
                //t1v.setTypeface(null, Typeface.BOLD);
                t1v.setLayoutParams(lp);
                t1v.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                tbrow.addView(t1v);
                tbrow.setGravity(Gravity.CENTER);

                for (int j = 0; j < lectures.size(); j++) {
                    attstatus = lectures.get(j).getAtt_status();
                    Log.d("Att_status", attstatus);
                    lecstatus = lectures.get(j).getLec_status();
                    Log.d("Lecture_Status", lecstatus);

                    TextView t1v4 = new TextView(Student_attendance_backup.this);
                    t1v4.setTextSize(17);


                    switch (attstatus) {
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

                    if (lectures.get(j).getLec_status().equals("2") || lectures.get(j).getLec_status().equals("3")) {
                        if (lectures.get(j).getLec_status().equals("3")) {
                            t1v4.setText("");
                        } else {
                            t1v4.setGravity(Gravity.END);
                        }

                        t1v4.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                    } else if (j > 0 && j != 5) {
                        String lecstatus3 = lectures.get(j - 1).getLec_status();
                        Log.d("Lectures3", lecstatus3);
                        String attstatus3 = lectures.get(j - 1).getLec_status();
                        Log.d("AttStatus3", attstatus3);

                        if (lecstatus3.equals("2") || lecstatus3.equals("3")) {
                            if (lecstatus3.equals("3")) {
                                switch (attstatus3) {
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

                            } else {
                                t1v4.setText("");
                            }

                            if (attstatus3.equals("")) {
                                t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                            } else {
                                t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                            }
                        } else {
                            t1v4.setBackground(getResources().getDrawable(R.drawable.table_row_bg));
                        }

                        if (j > 1) {
                            String lecstatus4 = lectures.get(j - 2).getLec_status();
                            Log.d("Lectures4", lecstatus4);
                            String attstatus4 = lectures.get(j - 2).getLec_status();
                            Log.d("AttStatus4", attstatus4);

                            if (lecstatus4.equals("3")) {
                                t1v4.setText("");
                                if (attstatus4.equals("")) {
                                    t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                } else {
                                    t1v4.setBackground(getResources().getDrawable(R.drawable.table_cell_right));
                                }
                            }
                        }
                    } else if (j == 5) {

                        String lecstatus3 = lectures.get(4).getLec_status();

                        if (lecstatus3.equals("2") || lecstatus3.equals("3")) {
                            t1v4.setBackground(getResources().getDrawable(R.drawable.table_last_cell_batch));
                        } else {
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
                    if (stringb.contains("H")) {
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
        }
    }

    ArrayList<Std_att_pojo.DataBean> reverseListlastmont;

    ArrayList<Std_att_pojo.DataBean> PRIVIOUS_MONTH_LIST;
    Std_att_pojo std_att_pojo_TEMP_new;

    private void LastMonthApiCall() {
       // DialogUtils.showProgressDialog(Student_attendance_backup.this, "");

        String URLs = URl.get_student_attendance_api + "&year=" + String.valueOf(myp.getSelectedYear()) + "&month=" + String.valueOf(myp.getSelectedMonth()) + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) + "&course_id=" + String.valueOf(storage.read("course_id", 3)) + "&div_id=" + String.valueOf(storage.read("swd_division_id", 3)) + "&shift_id=" + String.valueOf(storage.read("shift_id", 3)) + "&batch_id=" + String.valueOf(storage.read("swd_batch_id", 3)) + "&year_id=" + String.valueOf(storage.read("swd_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");

        System.out.println("get_student_attendance_api calls for last month  " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // DialogUtils.hideProgressDialog();

                        reverseListlastmont = new ArrayList<>();
                        std_att_pojo_TEMP_new = new Std_att_pojo();
                        PRIVIOUS_MONTH_LIST = new ArrayList<>();
                        response = response + "";

                        response = "{\"Data\":" + response + "}";

                        System.out.println("THIS IS get_student_attendance_api RESPONSE  for last month   " + response + "");

                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            std_att_pojo = gson.fromJson(response, Std_att_pojo.class);


                            if (std_att_pojo != null && std_att_pojo.getData().size() > 0)
                            {


                                reverseListlastmont.addAll(std_att_pojo.getData());

                                System.out.println("reverse List last month Size :::::::::::::::::::::::" + reverseListlastmont.size());


                                List<Std_att_pojo.DataBean> list2 = new ArrayList<>();
                                for (int j = reverseListlastmont.size() - 1; j >= 0; j--)
                                {

                                    System.out.println("reverse data reverse List last month date::::::::::::::::" + j + "::   " + reverseListlastmont.get(j).getDate());
                                    Std_att_pojo.DataBean bean = new Std_att_pojo.DataBean();
                                    bean = std_att_pojo.getData().get(j);
                                    list2.add(bean);
                                    std_att_pojo_TEMP_new.setData(list2);//reverse std_att_pojo_TEMP_new

                                }


                                /*show data only required from previous month*/
                                List<Std_att_pojo.DataBean> list3 = list2.subList(0, NO_OF_DATA_FROM_PREVIOUS_MNTH);
                                std_att_pojo_TEMP_new.setData(list3);

                                for (int k = 0; k < NO_OF_DATA_FROM_PREVIOUS_MNTH; k++)
                                {

                                    std_att_pojo_MASTER_PREV.setData(std_att_pojo_TEMP_new.getData());

                                    System.out.println("std_att_pojo_MASTER_PREV data from list ************" + std_att_pojo_MASTER_PREV.getData().get(k).getDate());

                                }


                                System.out.println("std_att_pojo_MASTER_PREV " + std_att_pojo_MASTER_PREV.getData().size() + "");
                                System.out.println("std_att_pojo_MASTER_CURRENRT " + std_att_pojo_MASTER_CURRENRT.getData().size() + "");
                                /*current data + prev*/
                                /*check for current and prev null*/


                                /*add current month data and previous month data to list and display it*/
                                if (std_att_pojo_MASTER_CURRENRT!=null && std_att_pojo_MASTER_PREV!=null)
                                {
                                    List<Std_att_pojo.DataBean> a = new ArrayList<>();
                                    a.addAll(std_att_pojo_MASTER_CURRENRT.getData());
                                    a.addAll(std_att_pojo_MASTER_PREV.getData());

                                    std_att_pojo_MASTER.setData(a);
                                    DisplayData();

                                }



                            }

                        } else {
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    @Override
    public void onClick(View v) {

        if (v == btnviewattendance) {
             init();
        }

        if (v == tvToDate) {
            //ppppppppppppppppppppppppp   showAndSetDateDialog(tvToDate, tvFromDate.getText().toString());
        }


        if (v == tvFromDate) {
            //ppppppppppppppp  myp.show();
            //showAndSetDateDialog(tvFromDate);
            // createDialogWithoutDateField(tvFromDate);
        }

    }
}
