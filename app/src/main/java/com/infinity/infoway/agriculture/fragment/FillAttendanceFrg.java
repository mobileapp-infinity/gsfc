package com.infinity.infoway.agriculture.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.FacultyPojo;

/**
 * A simple {@link Fragment} subclass.
 */
public class FillAttendanceFrg extends Fragment
{


    private android.support.v7.widget.RecyclerView recstudentdisplay;
    private android.widget.TextView tv1;
    private android.widget.Spinner spindist;
    private android.widget.EditText edtattendancestudent;
    private android.widget.TextView txttotalstud;
    private android.widget.LinearLayout lltotalstud;
    private android.widget.TextView txtpresentstud;
    private android.widget.LinearLayout llpresentstud;
    private android.widget.TextView txtabsentstud;
    private android.widget.LinearLayout llabsentstud;
    private android.widget.LinearLayout lltotalstudents;
    private android.widget.TextView tv2;
    private android.widget.Spinner spinunit;
    private android.widget.TextView tv3;
    private android.widget.EditText edttopic;
    private android.widget.TextView tv4;
    private android.widget.ListView lvteachingmethod;
    private android.widget.TextView tv9;
    private android.widget.Spinner spinaid;
    private android.widget.TextView tv8;
    private android.widget.Spinner spinFlinnt;


    FacultyPojo.TableBean bean;
    public FillAttendanceFrg()
    {
        // Required empty public constructor
    }



    public  FillAttendanceFrg(FacultyPojo.TableBean bean)
    {
        this.bean=bean;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fill_attendance_frg, container, false);


        initViews(view);
        return view;
    }

    public void initViews(View view)
    {
        this.spinFlinnt = (Spinner)view. findViewById(R.id.spin_Flinnt);
        this.tv8 = (TextView) view. findViewById(R.id.tv8);
        this.spinaid = (Spinner) view. findViewById(R.id.spin_aid);
        this.tv9 = (TextView)view.  findViewById(R.id.tv9);
        this.lvteachingmethod = (ListView) view. findViewById(R.id.lv_teaching_method);
        this.tv4 = (TextView) view. findViewById(R.id.tv4);
        this.edttopic = (EditText) view. findViewById(R.id.edt_topic);
        this.tv3 = (TextView) view. findViewById(R.id.tv3);
        this.spinunit = (Spinner) view. findViewById(R.id.spin_unit);
        this.tv2 = (TextView) view. findViewById(R.id.tv2);
        this.lltotalstudents = (LinearLayout) view. findViewById(R.id.ll_total_students);
        this.llabsentstud = (LinearLayout) view. findViewById(R.id.ll_absent_stud);
        this.txtabsentstud = (TextView) view. findViewById(R.id.txt_absent_stud);
        this.llpresentstud = (LinearLayout)view.  findViewById(R.id.ll_present_stud);
        this.txtpresentstud = (TextView)view.  findViewById(R.id.txt_present_stud);
        this.lltotalstud = (LinearLayout)view.  findViewById(R.id.ll_total_stud);
        this.txttotalstud = (TextView) view. findViewById(R.id.txt_total_stud);
        this.edtattendancestudent = (EditText) view. findViewById(R.id.edt_attendance_student);
        this.spindist = (Spinner) view. findViewById(R.id.spin_dist);
        this.tv1 = (TextView) view. findViewById(R.id.tv1);
        this.recstudentdisplay = (RecyclerView)view.  findViewById(R.id.rec_student_display);




    }

}
