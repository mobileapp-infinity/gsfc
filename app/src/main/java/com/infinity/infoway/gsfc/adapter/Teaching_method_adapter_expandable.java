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
import android.widget.CompoundButton;

import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;
import com.infinity.infoway.gsfc.model.TeachingMethodPojo;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pragna on 16-4-2019.
 */

public class Teaching_method_adapter_expandable extends BaseAdapter {

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
//    List<String> customBenifitList;
    DataStorage storage;
    RequestQueue queue;
    Activity a;

    TeachingMethodPojo teachingMethodPojo;
    Boolean b;

    public static HashMap<String, String> ID_Check_status = new HashMap<>();
//    public StudentListAdapter(List<String> customBenifitList, Context ctx) {
//        this.ctx = ctx;
//        this.customBenifitList=customBenifitList;
//        inflater = LayoutInflater.from(this.ctx);
//        //selected.addAll(customBenifitList);
//    }




    public Teaching_method_adapter_expandable(Context ctx, TeachingMethodPojo teachingMethodPojo) {

        this.ctx = ctx;
        this.teachingMethodPojo = teachingMethodPojo;
//        this.b = b;

        inflater = LayoutInflater.from(this.ctx);
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount() {
        return teachingMethodPojo.getTable().size();
    }

    @Override
    public Object getItem(int position) {
        return teachingMethodPojo.getTable().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<String> getSelected() {


        return selected;
    }

    public void clearSelected() {
        selected.clear();
    }


    private class ViewHolder {

//        CheckBox itemCheckBox;
       // CustomTextView txt_status_show;
    //    CustomTextView txt_status_counter;
      // final View mView;
        StudentsDisplyaFillPojo studentsDisplyaFillPojo;
        private CheckBox cbcheck;


    }
    public static ArrayList<String> selected_check_IDS = new ArrayList<>();
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_teaching_method, null);
            holder = new ViewHolder();
        //    holder.itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);

           holder. cbcheck = (CheckBox) view.findViewById(R.id.cb_rec_teaching_method);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
      //  holder.itemCheckBox.setTag(position); // This line is important.
//        holder.itemCheckBox.setText(customBenifitList.get(position)+ "");
//        holder.itemCheckBox.setTypeface(Validations.setTypeface(ctx));
//        //    holder.txt_status.setTypeface(Validations.setTypeface(ctx));
//        if (position == selectedPosition) {
//            holder.itemCheckBox.setChecked(true);
//        } else {
//            holder.itemCheckBox.setChecked(false);
//        }
//        holder.itemCheckBox.setChecked(selected.contains(customBenifitList + ""));
//        // holder.itemCheckBox.setOnClickListener(onStateChangedListener(holder.itemCheckBox, position));
//        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int pos = (Integer) buttonView.getTag();
//                if(buttonView.isShown()) {
//                    if (!buttonView.isChecked()) {
//                        selected.remove(customBenifitList.get(position) + "");
//                    } else if (buttonView.isChecked()) {
//                        if (!selected.contains(customBenifitList.get(position) + "")) {
//                            selected.add(customBenifitList.get(position) + "");
//                        }
//                    }
//                }
//            }
//        });




//        holder.txtsrno.setText("  " + teachingMethodPojo.getTable().get(position).getSr_no() + "");
        holder.cbcheck.setText(teachingMethodPojo.getTable().get(position).getTm_short_name()+"");
        holder.cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (b)
                {
                    selected_check_IDS .add(teachingMethodPojo.getTable().get(position).getTm_id());
                    System.out.println("selected_check_IDS checkeddddddd::::::::::::"+selected_check_IDS.toString());
                }
                else
                {
                    selected_check_IDS .remove(teachingMethodPojo.getTable().get(position).getTm_id());
                    System.out.println("selected_check_IDS uncheckeeeeeee::::::::::::"+selected_check_IDS.toString());
                }

                System.out.println("selected_check_IDS alllllll::::::::::::"+selected_check_IDS.toString());
            }
        });
        return view;
    }


}