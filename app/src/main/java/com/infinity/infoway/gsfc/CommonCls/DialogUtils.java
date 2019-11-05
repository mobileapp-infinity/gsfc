package com.infinity.infoway.gsfc.CommonCls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.infinity.infoway.gsfc.R;

public class DialogUtils
{
    static ProgressDialog m_Dialog = null;
    public interface DailogCallBackOkButtonClick {
        public void onDialogOkButtonClicked();

    }

    public interface DailogCallBackCancelButtonClick {
        public void onDialogCancelButtonClicked();

    }

    public static ProgressDialog showProgressDialog(Context context, String text)
    {
        m_Dialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        text = "Please Wait... ";
        //   m_Dialog.setProgressDrawable(R.drawable.progress_round);


//        progressDrawable.setBounds(downloadingBar.getProgressDrawable().getBounds());

//       m_Dialog.setProgressDrawable(progressDrawable);

        SpannableString spannableString = new SpannableString(text);
        Typeface typefaceSpan = Validations.setTypeface(context);
        spannableString.setSpan(typefaceSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        m_Dialog.setMessage(spannableString);

//        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }
    public static ProgressDialog showProgressDialogNotCancelable(Context context, String text)
    {
        m_Dialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        SpannableString spannableString = new SpannableString(text);
        Typeface typefaceSpan = Validations.setTypeface(context);
        spannableString.setSpan(typefaceSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        m_Dialog.setMessage(spannableString);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }
    public static void hideProgressDialog() {
        if (m_Dialog != null) {
            if (m_Dialog.isShowing()) {
                m_Dialog.dismiss();
            }
        }

        else
        {
            System.out.println("nullllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
        }

    }
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

    public static void showDialog4YNo(final Context context, String title, String message, String link,final DailogCallBackOkButtonClick dailogCallBackOkButtonClick, final DailogCallBackCancelButtonClick dailogCallBackCancelButtonClick)
    {
        title = title + "";
        link = link + "";
        if (title.contentEquals(""))
        {
            title = context.getResources().getString(R.string.app_name);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.aommoncls_dialogbox, null);
        CustomBoldTextView titileTextView = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        CustomTextView msgTextView = (CustomTextView) dialogView.findViewById(R.id.tv_msg);
        CustomTextView tv_msg1 = (CustomTextView) dialogView.findViewById(R.id.tv_msg1);

        if (link.contentEquals(""))
        {
            tv_msg1.setVisibility(View.INVISIBLE);
//            link = context.getResources().getString(R.string.app_name);
        }
else{
            tv_msg1.setText(link+"");
        }

//        CustomTextView tv_msg_guj = (CustomTextView) dialogView.findViewById(R.id.tv_msg_guj);
        CustomButton dialogButtonOKButton = (CustomButton) dialogView.findViewById(R.id.dialogButtonOK);
        CustomButton dialogButtonCancel = (CustomButton) dialogView.findViewById(R.id.dialogButtonCancel);
        dialogButtonCancel.setTypeface(Validations.setTypeface(context));
        tv_msg1.setTypeface(Validations.setTypeface(context));
        dialogButtonCancel.setTypeface(Validations.setTypeface(context));
        titileTextView.setTypeface(Validations.setTypeface(context));
        titileTextView.setText(title+"");
        msgTextView.setTypeface(Validations.setTypeface(context));
      //  tv_msg_guj.setTypeface(Validations.setTypeface(context));
        dialogButtonCancel.setVisibility(View.VISIBLE);
        dialogButtonCancel.setVisibility(View.GONE);
        dialogButtonOKButton.setTypeface(Validations.setTypeface(context));
        msgTextView.setText(message + "");
        //    if()
       // tv_msg_guj.setTextColor(context.getResources().getColor(R.color.colorAccent));
//        tv_msg_guj.setText(context.getResources().getString(R.string.ADD_MEMBER) + "");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final AlertDialog show = builder.show();
        dialogButtonOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                if (dailogCallBackOkButtonClick != null)
                    dailogCallBackOkButtonClick.onDialogOkButtonClicked();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                if (dailogCallBackCancelButtonClick != null)
                    dailogCallBackCancelButtonClick.onDialogCancelButtonClicked();
            }
        });
    }



    public  static  void POPUPDialog(final Context context,String date,String title,String detail,final DailogCallBackOkButtonClick dailogCallBackOkButtonClick)
    {


        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.popup_announcement, null);
        ImageView iv_delete = (ImageView)dialogView.findViewById(R.id.iv_delete);
        CustomBoldTextView titileTextView = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        CustomTextView tv_tv1 = (CustomTextView) dialogView.findViewById(R.id.tv_tv1);
        CustomTextView tv_tv2 = (CustomTextView) dialogView.findViewById(R.id.tv_tv2);
        CustomTextView tv_tv3 = (CustomTextView) dialogView.findViewById(R.id.tv_tv3);


        tv_tv1.setTypeface(Validations.setTypeface(context));
        tv_tv1.setText(" "+date+"");
        tv_tv2.setTypeface(Validations.setTypeface(context));
        tv_tv2.setText(" "+title+"");
        tv_tv3.setTypeface(Validations.setTypeface(context));
        tv_tv3.setText(" "+detail+"");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final AlertDialog show = builder.show();
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                show.dismiss();
                if (dailogCallBackOkButtonClick != null)
                    dailogCallBackOkButtonClick.onDialogOkButtonClicked();
            }
        });


    }




    public static void showDialog4YNo_(final Context context, String title, String message, String link,final DailogCallBackOkButtonClick dailogCallBackOkButtonClick, final DailogCallBackCancelButtonClick dailogCallBackCancelButtonClick)
    {
        title = title + "";
        link = link + "";
        if (title.contentEquals(""))
        {
            title = context.getResources().getString(R.string.app_name);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.aommoncls_dialogbox, null);
        CustomBoldTextView titileTextView = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        CustomTextView msgTextView = (CustomTextView) dialogView.findViewById(R.id.tv_msg);
        CustomTextView tv_msg1 = (CustomTextView) dialogView.findViewById(R.id.tv_msg1);

        if (link.contentEquals(""))
        {
            tv_msg1.setVisibility(View.INVISIBLE);
//            link = context.getResources().getString(R.string.app_name);
        }
        else{
            tv_msg1.setText(link+"");
        }

//        CustomTextView tv_msg_guj = (CustomTextView) dialogView.findViewById(R.id.tv_msg_guj);
        CustomButton dialogButtonOKButton = (CustomButton) dialogView.findViewById(R.id.dialogButtonOK);
        CustomButton dialogButtonCancel = (CustomButton) dialogView.findViewById(R.id.dialogButtonCancel);
        dialogButtonCancel.setTypeface(Validations.setTypeface(context));
        tv_msg1.setTypeface(Validations.setTypeface(context));
        dialogButtonCancel.setTypeface(Validations.setTypeface(context));
        titileTextView.setTypeface(Validations.setTypeface(context));
        titileTextView.setText(title+"");
        msgTextView.setTypeface(Validations.setTypeface(context));
        //  tv_msg_guj.setTypeface(Validations.setTypeface(context));
        dialogButtonCancel.setVisibility(View.VISIBLE);
        dialogButtonCancel.setVisibility(View.VISIBLE);
        dialogButtonOKButton.setTypeface(Validations.setTypeface(context));
        msgTextView.setText(message + "");
        //    if()
        // tv_msg_guj.setTextColor(context.getResources().getColor(R.color.colorAccent));
//        tv_msg_guj.setText(context.getResources().getString(R.string.ADD_MEMBER) + "");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final AlertDialog show = builder.show();
        dialogButtonOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                show.dismiss();
                if (dailogCallBackOkButtonClick != null)
                    dailogCallBackOkButtonClick.onDialogOkButtonClicked();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                if (dailogCallBackCancelButtonClick != null)
                    dailogCallBackCancelButtonClick.onDialogCancelButtonClicked();
            }
        });
    }

}
