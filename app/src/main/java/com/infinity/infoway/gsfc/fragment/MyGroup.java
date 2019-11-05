package com.infinity.infoway.gsfc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.MygroupPojo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroup extends Fragment {


    public static ListView lv_my_grp_elerning;
    List<MygroupPojo> rowItems;
    public MyGroup()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_group, container, false);


        findViews(view);
//           String[]  name = new String[] { "Admin","Admin1","Admin2"};
//
//           String[] create = new String[] {
//                   "Admin","Admin1","Admin2",
//                 };
//
//        String[] date = new String[] {
//                "17/03/2019","18/03/2019","19/03/2018"
//                };
//
//        rowItems = new ArrayList<MygroupPojo>();
//        for (int i = 0; i < name.length; i++) {
//            MygroupPojo item = new MygroupPojo(name[i], create[i], date[i]);
//            rowItems.add(item);
//        }
//
//        MyGroupAdapter myGroupAdapter =new MyGroupAdapter(getActivity(),rowItems);
//        lv_my_grp_elerning.setAdapter(myGroupAdapter);
//

        return  view;
    }


    public void findViews(View view)
    {
        lv_my_grp_elerning =(ListView)view.findViewById(R.id.lv_my_grp_elerning);
    }



}
