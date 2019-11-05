package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.FillAttendanceActivityfaculty;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pragna on 16-4-2019.
 */

public class SpinnerSimpleAdapter extends BaseAdapter {

    ArrayList<String> check_IDS = new ArrayList<>();
    private Context ctx;
    // List<String> AllStatuslist = new ArrayList<>();
    ViewHolder holder = null;
    public FragmentManager f_manager;
    //private LayoutInflater inflater;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    String customerId;
    View view;
    private int selectedPosition = -1;
    public ArrayList<String> selected = new ArrayList<String>();
    List<String> customBenifitList;
    DataStorage storage;
    RequestQueue queue;
    Activity a;

    StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    Boolean b;
    public managePostClick _managepost;
    ArrayList<String> type;

    public interface managePostClick
    {
        public void managepostClick(int postion);

        public void manageEditClick(int postion);

        public void manageDeleteClick(int postion);
    }

    public static HashMap<String, String> ID_Check_status = new HashMap<>();


    public SpinnerSimpleAdapter(Context ctx, ArrayList<String> type)
    {

        this.ctx = ctx;
        //   this.studentsDisplyaFillPojo = studentsDisplyaFillPojo;
        this.b = b;

        inflater = LayoutInflater.from(this.ctx);
        this.type = new ArrayList<>();
        this.type = type;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount()
    {
        // return customBenifitList.size();
        return type.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
        //  return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    private class ViewHolder {

        CheckBox itemCheckBox;


        private TextView tv_tv1, tv_tv2, tv_manage;
        ImageView iv_delete, iv_edit;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.spinner_simpleadapter, null);
            holder = new ViewHolder();

            holder.tv_tv1 = (TextView) view.findViewById(R.id.text1);


            view.setTag(holder);
        }
        else
            {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_tv1.setText(type.get(position) + "");


        this.b = FillAttendanceActivityfaculty.b;

        return view;
    }


}