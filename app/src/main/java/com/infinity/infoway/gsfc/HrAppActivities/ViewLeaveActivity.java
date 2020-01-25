package com.infinity.infoway.gsfc.HrAppActivities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.MySharedPrefereces;
import com.infinity.infoway.gsfc.R;


public class ViewLeaveActivity extends AppCompatActivity
{


    ListView lv_view_all;
    CardView cardroot;
    ListView lvviewall;
    CustomBoldTextView txt_act;
    ImageView iv_back;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("View Leaves");
        initView();

    }

    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        cardroot = (CardView) findViewById(R.id.card_root);
        lvviewall = (ListView) findViewById(R.id.lv_view_all);

        PackageInfo pInfo = null;
        assert pInfo != null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
    }
}
