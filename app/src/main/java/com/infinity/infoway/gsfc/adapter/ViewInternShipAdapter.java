package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.ExamDetailPOJO;
import com.infinity.infoway.gsfc.model.ViewInternShipPojo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class ViewInternShipAdapter extends BaseAdapter {
    ViewInternShipPojo viewInternShipPojo;
    LayoutInflater inflater;
    Context context;
    MarshMallowPermission marshMallowPermission;

    Activity activity;

    class ViewHolder {
        CustomTextView tv_sr, tv_name_in, tv_title, tv_desc, tv_status;
        ImageView iv_download;
    }

    ViewHolder viewHolder;

    public ViewInternShipAdapter(Context context, ViewInternShipPojo viewInternShipPojo, Activity activity) {
        this.context = context;
        this.viewInternShipPojo = viewInternShipPojo;
        this.activity = activity;

    }

    public int getCount() {
        return viewInternShipPojo.getData().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_internship_report_adapter, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_sr = (CustomTextView) convertView.findViewById(R.id.tv_sr);
            viewHolder.tv_desc = (CustomTextView) convertView.findViewById(R.id.tv_desc);
            viewHolder.tv_name_in = (CustomTextView) convertView.findViewById(R.id.tv_name_in);
            viewHolder.tv_title = (CustomTextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_status = (CustomTextView) convertView.findViewById(R.id.tv_status);
            viewHolder.iv_download = (ImageView) convertView.findViewById(R.id.iv_download);


            convertView.setTag(viewHolder);
        }

        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_sr.setText("  " + viewInternShipPojo.getData().get(position).getSwr_id() + "");
        viewHolder.tv_desc.setText("  " + viewInternShipPojo.getData().get(position).getSwr_decription() + "");
        viewHolder.tv_name_in.setText("  " + viewInternShipPojo.getData().get(position).getInts_name() + "");
        viewHolder.tv_title.setText("  " + viewInternShipPojo.getData().get(position).getSwr_report_title() + "");

        marshMallowPermission = new MarshMallowPermission(activity);
        viewHolder.iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {

                    String URL_CHILD = viewInternShipPojo.getData().get(position).getSwr_report_file();
                    System.out.println("URL_CHILD ON CLICK:::::::::::" + viewInternShipPojo.getData().get(position).getSwr_report_file());

                    String[] file2 = URL_CHILD.split("/");

                    System.out.println("file after split::::::>>>>***" + String.valueOf(file2));
                    System.out.println("file after split::::::" + file2.toString());

                    String result1 = file2[file2.length - 1];

                    System.out.println("result1::::" + result1);
                    String nameoffile1 = result1;

                    System.out.println("nameoffile1****" + System.currentTimeMillis() + "_" + nameoffile1);
                    String url = URL_CHILD;

                    if (url != null && url.length() > 5) {
                        String extention = url.substring(url.lastIndexOf("."), url.length());

                        System.out.println("extension of file in view Internship !!!!! " + extention);
                        //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                        new DownloadFileFromURL(extention).execute(url);
                    }
                    else
                        {
                        DialogUtils.Show_Toast(context, "No Documents Available");
                    }
                }
            }
        });

        if (viewInternShipPojo.getData().get(position).getSra_approve().contentEquals("1"))
        {
            viewHolder.tv_status.setText("  " + "Approve");
            viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (viewInternShipPojo.getData().get(position).getSra_approve().contentEquals("-1"))
        {
            viewHolder.tv_status.setText("  " + "Reject");
            viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
        } else if (viewInternShipPojo.getData().get(position).getSra_approve().contentEquals("0")) {
            viewHolder.tv_status.setText("  " + "Panding");
            viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));

        }
        return convertView;
    }


    private class DownloadFileFromURL extends AsyncTask<String, String, String>
    {


        String nameoffile;
        String Extenson;

        DownloadFileFromURL(String Extenson)
        {
            this.Extenson = Extenson;

            System.out.println("EXTENSION OF FILE::::::::::" + Extenson);

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            DialogUtils.showProgressDialog(activity, "");

        }

        /**
         * Downloading file in menubackground thread
         */
        @Override
        protected String doInBackground(String... f_url)
        {
            int count;
            try {
                URL url = new URL(f_url[0]);


                String urofysllabusl = f_url[0];
                System.out.println("urofysllabusl::::::" + urofysllabusl);
                String[] parts = urofysllabusl.split("/");
                System.out.println("parts::::::::::::::" + parts);
                String result = parts[parts.length - 1];
                System.out.println("result:::::::::::" + result);
                nameoffile = result;

                System.out.println("result::::::doInback::::" + result);
                System.out.println("name in  doInBackground>>>>" + nameoffile);
                URLConnection conection = url.openConnection();
                conection.connect();
                File dir = null;
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GSFC");
               // File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "Internship");
                // File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream("sdcard/GSFC/" + nameoffile);
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Internship/" + nameoffile);

                System.out.println("output:::::::::" + output.toString());
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            DialogUtils.hideProgressDialog();

            DialogUtils.Show_Toast(activity, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);
            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile);
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Internship/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file11);
                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(uri, "image/*");
                }

                intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(context, "No Apps can performs This acttion");
                }
            } else {


                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "image/*");

                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(context, "No Apps can performs This acttion");

                }

            }


        }

    }
}