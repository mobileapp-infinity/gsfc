package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.EmployeeFillAttendanceActivity;
import com.infinity.infoway.gsfc.activity.EmployeeFillAttendanceActivity_configuration;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;

import java.util.ArrayList;

/**
 * Created by Pragna on 30-4-2018.
 */

public class BenefitsAdapter extends BaseAdapter {


    private Context ctx;
    StudentsDisplyaFillPojo benefits = new StudentsDisplyaFillPojo();
    ViewHolder holder = null;
    public FragmentManager f_manager;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    String customerId;
    View view;
    static boolean torefesh = false;
    private int selectedPosition = -1;
    public static ArrayList<String> selected = new ArrayList<String>();
    public ArrayList<String> selected_name = new ArrayList<String>();


//    interface   clickonclick()
//    {
//
//    }
//


    public BenefitsAdapter(StudentsDisplyaFillPojo benefits, Context ctx) {
        this.ctx = ctx;
        this.benefits = benefits;
        inflater = LayoutInflater.from(this.ctx);

    }

    @Override
    public int getCount() {
        return benefits.getTable().size();
    }

    @Override
    public Object getItem(int position) {
        return benefits.getTable().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<String> getSelected() {


        return selected;
    }

    public ArrayList<String> getSelectedName() {


        return selected_name;
    }

    public void clearSelected() {
        selected.clear();
    }


    private class ViewHolder {

        //        CheckBox itemCheckBox;
//        CustomTextView txt_status_show;
//        CustomTextView txt_status_counter;
        private TextView txtsrno;
        //        private android.widget.CheckBox cbcheck;
        private Switch cbcheck;
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
            // ppppppppppp     view = inflater.inflate(R.layout.adapter_student_display, null);
            view = inflater.inflate(R.layout.adapter_stud_layout, null);
            holder = new ViewHolder();
            //  holder.itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);


            holder.llmainstudentfetch = (LinearLayout) view.findViewById(R.id.ll_main_student_fetch);
            holder.txtstatus = (TextView) view.findViewById(R.id.txt_status);
            //   holder.txtpreviouslectstatus = (TextView) view.findViewById(R.id.txt_previous_lect_status);
            //  holder.txtrollno = (TextView) view.findViewById(R.id.txt_roll_no);
            holder.txtstudname = (TextView) view.findViewById(R.id.txt_stud_name);
            holder.txtenrollno = (TextView) view.findViewById(R.id.txt_enroll_no);
            //  holder.cbcheck = (CheckBox) view.findViewById(R.id.cb_check);
            holder.cbcheck = (Switch) view.findViewById(R.id.cb_check);
            holder.txtsrno = (TextView) view.findViewById(R.id.txt_sr_no);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.cbcheck.setTag(position); // This line is important.
        //    holder.itemCheckBox.setText(benefits.getBenefits().get(position).getBenefits_name() + "");
        //  String s=benefits.getTable().get(position).get();
        // s=s.replace(","," , ");
        //    holder.itemCheckBox.setText( s+ "");
        //    holder.itemCheckBox.setTypeface(Validations.setTypeface(ctx));


        benefits = benefits;

//        holder.txtsrno.setText("  " + benefits.getTable().get(position).getSr_no() + "");
        holder.txtsrno.setText("  " + benefits.getTable().get(position).getSwd_roll_no() + "");
//        holder.txtpreviouslectstatus.setText("  " + benefits.getTable().get(position).getPre_att_status() + "");
        // holder.txtstatus.setText("  "+studentsDisplyaFillPojo.getTable().get(position).getAtt_status() + "");
        // holder.txtrollno.setText("  " + benefits.getTable().get(position).getSwd_roll_no() + "");
        holder.txtstudname.setText("  " + benefits.getTable().get(position).getStud_Name() + "");
        holder.txtenrollno.setText("  " + benefits.getTable().get(position).getStud_Enrollment_no() + "");

        if (EmployeeFillAttendanceActivity.DISPLAY_FIELD == 1) {
            holder.txtenrollno.setVisibility(View.VISIBLE);
            holder.txtstudname.setVisibility(View.VISIBLE);
            holder.llmainstudentfetch.setWeightSum(4.2f);
        } else if (EmployeeFillAttendanceActivity.DISPLAY_FIELD == 2) {
            holder.txtstudname.setVisibility(View.VISIBLE);
            holder.txtenrollno.setVisibility(View.GONE);
            holder.llmainstudentfetch.setWeightSum(2.7f);

        } else if (EmployeeFillAttendanceActivity.DISPLAY_FIELD == 3) {
            holder.txtenrollno.setVisibility(View.VISIBLE);
            holder.txtstudname.setVisibility(View.GONE);
            holder.llmainstudentfetch.setWeightSum(2.7f);

        } else if (EmployeeFillAttendanceActivity.DISPLAY_FIELD == 4) {
            holder.txtenrollno.setVisibility(View.GONE);
            holder.txtstudname.setVisibility(View.GONE);
            holder.llmainstudentfetch.setWeightSum(1.2f);

        }


        //    holder.txt_status.setTypeface(Validations.setTypeface(ctx));
        if (position == selectedPosition) {
            holder.cbcheck.setChecked(true);
        } else {
            holder.cbcheck.setChecked(false);
        }
        //   holder.cbcheck.setChecked(selected.contains(benefits.getBenefits().get(position).getBenefits_id() + ""));
        holder.cbcheck.setChecked(selected.contains(benefits.getTable().get(position).getStud_id() + ""));


        // holder.itemCheckBox.setOnClickListener(onStateChangedListener(holder.itemCheckBox, position));
        holder.cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {


            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int pos = (Integer) buttonView.getTag();
                if (buttonView.isShown())
                {
                    if (!buttonView.isChecked())
                    {
                        selected.remove(benefits.getTable().get(position).getStud_id() + "");
                        //selected_name.remove( "1");
                    }
                    else if (buttonView.isChecked())
                    {
                        if (!selected.contains(benefits.getTable().get(position).getStud_id() + ""))
                        {
                            selected.add(benefits.getTable().get(position).getStud_id() + "");


                            // selected_name.add(benefits.getBenefits().get(position).getBenefits_name() + "");
                        }
                    }
                }

//                FillAttendanceActivityfaculty.refresh();
                EmployeeFillAttendanceActivity_configuration.refresh();

            }
        });
        return view;
    }


}