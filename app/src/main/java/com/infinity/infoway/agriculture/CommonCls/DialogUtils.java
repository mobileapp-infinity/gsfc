package com.infinity.infoway.agriculture.CommonCls;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class DialogUtils
{


    public static Toast Show_Toast(Context ctx, String msg) {
       /* Toast t = new Toast(ctx);
        t.setText(msg + "");
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

        t.setDuration(Toast.LENGTH_SHORT);
        t.show();*/


        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //  Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        return toast;
    }
}
