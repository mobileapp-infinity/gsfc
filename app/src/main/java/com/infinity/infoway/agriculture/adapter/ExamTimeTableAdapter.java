package com.infinity.infoway.agriculture.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.activity.ExamTimetableDetailActivity;
import com.infinity.infoway.agriculture.model.ExamTTPojo;
import com.infinity.infoway.agriculture.model.NotificationResponse;

import java.util.ArrayList;

public class ExamTimeTableAdapter extends BaseAdapter
{
    ArrayList<ExamTTPojo> a1;
    LayoutInflater inflater;
    Context context;

    class ViewHolder
    {
        LinearLayout institute_ll;

        TextView term_name_tt,tv_exam_name,tv_date_exam,exam_tt_startdate,exam_end_date,weightage_exam_tt,tv_marks_from_exam,tv_view_exam_tt_detail;
    }

    ExamTimeTableAdapter.ViewHolder viewHolder;
    public ExamTimeTableAdapter(Context context,ArrayList<ExamTTPojo> a1)
    {
        this.context=context;
        this.a1 = a1;
    }

    public int getCount()
    {
        return a1.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.exam_tt_list_adapter, null);

            viewHolder = new ExamTimeTableAdapter.ViewHolder();

            viewHolder.term_name_tt=(TextView)convertView.findViewById(R.id.term_name_tt);
            viewHolder.tv_exam_name=(TextView)convertView.findViewById(R.id.tv_exam_name);
            viewHolder.tv_date_exam=(TextView)convertView.findViewById(R.id.tv_date_exam);
            viewHolder.exam_tt_startdate=(TextView)convertView.findViewById(R.id.exam_tt_startdate);
            viewHolder.exam_end_date=(TextView)convertView.findViewById(R.id.exam_end_date);
            viewHolder.weightage_exam_tt=(TextView)convertView.findViewById(R.id.weightage_exam_tt);
            viewHolder.tv_marks_from_exam=(TextView)convertView.findViewById(R.id.tv_marks_from_exam);
            viewHolder.tv_view_exam_tt_detail=(TextView)convertView.findViewById(R.id.tv_view_exam_tt_detail);

           // viewHolder.passing_marks_examtt_detail=(TextView)convertView.findViewById(R.id.passing_marks_examtt_detail);


            convertView.setTag(viewHolder);
        }



        {
            viewHolder = (ExamTimeTableAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.term_name_tt.setText(a1.get(position).getTerm_name()+"");
        viewHolder.tv_exam_name.setText(a1.get(position).getExam_name()+"");
        viewHolder.tv_date_exam.setText("ResultDate : "+a1.get(position).getExam_result_date()+"");
        viewHolder.exam_tt_startdate.setText(a1.get(position).getExam_start_date()+"");
        viewHolder.exam_end_date.setText(a1.get(position).getExam_end_date()+"");
        viewHolder.weightage_exam_tt.setText(a1.get(position).getWeightage()+"");
        viewHolder.tv_marks_from_exam.setText(a1.get(position).getConfig_mark()+"");



        viewHolder.tv_view_exam_tt_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, ExamTimetableDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EXAM_ID",a1.get(position).getExam_id()+"");
                System.out.println("Exxam ID::::::::::::::::::::::::::::::"+a1.get(position).exam_id);
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
