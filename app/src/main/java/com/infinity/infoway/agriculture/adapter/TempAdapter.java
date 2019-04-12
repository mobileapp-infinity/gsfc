package com.infinity.infoway.agriculture.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.activity.DirectivePageActivity;
import com.infinity.infoway.agriculture.app.MarshMallowPermission;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user01 on 1/17/2018.
 */

public class TempAdapter extends BaseExpandableListAdapter
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
    private HashMap<String, List<String>> _listDataChildName;
    public static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 4;

    public MarshMallowPermission marshMallowPermission = new MarshMallowPermission(_context);
    public TempAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> _listDataChild, HashMap<String, List<String>> _listDataChild1, HashMap<String, List<String>> _listDataChild2, HashMap<String, List<String>> _listDataChild3)
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = _listDataChild;
        this._listDataChild1 = _listDataChild1;
        this._listDataChild2 = _listDataChild2;
        this._listDataChild3 = _listDataChild3;
//        this._listDataChildName = listDataChildName;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon)
    {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);

    }

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


        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.child_item_1);

        TextView txtListName = (TextView) convertView
                .findViewById(R.id.child_item_2);
        TextView tvseparater = (TextView) convertView
                .findViewById(R.id.child_item_3);

        TextView number = (TextView) convertView.findViewById(R.id.child_item_4);

        //txtListChild.setText(childText);

        txtListChild.setText(childText3);
        txtListName.setText(childText1);
        tvseparater.setText(childText);
        number.setText(childText2);
        number.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"));
                if (checkPermission())
                {
                    try
                    {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(childText2));
                        _context.startActivity(intent);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(_context, "Please try again later" + e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }
                else
                    {
                    requestPermission();
                }

//
//                        try
//                        {
//                            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
//                            my_callIntent.setData(Uri.parse("tel:" + childText2));
//                            //here the word 'tel' is important for making a call...
//                            _context.startActivity(my_callIntent);
//                        }
//                        catch (ActivityNotFoundException e)
//                        {
//                            Toast.makeText(_context, "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }



            }

            private void requestPermission()
            {
                ActivityCompat.requestPermissions((Activity) _context, new
                        String[]{Manifest.permission.CALL_PHONE}, 1);
            }

            public boolean checkPermission()
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    int result = ContextCompat.checkSelfPermission(_context,
                            android.Manifest.permission.CALL_PHONE);
                    return result == PackageManager.PERMISSION_GRANTED;
                }
                else
                    {
                    return true;
                }

            }
//
        });


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
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.collage_name);


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
