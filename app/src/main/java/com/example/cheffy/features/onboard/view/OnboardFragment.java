package com.example.cheffy.features.onboard.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cheffy.R;
import com.google.android.material.button.MaterialButton;

public class OnboardFragment extends Fragment {


    public OnboardFragment() {
        // Required empty public constructor
    }


    MaterialButton btnStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboard, container, false);
        btnStart = view.findViewById(R.id.btnStart);
//        btnStart.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_onboardFragment_to_loginFragment));
        return view;
    }
}