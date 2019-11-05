package com.infinity.infoway.gsfc.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.LoginResponse;
import com.infinity.infoway.gsfc.rest.ApiClient;
import com.infinity.infoway.gsfc.rest.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBack extends AppCompatActivity {
    Toolbar toolbar;
    EditText etname, etemail, etmobile, etmessage, etwebsite;
    Button btnsubmit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        findviews();
        /*actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);*/
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

    }


    public static boolean isEditTextContainEmail(EditText argEditText)
    {

        try
        {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void findviews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etname = (EditText) findViewById(R.id.etname);
        etemail = (EditText) findViewById(R.id.etemail);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etmessage = (EditText) findViewById(R.id.etmessage);
        etwebsite = (EditText) findViewById(R.id.etwebsite);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });
    }


    public void submit()
    {

        if (TextUtils.isEmpty(etname.getText().toString()) || TextUtils.isEmpty(etmobile.getText().toString()) || TextUtils.isEmpty(etmessage.getText().toString())) {
            if (TextUtils.isEmpty(etname.getText().toString()))
            {
                etname.setError("Enter Name");
            }

            if (TextUtils.isEmpty(etmobile.getText().toString()))
            {
                etmobile.setError("Enter Mobile");
            }

            if (TextUtils.isEmpty(etmessage.getText().toString()))
            {
                etmessage.setError("Enter Message");

            }

        }
        else if (!etmobile.getText().toString().matches(MobilePattern))
        {
          /* if(!etemail.getText().toString().matches(emailPattern) ) {
               etemail.setError("Invalid Email Address");
           }*/
            if (!etmobile.getText().toString().matches(MobilePattern))
            {
                etmobile.setError("Invalid mobile number");
            }
          /* if(!Patterns.WEB_URL.matcher(etwebsite.getText().toString()).matches())
           {
               etwebsite.setError("Invalid mobile number");
           }*/
        }
        else
            {
            String name = etname.getText().toString();
            String email = etemail.getText().toString();
            String mobile = etmobile.getText().toString();
            String website = etwebsite.getText().toString();
            String message = etmessage.getText().toString();


            final ProgressDialog progressDialog = new ProgressDialog(FeedBack.this);
                progressDialog.setMessage("please wait ");
                progressDialog.show();
            ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponse> call = apiservice.FeedBack(name, email, mobile, website, message);
            call.enqueue(new Callback<LoginResponse>()
            {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
                {
                    progressDialog.dismiss();
                    if (response.isSuccessful())
                    {
                        if (response.body().getstatus().equals("1"))
                        {
                            Toast.makeText(FeedBack.this, "Your message Submited", Toast.LENGTH_LONG).show();
                        }
                        else
                            {

                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable)
                {
                    progressDialog.dismiss();
                    //Toast.makeText(ContactUs.this,"Error in Response",Toast.LENGTH_LONG).show();

                }
            });
        }

    }
}
