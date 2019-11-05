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
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.CommonCls.DialogUtils;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.app.MarshMallowPermission;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public class MyGroupELerningExpanAdapter extends BaseExpandableListAdapter {


    DataStorage storage;
    RequestQueue queue;
    private Context _context;
    private List<String> _listDataHeader; // header titles grp name
    private List<String> list_date_header; // header list_date_header grp date
    private List<String> list_desc; // header titles desc
    String STUD_ID;
    private HashMap<String, List<String>> _listDataChildName;//child title
    private HashMap<String, List<String>> _list_child_url;//child url
    private HashMap<String, List<String>> _list_date_upload;//child date of upload
    MarshMallowPermission marshMallowPermission;

    Activity activity;

    public MyGroupELerningExpanAdapter(Context context, List<String> _listDataHeader, List<String> list_date_header, List<String> list_desc, HashMap<String, List<String>> _listDataChildName, HashMap<String, List<String>> _list_date_upload, HashMap<String, List<String>> _list_child_url, String STUD_ID, Activity activity) {
        this._context = context;
        this._listDataHeader = _listDataHeader;
        this.list_date_header = list_date_header;
        this.list_desc = list_desc;
        this._listDataChildName = _listDataChildName;
        this._list_date_upload = _list_date_upload;
        this._list_child_url = _list_child_url;
        this.STUD_ID = STUD_ID;
        this.activity = activity;

        storage = new DataStorage("Login_Detail", context);
        queue = Volley.newRequestQueue(context);


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChildName.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getChildupload_dte(int groupPosition, int childPosititon) {
        return this._list_date_upload.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getUploadUrl(int groupPosition, int childPosititon) {
        return this._list_child_url.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        marshMallowPermission = new MarshMallowPermission(activity);
        final String childText = (String) getChild(groupPosition, childPosition);
        final String child_upload_date = (String) getChildupload_dte(groupPosition, childPosition);

        final String URL_CHILD = (String) getUploadUrl(groupPosition, childPosition);
        String headerTitle = (String) list_desc(groupPosition) + "";
        System.out.println("childText:::::::::::::" + childText);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.my_grp_child_expan, null);
        }


        ImageView iv_download = (ImageView) convertView.findViewById(R.id.iv_download);
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
                    System.out.println("URL_CHILD ON CLICK:::::::::::" + URL_CHILD);

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
                        //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                        new DownloadFileFromURL(extention).execute(url);
                    } else {
                        DialogUtils.Show_Toast(_context, "No Documents Available");
                    }

                }


            }
        });
        CustomTextView tv_desc_header = (CustomTextView) convertView
                .findViewById(R.id.tv_desc_header);

        CustomTextView dec_chid_tv = (CustomTextView) convertView
                .findViewById(R.id.dec_chid_tv);
        CustomTextView tv_date_child = (CustomTextView) convertView
                .findViewById(R.id.tv_date_child);

        dec_chid_tv.setText("  " + childText + "");
        tv_date_child.setText("  " + child_upload_date + "");
        if (childPosition == 0) {
            tv_desc_header.setVisibility(View.VISIBLE);
            tv_desc_header.setText(headerTitle + "");
        } else {
            tv_desc_header.setVisibility(View.GONE);
        }

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildName.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    public Object get_date(int groupPosition) {
        return this.list_date_header.get(groupPosition);
    }

    public Object list_desc(int groupPosition) {
        return this.list_desc.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition) + "";
        System.out.println("headerTitle:::::::::::::" + headerTitle);
        String Date = (String) get_date(groupPosition) + "";
        System.out.println("Date:::::::::::::" + Date);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.my_grp_header_expan, null);
        }

        CustomBoldTextView txt_sub_name_my_grp = (CustomBoldTextView) convertView
                .findViewById(R.id.txt_sub_name_my_grp);

        CustomTextView txt_date_header_date = (CustomTextView) convertView
                .findViewById(R.id.txt_date_header_date);

        txt_sub_name_my_grp.setText(headerTitle + "");
        txt_date_header_date.setText(Date + "");


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

            DialogUtils.showProgressDialog(activity, "");

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
//                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/SIMS");
               // File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "E_lerning");
                // File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agriculture");
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

//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/E_lerning/" + nameoffile);

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

            DialogUtils.Show_Toast(activity, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);
            //     String imagePath = Environment.getExternalStorageDirectory().toString() + "Agriculture/" + nameoffile;

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "GSFC/" + nameoffile);
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SIMS/", nameoffile);
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/E_lerning/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() + ".fileprovider", file11);
                _context.grantUriPermission(_context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".Zip") == 0 || Extenson.compareToIgnoreCase("Zip") == 0 || Extenson.compareToIgnoreCase(".rar") == 0 || Extenson.compareToIgnoreCase("rar") == 0) {
                    target.setDataAndType(uri, "application/x-wav");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(uri, "image/*");
                } else if (Extenson.compareToIgnoreCase(".mp4") == 0 || Extenson.compareToIgnoreCase("mp4") == 0) {
                    target.setDataAndType(uri, "video/*");
                } else if (Extenson.compareToIgnoreCase(".xls") == 0 || Extenson.compareToIgnoreCase("xls") == 0 || Extenson.compareToIgnoreCase(".xlsx") == 0 || Extenson.compareToIgnoreCase("xlsx") == 0) {
                    target.setDataAndType(uri, "application/vnd.ms-excel");
                } else if (Extenson.compareToIgnoreCase(".doc") == 0 || Extenson.compareToIgnoreCase("doc") == 0 || Extenson.compareToIgnoreCase(".docx") == 0 || Extenson.compareToIgnoreCase("docx") == 0) {
                    target.setDataAndType(uri, "application/msword");
                } else if (Extenson.compareToIgnoreCase(".pptx") == 0 || Extenson.compareToIgnoreCase("pptx") == 0) {
                    // Powerpoint file
                    target.setDataAndType(uri, "application/vnd.ms-powerpoint");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    _context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(_context, "No Apps can performs This acttion");
                }
            } else {


                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".Zip") == 0 || Extenson.compareToIgnoreCase("Zip") == 0 || Extenson.compareToIgnoreCase(".rar") == 0 || Extenson.compareToIgnoreCase("rar") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/x-wav");

                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "image/*");

                } else if (Extenson.compareToIgnoreCase(".mp4") == 0 || Extenson.compareToIgnoreCase("mp4") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "video/*");

                } else if (Extenson.compareToIgnoreCase(".xls") == 0 || Extenson.compareToIgnoreCase("xls") == 0 || Extenson.compareToIgnoreCase(".xlsx") == 0 || Extenson.compareToIgnoreCase("xlsx") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/vnd.ms-excel");

                } else if (Extenson.compareToIgnoreCase(".doc") == 0 || Extenson.compareToIgnoreCase("doc") == 0 || Extenson.compareToIgnoreCase(".docx") == 0 || Extenson.compareToIgnoreCase("docx") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/msword");

                } else if (Extenson.compareToIgnoreCase(".pptx") == 0 || Extenson.compareToIgnoreCase("pptx") == 0) {
                    // Powerpoint file
                    target.setDataAndType(Uri.fromFile(file11), "application/vnd.ms-powerpoint");

                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    _context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(_context, "No Apps can performs This acttion");

                }

            }


        }

    }
}

