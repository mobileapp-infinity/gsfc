package com.infinity.infoway.agriculture.CommonCls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomBoldTextView extends TextView {

    public CustomBoldTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
      //  Typeface customFont = FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsBold.otf");
        setTypeface(font);
      //  setTextSize(18);
       // setTextColor(Color.BLACK);
     //   setHintTextColor(Color.GRAY);
//        setTextSize(21);
    }
}