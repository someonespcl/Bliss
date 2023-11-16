package com.bliss.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import com.bliss.R;
import com.bliss.activities.EditProfileActivity;
import com.bliss.utils.CustomToast;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {}

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button startProfileActivity = view.findViewById(R.id.startEditProfileActivity);
        startProfileActivity.setOnClickListener(
                v -> {
                    startActivity(new Intent(requireContext(), EditProfileActivity.class));
                });

        ImageButton addIntrests = view.findViewById(R.id.add_intrests);
        addIntrests.setOnClickListener(v -> {
            CustomToast.showCustomToast(requireContext(), "It will be added in future");
        });

        return view;
    }
}
