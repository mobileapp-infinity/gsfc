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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.NotificationResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Activity extends AppCompatActivity {
    private ListView listView;

    //private ListAdapter adapter;
    TextView tvtitle, tvdescription, tvdate, title;
    Context ctx;
    DataStorage storage;
    CustomAdapter adapter;
    Toolbar toolbar;
    String type = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
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

        findviews();

        final ProgressDialog progressDialog = new ProgressDialog(Notification_Activity.this, R.style.MyTheme1);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<NotificationResponse>> call;

       /* if (storage.CheckLogin("stud_id", Notification_Activity.this))
        {
            call = apiInterface.getNotification("2");
        }
        else
            {
                call = apiInterface.getNotification("1");
        }


*/
        /*if (String.valueOf(storage.read("intitute_id", 3)) == null || String.valueOf(storage.read("intitute_id", 3)).equals(""))
        {
            storage.clear();
            Intent intent = new Intent(Notification_Activity.this, Login.class);
            startActivity(intent);
            finish();
        }
        else {*/


        if (storage.CheckLogin("stud_id", Notification_Activity.this))
        {
            call = apiInterface.get_stud_emp_notification("2", String.valueOf(storage.read("ac_id", 3)), String.valueOf(storage.read("dm_id", 3)), String.valueOf(storage.read("course_id", 3)), String.valueOf(storage.read("sm_id", 3)), String.valueOf(storage.read("intitute_id", 3)));
        }
        else
            {
            call = apiInterface.get_stud_emp_notification("1", String.valueOf(storage.read("emp_main_school_id", 3)), String.valueOf(storage.read("ac_code", 3)), "0", "0", String.valueOf(storage.read("intitute_id", 3)));
        }

        call.enqueue(new Callback<ArrayList<NotificationResponse>>()
        {
            @Override
            public void onResponse(Call<ArrayList<NotificationResponse>> call, Response<ArrayList<NotificationResponse>> response)
            {
                //Log.d("Er",response.toString());

                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().size() >= 1)
                    {
                        ArrayList<NotificationResponse> a1 = response.body();
                        adapter = new CustomAdapter(a1);
                        listView.setAdapter(adapter);
                        //   Log.d("Count", String.valueOf(listView.getAdapter().getCount()));
                        String num = String.valueOf(listView.getAdapter().getCount());
                        storage.write("Number", num);
                        storage.write("seen", "1");

                    }
                    else
                        {
                        Toast.makeText(Notification_Activity.this, "No records found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    progressDialog.dismiss();
                    Toast.makeText(Notification_Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<NotificationResponse>> call, Throwable t)
            {

                progressDialog.dismiss();

                //Log.e("Error",t.toString());
                Toast.makeText(Notification_Activity.this, "Please try again later", Toast.LENGTH_LONG).show();

            }
        });
        //  }
    }

    public void findviews() {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        listView = (ListView) findViewById(R.id.notificationlist);
        title = (TextView) findViewById(R.id.title);

        if (type.equals("news")) {
            title.setText("News");
        } else {
            title.setText("Notification");
        }


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("message")) {
            String message = intent.getStringExtra("message");
            Log.e("message", "" + message);
        }
    }

    public class CustomAdapter extends BaseAdapter {
        ArrayList<NotificationResponse> a1;
        LayoutInflater inflater;

        public CustomAdapter(ArrayList<NotificationResponse> a1) {

            this.a1 = a1;
        }

        public int getCount() {
            return a1.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)

                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.raw_notification, null);

            tvtitle = (TextView) vi.findViewById(R.id.txttopic);
            tvdescription = (TextView) vi.findViewById(R.id.tvnotificationdescription);
            tvdate = (TextView) vi.findViewById(R.id.txtdate);
            tvdate.setText(a1.get(position).getNotifdate());
            tvtitle.setText(a1.get(position).getNotifhead());
            tvdescription.setText(a1.get(position).getNotifmsg());

            return vi;
        }
    }
}
