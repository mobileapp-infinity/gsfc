package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user01 on 5/15/2018.
 */

public class ExpanLecturePlanDetailAdapter extends BaseExpandableListAdapter
{

    private Context _context;
    Activity activity;
    private List<String> _listDataHeader; // header titles
    private List<String> _listDataLateby; // header titles
    private List<String> _listDataEarlyby; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChild1;
    private HashMap<String, List<String>> _listDataChild2;
    private HashMap<String, List<String>> _listDataChild3;
    private HashMap<String, List<String>> _listDataChild4;
    private HashMap<String, List<String>> _listDataChild5;
    private HashMap<String, List<String>> _listDataChildName;
    public static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 4;

    public MarshMallowPermission marshMallowPermission = new MarshMallowPermission(_context);
    public ExpanLecturePlanDetailAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> _listDataChild, HashMap<String, List<String>> _listDataChild1, HashMap<String, List<String>> _listDataChild2, HashMap<String, List<String>> _listDataChild3)
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = _listDataChild;
        this._listDataChild1 = _listDataChild1;
        this._listDataChild2 = _listDataChild2;
        this._listDataChild3 = _listDataChild3;
        this._listDataChild4 = _listDataChild4;
        this._listDataChild5 = _listDataChild5;
//        this._listDataChildName = listDataChildName;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon)
    {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);

    }
//    public Object getChildName(int groupPosition, int childPosition)
//    {
//        return this._listDataChildName.get(this._listDataHeader.get(groupPosition)).get(childPosition);
//    }

    public Object getChild1(int groupPosition, int childPosititon)
    {
        return this._listDataChild1.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getChild2(int groupPosition, int childPosititon)
    {
        return this._listDataChild2.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getChild3(int groupPosition, int childPosititon)
    {
        return this._listDataChild3.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {

        final String childText = (String) getChild(groupPosition, childPosition);
        final String childText1 = (String) getChild1(groupPosition, childPosition);
        final String childText2 = (String) getChild2(groupPosition, childPosition);
        final String childText3 = (String) getChild3(groupPosition, childPosition);
//        final String childText4 = (String) getChildName(groupPosition, childPosition);


        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lectureplanexpanchilddata, null);
        }

        TextView sub_topic_sr_no = (TextView) convertView
                .findViewById(R.id.sub_topic_sr_no);

        TextView sub_topic_name = (TextView) convertView
                .findViewById(R.id.sub_topic_name);
        TextView sub_topic_method = (TextView) convertView
                .findViewById(R.id.sub_topic_method);
                TextView aid=(TextView)convertView.findViewById(R.id.sub_topic_aid);

//
//  TextView sr_no_head=(TextView)convertView.findViewById(R.id.sr_no_head);
//  TextView sub_topic_name_head=(TextView)convertView.findViewById(R.id.sub_topic_name_head);
//  TextView sub_topic_method_head=(TextView)convertView.findViewById(R.id.sub_topic_method_head);
//  TextView sub_topic_aid_head=(TextView)convertView.findViewById(R.id.sub_topic_aid_head);

        //txtListChild.setText(childText);


//        sr_no_head.setText(childText4);
//        sub_topic_name_head.setText(childText4);
//        sub_topic_method_head.setText(childText4);
//        sub_topic_aid_head.setText(childText4);


        sub_topic_sr_no.setText(childText);
        sub_topic_name.setText(childText1);
        sub_topic_method.setText(childText3);
        aid.setText(childText2);






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
        return this._listDataHeader.get(groupPosition);
    }

//    public Object getEarlyby(int groupPosition) {
//        return this._listDataEarlyby.get(groupPosition);
//    }
//
//    public Object getLateby(int groupPosition) {
//        return this._listDataLateby.get(groupPosition);
//    }

    @Override
    public int getGroupCount()
    {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        String headerTitle = (String) getGroup(groupPosition);
        Log.d("headertitle", headerTitle);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lectureplanexpanheaderdata, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.topic_name);


        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
