package com.infinity.infoway.gsfc.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.infinity.infoway.gsfc.R;

public class webViewDemo extends AppCompatActivity {
    ProgressBar progressbar;
    WebView wvprivacy;
    Toolbar toolbar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_demo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        url = "www.google.com";
        Log.d("url", url);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        wvprivacy = (WebView) findViewById(R.id.wvprivacy);
        wvprivacy.getSettings().setJavaScriptEnabled(true);
        // wvprivacy.getSettings().setLoadWithOverviewMode(true);
        wvprivacy.getSettings().setBuiltInZoomControls(true);
        wvprivacy.getSettings().setUseWideViewPort(true);
       // wvprivacy.clearCache(true);
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

        wvprivacy.setWebViewClient(new myWebClient());
        wvprivacy.loadUrl(url);
        wvprivacy.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });



        wvprivacy.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                super.onProgressChanged(view, progress);
            }
        });

    }


}
