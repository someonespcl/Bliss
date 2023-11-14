package com.bliss.fragments;

import android.app.Dialog;
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

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }
    
    private Dialog dialog;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.custom_dialog_add_intrests);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        
        Button startProfileActivity = view.findViewById(R.id.startEditProfileActivity);
        startProfileActivity.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), EditProfileActivity.class));
        });
        
        ImageButton addIntrests = view.findViewById(R.id.add_intrests);
        addIntrests.setOnClickListener(v -> {
            dialog.show();
        });
        
        
        
        return view;
    }
}