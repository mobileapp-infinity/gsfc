package com.infinity.infoway.agriculture.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.MyGroupAdapter;
import com.infinity.infoway.agriculture.model.MygroupPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewElerningGrp extends Fragment {


    List<MygroupPojo> rowItems;

    ListView lv_view_grp_elerning;
    public ViewElerningGrp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_elerning_grp, container, false);

        findViews(view);



        String[]  name = new String[] { "Admin"};

        String[] create = new String[] {
                "Admin"
        };

        String[] date = new String[] {
                "17/03/2019"
        };

        rowItems = new ArrayList<MygroupPojo>();
        for (int i = 0; i < name.length; i++) {
            MygroupPojo item = new MygroupPojo(name[i], create[i], date[i]);
            rowItems.add(item);
        }

        MyGroupAdapter myGroupAdapter =new MyGroupAdapter(getActivity(),rowItems);
        lv_view_grp_elerning.setAdapter(myGroupAdapter);





        return  view;
    }

    public void findViews(View view)
    {
        lv_view_grp_elerning =(ListView) view.findViewById(R.id.lv_view_grp_elerning);
    }

}
