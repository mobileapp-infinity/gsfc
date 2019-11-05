package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.LeaveResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeLeave extends AppCompatActivity
{
    Context ctx;
    DataStorage storage;
    private ActionBar actionBar;
    ListView leavelist;
    //    LazyAdapter leaveadapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_leave);

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

        findviews();

        if (storage.CheckLogin("emp_id", EmployeeLeave.this))
        {
            final ProgressDialog progressDialog = new ProgressDialog(EmployeeLeave.this, R.style.MyTheme1);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Please Wait");
            progressDialog.setProgressStyle(android.R.style .Widget_ProgressBar_Small);
            progressDialog.show();

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ArrayList<LeaveResponse>>call = apiService.getemployeeleave(String.valueOf(storage.read("emp_id", 3)), "1");

            call.enqueue(new Callback<ArrayList<LeaveResponse>>()
            {
                @Override
                public void onResponse(Call<ArrayList<LeaveResponse>>call, Response<ArrayList<LeaveResponse>> response)
                {
                    progressDialog.dismiss();

                    if (response.isSuccessful())
                    {
                        // Log.d("attlist", String.valueOf(response.body().size()));
                        if (response.body().size() >= 1)
                        {
                           ArrayList<LeaveResponse> leavedetail = response.body();
//                            leaveadapter = new LazyAdapter(leavedetail);
//                            leavelist.setAdapter(leaveadapter);
                            // Toast.makeText(EmployeeLeave.this, "Record found", Toast.LENGTH_LONG).show();

                        }
                        else
                            {
                            Toast.makeText(EmployeeLeave.this, "No Record Found", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {
                        //  Toast.makeText(EmployeeLeave.this, "Error in Response", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LeaveResponse>> call, Throwable t)
                {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    // Log.e("employeeattendancerespo", t.toString());
                }
            });
        }
        else
            {
            Toast.makeText(EmployeeLeave.this, "No records found", Toast.LENGTH_LONG).show();
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

//    public class LazyAdapter extends BaseAdapter {
//
//        private Activity activity;
//        private LayoutInflater inflater;
//        ArrayList<LeaveResponse> leavedetail;
//
//        LazyAdapter(ArrayList<LeaveResponse> leavedetail) {
//            this.leavedetail = leavedetail;
//        }
//
//
//        public int getCount() {
//            return leavedetail.size();
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroupElerning parent) {
//            View vi = convertView;
//            if (convertView == null)
//
//                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            vi = inflater.inflate(R.layout.view_leave, null);
//            // final String childText = (String) getChild(position);
//            //final  String childTextName=(String) getChildName(groupPosition,childPosition);
//            LinearLayout mainlayout = (LinearLayout) vi.findViewById(R.id.lllecture);
//            LinearLayout leaveayout = (LinearLayout) vi.findViewById(R.id.llfaculty);
//            TextView leavetype = (TextView) vi.findViewById(R.id.tvleavetype);        // title
//            TextView totalleave = (TextView) vi.findViewById(R.id.tvel_leave_days); // artist name
//            TextView tvtakenleave = (TextView) vi.findViewById(R.id.tvtaken_leave);
//            TextView tvbalance = (TextView) vi.findViewById(R.id.tvbalance);
//
//            // leavetype.setText(header.get(position));
//            leavetype.setText(leavedetail.get(position).getleave_type_name());
//            totalleave.setText(leavedetail.get(position).getel_leave_days());
//            tvtakenleave.setText(leavedetail.get(position).getaken_leave());
//            tvbalance.setText(leavedetail.get(position).getbalance());
//            // Setting all values in listview
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
//            return vi;
//        }
//    }
}
