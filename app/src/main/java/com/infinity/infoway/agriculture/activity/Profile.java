package com.infinity.infoway.agriculture.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.ProfileResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity
{

    Context ctx;
    DataStorage storage;
    CircleImageView profileimage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ImageView imgcollegedetail;

    LinearLayout profilebackground, familydetails, lleducationdetail, llemppersonaldetail, llstudentpersonaldetail, llempaccountdetail, llstudentcollegedetails, llofficialdetailemp;
    TextView textcollegedetails, txtfthermobilenoheader, txtprocourse, txtproname;

    //Student Detail
    TextView txtname, txtheadername, txtgender, txtemail, txtbirthdate, txtadharno, txtbloodgroup, txtmobileno, txtcasteemp;
    TextView txtcollege, txtdepartment, txtcourse, txtsemester, txtadmissionno, txtenrollmentno, txtadmissionyear, txtadmissiontype, txtfeetype, txtstudentquata, txtshift;
    TextView txtaddressline1, txtaddressline2, txtfathermobileno, txtcity, txtstate, txtcountry;

    //Employee Detail
    TextView txtnameemp, txtgenderemp, txtemailemp, txtbirthdateemp, txtadharnoemp, txtbloodgroupemp, txtmobilenoemp;
    TextView txtdepartmentnameemp, txtjobnameemp, txtempnumber, txtjoindateemp, txtofferleternoemp, txtcardnoemp, txtcategoryemp, txtpannoemp, txtpassportnoemp, txtdrivingnoemp, txtjobtimefromemp, txtjobtimetoemp;
    TextView txtbanknameemp, txtaccountnoemp, txtbranchnameemp, txtaccounttypeemp;

    //FAmily details
    TextView txtFathername, txtFatherOccupation, txtFatherContactname, txtFatherAddress, txtMothername, txtMotherOccupation, txtSpouseName, txtSpouseOccupation, txtSonDaughterName, txtSonDaughterDateofBirth;

    //educational details
    TextView education;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findviews();

        toolbar.post(new Runnable()
        {
            @Override
            public void run() {
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

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        assert ab != null;
        // ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);


        if (!storage.isOnline(Profile.this)) {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        }

        if (storage.CheckLogin("stud_id", this)) {
            llemppersonaldetail.setVisibility(View.GONE);
            llempaccountdetail.setVisibility(View.GONE);
            llofficialdetailemp.setVisibility(View.GONE);
            familydetails.setVisibility(View.GONE);
            lleducationdetail.setVisibility(View.GONE);
            final ProgressDialog progressDialog = new ProgressDialog(Profile.this, R.style.MyTheme1);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            mParams.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));
            mParams.put("school_id", String.valueOf(storage.read("ac_id", 3)));

            Call<ProfileResponse> call = apiService.getStudentProfile(mParams);

            call.enqueue(new Callback<ProfileResponse>()

            {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        //Presonal Details
                        collapsingToolbarLayout.setTitle(response.body().getName());
                        txtheadername.setText(response.body().getName());
                        txtname.setText(response.body().getName());
                        txtproname.setText(response.body().getName());
                        txtgender.setText(response.body().getGender());
                        txtbirthdate.setText(response.body().getBirthdate());
                        txtadharno.setText(response.body().getAdharno());
                        txtbloodgroup.setText(response.body().getBloodgroup());
                        txtemail.setText(response.body().getStud_email());
                        txtmobileno.setText(response.body().getStud_mobile_no());
                        txtmobileno.setText(response.body().getStud_mobile_no());

                        //Collage Details
                        txtcollege.setText(response.body().getSchoolName());
                        txtdepartment.setText(response.body().getDepartmentName());
                        txtcourse.setText(response.body().getCourseName());
                        txtprocourse.setText(response.body().getCourseName());
                        txtsemester.setText(response.body().getSemester());
                        txtadmissionno.setText(response.body().getAdmissionno());
                        txtenrollmentno.setText(response.body().getEnrollno());
                        txtadmissionyear.setText(response.body().getAdmissionyear());
                        txtadmissiontype.setText(response.body().getAdmissionType());
                        txtfeetype.setText(response.body().getFee_Type());
                        txtstudentquata.setText(response.body().getStudent_Quata());
                        txtshift.setText(response.body().getStud_Shift());

                        //Contact Details
                        txtaddressline1.setText(response.body().getStud_Address());
                        txtaddressline2.setText(response.body().getStud_Address1());
                        txtfathermobileno.setText(response.body().getStud_father_mobile_no());
                        txtcity.setText(response.body().getcity_name());
                        txtstate.setText(response.body().getstate_name());
                        txtcountry.setText(response.body().getcountry_name());

                        //Account details
                        txtbanknameemp.setText(response.body().getemp_bank_name());
                        txtaccountnoemp.setText(response.body().getemp_account_no());
                        txtbranchnameemp.setText(response.body().getemp_branch_name());
                        txtaccounttypeemp.setText(response.body().getemp_account_type());

                        //family details


                    } else {
                        // txtname.setText("Unsuccessfull");
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    // txtname.setText("Failure");
                    //  Log.e("Profile", t.toString());
                }
            });
        } else {
            textcollegedetails.setText("ACCOUNT DETAILS");
            txtfthermobilenoheader.setText("Home Mobile No");
            llstudentpersonaldetail.setVisibility(View.GONE);
            llstudentcollegedetails.setVisibility(View.GONE);
            llempaccountdetail.setVisibility(View.VISIBLE);
            imgcollegedetail.setBackground(getResources().getDrawable(R.drawable.ic_account_balance));
            final ProgressDialog progressDialog = new ProgressDialog(Profile.this, R.style.MyTheme1);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            mParams.put("year_id", String.valueOf(storage.read("swd_year_id", 3)));
            mParams.put("school_id", String.valueOf(storage.read("ac_id", 3)));

            Call<List<ProfileResponse>> call = apiService.getEmployeeProfile(String.valueOf(storage.read("emp_id", 3)));

            call.enqueue(new Callback<List<ProfileResponse>>() {
                @Override
                public void onResponse(Call<List<ProfileResponse>> call, Response<List<ProfileResponse>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful())
                    {
                        if (response.body().size() > 0)
                        {
                            //Presonal Details
                            txtheadername.setText(response.body().get(0).getName());
                            Log.d("txtheader", String.valueOf(response.body().get(0).getName()));
                            txtnameemp.setText(response.body().get(0).getName());
                            txtproname.setText(response.body().get(0).getName());
                            txtprocourse.setVisibility(View.GONE);
                            txtgenderemp.setText(response.body().get(0).getGender());
                            txtbirthdateemp.setText(response.body().get(0).getempb_date());
                            txtadharnoemp.setText(response.body().get(0).getemp_adhar_no());
                            txtbloodgroupemp.setText(response.body().get(0).getBloodgroup());
                            txtemailemp.setText(response.body().get(0).getemp_email());
                            txtmobilenoemp.setText(response.body().get(0).getemp_mobile_phone());
                            txtgenderemp.setText(response.body().get(0).getgender());
//                            txtcategoryemp.setText(response.body().get(0).getCategory());
//                            txtcasteemp.setText(response.body().get(0).getreligion_name());
                            txtpannoemp.setText(response.body().get(0).getemp_pan_no());
//                            txtpassportnoemp.setText(response.body().get(0).getemp_passport_no());
//                            txtdrivingnoemp.setText(response.body().get(0).getemp_driving_licen_no());

                            //Official Details
                            txtempnumber.setText(response.body().get(0).getemp_number());
                            txtjoindateemp.setText(response.body().get(0).getempjoining_date());
                            txtofferleternoemp.setText(response.body().get(0).getemp_offer_letter_no());
                            txtcardnoemp.setText(response.body().get(0).getemp_card_no());
                            txtjobtimefromemp.setText(response.body().get(0).getemp_job_time_from());
                            txtjobtimetoemp.setText(response.body().get(0).getemp_job_time_to());
                            txtdepartmentnameemp.setText(response.body().get(0).geted_name());
                            txtjobnameemp.setText(response.body().get(0).getJob_Name());

                            //Contact Details
                            txtaddressline1.setText(response.body().get(0).getemp_home_address_line1());
                            txtaddressline2.setText(response.body().get(0).getemp_home_address_line2());
                            txtfathermobileno.setText(response.body().get(0).getemp_home_phone());
                            txtcity.setText(response.body().get(0).getcity_name());
                            txtstate.setText(response.body().get(0).getstate_name());
                            txtcountry.setText(response.body().get(0).getcountry_name());

                            //Account details
                            txtbanknameemp.setText(response.body().get(0).getemp_bank_name());
                            txtaccountnoemp.setText(response.body().get(0).getemp_account_no());
                            txtbranchnameemp.setText(response.body().get(0).getemp_branch_name());
                            txtaccounttypeemp.setText(response.body().get(0).getemp_account_type());


                            //Family details
                            txtFathername.setText(response.body().get(0).getEmp_father_name());
                            txtFatherOccupation.setText(response.body().get(0).getEmp_father_occupation());
                            txtFatherAddress.setText(response.body().get(0).getEmp_permanent_address1());
                            txtMothername.setText(response.body().get(0).getEmp_mother_name());
                            txtMotherOccupation.setText(response.body().get(0).getEmp_mother_occupation());
                            txtSpouseName.setText(response.body().get(0).getEmp_spouse_name());
                            txtSpouseOccupation.setText(response.body().get(0).getEmp_spouse_occupation());
                            txtSonDaughterName.setText(response.body().get(0).getEmp_child_name());
                            txtSonDaughterDateofBirth.setText(response.body().get(0).getemp_date_of_chield());

                            //education details
                            education.setText(response.body().get(0).getEmp_qualification());

                        } else {
                            Toast.makeText(Profile.this, "No Records Found", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        txtname.setText("Unsuccessfull");
                    }
                }


                @Override
                public void onFailure(Call<List<ProfileResponse>> call, Throwable t) {
                    progressDialog.dismiss();
                    // Log error here since request failed
                    txtname.setText("Failure");
                    // Log.e("Profile", t.toString());
                }
            });
        }

        Drawable mDefaultBackground = getResources().getDrawable(R.drawable.defaultprofile);
        Glide.with(Profile.this).load(String.valueOf(storage.read("stud_photo", 3))).fitCenter().error(mDefaultBackground).into(profileimage);

       /* BitmapDrawable menubackground = new BitmapDrawable (BitmapFactory.decodeResource(getResources(),
                R.drawable.background));

        menubackground.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
*/
        // ab.setBackgroundDrawable(menubackground);
       /* Bitmap bMap = BitmapFactory.decodeResource(res, R.drawable.background);
        BitmapDrawable actionBarBackground = new BitmapDrawable(res, bMap);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(actionBarBackground);*/

        // profileimage.setImageUrl("http://gradlesol.com/ourprojects/MatrimonyApp/uploads/default_profile.png", imageLoader);

        //  ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // If you are using normal ImageView
    }

    public void findviews() {
        ctx = this;
        storage = new DataStorage("Login_Detail", ctx);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileimage = (CircleImageView) findViewById(R.id.imgcircularprofiler);
        profilebackground = (LinearLayout) findViewById(R.id.rlbackgroundprofile);
        llemppersonaldetail = (LinearLayout) findViewById(R.id.llemppersonaldetail);
        llstudentpersonaldetail = (LinearLayout) findViewById(R.id.llstudentpersonaldetail);
        llempaccountdetail = (LinearLayout) findViewById(R.id.llempaccountdetail);
        llstudentcollegedetails = (LinearLayout) findViewById(R.id.llstudentcollegedetails);
        llofficialdetailemp = (LinearLayout) findViewById(R.id.llofficialdetailemp);
        familydetails = (LinearLayout) findViewById(R.id.familydetails);
        lleducationdetail = (LinearLayout) findViewById(R.id.lleducationdetail);
        //STUDENT DETAIL
        //Personal Details
        txtname = (TextView) findViewById(R.id.txtname);
        txtproname = (TextView) findViewById(R.id.txtproname);
        txtprocourse = (TextView) findViewById(R.id.txtprocourse);
        txtheadername = (TextView) findViewById(R.id.txtheadername);
        txtgender = (TextView) findViewById(R.id.txtgender);
        txtbirthdate = (TextView) findViewById(R.id.txtbirthdate);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtadharno = (TextView) findViewById(R.id.txtadharno);
        txtbloodgroup = (TextView) findViewById(R.id.txtbloodgroup);
        txtmobileno = (TextView) findViewById(R.id.txtmobileno);
        txtfthermobilenoheader = (TextView) findViewById(R.id.txtfthermobilenoheader);

        //College Details
        txtcollege = (TextView) findViewById(R.id.txtcollege);
        txtdepartment = (TextView) findViewById(R.id.txtdepartment);
        txtcourse = (TextView) findViewById(R.id.txtcourse);
        txtsemester = (TextView) findViewById(R.id.txtsemester);
        txtadmissionno = (TextView) findViewById(R.id.txtadmissionno);
        txtenrollmentno = (TextView) findViewById(R.id.txtenrollmentno);
        txtadmissionyear = (TextView) findViewById(R.id.txtadmissionyear);
        txtadmissiontype = (TextView) findViewById(R.id.txtadmissiontype);
        txtfeetype = (TextView) findViewById(R.id.txtfeetype);
        txtstudentquata = (TextView) findViewById(R.id.txtstudentquata);
        txtshift = (TextView) findViewById(R.id.txtshift);
        textcollegedetails = (TextView) findViewById(R.id.textcollegedetails);
        imgcollegedetail = (ImageView) findViewById(R.id.imgcollegedetail);

        //Contact Details
        txtaddressline1 = (TextView) findViewById(R.id.txtaddressline1);
        txtaddressline2 = (TextView) findViewById(R.id.txtaddressline2);
        txtfathermobileno = (TextView) findViewById(R.id.txtfathermobileno);
        txtcity = (TextView) findViewById(R.id.txtcity);
        txtstate = (TextView) findViewById(R.id.txtstate);
        txtcountry = (TextView) findViewById(R.id.txtcountry);

        //EMPLOYEE DETAIL
        //Personal Details
        txtnameemp = (TextView) findViewById(R.id.txtnameemp);
        txtgenderemp = (TextView) findViewById(R.id.txtgenderemp);
        txtbirthdateemp = (TextView) findViewById(R.id.txtbirthdateemp);
        txtemailemp = (TextView) findViewById(R.id.txtemailemp);
        txtadharnoemp = (TextView) findViewById(R.id.txtadharnoemp);
        txtbloodgroupemp = (TextView) findViewById(R.id.txtbloodgroupemp);
        txtmobilenoemp = (TextView) findViewById(R.id.txtmobilenoemp);
        txtempnumber = (TextView) findViewById(R.id.txtempnumber);
        txtjoindateemp = (TextView) findViewById(R.id.txtjoindateemp);
        txtofferleternoemp = (TextView) findViewById(R.id.txtofferleternoemp);
        txtcardnoemp = (TextView) findViewById(R.id.txtcardnoemp);
//        txtcategoryemp = (TextView)findViewById(R.id.txtcategoryemp);
//        txtcasteemp = (TextView)findViewById(R.id.txtcasteemp);
        txtpannoemp = (TextView) findViewById(R.id.txtpannoemp);
//        txtpassportnoemp = (TextView)findViewById(R.id.txtpassportnoemp);
//        txtdrivingnoemp = (TextView)findViewById(R.id.txtdrivingnoemp);
        txtjobtimefromemp = (TextView) findViewById(R.id.txtjobtimefromemp);
        txtjobtimetoemp = (TextView) findViewById(R.id.txtjobtimetoemp);

        //Official Details
        txtjobnameemp = (TextView) findViewById(R.id.txtjobnameemp);
        txtdepartmentnameemp = (TextView) findViewById(R.id.txtdepartmentnameemp);


        //Account Details
        txtbanknameemp = (TextView) findViewById(R.id.txtbanknameemp);
        txtaccountnoemp = (TextView) findViewById(R.id.txtaccountnoemp);
        txtbranchnameemp = (TextView) findViewById(R.id.txtbranchnameemp);
        txtaccounttypeemp = (TextView) findViewById(R.id.txtaccounttypeemp);

        //family details
        txtFathername = (TextView) findViewById(R.id.txtfathername);
        txtFatherOccupation = (TextView) findViewById(R.id.txtfatheroccupation);
        txtFatherContactname = (TextView) findViewById(R.id.txtfathercontactname);
        txtFatherAddress = (TextView) findViewById(R.id.txtFatherAddress);
        txtMothername = (TextView) findViewById(R.id.txtMothername);
        txtMotherOccupation = (TextView) findViewById(R.id.txtMotherOccupation);
        txtSpouseName = (TextView) findViewById(R.id.txtSpousName);
        txtSpouseOccupation = (TextView) findViewById(R.id.txtSpouseOccupation);
        txtSonDaughterName = (TextView) findViewById(R.id.txtSonDaughterName);
        txtSonDaughterDateofBirth = (TextView) findViewById(R.id.txtSonDaughterDateofBirth);

        //Education detail
        education = (TextView) findViewById(R.id.txtedu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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
                                finish();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }

 /*   private void dynamicToolbarColor()
    {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener()
        {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.orange));
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
    }*/

 /*   private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }*/

}
