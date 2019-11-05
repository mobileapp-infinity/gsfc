package com.infinity.infoway.gsfc.CommonCls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomEditText extends EditText
{

    public CustomEditText(Context context)
    {
        super(context);

        applyCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context)
    {
        //  Typeface customFont = FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
        setPadding(3,0,0,0);

        //  setBackground(null);
    }
}