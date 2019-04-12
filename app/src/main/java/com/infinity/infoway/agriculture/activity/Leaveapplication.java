package com.infinity.infoway.agriculture.activity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.ViewPagerElerningAdapter;
import com.infinity.infoway.agriculture.fragment.AddLeaveApplicationFrg;
import com.infinity.infoway.agriculture.fragment.MyGroup;
import com.infinity.infoway.agriculture.fragment.ViewElerningGrp;
import com.infinity.infoway.agriculture.fragment.ViewleaveApplication;

public class Leaveapplication extends AppCompatActivity
{
    Toolbar toolbar;
      TabLayout tabs_leave_app;
      ViewPager vp_leave_app;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaveapplication);



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
        findViews();

        setupViewPager(vp_leave_app, 0.0);
    }

    public void findViews()
    {
        tabs_leave_app =(TabLayout)findViewById(R.id.tabs_leave_app);
        vp_leave_app =(ViewPager)findViewById(R.id.vp_leave_app);
        tabs_leave_app.setupWithViewPager(vp_leave_app);
        View root = tabs_leave_app.getChildAt(0);

        if (root instanceof LinearLayout)
        {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.black));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }


    private void setupViewPager(ViewPager viewPager, Double progress) {
        ViewPagerElerningAdapter adapter = new ViewPagerElerningAdapter(getSupportFragmentManager());

        adapter.addFragment(new AddLeaveApplicationFrg(), "  " + "Add Leave Applicaion" + "  ");
        adapter.addFragment(new ViewleaveApplication(), "  " + "View Leave Application" + "  ");

        viewPager.setAdapter(adapter);
    }

}
