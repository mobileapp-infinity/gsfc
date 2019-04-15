package com.infinity.infoway.agriculture.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;

public class testi extends AppCompatActivity {
    private android.widget.ImageView imgprofile;
    private android.widget.ImageView ivatt;
    private android.widget.TextView textfee;
    private android.widget.ImageView ivplacement;
    private android.widget.TextView textattendence;
    private android.widget.LinearLayout layout1;
    private android.widget.ImageView ivlessionplan;
    private android.widget.ImageView imgtimetable;
    private android.widget.TextView textView6;
    private android.widget.ImageView ivassignment;
    private android.widget.ImageView imgnewannouncement;
    private android.widget.TextView txterning;
    private android.widget.ImageView imgplacement;
    private android.widget.TextView txtplacement;
    private android.widget.ImageView imgleaveapp;
    private android.widget.LinearLayout newiconll;
    private android.widget.LinearLayout llviewmore;
    private android.widget.TextView txtreceipt;
    private android.widget.ImageView imgattendance;
    private android.widget.TextView txt1;
    private android.widget.ImageView imgresult;
    private android.widget.ImageView assignmentimg;
    private android.widget.LinearLayout feeslayout;
    private android.widget.ImageView imgnews;
    private android.widget.TextView txtnews;
    private android.widget.TextView txtfeedback;
    private android.widget.ImageView imgdashboard;
    private android.widget.ImageView imgfeedback;
    private android.widget.LinearLayout studentlayout;
    private android.widget.ImageView pendingfeesimg;
    private android.widget.ImageView ivmidexamtt;
    private android.widget.ImageView imgfeecircular;
    private android.widget.LinearLayout fivell;
    private android.widget.LinearLayout llviewshide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_panel_layout);
        this.llviewshide = (LinearLayout) findViewById(R.id.ll_views_hide);
        this.fivell = (LinearLayout) findViewById(R.id.five_ll);
        this.imgfeecircular = (ImageView) findViewById(R.id.img_feecircular);
        this.ivmidexamtt = (ImageView) findViewById(R.id.iv_mid_exam_tt);
        this.pendingfeesimg = (ImageView) findViewById(R.id.pending_fees_img);
        this.studentlayout = (LinearLayout) findViewById(R.id.studentlayout);
        this.imgfeedback = (ImageView) findViewById(R.id.imgfeedback);
        this.imgdashboard = (ImageView) findViewById(R.id.img_dashboard);
        this.txtfeedback = (TextView) findViewById(R.id.txtfeedback);
        this.txtnews = (TextView) findViewById(R.id.txtnews);
        this.imgnews = (ImageView) findViewById(R.id.imgnews);
        this.feeslayout = (LinearLayout) findViewById(R.id.fees_layout);
        this.assignmentimg = (ImageView) findViewById(R.id.assignment_img);
        this.imgresult = (ImageView) findViewById(R.id.img_result);
        this.txt1 = (TextView) findViewById(R.id.txt_1);
        this.imgattendance = (ImageView) findViewById(R.id.imgattendance);
        this.txtreceipt = (TextView) findViewById(R.id.txt_receipt);
        this.llviewmore = (LinearLayout) findViewById(R.id.ll_view_more);
        this.newiconll = (LinearLayout) findViewById(R.id.new_icon_ll);
        this.imgleaveapp = (ImageView) findViewById(R.id.img_leave_app);
        this.txtplacement = (TextView) findViewById(R.id.txt_placement);
        this.imgplacement = (ImageView) findViewById(R.id.img_placement);
        this.txterning = (TextView) findViewById(R.id.txt_erning);
        this.imgnewannouncement = (ImageView) findViewById(R.id.img_new_announcement);
        this.ivassignment = (ImageView) findViewById(R.id.iv_assignment);
        this.textView6 = (TextView) findViewById(R.id.textView6);
        this.imgtimetable = (ImageView) findViewById(R.id.imgtimetable);
        this.ivlessionplan = (ImageView) findViewById(R.id.iv_lession_plan);
        this.layout1 = (LinearLayout) findViewById(R.id.layout1);
        this.textattendence = (TextView) findViewById(R.id.textattendence);
        this.ivplacement = (ImageView) findViewById(R.id.iv_placement);
        this.textfee = (TextView) findViewById(R.id.textfee);
        this.ivatt = (ImageView) findViewById(R.id.iv_att);
        this.imgprofile = (ImageView) findViewById(R.id.imgprofile);
    }
}
