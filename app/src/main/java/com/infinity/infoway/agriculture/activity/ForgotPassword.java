package com.infinity.infoway.agriculture.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.LoginResponse;
import com.infinity.infoway.agriculture.rest.ApiClient;
import com.infinity.infoway.agriculture.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity
{

    Button btforgotpassword;
    EditText etmobileno;
    WebView webview;
    String MobilePattern = "[0-9]{10}";
    private String username, password;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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

        btforgotpassword = (Button) findViewById(R.id.btforgotpassword);
        etmobileno = (EditText) findViewById(R.id.etmobileno);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());

        if (Build.VERSION.SDK_INT >= 21)
        {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        btforgotpassword.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(etmobileno.getText().toString()))
                {
                    etmobileno.setError("enter mobile no.");
                }
                else if (etmobileno.getText().toString().matches(MobilePattern))
                {
                    final ProgressDialog process = new ProgressDialog(ForgotPassword.this, R.style.MyTheme1);
                    process.setCancelable(false);
                    process.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    process.setMessage("sending SMS");
                    process.show();
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<LoginResponse> call = apiService.forgotpassword(etmobileno.getText().toString());
                    call.enqueue(new Callback<LoginResponse>()
                    {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
                        {
                            process.dismiss();
                            if (response.isSuccessful())
                            {

                                if (response.body().getstatus().equals("1"))
                                {
                                    username = response.body().getusername();
                                    password = response.body().getpassword();

                                   // String webUrl = "http://message.smartwave.co.in/rest/services/sendSMS/sendGroupSms?AUTH_KEY=15b4c7be4da47e2cb485b654fb78fb4&senderId=ATMIYA&routeId=1&smsContentType=english&message= Username :" + username + "  Password :" + password + "&mobileNos=" + etmobileno.getText().toString();
                                    //webview.loadUrl(webUrl, null);
                                    finish();
                                    Toast.makeText(ForgotPassword.this, "SMS sent", Toast.LENGTH_LONG).show();

                                }
                                else
                                    {
                                    Toast.makeText(ForgotPassword.this, "Your mobile number is not registered", Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t)
                        {

                            process.dismiss();
                            // Log error here since request failed
                            // Log.e("ForgotPassword", t.toString());
                        }
                    });
                }
                else
                    {
                    Toast.makeText(ForgotPassword.this, "Please Enter valid mobile number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }*/
}
