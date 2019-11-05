package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.EmployeeLEcturePlanAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.lp_sub_topic;
import com.infinity.infoway.gsfc.model.new_lect_plan;
import com.infinity.infoway.gsfc.model.newlectplandetail;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;
import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEmpLectPlan extends AppCompatActivity
{
    EmployeeLEcturePlanAdapter listAdapter;
    ExpandableListView expListView;
    ListView listview;
    LazyAdapter ladapter;
    ArrayList<new_lect_plan> lectureplanlist;
    HashMap<Integer, ArrayList<newlectplandetail>> listDataChild;
    ArrayList<String> a1;
    DataStorage storage;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_emp_lect_plan);

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
        storage = new DataStorage("Login_Detail", NewEmpLectPlan.this);
        listview = (ListView) findViewById(R.id.list_item1);
        expListView = (ExpandableListView)findViewById(R.id.lvExp);
        lectureplanlist = new ArrayList<>();
        listDataChild = new HashMap<>();
        a1 = new ArrayList<String>();


        final ProgressDialog dialog = new ProgressDialog(NewEmpLectPlan.this, R.style.MyTheme1);
        dialog.setCancelable(true);
        dialog.setMessage("Please Wait");
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();

        ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<new_lect_plan>> call;
        if (storage.CheckLogin("emp_id", NewEmpLectPlan.this))
        {
            call = apiservice.get_new_lect_plan(String.valueOf(storage.read("emp_id", 3)));
//            call =apiservice.getEmpLecturePlan(String.valueOf(storage.read("emp_id",3)));
        }
        else
        {
            call = apiservice.get_student_new_lession_plan(String.valueOf(storage.read("stud_id", 3)));
        }

        //Log.d("Emp_id",String.valueOf(storage.read("emp_id",3)));
        call.enqueue(new Callback<ArrayList<new_lect_plan>>()
        {

            @Override
            public void onResponse(Call<ArrayList<new_lect_plan>> call, Response<ArrayList<new_lect_plan>> response)
            {
                dialog.dismiss();
                if (response.isSuccessful())
                {
                    lectureplanlist = response.body();

                    if (response.body().size() >= 1)
                    {

                        for (int i = 0; i < response.body().size(); i++)
                        {

                            ArrayList<newlectplandetail> topics = new ArrayList<newlectplandetail>();
                            ArrayList<lp_sub_topic> topics1 = new ArrayList<lp_sub_topic>();
                            topics = response.body().get(i).getdetails();
                            listDataChild.put(i, topics); // Header,
                            ladapter = new LazyAdapter(lectureplanlist);
                            listview.setAdapter(ladapter);

                        }
                    }
                    else
                    {
                        Toast.makeText(NewEmpLectPlan.this, "No Records Found", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<ArrayList<new_lect_plan>> call, Throwable t) {
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
        ArrayList<new_lect_plan> lessiondetail1;
        Bundle bundle;

        LazyAdapter(ArrayList<new_lect_plan> new_lect_plan)
        {
            this.lessiondetail1 = new_lect_plan;
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

//            vi = inflater.inflate(R.layout.view_lectureplan_employee, null, false);
            vi = inflater.inflate(R.layout.dummuuyy, null, false);


            TextView tvcoursename = (TextView) vi.findViewById(R.id.tvcoursename);
            TextView tvsemester = (TextView) vi.findViewById(R.id.tvsemester);
            TextView tvdivision = (TextView) vi.findViewById(R.id.tvdivision);
            TextView tvsubjectname = (TextView) vi.findViewById(R.id.tvsubjectname);
            TextView tvLecture_Per_Week = (TextView) vi.findViewById(R.id.tvLecture_Per_Week);
            LinearLayout linear1 = (LinearLayout) vi.findViewById(R.id.linear1);
            //  TextView tvreferancebook = (TextView) vi.findViewById(R.id.tvreferancebook);
            LinearLayout lltopics = (LinearLayout) vi.findViewById(R.id.lltopics);

            tvcoursename.setText(lessiondetail1.get(position).getCourse_Name());
            tvsemester.setText(lessiondetail1.get(position).getSemester() + "(" + lessiondetail1.get(position).getdivision() + ")");
            tvdivision.setText(lessiondetail1.get(position).getdivision());
            tvsubjectname.setText(lessiondetail1.get(position).getSubject());


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    ArrayList<newlectplandetail> topics = lessiondetail1.get(position).getdetails();
                    Intent intent = new Intent(NewEmpLectPlan.this, NewEmpLecturePlanDetail.class);
                    intent.putParcelableArrayListExtra("Name", lessiondetail1);
                    intent.putParcelableArrayListExtra("Topic", topics);
                    intent.putExtra("position", position);
                    startActivity(intent);

                }
            });



            tvLecture_Per_Week.setText(lessiondetail1.get(position).getLecture_Per_Week() + " Lectures per week");
            //    tvreferancebook.setText(lessiondetail.get(position).getref_book_name());
            return vi;

        }
    }
}
