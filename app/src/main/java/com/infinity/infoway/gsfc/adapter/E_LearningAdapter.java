package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.ELGroupDisplayPojo;

import java.util.ArrayList;

/**
 * Created by Pragna on 16-4-2019.
 */

public class E_LearningAdapter extends BaseAdapter {


    private Context ctx;
    ViewHolder holder = null;

    private LayoutInflater inflater;
    SharedPreferences prefs;
    String customerId;
    View view;

    ELGroupDisplayPojo elGroupDisplayPojo;
    public ArrayList<String> selected = new ArrayList<String>();

    DataStorage storage;
    RequestQueue queue;
    Activity a;


    Boolean b;
    public managePostClick _managepost;

    public interface managePostClick
    {
        public void managepostClick(int postion);

        public void manageEditClick(int postion);

        public void manageDeleteClick(int postion);
    }


    public E_LearningAdapter(Context ctx, Boolean b, ELGroupDisplayPojo elGroupDisplayPojo, managePostClick _managepost) {

        this.ctx = ctx;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        this._managepost = _managepost;
        this.elGroupDisplayPojo = elGroupDisplayPojo;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount()
    {
        // return customBenifitList.size();
        return elGroupDisplayPojo.getTable().size();
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

        private TextView tv_tv1, tv_tv2, tv_manage;
        ImageView iv_delete, iv_edit;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.e_learning_adpter, null);
            holder = new ViewHolder();

            holder.tv_tv1 = (TextView) view.findViewById(R.id.tv_tv1);
            holder.tv_tv2 = (TextView) view.findViewById(R.id.tv_tv2);
            holder.iv_edit = (ImageView) view.findViewById(R.id.iv_edit_eL);
            holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            holder.tv_manage = (TextView) view.findViewById(R.id.tv_manage);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

//        holder.tv_tv1.setText(elGroupDisplayPojo.getTable().get(position).getEl_grp_name() + "(" + elGroupDisplayPojo.getTable().get(position).getEl_grp_type_id() + ")"+"");

        holder.tv_tv1.setText(elGroupDisplayPojo.getTable().get(position).getEl_grp_name() + "");

        holder.tv_tv2.setText(elGroupDisplayPojo.getTable().get(position).getEl_grp_desc() + "");

        holder.tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.managepostClick(position);
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.manageDeleteClick(position);
            }
        });
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.manageEditClick(position);
            }
        });


        return view;
    }


}