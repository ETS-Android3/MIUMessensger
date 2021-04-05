package com.example.miumessenger.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.miumessenger.R;
import com.example.miumessenger.databinding.FragmentWebBinding;


public class AcademicCalenderFragment extends Fragment {

    public AcademicCalenderFragment() {

    }

    FragmentWebBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebBinding.inflate(inflater, container, false);
        getActivity().setTitle("Academic Calender");

        WebSettings webSettings;
        webSettings = binding.portalView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.portalView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        binding.portalView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.portalView.getSettings().setAppCacheEnabled(true);
        binding.portalView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        binding.portalView.loadUrl("https://drive.google.com/file/d/1Ihh1g9STK9dzaHPCKZfAzNMdD4eijPxx/view?usp=sharing");
        binding.portalView.setWebViewClient(new WebViewClient());


        binding.forwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.portalView.canGoBack()) {
                    binding.portalView.goForward();
                } else {
                    Toast.makeText(getActivity(),"NO More Page!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.portalView.canGoBack()) {
                    binding.portalView.goBack();
                } else {
                    Toast.makeText(getActivity(),"NO More Page!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.portalView.reload();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}