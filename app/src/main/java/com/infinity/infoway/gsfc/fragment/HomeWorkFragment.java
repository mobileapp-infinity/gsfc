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
import com.infinity.infoway.gsfc.adapter.HomeworkAdapter;
import com.infinity.infoway.gsfc.model.Lecturedetail;
import com.infinity.infoway.gsfc.model.homework_array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWorkFragment extends Fragment
{


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<homework_array> contentlist = new ArrayList<>();

    // ArrayList<Lecturedetail> sem=new ArrayList<Lecturedetail>();
    private OnListFragmentInteractionListener mListener;
    TextView tvnorecord;
    RecyclerView recyclerView;

    public HomeWorkFragment()
    {

    }

    @SuppressWarnings("unused")
    public HomeWorkFragment newInstance(int columnCount, ArrayList<homework_array> timetable)
    {
        HomeWorkFragment fragment = new HomeWorkFragment();
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
            contentlist = (ArrayList<homework_array>) b.getSerializable("elist");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_homework_days, container, false);
        tvnorecord = (TextView) view.findViewById(R.id.tvnorecord);
        // Set the adapter

        recyclerView = (RecyclerView)view.findViewById(R.id.list_homework);

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
        recyclerView.setAdapter(new HomeworkAdapter(getActivity(), contentlist, mListener));

        return view;
    }

    public interface OnListFragmentInteractionListener
    {
        void onListFragmentInteraction(ArrayList<Lecturedetail> item);
    }
}
