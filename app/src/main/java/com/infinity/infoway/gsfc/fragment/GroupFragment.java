package com.infinity.infoway.gsfc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.adapter.News_Expandable_list_view_Adapter;
import com.infinity.infoway.gsfc.model.group_news_detail;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<group_news_detail> contentlist = new ArrayList<>();

    // ArrayList<Lecturedetail> sem=new ArrayList<Lecturedetail>();
    private OnListFragmentInteractionListener mListener;
    TextView tvnorecord;
    RecyclerView recyclerView;


    public GroupFragment()
    {

    }

    public GroupFragment newInstance(int columnCount, ArrayList<group_news_detail> timetable)
    {

        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", timetable);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        Bundle b = getArguments();
        if (b != null)
        {
            mColumnCount = b.getInt(ARG_COLUMN_COUNT);
            contentlist = (ArrayList<group_news_detail>) b.getSerializable("elist");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        tvnorecord = (TextView) view.findViewById(R.id.tvnorecord);
        // Set the adapter

        recyclerView = (RecyclerView)view.findViewById(R.id.group_list);

        if (mColumnCount <= 1)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        if (contentlist == null)
        {
            tvnorecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new News_Expandable_list_view_Adapter(getActivity(), contentlist));

        return view;
    }

    public interface OnListFragmentInteractionListener
    {
        void onListFragmentInteraction(ArrayList<group_news_detail> item);


    }

}
