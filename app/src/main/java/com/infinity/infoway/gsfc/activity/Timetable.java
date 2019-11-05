package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.infinity.infoway.gsfc.fragment.DaysFragment;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.Lecturedetail;
import com.infinity.infoway.gsfc.model.TimeTableResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Timetable extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar actionBar;
    Context ctx;
    DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__timetable);
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

    public void findviews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
    }

    private void setupViewPager(final ViewPager viewPager)
    {
        if (storage.CheckLogin("stud_id", Timetable.this))
        {
            final ProgressDialog progressDialog = new ProgressDialog(Timetable.this, R.style.MyTheme1);
            progressDialog.setCancelable(true);
           // progressDialog.setMessage("Please Wait");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            //Log.d("stud_id",String.valueOf(storage.read("stud_id", 3)));
            mParams.put("course_id", String.valueOf(storage.read("course_id", 3)));
            // Log.d("course_id",String.valueOf(storage.read("course_id", 3)));
            mParams.put("div_id", String.valueOf(storage.read("swd_division_id", 3)));
            // Log.d("div_id",String.valueOf(storage.read("swd_division_id", 3)));
            mParams.put("shift_id", String.valueOf(storage.read("shift_id", 3)));
            // Log.d("shift_id",String.valueOf(storage.read("shift_id", 3)));
            // mParams.put("div_id", "1");
            mParams.put("batch_id", String.valueOf(storage.read("swd_batch_id", 3)));
            // Log.d("batch_id",String.valueOf(storage.read("swd_batch_id", 3)));
            //  mParams.put("batch_id", "1");
            mParams.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));
            // Log.d("year_id",String.valueOf(storage.read("swd_year_id", 3)));
            Call<ArrayList<TimeTableResponse>> call = apiService.getStudentTimetable(mParams);

            call.enqueue(new Callback<ArrayList<TimeTableResponse>>()
            {
                @Override
                public void onResponse(Call<ArrayList<TimeTableResponse>> call, Response<ArrayList<TimeTableResponse>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful())
                    {
                        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        Log.d("atlist", String.valueOf(response.body().size()));
                        if (response.body().size() >= 1)
                        {
                            for (int i = 0; i < response.body().size(); i++)
                            {
                                String day = response.body().get(i).getday_name().substring(0, Math.min(response.body().get(i).getday_name().length(), 3));
                                ArrayList<Lecturedetail> lecturedetail = new ArrayList<Lecturedetail>();
                                lecturedetail = response.body().get(i).getLecturedetail();
                                Log.d("lect_d", String.valueOf(lecturedetail.size()));
                                //Toast.makeText(Timetable.this," timetable",Toast.LENGTH_LONG).show();
                              /*  if(lecturedetail.size()>=1)
                                {
                                    if (storage.CheckLogin("stud_id", ctx) && (lecturedetail.get(i).getlect_name().equals("") || lecturedetail.get(i).getlect_name() == null))
                                    {
                                        if (lecturedetail.get(i).getLab_array().size() > 1)
                                        {

                                            Log.d("labdetail", lecturedetail.get(i).getLab_array().get(0).getlect_name());
                                        }
                                    }

                                }*/


                                Bundle bundl = new Bundle();
                                bundl.putSerializable("elist", lecturedetail);
                                DaysFragment dayfragment = new DaysFragment();
                                dayfragment.setArguments(bundl);
                                adapter.addFragment(dayfragment, day);


                            }

                            viewPager.setAdapter(adapter);
                        }
                        else
                            {
                            Toast.makeText(Timetable.this, "No Records Found", Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                        {
                        if (String.valueOf(storage.read("swd_division_id", 3)).equals("0") || String.valueOf(storage.read("swd_batch_id", 3)).equals("0"))
                        {
                            Toast.makeText(Timetable.this, "You have not allocated Batch/Division", Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                            Toast.makeText(Timetable.this, "No Records Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<TimeTableResponse>> call, Throwable t)
                {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    //  Log.e("emplotteattendancerespo", t.toString());
                }
            });
        }
        else
            {
            final ProgressDialog progressDialog = new ProgressDialog(Timetable.this, R.style.MyTheme1);
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ArrayList<TimeTableResponse>> call = apiService.getEmployeeTimetable(String.valueOf(storage.read("emp_id",3)),String.valueOf(storage.read("emp_year_id",3)));

            call.enqueue(new Callback<ArrayList<TimeTableResponse>>()
            {
                @Override
                public void onResponse(Call<ArrayList<TimeTableResponse>> call, Response<ArrayList<TimeTableResponse>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful())
                    {
                        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        Log.d("attlist", String.valueOf(response.body().size()));

                        if (response.body().size() >= 1)
                        {
                            for (int i = 0; i < response.body().size(); i++)
                            {
                                // Log.d("attlist", String.valueOf(response.body().size()));
                                String day = response.body().get(i).getday_name().substring(0, Math.min(response.body().get(i).getday_name().length(), 3));
                                Log.d("day_name",day);
//                                ArrayList<Lecturedetail> lecturedetail = response.body().get(i).getLecturedetail();
                                ArrayList<Lecturedetail> lecturedetail_emp = new ArrayList<Lecturedetail>();
                                lecturedetail_emp = response.body().get(i).getLecturedetail();
                                Log.d("lect", String.valueOf(lecturedetail_emp));


                                Bundle bundl = new Bundle();
                                bundl.putSerializable("elist",lecturedetail_emp);
                                DaysFragment dayfragment = new DaysFragment();
                                dayfragment.setArguments(bundl);
                                adapter.addFragment(dayfragment, day);
                                //Toast.makeText(Timetable.this,"tt",Toast.LENGTH_LONG).show();
                            }

                            viewPager.setAdapter(adapter);
                        }
                        else
                            {
                            Toast.makeText(Timetable.this, "No records found", Toast.LENGTH_LONG).show();

                        }

                    }
                    else
                        {
                        Toast.makeText(Timetable.this, "Plese Try Again Later", Toast.LENGTH_LONG).show();
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
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);

        }

    }


}
