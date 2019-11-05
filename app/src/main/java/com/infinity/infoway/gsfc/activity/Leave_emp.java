package com.infinity.infoway.gsfc.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.leave_data;
import com.infinity.infoway.gsfc.rest.ApiInterface;
import com.infinity.infoway.gsfc.rest.Api_client_leave;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leave_emp extends AppCompatActivity
{
    Context ctx;
    DataStorage storage;
    private ActionBar actionBar;
    ListView leavelist;
    LazyAdapter leaveadapter;
    Toolbar toolbar;
    String key_leave = "v8llRrQaDng=";
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_emp);

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
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        findviews();

        if (storage.CheckLogin("emp_id", Leave_emp.this))
        {
            final ProgressDialog progressDialog = new ProgressDialog(Leave_emp.this, R.style.MyTheme1);
            progressDialog.setCancelable(true);
            //progressDialog.setMessage("Please Wait");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            final ApiInterface apiService = Api_client_leave.getClient().create(ApiInterface.class);

            Call<ArrayList<leave_data>> call = apiService.get_leave(key_leave, String.valueOf(storage.read("emp_number", 3)), year);

            call.enqueue(new Callback<ArrayList<leave_data>>()
            {
                @Override
                public void onResponse(Call<ArrayList<leave_data>> call, Response<ArrayList<leave_data>> response)
                {
                    System.out.println("leave detail ::::: "+call.request());
                    progressDialog.dismiss();

                    if (response.isSuccessful())
                    {
                        System.out.println("leave detail ::::: "+call.request());
                        Log.d("attlist", String.valueOf(response.body().size()));
                        if (response.body().size() >= 1)
                        {
                            ArrayList<leave_data> leavedetail = response.body();
                            Log.d("leavedetail", String.valueOf(leavedetail));
                            leaveadapter = new LazyAdapter(leavedetail);
                            leavelist.setAdapter(leaveadapter);
                            //  Toast.makeText(Leave_emp.this,"leave",Toast.LENGTH_LONG).show();
                            // Toast.makeText(EmployeeLeave.this, "Record found", Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                            Toast.makeText(Leave_emp.this, "No Records found", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {
                        Toast.makeText(Leave_emp.this, "Please try again later", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<leave_data>> call, Throwable t)
                {
                    progressDialog.dismiss();
                    System.out.println("error in leave detail employee :::: "+t.getMessage());
                    t.printStackTrace();
                    // Log error here since request failed
                    // Log.e("employeeattendancerespo", t.toString());
                }
            });
        }
        else
            {
            Toast.makeText(Leave_emp.this, "No records found", Toast.LENGTH_LONG).show();
        }
    }

    public void findviews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        leavelist = (ListView) findViewById(R.id.listleave);

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

        private Activity activity;
        private LayoutInflater inflater;
        ArrayList<leave_data> leavedetail;

        LazyAdapter(ArrayList<leave_data> leavedetail)
        {
            this.leavedetail = leavedetail;
        }


        public int getCount()
        {
            return leavedetail.size();
        }

        public Object getItem(int position)
        {
            return position;
        }

        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View vi = convertView;
            if (convertView == null)

                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.view_leave,null);
            // final String childText = (String) getChild(position);
            //final  String childTextName=(String) getChildName(groupPosition,childPosition);
//            LinearLayout mainlayout = (LinearLayout) vi.findViewById(R.id.lllecture);
//            LinearLayout leaveayout = (LinearLayout) vi.findViewById(R.id.llfaculty);
            TextView leavetype = (TextView) vi.findViewById(R.id.tvleavetype);        // title
            TextView totalleave = (TextView) vi.findViewById(R.id.tvel_leave_days); // artist name
            TextView tvtakenleave = (TextView) vi.findViewById(R.id.tvtaken_leave);
            TextView tvbalance = (TextView) vi.findViewById(R.id.tvbalance);

            // leavetype.setText(header.get(position));
            leavetype.setText(leavedetail.get(position).getLeave_Name());
            totalleave.setText(leavedetail.get(position).getLeave_Day());
            tvtakenleave.setText(leavedetail.get(position).getLeave_Taken());
            tvbalance.setText(leavedetail.get(position).getLeave_Balance());
            // Setting all values in listview
//            ArrayList<AttendanceInoutTime> leavearray = leavedetail.get(position).getleave_array();
//            Log.d("leavearray", String.valueOf(leavearray));
//            if (leavearray.size() > 0) {
//                for (int i = 0; i < leavearray.size(); i++) {
//                    LayoutInflater vid = (LayoutInflater) ctx
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                    LinearLayout ll = new LinearLayout(ctx);
//                    LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                    LLParams.setMargins(0, 4, 0, 0);
//                    ll.setLayoutParams(LLParams);
//                    ll.setOrientation(LinearLayout.VERTICAL);
//                    ll.addView(vid.inflate(R.layout.view_leave_detail, null));
//                    ll.setId(i);
//
//                    mainlayout.addView(ll);
////                    mainlayout.addView(ll);
//                    LinearLayout llclassroom = (LinearLayout)ll.findViewById(R.id.llclassroom);
//
//                    TextView batch = (TextView) ll.findViewById(R.id.tvdivisionb);
//
//                    TextView tvfaculty = (TextView) ll.findViewById(R.id.tvfaculty);
//
//                    batch.setText(leavearray.get(i).getleavee());
//
//                    tvfaculty.setText(leavearray.get(i).getleave_date());
//                }
//            } else {
//                leaveayout.setVisibility(View.GONE);
//            }
            return vi;
        }
    }

}
