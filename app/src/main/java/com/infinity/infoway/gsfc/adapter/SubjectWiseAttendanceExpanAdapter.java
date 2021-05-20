package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.infinity.infoway.gsfc.R;

import java.util.HashMap;
import java.util.List;

public class SubjectWiseAttendanceExpanAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles

    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChild2;
    private HashMap<String, List<String>> _listDataChild3;
    private HashMap<String, List<String>> _listDataChild4;
    private HashMap<String, List<String>> _listDataChild5;
    private HashMap<String, List<String>> _listDataChild6;
    private HashMap<String, List<String>> list_aggregate;


    public SubjectWiseAttendanceExpanAdapter(Context context, List<String> listDataHeader,
                                             HashMap<String, List<String>> listChildData, HashMap<String, List<String>> listChildData2, HashMap<String, List<String>> listChildData3, HashMap<String, List<String>> listChildData4, HashMap<String, List<String>> listChildData5, HashMap<String, List<String>> listChildData6, HashMap<String, List<String>> list_aggregate) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listDataChild2 = listChildData2;
        this._listDataChild3 = listChildData3;
        this._listDataChild4 = listChildData4;
        this._listDataChild5 = listChildData5;
        this._listDataChild6 = listChildData6;
        this.list_aggregate = list_aggregate;

        for (String s :list_aggregate.keySet())
        {
            System.out.println("key of aggregate :::::::: "+list_aggregate.keySet());
            System.out.println("value of aggregate :::::::: "+list_aggregate.get(s));
        }

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getTotLect(int groupPosition, int childPosition) {
        return this._listDataChild5.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public Object getRemLect(int groupPosition, int childPosition) {
        return this._listDataChild4.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public Object getPresLect(int groupPosition, int childPosition) {
        return this._listDataChild3.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public Object getTotPres(int groupPosition, int childPosition) {
        return this._listDataChild6.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public Object getAggrigate(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public Object getPercentageLect(int groupPosition, int childPosition) {
        return this._listDataChild2.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }
    public Object getAggregateForTotal(int groupPosition, int childPosition) {
        return this.list_aggregate.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_sub_wise_attgsfc, null);
        }

        TextView tv_total_lect = (TextView) convertView
                .findViewById(R.id.tv_total_lect);

        tv_total_lect.setText((String) getTotLect(groupPosition, childPosition));

        TextView tv_remaining_lect = (TextView) convertView
                .findViewById(R.id.tv_remaining_lect);

        tv_remaining_lect.setText((String) getRemLect(groupPosition, childPosition));


        TextView tv_prs_in_lect = (TextView) convertView
                .findViewById(R.id.tv_prs_in_lect);

        tv_prs_in_lect.setText((String) getPresLect(groupPosition, childPosition));

        TextView tv_att_in_per = (TextView) convertView.findViewById(R.id.tv_att_in_per);

       /* if (childPosition == _listDataChild.get(this._listDataHeader.get(groupPosition))
                .size()-1)
        {
            tv_att_in_per.setText((String) getAggregateForTotal(groupPosition, childPosition));

            System.out.println("***************************** ");
        }
        else
        {
            tv_att_in_per.setText((String) getAggrigate(groupPosition, childPosition));

        }*/
        System.out.println("getChildView "+groupPosition+"");
        if (groupPosition == _listDataHeader.size())
        {
            // here i have done one changes before it was -1 and i remove -1//
            tv_att_in_per.setText((String) getAggregateForTotal(groupPosition, childPosition));
//            tv_att_in_per.setText((String) getAggrigate(groupPosition-1, childPosition));
           // tv_att_in_per.setText("PP");
            System.out.println("***************************** ");
        }
        else
        {
            tv_att_in_per.setText((String) getAggrigate(groupPosition, childPosition));
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        System.out.println("getgroup "+groupPosition+"");
        return this._listDataHeader.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        //Log.d("headertitle", headerTitle);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_sub_attgsfc, null);
        }

        TextView sub_name = (TextView) convertView
                .findViewById(R.id.sub_name);


        sub_name.setTypeface(null, Typeface.BOLD);

        sub_name.setText(headerTitle);


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
