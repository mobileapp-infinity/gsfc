package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.EnrollGrpNewElerning;
import com.infinity.infoway.gsfc.model.CheckEnrollPojo;
import com.infinity.infoway.gsfc.model.InsertELerningPojo;

import java.util.HashMap;
import java.util.List;

public class ElerningExpanEnrollGroupAdapter extends BaseExpandableListAdapter {

    DataStorage storage;
    RequestQueue queue;
    private Context _context;
    private List<String> _listDataHeader; // header titles grp name
    private List<String> list_group_id; // header titles grp id
    private List<String> list_date; // header titles date
    String STUD_ID;
    private HashMap<String, List<String>> _listDataChildName;//child title
    InsertELerningPojo insertELerningPojo;

    public ElerningExpanEnrollGroupAdapter(Context context, List<String> list_group_id, List<String> listDataHeader, List<String> list_date, HashMap<String, List<String>> _listDataChildName, String STUD_ID)
    {
        this._context = context;
        this.list_group_id = list_group_id;
        this._listDataHeader = listDataHeader;
        this.list_date = list_date;
        this._listDataChildName = _listDataChildName;
        this.STUD_ID = STUD_ID;

        storage = new DataStorage("Login_Detail", context);
        queue = Volley.newRequestQueue(context);


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChildName.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        System.out.println("childText:::::::::::::" + childText);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_expan_enroll_grp, null);
        }

        CustomTextView tv_desc_enroll = (CustomTextView) convertView
                .findViewById(R.id.tv_desc_enroll);

        CustomTextView tv_click_enroll = (CustomTextView) convertView
                .findViewById(R.id.tv_click_enroll);
        CustomTextView tv_click_decline = (CustomTextView) convertView
                .findViewById(R.id.tv_click_decline);


        tv_desc_enroll.setText("   " + childText);


        tv_click_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                CHECK_GROUP_IS_ENROLL_OR_NOT(list_group_id.get(groupPosition));

                System.out.println("list_group_id:::::" + list_group_id.get(groupPosition));

            }
        });

        tv_click_decline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DECLINE_STUDENT_FROM_GROUP(list_group_id.get(groupPosition));

            }
        });
        return convertView;
    }

    private void DECLINE_STUDENT_FROM_GROUP(String GRP_ID)
    {
        String URLs = URl.Insert_E_Learning_Group_Wise_Student_Master_API + "&group_id=" + GRP_ID + "&stud_id=" + STUD_ID + "&created_by=" + STUD_ID + "&created_ip=" + "1" + "&enroll_status=" + "0" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Check_Exist_E_Learning_Group_Wise_Student_Master_API calls decline student    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Check_Exist_E_Learning_Group_Wise_Student_Master_API RESPONSE  decline student     " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            insertELerningPojo = gson.fromJson(response, InsertELerningPojo.class);

                            if (insertELerningPojo != null && insertELerningPojo.getTable().get(0).getError_code().contentEquals("1"))
                            {
                                EnrollGrpNewElerning.Enroll_To_Group_API(STUD_ID);

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

    CheckEnrollPojo checkEnrollPojo;

    private void CHECK_GROUP_IS_ENROLL_OR_NOT(final String GROUP_ID)
    {

        //DialogUtils.showProgressDialog(ELerningStudentActivity.this,"");


        String URLs = URl.Check_Exist_E_Learning_Group_Wise_Student_Master_API + "&group_id=" + GROUP_ID + "&stud_id=" + STUD_ID + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Check_Exist_E_Learning_Group_Wise_Student_Master_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Check_Exist_E_Learning_Group_Wise_Student_Master_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            checkEnrollPojo = gson.fromJson(response, CheckEnrollPojo.class);

                            if (checkEnrollPojo != null) {


                                if (checkEnrollPojo.getTable().get(0).getExist_status().contentEquals("0")) {
                                    ENROLL_STUDENT_IF_NOT(GROUP_ID, checkEnrollPojo.getTable().get(0).getExist_status());
                                }


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




    private void ENROLL_STUDENT_IF_NOT(String grp_id, String ENROLL_STATUS) {

        String URLs = URl.Insert_E_Learning_Group_Wise_Student_Master_API + "&group_id=" + grp_id + "&stud_id=" +STUD_ID + "&created_by=" + STUD_ID + "&created_ip=" + "1" + "&enroll_status=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Check_Exist_E_Learning_Group_Wise_Student_Master_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("THIS IS Check_Exist_E_Learning_Group_Wise_Student_Master_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            insertELerningPojo = gson.fromJson(response, InsertELerningPojo.class);

                            if (insertELerningPojo != null && insertELerningPojo.getTable().size() > 0) {


                                if (insertELerningPojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(_context, "Enroll Successfully");


                                    EnrollGrpNewElerning.Enroll_To_Group_API(STUD_ID);
                                }


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

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildName.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    public Object get_date(int groupPosition) {
        return this.list_date.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition) + "";
        System.out.println("headerTitle:::::::::::::" + headerTitle);
        String Date = (String) get_date(groupPosition) + "";
        System.out.println("Date:::::::::::::" + Date);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_expan_enroll_grp, null);
        }

        CustomBoldTextView txt_sub_name = (CustomBoldTextView) convertView
                .findViewById(R.id.txt_sub_name);

        CustomTextView txt_date_header = (CustomTextView) convertView
                .findViewById(R.id.txt_date_header);

        txt_sub_name.setText(headerTitle);
        txt_date_header.setText("  "+Date);

     /*
        tvearlyby.setText(headerearlyby);
        tvlateby.setText(headerlateby);

        tvsrno.setText(String.valueOf(groupPosition + 1));
*/
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
