package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.ExpanLecturePlanDetailAdapter;
import com.infinity.infoway.agriculture.app.DataStorage;


import com.infinity.infoway.agriculture.model.Methods;
import com.infinity.infoway.agriculture.model.lp_sub_topic;
import com.infinity.infoway.agriculture.model.new_lect_plan;
import com.infinity.infoway.agriculture.model.newlectplandetail;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEmpLecturePlanDetail extends AppCompatActivity {

    int positiontopic;
    RecyclerView listView;
    // ListView listView;
    int subtopics;
    ArrayList<new_lect_plan> lectureplanlist;
    ArrayList<String> a1;

    ArrayList<String> lecturestatic;
    DataStorage storage;
    ArrayList<lp_sub_topic> sub;
    ExpanLecturePlanDetailAdapter expanLecturePlanDetailAdapter;
    ExpandableListView listview_expan;
    ArrayList<String> a12;
    Context ctx;
    TextView tvcoursename, tvsemester, tvdivision, tvsubjectname, tvLecture_Per_Week, refbook1;
    TextView t1;
    String name;
    ArrayList<lp_sub_topic> lec1;
    Toolbar toolbar;
    HashMap<String, List<String>> listDataChildName;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChild1;
    HashMap<String, List<String>> listDataChild2;
    HashMap<String, List<String>> listDataChild3;
    HashMap<String, List<String>> listDataChild4;
    HashMap<String, List<String>> listDataChild5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_emp_lecture_plan_detail);
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

        t1 = (TextView) findViewById(R.id.txtd);
        storage = new DataStorage("Login_Detail", NewEmpLecturePlanDetail.this);
        a12 = new ArrayList<String>();

        tvcoursename = (TextView) findViewById(R.id.tvcoursename);
        tvsemester = (TextView) findViewById(R.id.tvsemester);
        tvdivision = (TextView) findViewById(R.id.tvdivision);
        tvsubjectname = (TextView) findViewById(R.id.tvsubjectname);
        listview_expan = (ExpandableListView) findViewById(R.id.listview_expan);
        tvLecture_Per_Week = (TextView) findViewById(R.id.tvLecture_Per_Week);
        refbook1 = (TextView) findViewById(R.id.refbook);

        listView = (RecyclerView) findViewById(R.id.listview);
        a1 = new ArrayList<String>();
        a12 = new ArrayList<String>();
        lecturestatic = new ArrayList<>();

        Intent b = getIntent();
        positiontopic = b.getIntExtra("position", 0);

        ArrayList<? extends new_lect_plan> testing1 = b.getParcelableArrayListExtra("Name");

        String coursename = testing1.get(positiontopic).getCourse_Name();
        String semster = testing1.get(positiontopic).getSemester();
        String div = testing1.get(positiontopic).getdivision();
        String subname = testing1.get(positiontopic).getSubject();
        final String lecture = testing1.get(positiontopic).getLecture_Per_Week();
        String refbook = testing1.get(positiontopic).getref_book_name();
        String fac = testing1.get(positiontopic).getFaculty_name();
//        Log.d("fac",fac);

        tvcoursename.setText(coursename);
        tvsemester.setText(semster);
        tvdivision.setText(div);
        tvsubjectname.setText(subname);
        tvLecture_Per_Week.setText(lecture + " lectures per week");
        refbook1.setText(refbook);

        if (storage.CheckLogin("stud_id", NewEmpLecturePlanDetail.this))
        {
            LinearLayout faculty_student = (LinearLayout) findViewById(R.id.faculty_student);
            faculty_student.setVisibility(View.VISIBLE);

            TextView fac_name = (TextView) findViewById(R.id.fac_name);
            fac_name.setText(fac);

        }

        ArrayList<? extends newlectplandetail> testing = b.getParcelableArrayListExtra("Topic");

        //String name=testing.get(i).gettopic_Name();
        for (int i = 0; i < testing.size(); i++) {
            a1.add("Sr.no:- " + testing.get(i).getSrno());
            a1.add("Aid:- " + testing.get(i).getaid());
            a1.add("Topic:- " + testing.get(i).gettopic_Name());
        }

        listDataHeader = new ArrayList<>();
        listDataChildName = new HashMap<String, List<String>>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChild1 = new HashMap<String, List<String>>();
        listDataChild2 = new HashMap<String, List<String>>();
        listDataChild3 = new HashMap<String, List<String>>();
        listDataChild4 = new HashMap<String, List<String>>();
        listDataChild5 = new HashMap<String, List<String>>();

        final ProgressDialog progressDialog = new ProgressDialog(NewEmpLecturePlanDetail.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<new_lect_plan>> call;
        if (storage.CheckLogin("emp_id", NewEmpLecturePlanDetail.this)) {
            call = apiservice.get_new_lect_plan(String.valueOf(storage.read("emp_id", 3)));
        } else {
            call = apiservice.get_student_new_lession_plan(String.valueOf(storage.read("stud_id", 3)));
        }

        // Log.d("Emp_id", String.valueOf(storage.read("emp_id", 3)));
        call.enqueue(new Callback<ArrayList<new_lect_plan>>() {

            @Override
            public void onResponse(Call<ArrayList<new_lect_plan>> call, Response<ArrayList<new_lect_plan>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body().size() >= 1)
                    {


//
//                                lectureplanlist = response.body();
//                                Log.d("lectplandetail", String.valueOf(lectureplanlist));
//

                        ArrayList<newlectplandetail> lec1 = response.body().get(positiontopic).getdetails();
                        List<String> childlistname = new ArrayList<String>();
                        childlistname.add("Sr No.");
                        childlistname.add("Topic Name");
                        childlistname.add("Method");
                        childlistname.add("Aid");

                        for (int k = 0; k < lec1.size(); k++)
                        {

                            List<String> childlist = new ArrayList<String>();
                            List<String> childlist1 = new ArrayList<String>();
                            List<String> childlist2 = new ArrayList<String>();
                            List<String> childlist3 = new ArrayList<String>();
                            List<String> childlist4 = new ArrayList<String>();
                            List<String> childlist5 = new ArrayList<String>();


                            listDataHeader.add(lec1.get(k).gettopic_Name());

                            ArrayList<lp_sub_topic> sub_1 = lec1.get(k).getLp_sub_topic();

                            for (int j = 0; j < sub_1.size(); j++)
                            {

                                childlist.add(sub_1.get(j).getSub_sr_no());
                                childlist1.add(sub_1.get(j).getSub_topic_Name());
                                childlist2.add(sub_1.get(j).getSub_topic_method());
                                childlist3.add(sub_1.get(j).getSub_topic_aid());
//                                        childlist4.add(sub_1.get(j).getSub_topic_co());
//                                        childlist5.add(sub_1.get(j).getSub_topic_op());
                            }

//                                    listDataChildName.put(listDataHeader.get(k), childlistname);

                            listDataChild.put(listDataHeader.get(k), childlist);
                            listDataChild1.put(listDataHeader.get(k), childlist1);
                            listDataChild2.put(listDataHeader.get(k), childlist2);
                            listDataChild3.put(listDataHeader.get(k), childlist3);
                            listDataChildName.put(listDataHeader.get(k), childlistname);
//                                    listDataChild4.put(listDataHeader.get(k), childlist4);
//                                    listDataChild5.put(listDataHeader.get(k), childlist5);


                        }


                        expanLecturePlanDetailAdapter = new ExpanLecturePlanDetailAdapter(NewEmpLecturePlanDetail.this, listDataHeader, listDataChild, listDataChild1, listDataChild2, listDataChild3);
                        listview_expan.setAdapter(expanLecturePlanDetailAdapter);


                    } else {
                        Toast.makeText(NewEmpLecturePlanDetail.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //  Toast.makeText(Lectureplan_Detail.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<new_lect_plan>> call, Throwable t) {
                progressDialog.dismiss();
                // Log.e("Error1", t.toString());
                // Toast.makeText(Lectureplan_Detail.this, "Error in Response", Toast.LENGTH_LONG).show();
            }
        });


    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
