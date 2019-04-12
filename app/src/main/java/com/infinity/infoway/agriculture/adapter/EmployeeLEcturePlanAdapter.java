package com.infinity.infoway.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.LecturePlanEmployee;
import com.infinity.infoway.agriculture.model.LectureplanEmployeeSubTopics;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeLEcturePlanAdapter extends BaseExpandableListAdapter {
    private Context _context;

    private HashMap<Integer, ArrayList<LectureplanEmployeeSubTopics>> _listDataChild;
    ArrayList<LecturePlanEmployee> lessiondetail;

    public EmployeeLEcturePlanAdapter(Context context, ArrayList<LecturePlanEmployee> lessiondetail,
                                      HashMap<Integer, ArrayList<LectureplanEmployeeSubTopics>> listChildData) {
        this._context = context;
        this.lessiondetail = lessiondetail;
        this._listDataChild = listChildData;


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition).get(childPosititon).gettopic_Name();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_topics_lectureplaning, null);
        }

        LinearLayout lltopics = (LinearLayout) convertView.findViewById(R.id.lltopics);

        ArrayList<LectureplanEmployeeSubTopics> topics = _listDataChild.get(childPosition);
       /* for(int i=0 ;i<topics.size();i++)
        {*/
        TextView tvtopics = (TextView) convertView.findViewById(R.id.tvtopicname);
         /*   tvtopics.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));*/
        //  tvtopics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_forward, 0, 0, 0);
        // tvtopics.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_arrow_forward),null,null,null);
        tvtopics.setText((String) getChild(groupPosition, childPosition));
        // tvtopics.setTextColor(_context.getResources().getColor(R.color.fees));
        //  tvtopics.setPadding(5,0,0,0);
        // lltopics.addView(tvtopics);
        // }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(groupPosition)
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.lessiondetail.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.lessiondetail.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_lectureplan_employee, null);
        }

        TextView tvcoursename = (TextView) convertView.findViewById(R.id.tvcoursename);
        TextView tvsemester = (TextView) convertView.findViewById(R.id.tvsemester);
        TextView tvdivision = (TextView) convertView.findViewById(R.id.tvdivision);
        TextView tvsubjectname = (TextView) convertView.findViewById(R.id.tvsubjectname);
        TextView tvLecture_Per_Week = (TextView) convertView.findViewById(R.id.tvLecture_Per_Week);
        TextView tvreferancebook = (TextView) convertView.findViewById(R.id.tvreferancebook);
        LinearLayout lltopics = (LinearLayout) convertView.findViewById(R.id.lltopics);

        tvcoursename.setText(lessiondetail.get(groupPosition).getCourse_Name());
        tvsemester.setText(lessiondetail.get(groupPosition).getSemester() + "(" + lessiondetail.get(groupPosition).getdivision() + ")");
        tvdivision.setText(lessiondetail.get(groupPosition).getdivision());
        tvsubjectname.setText(lessiondetail.get(groupPosition).getSubject());
        tvLecture_Per_Week.setText(lessiondetail.get(groupPosition).getLecture_Per_Week() + " Lectures per week");
        tvreferancebook.setText(lessiondetail.get(groupPosition).getref_book_name());


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
