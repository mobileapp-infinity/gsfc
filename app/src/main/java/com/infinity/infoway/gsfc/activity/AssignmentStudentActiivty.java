package com.infinity.infoway.gsfc.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomButton;
import com.infinity.infoway.gsfc.CommonCls.CustomEditText;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.R;

public class AssignmentStudentActiivty extends AppCompatActivity
{

    private CustomTextView title;
    private Toolbar toolbar;
    private CustomBoldTextView tvgpname;
    private Spinner spinassigncourse;
    private CustomBoldTextView tvasgnname;
    private CustomEditText edtassignname;
    private CustomBoldTextView tvgrptype;
    private CustomButton btnuploadfileassign;
    private CustomBoldTextView tvdesc;
    private CustomEditText edtdescelerning;
    private CustomBoldTextView tvdate;
    private CustomEditText edtassigndat;
    private LinearLayout llelerning;
    private CustomBoldTextView tvsave;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_student_actiivty);
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

        INITVIEWS();
    }

    private void INITVIEWS()
    {
        this.tvsave = (CustomBoldTextView) findViewById(R.id.tv_save);
        this.llelerning = (LinearLayout) findViewById(R.id.ll_elerning);
        this.edtassigndat = (CustomEditText) findViewById(R.id.edt_assign_dat);
        this.tvdate = (CustomBoldTextView) findViewById(R.id.tv_date);
        this.edtdescelerning = (CustomEditText) findViewById(R.id.edt_desc_e_lerning);
        this.tvdesc = (CustomBoldTextView) findViewById(R.id.tv_desc);
        this.btnuploadfileassign = (CustomButton) findViewById(R.id.btn_upload_file_assign);
        this.tvgrptype = (CustomBoldTextView) findViewById(R.id.tv_grp_type);
        this.edtassignname = (CustomEditText) findViewById(R.id.edt_assign_name);
        this.tvasgnname = (CustomBoldTextView) findViewById(R.id.tv_asgn_name);
        this.spinassigncourse = (Spinner) findViewById(R.id.spin_assign_course);
        this.tvgpname = (CustomBoldTextView) findViewById(R.id.tv_gp_name);

        this.title = (CustomTextView) findViewById(R.id.title);
    }
}
