package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.EmployeeFillAttendanceActivity;
import com.infinity.infoway.gsfc.activity.EmployeeFillAttendanceActivity_configuration;
import com.infinity.infoway.gsfc.model.FacultyPojo;

public class FacultyPendingAttendanceAdapter extends BaseAdapter {
    Context ctx;
    FacultyPojo facultyPojo;


    class ViewHolder {

        ImageView iv_authority;
        LinearLayout ll_authority_adapter;
        private TextView txtdate;
        private TextView txtsem;
        private TextView txtabx;
        private TextView txtsub;
        private TextView txtlect;
        private TextView txtclass;
        private TextView txt_fill_attendance;
        private LinearLayout lladapterfacultyattendance;
    }

    ViewHolder viewHolder;

    public FacultyPendingAttendanceAdapter(Context ctx, FacultyPojo facultyPojo) {
        this.ctx = ctx;
        this.facultyPojo = facultyPojo;
    }

    @Override
    public int getCount() {
//        return enrollPojo.getEnroll().size();
        return facultyPojo.getTable().size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View itemView, ViewGroup viewGroup) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        if (itemView == null) {
//            itemView = mInflater.inflate(R.layout.enroll_activity_adapter, null);
            itemView = mInflater.inflate(R.layout.adapter_fsculty_attendance, null);

            viewHolder = new ViewHolder();


            viewHolder.lladapterfacultyattendance = (LinearLayout) itemView.findViewById(R.id.ll_adapter_faculty_attendance);
            viewHolder.txtclass = (TextView) itemView.findViewById(R.id.txt_class);
            viewHolder.txtlect = (TextView) itemView.findViewById(R.id.txt_lect);
            viewHolder.txtsub = (TextView) itemView.findViewById(R.id.txt_sub);
            viewHolder.txtabx = (TextView) itemView.findViewById(R.id.txt_a_bx);
            viewHolder.txtsem = (TextView) itemView.findViewById(R.id.txt_sem);
            viewHolder.txtdate = (TextView) itemView.findViewById(R.id.txt_date);
            viewHolder.txt_fill_attendance = (TextView) itemView.findViewById(R.id.txt_fill_attendance);


            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }


        viewHolder.txtdate.setText(facultyPojo.getTable().get(i).getDl_date() + "");
        viewHolder.txtsem.setText(facultyPojo.getTable().get(i).getSemester_name() + "");
        viewHolder.txtabx.setText(facultyPojo.getTable().get(i).getBatch_name() + "");
        viewHolder.txtsub.setText(facultyPojo.getTable().get(i).getSub_name() + "");
        viewHolder.txtlect.setText(facultyPojo.getTable().get(i).getLecture_name() + "");
        viewHolder.txtclass.setText(facultyPojo.getTable().get(i).getResourse_name() + "");

        viewHolder.txt_fill_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ppppppppppppppppppppppppppppppp   Intent intent = new Intent(ctx, FillAttendanceActivityfaculty.class);
//                Intent intent = new Intent(ctx, EmployeeFillAttendanceActivity.class);
                Intent intent = new Intent(ctx, EmployeeFillAttendanceActivity_configuration.class);
                intent.putExtra("data", facultyPojo.getTable().get(i));
                ctx.startActivity(intent);
            }
        });
        return itemView;
    }
}
