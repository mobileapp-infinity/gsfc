package com.infinity.infoway.gsfc.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.infinity.infoway.gsfc.R;

public class MoreApplication extends AppCompatActivity
{
    ProgressBar progressbar;
    WebView wvprivacy;
    Toolbar toolbar;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_moreapp);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        url = getIntent().getStringExtra("url");
        Log.d("url", url);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        wvprivacy = (WebView) findViewById(R.id.wvprivacy);
        wvprivacy.getSettings().setJavaScriptEnabled(true);
        wvprivacy.getSettings().setDomStorageEnabled(true);
        // wvprivacy.getSettings().setLoadWithOverviewMode(true);
        wvprivacy.getSettings().setBuiltInZoomControls(true);
        wvprivacy.getSettings().setUseWideViewPort(true);
        wvprivacy.clearCache(true);
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


        wvprivacy.loadUrl(url);
        //wvprivacy.loadDataWithBaseURL(url,url,"text/html", "UTF-8",null);
//        wvprivacy.loadUrl("javascript:alert('hello')");
        //  wvprivacy.loadData(url,"text/html", "UTF-8");


        wvprivacy.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (URLUtil.isNetworkUrl(url))
                {
                    return false;
                }
                if (appInstalledOrNot(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    // do something if app is not installed
                }
                return true;
            }

        });


        wvprivacy.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });


        wvprivacy.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }
        });

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

}
