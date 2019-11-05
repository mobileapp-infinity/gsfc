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
import com.infinity.infoway.gsfc.adapter.E_LearningAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.DeletePojo;
import com.infinity.infoway.gsfc.model.ELGroupDisplayPojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class E_Learning_List extends AppCompatActivity {
    private Toolbar toolbar;
    private CustomTextView title;
    private ListView list;
    private FloatingActionButton fab;
    private RelativeLayout llelerning;


    DataStorage storage;
    RequestQueue queue;
    private SearchView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.e_learning_listing);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        Init();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Serach_API(s);
                return false;
            }
        });

        // E_LearninGroupDisplay();


        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(E_Learning_List.this, E_Learning.class);
                i.putExtra("IS_Edit", false);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        E_LearninGroupDisplay();
    }

    private void Serach_API(String Seacrch) {
        DialogUtils.showProgressDialog(E_Learning_List.this, "");
//        if (Seacrch.contentEquals("+"))
//        {
//            Seacrch.replace("+","%2B");
//        }
//        else
//        {
//            Seacrch =Seacrch;
//        }
        String query = null;
        try {
            query = URLEncoder.encode(Seacrch, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String URLs = URl.search_e_learning_group_API + "&search=" + query + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("search_e_learning_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS search_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 10)
                        {
                            Gson gson = new Gson();

                            final ELGroupDisplayPojo elGroupDisplayPojo = gson.fromJson(response, ELGroupDisplayPojo.class);

                            if (elGroupDisplayPojo != null && elGroupDisplayPojo.getTable().size() > 0)

                            {

                                E_LearningAdapter adapter;
                                adapter = new E_LearningAdapter(E_Learning_List.this, true, elGroupDisplayPojo, new E_LearningAdapter.managePostClick() {
                                    @Override
                                    public void managepostClick(int postion)
                                    {

                                        Intent i = new Intent(E_Learning_List.this, E_Learning_PostList.class);
                                        i.putExtra("GRP_ID", elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
                                        i.putExtra("POS", postion + "");
                                        startActivity(i);


                                    }

                                    @Override
                                    public void manageEditClick(int postion) {
                                        System.out.println("CALLED manageEditClick -----------------");

                                        Intent i = new Intent(E_Learning_List.this, E_Learning.class);
                                        i.putExtra("Grp_ID", elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
                                        i.putExtra("Grp_Name", elGroupDisplayPojo.getTable().get(postion).getEl_grp_name());
                                        i.putExtra("Grp_Type", elGroupDisplayPojo.getTable().get(postion).getEl_grp_type_id());
                                        i.putExtra("Grp_Desc", elGroupDisplayPojo.getTable().get(postion).getEl_grp_desc());
                                        i.putExtra("IS_Edit", true);
                                        startActivity(i);

                                    }

                                    @Override
                                    public void manageDeleteClick(final int postion) {
                                        DialogUtils.showDialog4YNo_(E_Learning_List.this, getResources().getString(R.string.app_name), "Are you Sure to delete ? ", "", new DialogUtils.DailogCallBackOkButtonClick() {
                                            @Override
                                            public void onDialogOkButtonClicked() {
                                                System.out.println("caleedd delete :::: " + elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
                                                Delete_Group(elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());

                                            }
                                        }, new DialogUtils.DailogCallBackCancelButtonClick() {
                                            @Override
                                            public void onDialogCancelButtonClicked() {

                                            }
                                        });
                                    }
                                });

                                list.setAdapter(adapter);
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

    public void Delete_Group(String ID)
    {
        DialogUtils.showProgressDialog(E_Learning_List.this, "");
        String URLs = URl.delete_e_learning_group_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "&el_grp_id=" + ID + "&deleted_by=" + String.valueOf(storage.read("emp_id", 3)) + "&deleted_ip=" + "1" + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("delete_e_learning_group_API calls    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        response = response + "";
//                        response = "{\"Enroll_Group\":" + response + "}";
                        System.out.println("THIS IS delete_e_learning_group_API RESPONSE      " + response + "");


                        if (response.length() > 5) {
                            Gson gson = new Gson();

                            DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                            if (deletePojo != null && deletePojo.getTable().size() > 0)

                            {

                                if (deletePojo.getTable().get(0).getError_code().contentEquals("1"))
                                {
                                    DialogUtils.Show_Toast(E_Learning_List.this, "Group Delete Successfully");
                                    E_LearninGroupDisplay();

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

    private void DeleteGrouupAPI() {
    }


    private void Init() {
        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(this);
        this.search = (SearchView) findViewById(R.id.search);
        this.llelerning = (RelativeLayout) findViewById(R.id.ll_elerning);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.list = (ListView) findViewById(R.id.list);
        this.title = (CustomTextView) findViewById(R.id.title);
    }

    private void E_LearninGroupDisplay() {


        DialogUtils.showProgressDialog(E_Learning_List.this, "");
        String URLs = URl.get_e_learning_group_API + "&institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "&year_id=" + String.valueOf(storage.read("emp_year_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("get_e_learning_group_API calls    " + URLs + "");
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

                            final ELGroupDisplayPojo elGroupDisplayPojo = gson.fromJson(response, ELGroupDisplayPojo.class);

                            if (elGroupDisplayPojo != null && elGroupDisplayPojo.getTable().size() > 0)

                            {

                                E_LearningAdapter adapter;
                                adapter = new E_LearningAdapter(E_Learning_List.this, true, elGroupDisplayPojo, new E_LearningAdapter.managePostClick() {
                                    @Override
                                    public void managepostClick(int postion) {

                                        Intent i = new Intent(E_Learning_List.this, E_Learning_PostList.class);
                                        i.putExtra("GRP_ID", elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
                                        i.putExtra("POS", postion + "");
                                        startActivity(i);


                                    }

                                    @Override
                                    public void manageEditClick(int postion) {
                                        System.out.println("CALLED manageEditClick -----------------");

                                        Intent i = new Intent(E_Learning_List.this, E_Learning.class);
                                        i.putExtra("Grp_ID", elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
                                        i.putExtra("Grp_Name", elGroupDisplayPojo.getTable().get(postion).getEl_grp_name());
                                        i.putExtra("Grp_Type", elGroupDisplayPojo.getTable().get(postion).getEl_grp_type_id());
                                        i.putExtra("Grp_Desc", elGroupDisplayPojo.getTable().get(postion).getEl_grp_desc());
                                        i.putExtra("IS_Edit", true);

                                        startActivity(i);

                                    }

                                    @Override
                                    public void manageDeleteClick(final int postion) {
                                        DialogUtils.showDialog4YNo_(E_Learning_List.this, getResources().getString(R.string.app_name), "Are you Sure to delete ? ", "", new DialogUtils.DailogCallBackOkButtonClick() {
                                            @Override
                                            public void onDialogOkButtonClicked() {
                                                Delete_Group(elGroupDisplayPojo.getTable().get(postion).getEl_grp_id());
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
}
