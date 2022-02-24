package com.example.notes.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.ui.NavDrawable;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutFragment extends Fragment {

    public static final String ABOUT_TAG = "ABOUT_TAG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar_about);

        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).initDrawer(toolbar);
        }
    }
}
