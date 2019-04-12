package com.infinity.infoway.agriculture.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.EventAdapter;
import com.infinity.infoway.agriculture.adapter.ExpandableListAdapter1;
import com.infinity.infoway.agriculture.adapter.HolidayAdapter;
import com.infinity.infoway.agriculture.adapter.MoviesAdapter;
import com.infinity.infoway.agriculture.adapter.NewsAdapter;
import com.infinity.infoway.agriculture.adapter.NextHoliadyAdapter;
import com.infinity.infoway.agriculture.adapter.TempAdapter;
import com.infinity.infoway.agriculture.adapter.collagedisplayadapter;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.app.MarshMallowPermission;
import com.infinity.infoway.agriculture.model.ChartDataRemainingAttendance;
import com.infinity.infoway.agriculture.model.Emp_detail;
import com.infinity.infoway.agriculture.model.Holiday;
import com.infinity.infoway.agriculture.model.Holiday_next;
import com.infinity.infoway.agriculture.model.NewsData;
import com.infinity.infoway.agriculture.model.ProfileResponse;
import com.infinity.infoway.agriculture.model.birthdata;
import com.infinity.infoway.agriculture.model.collage_list;
import com.infinity.infoway.agriculture.model.dir_stud_attendance;
import com.infinity.infoway.agriculture.model.hod_list;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.jjoe64.graphview.GraphView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;

import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectivePageActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    //Entries for piechart labels
    ArrayList<String> PieEntryLabels;
    ArrayList<String> PieEntryLabels2;
    ArrayList<String> PieEntryLabels3;
    ImageView dir_logout, main_logout;
    ArrayList<ChartDataRemainingAttendance> chart_detail = new ArrayList<ChartDataRemainingAttendance>();
    public MarshMallowPermission marshMallowPermission = new MarshMallowPermission((Activity) context);

    ArrayList<String> labels = new ArrayList<>();
    DataStorage storage;
    Float s2, s1;

    private RecyclerView recyclerView, rec_next_holiday, rec_latest_news, rec_next_holiday_1, rec_collage_display;
    private MoviesAdapter mAdapter;
    HolidayAdapter holidayAdapter;
    collagedisplayadapter collagedisplayadapter_;
    EventAdapter eventAdapter;
    TempAdapter tempAdapter;
    NewsAdapter newsAdapter;
    NextHoliadyAdapter nextHoliadyAdapter;
    String collage_id;
    Calendar myCalendar = Calendar.getInstance();
    double d, d1;
    String val, val1, val3, stu_pie1_present1, stu_pie1_present2;
    HashMap<String, List<String>> listDataChildName;
    //    List<ChartData> value;
    //Expandable listview for collage display
    ExpandableListAdapter1 listAdapter;
    ExpandableListView expListView;
        List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChild1;
    HashMap<String, List<String>> listDataChild2;
    HashMap<String, List<String>> listDataChild3;
    //    StackBarChart stackBarChart;
    List<String> h_lables;
    EditText ed_selectdate_remaining_attendance;
    Button btn_view_attendance;
    StackedBarChart mStackedBarChart;
    ArrayList<Holiday> news_list;
    PieChart stud_pi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directive_page);

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
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(DirectivePageActivity.this)
                        //set message, title, and icon
                        .setTitle("Exit")
                        .setMessage("Do you want to Exit from this App?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                finish();
                                DirectivePageActivity.this.finish();

//                                Intent intent = new Intent(DirectivePageActivity.this, Login.class);
//                                startActivity(intent);
//                                finish();
                            }
                        })

                        .setNeutralButton("No", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                                dialog.dismiss();
                            }
                        }).create();

                myQuittingDialogBox.show();
            }
        });
        news_list = new ArrayList<>();
        findViews();


        // Stacked BarChart


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        ed_selectdate_remaining_attendance.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(DirectivePageActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONDAY),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateApp();
        init();
        studentChartAttendanceData();
        prepareBirthData();
        prepareHolidayData();
        prepareNewsData();
        prepareListDataExpan();


        rec_next_holiday = (RecyclerView) findViewById(R.id.rec_latest_event);
        rec_next_holiday.setAdapter(eventAdapter);


        //code for pie chart
//        entries = new ArrayList<>();
//        entries2 = new ArrayList<>();
//        entries3 = new ArrayList<>();

        PieEntryLabels = new ArrayList<>();
        PieEntryLabels2 = new ArrayList<>();
        PieEntryLabels3 = new ArrayList<>();

//        AddValuesToPIEENTRY();
//        AddValuesToPIEENTRY2();
//        AddValuesToPIEENTRY3();

        AddValuesToPieEntryLabels();
        AddValuesToPieEntryLabels2();
        AddValuesToPieEntryLabels3();

    /*    pieDataSet = new PieDataSet(entries, "");
        pieDataSet2 = new PieDataSet(entries2, "");
        pieDataSet3 = new PieDataSet(entries3, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieData2 = new PieData(PieEntryLabels2, pieDataSet2);
        pieData3 = new PieData(PieEntryLabels3, pieDataSet3);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
*/
     /*   pieChart.setData(pieData);
//        pieChart2.setData(pieData2);
        pieChart3.setData(pieData3);

        pieChart.animateY(3000);
//        pieChart2.animateY(2000);
        pieChart3.animateY(1000);
*/

        //for pie chart
//        pieChart = (PieChart) findViewById(R.id.chart1);
//        pieChart.setUsePercentValues(true);
       /* ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(8f, 0));
        yvalues.add(new Entry(15f, 1));
        yvalues.add(new Entry(12f, 2));
        yvalues.add(new Entry(25f, 3));
        yvalues.add(new Entry(23f, 4));
        yvalues.add(new Entry(17f, 5));
*/

    }


    public void findViews()
    {

        storage = new DataStorage("Login_Detail", DirectivePageActivity.this);
        StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
        ed_selectdate_remaining_attendance = (EditText) findViewById(R.id.ed_selectdate_remaining_attendance);
        Calendar newDate = Calendar.getInstance();
//        newDate.set(Calendar.DATE,15);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        ed_selectdate_remaining_attendance.setText(dateFormat.format(newDate.getTime()));
//        stud_piechart= (PieChart) findViewById(R.id.stud_piechart);
        dir_logout = (ImageView) findViewById(R.id.dir_dashboard);
        dir_logout.setOnClickListener(this);
        main_logout = (ImageView) findViewById(R.id.main_logout);
        main_logout.setOnClickListener(this);
        btn_view_attendance = (Button) findViewById(R.id.btn_view_attendance);
        btn_view_attendance.setOnClickListener(this);

        expListView = (ExpandableListView) findViewById(R.id.expan_collage_display);
        recyclerView = (RecyclerView) findViewById(R.id.rec_today_birthday);
//        rec_collage_display = (RecyclerView) findViewById(R.id.rec_collage_display);
        rec_next_holiday = (RecyclerView) findViewById(R.id.rec_latest_event);
        rec_next_holiday_1 = (RecyclerView) findViewById(R.id.rec_next_holiday);
    }

    private void updateLabel()
    {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_selectdate_remaining_attendance.setText(sdf.format(myCalendar.getTime()));

    }

    public void updateApp()
    {

        PackageInfo pInfo = null;

        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        assert  pInfo != null;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.checkversionupdate(pInfo.versionCode);
        call.enqueue(new Callback<ProfileResponse>()
        {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getVersion() == 1)
                    {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(DirectivePageActivity.this)
                                //set message, title, and icon
                                .setMessage("New version available.Would you like to update your app ?")
                                //.setMessage("Do you want to exit from this App?")

                                .setPositiveButton("Update", new DialogInterface.OnClickListener()
                                {

                                    public void onClick(DialogInterface dialog, int whichButton)
                                    {
                                        try
                                        {
                                            try
                                            {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();
                                            }
                                            catch (android.content.ActivityNotFoundException anfe)
                                            {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            //	System.out.println("");
                                        }

                                               /*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*/
                                    }
                                })

                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int whichButton)
                                    {

                                        dialog.dismiss();
                                    }
                                }).create();

                        myQuittingDialogBox.show();
                    }
                    else if (response.body().getVersion() == 2)
                    {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(DirectivePageActivity.this)
                                .setCancelable(false)
                                //set message, title, and icon
                                .setTitle("New version available in.Would you like to update your app ?")
                                //.setMessage("Do you want to exit from this App?")

                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try
                                        {
                                            try
                                            {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();

                                            }
                                            catch (android.content.ActivityNotFoundException anfe)
                                            {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();

                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            //	System.out.println("");
                                        }

                                               /*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*/
                                    }
                                }).create();

                        myQuittingDialogBox.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t)
            {
                // Log error here since request failed
                //  Toast.makeText(Main3Activity.this,"Error in Response",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void init()
    {

        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ChartDataRemainingAttendance>> call = apiService.get_chart_data(ed_selectdate_remaining_attendance.getText().toString());
        call.enqueue(new Callback<ArrayList<ChartDataRemainingAttendance>>()
        {
            @Override
            public void onResponse(Call<ArrayList<ChartDataRemainingAttendance>> call, Response<ArrayList<ChartDataRemainingAttendance>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    Log.d("attlist", String.valueOf(response.body().size()));

                    if (response.body().size() >= 1)
                    {

                        chart_detail = response.body();
//                        response.body().clear();
                        for (int i = 0; i < chart_detail.size(); i++)
                        {

                            Log.d("chart_detail", String.valueOf(chart_detail.size()));

                            mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);

                            List<String> h_lables = new ArrayList<String>();
                            h_lables.add(response.body().get(i).getDepartment());
                            StackedBarModel s1 = new StackedBarModel(String.valueOf(h_lables));


                            if (chart_detail.get(i).getCompleted() == null)
                            {
                                if (!(chart_detail.get(i).getRemaining() == null))
                                {
                                    if (chart_detail.get(i).getRemaining() == chart_detail.get(i).getTotal()) {
                                        val = chart_detail.get(i).getRemaining();
                                        Log.d("val", val);
                                        val1 = chart_detail.get(i).getRemaining();
                                        Log.d("val1", val1);
                                        Float f = Float.parseFloat(val);
                                        Float f1 = Float.parseFloat(val1);

                                        s1.addBar(new BarModel(f, 0xFFCDA67F));
                                        s1.addBar(new BarModel(f1, 0xFF56B7F1));


                                    }
                                }

                            }
                            else if (chart_detail.get(i).getRemaining() == null)
                            {
                                if (!(chart_detail.get(i).getCompleted() == null))
                                {

                                    val = chart_detail.get(i).getCompleted();
                                    Log.d("val2", val);
                                    val1 = chart_detail.get(i).getCompleted();
                                    Log.d("val3", val1);
                                    Float f = Float.parseFloat(val);
                                    Float f1 = Float.parseFloat(val1);

                                    s1.addBar(new BarModel(f, 0xFF56B7F1));
                                    s1.addBar(new BarModel(f1, 0xFF56B7F1));

                                }


                            }
                            else
                                {

                                val = chart_detail.get(i).getCompleted();
                                Log.d("val4", val);
                                val1 = chart_detail.get(i).getRemaining();
                                Log.d("val5", val1);


                                Float f = Float.parseFloat(val);
                                Float f1 = Float.parseFloat(val1);

                                s1.addBar(new BarModel(f, 0xFF63CBB0));
                                s1.addBar(new BarModel(f1, 0xFF56B7F1));

                            }


                            mStackedBarChart.isShowValues();
                            mStackedBarChart.performClick();
                            mStackedBarChart.startAnimation();
                            mStackedBarChart.addBar(s1);
//                            mStackedBarChart.cancelLongPress();


                        }
                    }
                    else
                        {
//                        mStackedBarChart.clearChart();
                        Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {

                }

            }

            public void onFailure(Call<ArrayList<ChartDataRemainingAttendance>> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    public void studentChartAttendanceData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<dir_stud_attendance> call = apiService.get_dir_stud_attendance(ed_selectdate_remaining_attendance.getText().toString());

        call.enqueue(new Callback<dir_stud_attendance>()
        {
            @Override
            public void onResponse(Call<dir_stud_attendance> call, Response<dir_stud_attendance> response) {
                progressDialog.dismiss();
//                mStackedBarChart.clearChart();
                if (response.isSuccessful())
                {

                    stud_pi = (PieChart) findViewById(R.id.stud_piechart);

                    if (response.body().getP_per() == null)
                    {
                        stu_pie1_present1 = response.body().getAB_per();

                    }
                    else if (response.body().getAB_per() == null)
                    {
                        stu_pie1_present2 = response.body().getP_per();

                    }
                    else if (response.body().getP_per() == null && response.body().getAB_per() == null)
                    {
                        Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();

                    }
                    else
                        {

                        stu_pie1_present1 = response.body().getP_per();
                        stu_pie1_present2 = response.body().getAB_per();
                        s1 = Float.parseFloat(stu_pie1_present1);
                        s2 = Float.parseFloat(stu_pie1_present2);

                            stud_pi.addPieSlice(new PieModel("Present", s1, Color.parseColor("#FE6DA8")));
                            stud_pi.addPieSlice(new PieModel("Absent", s2, Color.parseColor("#56B7F1")));
                            stud_pi.startAnimation();

                    }


                }
                else
                {

                }

                if (response.body().getAB_per() == null && response.body().getP() == null && response.body().getP_per() == null && response.body().getTotal() == null) {
                    Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                }

            }

            public void onFailure(Call<dir_stud_attendance> call, Throwable t)
            {
                progressDialog.dismiss();

            }
        });
    }


    private void prepareBirthData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<birthdata>> call = apiService.get_today_birthday();

        call.enqueue(new Callback<ArrayList<birthdata>>()
        {
            @Override
            public void onResponse(Call<ArrayList<birthdata>> call, Response<ArrayList<birthdata>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1)
                    {
                       for (int i = 0; i < response.body().size(); i++)
                        {

                            ArrayList<birthdata> news_detail = response.body();
                            Log.d("news_detail", String.valueOf(news_detail.size()));
                            recyclerView = (RecyclerView) findViewById(R.id.rec_today_birthday);
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            mAdapter = new MoviesAdapter(DirectivePageActivity.this, news_detail);
                            recyclerView.setAdapter(mAdapter);

                        }
                    }
                    else
                        {
                        Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    //  Toast.makeText(EmployeeLeave.this, "Error in Response", Toast.LENGTH_LONG).show();
                }
            }

            public void onFailure(Call<ArrayList<birthdata>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
            }
        });
    }

    private void prepareHolidatListData1()
    {

    }

    public void preparecollagedatadisplay() {
        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<collage_list>> call = apiService.get_collage_list();
        call.enqueue(new Callback<ArrayList<collage_list>>()
        {
            @Override
            public void onResponse(Call<ArrayList<collage_list>> call, Response<ArrayList<collage_list>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {
                            collage_id = response.body().get(i).getCollege_id();
                            Log.d("collage_id", collage_id);
                            ArrayList<collage_list> news_detail = response.body();
                            Log.d("news_detail", String.valueOf(news_detail.size()));
//                            rec_collage_display = (RecyclerView) findViewById(R.id.rec_collage_display);
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rec_collage_display.setLayoutManager(layoutManager);
                            collagedisplayadapter_ = new collagedisplayadapter(DirectivePageActivity.this, news_detail);
                            rec_collage_display.setAdapter(collagedisplayadapter_);

                        }
                    }
                    else
                        {
                        Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {

                }
            }


            public void onFailure(Call<ArrayList<collage_list>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
            }
        });

    }

//    public  void showEvent(){
//
//        Holiday holiday1=new Holiday("17/2/2018","Sportrs Day");
//        news_list.add(holiday1);
//        holiday1=new Holiday("18/2/2018","Holiday");
//        news_list.add(holiday1);
//
//        eventAdapter.notifyDataSetChanged();
//
//    }


    private void prepareNewsData() {
//        Holiday holiday1=new Holiday("25/8/2018","placement news");
//        news_list.add(holiday1);
//        holiday1=new Holiday("85/25/2018","campus news");
//        news_list.add(holiday1);
//        newsAdapter.notifyDataSetChanged();
        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<NewsData>> call = apiService.get_news();

        call.enqueue(new Callback<ArrayList<NewsData>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsData>> call, Response<ArrayList<NewsData>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1) {
                        for (int i = 0; i < response.body().size(); i++) {

                            ArrayList<NewsData> news_detail = response.body();
                            Log.d("news_detail", String.valueOf(news_detail.size()));
                            rec_latest_news = (RecyclerView) findViewById(R.id.rec_latest_news);
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rec_latest_news.setLayoutManager(layoutManager);
                            newsAdapter = new NewsAdapter(DirectivePageActivity.this, news_detail);
                            rec_latest_news.setAdapter(newsAdapter);

                        }
                    } else {
                        Toast.makeText(DirectivePageActivity.this, "No Records found", Toast.LENGTH_LONG).show();

                    }
                } else {
                    //  Toast.makeText(EmployeeLeave.this, "Error in Response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewsData>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
            }
        });


    }

    private void prepareHolidayData() {

        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Holiday_next>> call = apiService.get_next_holiday(String.valueOf(storage.read("emp_main_school_id", 3)));

        call.enqueue(new Callback<ArrayList<Holiday_next>>() {
            @Override
            public void onResponse(Call<ArrayList<Holiday_next>> call, Response<ArrayList<Holiday_next>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Log.d("attlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1) {
                        for (int i = 0; i < response.body().size(); i++) {

                            ArrayList<Holiday_next> holiday_detail = response.body();
                            Log.d("holiday_detail", String.valueOf(holiday_detail.size()));
                            rec_next_holiday_1 = (RecyclerView) findViewById(R.id.rec_next_holiday);
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rec_next_holiday_1.setLayoutManager(layoutManager);
                            nextHoliadyAdapter = new NextHoliadyAdapter(DirectivePageActivity.this, holiday_detail);
                            rec_next_holiday_1.setAdapter(nextHoliadyAdapter);

                        }
                    } else {
                        Toast.makeText(DirectivePageActivity.this, "No Records found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DirectivePageActivity.this, "Please try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Holiday_next>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("employeeattendancerespo", t.toString());
            }
        });


    }

    public void prepareListDataExpan()
    {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChild1 = new HashMap<String, List<String>>();
        listDataChild2 = new HashMap<String, List<String>>();
        listDataChild3 = new HashMap<String, List<String>>();
        listDataChildName = new HashMap<>();

        final ProgressDialog progressDialog = new ProgressDialog(DirectivePageActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<hod_list>> call = apiService.get_collage_hod_list();
        call.enqueue(new Callback<ArrayList<hod_list>>()
        {
            @Override
            public void onResponse(Call<ArrayList<hod_list>> call, Response<ArrayList<hod_list>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {

                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {


                            //Adding Header
                            listDataHeader.add(response.body().get(i).getCollege_name());
                            Log.d("listdataheader", String.valueOf(listDataHeader));

//
                            // Adding child data
                            List<String> childlist = new ArrayList<String>();
                            List<String> childlist1 = new ArrayList<String>();
                            List<String> childlist2 = new ArrayList<String>();
                            List<String> childlist3 = new ArrayList<String>();

                            ArrayList<Emp_detail> Emp_detail = response.body().get(i).getEmp_detail();
                            for (int j=0; j< Emp_detail.size(); j++)
                            {
                                childlist.add(Emp_detail.get(j).getEmp_name());
                                childlist1.add(Emp_detail.get(j).getDepartment_name());
                                childlist2.add(Emp_detail.get(j).getDesignation());
                                childlist3.add(Emp_detail.get(j).getEmp_mobile_no());

                            }

                            listDataChild.put(listDataHeader.get(i), childlist);
                            listDataChild1.put(listDataHeader.get(i), childlist1);
                            listDataChild2.put(listDataHeader.get(i), childlist2);
                            listDataChild3.put(listDataHeader.get(i), childlist3);


                            Log.d("childdata", String.valueOf(listDataChild));
//                            listDataChildName.put(listDataHeader.get(i), childlistname);
//                            Log.d("childname", String.valueOf(listDataChildName));
                        }

                        tempAdapter = new TempAdapter(DirectivePageActivity.this, listDataHeader, listDataChild1, listDataChild2, listDataChild3, listDataChild);
                        expListView.setAdapter(tempAdapter);
                    }
                    else
                        {

                        tempAdapter = new TempAdapter(DirectivePageActivity.this, listDataHeader, listDataChild1, listDataChild2, listDataChild3, listDataChild);
                        expListView.setAdapter(tempAdapter);
                        Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } else

                {
                    // Toast.makeText(Employee_Attendance.this, "Error in Response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<hod_list>> call, Throwable t)
            {
                progressDialog.dismiss();
                // Log error here since request failed
                //     Log.e("emplotteattendancerespo", t.toString());
            }
        });

    }

    /*public void AddValuesToPIEENTRY()
    {
        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(4f, 1));

    }

    public void AddValuesToPIEENTRY2()
    {
        entries2.add(new BarEntry(8f, 0));
        entries2.add(new BarEntry(4f, 1));

    }

    public void AddValuesToPIEENTRY3()
    {
        entries3.add(new BarEntry(5f, 0));
        entries3.add(new BarEntry(4f, 1));

    }*/

    public void AddValuesToPieEntryLabels()
    {
        PieEntryLabels.add("Present");
        PieEntryLabels.add("Absent");
    }

    public void AddValuesToPieEntryLabels2()
    {
        PieEntryLabels.add("Present");
        PieEntryLabels2.add("Absent");
        PieEntryLabels.add("Total");
    }

    public void AddValuesToPieEntryLabels3()
    {
        PieEntryLabels3.add("Present");
        PieEntryLabels3.add("Absent");
    }

    @Override
    public void onClick(View view) {
        if (view == btn_view_attendance) {

            if (chart_detail.isEmpty()) {
                Toast.makeText(DirectivePageActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            } else {
                mStackedBarChart.clearChart();
            }

//            mStackedBarChart.clearChart();
            stud_pi.clearChart();
            init();
            studentChartAttendanceData();
        }

        if (view == main_logout) {
//
//            Intent it = new Intent(DirectivePageActivity.this, Main3Activity.class);
//            startActivity(it);
//            finish();
            AlertDialog myQuittingDialogBox = new AlertDialog.Builder(DirectivePageActivity.this)
                    //set message, title, and icon
                    .setTitle("Logout")
                    .setMessage("Do you want to logout from this App?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            storage.clear();
                            Intent intent = new Intent(DirectivePageActivity.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })

                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    }).create();

            myQuittingDialogBox.show();
        }
        if (view == dir_logout) {
            Intent intent = new Intent(DirectivePageActivity.this, Main3Activity.class);
            startActivity(intent);
            finish();
        }
        {

        }


    }
}
