package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.AssignView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class AssignmentViewAdapter extends BaseAdapter
{
    MarshMallowPermission marshMallowPermission;

    private Context ctx;
    ViewHolder holder = null;
    private LayoutInflater inflater;
    View view;
    DataStorage storage;
    Activity a;
    AssignView assignView;


    public AssignmentViewAdapter(Context ctx, Activity a, AssignView assignView)
    {

        this.ctx = ctx;
        this.a = a;
        this.assignView = assignView;

        inflater = LayoutInflater.from(this.ctx);
        storage = new DataStorage("Login_Detail", ctx);


    }

    @Override
    public int getCount() {
        return assignView.getAssignment().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        ImageView iv_file;


        CustomTextView tv_course, date, tv_sem, tv_div, tv_sub_name, tv_an;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_assignment_faculty, null);
            holder = new ViewHolder();


            holder.iv_file = (ImageView) view.findViewById(R.id.iv_file);

            holder.date = (CustomTextView) view.findViewById(R.id.date);
            holder.tv_an = (CustomTextView) view.findViewById(R.id.tv_an);
            holder.tv_course = (CustomTextView) view.findViewById(R.id.tv_course);
            holder.tv_sem = (CustomTextView) view.findViewById(R.id.tv_sem);
            holder.tv_div = (CustomTextView) view.findViewById(R.id.tv_div);
            holder.tv_sub_name = (CustomTextView) view.findViewById(R.id.tv_sub_name);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        marshMallowPermission = new MarshMallowPermission(a);
        holder.tv_course.setText("  " + assignView.getAssignment().get(position).getCourse_short_name() + "");
        holder.tv_an.setText("  " + assignView.getAssignment().get(position).getAm_name() + "");
        holder.tv_sub_name.setText("  " + assignView.getAssignment().get(position).getSub_short_name() + "");
        holder.tv_div.setText("  " + assignView.getAssignment().get(position).getDvm_name() + "");
        holder.tv_sem.setText("  " + assignView.getAssignment().get(position).getSm_name() + "");
        holder.date.setText("  " + assignView.getAssignment().get(position).getCreated_date() + "");
        holder.iv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = assignView.getAssignment().get(position).getAm_file() + "";

                if (url != null && url.length() > 5) {
                    if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    } else {
                        String extention = url.substring(url.lastIndexOf("."), url.length());
                        new DownloadFileFromURL(extention).execute(url);
                    }

                } else {
                    DialogUtils.Show_Toast(a, "No Documents Available");
                }

            }
        });


        return view;
    }

    private class DownloadFileFromURL extends AsyncTask<String, String, String> {


        String nameoffile;
        String Extenson;

        DownloadFileFromURL(String Extenson) {
            this.Extenson = Extenson;

            System.out.println("EXTENSION OF FILE::::::::::" + Extenson);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DialogUtils.showProgressDialog(a, "");

        }

        /**
         * Downloading file in menubackground thread
         */
        @Override
        protected String doInBackground(String... f_url) {
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

            //    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "Assignment");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Output stream
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SIMS/" + System.currentTimeMillis() + "_" + nameoffile);

                OutputStream output = new FileOutputStream("sdcard/GSFC/" + nameoffile);
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Assignment/" + nameoffile);

                System.out.println("output:::::::::" + output.toString());
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
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
        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog after the file was downloaded
            DialogUtils.hideProgressDialog();

            DialogUtils.Show_Toast(a, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);
            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile);

//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Assignment/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));


            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24)
            {

                Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".fileprovider", file11);
                ctx.grantUriPermission(ctx.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

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
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can performs This acttion");
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
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can performs This acttion");

                }

            }


        }

    }

}
