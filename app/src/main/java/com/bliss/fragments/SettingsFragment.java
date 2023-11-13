package com.bliss.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.os.VibrationEffect;
import android.animation.ObjectAnimator;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Boolean passwordVisibile = false;
    
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
        
        dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        
        Button dialogCancelButton = dialog.findViewById(R.id.deactivate_dialog_cancel_button);
        Button deactivateAccountButton = dialog.findViewById(R.id.deactivate_account_button);
        EditText deactivatePassword = dialog.findViewById(R.id.deactivate_account_password);
        
        dialogCancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        
        deactivateAccountButton.setOnClickListener(v -> {
            String _deactivatePassword = deactivatePassword.getText().toString().trim();
            if(_deactivatePassword.isEmpty()) {
                CustomToast.showCustomToast(requireContext(), "Cannot be empty");
            	vibrateDevice();
                showErrorAnimation(deactivatePassword);
            } else if(_deactivatePassword.length() < 6) {
            	CustomToast.showCustomToast(requireContext(), "Cannot be less than 6");
            } else {
                authViewModel.deactivateAccount(deactivatePassword.getText().toString().trim());
            }
        });
        
        deactivatePassword.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int Right = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX()
                                    >= deactivatePassword.getRight()
                                            - deactivatePassword
                                                    .getCompoundDrawables()[Right]
                                                    .getBounds()
                                                    .width()) {
                                int selection = deactivatePassword.getSelectionEnd();
                                if (passwordVisibile.booleanValue()) {
                                    deactivatePassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_close_icon, 0);
                                    deactivatePassword.setTextColor(Color.parseColor("#FFC100"));
                                    deactivatePassword.setTransformationMethod(
                                            PasswordTransformationMethod.getInstance());
                                } else {
                                    deactivatePassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_open_icon, 0);
                                    deactivatePassword.setTextColor(Color.WHITE);
                                    deactivatePassword.setTransformationMethod(
                                            HideReturnsTransformationMethod.getInstance());
                                }
                                passwordVisibile = !passwordVisibile;
                                deactivatePassword.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });

        
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
                    dialog.show();
                });

        return view;
    }
    
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        } else {}
    }
    
    private void showErrorAnimation(View view) {
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(view,  "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        shakeAnimator.setDuration(1000);
        shakeAnimator.setInterpolator(new DecelerateInterpolator());
        shakeAnimator.start();
    }
}