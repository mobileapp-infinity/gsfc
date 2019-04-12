package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.EmployeeLEcturePlanAdapter;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.LecturePlanEmployee;
import com.infinity.infoway.agriculture.model.LectureplanEmployeeSubTopics;
import com.infinity.infoway.agriculture.model.Methods;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lectureplan extends AppCompatActivity
{
    EmployeeLEcturePlanAdapter listAdapter;
    ExpandableListView expListView;
    ListView listview;
    LazyAdapter ladapter;
    ArrayList<LecturePlanEmployee> lectureplanlist;
    HashMap<Integer, ArrayList<LectureplanEmployeeSubTopics>> listDataChild;
    ArrayList<String> a1;
    DataStorage storage;
    Toolbar toolbar;
    //private TransparentLoginDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__lecture_plan);

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
        storage = new DataStorage("Login_Detail", Lectureplan.this);
        listview = (ListView) findViewById(R.id.list_item1);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        lectureplanlist = new ArrayList<>();
        listDataChild = new HashMap<>();
        a1 = new ArrayList<String>();


        final ProgressDialog dialog = new ProgressDialog(Lectureplan.this, R.style.MyTheme1);
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();

        ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<LecturePlanEmployee>> call;
        if (storage.CheckLogin("emp_id", Lectureplan.this))
        {
            call = apiservice.getEmpLecturePlan(String.valueOf(storage.read("emp_id", 3)));
//            call =apiservice.getEmpLecturePlan(String.valueOf(storage.read("emp_id",3)));
        } else {
            call = apiservice.getStudentLecturePlan(String.valueOf(storage.read("stud_id", 3)));
        }
        //Log.d("Emp_id",String.valueOf(storage.read("emp_id",3)));
        call.enqueue(new Callback<ArrayList<LecturePlanEmployee>>()
        {

            @Override
            public void onResponse(Call<ArrayList<LecturePlanEmployee>> call, Response<ArrayList<LecturePlanEmployee>> response) {
                dialog.dismiss();
                if (response.isSuccessful())
                {
                    lectureplanlist = response.body();
                    if (response.body().size() >= 1)
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {

                            ArrayList<LectureplanEmployeeSubTopics> topics = new ArrayList<LectureplanEmployeeSubTopics>();
                            ArrayList<Methods> topics1 = new ArrayList<Methods>();
                            topics = response.body().get(i).getdetails();
                            listDataChild.put(i, topics); // Header,
                            ladapter = new  LazyAdapter(lectureplanlist);
                            listview.setAdapter(ladapter);

                        }
                    }
                    else
                        {
                        Toast.makeText(Lectureplan.this, "No Records Found", Toast.LENGTH_LONG).show();
                    }

                    /*listAdapter = new EmployeeLEcturePlanAdapter(Lectureplan.this, lectureplanlist,  listDataChild);
                    expListView.setAdapter(listAdapter);*/
                }
                else
                    {
                    // Toast.makeText(Lectureplan.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LecturePlanEmployee>> call, Throwable t)
            {
                // Log.e("Error1", t.toString());
                // Toast.makeText(Lectureplan.this, "Error in Response", Toast.LENGTH_LONG).show();
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public class LazyAdapter extends BaseAdapter
    {

        private LayoutInflater inflater;
        ArrayList<LecturePlanEmployee> lessiondetail1;
        Bundle bundle;

        LazyAdapter(ArrayList<LecturePlanEmployee> lessiondetail)
        {
            this.lessiondetail1 = lessiondetail;
        }

        @Override
        public int getCount()
        {
            return lessiondetail1.size();
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            View vi = convertView;

            if (convertView == null)
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(R.layout.view_lectureplan_employee, null, false);


            TextView tvcoursename = (TextView) vi.findViewById(R.id.tvcoursename);
            TextView tvsemester = (TextView) vi.findViewById(R.id.tvsemester);
            TextView tvdivision = (TextView) vi.findViewById(R.id.tvdivision);
            TextView tvsubjectname = (TextView) vi.findViewById(R.id.tvsubjectname);
            TextView tvLecture_Per_Week = (TextView) vi.findViewById(R.id.tvLecture_Per_Week);
            LinearLayout linear1 = (LinearLayout) vi.findViewById(R.id.linear1);
            //  TextView tvreferancebook = (TextView) vi.findViewById(R.id.tvreferancebook);
            LinearLayout lltopics = (LinearLayout) vi.findViewById(R.id.lltopics);

            if (storage.CheckLogin("emp_id", Lectureplan.this))
            {
                tvcoursename.setText(lessiondetail1.get(position).getCourse_Name());
                tvsemester.setText(lessiondetail1.get(position).getSemester() + "(" + lessiondetail1.get(position).getdivision() + ")");
                tvdivision.setText(lessiondetail1.get(position).getdivision());
                tvsubjectname.setText(lessiondetail1.get(position).getSubject());

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        ArrayList<LectureplanEmployeeSubTopics> topics = lessiondetail1.get(position).getdetails();
                        Intent intent = new Intent(Lectureplan.this, Lectureplan_Detail.class);
                        intent.putParcelableArrayListExtra("Name", lessiondetail1);
                        intent.putParcelableArrayListExtra("Topic", topics);
                        intent.putExtra("position", position);
                        startActivity(intent);

                    }
                });
            }
            else
                {
                tvcoursename.setText(lessiondetail1.get(position).getSubject_name());
                tvsemester.setVisibility(View.GONE);
                tvdivision.setVisibility(View.GONE);
                tvsubjectname.setText(lessiondetail1.get(position).getfaculty_name());
                lltopics.setVisibility(View.VISIBLE);

                final ArrayList<LectureplanEmployeeSubTopics> topics = lessiondetail1.get(position).getdetails();
                Log.d("lectureplan", String.valueOf(topics));


                    if (!(lessiondetail1.get(position).getdetails()==null))
                    {

                        for (int i = 0; i< lessiondetail1.get(position).getdetails().size(); i++)
                        {
                            TextView tvtopics = new TextView(Lectureplan.this);
                            tvtopics.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            tvtopics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_forward, 0, 0, 0);
                            // tvtopics.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_arrow_forward),null,null,null);
                            tvtopics.setText(topics.get(i).gettopic_name());
                            tvtopics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            tvtopics.setPadding(5, 5, 0, 0);
                            lltopics.addView(tvtopics);

                        }

                    }
                    else
                        {

                        lltopics.setVisibility(View.GONE);
                    }





            }

            tvLecture_Per_Week.setText(lessiondetail1.get(position).getLecture_Per_Week() + " Lectures per week");
            //    tvreferancebook.setText(lessiondetail.get(position).getref_book_name());
            return vi;

        }
    }
}
