package com.infinity.infoway.gsfc.HrAppActivities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.MySharedPrefereces;
import com.infinity.infoway.gsfc.R;
import com.makeramen.roundedimageview.RoundedImageView;


public class ProfileActivity extends AppCompatActivity
{

    RoundedImageView profileimg;
    ImageView editpic;
    CustomBoldTextView txtusername;
    LinearLayout widget393;
    LinearLayout widget39;
    CustomTextView edtempcode;
    CustomTextView edtempname;
    ImageView ivreporting;
    CustomTextView edtreportingto;
    CustomTextView edtbranch;
    CustomTextView edtdept;
    CustomTextView edtdesignation;
    CustomBoldTextView txtprofilesubmit;
    LinearLayout widget37;
    RelativeLayout widget30;
    CustomBoldTextView txt_act;
    ImageView iv_back;
    CustomBoldTextView tv_emp_code,tv_version,tv_version_code;

    MySharedPrefereces mySharedPrefereces;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_u);
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
        txt_act.setText("Profile");
        initView();
    }

    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        profileimg = (RoundedImageView) findViewById(R.id.profile_img);
        editpic = (ImageView) findViewById(R.id.edit_pic);
        txtusername = (CustomBoldTextView) findViewById(R.id.txt_username);
        widget393 = (LinearLayout) findViewById(R.id.widget393);
        widget39 = (LinearLayout) findViewById(R.id.widget39);
        edtempcode = (CustomTextView) findViewById(R.id.edt_emp_code);
        edtempcode.setText(mySharedPrefereces.getEmpCode()+"");
        edtempname = (CustomTextView) findViewById(R.id.edt_emp_name);
        edtempname.setText(mySharedPrefereces.getFullName()+"");
        ivreporting = (ImageView) findViewById(R.id.iv_reporting);
        edtreportingto = (CustomTextView) findViewById(R.id.edt_reporting_to);
        edtreportingto.setText(mySharedPrefereces.getREPORTINGTO()+"");
        edtbranch = (CustomTextView) findViewById(R.id.edt_branch);
        edtbranch.setText(mySharedPrefereces.getBranch()+"");
        edtdept = (CustomTextView) findViewById(R.id.edt_dept);
        edtdept.setText(mySharedPrefereces.getDepartment()+"");
        edtdesignation = (CustomTextView) findViewById(R.id.edt_designation);
        edtdesignation.setText(mySharedPrefereces.getDesignation()+"");
        txtprofilesubmit = (CustomBoldTextView) findViewById(R.id.txt_profile_submit);
        widget37 = (LinearLayout) findViewById(R.id.widget37);
        widget30 = (RelativeLayout) findViewById(R.id.widget30);


        Glide.with(ProfileActivity.this).load(mySharedPrefereces.getUserPhoto()+"").error(R.drawable.noimage).into(profileimg);
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
