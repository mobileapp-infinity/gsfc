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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.FillAttendanceActivityfaculty;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.PostElPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pragna on 16-4-2019.
 */

public class E_LearningPostAdapter extends BaseAdapter
{
    private Context ctx;
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
    PostElPojo postElPojo;
    Boolean b;
    public managePostClick _managepost;

    public interface managePostClick
    {
        /*public void managepostClick(int postion);*/

        public void manageEditClick(int postion);

        public void manageDeleteClick(int postion);
    }


    public E_LearningPostAdapter(Context ctx,PostElPojo postElPojo, Boolean b, managePostClick _managepost)
    {

        this.ctx = ctx;
        this.postElPojo = postElPojo;
        //   this.studentsDisplyaFillPojo = studentsDisplyaFillPojo;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        this._managepost = _managepost;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount()
    {
        // return customBenifitList.size();
        return postElPojo.getTable().size();
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


    private class ViewHolder
    {

        private TextView tv_tv1, tv_tv2, tv_manage, tv_tv0;
        ImageView iv_delete, iv_edit;

    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
        {
//            view = inflater.inflate(R.layout.e_learning_adpter, null);
            view = inflater.inflate(R.layout.e_learning_postadpter, null);
            holder = new ViewHolder();
            //    holder.itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);


            // holder.llmainstudentfetch = (LinearLayout) view.findViewById(R.id.ll_main_student_fetch);
            holder.tv_tv1 = (TextView) view.findViewById(R.id.tv_tv1);
            holder.tv_tv0 = (TextView) view.findViewById(R.id.tv_tv0);
            holder.tv_tv2 = (TextView) view.findViewById(R.id.tv_tv2);
            holder.iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
            holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
//            holder.tv_manage = (TextView) view.findViewById(R.id.tv_manage);

            view.setTag(holder);
        }
        else
            {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_tv0.setText(postElPojo.getTable().get(position).getEl_grp_file_date()+"");
        holder.tv_tv1.setText(postElPojo.getTable().get(position).getEl_grp_name()+"");
        holder.tv_tv2.setText(postElPojo.getTable().get(position).getEl_grp_file_desc()+"");
       /* holder.tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.managepostClick(position);
            }
        });*/
        holder.iv_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                _managepost.manageDeleteClick(position);
            }
        });
        holder.iv_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                _managepost.manageEditClick(position);
            }
        });


        this.b = FillAttendanceActivityfaculty.b;

        return view;
    }

}