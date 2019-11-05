package com.infinity.infoway.gsfc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.BatchPojo;
import com.infinity.infoway.gsfc.model.FacultyPojo;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyPendingFragment extends Fragment {

    FacultyPojo.TableBean bean;
    private EditText edtatendancedate;
    private EditText edtcollegename;
    private EditText edtdeptname;
    private EditText edtcourse;
    private EditText edtsem;
    private EditText edtsub;
    private android.widget.TextView tvsname;
    private EditText edtperiodlab;
    private EditText edtresourse;
    private EditText edtdiv;
    private EditText edtbatch;
    ListView batch_stud_fr1;

    RequestQueue queue;

    public FacultyPendingFragment() {
        // Required empty public constructor
    }

    public FacultyPendingFragment(FacultyPojo.TableBean bean)
    {
        this.bean = bean;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_pending, container, false);


        initViews(view);


        Api_call_Disply_batch_of_student();
        return view;
    }


    public void initViews(View view) {
        // this.edtbatch = (EditText) view.findViewById(R.id.edt_batch);
        // edtbatch.setText(bean.getBatch_name() + "");
        queue = Volley.newRequestQueue(getActivity());
        batch_stud_fr1 = (ListView) view.findViewById(R.id.batch_stud_fr1);

        this.edtdiv = (EditText) view.findViewById(R.id.edt_div);
        edtdiv.setText(bean.getDivision_name() + "");
        this.edtresourse = (EditText) view.findViewById(R.id.edt_resourse);
        edtresourse.setText(bean.getResourse_name() + "");
        this.edtperiodlab = (EditText) view.findViewById(R.id.edt_period_lab);
        // edtperiodlab.setText(bean.);
        this.edtsub = (EditText) view.findViewById(R.id.edt_sub);
        edtsub.setText(bean.getSub_name() + "");
        this.edtsem = (EditText) view.findViewById(R.id.edt_sem);
        edtsem.setText(bean.getSemester_name() + "");
        this.edtcourse = (EditText) view.findViewById(R.id.edt_course);
        edtcourse.setText(bean.getCourse_name() + "");
        this.edtdeptname = (EditText) view.findViewById(R.id.edt_dept_name);
        edtdeptname.setText(bean.getDepartment() + "");
        this.edtcollegename = (EditText) view.findViewById(R.id.edt_college_name);
        edtcollegename.setText(bean.getCollege_name() + "");
        this.edtatendancedate = (EditText) view.findViewById(R.id.edt_atendance_date);
        edtatendancedate.setText(bean.getDl_date() + "");

    }

    BatchPojo batchPojo;

    public void Api_call_Disply_batch_of_student() {
        String URLs = URl.Display_batch_student_wise + "&dept_id=" + bean.getDept_id() + "&course_id=" + bean.getCourse_id() + "&sem_id=" + bean.getSm_id() + "&dvm_id=" + bean.getDiv_id() + "&batch_id=" + bean.getBatch_id() + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Display_batch_student_wise calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Display_batch_student_wise RESPONSE      " + response + "");
                        //response = "{\"Faculty\":" + response + "}";


                        System.out.println("THIS IS Display_batch_student_wise RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();


                            batchPojo = gson.fromJson(response, BatchPojo.class);
                            if (batchPojo != null && batchPojo.getTable().size() > 0) {

//                                BatchAdapterCheckBox adapter = new BatchAdapterCheckBox(getActivity(), batchPojo);
//                                batch_stud_fr1.setAdapter(adapter);
                            }


                        }
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
