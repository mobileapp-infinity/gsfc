package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
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

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.GroupFragment;
import com.infinity.infoway.gsfc.model.NewsData;
import com.infinity.infoway.gsfc.model.group_news_detail;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsGroupWise extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar actionBar;
    DataStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_group_wise);

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
        storage = new DataStorage("Login_Detail", this);
        viewPager = (ViewPager) findViewById(R.id.viewpager_news);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs_news);
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

    private void setupViewPager(final ViewPager viewPager)
    {
        final ProgressDialog progressDialog = new ProgressDialog(NewsGroupWise.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
       // progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NewsData>> call = apiService.get_group(String.valueOf(storage.read("intitute_id",3)));
        call.enqueue(new Callback<ArrayList<NewsData>>()
        {
            @Override
            public void onResponse(Call<ArrayList<NewsData>> call, Response<ArrayList<NewsData>> response)
            {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager());
                    Log.d("atlist", String.valueOf(response.body().size()));
                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i <response.body().size(); i++)
                        {
                            String day = response.body().get(i).getGroup_name();
                            Log.d("dat_name",day);
                            ArrayList<group_news_detail>news_detail = new ArrayList<group_news_detail>();
                            news_detail = response.body().get(i).getGroup_news_details();
                            Log.d("lect_d", String.valueOf(news_detail));
                            if (news_detail.isEmpty())
                            {
                                Toast.makeText(NewsGroupWise.this, "No Records Found", Toast.LENGTH_SHORT).show();
                            }



                            Bundle bundl = new Bundle();
                            bundl.putSerializable("elist", news_detail);
                            GroupFragment dayfragment = new GroupFragment();
                            dayfragment.setArguments(bundl);
                            adapter.addFragment(dayfragment,day);


                        }

                        viewPager.setAdapter(adapter);
                    }
                    else
                    {
                        Toast.makeText(NewsGroupWise.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewsData>> call, Throwable t)
            {
                progressDialog.dismiss();
                // Log error here since request failed
                //  Log.e("emplotteattendancerespo", t.toString());
            }
        });
    }


    public class ViewAdapter  extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewAdapter(FragmentManager manager)
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
