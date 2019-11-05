package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.ExamDetailPOJO;

import java.util.ArrayList;

public class ExamTimeTableDetailAdapter extends BaseAdapter
{
    ArrayList<ExamDetailPOJO> a1;
    LayoutInflater inflater;
    Context context;

    class ViewHolder
    {
        LinearLayout institute_ll;
      TextView sub_name_exam_detail,tv_date_exam_detail,exam_tt_total_marks_detail,passing_marks_examtt_detail,st_time_exam_detail,end_time_exam_detail;

    }

    ViewHolder viewHolder;
    public ExamTimeTableDetailAdapter(Context context,ArrayList<ExamDetailPOJO> a1)
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.sub_lv_exam_tt_adapter, null);

            viewHolder = new ViewHolder();

            viewHolder.sub_name_exam_detail=(TextView)convertView.findViewById(R.id.sub_name_exam_detail);
            viewHolder.tv_date_exam_detail=(TextView)convertView.findViewById(R.id.tv_date_exam_detail);
            viewHolder.exam_tt_total_marks_detail=(TextView)convertView.findViewById(R.id.exam_tt_total_marks_detail);
            viewHolder.passing_marks_examtt_detail=(TextView)convertView.findViewById(R.id.passing_marks_examtt_detail);
            viewHolder.st_time_exam_detail=(TextView)convertView.findViewById(R.id.st_time_exam_detail);
            viewHolder.end_time_exam_detail=(TextView)convertView.findViewById(R.id.end_time_exam_detail);



            convertView.setTag(viewHolder);
        }



        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sub_name_exam_detail.setText(a1.get(position).getSubject_name()+"");
        viewHolder.tv_date_exam_detail.setText(a1.get(position).getExam_date()+"");

        viewHolder.exam_tt_total_marks_detail.setText(a1.get(position).getMet_total_marks()+"");
        viewHolder.passing_marks_examtt_detail.setText(a1.get(position).getMet_pass_marks()+"");
        viewHolder.st_time_exam_detail.setText(a1.get(position).getExam_start_time()+"");
        //    viewHolder.weightage_exam_tt.setText(a1.get(position).getWeightage()+"");
        viewHolder.end_time_exam_detail.setText(a1.get(position).getExam_to_time()+"");
       // viewHolder.term_name_tt.setText(a1.get(position).getTerm_name()+"");



        return convertView;
    }
}
