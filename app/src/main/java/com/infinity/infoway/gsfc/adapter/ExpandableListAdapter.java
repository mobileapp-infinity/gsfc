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

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context _context;
    private List<String> _listDataHeader; // header titles
    private List<String> _listDataLateby; // header titles
    private List<String> _listDataEarlyby; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChildName;


    public ExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listDataEarlyby, List<String> listDataLateby,
                                 HashMap<String, List<String>> listChildData, HashMap<String, List<String>> listDataChildName)
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listDataChildName = listDataChildName;
        this._listDataEarlyby = listDataEarlyby;
        this._listDataLateby = listDataLateby;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon)
    {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getChildName(int groupPosition, int childPosition)
    {
        return this._listDataChildName.get(this._listDataHeader.get(groupPosition)).get(childPosition);
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

        final String childText = (String)getChild(groupPosition, childPosition);
        final String childTextName = (String)getChildName(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_empattendance_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        TextView txtListName = (TextView) convertView
                .findViewById(R.id.lblListName);
        TextView tvseparater = (TextView) convertView
                .findViewById(R.id.tvseparater);

        if (childPosition > 3)
        {
            tvseparater.setVisibility(View.GONE);
            txtListChild.setText("Outtime :" + childTextName);
            txtListName.setText("Intime :" + childText);

        } else
            {
            txtListChild.setText(childText);
            txtListName.setText(childTextName);
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
        return this._listDataHeader.get(groupPosition);
    }

    public Object getEarlyby(int groupPosition)
    {
        return this._listDataEarlyby.get(groupPosition);
    }

    public Object getLateby(int groupPosition)
    {
        return this._listDataLateby.get(groupPosition);
    }

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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition)+"";
//        Log.d("headertitle", headerTitle);
        String headerlateby = (String) getLateby(groupPosition)+"";
       // Log.d("headerlateby", headerlateby);
        String headerearlyby = (String) getEarlyby(groupPosition)+"";
       // Log.d("headerearlyby", headerearlyby);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_empattendance_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);

        TextView tvsrno = (TextView) convertView
                .findViewById(R.id.tvsrno);

        TextView tvearlyby = (TextView) convertView
                .findViewById(R.id.tvearlyby);

        TextView tvlateby = (TextView) convertView
                .findViewById(R.id.tvlateby);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        tvsrno.setTypeface(null, Typeface.BOLD);

        lblListHeader.setText(headerTitle);
        tvearlyby.setText(headerearlyby);
        tvlateby.setText(headerlateby);

        if (headerearlyby.equals("P"))
        {
            tvearlyby.setTextColor(_context.getResources().getColor(R.color.green));
            tvearlyby.setTypeface(null, Typeface.BOLD);

        } else if (headerearlyby.equals("AB"))
        {
            tvearlyby.setTextColor(_context.getResources().getColor(R.color.red));
            tvearlyby.setTypeface(null, Typeface.BOLD);
        } else if (headerearlyby.equals("WO"))
        {
            tvearlyby.setTypeface(null, Typeface.BOLD);
            tvearlyby.setTextColor(_context.getResources().getColor(R.color.profile));
        }
        tvsrno.setText(String.valueOf(groupPosition + 1));

        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}