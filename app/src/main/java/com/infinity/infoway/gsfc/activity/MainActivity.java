package com.infinity.infoway.gsfc.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.Pageradapter;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.FcmResponse;
import com.infinity.infoway.gsfc.model.LoginResponse;
import com.infinity.infoway.gsfc.model.ProfileResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;
import com.infinity.infoway.gsfc.rest.Api_Client;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    private static final int MENU_ADD = 0;
    ImageView imgprofile, imgtimetable, imgattendance, imgfees, imgnews, imgfeedback, imgsyllabus, imglessionplan;
    TextView nav_profile, nav_timetable, nav_attendance, nav_fees, nav_news, nav_feedback, nav_syllabus, nav_lessionplan, nav_share, nav_logout;
    Context ctx;
    DataStorage storage;
    NavigationView mNavigationView;
    CircleImageView imgmenuprofile;
    TextView txtmenuname, txtmenucourse, tvActionNotification, textfee, txtnews, txtfeedback;
    LinearLayout studentlayout;
    ListPopupWindow listPopupWindow;
    Toolbar toolbar;
    View navHeaderView;
    ArrayList<String> products = new ArrayList<String>();
    String refreshedToken;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("Exit me", false))
        {
            finish();
            System.exit(0);
            return;
            // add this to prevent from doing unnecessary stuffs
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // checking for type intent filter
                if (intent.getAction().equals(DataStorage.REGISTRATION_COMPLETE))
                {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(DataStorage.TOPIC_GLOBAL);
                    // displayFirebaseRegId();
                } else if (intent.getAction().equals(DataStorage.PUSH_NOTIFICATION))
                {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        navHeaderView = navigationView.inflateHeaderView(R.layout.view_menu);

        MenuSlice();

        sendIdToserver(refreshedToken);

        UpdateUserinterface();

        updateApp();

        init();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if (doubleBackToExitPressedOnce)
            {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast toast = Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            new Handler().postDelayed(new Runnable()
            {
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
    }

    public void MenuSlice()
    {
        View headerLayout = mNavigationView.getHeaderView(0);
        imgmenuprofile = (CircleImageView) headerLayout.findViewById(R.id.imgmenuprofile);
        txtmenuname = (TextView) headerLayout.findViewById(R.id.txtmenuname);
        txtmenucourse = (TextView) headerLayout.findViewById(R.id.txtmenucourse);
        nav_profile = (TextView) headerLayout.findViewById(R.id.nav_profile);
        nav_profile.setOnClickListener(this);
        nav_timetable = (TextView) headerLayout.findViewById(R.id.nav_timetable);
        nav_timetable.setOnClickListener(this);
        nav_attendance = (TextView) headerLayout.findViewById(R.id.nav_attendance);
        nav_attendance.setOnClickListener(this);
        nav_fees = (TextView) headerLayout.findViewById(R.id.nav_fees);
        nav_fees.setOnClickListener(this);
        nav_news = (TextView) headerLayout.findViewById(R.id.nav_news);
        nav_news.setOnClickListener(this);
        nav_feedback = (TextView) headerLayout.findViewById(R.id.nav_feedback);
        nav_feedback.setOnClickListener(this);
        nav_syllabus = (TextView) headerLayout.findViewById(R.id.nav_syllabus);
        nav_syllabus.setOnClickListener(this);
        nav_lessionplan = (TextView) headerLayout.findViewById(R.id.nav_lessionplan);
        nav_lessionplan.setOnClickListener(this);
        nav_share = (TextView) headerLayout.findViewById(R.id.nav_share);
        nav_share.setOnClickListener(this);
        nav_logout = (TextView) headerLayout.findViewById(R.id.nav_logout);
        nav_logout.setOnClickListener(this);

        txtmenuname.setText(String.valueOf(storage.read("name", 3)));
        txtmenucourse.setText(String.valueOf(storage.read("course_fullname", 3)));


       /* if(!storage.CheckLogin("stud_id",ctx))
        {
           //nav_feedback.setVisibility(View.GONE);
            //nav_news.setVisibility(View.GONE);
           nav_fees.setText("Remaining Attendance");
        }*/
        if (!String.valueOf(storage.read("stud_photo", 3)).equals("") || !(String.valueOf(storage.read("stud_photo", 3)) == null)) {
            Drawable mDefaultBackground = getResources().getDrawable(R.drawable.defaultprofile);
            Glide.with(MainActivity.this).load(String.valueOf(storage.read("stud_photo", 3))).fitCenter().error(mDefaultBackground).into(imgmenuprofile);
        }
    }

    public void findviews()
    {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (storage.CheckLogin("stud_id", MainActivity.this)) {
            // Log.d("stud_id", String.valueOf(storage.read("stud_id",3)));
        } else {
            //  Log.d("emp_id", String.valueOf(storage.read("emp_id",3)));
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");

        imgprofile = (ImageView) findViewById(R.id.imgprofile);
        imgprofile.setOnClickListener(this);
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
    }

    public void UpdateUserinterface() {
        if (storage.CheckLogin("emp_id", this)) {
            studentlayout.setVisibility(View.GONE);
            textfee.setText("Remaining Attedance");
            imgfees.setImageDrawable(getResources().getDrawable(R.drawable.remainingattendance));
            txtnews.setText("Leave");
            imgnews.setImageDrawable(getResources().getDrawable(R.drawable.leave));
            txtfeedback.setText("Lecture \n Plan");
            imgfeedback.setImageDrawable(getResources().getDrawable(R.drawable.lectureplan));
            nav_fees.setText("Rem. Attendance");
            //nav_fees.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon, 0, 0, 0);
            nav_fees.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.remaining_attendance, 0, 0, 0);
            nav_news.setText("Leave");
            nav_news.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.leave, 0, 0, 0);
            nav_feedback.setText("Lecture Plan");
            nav_feedback.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.lecture_plan, 0, 0, 0);
            nav_lessionplan.setVisibility(View.GONE);
            nav_syllabus.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        final View menu_hotlist = menu.findItem(R.id.action_notification).getActionView();
        tvActionNotification = (TextView) menu_hotlist.findViewById(R.id.notification_count);
        updateHotCount(products.size());

        new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Notification_Activity.class);
                Intent intent;
                if (storage.CheckLogin("stud_id",MainActivity.this))
                {
                    intent = new Intent(MainActivity.this, AnnouncementStudentActiivty.class);

                }
                else
                {
                    intent = new Intent(MainActivity.this,AnnouncementFaculty.class);

                }
                intent.putExtra("type", "notification");
                startActivity(intent);
                //  showProfileMenuPopup(v);
                listPopupWindow = new ListPopupWindow(MainActivity.this);
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
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setModal(true);
                listPopupWindow.setOnItemClickListener(MainActivity.this);
                listPopupWindow.show();
/*
                 productName.setOnClickListener(new DialogInterface.OnClickListener()
                {

                });
*/

            }
        };

        return super.onCreateOptionsMenu(menu);

       /* getMenuInflater().inflate(R.menu.main, menu);
        return true;*/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(MainActivity.this, Notification_Activity.class);


        Intent intent = new Intent(MainActivity.this, AnnouncementStudentActiivty.class);
        intent.putExtra("position", 3);
        intent.putExtra("type", "notification");
        intent.putExtra("title", products.get(position));
        intent.putExtra("description", "Description");
        startActivity(intent);
        //productName.setText(products[position]);
        listPopupWindow.dismiss();
    }

/*  public void showProfileMenuPopup(View v)
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
                    Intent intent=new Intent(MainActivity.this,Notification_Activity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
        popup.show();
    }*/

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
                else
                {
                    tvActionNotification.setVisibility(View.VISIBLE);
                    tvActionNotification.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
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

            if (midy < displayFrame.height())
            {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            }
            else
            {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }

    /* @SuppressWarnings("StatementWithEmptyBody")
     @Override
     public boolean onNavigationItemSelected(MenuItem item)
     {
         // Handle navigation view item clicks here.
         int id = item.getItemId();

         if (id == R.id.nav_profile)
         {
             // Handle the camera action
             Intent intent=new Intent(MainActivity.this,Profile.class);
             startActivity(intent);
         } else if (id == R.id.nav_fees)
         {

         } else if (id == R.id.nav_feedback)
         {

         } else if (id == R.id.nav_attendance)
         {
             if(storage.CheckLogin("stud_id",MainActivity.this))
             {
                 Intent intentattendance = new Intent(MainActivity.this, Student_Attendance.class);
                 startActivity(intentattendance);
             }else {
                 Intent intentattendance = new Intent(MainActivity.this, Employee_Attendance.class);
                 startActivity(intentattendance);
             }

         } else if (id == R.id.nav_timetable)
         {
             Intent intenttimetable=new Intent(MainActivity.this,Timetable.class);
             startActivity(intenttimetable);

         } else if (id == R.id.nav_news)
         {

         }else if (id == R.id.nav_logout)
         {
             AlertDialog myQuittingDialogBox =new AlertDialog.Builder(MainActivity.this)
                     //set message, title, and icon
                     .setTitle("Logout")
                     .setMessage("Do you want to logout from this App?")
                     .setPositiveButton("yes", new DialogInterface.OnClickListener()
                     {
                         public void onClick(DialogInterface dialog, int whichButton)
                         {
                             storage.clear();
                             Intent intent=new Intent(MainActivity.this,Login.class);
                             startActivity(intent);
                             finish();
                         }
                     })

                     .setNeutralButton("No", new DialogInterface.OnClickListener()
                     {
                         public void onClick(DialogInterface dialog, int whichButton)
                         {

                             dialog.dismiss();
                         }
                     }).create();

             myQuittingDialogBox.show();
         }

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
         return true;
     }
 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgprofile:
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                break;

            case R.id.imgtimetable:
                Intent intenttimetable = new Intent(MainActivity.this, Timetable.class);
                startActivity(intenttimetable);
                break;

            case R.id.imgattendance:
                if (storage.CheckLogin("stud_id", MainActivity.this)) {
                    Intent intentattendance = new Intent(MainActivity.this, Student_Attendance.class);
                    startActivity(intentattendance);
                } else {
                    Intent intentattendance = new Intent(MainActivity.this, Employee_Attendance.class);
                    startActivity(intentattendance);
                }
                break;

            case R.id.imgfees:
                if (storage.CheckLogin("stud_id", MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, Fees_Activity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Remaining_Attendance.class));
                }
                break;

            case R.id.imgnews:
                if (storage.CheckLogin("emp_id", MainActivity.this)) {
                    Intent intentimgnews = new Intent(MainActivity.this, EmployeeLeave.class);
                    startActivity(intentimgnews);
                }
                break;

            case R.id.imgsyllabus:
                Intent intentimgsyllabus = new Intent(MainActivity.this, Syllabus.class);
                startActivity(intentimgsyllabus);
                break;

            case R.id.imglessionplan:
                Intent imglessionplan = new Intent(MainActivity.this, Lectureplan.class);
                startActivity(imglessionplan);
                break;

            case R.id.imgfeedback:
                if (storage.CheckLogin("emp_id", MainActivity.this)) {
                    Intent imgfeedback = new Intent(MainActivity.this, Lectureplan.class);
                    startActivity(imgfeedback);
                }
                break;

            //MENU

            case R.id.nav_profile:
                Intent intmenuprofile = new Intent(MainActivity.this, Profile.class);
                startActivity(intmenuprofile);
                break;

            case R.id.nav_timetable:
                Intent intentmenutimetable = new Intent(MainActivity.this, Timetable.class);
                startActivity(intentmenutimetable);
                break;

            case R.id.nav_attendance:
                if (storage.CheckLogin("stud_id", MainActivity.this)) {
                    Intent intentattendance = new Intent(MainActivity.this, Student_Attendance.class);
                    startActivity(intentattendance);
                } else {
                    Intent intentattendance = new Intent(MainActivity.this, Employee_Attendance.class);
                    startActivity(intentattendance);
                }
                break;

            case R.id.nav_fees:
                if (storage.CheckLogin("stud_id", MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, Fees_Activity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Remaining_Attendance.class));
                }
                break;

            case R.id.nav_news:
                if (storage.CheckLogin("emp_id", MainActivity.this)) {
                    Intent intentimgnews = new Intent(MainActivity.this, EmployeeLeave.class);
                    startActivity(intentimgnews);
                }
                break;

            case R.id.nav_syllabus:
                Intent intentmenusyllabus = new Intent(MainActivity.this, Syllabus.class);
                startActivity(intentmenusyllabus);
                break;

            case R.id.nav_lessionplan:
                Intent intentmenuplan = new Intent(MainActivity.this, Lectureplan.class);
                startActivity(intentmenuplan);
                break;

            case R.id.nav_feedback:
                if (storage.CheckLogin("emp_id", MainActivity.this)) {
                    Intent imgfeedback = new Intent(MainActivity.this, Lectureplan.class);
                    startActivity(imgfeedback);
                }
                break;

            case R.id.nav_logout:
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MainActivity.this)
                        //set message, title, and icon
                        .setTitle("Logout")
                        .setMessage("Do you want to logout from this App?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                storage.clear();
                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                                finish();
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
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Agriculture Junagadh Campus App... ");
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
                    if (response.body().getVersion() == 1) {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MainActivity.this)
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
                    } else if (response.body().getVersion() == 2) {
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MainActivity.this)
                                //set message, title, and icon
                                .setTitle("New version available in.Would you like to update your app ?")
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
                                }).create();

                        myQuittingDialogBox.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_LONG).show();

            }
        });

    }


    private void init() {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.getSliderImages(String.valueOf(storage.read("im_domain_name", 3)),String.valueOf(storage.read("intitute_id",3)));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                // Toast.makeText(MainActivity.this,  "products found", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    ArrayList<String> resp = response.body().geturl();
                    for (int i = 0; i < resp.size(); i++)
                        XMENArray.add(resp.get(i));

                    mPager = (ViewPager) findViewById(R.id.pager);
                    mPager.setAdapter(new Pageradapter(MainActivity.this, XMENArray));
                    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                    indicator.setViewPager(mPager);

                    // Auto start of viewpager
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable()
                    {
                        public void run()
                        {
                            if (currentPage == XMENArray.size())
                            {
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
                    }, 2500, 2500);
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

    public void sendIdToserver(String refreshedToken) {
        if (storage.CheckLogin("stud_id", this)) {
            ApiInterface apiInterface = Api_Client.getClient().create(ApiInterface.class);
            Map<String, String> maps;
            maps = new HashMap<String, String>();
            maps.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            maps.put("fcm_id", refreshedToken);
            maps.put("Key", "$tUDt&o9&Pk4pp]le$Z");


            Call<FcmResponse> call = apiInterface.getfcmid(maps);
            call.enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {

                    if (response.isSuccessful()) {
                        // Toast.makeText(MainActivity.this,"Stud Success",Toast.LENGTH_LONG).show();
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
}
