package com.infinity.infoway.agriculture.CommonCls;

import android.content.Context;
import android.graphics.Typeface;

public class Validations {
    public static Typeface setTypeface(Context ctx) {
//        Typeface font = Typeface.createFromAsset(
//                ctx.getAssets(),
//                "fonts/Helvetica_guj.ttf");
        Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/PoppinsRegular.otf");

     /*   Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/HelveticaNeue.ttf");*/

        return font;
    }
}
