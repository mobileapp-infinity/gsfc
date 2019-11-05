package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infinity.infoway.gsfc.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Pageradapter extends PagerAdapter
{
    private ArrayList<String>images;
    private LayoutInflater inflater;
    private Context context;

    public Pageradapter(Context context, ArrayList<String> images)
    {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position)
    {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView)myImageLayout.findViewById(R.id.image);
        //Glide.with(context).load("https://" + images.get(position)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(myImage);
        //Glide.with(context).load(images.get(position)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(myImage);
        Picasso.with(context)
                .load(images.get(position) + "")
                .into(myImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {


                    }
                });
        // myImage.setImageDrawable(context.getResources().getDrawable(images.get(position)));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }
}
