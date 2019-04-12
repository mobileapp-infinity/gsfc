package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.fragment.DaysFragment;
import com.infinity.infoway.agriculture.model.Lecturedetail;
import com.infinity.infoway.agriculture.model.TimeTableResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Timetable_employee extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar actionBar;
    Context ctx;
    DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_employee);


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

        findviews();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        View root = tabLayout.getChildAt(0);

        if (root instanceof LinearLayout)
        {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.grey));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }

    public void findviews() {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
    }


    private void setupViewPager(final ViewPager viewPager)
    {
        final ProgressDialog progressDialog = new ProgressDialog(Timetable_employee.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<TimeTableResponse>> call = apiService.getEmployeeTimetable(String.valueOf(storage.read("emp_id", 3)), String.valueOf(storage.read("emp_year_id", 3)));

        call.enqueue(new Callback<ArrayList<TimeTableResponse>>()
        {
            @Override
            public void onResponse(Call<ArrayList<TimeTableResponse>> call, Response<ArrayList<TimeTableResponse>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    Timetable_employee.ViewPagerAdapter adapter = new Timetable_employee.ViewPagerAdapter(getSupportFragmentManager());
                    Log.d("attlist", String.valueOf(response.body().size()));

                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {
                            // Log.d("attlist", String.valueOf(response.body().size()));
                            String day = response.body().get(i).getday_name().substring(0, Math.min(response.body().get(i).getday_name().length(), 3));
                            Log.d("day_name", day);
//                                ArrayList<Lecturedetail> lecturedetail = response.body().get(i).getLecturedetail();
//                                ArrayList<Lecturedetail> lecturedetail_emp = new ArrayList<Lecturedetail>();
//                                lecturedetail_emp = response.body().get(i).getLecturedetail();
//                                Log.d("lect", String.valueOf(lecturedetail_emp));


                            ArrayList<Lecturedetail> final_list = new ArrayList<>();
                            final_list = response.body().get(i).getLecturedetail();

                            Log.d("final_list", String.valueOf(final_list));


                            Bundle bundl = new Bundle();
                            bundl.putSerializable("elist", final_list);
                            DaysFragment dayfragment = new DaysFragment();
                            dayfragment.setArguments(bundl);
                            adapter.addFragment(dayfragment, day);
                            //Toast.makeText(Timetable.this,"tt",Toast.LENGTH_LONG).show();
                        }

                        viewPager.setAdapter(adapter);
                    }
                    else
                        {
                        Toast.makeText(Timetable_employee.this, "No records found_1", Toast.LENGTH_LONG).show();

                    }

                }
                else
                    {
                    Toast.makeText(Timetable_employee.this, "Plese Try Again Later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TimeTableResponse>> call, Throwable t) {
                progressDialog.dismiss();
                // Log error here since request failed
                // Log.e("emplotteattendancerespo", t.toString());
            }
        });

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {

            return mFragmentTitleList.get(position);

        }

    }
}




