package com.infinity.infoway.agriculture.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.infinity.infoway.agriculture.R;

public class PlacementActivity extends AppCompatActivity
{
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);


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


    }
}
