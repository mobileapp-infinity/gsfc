package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.ELearningActivity;
import com.infinity.infoway.gsfc.fragment.MyGroup;
import com.infinity.infoway.gsfc.model.MygroupPojo;

import java.util.ArrayList;
import java.util.List;

public class MyGroupAdapter extends BaseAdapter
{

    Context context;
    List<MygroupPojo> rowItems;

    public MyGroupAdapter(Context context, List<MygroupPojo> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
      //  ImageView imageView;
        TextView txt_gpcreated_date;
        TextView txt_gpcreated_by;
        TextView txt_gp_name_elerning;
        TextView txt_view_grp_button;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_view_elerning_grp, null);
            holder = new ViewHolder();


            holder.txt_gpcreated_date = (TextView) convertView.findViewById(R.id.txt_gpcreated_date_join);
            holder.txt_gpcreated_by = (TextView) convertView.findViewById(R.id.txt_gpcreated_by_join);
            holder.txt_gp_name_elerning = (TextView) convertView.findViewById(R.id.txt_gp_name_elerning_join);
            holder.txt_view_grp_button = (TextView) convertView.findViewById(R.id.txt_join_grp_button);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        MygroupPojo rowItem = (MygroupPojo) getItem(position);

        holder.txt_gpcreated_date.setText("  "+rowItem.getDesc());
        holder.txt_gpcreated_by.setText("   "+rowItem.getTitle());
        holder.txt_gp_name_elerning.setText("  "+rowItem.getImageId());
        final ViewHolder finalHolder = holder;
        holder.txt_view_grp_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String name_grp = finalHolder.txt_gp_name_elerning.getText().toString();
                System.out.println("name_grp On click :::::::::::::"+name_grp);

                String name_grp_created_by = finalHolder.txt_gpcreated_by.getText().toString();

                System.out.println("name_grp_created_by On click :::::::::::::"+name_grp_created_by);

                String dategrp = finalHolder.txt_gpcreated_date.getText().toString();
                System.out.println("dategrp On click :::::::::::::"+dategrp);


                String[]  name = new String[] { name_grp};

                String[] create = new String[] {
                        name_grp_created_by
                };

                String[] date = new String[] {
                        dategrp
                };

                rowItems = new ArrayList<MygroupPojo>();
                for (int i = 0; i < name.length; i++)
                {
                    MygroupPojo item = new MygroupPojo(name[i], create[i], date[i]);
                    rowItems.add(item);
                }

              ViewgrpAdapter viewgrpAdapter = new ViewgrpAdapter(context,rowItems);
                MyGroup.lv_my_grp_elerning.setAdapter(viewgrpAdapter);


                ELearningActivity.vp_elerning.setCurrentItem(1);


            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
