package com.infinity.infoway.agriculture.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.activity.FillAttendanceActivityfaculty;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.fragment.HomeWorkFragment;
import com.infinity.infoway.agriculture.model.StudentsDisplyaFillPojo;
import com.infinity.infoway.agriculture.model.homework_array;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentDisplayfillAttendanceAdapter extends RecyclerView.Adapter<StudentDisplayfillAttendanceAdapter.ViewHolder> {

    Activity a;
    Context ctx;
    DataStorage storage;
    StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    Boolean b;
    public static HashMap<String, String> ID_Check_status = new HashMap<>();

    ArrayList<String> check_IDS = new ArrayList<>();

    public StudentDisplayfillAttendanceAdapter(Context ctx, StudentsDisplyaFillPojo studentsDisplyaFillPojo, Boolean b) {

        this.ctx = ctx;
        this.studentsDisplyaFillPojo = studentsDisplyaFillPojo;
        this.b = b;


        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public StudentDisplayfillAttendanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_display, parent, false);
        return new StudentDisplayfillAttendanceAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StudentDisplayfillAttendanceAdapter.ViewHolder holder, final int position) {
        this.b = FillAttendanceActivityfaculty.b;
        ;
        holder.studentsDisplyaFillPojo = studentsDisplyaFillPojo;

        holder.txtsrno.setText("  " + studentsDisplyaFillPojo.getTable().get(position).getSr_no() + "");
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
        System.out.println("check_IDS after add and not notify:::::::::::::::" + check_IDS.toString());


        holder.cbcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.txtsrno.getTag();

               // Toast.makeText(ctx, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                FillAttendanceActivityfaculty.cb_check_fill_attendance.setChecked(false);


                if (checked)
                {
                    ID_Check_status.put(studentsDisplyaFillPojo.getTable().get(position).getStud_id(), "1");
                    check_IDS.add(studentsDisplyaFillPojo.getTable().get(position).getStud_id());
                }
                else
                    {
                    ID_Check_status.put(studentsDisplyaFillPojo.getTable().get(position).getStud_id(), "0");
                    check_IDS.remove(studentsDisplyaFillPojo.getTable().get(position).getStud_id());
                    System.out.println("check_IDS after remove:::::::::::::::" + check_IDS.toString());
                }


            }
        });

//        if (check_IDS.contains(studentsDisplyaFillPojo.getTable().get(position).getStud_id())) {
//            holder.cbcheck.setChecked(true);
//            System.out.println("check_IDS after check:::::::::::::::" + check_IDS.toString());
//        } else {
//            //check_IDS.remove(studentsDisplyaFillPojo.getTable().get(position).getStud_id());
//            holder.cbcheck.setChecked(false);
//        }

        System.out.println("check_IDS after notify:::::::::::::::" + check_IDS.toString());

    }






    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




    @Override
    public int getItemCount() {
        return studentsDisplyaFillPojo.getTable().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        StudentsDisplyaFillPojo studentsDisplyaFillPojo;


        private android.widget.TextView txtsrno;
        private android.widget.CheckBox cbcheck;
        private android.widget.TextView txtenrollno;
        private android.widget.TextView txtstudname;
        private android.widget.TextView txtrollno;
        private android.widget.TextView txtpreviouslectstatus;
        private android.widget.TextView txtstatus;
        private android.widget.LinearLayout llmainstudentfetch;

        ViewHolder(View view) {
            super(view);
            mView = view;


            this.llmainstudentfetch = (LinearLayout) view.findViewById(R.id.ll_main_student_fetch);
            this.txtstatus = (TextView) view.findViewById(R.id.txt_status);
            this.txtpreviouslectstatus = (TextView) view.findViewById(R.id.txt_previous_lect_status);
            this.txtrollno = (TextView) view.findViewById(R.id.txt_roll_no);
            this.txtstudname = (TextView) view.findViewById(R.id.txt_stud_name);
            this.txtenrollno = (TextView) view.findViewById(R.id.txt_enroll_no);
            this.cbcheck = (CheckBox) view.findViewById(R.id.cb_check);
            this.txtsrno = (TextView) view.findViewById(R.id.txt_sr_no);


        }


    }
}












