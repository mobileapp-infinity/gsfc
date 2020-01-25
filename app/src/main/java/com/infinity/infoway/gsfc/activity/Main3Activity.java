package com.infinity.infoway.gsfc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;


import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.CommonCls.MySharedPrefereces;
import com.infinity.infoway.gsfc.HrAppAPI.URLS;
import com.infinity.infoway.gsfc.HrAppActivities.LoginActivity;
import com.infinity.infoway.gsfc.HrAppPojo.LoginPojo;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.Pageradapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.Login_Master;
import com.infinity.infoway.gsfc.model.FcmResponse;
import com.infinity.infoway.gsfc.model.LoginResponse;
import com.infinity.infoway.gsfc.model.NotificationResponse;
import com.infinity.infoway.gsfc.model.ProfileResponse;
import com.infinity.infoway.gsfc.model.PunchInPunchOutpojo;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;
import com.infinity.infoway.gsfc.rest.Api_Client;
import com.infinity.infoway.gsfc.service.Background_Service;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    private static final int MENU_ADD = 0;
    ImageView assignment_img, iv_internship_work_report, receipt_img, pending_fees_img, img_home_work, img_dashboard, imgtimetable, imgattendance, imgfees, imgnews, imgfeedback, imgsyllabus, iv_rem_att, imglessionplan, imgexam_tt, img_feecircular, imgprofile, imgprofile_emp, imgattendance_emp, iv_leave_app_emp, imgnews_emp, imgtimetable_emp, img_Moreapp, img_Moreapp_emp;
    TextView nav_profile, nav_change_psw, nav_timetable, nav_attendance, nav_pending_fees, nav_fees, nav_news, nav_feedback, nav_syllabus, nav_lessionplan, nav_share, nav_logout, nav_rec, nav_fee_circular, nav_result, nav_activity, nav_homewrork, nav_assignment, nav_exam_time_table, nav_memberno, nav_version_name, nav_placement, nav_Leave_app, nav_elrning, nav_more, nav_lleave;
    Context ctx;
    TextView nav_lessionplan_a, nav_feedback_a, nav_news_a, nav_share_a;
    ImageView notification_dashboard, img_result, img_elerning, img_placement, img_leave_app;
    DataStorage storage;
    NavigationView mNavigationView;
    CircleImageView imgmenuprofile;
    LinearLayout five_ll;
    TextView txtmenuname, txtmenucourse, tvActionNotification, textfee, txtnews, txtfeedback, emp_nav_el;
    LinearLayout studentlayout, ll_view_more, ll_views_hide;
    ListPopupWindow listPopupWindow;
    ArrayList<String> products = new ArrayList<String>();
    String refreshedToken;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    LinearLayout llactionbar;
    private SlidingUpPanelLayout mLayout;
    ImageView image;
    Toolbar toolbar;
    float slideoffset = 1;
    TextView syl;
    TextView txt, nav_punch_in_out, nav_internship_work_rpt;
    View student_layout;
    View Emp_layout;
    Login_Master login_master;

    private ImageView ivatt;

    private ImageView ivplacement, iv_att;
    private TextView textattendence;
    private LinearLayout layout1;
    private ImageView ivlessionplan;

    private TextView textView6;
    private ImageView ivassignment;
    private ImageView imgnewannouncement;
    private TextView txterning;
    private ImageView imgplacement;
    private TextView txtplacement;
    private ImageView imgleaveapp;
    private LinearLayout newiconll;
    private LinearLayout llviewmore;
    private TextView txtreceipt;

    private TextView txt1;
    private TextView emp_nav_asnmnt;
    private ImageView imgresult;
    private ImageView assignmentimg;
    private ImageView iv_el_emp;
    private LinearLayout feeslayout;

    ImageView img_assignment_emp;

    private ImageView imgdashboard, iv_payroll;

    int final_count_stud_ann_emp, final_count_emp_ann_emp, final_emp_ann_count;

    private ImageView pendingfeesimg;
    private ImageView ivmidexamtt;
    private ImageView imgfeecircular;
    private LinearLayout fivell;
    private LinearLayout llviewshide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        try {
            Intent intent = new Intent();

            String manufacturer = android.os.Build.MANUFACTURER;

            student_layout = findViewById(R.id.student_layout);
            Emp_layout = findViewById(R.id.employee1_layout);
            Emp_layout.setVisibility(View.GONE);


            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.iqoo.secure.MainActivity", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
            }

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            if (list.size() > 0) {
                startActivity(intent);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
        }
//        if (String.valueOf(storage.read("is_director", 3)).equals(1))
//        {
//            Intent it = new Intent(Main3Activity.this, DirectivePageActivity.class);
//            startActivity(it);
//        }
//        else
//        {


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("message")) {
            String message = intent.getStringExtra("message");
            Log.e("message", "" + message);
        }

        llactionbar = (LinearLayout) findViewById(R.id.llactionbar);
        llactionbar.setOnClickListener(this);

        final Intent in = new Intent(Main3Activity.this, Background_Service.class);
        startService(in);

       /* i1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Main3Activity.this, MainActivity.class));
            }
        });*/

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setDragView(llactionbar);
        // mLayout.setDragView(i1);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                slideoffset = slideOffset;
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(final View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        if (getIntent().getBooleanExtra("Exit me", false)) {
            finish();
            System.exit(0);

            return; // add this to prevent from doing unnecessary stuffs
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(DataStorage.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(DataStorage.TOPIC_GLOBAL);


                    // displayFirebaseRegId();
                } else if (intent.getAction().equals(DataStorage.PUSH_NOTIFICATION)) {
                    //Log.d("messagebroadcast","received");
                    storage.write("seen", "0");
                    // new push notification is received
                    //String message = intent.getStringExtra("message");
                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    // txtMessage.setText(message);
                }
            }
        };

        products.add("Exam date out");
        products.add("Attendance report");
        products.add("Assignment list");
        products.add("Annual function date");
        products.add("Holiday notice");


        //Log.d("notification_size",String.valueOf(products.size()));

        findviews();

        MenuSlice();


        //************** for login data store only for students not of employee***************
        //get_student_data_from_student_ID();

        /*send token to firebase 3aug 2019 for push notification*/
        sendIdToserver(refreshedToken);

        UpdateUserinterface();

        //updateApp();

        init();

        notification();

//        if (storage.CheckLogin("emp_id", this))
//        {
//
//            if (storage.read("emp_id", 3).equals("1716"))
//            {
//                nav_logout.setVisibility(View.INVISIBLE);
//            }
//            else
//                {
//                    nav_logout.setVisibility(View.VISIBLE);
//            }
//
//        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mLayout != null) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast toast = Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);

            /*AlertDialog myQuittingDialogBox =new AlertDialog.Builder(MainActivity.this)
                    //set message, title, and icon
                    .setTitle("Exit ?")
                    //.setMessage("Do you want to exit from this App?")

                    .setPositiveButton("ok", new DialogInterface.OnClickListener()
                    {

                        public void onClick(DialogInterface dialog, int whichButton)
                        {


                            Intent i=new Intent(MainActivity.this,MainActivity.class);
                            i.putExtra("Exit me", true);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    })

                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    }).create();

            myQuittingDialogBox.show();*/
    }

/*nirali intialize app controls 3aug*/
    public void MenuSlice() {
//  View headerLayout = mNavigationView.getHeaderView(0);
        imgmenuprofile = (CircleImageView) findViewById(R.id.imgmenuprofile);
        ll_view_more = (LinearLayout) findViewById(R.id.ll_view_more);
        ll_view_more.setOnClickListener(this);
        ll_views_hide = (LinearLayout) findViewById(R.id.ll_views_hide);
        ll_views_hide.setOnClickListener(this);
        txtmenuname = (TextView) findViewById(R.id.txtmenuname);
        txtmenucourse = (TextView) findViewById(R.id.txtmenucourse);
        nav_profile = (TextView) findViewById(R.id.nav_profile);
        nav_profile.setOnClickListener(this);
        nav_timetable = (TextView) findViewById(R.id.nav_timetable);
        nav_timetable.setOnClickListener(this);
        nav_change_psw = (TextView) findViewById(R.id.nav_change_psw);
        nav_change_psw.setOnClickListener(this);
        nav_attendance = (TextView) findViewById(R.id.nav_attendance);
        llactionbar = (LinearLayout) findViewById(R.id.llactionbar);
        nav_attendance.setOnClickListener(this);

        emp_nav_asnmnt = (TextView) findViewById(R.id.emp_nav_asnmnt);
        emp_nav_asnmnt.setOnClickListener(this);

        nav_fees = (TextView) findViewById(R.id.nav_fees);
        nav_fees.setOnClickListener(this);


        nav_result = (TextView) findViewById(R.id.nav_result);
        nav_placement = (TextView) findViewById(R.id.nav_placement);
        nav_placement.setOnClickListener(this);
        nav_Leave_app = (TextView) findViewById(R.id.nav_Leave_app);
        nav_Leave_app.setOnClickListener(this);

        imgattendance_emp = (ImageView) findViewById(R.id.imgattendance_emp);
        imgattendance_emp.setOnClickListener(this);
        iv_leave_app_emp = (ImageView) findViewById(R.id.iv_leave_app_emp);
        iv_leave_app_emp.setOnClickListener(this);
        imgnews_emp = (ImageView) findViewById(R.id.imgnews_emp);
        imgnews_emp.setOnClickListener(this);


        nav_elrning = (TextView) findViewById(R.id.nav_elrning);
        //     syl = (TextView) findViewById(R.id.syl);
        nav_elrning.setOnClickListener(this);
        nav_more = (TextView) findViewById(R.id.nav_more);
        //     syl = (TextView) findViewById(R.id.syl);
        nav_more.setOnClickListener(this);

        nav_lleave = (TextView) findViewById(R.id.nav_lleave);
        //     syl = (TextView) findViewById(R.id.syl);
        nav_lleave.setOnClickListener(this);
        nav_result.setOnClickListener(this);
        nav_activity = (TextView) findViewById(R.id.nav_acivity);
        nav_activity.setOnClickListener(this);
        nav_homewrork = (TextView) findViewById(R.id.nav_homework);
        nav_homewrork.setOnClickListener(this);
        nav_assignment = (TextView) findViewById(R.id.nav_asnmnt);
        nav_assignment.setOnClickListener(this);
        nav_exam_time_table = (TextView) findViewById(R.id.nav_exam_TT);
        nav_exam_time_table.setOnClickListener(this);

        nav_pending_fees = (TextView) findViewById(R.id.nav_pending_fees);
        nav_pending_fees.setOnClickListener(this);
        nav_news = (TextView) findViewById(R.id.nav_news);
        nav_news.setOnClickListener(this);
        nav_feedback = (TextView) findViewById(R.id.nav_feedback);
        nav_feedback.setOnClickListener(this);
        nav_syllabus = (TextView) findViewById(R.id.nav_syllabus);
        nav_syllabus.setOnClickListener(this);
        nav_lessionplan = (TextView) findViewById(R.id.nav_lessionplan);
        nav_lessionplan.setOnClickListener(this);
        nav_share = (TextView) findViewById(R.id.nav_share);
        nav_share.setOnClickListener(this);
        nav_logout = (TextView) findViewById(R.id.nav_logout);
        nav_logout.setOnClickListener(this);
        nav_rec = (TextView) findViewById(R.id.nav_rec);
        nav_rec.setOnClickListener(this);
        nav_fee_circular = (TextView) findViewById(R.id.nav_fee_circular);
        nav_fee_circular.setOnClickListener(this);
        txtmenuname.setText(String.valueOf(storage.read("name", 3)));
        txtmenucourse.setText(String.valueOf(storage.read("course_fullname", 3)));

        nav_feedback_a = (TextView) findViewById(R.id.nav_aa);
        nav_news_a = (TextView) findViewById(R.id.nav_bb);
        nav_share_a = (TextView) findViewById(R.id.nav_cc);
        imglessionplan = (ImageView) findViewById(R.id.imglessionplan);

        imgtimetable = (ImageView) findViewById(R.id.imgtimetable);
        imgtimetable.setOnClickListener(this);
        imgtimetable_emp = (ImageView) findViewById(R.id.imgtimetable_emp);
        imgtimetable_emp.setOnClickListener(this);


        imglessionplan.setOnClickListener(this);

        imgnews = (ImageView) findViewById(R.id.imgnews);
        imgnews.setOnClickListener(this);

        img_assignment_emp = (ImageView) findViewById(R.id.img_assignment_emp);
        img_assignment_emp.setOnClickListener(this);

        iv_rem_att = (ImageView) findViewById(R.id.iv_rem_att);
        iv_rem_att.setOnClickListener(this);

        imgprofile = (ImageView) findViewById(R.id.imgprofile);

        imgprofile.setOnClickListener(this);
        imgprofile_emp = (ImageView) findViewById(R.id.imgprofile_emp);

        imgprofile_emp.setOnClickListener(this);

        imgattendance_emp = (ImageView) findViewById(R.id.imgattendance_emp);
        imgattendance_emp.setOnClickListener(this);


        img_leave_app = (ImageView) findViewById(R.id.img_leave_app);
        img_leave_app.setOnClickListener(this);

        img_elerning = (ImageView) findViewById(R.id.img_elerning);
        img_elerning.setOnClickListener(this);

        imgsyllabus = (ImageView) findViewById(R.id.imgsyllabus);
        imgsyllabus.setOnClickListener(this);
        img_Moreapp = (ImageView) findViewById(R.id.img_Moreapp);
        img_Moreapp.setOnClickListener(this);
        img_Moreapp_emp = (ImageView) findViewById(R.id.img_Moreapp_emp);
        img_Moreapp_emp.setOnClickListener(this);


        if (storage.CheckLogin("stud_id", ctx)) {
            nav_fees.setVisibility(View.GONE);
            nav_rec.setVisibility(View.GONE);
            nav_activity.setVisibility(View.GONE);
            nav_homewrork.setVisibility(View.GONE);
            nav_result.setVisibility(View.GONE);
            nav_feedback.setVisibility(View.GONE);
            nav_lleave.setVisibility(View.GONE);
            nav_attendance.setVisibility(View.VISIBLE);

        } else {
            nav_attendance.setVisibility(View.GONE);
//            nav_fees.setVisibility(View.GONE);
//            nav_rec.setVisibility(View.GONE);
//            nav_activity.setVisibility(View.GONE);
//            nav_homewrork.setVisibility(View.GONE);
//            nav_result.setVisibility(View.GONE);
        }


//        nav_lessionplan_a = (TextView) findViewById(R.id.nav_dd);
       /* if(!storage.CheckLogin("stud_id",ctx))
        {
           //nav_feedback.setVisibility(View.GONE);
            //nav_news.setVisibility(View.GONE);
           nav_fees.setText("Remaining Attendance");
        }*/
        if (!String.valueOf(storage.read("stud_photo", 3)).equals("") || !(String.valueOf(storage.read("stud_photo", 3)) == null)) {
            Drawable mDefaultBackground = getResources().getDrawable(R.drawable.defaultprofile);
            Glide.with(Main3Activity.this).load(String.valueOf(storage.read("stud_photo", 3))).fitCenter().error(mDefaultBackground).into(imgmenuprofile);
        }
    }

    public void findviews()
    {
        iv_internship_work_report = (ImageView) findViewById(R.id.iv_internship_work_report);
        iv_internship_work_report.setOnClickListener(this);
        notification_dashboard = (ImageView) findViewById(R.id.notification_dashboard);
        notification_dashboard.setOnClickListener(this);
        txt = (TextView) findViewById(R.id.notification_count);
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        login_master = new Login_Master("Login_Master", this);

        if (!storage.isOnline(Main3Activity.this))
        {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        }

        if (storage.CheckLogin("stud_id", ctx))
        {
            student_layout.setVisibility(View.VISIBLE);
            Emp_layout.setVisibility(View.GONE);
        }
        else
            {
            student_layout.setVisibility(View.GONE);
            Emp_layout.setVisibility(View.VISIBLE);
        }


        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("refreshedToken:::::" + refreshedToken);
        //Log.d("refreshedToken", refreshedToken);
        if (storage.CheckLogin("stud_id", Main3Activity.this))
        {
            //  Log.d("stud_id", String.valueOf(storage.read("stud_id",3)));
        }
        else
            {
            // Log.d("emp_id", String.valueOf(storage.read("emp_id",3)));
        }
        nav_memberno = (TextView) findViewById(R.id.nav_memberno);
        nav_version_name = (TextView) findViewById(R.id.nav_version_name);


        PackageInfo pInfo = null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        assert pInfo != null;
        System.out.println("version name:::::::" + pInfo.versionName);

        nav_version_name.setText("  " + pInfo.versionName);


        this.llviewshide = (LinearLayout) findViewById(R.id.ll_views_hide);
        this.fivell = (LinearLayout) findViewById(R.id.five_ll);
        this.imgfeecircular = (ImageView) findViewById(R.id.img_feecircular);
        this.ivmidexamtt = (ImageView) findViewById(R.id.iv_mid_exam_tt);
        this.pendingfeesimg = (ImageView) findViewById(R.id.pending_fees_img);
        this.studentlayout = (LinearLayout) findViewById(R.id.studentlayout);
        this.imgfeedback = (ImageView) findViewById(R.id.imgfeedback);
        this.imgdashboard = (ImageView) findViewById(R.id.img_dashboard);
        this.iv_payroll = (ImageView) findViewById(R.id.iv_payroll);
        this.iv_payroll.setOnClickListener(this);
        this.txtfeedback = (TextView) findViewById(R.id.txtfeedback);
        this.txtnews = (TextView) findViewById(R.id.txtnews);
        this.imgnews = (ImageView) findViewById(R.id.imgnews);
        this.feeslayout = (LinearLayout) findViewById(R.id.fees_layout);
        this.assignmentimg = (ImageView) findViewById(R.id.assignment_img);
        this.imgresult = (ImageView) findViewById(R.id.img_result);
        this.txt1 = (TextView) findViewById(R.id.txt_1);
        this.imgattendance = (ImageView) findViewById(R.id.imgattendance);
        this.txtreceipt = (TextView) findViewById(R.id.txt_receipt);
        this.llviewmore = (LinearLayout) findViewById(R.id.ll_view_more);
        this.newiconll = (LinearLayout) findViewById(R.id.new_icon_ll);
        this.imgleaveapp = (ImageView) findViewById(R.id.img_leave_app);
        this.txtplacement = (TextView) findViewById(R.id.txt_placement);
        this.imgplacement = (ImageView) findViewById(R.id.img_placement);
        this.txterning = (TextView) findViewById(R.id.txt_erning);
        this.imgnewannouncement = (ImageView) findViewById(R.id.img_new_announcement);
        this.ivassignment = (ImageView) findViewById(R.id.iv_assignment);
        this.textView6 = (TextView) findViewById(R.id.textView6);
        this.imgtimetable = (ImageView) findViewById(R.id.imgtimetable);
        this.ivlessionplan = (ImageView) findViewById(R.id.iv_lession_plan);
        this.layout1 = (LinearLayout) findViewById(R.id.layout1);
        this.textattendence = (TextView) findViewById(R.id.textattendence);
        this.ivplacement = (ImageView) findViewById(R.id.iv_placement);
        this.textfee = (TextView) findViewById(R.id.textfee);
        this.nav_punch_in_out = (TextView) findViewById(R.id.nav_punch_in_out);
        this.nav_internship_work_rpt = (TextView) findViewById(R.id.nav_internship_work_rpt);
        this.nav_punch_in_out.setOnClickListener(this);
        this.nav_internship_work_rpt.setOnClickListener(this);
        this.ivatt = (ImageView) findViewById(R.id.iv_att);
        this.iv_att = (ImageView) findViewById(R.id.iv_att);
        this.imgprofile = (ImageView) findViewById(R.id.imgprofile);
        imgprofile.setOnClickListener(this);
        ivatt.setOnClickListener(this);
        ivlessionplan.setOnClickListener(this);
        ivplacement.setOnClickListener(this);
        iv_att.setOnClickListener(this);
        imgtimetable.setOnClickListener(this);
        ivassignment.setOnClickListener(this);
        ivlessionplan.setOnClickListener(this);
        imgnewannouncement.setOnClickListener(this);

        emp_nav_el = (TextView) findViewById(R.id.emp_nav_el);
        emp_nav_el.setOnClickListener(this);

//        iv_el_emp = (ImageView) findViewById(R.id.iv_el_emp);
        // iv_el_emp.setOnClickListener(this);
    }

    public void UpdateUserinterface()
    {
        if (storage.CheckLogin("emp_id", this))
        {
            ll_view_more.setVisibility(View.GONE);
            ll_views_hide.setVisibility(View.VISIBLE);
            studentlayout.setVisibility(View.VISIBLE);
            emp_nav_asnmnt.setVisibility(View.VISIBLE);
            emp_nav_el.setVisibility(View.GONE);

            nav_fees.setText("Pending Attendance");
            nav_fees.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.remaining_attendance, 0, 0, 0);

//            nav_news.setText("News");
            nav_news.setText("Announcement");
            nav_news.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.news, 0, 0, 0);

//            nav_feedback.setText("Lession Plan");
            nav_feedback.setText("Lesson Plan");
            nav_feedback.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.lesson_plan, 0, 0, 0);

            // nav_lessionplan.setText("Pending Attendance");
            nav_lessionplan.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.time_table, 0, 0, 0);
            nav_lessionplan.setVisibility(View.GONE);
            nav_syllabus.setVisibility(View.GONE);
            nav_pending_fees.setVisibility(View.GONE);
            nav_rec.setVisibility(View.GONE);
            nav_logout.setVisibility(View.VISIBLE);


            nav_assignment.setVisibility(View.GONE);
            nav_syllabus.setVisibility(View.GONE);
            nav_pending_fees.setVisibility(View.GONE);
            nav_rec.setVisibility(View.GONE);
            nav_exam_time_table.setVisibility(View.GONE);
            nav_homewrork.setVisibility(View.GONE);
            nav_activity.setVisibility(View.GONE);
            nav_result.setVisibility(View.GONE);
            nav_elrning.setVisibility(View.GONE);
            nav_Leave_app.setVisibility(View.GONE);
            nav_placement.setVisibility(View.GONE);

            nav_fee_circular.setVisibility(View.GONE);
            //       img_leave_app.setVisibility(View.INVISIBLE);
            //       img_placement.setVisibility(View.INVISIBLE);
            //img_elerning.setVisibility(View.GONE);

            imgfeedback.setVisibility(View.GONE);


        } else if (storage.CheckLogin("stud_id", this)) {
            this.nav_internship_work_rpt.setVisibility(View.VISIBLE);
            this.nav_punch_in_out.setVisibility(View.VISIBLE);
            this.iv_payroll.setVisibility(View.GONE);
        } else {
            nav_logout.setVisibility(View.VISIBLE);
            ///pppppppp    img_dashboard.setVisibility(View.GONE);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        final View menu_hotlist = menu.findItem(R.id.action_notification).getActionView();
        tvActionNotification = (TextView) menu_hotlist.findViewById(R.id.notification_count);
        updateHotCount(products.size());

        new MainActivity.MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main3Activity.this, Notification_Activity.class);
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intent = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);
                    intent.putExtra("type", "notification");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Main3Activity.this, AnnouncementFaculty.class);
                    intent.putExtra("type", "notification");
                    startActivity(intent);

                }

                //  showProfileMenuPopup(v);
                listPopupWindow = new ListPopupWindow(Main3Activity.this);
                listPopupWindow.setAdapter(new listpopupadapter(products, "asiji"));
                // listPopupWindow.setListSelector(getResources().getDrawable(R.drawable.list_row_selector));
                listPopupWindow.setAnchorView(v);
                //listPopupWindow.setListSelector(getResources().getDrawable(R.drawable.list_row_selector));
                WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                int pixel = windowmanager.getDefaultDisplay().getWidth();
                int dp = pixel / (int) getResources().getDisplayMetrics().density;
                listPopupWindow.setWidth(dp);
                listPopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //listPopupWindow.setBackgroundDrawable(R.color.transparent);
                listPopupWindow.setHeight(android.support.v7.widget.ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setModal(true);
                listPopupWindow.setOnItemClickListener(Main3Activity.this);
                listPopupWindow.show();

            }
        };

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        Intent intent = new Intent(Main3Activity.this, Notification_Activity.class);
        Intent intent;
        if (storage.CheckLogin("stud_id", Main3Activity.this))
        {
            intent = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);

        }
        else
            {
            intent = new Intent(Main3Activity.this, AnnouncementFaculty.class);

        }

        intent.putExtra("position", 3);
        intent.putExtra("title", products.get(position));
        intent.putExtra("type", "notification");
        intent.putExtra("description", "Description");
        startActivity(intent);
        //productName.setText(products[position]);
        listPopupWindow.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // call the updating code on the main thread,
// so we can call this asynchronously
    public void updateHotCount(final int new_hot_number)
    {
        //if (tvActionNotification == null) return;

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (new_hot_number == 0)
                    tvActionNotification.setVisibility(View.INVISIBLE);
                else {
                    tvActionNotification.setVisibility(View.VISIBLE);
                    tvActionNotification.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener
    {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint)
        {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        // @Override abstract public void onClick(View v);

        @Override
        public boolean onLongClick(View v)
        {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }

    RequestQueue queue;
    MySharedPrefereces mySharedPrefereces;

    @Override
    public void onClick(View v)
    {
        mySharedPrefereces = new MySharedPrefereces(Main3Activity.this);
        queue = Volley.newRequestQueue(Main3Activity.this);
        switch (v.getId()) {

            case R.id.emp_nav_el:
                Intent in_emp_nav_el = new Intent(Main3Activity.this, E_Learning_List.class);
                startActivity(in_emp_nav_el);
                break;
            case R.id.iv_internship_work_report:
                Intent intent_internship = new Intent(Main3Activity.this, ViewInternshipWorkReportActivity.class);
                startActivity(intent_internship);
                break;
            case R.id.imgprofile:

                Intent intent = new Intent(Main3Activity.this, Profile.class);
                //Intent intent = new Intent(Main3Activity.this, ELearningActivity.class);

                // ActivityOptions options =
//                        ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.fade_in, R.anim.fade_out);
                ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.righttoleft, R.anim.righttoleft);
//                        ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.bounce, R.anim.bounce);
                //   startActivity(intent, options.toBundle());

                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            /********nirali *****for hr app combine with gsfc 4dec 2019*********/
            case R.id.iv_payroll:
                /*nirali**** 4dec 2019 for HR app automatically login*/
                String PASSWORD = String.valueOf(storage.read("emp_password", 3));
                String pssword = PASSWORD;
                /*encrypt password for special characters allowed ***** 27aug 2019 nirali*/
                try {
                    pssword = URLEncoder.encode(pssword, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                DialogUtils.showProgressDialog(Main3Activity.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
                String url = URLS.LoginCheck + "&userName=" + String.valueOf(storage.read("emp_username", 3)) + "&passWord=" + pssword + "";
                url.replace(" ", "%20");

                System.out.println("LoginCheck URL " + url + "");
                StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        DialogUtils.hideProgressDialog();

                        System.out.println("response of LoginCheck !!!!!!!!!!! " + response);
                        response = response + "";
                        if (response.length() > 5)
                        {
                            response = "{\"Data\":" + response + "}";

                            System.out.println("sucess response LoginCheck !!!!!!!!!!!!!!!!!!!" + response + "");
                            Gson gson = new Gson();
                            LoginPojo loginPojo = gson.fromJson(response, LoginPojo.class);
                            if (loginPojo != null)
                            {
                                if (loginPojo.getData() != null)
                                {
                                    if (loginPojo.getData().get(0) != null)
                                    {
                                        if (loginPojo.getData().size() > 0)
                                        {
                                            if (loginPojo.getData().get(0).getStatus().contentEquals("1"))
                                            {
                                                //DialogUtils.Show_Toast(LoginActivity.this,"Login Sucessfully");
                                                //********* store login data of user ****************
                                                mySharedPrefereces.storeLoginData(loginPojo.getData().get(0).getStatus() + "", loginPojo.getData().get(0).getUsrm_id() + "", loginPojo.getData().get(0).getEmp_code() + "", loginPojo.getData().get(0).getUsrm_name() + "", loginPojo.getData().get(0).getUsrm_dis_name() + "", loginPojo.getData().get(0).getComp_id() + "", loginPojo.getData().get(0).getUsrm_brm_id() + "", loginPojo.getData().get(0).getCom_name() + "", loginPojo.getData().get(0).getFin_year() + "", loginPojo.getData().get(0).getFin_id() + "", loginPojo.getData().get(0).getFin_start_date() + "", loginPojo.getData().get(0).getFin_end_date() + "", loginPojo.getData().get(0).getEmp_id() + "", loginPojo.getData().get(0).getDepartment() + "", loginPojo.getData().get(0).getReportingto() + "", loginPojo.getData().get(0).getUserphoto() + "", loginPojo.getData().get(0).getDesignation() + "", loginPojo.getData().get(0).getBranch() + "", loginPojo.getData().get(0).getFullName() + "");


                                                Intent payroll_intent = new Intent(Main3Activity.this, com.infinity.infoway.gsfc.HrAppActivities.MainActivity.class);
                                                startActivity(payroll_intent);
                                               // finish();

                                            }
                                            else
                                                {
                                                //  DialogUtils.Show_Toast(LoginActivity.this,"Invalid UserName/Password");
                                            }
                                        }


                                    }
                                }
                            }
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtils.Show_Toast(Main3Activity.this, "Please Try Again Later");
                        DialogUtils.hideProgressDialog();
                        System.out.println("errorrrrrrrrrr " + error);
                        System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(request);




                break;

            case R.id.emp_nav_asnmnt:
                Intent nav_emp_nav_asnmnt = new Intent(Main3Activity.this, AssignmentEmployeeActivity.class);
                startActivity(nav_emp_nav_asnmnt);
                break;
            case R.id.img_assignment_emp:
                Intent intent_as = new Intent(Main3Activity.this, AssignmentEmployeeActivity.class);
//                Intent intent_as = new Intent(Main3Activity.this, PunchInPunchOutActivity.class);
                startActivity(intent_as);
                break;

            case R.id.nav_internship_work_rpt:
                Intent punch_intent = new Intent(Main3Activity.this, ViewInternshipWorkReportActivity.class);

                startActivity(punch_intent);
                break;

            case R.id.nav_punch_in_out:
                Intent punch_in_out = new Intent(Main3Activity.this, PunchInPunchOutActivity.class);

                startActivity(punch_in_out);
                break;

            case R.id.imgprofile_emp:

                intent = new Intent(Main3Activity.this, Profile.class);
                //Intent intent = new Intent(Main3Activity.this, ELearningActivity.class);

                // ActivityOptions options =
//                        ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.fade_in, R.anim.fade_out);
                ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.righttoleft, R.anim.righttoleft);
//                        ActivityOptions.makeCustomAnimation(Main3Activity.this, R.anim.bounce, R.anim.bounce);
                //   startActivity(intent, options.toBundle());

                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.receipt_img:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent fee_intent = new Intent(Main3Activity.this, Receipt_Activity.class);
                    startActivity(fee_intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent fee_intent = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(fee_intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }

                break;

            case R.id.img_elerning:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intent_elerning = new Intent(Main3Activity.this, ELerningStudentActivity.class);
                    startActivity(intent_elerning);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                }
              /*  else {
                    Intent intent_elerning = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intent_elerning);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }*/
                break;

            case R.id.img_placement:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intent_place = new Intent(Main3Activity.this, PlacementActivity.class);
                    startActivity(intent_place);
                } else {
                    Intent intent_place = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(intent_place);
                }

                break;

            case R.id.img_Moreapp:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                  /*  Staff link : http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560
Student Link: http://online.jau.in/mobileapp/students/index.php?uid=2010457852
//                    Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);*/
                    Intent intent_place = new Intent(Main3Activity.this, PunchInPunchOutActivity.class);
                    //intent_place.putExtra("url", "http://online.jau.in/mobileapp/students/index.php?uid=2010457852");
                    startActivity(intent_place);
                } else {
                   /* Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);
                    intent_place.putExtra("url", "http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560");
                    startActivity(intent_place);*/
                }

                break;
           /* case R.id.nav_more:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    *//*Staff link : http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560
Student Link: http://online.jau.in/mobileapp/students/index.php?uid=2010457852*//*
                    Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);
                    intent_place.putExtra("url", "http://online.jau.in/mobileapp/students/index.php?uid=2010457852");
                    startActivity(intent_place);
                } else {
                    Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);
                    intent_place.putExtra("url", "http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560");
                    startActivity(intent_place);
                }

                break;*/

           /* case R.id.iv_el_emp:
                Intent iniv_el_emp = new Intent(Main3Activity.this, E_Learning_List.class);
                startActivity(iniv_el_emp);
                break;*/
           /* case R.id.img_Moreapp_emp:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    *//*Staff link : http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560
Student Link: http://online.jau.in/mobileapp/students/index.php?uid=2010457852*//*
                    Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);
                    intent_place.putExtra("url", "http://online.jau.in/mobileapp/students/index.php?uid=2010457852");
                    startActivity(intent_place);
                } else {
                    Intent intent_place = new Intent(Main3Activity.this, MoreApplication.class);
                    intent_place.putExtra("url", "http://online.jau.in/mobileapp/staff/index.php?uid=AGRI1560");
                    startActivity(intent_place);
                }

                break;*/

//            case R.id.img_leave_app:
//                Intent intent_leave_app = new Intent(Main3Activity.this, Leaveapplication.class);
//                startActivity(intent_leave_app);
//                overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;

            case R.id.iv_leave_app_emp:
//                Intent intent_leave_app = new Intent(Main3Activity.this, Leaveapplication.class);
                Intent intent_leave_app = new Intent(Main3Activity.this, Leave_emp.class);
                startActivity(intent_leave_app);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.nav_lleave:
//                Intent intent_leave_app = new Intent(Main3Activity.this, Leaveapplication.class);
                intent_leave_app = new Intent(Main3Activity.this, Leave_emp.class);
                startActivity(intent_leave_app);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.img_feecircular:
                Intent intent_img_feecircular = new Intent(Main3Activity.this, FeeCircularActivity.class);
                intent_img_feecircular.putExtra("fc_file", String.valueOf(storage.read("fc_file", 3)));
                startActivity(intent_img_feecircular);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_fee_circular:
                Intent intent_nav_fee_circular = new Intent(Main3Activity.this, FeeCircularActivity.class);
                intent_nav_fee_circular.putExtra("fc_file", String.valueOf(storage.read("fc_file", 3)));
                startActivity(intent_nav_fee_circular);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_change_psw:


                Intent change_psw = new Intent(Main3Activity.this, ChangePasswordActivity.class);
                startActivity(change_psw);
                overridePendingTransition(R.anim.enter, R.anim.exit);





           /*     if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    DialogUtils.showDialog4YNo(Main3Activity.this, "Change Password", "You can change the password from Online HRD / Tour Programme Login.\n",
                            "http://online.jau.in/student_network/index.php", new DialogUtils.DailogCallBackOkButtonClick() {
                                @Override
                                public void onDialogOkButtonClicked() {
//                        try {
//                            try {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            }
//                        } catch (Exception e) {
//                            //	System.out.println("");
//                        }
                                }
                            }, new DialogUtils.DailogCallBackCancelButtonClick() {
                                @Override
                                public void onDialogCancelButtonClicked() {

                                }
                            });
                } else {


//                Intent change_psw = new Intent(Main3Activity.this, ChangePasswordActivity.class);
//                startActivity(change_psw);
//                overridePendingTransition(R.anim.enter, R.anim.exit);
                    DialogUtils.showDialog4YNo(Main3Activity.this, "Change Password", "You can change the password from Online HRD / Tour Programme Login.\n",
                            "http://online.jau.in/jau_programmes/login.php", new DialogUtils.DailogCallBackOkButtonClick() {
                                @Override
                                public void onDialogOkButtonClicked() {
//                        try {
//                            try {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
//                                finish();
//                            }
//                        } catch (Exception e) {
//                            //	System.out.println("");
//                        }
                                }
                            }, new DialogUtils.DailogCallBackCancelButtonClick() {
                                @Override
                                public void onDialogCancelButtonClicked() {

                                }
                            });
                }*/

//                finish();
//                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                break;


            case R.id.img_result:
                Intent img_result_intent = new Intent(Main3Activity.this, ResultActivity.class);
                //Intent img_result_intent = new Intent(Main3Activity.this, Leaveapplication.class);
                startActivity(img_result_intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.imgtimetable:
                startActivity(new Intent(Main3Activity.this, Timetable.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.imgtimetable_emp:
                startActivity(new Intent(Main3Activity.this, Timetable.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.imgattendance:
//            case R.id.iv_att:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intentattendance = new Intent(Main3Activity.this, Syllabus.class);
                    startActivity(intentattendance);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(intentattendance);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }


            case R.id.imgsyllabus:
//            case R.id.iv_att:
                // if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                Intent intentattendance = new Intent(Main3Activity.this, Syllabus.class);
                startActivity(intentattendance);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                //  }
//                else {
//                    Intent intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
//                    startActivity(intentattendance);
//                    overridePendingTransition(R.anim.enter, R.anim.exit);
//                }

                break;
            case R.id.imgattendance_emp:
//            case R.id.iv_att:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    intentattendance = new Intent(Main3Activity.this, Syllabus.class);
                    startActivity(intentattendance);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(intentattendance);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                break;


            case R.id.imgfees:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    startActivity(new Intent(Main3Activity.this, Fees_Activity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    startActivity(new Intent(Main3Activity.this, FacultyAttendance.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                break;

           /* case R.id.imgnews:

                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent intentimgnews = new Intent(Main3Activity.this, Notification_Activity.class);
                    intentimgnews.putExtra("type", "news");
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }*/
            case R.id.img_new_announcement:

               /* if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                else {*/
                   /* Intent intentimgnews = new Intent(Main3Activity.this, Notification_Activity.class);
                    intentimgnews.putExtra("type", "news");
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);*/
                Intent intentann = null;
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    intentann = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);


                } else {
                    intentann = new Intent(Main3Activity.this, AnnouncementFaculty.class);

                }
                intentann.putExtra("type", "news");
                startActivity(intentann);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                // }


                break;

            case R.id.imgnews_emp:

                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
//                    Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    Intent intentimgnews = new Intent(Main3Activity.this, AnnouncementFaculty.class);
//                    Intent intentimgnews = new Intent(Main3Activity.this, ViewInternshipWorkReportActivity.class);
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
//                    Intent intentimgnews = new Intent(Main3Activity.this, Notification_Activity.class);
                    Intent intentimgnews = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);
                    intentimgnews.putExtra("type", "news");
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }


                break;


//            case R.id.imgsyllabus:
//
//                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
//                    Intent intentimgnews = new Intent(Main3Activity.this, Leave_emp.class);
//                    startActivity(intentimgnews);
//                    overridePendingTransition(R.anim.enter, R.anim.exit);
//                }
//
//                else {
//                    Intent intentimgsyllabus = new Intent(Main3Activity.this, Student_Attendance.class);
//                    startActivity(intentimgsyllabus);
//                }
//
//                break;
            case R.id.iv_att:

//                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
//                    Intent intentimgnews = new Intent(Main3Activity.this, Leave_emp.class);
//                    startActivity(intentimgnews);
//                    overridePendingTransition(R.anim.enter, R.anim.exit);
//                }
//
//                else {
                Intent intentimgsyllabus = new Intent(Main3Activity.this, Student_Attendance.class);
//                Intent intentimgsyllabus = new Intent(Main3Activity.this, Student_attendance_backup.class);
                startActivity(intentimgsyllabus);
                // }

                break;

//            case R.id.assignment_img:
            case R.id.iv_assignment:
                Intent intent_ass_img = new Intent(Main3Activity.this, AssignmentActivity.class);
                startActivity(intent_ass_img);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.pending_fees_img:
                Intent pending_fees = new Intent(Main3Activity.this, PendingfeesActivity.class);
                startActivity(pending_fees);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.iv_mid_exam_tt:
                Intent intet_tt_mid = new Intent(Main3Activity.this, ExamDisplayTimeTable.class);
                startActivity(intet_tt_mid);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;


            case R.id.img_dashboard:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intentds = new Intent(Main3Activity.this, Student_Main_Activity.class);
                    startActivity(intentds);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {

                    Intent dashboard = new Intent(Main3Activity.this, DirectivePageActivity.class);
                    startActivity(dashboard);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
                break;


//            case R.id.nav_placement:
            case R.id.nav_placement:
                Intent intent1 = new Intent(Main3Activity.this, PlacementActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.iv_placement:
                Intent intent21 = new Intent(Main3Activity.this, PlacementActivity.class);
                startActivity(intent21);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_elrning:
//                Intent intent2 = new Intent(Main3Activity.this, ELearningActivity.class);
                Intent intent2 = new Intent(Main3Activity.this, ELerningStudentActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;

            case R.id.nav_Leave_app:
//                Intent intent3 = new Intent(Main3Activity.this, Leaveapplication.class);
                Intent intent3 = new Intent(Main3Activity.this, ViewLeaveApplication.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;


            case R.id.img_leave_app:
//                 intent3 = new Intent(Main3Activity.this, Leaveapplication.class);
                intent3 = new Intent(Main3Activity.this, ViewLeaveApplication.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;


            case R.id.imglessionplan:
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent imgfeedback = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    //Intent imgfeedback = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(imgfeedback);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent imglessionplan = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(imglessionplan);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                break;
            case R.id.iv_lession_plan:
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent imgfeedback = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    //Intent imgfeedback = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(imgfeedback);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent imglessionplan = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(imglessionplan);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                break;

            case R.id.imgfeedback:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent imgfeedback = new Intent(Main3Activity.this, HomeworkActivity.class);
                    startActivity(imgfeedback);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                break;


            case R.id.notification_dashboard: {
//                Intent intent1_noty = new Intent(Main3Activity.this, Notification_Activity.class);
                Intent intent1_noty = null;
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    intent1_noty = new Intent(Main3Activity.this, AnnouncementFaculty.class);
                } else {
                    intent1_noty = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);
                }

                intent1_noty.putExtra("type", "notification");
                startActivity(intent1_noty);
                txt.setVisibility(View.GONE);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            }


            case R.id.nav_rec: {
                Intent intent2_rec = new Intent(Main3Activity.this, Receipt_Activity.class);
                startActivity(intent2_rec);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            }

            case R.id.ll_view_more:
                Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);

                ll_view_more.setVisibility(View.GONE);
                ll_views_hide.setAnimation(slide_up);
                ll_views_hide.setVisibility(View.VISIBLE);
                break;

            //MENU

            case R.id.nav_profile:
                Intent intmenuprofile = new Intent(Main3Activity.this, Profile.class);
                startActivity(intmenuprofile);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;

            case R.id.nav_timetable:
                Intent intentmenutimetable = new Intent(Main3Activity.this, Timetable.class);
                startActivity(intentmenutimetable);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;

            case R.id.nav_attendance:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    intentattendance = new Intent(Main3Activity.this, Student_Attendance.class);
//                    intentattendance = new Intent(Main3Activity.this, Student_attendance_backup.class);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                    startActivity(intentattendance);
                } else {
                    intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(intentattendance);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }
                break;

            case R.id.nav_fees:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    startActivity(new Intent(Main3Activity.this, Fees_Activity.class));
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                } else {
                    startActivity(new Intent(Main3Activity.this, FacultyAttendance.class));
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }
                break;

            case R.id.iv_rem_att:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
//                    startActivity(new Intent(Main3Activity.this, Fees_Activity.class));
//                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                } else {
                    startActivity(new Intent(Main3Activity.this, FacultyAttendance.class));
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }
                break;


            case R.id.nav_news:
//                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
//                    Intent intentimgnews = new Intent(Main3Activity.this, EmployeeLeave.class);
//                    startActivity(intentimgnews);
//                } else {
//                    Intent intent1 = new Intent(Main3Activity.this, Notification_Activity.class);
//                    intent1.putExtra("type", "news");
//                    startActivity(intent1);
//                }
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    // Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    Intent intentimgnews = new Intent(Main3Activity.this, AnnouncementFaculty.class);
                    startActivity(intentimgnews);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                } else {
                    Intent intent1_noti = new Intent(Main3Activity.this, AnnouncementStudentActiivty.class);
                    // intent1.putExtra("type", "news");
                    startActivity(intent1_noti);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }
                break;

            case R.id.nav_syllabus:
                Intent intentmenusyllabus = new Intent(Main3Activity.this, Syllabus.class);
                startActivity(intentmenusyllabus);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;


            case R.id.nav_pending_fees:
                Intent pending_fees_nav = new Intent(Main3Activity.this, PendingfeesActivity.class);
                startActivity(pending_fees_nav);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;

            case R.id.nav_lessionplan:
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentmenuplan = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(intentmenuplan);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                } else {
                    Intent intentmenuplan = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(intentmenuplan);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }

                break;


            case R.id.nav_result:
                Intent intent_nav_result = new Intent(Main3Activity.this, ResultActivity.class);
                startActivity(intent_nav_result);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;


            case R.id.nav_homework:
                Intent intent_homework = new Intent(Main3Activity.this, HomeworkActivity.class);
                startActivity(intent_homework);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;


            case R.id.nav_asnmnt:
                Intent intent_nav_as = new Intent(Main3Activity.this, AssignmentActivity.class);
                startActivity(intent_nav_as);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;

            case R.id.nav_exam_TT:
                Intent intent_exam_tt = new Intent(Main3Activity.this, ExamDisplayTimeTable.class);
                startActivity(intent_exam_tt);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;


            case R.id.nav_acivity:
                Intent act = new Intent(Main3Activity.this, Student_Main_Activity.class);
                startActivity(act);
                overridePendingTransition(R.anim.slide_up, R.anim.blink);
                break;

            /*case R.id.img_result:

                    Intent result_intent = new Intent(Main3Activity.this, ResultActivity.class);
                    startActivity(result_intent);



                break;
*/


            case R.id.nav_feedback:
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent imgfeedback = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(imgfeedback);
                    overridePendingTransition(R.anim.slide_up, R.anim.blink);
                }
                break;

            case R.id.nav_logout:
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Main3Activity.this)
                        //set message, title, and icon
                        .setTitle("Logout")
                        .setMessage("Do you want to logout from this App?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (storage.read("emp_id", 3).equals("1716")) {
                                    storage.clear();
                                    Intent it = new Intent(Main3Activity.this, Login.class);
                                    startActivity(it);
                                    finish();
                                } else {
                                    storage.clear();
                                    Intent intent = new Intent(Main3Activity.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        })

                        .setNeutralButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();
                            }
                        }).create();

                myQuittingDialogBox.show();
                break;
            case R.id.nav_share:
                String shareBody = "https://play.google.com/store/apps/details?id=com.infinity.infoway.gsfc&hl=en";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GSFC University App... ");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Using.."));
                break;


            default:
                break;
        }
    }

    public class listpopupadapter extends BaseAdapter {
        ArrayList<String> a;
        String type;

        public listpopupadapter(ArrayList<String> a, String type) {
            this.a = a;
            this.type = type;
        }

        @Override
        public int getCount() {
            return a.size();
        }

        @Override
        public Object getItem(int position) {
            return getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notification, null);
            TextView txtnoification = (TextView) root.findViewById(R.id.txtnotification);
            txtnoification.setText(a.get(position));
            return root;
        }
    }

/*
    public void updateApp() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert pInfo != null;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.checkversionupdate(pInfo.versionCode);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {

                    //Optional Update
                    if (response.body().getVersion() == 1) {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Main3Activity.this)
                                //set message, title, and icon
                                .setMessage("New version available.Would you like to update your app ?")
                                //.setMessage("Do you want to exit from this App?")

                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();
                                            }
                                        } catch (Exception e) {
                                            //	System.out.println("");
                                        }

                                               */
/*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*//*

                                    }
                                })

                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        dialog.dismiss();
                                    }
                                }).create();

                        myQuittingDialogBox.show();

                    }
                    // Force Update
                    else if (response.body().getVersion() == 2) {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Main3Activity.this)
                                .setCancelable(false)
                                //set message, title, and icon
                                .setTitle("New version available in.Would you like to update your app ?")
                                //.setMessage("Do you want to exit from this App?")

                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        try {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();

                                            } catch
                                            (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + DataStorage.PACKAGE_NAME)));
                                                finish();

                                            }
                                        } catch (Exception e) {
                                            //	System.out.println("");
                                        }

                                               */
/*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*//*

                                    }
                                }).create();

                        myQuittingDialogBox.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // Log error here since request failed
                //  Toast.makeText(Main3Activity.this,"Error in Response",Toast.LENGTH_LONG).show();

            }
        });

    }
*/


    private void init() {


        final long DELAY_MS = 600;//delay in milliseconds before task is to be executed
        final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.getSliderImages(String.valueOf(storage.read("im_domain_name", 3)), String.valueOf(storage.read("intitute_id", 3)));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Toast.makeText(MainActivity.this,  "products found", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    ArrayList<String> resp = response.body().geturl();
                    for (int i = 0; i < resp.size(); i++)
                        XMENArray.add(resp.get(i));
                    Log.d("xmenarray", String.valueOf(XMENArray.size()));

                    mPager = (ViewPager) findViewById(R.id.pager);
                    /*mPager.startAutoScroll();
                    mPager.setInterval(3000);
                    mPager.setCycle(true);
                    mPager.setStopScrollWhenTouch(true);*/
                    mPager.setAdapter(new Pageradapter(Main3Activity.this, XMENArray));
                    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                    indicator.setViewPager(mPager);

                    // Auto start of viewpager
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == XMENArray.size()) {
                                currentPage = 0;
                            }
                            mPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);
                } else {
                    //Toast.makeText(PrivacyAndTermsCondition.this, "Error in Response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                //Toast.makeText(PrivacyAndTermsCondition.this, "Error", Toast.LENGTH_LONG).show();
                //  Log.e("termserror", t.toString());
            }
        });
    }


    public void get_student_data_from_student_ID() {
        System.out.println("calleddddddddddddddddddddddd---------general");
        if (storage.CheckLogin("stud_id", Main3Activity.this)) {
            System.out.println("stud_ID---from get_student_data_from_student_ID---" + String.valueOf(storage.read("stud_id", 3)));

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponse> call = apiInterface.get_student_data_from_stud_id(String.valueOf(storage.read("stud_id", 3)));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        System.out.println("calleddddddddddddddddddddddd---------student");
                        storage.write("stud_id", response.body().getstud_id());
                        storage.write("dm_id", response.body().getdm_id());
                        storage.write("dm_full_name", response.body().getdm_full_name());
                        storage.write("course_id", response.body().getcourse_id());
                        storage.write("course_fullname", response.body().getcourse_fullname());
                        storage.write("sm_id", response.body().getsm_id());
                        storage.write("sm_name", response.body().getsm_name());
                        storage.write("ac_id", response.body().getac_id());
                        storage.write("ac_full_name", response.body().getac_full_name());
                        storage.write("swd_year_id", response.body().getswd_year_id());
                        storage.write("name", response.body().getname());
                        storage.write("stud_admission_no", response.body().getstud_admission_no());
                        storage.write("stud_photo", response.body().getstud_photo());
                        storage.write("swd_batch_id", response.body().getswd_batch_id());
                        storage.write("shift_id", response.body().getshift_id());
                        storage.write("swd_division_id", response.body().getswd_division_id());
                        storage.write("fc_file", response.body().getfc_file());
                        storage.write("intitute_id", response.body().getinstitute_id());
                        storage.write("im_domain_name", response.body().getim_domain_name());
                        storage.write("seen", "0");

                        init();

                        // notification();

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        } else {
            if (storage.CheckLogin("emp_id", this)) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginResponse> call = apiInterface.login_user_new_api_from_emp_id(String.valueOf(storage.read("emp_id", 3)));
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.isSuccessful()) {
                            System.out.println("calleddddddddddddddddddddddd---------emp");

                            storage.write("emp_id", response.body().getemp_id());
                            storage.write("emp_main_school_id", response.body().getemp_main_school_id());
                            storage.write("emp_username", response.body().getemp_username());
                            storage.write("emp_password", response.body().getemp_password());
                            storage.write("ac_full_name", response.body().getac_full_name());
                            storage.write("ac_logo", response.body().getac_logo());
                            storage.write("name", response.body().getname());
                            storage.write("stud_photo", response.body().getstud_photo());
                            storage.write("emp_number", response.body().getEmp_number());
                            storage.write("ac_code", response.body().getAc_code());
                            storage.write("is_director", response.body().getIs_director());
                            storage.write("seen", "0");
                            storage.write("emp_year_id", response.body().getemp_year_id());
                            storage.write("intitute_id", response.body().getinstitute_id_());
                            storage.write("im_domain_name", response.body().getim_domain_name());

                            init();

                            //   notification();
                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        }
    }

    public void sendIdToserver(String refreshedToken) {
        if (storage.CheckLogin("stud_id", this)) {
            ApiInterface apiInterface = Api_Client.getClient().create(ApiInterface.class);
            Map<String, String> maps;
            maps = new HashMap<String, String>();
            maps.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            maps.put("FCM_ID", refreshedToken);
            maps.put("KEY", "$tUDt&o9&Pk4pp]le$Z");

            Call<FcmResponse> call = apiInterface.getfcmid(maps);
            call.enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {

                    if (response.isSuccessful()) {
//                        Toast.makeText(Main3Activity.this, "Stud Success", Toast.LENGTH_LONG).show();
                    } else {
                        // Toast.makeText(MainActivity.this,"Not Success",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<FcmResponse> call, Throwable t) {

                }
            });
        } else {
            if (storage.CheckLogin("emp_id", this)) {
                ApiInterface apiInterface = Api_Client.getClient().create(ApiInterface.class);
                Map<String, String> maps;
                maps = new HashMap<String, String>();
                maps.put("emp_id", String.valueOf(storage.read("emp_id", 3)));
                maps.put("fcm_id", refreshedToken);
                maps.put("Key", "#Mpt&o9&Pk4pp]le$Z");


                Call<FcmResponse> call = apiInterface.getempfcmid(maps);
                call.enqueue(new Callback<FcmResponse>() {
                    @Override
                    public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {

                        if (response.isSuccessful()) {
                            // Toast.makeText(MainActivity.this,"Emp Success",Toast.LENGTH_LONG).show();


                        } else {
                            //Toast.makeText(MainActivity.this,"Not Success",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<FcmResponse> call, Throwable t) {

                    }
                });
            }

        }
    }


    public void notification() {
//        final ProgressDialog progressDialog = new ProgressDialog(Main3Activity.this, R.style.MyTheme1);
//        progressDialog.setCancelable(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //************ for student*************
        Call<ArrayList<NotificationResponse>> call = null;
        //******************* employee and students both*******************
        Call<ArrayList<NotificationResponse>> call1 = null;
        //******************* employee and students both*******************
        Call<ArrayList<NotificationResponse>> call2 = null;


       /* if (String.valueOf(storage.read("intitute_id", 3)) == null || String.valueOf(storage.read("intitute_id", 3)).equals("")) {
            storage.clear();
            Intent intent = new Intent(Main3Activity.this, Login.class);
            startActivity(intent);
            finish();
        } else {*/
        if (storage.CheckLogin("stud_id", Main3Activity.this)) {
            //******** student in student announcement*************
            call = apiInterface.get_stud_emp_notification("2", String.valueOf(storage.read("ac_id", 3)), String.valueOf(storage.read("dm_id", 3)), String.valueOf(storage.read("course_id", 3)), String.valueOf(storage.read("sm_id", 3)), String.valueOf(storage.read("intitute_id", 3)));

            System.out.println("if student ::::::::::" + call.request());
        } else {
            String permenant_clg_ID = "";

            //*************** in some cases emp_permenant_college is null from api ************
            if (String.valueOf(storage.read("emp_permenant_college", 3)) == null || String.valueOf(storage.read("emp_permenant_college", 3)).compareToIgnoreCase("null") == 0 || String.valueOf(storage.read("emp_permenant_college", 3)).contentEquals("")) {
                permenant_clg_ID = "0";
            } else {
                permenant_clg_ID = String.valueOf(storage.read("emp_permenant_college", 3));
            }
            //********** student in emp announcement**********
            call2 = apiInterface.get_stud_emp_notification("2", permenant_clg_ID, String.valueOf(storage.read("emp_department_id", 3)), "0", "0", String.valueOf(storage.read("intitute_id", 3)));
            System.out.println("student in emp ::::::::::" + call2.request());


            //********* emp in emp announcement************
            call1 = apiInterface.get_stud_emp_notification("1", permenant_clg_ID, String.valueOf(storage.read("emp_department_id", 3)), "0", "0", String.valueOf(storage.read("intitute_id", 3)));

            System.out.println("student in emp call1:::::::: " + call1.request());
            System.out.println("student in emp call2:::::::: " + call2.request());
        }

        final Call<ArrayList<NotificationResponse>> finalCall_emp = call1;
        final Call<ArrayList<NotificationResponse>> finalCall1_stud = call2;

//        System.out.println("call1 request ::::::"+call1.request());
//        System.out.println("call request **************::::::"+call.request());
        // System.out.println("call2 request print ::::::"+call2.request());
        if (storage.CheckLogin("stud_id", Main3Activity.this)) {

            call.enqueue(new Callback<ArrayList<NotificationResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<NotificationResponse>> call, Response<ArrayList<NotificationResponse>> response) {
                    //Log.d("Er",response.toString());
                    if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                        if (response.body().size() >= 1) {

//                            int size_response = response.body().size();
                            int size_response = response.body().size();
//                        int size_response = 10;


                            if (storage.CheckLogin("stud_id", Main3Activity.this)) {

                                System.out.println("size_response:::::::" + size_response);
                                String num_act = String.valueOf(storage.read("Number", 3));
                                int count_stud_announcement = Integer.parseInt(String.valueOf(login_master.read("count_stud_announcement", 1)));
//                                int count_stud_announcement = Integer.parseInt(String.valueOf(login_master.read("count_stud_announcement", 1)));
                                System.out.println("notify::::::::::::" + count_stud_announcement);
                                if (size_response > count_stud_announcement) {
                                    int final_count_stud_ann = size_response - count_stud_announcement;
                                    System.out.println("final_num:::::::::::" + final_count_stud_ann);
                                    txt.setText(String.valueOf(final_count_stud_ann));
                                } else {
                                    txt.setVisibility(View.GONE);
                                }
                            }

                        } else {
                            txt.setVisibility(View.GONE);
                        }

                    } else {
//                        progressDialog.dismiss();
                        Toast.makeText(Main3Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NotificationResponse>> call, Throwable t) {
//                progressDialog.dismiss();
                }
            });

        } else {


            finalCall1_stud.enqueue(new Callback<ArrayList<NotificationResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<NotificationResponse>> finalCall1, Response<ArrayList<NotificationResponse>> response) {
                    //Log.d("Er",response.toString());
                    if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                        if (response.body().size() >= 1) {


                            int count_stud_emp_rsdponse = response.body().size();

                            int count_stud_ann = Integer.parseInt(String.valueOf(login_master.read("count_stud_ann_emp", 1)));
                            System.out.println("count_stud_emp_rsdponse::::::::::::" + count_stud_emp_rsdponse);
                            System.out.println("count_stud_ann::::::::::::" + count_stud_ann);
//

                            if (count_stud_emp_rsdponse > count_stud_ann) {
                                final_count_stud_ann_emp = count_stud_emp_rsdponse - count_stud_ann;
                                System.out.println("final_num:::::::::::" + final_count_stud_ann_emp);
                                // txt.setText(String.valueOf(final_count_stud_ann_emp));
                            }

                        }


                        finalCall_emp.enqueue(new Callback<ArrayList<NotificationResponse>>() {
                            @Override
                            public void onResponse(Call<ArrayList<NotificationResponse>> finalCall, Response<ArrayList<NotificationResponse>> response) {
                                //Log.d("Er",response.toString());
                                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                                    if (response.body().size() >= 1) {

                                        int count_emp_resp = response.body().size();
                                        System.out.println("count_emp_resp::::::::" + count_emp_resp);

                                        int temp = Integer.parseInt(String.valueOf(login_master.read("count_emp_ann_emp", 1)));
                                        System.out.println("count of emp in emp ann :::::::::::" + temp);

                                        if (count_emp_resp > temp) {
                                            final_count_emp_ann_emp = count_emp_resp - temp;
                                            System.out.println("final_count_emp_ann_emp::::::::" + final_count_emp_ann_emp);
                                        }

                                    }
//
//                                    else
//                                    {
//                                        DialogUtils.Show_Toast(Main3Activity.this,"No Records Found");
//                                    }

                                } else {
                                    Toast.makeText(Main3Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<NotificationResponse>> finalCall_emp, Throwable t) {
//                progressDialog.dismiss();
                            }
                        });


                    } else {
                        Toast.makeText(Main3Activity.this, "Please try again later", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NotificationResponse>> finalCall1_stud, Throwable t) {
//                progressDialog.dismiss();
                }
            });


            if (final_count_emp_ann_emp > 0 && final_count_stud_ann_emp > 0) {
                final_emp_ann_count = final_count_emp_ann_emp + final_count_stud_ann_emp;
                System.out.println("final_emp_ann_count>>>>" + final_emp_ann_count);
                txt.setText(String.valueOf(final_emp_ann_count));
            } else {
                txt.setVisibility(View.GONE);
            }


        }

    }

    //}


    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DataStorage.DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("No Connection");
                errorDialog.setMessage("No internet connection Found...!");
                errorDialog.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                //   finish();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }

}




