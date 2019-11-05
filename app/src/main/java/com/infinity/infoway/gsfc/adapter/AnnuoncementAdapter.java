package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;
import com.infinity.infoway.gsfc.model.AnnouncePojo;
import com.infinity.infoway.gsfc.model.StudentsDisplyaFillPojo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pragna on 16-4-2019.
 */

public class AnnuoncementAdapter extends BaseAdapter
{
    MarshMallowPermission marshMallowPermission;
    ArrayList<String> check_IDS = new ArrayList<>();
    private Context ctx;
    // List<String> AllStatuslist = new ArrayList<>();
    ViewHolder holder = null;
    public FragmentManager f_manager;
    //private LayoutInflater inflater;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    String customerId;
    View view;
    private int selectedPosition = -1;
    public ArrayList<String> selected = new ArrayList<String>();
    List<String> customBenifitList;
    DataStorage storage;
    RequestQueue queue;
    Activity a;

    StudentsDisplyaFillPojo studentsDisplyaFillPojo;
    Boolean b;
    public managePostClick _managepost;

    AnnouncePojo announcePojo;
    Boolean aBoolean;

    String stud_id, emp_Id;

    public interface managePostClick {
        public void managepostClick(int postion);

        public void manageEditClick(int postion);

        public void manageDeleteClick(int postion);
    }

    public static HashMap<String, String> ID_Check_status = new HashMap<>();


    public AnnuoncementAdapter(Context ctx, Activity a, AnnouncePojo announcePojo, Boolean b, managePostClick _managepost)
    {

        this.ctx = ctx;
        this.a = a;
        // this.aBoolean = aBoolean;
        this.announcePojo = announcePojo;
        //   this.studentsDisplyaFillPojo = studentsDisplyaFillPojo;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        this._managepost = _managepost;
        storage = new DataStorage("Login_Detail", ctx);


    }

    @Override
    public int getCount()
    {
        // return customBenifitList.size();
        System.out.println("notify size :::::::::"+announcePojo.getNoty().size());
        return announcePojo.getNoty().size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
        //  return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    private class ViewHolder {
        ImageView iv_file;


        CustomTextView date, tv_announce_by, tv_desc_ann, tv_ann_by, tv_view;

        LinearLayout ll_announce_by, ll_final_ann, ll_main_view_ann,ll_no_records,ll_main;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
//            view = inflater.inflate(R.layout.e_learning_adpter, null);
            view = inflater.inflate(R.layout.anouncement_adpter, null);
            holder = new ViewHolder();
            //    holder.itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);


            // holder.llmainstudentfetch = (LinearLayout) view.findViewById(R.id.ll_main_student_fetch);

            holder.iv_file = (ImageView) view.findViewById(R.id.iv_file);
            holder.ll_announce_by = (LinearLayout) view.findViewById(R.id.ll_announce_by);
            holder.ll_main_view_ann = (LinearLayout) view.findViewById(R.id.ll_main_view_ann);
            holder.ll_final_ann = (LinearLayout) view.findViewById(R.id.ll_final_ann);
            holder.date = (CustomTextView) view.findViewById(R.id.date);
            holder.tv_view = (CustomTextView) view.findViewById(R.id.tv_view);
            holder.tv_ann_by = (CustomTextView) view.findViewById(R.id.tv_ann_by);
            holder.tv_announce_by = (CustomTextView) view.findViewById(R.id.tv_announce_by);
            holder.tv_desc_ann = (CustomTextView) view.findViewById(R.id.tv_desc_ann);

            holder.ll_no_records=(LinearLayout)view.findViewById(R.id.ll_no_records);
            holder.ll_main=(LinearLayout)view.findViewById(R.id.ll_main);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        System.out.println("notify###### "+announcePojo.getNoty());

        System.out.println("notify size ***** ###### "+announcePojo.getNoty().size());
        if (announcePojo.getNoty().size()>0)
        {
            System.out.println("call size >0 ******");
            holder.ll_no_records.setVisibility(View.GONE);
            holder.ll_main.setVisibility(View.VISIBLE);
        }

        else
        {
            System.out.println("call size ==0 ******");
            holder.ll_no_records.setVisibility(View.VISIBLE);
            holder.ll_main.setVisibility(View.GONE);
        }
        marshMallowPermission = new MarshMallowPermission(a);
        holder.date.setText(announcePojo.getNoty().get(position).getNotif_date() + "");
        holder.iv_file.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = announcePojo.getNoty().get(position).getApp_file_path() + "";

                if (url != null && url.length() > 5)
                {
                    if (!marshMallowPermission.checkPermissionForExternalStorage())
                    {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    } else {
                        String extention = url.substring(url.lastIndexOf("."), url.length());
                        new DownloadFileFromURL(extention).execute(url);
                    }

                } else {
                    DialogUtils.Show_Toast(a, "No Documents Available");
                }
               /* if (url != null && url.length() > 5) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    ctx.startActivity(i);

                } else {
                    DialogUtils.Show_Toast(ctx, "No Documents Found");
                }*/

            }
        });

        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.POPUPDialog(ctx, announcePojo.getNoty().get(position).getNotif_date(), announcePojo.getNoty().get(position).getNotif_head(), announcePojo.getNoty().get(position).getNotif_msg(), new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {

                    }
                });
            }
        });

      /*  if (storage.CheckLogin("stud_id", ctx))
        {
            holder.ll_main_view_ann.setVisibility(View.VISIBLE);
            holder.ll_final_ann.setVisibility(View.VISIBLE);
            holder.tv_ann_by.setVisibility(View.VISIBLE);
            // holder.tv_announce_by.setText(announcePojo.getNoty().get(position).getAnnounced_by() + "");

*/
        String s = announcePojo.getNoty().get(position).getAnnounced_by() + "";


        if (s == null || s.compareToIgnoreCase("null") == 0) {
            s = "";
        }


        holder.tv_ann_by.setText("  " + s + "");


//        } else {
//            holder.ll_main_view_ann.setVisibility(View.GONE);
//            holder.ll_final_ann.setVisibility(View.GONE);
//            holder.ll_final_ann.setVisibility(View.GONE);
//        }
      /*  if (aBoolean == true) {
            holder.ll_announce_by.setVisibility(View.GONE);
            holder.tv_ann_by.setVisibility(View.GONE);
        } else {
            //holder.ll_announce_by.setVisibility(View.VISIBLE);

        }
*/
        holder.tv_desc_ann.setText(announcePojo.getNoty().get(position).getNotif_msg() + "");
       /* holder.tv_manage.setVisibility(View.GONE);
        holder.tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.managepostClick(position);
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.manageDeleteClick(position);
            }
        });
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _managepost.manageEditClick(position);
            }
        });
*/

        // this.b = FillAttendanceActivityfaculty.b;

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


//                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/SIMS");
//                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "Announcement");
                 File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GSFC");
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
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Announcement/" + nameoffile);

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
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            DialogUtils.hideProgressDialog();

            DialogUtils.Show_Toast(a, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);
//                 String imagePath = Environment.getExternalStorageDirectory().toString() + "GSFC/" + nameoffile;

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile);
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SIMS/", nameoffile);
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Announcement/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


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