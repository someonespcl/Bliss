package com.bliss.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bliss.R;
import com.bliss.activities.LoginActivity;
import com.bliss.utils.CustomToast;
import com.bliss.viewmodel.AuthViewModel;

public class SettingsFragment extends Fragment {
    
    private Dialog dialog;
    private AuthViewModel authViewModel;
    
    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        
        authViewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        
        authViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                
            } else {
                CustomToast.showCustomToast(requireContext(), "Account Deleted");
                startActivity(new Intent(requireContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        
        authViewModel.getAuthenticationError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                CustomToast.showCustomToast(requireContext(), error);
            }
        });
        
        ImageView deactivateButton = view.findViewById(R.id.deactivate_account_setting);
        deactivateButton.setOnClickListener(
                v -> {
                    dialog = new Dialog(requireContext());
                    dialog.setContentView(R.layout.custom_dialog_layout);
                    dialog.getWindow()
                            .setBackgroundDrawableResource(R.drawable.custom_dialog_background);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    if (dialog != null) {
                        Button dialogCancelButton =
                                dialog.findViewById(R.id.deactivate_dialog_cancel_button);
                        Button deactivateAccountButton =
                                dialog.findViewById(R.id.deactivate_account_button);

                        // Check if buttons are not null
                        if (dialogCancelButton != null && deactivateAccountButton != null) {
                            dialogCancelButton.setOnClickListener(
                                    view1 -> {
                                        dialog.dismiss();
                                    });

                            deactivateAccountButton.setOnClickListener(
                                    view12 -> {
                                        authViewModel.deactivateAccount();
                                    });
                        }
                    }
                });

        return view;
    }
}