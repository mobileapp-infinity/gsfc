package com.infinity.infoway.gsfc.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.SubjectWiseAttendanceExpanAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.AttData;
import com.infinity.infoway.gsfc.model.Innerdata;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubWiseAttendance extends Fragment {

    DataStorage storage;
    ExpandableListView el_sub_wise_attgsfc;
    SubjectWiseAttendanceExpanAdapter listAdapter;
    List<String> listDataHeader;
    List<String> listDataLateby;
    List<String> listDataEarlyby;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChild2;
    HashMap<String, List<String>> listDataChild3;
    HashMap<String, List<String>> listDataChild4;
    HashMap<String, List<String>> listDataChild5;
    HashMap<String, List<String>> listDataChild6;
    HashMap<String, List<String>> list_aggregate;


    public SubWiseAttendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_wise_attendance, container, false);

        Init(view);

//        API_call_Subject_Wise_Attendance();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();//worked

            API_call_Subject_Wise_Attendance();
        }
    }

    private void Init(View view) {

        storage = new DataStorage("Login_Detail", getActivity());
        el_sub_wise_attgsfc = (ExpandableListView) view.findViewById(R.id.el_sub_wise_attgsfc);
    }

    private void API_call_Subject_Wise_Attendance()
    {


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChild2 = new HashMap<String, List<String>>();
        listDataChild3 = new HashMap<String, List<String>>();
        listDataChild4 = new HashMap<String, List<String>>();
        listDataChild5 = new HashMap<String, List<String>>();
        listDataChild6 = new HashMap<String, List<String>>();
        list_aggregate = new HashMap<String, List<String>>();


        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyTheme1);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> mParams;
        mParams = new HashMap<String, String>();

        //Log.d("school_id", String.valueOf(storage.read("emp_main_school_id", 3)));
        mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));

        // Log.d("emp_id", String.valueOf(storage.read("emp_id", 3)));
        mParams.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));


        Call<ArrayList<AttData>> call = apiService.get_sub_wise_att(mParams);
        System.out.println("subject wise att ::::::"+call.request());
        call.enqueue(new Callback<ArrayList<AttData>>()
        {
            @Override
            public void onResponse(Call<ArrayList<AttData>> call, Response<ArrayList<AttData>> response)
            {

                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {
                            //Adding Header
                            listDataHeader.add(response.body().get(i).getSub_fullname());

                            // Adding child data
                            List<String> childlist = new ArrayList<String>();
                            List<String> childlist2 = new ArrayList<String>();
                            List<String> childlist3 = new ArrayList<String>();
                            List<String> childlist4 = new ArrayList<String>();
                            List<String> childlist5 = new ArrayList<String>();
                            List<String> childlist6 = new ArrayList<String>();
                            List<String> list_aggre = new ArrayList<String>();


//                                 Adding child data Name
                            List<String> childlistname = new ArrayList<String>();


                            //Addind List inout time
                            ArrayList<Innerdata> innerdata = response.body().get(i).getInout_array1();

                            for (int j = 0; j < innerdata.size(); j++)
                            {
//                                childlist.add(innerdata.get(j).getAggr());
                                childlist.add(innerdata.get(j).getPersentage_lect());
                                childlist2.add(innerdata.get(j).getPersentage_lect());
                                childlist3.add(innerdata.get(j).getPresent_lect());
                                childlist4.add(innerdata.get(j).getRemaining_lect());
                                childlist5.add(innerdata.get(j).getTot_lect());
                                childlist6.add(innerdata.get(j).getTotalPresent());
                                list_aggre.add(innerdata.get(j).getAggr());
                            }

                            listDataChild.put(listDataHeader.get(i), childlist);
                            listDataChild2.put(listDataHeader.get(i), childlist2);
                            listDataChild3.put(listDataHeader.get(i), childlist3);
                            listDataChild4.put(listDataHeader.get(i), childlist4);
                            listDataChild5.put(listDataHeader.get(i), childlist5);
                            listDataChild6.put(listDataHeader.get(i), childlist6);
                            list_aggregate.put(listDataHeader.get(i), list_aggre);
                            System.out.println("aggregate of lecture::::::::: "+list_aggre.toString());
                            Log.d("childdata", String.valueOf(listDataChild));


                        }

                        listAdapter = new SubjectWiseAttendanceExpanAdapter(getActivity(), listDataHeader, listDataChild,listDataChild2,listDataChild3,listDataChild4,listDataChild5,listDataChild6,list_aggregate);
                        el_sub_wise_attgsfc.setAdapter(listAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_LONG).show();
                        listAdapter = new SubjectWiseAttendanceExpanAdapter(getActivity(), listDataHeader, listDataChild,listDataChild2,listDataChild3,listDataChild4,listDataChild5,listDataChild6,list_aggregate);

                    }
                } else {
//                     Toast.makeText(getActivity(), "Please Try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AttData>> call, Throwable t)
            {
                progressDialog.dismiss();
                // Log error here since request failed
                //     Log.e("emplotteattendancerespo", t.toString());
            }
        });


    }
}
