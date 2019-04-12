package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.RemainingAttResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Remaining_Attendance extends AppCompatActivity
{
    ListView listView;
    DataStorage storage;
    LazyAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining__attendance);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
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

        listView=(ListView)findViewById(R.id.listview);
        storage=new DataStorage("Login_Detail",Remaining_Attendance.this);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Map<String,String> maps;
        maps = new HashMap<String,String>();
        maps.put("Emp_id",String.valueOf(storage.read("emp_id",3)));

        final ProgressDialog progressDialog = new ProgressDialog(Remaining_Attendance.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

       // Log.d("Emp_Id",String.valueOf(storage.read("emp_id",3)));
        Call<ArrayList<RemainingAttResponse>> call=apiInterface.getRemainingattendance(maps);
        call.enqueue(new Callback<ArrayList<RemainingAttResponse>>()
        {
            @Override
            public void onResponse(Call<ArrayList<RemainingAttResponse>> call, Response<ArrayList<RemainingAttResponse>> response)
            {
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    if (response.body().size() >= 1)
                    {
                        ArrayList<RemainingAttResponse> att = response.body();
                        Log.d("att", String.valueOf(att));
                        adapter = new LazyAdapter(att);
                        listView.setAdapter(adapter);

                        // Toast.makeText(EmployeeLeave.this, "Record found", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Remaining_Attendance.this, "No Records Found", Toast.LENGTH_LONG).show();


                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RemainingAttResponse>> call, Throwable t)
            {
                progressDialog.dismiss();
            }
        });
    }

    public class LazyAdapter extends BaseAdapter
    {

        LayoutInflater inflater;
        ArrayList<RemainingAttResponse> att;

         LazyAdapter(ArrayList<RemainingAttResponse> att)
         {
             this.att=att;
         }

        @Override
        public int getCount()
        {
            return att.size();
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View vi=convertView;
            if(convertView==null)
                inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.view_rem_attendance, null);

            TextView txtdate=(TextView)vi.findViewById(R.id.txtdate);
            TextView txtcoursename=(TextView)vi.findViewById(R.id.txtcoursename);
            TextView txtsemname=(TextView)vi.findViewById(R.id.txtsemname);
            TextView txtdivision=(TextView)vi.findViewById(R.id.txtDivision);
            TextView txtlecno=(TextView)vi.findViewById(R.id.txtlecno);
            txtdate.setText(att.get(position).getDate());
            txtcoursename.setText(att.get(position).getCourse());
            txtsemname.setText(att.get(position).getSemname());
            txtdivision.setText(att.get(position).getDivision());
            txtlecno.setText(att.get(position).getLecno());
            return vi;
        }
    }
}
