package com.infinity.infoway.gsfc.CommonCls;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        //  Typeface customFont = FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
     //   setTextColor(Color.WHITE);
        setHintTextColor(Color.GRAY);

    }
}