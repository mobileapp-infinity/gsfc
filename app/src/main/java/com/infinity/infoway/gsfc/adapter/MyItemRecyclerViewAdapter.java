package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.infinity.infoway.gsfc.fragment.DaysFragment;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.Lablist;
import com.infinity.infoway.gsfc.model.Lecturedetail;

import java.util.ArrayList;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>
{

    private ArrayList<Lecturedetail> mValues = new ArrayList<Lecturedetail>();
    private final DaysFragment.OnListFragmentInteractionListener mListener;
    Activity a;
    Context ctx;
    DataStorage storage;

    public MyItemRecyclerViewAdapter(Activity a, ArrayList<Lecturedetail> items, DaysFragment.OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
        this.ctx = a;
        this.a = a;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_lecture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues;
        holder.tvLectureNo.setText(mValues.get(position).getlect_name());

        if (storage.CheckLogin("emp_id", ctx)) //EMPLOYEE
        {
            if (mValues.get(position).getsm_name() == null || mValues.get(position).getrm_name().equals(""))
            {
                holder.llclassroom.setVisibility(View.INVISIBLE);
                holder.lllecture.setVisibility(View.INVISIBLE);
                holder.lltime.setVisibility(View.INVISIBLE);

                if (mValues.get(position).getlect_name().length() > 15)
                {
                    holder.lllecture.setVisibility(View.GONE);
                    holder.tvSemester.setVisibility(View.GONE);
                    holder.tvLectureNo.setText("RECESS");
                    holder.lltime.setVisibility(View.VISIBLE);
                }
                //  holder.mainlectlayout.setBackgroundResource(R.color.list_row_hover_start_color);
            }
//            else {
//                holder.llclassroom.setVisibility(View.VISIBLE);
//                holder.lllecture.setVisibility(View.VISIBLE);
//                holder.lltime.setVisibility(View.VISIBLE);
//
//            }

            if (mValues.get(position).gettt_prac_the_status().equals("2"))
            {
                String lect2 = mValues.get(position + 1).getlect_name();
                holder.tvLectureNo.setText(mValues.get(position).getlect_name() + "/" + lect2.substring(lect2.length() - 1));
                // holder.mainlectlayout.setVisibility(View.GONE);
            }

            if (position != 0 && mValues.get(position - 1).gettt_prac_the_status().equals("2"))
            {
                //holder.tvLectureNo.setText(mValues.get(position).getlect_name()+ "/" +mValues.get(position+1).getrm_name());
                holder.mainlectlayout.setVisibility(View.GONE);
            }

            holder.tvhdivision.setText("Division");
            holder.tvDivision.setText(mValues.get(position).getdvm_name());
            holder.tvSemester.setText(mValues.get(position).getsm_name());
            assert mValues.get(position).getdvm_name() != null;
            assert mValues.get(position).getsub_short_name() != null;
            holder.tvBatch.setText(mValues.get(position).getsub_short_name());
            assert mValues.get(position).getrm_name() != null;
            holder.tvClassroom.setText(mValues.get(position).getrm_name());
            holder.tvstarttime.setText(mValues.get(position).getlect_st_time());
            holder.tvendtime.setText(mValues.get(position).getlect_end_time());
            holder.tvSemester.setVisibility(View.GONE);


        }
        else  //STUDENT
        {

            holder.tvSemester.setText(mValues.get(position).getsm_name());
            assert mValues.get(position).getdvm_name() != null;
            assert mValues.get(position).getsub_short_name() != null;
            holder.tvBatch.setText(mValues.get(position).getsub_short_name());
            assert mValues.get(position).getrm_name() != null;
            holder.tvClassroom.setText(mValues.get(position).getrm_name());
            holder.tvstarttime.setText(mValues.get(position).getlect_st_time());
            holder.tvendtime.setText(mValues.get(position).getlect_end_time());
            holder.tvSemester.setVisibility(View.GONE);

            ArrayList<Lablist> labdetail;

            holder.tvhdivision.setText("Faculty");
            holder.tvDivision.setText(mValues.get(position).getemp_name());
            holder.lldivision.setVisibility(View.GONE);
            holder.lllecture.setVisibility(View.VISIBLE);
            holder.lltime.setVisibility(View.VISIBLE);
            holder.llclassroom.setVisibility(View.VISIBLE);

            if (position != 0 && (mValues.get(position).getlect_name().equals("") || mValues.get(position - 1).getlect_name() == null))
            {
                //holder.tvLectureNo.setText(mValues.get(position).getlect_name()+ "/" +mValues.get(position+1).getrm_name());
                holder.mainlectlayout.setVisibility(View.GONE);
            }

            if (mValues.get(position).getsm_name() == null && mValues.get(position).getrm_name().equals(""))
            {
                holder.llclassroom.setVisibility(View.INVISIBLE);
                holder.lllecture.setVisibility(View.INVISIBLE);
                holder.lltime.setVisibility(View.INVISIBLE);
            }
            if (storage.CheckLogin("stud_id", ctx) && (mValues.get(position).getlect_name().equals("") || mValues.get(position).getlect_name() == null)) {

                holder.llclassroom.setVisibility(View.VISIBLE);
                holder.lllecture.setVisibility(View.VISIBLE);
                holder.lltime.setVisibility(View.VISIBLE);
                labdetail = mValues.get(position).getLab_array();
                holder.batchlayout.setVisibility(View.GONE);

                if (labdetail.size() >= 1)
                {
                    holder.mainlectlayout.setVisibility(View.VISIBLE);
                    //Log.d("arraylab",String.valueOf(labdetail.size()));
                    //Log.d("lectname",String.valueOf(mValues.get(position).getLab_array().get(0).getlect_name()));
                    String lectname = mValues.get(position).getLab_array().get(0).getlect_name();
//               String lectname1=mValues.get(position+1).getlect_name();
                    //String lect2=mValues.get(position+1).getlect_name();
                    holder.tvLectureNo.setText(lectname + "/" + String.valueOf((Integer.parseInt(lectname.substring(lectname.length() - 1)) + 1)));
                    holder.lllecture.setVisibility(View.GONE);
                    holder.lldivision.setVisibility(View.VISIBLE);
                    holder.tvhdivision.setText("Batch");
                    holder.batchlayout.setVisibility(View.VISIBLE);

                    for (int i = 0; i <labdetail.size(); i++)
                    {
                        LayoutInflater vi = (LayoutInflater) ctx
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        LinearLayout ll = new LinearLayout(ctx);
                        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        LLParams.setMargins(0, 0, 0, 0);
                        ll.setLayoutParams(LLParams);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        ll.addView(vi.inflate(R.layout.view_lecture_batch, null));
                        ll.setId(i);

                        holder.batchlayout.addView(ll);

                        if (position == 0 || position == 4 || position == 2)
                        {
                            holder.batchlayout.setBackgroundColor(a.getResources().getColor(R.color.lllecture1));
                        }
                        else if (position == 1 || position == 6 || position == 5)
                        {

                        }
                        else if (position == 3 || position == 7 || position == 8)
                        {
                            holder.batchlayout.setBackgroundColor(a.getResources().getColor(R.color.lllecture3));
                        }
                        TextView batch = (TextView) ll.findViewById(R.id.tvbatch);
                        TextView tvBatch = (TextView) ll.findViewById(R.id.tvsubject);
                        TextView tvfaculty = (TextView) ll.findViewById(R.id.tvfaculty);
                        TextView tvClassroom = (TextView) ll.findViewById(R.id.tvclassroom);
                        batch.setText(mValues.get(position).getLab_array().get(i).getdvm_name());
                        tvfaculty.setText(mValues.get(position).getLab_array().get(i).getemp_name());
                        //  assert mValues.get(position).getLab_array().get(0).getsub_short_name() != null;
                        tvBatch.setText(mValues.get(position).getLab_array().get(i).getsub_short_name());
                        // assert mValues.get(position).getLab_array().get(0).getrm_name() != null;
                        tvClassroom.setText(mValues.get(position).getLab_array().get(i).getrm_name());

                        System.out.println("tvClassroom values :::::: "+mValues.get(position).getLab_array().get(i).getrm_name());
                    }
                }

            }

            if (storage.CheckLogin("stud_id", ctx) && mValues.get(position).getLab_array() == null && mValues.get(position).getlect_st_time().equals("") && position != 0)
            {
                if (mValues.get(position - 1).getLab_array() != null)
                {
                    holder.mainlectlayout.setVisibility(View.GONE);
                }
            }

        }

        if (mValues.get(position).getlect_name().contains("RECESS"))
        {
            holder.lllecture.setVisibility(View.GONE);
            holder.tvSemester.setVisibility(View.GONE);
            holder.tvLectureNo.setText("RECESS");
            holder.lltime.setVisibility(View.VISIBLE);
            holder.rllectureheader.setBackgroundColor(a.getResources().getColor(R.color.recess));
            holder.llimagelecture.setBackgroundColor(a.getResources().getColor(R.color.darkrecess));
            holder.imglecture.setBackground(a.getResources().getDrawable(R.drawable.recess));
        }
        else if (position == 0 || position == 4 || position == 2)
        {
            holder.rllectureheader.setBackgroundColor(a.getResources().getColor(R.color.rllectureheader1));
            holder.llimagelecture.setBackgroundColor(a.getResources().getColor(R.color.llimagelecture1));
            holder.lllecture.setBackgroundColor(a.getResources().getColor(R.color.lllecture1));
        }
        else if (position == 1 || position == 6 || position == 5)
        {
            holder.rllectureheader.setBackgroundColor(a.getResources().getColor(R.color.rllectureheader2));
            holder.llimagelecture.setBackgroundColor(a.getResources().getColor(R.color.llimagelecture2));
            holder.lllecture.setBackgroundColor(a.getResources().getColor(R.color.lllecture2));
        }
        else if (position == 3 || position == 7 || position == 8)
        {
            holder.rllectureheader.setBackgroundColor(a.getResources().getColor(R.color.rllectureheader3));
            holder.llimagelecture.setBackgroundColor(a.getResources().getColor(R.color.llimagelecture3));
            holder.lllecture.setBackgroundColor(a.getResources().getColor(R.color.lllecture3));
        }

        //holder.tvDivision.setText(mValues.get(position).getemp_name());
        //holder.tvhdivision.setText("Faculty");


        holder.lltime.setVisibility(View.INVISIBLE);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mListener)
                    {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final View mView;
        ImageView imglecture;
        final TextView tvhdivision, tvLectureNo, tvSemester, tvDivision, tvBatch, tvClassroom, tvendtime, tvstarttime;
        LinearLayout lllecture, llclassroom, mainlectlayout, lldivision, lltime, batchlayout, llimagelecture;
        ArrayList<Lecturedetail> mItem;
        RelativeLayout rllectureheader;

        ViewHolder(View view)
        {
            super(view);
            mView = view;
            tvLectureNo = (TextView) view.findViewById(R.id.idlecture);
            tvSemester = (TextView) view.findViewById(R.id.tvsemester);
            tvDivision = (TextView) view.findViewById(R.id.tvdivision);
            tvhdivision = (TextView) view.findViewById(R.id.tvhdivision);
            tvBatch = (TextView) view.findViewById(R.id.tvbatch);
            tvClassroom = (TextView) view.findViewById(R.id.tvclassroom);
            tvendtime = (TextView) view.findViewById(R.id.tvendtime);
            tvstarttime = (TextView) view.findViewById(R.id.tvstarttime);
            llclassroom = (LinearLayout) view.findViewById(R.id.llclassroom);
            lllecture = (LinearLayout) view.findViewById(R.id.lllecture);
            lldivision = (LinearLayout) view.findViewById(R.id.lldivision);
            mainlectlayout = (LinearLayout) view.findViewById(R.id.mainlectlayout);
            batchlayout = (LinearLayout) view.findViewById(R.id.batchlayout);
            llimagelecture = (LinearLayout) view.findViewById(R.id.llimagelecture);
            rllectureheader = (RelativeLayout) view.findViewById(R.id.rllectureheader);
            lltime = (LinearLayout) view.findViewById(R.id.lltime);
            imglecture = (ImageView) view.findViewById(R.id.imglecture);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + tvSemester.getText() + "'";
        }
    }
}
