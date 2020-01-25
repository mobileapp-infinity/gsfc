package com.infinity.infoway.gsfc.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import com.infinity.infoway.gsfc.adapter.ViewInternShipAdapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.PunchPojo;
import com.infinity.infoway.gsfc.model.ViewInternShipPojo;

import java.util.ArrayList;

public class ViewInternshipWorkReportActivity extends AppCompatActivity implements View.OnClickListener
{

    protected CustomTextView title;
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected ListView listEwr;
    protected RelativeLayout llElerning;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    public static final int ALL_PERMISSIONS_RESULT = 101;

    CustomBoldTextView tv_no_records_leave;
    RequestQueue queue;
    DataStorage storage;
    SwipeRefreshLayout swipe_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_view_internship_work_report);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        initView();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);


        InternShipWorkreportAPICall();



    }

    @Override
    protected void onResume() {
        super.onResume();

        InternShipWorkreportAPICall();
    }

    private void InternShipWorkreportAPICall()
    {
//        DialogUtils.showProgressDialog(ViewInternshipWorkReportActivity.this, "");

        final ProgressDialog progressDialog = new ProgressDialog(ViewInternshipWorkReportActivity.this, R.style.MyTheme1);
        progressDialog.setCancelable(true);
       // progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        String URLs = URl.Get_grd_work_report_API + "&student_id=" + String.valueOf(storage.read("stud_id", 3))  + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_grd_work_report_API calls    " + URLs + "");

        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
//                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Data\":" + response + "}";

                        System.out.println("THIS IS Get_grd_work_report_API RESPONSE    " + response + "");


                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            ViewInternShipPojo  viewInternShipPojo = gson.fromJson(response, ViewInternShipPojo.class);

                            if (viewInternShipPojo != null && viewInternShipPojo.getData().size() > 0)
                            {
                                tv_no_records_leave.setVisibility(View.GONE);

                                ViewInternShipAdapter viewInternShipAdapter = new ViewInternShipAdapter(ViewInternshipWorkReportActivity.this,viewInternShipPojo,ViewInternshipWorkReportActivity.this);

                                listEwr.setAdapter(viewInternShipAdapter);


                            }
                            else
                                {
                                tv_no_records_leave.setVisibility(View.VISIBLE);
                              //  DialogUtils.Show_Toast(ViewInternshipWorkReportActivity.this,"No Records Found");
                            }


                        }
                        else
                            {
                                tv_no_records_leave.setVisibility(View.VISIBLE);
                             // DialogUtils.Show_Toast(ViewInternshipWorkReportActivity.this,"No Records Found");
                        }


                    }
                }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
//                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);


    }

    private boolean canAskPermission()
    {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean hasPermission(String permission)
    {
        if (canAskPermission())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted)
    {
        ArrayList result = new ArrayList();

        for (String perm : wanted)
        {
            if (!hasPermission(perm))
            {
                result.add(perm);
            }
        }

        return result;
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.fab)
        {
            Intent intent = new Intent(ViewInternshipWorkReportActivity.this, InternshipWorkReport.class);
            startActivity(intent);


           /* GPSTracker gpsTracker = new GPSTracker(ViewInternshipWorkReportActivity.this);


            if (!gpsTracker.canGetLocation())
            {
                gpsTracker.showSettingsAlert();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                    if (permissionsToRequest.size() > 0)
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
                String Latitude = String.valueOf(gpsTracker.getLatitude());
                System.out.println("Latitude is :::" + Latitude);

                String Longitude = String.valueOf(gpsTracker.getLongitude());
                System.out.println("Longitude is :::" + Longitude);
            }*/


        }
    }

    public void shuffle(){
        InternShipWorkreportAPICall();
    }
    private void initView()
    {
        queue = Volley.newRequestQueue(ViewInternshipWorkReportActivity.this);
        storage = new DataStorage("Login_Detail",ViewInternshipWorkReportActivity.this);
        swipe_refresh=  (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                shuffle();
                swipe_refresh.setRefreshing(false);
            }
        });
        tv_no_records_leave =(CustomBoldTextView)findViewById(R.id.tv_no_records_leave);
        title = (CustomTextView) findViewById(R.id.title);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(ViewInternshipWorkReportActivity.this);
        listEwr = (ListView) findViewById(R.id.list_ewr);
        llElerning = (RelativeLayout) findViewById(R.id.ll_elerning);
    }
}
