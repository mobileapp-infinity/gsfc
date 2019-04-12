package com.infinity.infoway.agriculture.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.Pageradapter;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.FcmResponse;
import com.infinity.infoway.agriculture.model.LoginResponse;
import com.infinity.infoway.agriculture.model.NotificationResponse;
import com.infinity.infoway.agriculture.model.ProfileResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.infinity.infoway.agriculture.rest.Api_Client;
import com.infinity.infoway.agriculture.service.Background_Service;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.annotation.ElementType;
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
    ImageView imgprofile, assignment_img, receipt_img, pending_fees_img, img_home_work, img_dashboard, imgtimetable, imgattendance, imgfees, imgnews, imgfeedback, imgsyllabus, imglessionplan, imgexam_tt, img_feecircular;
    TextView nav_profile, nav_change_psw, nav_timetable, nav_attendance, nav_pending_fees, nav_fees, nav_news, nav_feedback, nav_syllabus, nav_lessionplan, nav_share, nav_logout, nav_rec, nav_fee_circular, nav_result, nav_activity, nav_homewrork, nav_assignment, nav_exam_time_table, nav_memberno, nav_version_name, nav_placement, nav_Leave_app, nav_elrning;
    Context ctx;
    TextView nav_lessionplan_a, nav_feedback_a, nav_news_a, nav_share_a;
    ImageView notification_dashboard, img_result, img_elerning, img_placement, img_leave_app;
    DataStorage storage;
    NavigationView mNavigationView;
    CircleImageView imgmenuprofile;
    LinearLayout five_ll;
    TextView txtmenuname, txtmenucourse, tvActionNotification, textfee, txtnews, txtfeedback;
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
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;

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


        get_student_data_from_student_ID();

        sendIdToserver(refreshedToken);

        UpdateUserinterface();

        updateApp();

        // init();

        //notification();

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
        nav_fees = (TextView) findViewById(R.id.nav_fees);
        nav_fees.setOnClickListener(this);
        nav_result = (TextView) findViewById(R.id.nav_result);
        nav_placement = (TextView) findViewById(R.id.nav_placement);
        nav_placement.setOnClickListener(this);
        nav_Leave_app = (TextView) findViewById(R.id.nav_Leave_app);
        nav_Leave_app.setOnClickListener(this);
        nav_elrning = (TextView) findViewById(R.id.nav_elrning);
        nav_elrning.setOnClickListener(this);
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

    public void findviews() {
        notification_dashboard = (ImageView) findViewById(R.id.notification_dashboard);
        notification_dashboard.setOnClickListener(this);
        txt = (TextView) findViewById(R.id.notification_count);
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        if (!storage.isOnline(Main3Activity.this)) {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        }
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("refreshedToken:::::" + refreshedToken);
        //Log.d("refreshedToken", refreshedToken);
        if (storage.CheckLogin("stud_id", Main3Activity.this)) {
            //  Log.d("stud_id", String.valueOf(storage.read("stud_id",3)));
        } else {
            // Log.d("emp_id", String.valueOf(storage.read("emp_id",3)));
        }
        nav_memberno = (TextView) findViewById(R.id.nav_memberno);
        nav_version_name = (TextView) findViewById(R.id.nav_version_name);


        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert pInfo != null;
        System.out.println("version name:::::::" + pInfo.versionName);


        nav_version_name.setText("  " + pInfo.versionName);
        imgprofile = (ImageView) findViewById(R.id.imgprofile);
        five_ll = (LinearLayout) findViewById(R.id.five_ll);
        img_dashboard = (ImageView) findViewById(R.id.img_dashboard);
        img_dashboard.setOnClickListener(this);
        assignment_img = (ImageView) findViewById(R.id.assignment_img);
        assignment_img.setOnClickListener(this);
        imgprofile.setOnClickListener(this);
        receipt_img = (ImageView) findViewById(R.id.receipt_img);
        receipt_img.setOnClickListener(this);
        pending_fees_img = (ImageView) findViewById(R.id.pending_fees_img);
        pending_fees_img.setOnClickListener(this);
        imgexam_tt = (ImageView) findViewById(R.id.iv_mid_exam_tt);
        imgexam_tt.setOnClickListener(this);
        img_feecircular = (ImageView) findViewById(R.id.img_feecircular);
        img_feecircular.setOnClickListener(this);
        imgtimetable = (ImageView) findViewById(R.id.imgtimetable);
        imgtimetable.setOnClickListener(this);
        imgfees = (ImageView) findViewById(R.id.imgfees);
        imgfees.setOnClickListener(this);
        imgnews = (ImageView) findViewById(R.id.imgnews);
        imgnews.setOnClickListener(this);
        imgattendance = (ImageView) findViewById(R.id.imgattendance);
        imgattendance.setOnClickListener(this);
        imgfeedback = (ImageView) findViewById(R.id.imgfeedback);
        imgfeedback.setOnClickListener(this);
        imgsyllabus = (ImageView) findViewById(R.id.imgsyllabus);
        imgsyllabus.setOnClickListener(this);
        imglessionplan = (ImageView) findViewById(R.id.imglessionplan);
        imglessionplan.setOnClickListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        textfee = (TextView) findViewById(R.id.textfee);
        txtnews = (TextView) findViewById(R.id.txtnews);
        txtfeedback = (TextView) findViewById(R.id.txtfeedback);
        studentlayout = (LinearLayout) findViewById(R.id.studentlayout);
        llactionbar = (LinearLayout) findViewById(R.id.llactionbar);
        img_result = (ImageView) findViewById(R.id.img_result);
        img_result.setOnClickListener(this);
        img_elerning = (ImageView) findViewById(R.id.img_elerning);
        img_elerning.setOnClickListener(this);
        img_placement = (ImageView) findViewById(R.id.img_placement);
        img_placement.setOnClickListener(this);
        img_leave_app = (ImageView) findViewById(R.id.img_leave_app);
        img_leave_app.setOnClickListener(this);
//        if (storage.read("emp_id", 3).equals("1716"))
//        {
//            img_dashboard.setVisibility(View.VISIBLE);
//
//        }
//        else
//            {
//            img_dashboard.setVisibility(View.INVISIBLE);
//        }

    }

    public void UpdateUserinterface() {
        if (storage.CheckLogin("emp_id", this)) {
            ll_view_more.setVisibility(View.GONE);
            ll_views_hide.setVisibility(View.VISIBLE);
            studentlayout.setVisibility(View.VISIBLE);

            //textfee.setText("Remaining Attendance");
            textfee.setText("Pending Attendance");


            //imgfees.setImageDrawable(getResources().getDrawable(R.drawable.remainingattendance));
            imgfees.setImageDrawable(getResources().getDrawable(R.drawable.panding));


            txtnews.setText("Leave");
            //imgnews.setImageDrawable(getResources().getDrawable(R.drawable.leave));
            imgsyllabus.setImageDrawable(getResources().getDrawable(R.drawable.leave));
//            txtfeedback.setText("Lecture \n Plan");
//            imgfeedback.setImageDrawable(getResources().getDrawable(R.drawable.lectureplan));
            //imglessionplan.setImageDrawable(getResources().getDrawable(R.drawable.lectureplan));
            imglessionplan.setImageDrawable(getResources().getDrawable(R.drawable.lessionplan));

            //for  re arranging emp dashboard
            //imgnews.setImageDrawable(getResources().getDrawable(R.drawable.news));
            imgnews.setVisibility(View.GONE);
            img_elerning.setImageDrawable(getResources().getDrawable(R.drawable.news));
            //img_placement.setImageDrawable(getResources().getDrawable(R.drawable.panding));
            nav_fees.setText("Pending Attendance");
            nav_fees.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.remaining_attendance, 0, 0, 0);

            nav_news.setText("News");
            nav_news.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.news, 0, 0, 0);

            nav_feedback.setText("Lession Plan");
            nav_feedback.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.lesson_plan, 0, 0, 0);

           // nav_lessionplan.setText("Pending Attendance");
            nav_lessionplan.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.time_table, 0, 0, 0);
             nav_lessionplan.setVisibility(View.GONE);
            nav_syllabus.setVisibility(View.GONE);
            nav_pending_fees.setVisibility(View.GONE);
            nav_rec.setVisibility(View.GONE);
            nav_logout.setVisibility(View.VISIBLE);


            imgattendance.setVisibility(View.GONE);
            receipt_img.setImageDrawable(getResources().getDrawable(R.drawable.attendance));
            five_ll.setVisibility(View.GONE);
            img_feecircular.setVisibility(View.INVISIBLE);
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
            img_leave_app.setVisibility(View.INVISIBLE);
            img_placement.setVisibility(View.INVISIBLE);
            //img_elerning.setVisibility(View.GONE);

            imgfeedback.setVisibility(View.GONE);
            if (storage.read("emp_id", 3).equals("1716")) {
                img_dashboard.setVisibility(View.VISIBLE);
                img_dashboard.setImageDrawable(getResources().getDrawable(R.drawable.dashboard_2));
//                img_dashboard.setPadding(0,0,15,0);
                nav_logout.setVisibility(View.INVISIBLE);
                imgexam_tt.setVisibility(View.GONE);
            }

        } else if (storage.CheckLogin("stud_id", this)) {
            assignment_img.setVisibility(View.VISIBLE);
            pending_fees_img.setVisibility(View.GONE);
            img_feecircular.setVisibility(View.GONE);
            imgexam_tt.setVisibility(View.GONE);
            img_dashboard.setVisibility(View.VISIBLE);
            img_dashboard.setImageDrawable(getResources().getDrawable(R.drawable.activity_icon));
//                imgfeedback.setVisibility(View.VISIBLE);
            nav_feedback.setVisibility(View.GONE);
            nav_news.setVisibility(View.VISIBLE);
            nav_logout.setVisibility(View.VISIBLE);
            imgfeedback.setVisibility(View.VISIBLE);
            imgfeedback.setImageDrawable(getResources().getDrawable(R.drawable.homework_icon_2));
            img_result.setVisibility(View.VISIBLE);
            five_ll.setVisibility(View.VISIBLE);
        } else {
            nav_logout.setVisibility(View.VISIBLE);
            img_dashboard.setVisibility(View.GONE);

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
                Intent intent = new Intent(Main3Activity.this, Notification_Activity.class);
                intent.putExtra("type", "notification");
                startActivity(intent);
                //  showProfileMenuPopup(v);
                listPopupWindow = new ListPopupWindow(Main3Activity.this);
                listPopupWindow.setAdapter(new Main3Activity.listpopupadapter(products, "asiji"));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Main3Activity.this, Notification_Activity.class);
        intent.putExtra("position", 3);
        intent.putExtra("title", products.get(position));
        intent.putExtra("type", "notification");
        intent.putExtra("description", "Description");
        startActivity(intent);
        //productName.setText(products[position]);
        listPopupWindow.dismiss();
    }

/*
    public void showProfileMenuPopup(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.notification_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                int id = menuItem.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.nav_profile)
                {
                    Intent intent= new Intent(Main3Activity.this,Notification_Activity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
        popup.show();
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // call the updating code on the main thread,
// so we can call this asynchronously
    public void updateHotCount(final int new_hot_number) {
        //if (tvActionNotification == null) return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    tvActionNotification.setVisibility(View.INVISIBLE);
                else {
                    tvActionNotification.setVisibility(View.VISIBLE);
                    tvActionNotification.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        // @Override abstract public void onClick(View v);

        @Override
        public boolean onLongClick(View v) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgprofile:
                Intent intent = new Intent(Main3Activity.this, Profile.class);
                //Intent intent = new Intent(Main3Activity.this, ELearningActivity.class);
                startActivity(intent);
                break;

            case R.id.receipt_img:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent fee_intent = new Intent(Main3Activity.this, Receipt_Activity.class);
                    startActivity(fee_intent);
                } else {
                    Intent fee_intent = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(fee_intent);
                }

                break;

            case R.id.img_elerning:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intent_elerning = new Intent(Main3Activity.this, ELearningActivity.class);
                    startActivity(intent_elerning);

                } else {
                    Intent intent_elerning = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intent_elerning);
                }
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

            case R.id.img_leave_app:
                Intent intent_leave_app = new Intent(Main3Activity.this, Leaveapplication.class);
                startActivity(intent_leave_app);
                break;


            case R.id.img_feecircular:
                Intent intent_img_feecircular = new Intent(Main3Activity.this, FeeCircularActivity.class);
                intent_img_feecircular.putExtra("fc_file", String.valueOf(storage.read("fc_file", 3)));
                startActivity(intent_img_feecircular);
                break;

            case R.id.nav_fee_circular:
                Intent intent_nav_fee_circular = new Intent(Main3Activity.this, FeeCircularActivity.class);
                intent_nav_fee_circular.putExtra("fc_file", String.valueOf(storage.read("fc_file", 3)));
                startActivity(intent_nav_fee_circular);
                break;

            case R.id.nav_change_psw:
                Intent change_psw = new Intent(Main3Activity.this, ChangePasswordActivity.class);
                startActivity(change_psw);
//                finish();
//                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                break;


            case R.id.img_result:
                Intent img_result_intent = new Intent(Main3Activity.this, ResultActivity.class);
                //Intent img_result_intent = new Intent(Main3Activity.this, Leaveapplication.class);
                startActivity(img_result_intent);
                break;

            case R.id.imgtimetable:
                startActivity(new Intent(Main3Activity.this, Timetable.class));
                break;

            case R.id.imgattendance:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intentattendance = new Intent(Main3Activity.this, Syllabus.class);
                    startActivity(intentattendance);
                } else {
                    Intent intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(intentattendance);
                }
                break;

            case R.id.imgfees:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    startActivity(new Intent(Main3Activity.this, Fees_Activity.class));
                } else {
                    startActivity(new Intent(Main3Activity.this, FacultyAttendance.class));
                }
                break;

            case R.id.imgnews:

                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intentimgnews);
                } else {
                    Intent intentimgnews = new Intent(Main3Activity.this, Notification_Activity.class);
                    intentimgnews.putExtra("type", "news");
                    startActivity(intentimgnews);
                }


                break;

            case R.id.imgsyllabus:

                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentimgnews = new Intent(Main3Activity.this, Leave_emp.class);
                    startActivity(intentimgnews);
                } else {
                    Intent intentimgsyllabus = new Intent(Main3Activity.this, Student_Attendance.class);
                    startActivity(intentimgsyllabus);
                }

                break;

            case R.id.assignment_img:
                Intent intent_ass_img = new Intent(Main3Activity.this, AssignmentActivity.class);
                startActivity(intent_ass_img);
                break;

            case R.id.pending_fees_img:
                Intent pending_fees = new Intent(Main3Activity.this, PendingfeesActivity.class);
                startActivity(pending_fees);
                break;

            case R.id.iv_mid_exam_tt:
                Intent intet_tt_mid = new Intent(Main3Activity.this, ExamDisplayTimeTable.class);
                startActivity(intet_tt_mid);
                break;


            case R.id.img_dashboard:

                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intent1 = new Intent(Main3Activity.this, Student_Main_Activity.class);
                    startActivity(intent1);
                } else {

                    Intent dashboard = new Intent(Main3Activity.this, DirectivePageActivity.class);
                    startActivity(dashboard);
                    finish();
                }
                break;


            case R.id.nav_placement:
                Intent intent1 = new Intent(Main3Activity.this, PlacementActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_elrning:
                Intent intent2 = new Intent(Main3Activity.this, ELearningActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_Leave_app:
                Intent intent3 = new Intent(Main3Activity.this, Leaveapplication.class);
                startActivity(intent3);
                break;

            case R.id.imglessionplan:
                if (storage.CheckLogin("emp_id", Main3Activity.this))
                {
                    Intent imgfeedback = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    //Intent imgfeedback = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(imgfeedback);
                } else {
                    Intent imglessionplan = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(imglessionplan);
                }
                break;

            case R.id.imgfeedback:
                if (storage.CheckLogin("stud_id", Main3Activity.this))
                {
                    Intent imgfeedback = new Intent(Main3Activity.this, HomeworkActivity.class);
                    startActivity(imgfeedback);
                }
                break;


            case R.id.notification_dashboard:
                {
                Intent intent1_noty = new Intent(Main3Activity.this, Notification_Activity.class);
                intent1_noty.putExtra("type", "notification");
                startActivity(intent1_noty);
                txt.setVisibility(View.GONE);
                break;
            }


            case R.id.nav_rec: {
                Intent intent2_rec = new Intent(Main3Activity.this, Receipt_Activity.class);
                startActivity(intent2_rec);
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
                break;

            case R.id.nav_timetable:
                Intent intentmenutimetable = new Intent(Main3Activity.this, Timetable.class);
                startActivity(intentmenutimetable);
                break;

            case R.id.nav_attendance:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    Intent intentattendance = new Intent(Main3Activity.this, Student_Attendance.class);
                    startActivity(intentattendance);
                } else {
                    Intent intentattendance = new Intent(Main3Activity.this, EmployeeAttendance.class);
                    startActivity(intentattendance);
                }
                break;

            case R.id.nav_fees:
                if (storage.CheckLogin("stud_id", Main3Activity.this)) {
                    startActivity(new Intent(Main3Activity.this, Fees_Activity.class));
                } else {
                    startActivity(new Intent(Main3Activity.this, FacultyAttendance.class));
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
                if (storage.CheckLogin("emp_id", Main3Activity.this))
                {
                    Intent intentimgnews = new Intent(Main3Activity.this, NewsGroupWise.class);
                    startActivity(intentimgnews);
                } else {
                    Intent intent1_noti = new Intent(Main3Activity.this, Notification_Activity.class);
                    // intent1.putExtra("type", "news");
                    startActivity(intent1_noti);
                }
                break;

            case R.id.nav_syllabus:
                Intent intentmenusyllabus = new Intent(Main3Activity.this, Syllabus.class);
                startActivity(intentmenusyllabus);
                break;


            case R.id.nav_pending_fees:
                Intent pending_fees_nav = new Intent(Main3Activity.this, PendingfeesActivity.class);
                startActivity(pending_fees_nav);
                break;

            case R.id.nav_lessionplan:
                if (storage.CheckLogin("emp_id", Main3Activity.this)) {
                    Intent intentmenuplan = new Intent(Main3Activity.this, FacultyAttendance.class);
                    startActivity(intentmenuplan);
                } else {
                    Intent intentmenuplan = new Intent(Main3Activity.this, NewEmpLectPlan.class);
                    startActivity(intentmenuplan);
                }

                break;


            case R.id.nav_result:
                Intent intent_nav_result = new Intent(Main3Activity.this, ResultActivity.class);
                startActivity(intent_nav_result);
                break;


            case R.id.nav_homework:
                Intent intent_homework = new Intent(Main3Activity.this, HomeworkActivity.class);
                startActivity(intent_homework);
                break;


            case R.id.nav_asnmnt:
                Intent intent_nav_as = new Intent(Main3Activity.this, AssignmentActivity.class);
                startActivity(intent_nav_as);
                break;

            case R.id.nav_exam_TT:
                Intent intent_exam_tt = new Intent(Main3Activity.this, ExamDisplayTimeTable.class);
                startActivity(intent_exam_tt);
                break;


            case R.id.nav_acivity:
                Intent act = new Intent(Main3Activity.this, Student_Main_Activity.class);
                startActivity(act);
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
                String shareBody = "https://play.google.com/store/apps/details?id=com.infinity.infoway.agriculture&hl=en";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Agriculture Campus App... ");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
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

                                               /*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*/
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

                                               /*Intent i=new Intent(MainActivity.this,MainActivity.class);
                                               i.putExtra("Exit me", true);
                                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                               startActivity(i);*/
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


    public void get_student_data_from_student_ID()

    {
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

                        notification();

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

                            notification();
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
        Call<ArrayList<NotificationResponse>> call;


       /* if (String.valueOf(storage.read("intitute_id", 3)) == null || String.valueOf(storage.read("intitute_id", 3)).equals("")) {
            storage.clear();
            Intent intent = new Intent(Main3Activity.this, Login.class);
            startActivity(intent);
            finish();
        } else {*/
        if (storage.CheckLogin("stud_id", Main3Activity.this)) {
            call = apiInterface.get_stud_emp_notification("2", String.valueOf(storage.read("ac_id", 3)), String.valueOf(storage.read("dm_id", 3)), String.valueOf(storage.read("course_id", 3)), String.valueOf(storage.read("sm_id", 3)), String.valueOf(storage.read("intitute_id", 3)));
        } else {
            call = apiInterface.get_stud_emp_notification("1", String.valueOf(storage.read("emp_main_school_id", 3)), String.valueOf(storage.read("ac_code", 3)), "0", "0", String.valueOf(storage.read("intitute_id", 3)));
        }

        call.enqueue(new Callback<ArrayList<NotificationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationResponse>> call, Response<ArrayList<NotificationResponse>> response) {
                //Log.d("Er",response.toString());
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                    if (response.body().size() >= 1) {
                        // Log.d("Count", String.valueOf(listView.getAdapter().getCount()));
                        String num = String.valueOf(response.body().size());
                        storage.write("Number", num);
                        if (storage.read("seen", 3).equals("0"))
                            txt.setText(num);
                        else
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
        //}


    }

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




