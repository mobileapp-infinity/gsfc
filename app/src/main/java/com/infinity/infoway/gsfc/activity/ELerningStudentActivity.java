package com.infinity.infoway.gsfc.activity;

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


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.ViewPagerElerningAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.EnrollGrpNewElerning;
import com.infinity.infoway.gsfc.fragment.MyGroupNewElerningFrg;

public class ELerningStudentActivity extends AppCompatActivity {

    RequestQueue queue;

    DataStorage storage;
    Toolbar toolbar;
    public  TabLayout tab_elerning;
    public  ViewPager vp_elerning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elerning_student);

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
        INIT_VIEWS();

        setupViewPager(vp_elerning, 0.0);

        //Enroll_To_Group_API();
    }

    private void INIT_VIEWS()
    {

        queue = Volley.newRequestQueue(this);

        storage = new DataStorage("Login_Detail", this);

        tab_elerning = (TabLayout) findViewById(R.id.tabs_elerning);
        vp_elerning = (ViewPager) findViewById(R.id.vp_elerning);
        tab_elerning.setupWithViewPager(vp_elerning);
        View root = tab_elerning.getChildAt(0);

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

    private void setupViewPager(ViewPager viewPager, Double progress)
    {
        ViewPagerElerningAdapter adapter = new ViewPagerElerningAdapter(getSupportFragmentManager());


        adapter.addFragment(new EnrollGrpNewElerning(), "   " + "Join Group" + "    ");
        adapter.addFragment(new MyGroupNewElerningFrg(ELerningStudentActivity.this), "   " + "Enroll To Group" + "    ");
        viewPager.setAdapter(adapter);
    }

}
