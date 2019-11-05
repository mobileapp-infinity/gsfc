package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.FillAttendanceActivityfaculty;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pragna on 16-4-2019.
 */

public class StudentListAdapter extends BaseAdapter {

    ArrayList<String> check_IDS = new ArrayList<>();
    private Context ctx;
    // List<String> AllStatuslist = new ArrayList<>();
    ViewHolder holder = null;
    public FragmentManager f_manager;
    //private LayoutInflater inflater;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    String customerId;
    View view;
    private int selectedPosition = -1;
    public ArrayList<String> selected = new ArrayList<String>();
    List<String> customBenifitList;
    DataStorage storage;
    RequestQueue queue;
    Activity a;

    StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    Boolean b;

    public static HashMap<String, String> ID_Check_status = new HashMap<>();
//    public StudentListAdapter(List<String> customBenifitList, Context ctx) {
//        this.ctx = ctx;
//        this.customBenifitList=customBenifitList;
//        inflater = LayoutInflater.from(this.ctx);
//        //selected.addAll(customBenifitList);
//    }




    public StudentListAdapter(Context ctx, StudentsDisplyaFillPojo studentsDisplyaFillPojo, Boolean b) {

        this.ctx = ctx;
        this.studentsDisplyaFillPojo = studentsDisplyaFillPojo;
        this.b = b;


        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount() {
        return customBenifitList.size();
    }

    @Override
    public Object getItem(int position) {
        return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<String> getSelected() {


        return selected;
    }

    public void clearSelected() {
        selected.clear();
    }


    private class ViewHolder {

        CheckBox itemCheckBox;
       // CustomTextView txt_status_show;
    //    CustomTextView txt_status_counter;
      // final View mView;
        StudentsDisplyaFillPojo studentsDisplyaFillPojo;


        private TextView txtsrno;
        private CheckBox cbcheck;
        private TextView txtenrollno;
        private TextView txtstudname;
        private TextView txtrollno;
        private TextView txtpreviouslectstatus;
        private TextView txtstatus;
        private LinearLayout llmainstudentfetch;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_stud_layout, null);
            holder = new ViewHolder();
        //    holder.itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);


            holder.llmainstudentfetch = (LinearLayout) view.findViewById(R.id.ll_main_student_fetch);
         //   holder.txtstatus = (TextView) view.findViewById(R.id.txt_status);
         //   holder.txtpreviouslectstatus = (TextView) view.findViewById(R.id.txt_previous_lect_status);
          //  holder.txtrollno = (TextView) view.findViewById(R.id.txt_roll_no);
            holder.txtstudname = (TextView) view.findViewById(R.id.txt_stud_name);
            holder.txtenrollno = (TextView) view.findViewById(R.id.txt_enroll_no);
            holder.cbcheck = (CheckBox) view.findViewById(R.id.cb_check);
            holder.txtsrno = (TextView) view.findViewById(R.id.txt_sr_no);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemCheckBox.setTag(position); // This line is important.
//        holder.itemCheckBox.setText(customBenifitList.get(position)+ "");
//        holder.itemCheckBox.setTypeface(Validations.setTypeface(ctx));
//        //    holder.txt_status.setTypeface(Validations.setTypeface(ctx));
//        if (position == selectedPosition) {
//            holder.itemCheckBox.setChecked(true);
//        } else {
//            holder.itemCheckBox.setChecked(false);
//        }
//        holder.itemCheckBox.setChecked(selected.contains(customBenifitList + ""));
//        // holder.itemCheckBox.setOnClickListener(onStateChangedListener(holder.itemCheckBox, position));
//        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int pos = (Integer) buttonView.getTag();
//                if(buttonView.isShown()) {
//                    if (!buttonView.isChecked()) {
//                        selected.remove(customBenifitList.get(position) + "");
//                    } else if (buttonView.isChecked()) {
//                        if (!selected.contains(customBenifitList.get(position) + "")) {
//                            selected.add(customBenifitList.get(position) + "");
//                        }
//                    }
//                }
//            }
//        });


        this.b = FillAttendanceActivityfaculty.b;
        ;
        holder.studentsDisplyaFillPojo = studentsDisplyaFillPojo;

//        holder.txtsrno.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getSr_no() + "");
        holder.txtsrno.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getSwd_roll_no() + "");
        holder.txtpreviouslectstatus.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getPre_att_status() + "");
        // holder.txtstatus.setText("  "+studentsDisplyaFillPojo.getTable().get(position).getAtt_status() + "");
        holder.txtrollno.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getSwd_roll_no() + "");
        holder.txtstudname.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getStud_Name() + "");
        holder.txtenrollno.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getStud_Enrollment_no() + "");
        System.out.println("value of b In onCheckedChanged onBindViewHolder::::::::"+b);

        if (b == true) {
            holder.cbcheck.setChecked(true);

            holder.txtstatus.setText("  P  ");
            holder.txtstatus.setBackgroundColor(ctx.getResources().getColor(R.color.green));

            check_IDS.add(studentsDisplyaFillPojo.getTable().get(position).getStud_id() + "");
        } else {
            holder.cbcheck.setChecked(false);
            holder.txtstatus.setText("  A  ");
            holder.txtstatus.setBackgroundColor(ctx.getResources().getColor(R.color.red));
        }
        return view;
    }


}