package com.infinity.infoway.agriculture.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.adapter.MyItemRecyclerViewAdapter;
import com.infinity.infoway.agriculture.model.Lecturedetail;

import java.util.ArrayList;

public class DaysFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<Lecturedetail> contentlist = new ArrayList<>();

    // ArrayList<Lecturedetail> sem=new ArrayList<Lecturedetail>();
    private OnListFragmentInteractionListener mListener;
    TextView tvnorecord;
    RecyclerView recyclerView;

    public DaysFragment()
    {

    }

    @SuppressWarnings("unused")
    public DaysFragment newInstance(int columnCount, ArrayList<Lecturedetail> timetable)
    {
        DaysFragment fragment = new DaysFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", timetable);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();

        if (b != null)
        {
            mColumnCount = b.getInt(ARG_COLUMN_COUNT);
            contentlist = (ArrayList<Lecturedetail>) b.getSerializable("elist");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_timetable_days, container, false);
        tvnorecord = (TextView) view.findViewById(R.id.tvnorecord);
        // Set the adapter

        recyclerView = (RecyclerView)view.findViewById(R.id.list);

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
            Toast.makeText(getActivity(),"No records found",Toast.LENGTH_LONG).show();
            tvnorecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(getActivity(), contentlist, mListener));

        return view;
    }

    public interface OnListFragmentInteractionListener
    {
        void onListFragmentInteraction(ArrayList<Lecturedetail> item);
    }
}
