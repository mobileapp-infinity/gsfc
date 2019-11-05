package com.infinity.infoway.gsfc.fragment;


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
import com.infinity.infoway.gsfc.adapter.ElerningExpanEnrollGroupAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.EnrollGrpPoJO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollGrpNewElerning extends Fragment {

    public static RequestQueue queue;

    DataStorage storage;
    public static ExpandableListView expan_elerning_grp;
    public static List<String> listDataHeader;
    public static List<String> list_group_id;
    public static List<String> list_date_header;
    public static List<String> childlist;
    public static ElerningExpanEnrollGroupAdapter elerningExpanEnrollGroupAdapter;
    public static HashMap<String, List<String>> listDataChild;

    public static CustomBoldTextView txt_no_available_grp;
    public static Context ctx;
    Toolbar toolbar;

    public EnrollGrpNewElerning() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enroll_grp_new_elerning, container, false);

        ctx = getActivity();
        INIT_VIEWS(view);

        Enroll_To_Group_API(String.valueOf(storage.read("stud_id", 3)));


        return view;
    }

    private void INIT_VIEWS(View view) {

        queue = Volley.newRequestQueue(getActivity());

        txt_no_available_grp = (CustomBoldTextView) view.findViewById(R.id.txt_no_available_grp);

        storage = new DataStorage("Login_Detail", getActivity());

        expan_elerning_grp = (ExpandableListView) view.findViewById(R.id.expan_elerning_grp);

    }


    public static EnrollGrpPoJO enrollGrpPoJO;

    public static void Enroll_To_Group_API(final String Stud_ID) {
        //  DialogUtils.showProgressDialog(ctx, "");
        listDataHeader = new ArrayList<>();
        list_group_id = new ArrayList<>();
        list_date_header = new ArrayList<>();

        listDataChild = new HashMap<>();

//        String URLs = URl.Get_student_wise_group_detail_for_enroll_to_group_API + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) + "";

        String URLs = URl.Get_student_wise_group_detail_for_enroll_to_group_API + "&stud_id=" + Stud_ID + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_student_wise_group_detail_for_enroll_to_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS Get_student_wise_group_detail_for_enroll_to_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            enrollGrpPoJO = gson.fromJson(response, EnrollGrpPoJO.class);

                            if (enrollGrpPoJO != null && enrollGrpPoJO.getEnroll_Group().size() > 0)

                            {
                                txt_no_available_grp.setVisibility(View.GONE);

                                for (int i = 0; i < enrollGrpPoJO.getEnroll_Group().size(); i++) {
                                    list_group_id.add(enrollGrpPoJO.getEnroll_Group().get(i).getGroup_id() + "");
                                    listDataHeader.add(enrollGrpPoJO.getEnroll_Group().get(i).getGroup_name() + "");

                                    list_date_header.add(enrollGrpPoJO.getEnroll_Group().get(i).getGroup_created_date() + "");


                                    List<EnrollGrpPoJO.EnrollGroupBean.GroupDescriptionArrayBean> second_list = new ArrayList<>();
                                    childlist = new ArrayList<String>();

                                    second_list = enrollGrpPoJO.getEnroll_Group().get(i).getGroup_description_array();


                                    for (int j = 0; j < second_list.size(); j++) {
                                        childlist.add(second_list.get(j).getGrp_desc() + "");
                                    }


                                    listDataChild.put(listDataHeader.get(i), childlist);

                                    System.out.println("listDataChild key>>>>>" + listDataChild.keySet());
                                    System.out.println("listDataChild value >>>>>");
                                }


                                for (String s : listDataChild.keySet()) {
                                    System.out.println("KEY OF GROUP::::" + s);
                                    System.out.println("value of group::::" + listDataChild.get(s));
                                }
                                elerningExpanEnrollGroupAdapter = new ElerningExpanEnrollGroupAdapter(ctx, list_group_id, listDataHeader, list_date_header, listDataChild, Stud_ID);
                                expan_elerning_grp.setAdapter(elerningExpanEnrollGroupAdapter);

                            } else {
                                expan_elerning_grp.setVisibility(View.GONE);
                                txt_no_available_grp.setVisibility(View.VISIBLE);
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
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }
}
