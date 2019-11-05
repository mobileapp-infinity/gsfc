package com.infinity.infoway.gsfc.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.MyGroupELerningExpanAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.DisplayELerningGrpPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupNewElerningFrg extends Fragment {

    DataStorage storage;
    RequestQueue queue;

    public List<String> list_grp_name;
    public List<String> list_group_date;
    public List<String> list_desc;
    public List<String> childlist;
    public List<String> childdate;
    public List<String> childurl;
    public MyGroupELerningExpanAdapter myGroupELerningExpanAdapter;
    public HashMap<String, List<String>> listDataChild;
    public HashMap<String, List<String>> listDataChild_date;
    public HashMap<String, List<String>> listDataChild_url;

    public CustomBoldTextView txt_no_available_grp_my;
    public Context ctx;
    Toolbar toolbar;

    String STUD_ID;
    ExpandableListView expan_elerning_mygrp;
    Activity activity;

    public MyGroupNewElerningFrg()
    {
        // Required empty public constructor
    }

    public MyGroupNewElerningFrg(Activity activity)
    {

        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_group_new_elerning_frg, container, false);

        INITVIEWS(view);

        STUD_ID = String.valueOf(storage.read("stud_id", 3));

        DISPLAY_STUDENT_ENROLL_GROUP(STUD_ID);

        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            System.out.println("THIS IS REFRESH MyGroupNewElerningFrg****** ");
            DISPLAY_STUDENT_ENROLL_GROUP(STUD_ID);
            System.out.println("STUD_ID MyGroupNewElerningFrg*****::::::*****"+STUD_ID);
        }
    }
    DisplayELerningGrpPojo displayELerningGrpPojo;

    private void DISPLAY_STUDENT_ENROLL_GROUP(final String stud_id)
    {


        //DialogUtils.showProgressDialog(getActivity(), "");
        list_grp_name = new ArrayList<>();
        list_group_date = new ArrayList<>();
        list_desc = new ArrayList<>();

        listDataChild = new HashMap<>();
        listDataChild_date = new HashMap<>();
        listDataChild_url = new HashMap<>();

//        String URLs = URl.Get_student_wise_group_detail_for_enroll_to_group_API + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) + "";

        String URLs = URl.Get_My_Group_Master_Detail_student_Wise_API + "&stud_id=" + stud_id + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_My_Group_Master_Detail_student_Wise_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";

                        response = "{\"My_Group\":" + response + "}";

                        System.out.println("THIS IS Get_My_Group_Master_Detail_student_Wise_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            displayELerningGrpPojo = gson.fromJson(response, DisplayELerningGrpPojo.class);

                            if (displayELerningGrpPojo != null && displayELerningGrpPojo.getMy_Group().size() > 0) {
                                txt_no_available_grp_my.setVisibility(View.GONE);

                                for (int i = 0; i < displayELerningGrpPojo.getMy_Group().size(); i++) {

                                    list_grp_name.add(displayELerningGrpPojo.getMy_Group().get(i).getGroup_name() + "");
                                    list_group_date.add(displayELerningGrpPojo.getMy_Group().get(i).getGroup_created_date() + "");
                                    list_desc.add(displayELerningGrpPojo.getMy_Group().get(i).getGrp_desc() + "");

                                    childlist = new ArrayList<String>();
                                    childdate = new ArrayList<String>();
                                    childurl = new ArrayList<String>();
                                    List<DisplayELerningGrpPojo.MyGroupBean.GroupDetailArrayBean> second_list = new ArrayList<>();


                                    second_list = displayELerningGrpPojo.getMy_Group().get(i).getGroup_detail_array();


                                    System.out.println("second_list In My GRP::::::::::::" + second_list.size());
                                    System.out.println("second_list In My GRP------***::::::::::::" + second_list.toString());


                                    if (second_list != null && !second_list.isEmpty() && second_list.size() > 1) {
                                        for (int j = 0; j < second_list.size(); j++) {
                                            childlist.add(second_list.get(j).getFile_desc() + "");
                                            childdate.add(second_list.get(j).getFile_upload_date() + "");
                                            childurl.add(second_list.get(j).getFile_url() + "");
                                        }
                                    }


                                    listDataChild.put(list_grp_name.get(i), childlist);
                                    listDataChild_date.put(list_grp_name.get(i), childdate);
                                    listDataChild_url.put(list_grp_name.get(i), childurl);

                                }


                                myGroupELerningExpanAdapter = new MyGroupELerningExpanAdapter(getActivity(), list_grp_name, list_group_date, list_desc, listDataChild, listDataChild_date, listDataChild_url, stud_id, activity);
                                expan_elerning_mygrp.setAdapter(myGroupELerningExpanAdapter);

                            } else {
                                expan_elerning_mygrp.setVisibility(View.GONE);
                                txt_no_available_grp_my.setVisibility(View.VISIBLE);
                            }


                        }

//                        else
//                        {
//                            DialogUtils.Show_Toast(FacultyAttendance.this,"No Pending Attendance");
//                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }

    private void INITVIEWS(View view)
    {
        storage = new DataStorage("Login_Detail", getActivity());

        queue = Volley.newRequestQueue(getActivity());


        txt_no_available_grp_my = (CustomBoldTextView) view.findViewById(R.id.txt_no_available_grp_my);
        expan_elerning_mygrp = (ExpandableListView) view.findViewById(R.id.expan_elerning_mygrp);
    }

}
