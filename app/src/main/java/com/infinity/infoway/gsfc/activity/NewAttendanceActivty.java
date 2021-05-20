package com.infinity.infoway.gsfc.activity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.ViewPagerAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.LectWiseAttendance;
import com.infinity.infoway.gsfc.fragment.SubWiseAttendance;

public class NewAttendanceActivty extends AppCompatActivity {

   // private MonthYearPicker myp;
    TabLayout tabsgsfc;
    TableRow tbrow1;
    String attstatus, lecstatus, date;
    TextView tvFromDate, tvToDate;
    Button btnviewattendance;
    LinearLayout llToDate, llFromDate;
    DataStorage storage;
    Toolbar toolbar;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_attendance_activty);

        toolbar = (Toolbar) findViewById(R.id.toolbargsfc);
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
        setupViewPager(viewPager, 0.0);

    }

    public void findviews() {
        storage = new DataStorage("Login_Detail", NewAttendanceActivty.this);
        tabsgsfc = (TabLayout) findViewById(R.id.tabsgsfc);
        viewPager = (ViewPager) findViewById(R.id.vp_attgsfc);
        tabsgsfc.setupWithViewPager(viewPager);
        View root = tabsgsfc.getChildAt(0);

        if (root instanceof LinearLayout)
        {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(20);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
//        Calendar newDate = Calendar.getInstance();
//        newDate.set(Calendar.DAY_OF_MONTH, 1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
//        dateFormat.format(newDate.getTime());
//
//        tbrow1 = (TableRow) findViewById(R.id.tbrow1);
//        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
//        tvFromDate.setText(dateFormat.format(newDate.getTime()));
//        tvFromDate.setOnClickListener(this);
//        tvToDate = (TextView) findViewById(R.id.tvToDate);
//        tvToDate.setOnClickListener(this);
//        Calendar newDate2 = Calendar.getInstance();
//
//        //tvToDate.setText(dateFormat.format(newDate2.getTime()));
//        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
//        llToDate = (LinearLayout) findViewById(R.id.llToDate);
//        btnviewattendance = (Button) findViewById(R.id.btnviewattendance);
//        btnviewattendance.setOnClickListener(this);
//        tableLayout = (TableLayout) findViewById(R.id.table1);


    }

    private void setupViewPager(ViewPager viewPager, Double progress)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LectWiseAttendance(), "  " + "Lecture-Wise Attendance" + "  ");
        adapter.addFragment(new SubWiseAttendance(), "  " + "Subject-Wise Attendance" + "  ");
        viewPager.setAdapter(adapter);
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
}
