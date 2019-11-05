package com.infinity.infoway.gsfc.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;

public class FeeCircularActivity extends AppCompatActivity {

    TextView txt_view_fee_circular;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_circular);


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


        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        final String name = getBundle.getString("fc_file");
        txt_view_fee_circular = (TextView) findViewById(R.id.txt_view_fee_circular);
        txt_view_fee_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
                    startActivity(browserIntent);

                } catch (Exception e) {

                }
            }
        });

    }
}
