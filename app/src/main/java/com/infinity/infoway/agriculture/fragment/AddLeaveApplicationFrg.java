package com.infinity.infoway.agriculture.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infinity.infoway.agriculture.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLeaveApplicationFrg extends Fragment
{


    public AddLeaveApplicationFrg()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_leave_application_frg, container, false);
    }

}
