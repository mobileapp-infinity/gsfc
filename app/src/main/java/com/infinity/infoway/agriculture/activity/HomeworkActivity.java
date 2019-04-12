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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.fragment.DaysFragment;
import com.infinity.infoway.agriculture.fragment.HomeWorkFragment;
import com.infinity.infoway.agriculture.model.Lecturedetail;
import com.infinity.infoway.agriculture.model.Response_Activity;
import com.infinity.infoway.agriculture.model.TimeTableResponse;
import com.infinity.infoway.agriculture.model.homework_array;
import com.infinity.infoway.agriculture.model.homework_response;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeworkActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_homework);

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

        findViews();

        viewPager = (ViewPager) findViewById(R.id.homework_view_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs_homework);
        tabLayout.setupWithViewPager(viewPager);
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout)
        {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new  GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.grey));
            drawable.setSize(2,1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }


    }

    public void findViews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
    }


    private void setupViewPager(final ViewPager viewPager)
    {

        final ProgressDialog progressDialog = new ProgressDialog(HomeworkActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> mParams;
        mParams = new HashMap<String, String>();
        mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
        mParams.put("div_id", String.valueOf(storage.read("swd_division_id", 3)));
        mParams.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));
        Call<ArrayList<homework_response>> call = apiService.get_homework_content_delivered(mParams);

        call.enqueue(new Callback<ArrayList<homework_response>>()
        {
            @Override
            public void onResponse(Call<ArrayList<homework_response>> call, Response<ArrayList<homework_response>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    HomeworkActivity.ViewPagerAdapter adapter = new HomeworkActivity.ViewPagerAdapter(getSupportFragmentManager());
                    Log.d("atlist", String.valueOf(response.body().size()));
                    if (response.body()!= null && response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {

                            String day = response.body().get(i).getDay_name();
                            String date = response.body().get(i).getDate();
                            ArrayList<homework_array> lecturedetail = new ArrayList<homework_array>();
                            lecturedetail = response.body().get(i).getHomework_array();
                            Log.d("lectutre_d", String.valueOf(lecturedetail.size()));


                            Bundle bundl = new Bundle();
                            bundl.putSerializable("elist", lecturedetail);
                            HomeWorkFragment homeWorkFragment = new HomeWorkFragment();
                            homeWorkFragment.setArguments(bundl);
                            adapter.addFragment(homeWorkFragment,day,date);


                        }

                        viewPager.setAdapter(adapter);
                    }
                    else
                        {
                        Toast.makeText(HomeworkActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }

                }
                else
                    {
                    if (String.valueOf(storage.read("swd_division_id", 3)).equals("0") || String.valueOf(storage.read("swd_batch_id", 3)).equals("0"))
                    {
                        Toast.makeText(HomeworkActivity.this, "You have not allocated Batch/Division", Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                        Toast.makeText(HomeworkActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<homework_response>> call, Throwable t)
            {
                progressDialog.dismiss();
                // Log error here since request failed
                //  Log.e("emplotteattendancerespo", t.toString());
            }
        });

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<String> mFragmentTitleListdate = new ArrayList<>();


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

        public void addFragment(Fragment fragment, String title,String date) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentTitleListdate.add(date);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {

            return mFragmentTitleList.get(position) + mFragmentTitleListdate.get(position);

        }

    }


}
