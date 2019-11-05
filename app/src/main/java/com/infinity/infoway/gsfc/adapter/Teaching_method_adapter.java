package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.TeachingMethodPojo;

import java.util.ArrayList;

public class Teaching_method_adapter  extends RecyclerView.Adapter<Teaching_method_adapter.ViewHolder> {

    Activity a;
    Context ctx;
    DataStorage storage;
    TeachingMethodPojo teachingMethodPojo;

   public static ArrayList<String> selected_check_IDS = new ArrayList<>();

    public Teaching_method_adapter(Context ctx, TeachingMethodPojo teachingMethodPojo) {

        this.ctx = ctx;
        this.teachingMethodPojo = teachingMethodPojo;

        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_teaching_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.teachingMethodPojo = teachingMethodPojo;
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



    }


    @Override
    public int getItemCount()
    {
        return teachingMethodPojo.getTable().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final View mView;
        TeachingMethodPojo teachingMethodPojo;



        private CheckBox cbcheck;


        ViewHolder(View view)
        {
            super(view);
            mView = view;


            this.cbcheck = (CheckBox) view.findViewById(R.id.cb_rec_teaching_method);



        }


    }
}


