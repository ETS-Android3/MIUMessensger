package com.example.miumessenger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miumessenger.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PortalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PortalFragment extends Fragment {



    public PortalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Portal");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portal, container, false);
    }
}