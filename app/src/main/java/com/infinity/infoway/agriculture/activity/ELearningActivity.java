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
import com.infinity.infoway.agriculture.fragment.MyGroup;
import com.infinity.infoway.agriculture.fragment.ViewElerningGrp;

public class ELearningActivity extends AppCompatActivity
{


    Toolbar toolbar;
    public static TabLayout tab_elerning;
    public static ViewPager vp_elerning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elearning);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });


        findViews();

        setupViewPager(vp_elerning, 0.0);


    }

    private void setupViewPager(ViewPager viewPager, Double progress) {
        ViewPagerElerningAdapter adapter = new ViewPagerElerningAdapter(getSupportFragmentManager());


        adapter.addFragment(new ViewElerningGrp(), "   " + "Join Group" + "    ");
        adapter.addFragment(new MyGroup(), "  " + "My Group" + "    ");
        viewPager.setAdapter(adapter);
    }

    public void findViews()
    {
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


//    public void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout)
//    {
//        for (int i = 0; i < viewPager.getAdapter().getCount(); i++)
//        {
//            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.layout_txt_tab_detail, null);
//            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
//            tv.setText(viewPager.getAdapter().getPageTitle(i));
//            Typeface font = Typeface.createFromAsset(
//                    activity.getAssets(),
//                    "fonts/PoppinsBold.otf");
//            tv.setTypeface(font);
//            // tv.setTypeface(Typeface.createFromAsset(Typeface.createFromAsset(getAssets(), "fonts/fontawesome_webfont.ttf"));
//            tabLayout.getTabAt(i).setCustomView(tv);
//        }
//    }


}
