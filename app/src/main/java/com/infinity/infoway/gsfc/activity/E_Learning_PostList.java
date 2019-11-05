package com.infinity.infoway.gsfc.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.URl;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.E_LearningPostAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.DeletePojo;
import com.infinity.infoway.gsfc.model.PostElPojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class E_Learning_PostList extends AppCompatActivity
{
    private Toolbar toolbar;
    private CustomTextView title;
    private ListView list;
    private FloatingActionButton fab;
    private RelativeLayout llelerning;
    E_LearningPostAdapter adapter;
    DataStorage storage;
    RequestQueue queue;

    String ID;
    private SearchView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_learning_postlisting);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {

                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        ID = intent.getStringExtra("GRP_ID");
        System.out.println("GRP_ID in EL POSt ::::::" + ID);
        init();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                DisplayManagePost(s);
                return false;
            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        DisplayManagePost("");
    }

    private void DisplayManagePost(String s)
    {
        String query = null;
        try {
            query = URLEncoder.encode(s, "utf-8");
            System.out.println("query para::::::::::::::::::" + query);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("error::::" + e);
        }

        DialogUtils.showProgressDialog(E_Learning_PostList.this, "");
        String URLs = URl.Get_E_Learning_File_Master_Detail_Group_Wise_Employee_API + "&search=" + query + "&el_grp_file_grp_id=" + ID + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_E_Learning_File_Master_Detail_Group_Wise_Employee_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS get_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            final PostElPojo postElPojo = gson.fromJson(response, PostElPojo.class);

                            if (postElPojo != null && postElPojo.getTable().size() > 0)

                            {

                                E_LearningPostAdapter adapter;
                                adapter = new E_LearningPostAdapter(E_Learning_PostList.this, postElPojo, true, new E_LearningPostAdapter.managePostClick() {


                                    @Override
                                    public void manageEditClick(int postion)
                                    {

                                                Intent intent = new Intent(E_Learning_PostList.this,E_LearningAddPost.class);
                                                intent.putExtra("FILE_ID",postElPojo.getTable().get(postion).getEl_grp_file_id());
                                                intent.putExtra("FILE_GRP_ID",postElPojo.getTable().get(postion).getEl_grp_file_grp_id());
                                                intent.putExtra("DESCRIPTION",postElPojo.getTable().get(postion).getEl_grp_file_desc());
                                                intent.putExtra("DATE",postElPojo.getTable().get(postion).getEl_grp_file_date());
                                                intent.putExtra("GROUP",postElPojo.getTable().get(postion).getEl_grp_name());
                                                intent.putExtra("FILE_URL",postElPojo.getTable().get(postion).getEl_file_url());
                                                intent.putExtra("FILE_NAME",postElPojo.getTable().get(postion).getEl_file_url());

                                                intent.putExtra("IS_EDIT_POST",true);

                                                startActivity(intent);

                                    }

                                    @Override
                                    public void manageDeleteClick(final int postion) {
                                        DialogUtils.showDialog4YNo_(E_Learning_PostList.this, getResources().getString(R.string.app_name), "Are you Sure to delete ? ", "", new DialogUtils.DailogCallBackOkButtonClick() {
                                            @Override
                                            public void onDialogOkButtonClicked()
                                            {
                                                DeletePost(postElPojo.getTable().get(postion).getEl_grp_file_id());

                                            }
                                        }, new DialogUtils.DailogCallBackCancelButtonClick() {
                                            @Override
                                            public void onDialogCancelButtonClicked() {

                                            }
                                        });
                                    }
                                });

                                list.setAdapter(adapter);
                            } else {
                                list.setVisibility(View.GONE);
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }

    private void DeletePost(String ID) {


        DialogUtils.showProgressDialog(E_Learning_PostList.this, "");
        String URLs = URl.Delete_E_Learning_File_Master_API + "&el_grp_file_id=" + ID + "&el_grp_file_deleted_by=" + String.valueOf(storage.read("emp_id", 3)) + "&el_grp_file_deleted_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Delete_E_Learning_File_Master_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS Delete_E_Learning_File_Master_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                            if (deletePojo != null && deletePojo.getTable().size() > 0)

                            {

                                if (deletePojo.getTable().get(0).getError_code().contentEquals("1")) {
                                    DialogUtils.Show_Toast(E_Learning_PostList.this, "Post Delete Successfully");
                                    DisplayManagePost("");

                                }
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
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);
    }

    private void init() {
        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(this);
        this.search = (SearchView) findViewById(R.id.search);
        this.llelerning = (RelativeLayout) findViewById(R.id.ll_elerning);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.list = (ListView) findViewById(R.id.list);
        this.title = (CustomTextView) findViewById(R.id.title);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(E_Learning_PostList.this, E_LearningAddPost.class);
                i.putExtra("IS_EDIT_POST",false);
                startActivity(i);
            }
        });
    }
}
