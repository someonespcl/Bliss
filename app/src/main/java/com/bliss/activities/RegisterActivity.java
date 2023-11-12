package com.bliss.activities;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Pair;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import com.bliss.activities.MainActivity;
import com.bliss.R;
import com.bliss.databinding.ActivityRegisterBinding;
import com.bliss.utils.CustomLoadingDialog;
import com.bliss.utils.CustomToast;
import com.bliss.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;
    Boolean passwordVisibile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        authViewModel = new ViewModelProvider(RegisterActivity.this).get(AuthViewModel.class);
        
        authViewModel.getAuthenticatedUser().observe(this, user -> {
            if (user != null) {
                CustomLoadingDialog.hideLoadingDialog();
                CustomToast.showCustomToast(RegisterActivity.this, "Registration Successfull");
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
        
        authViewModel.getAuthenticationError().observe(RegisterActivity.this, error -> {
            if (error != null) {
                CustomLoadingDialog.hideLoadingDialog();
                CustomToast.showCustomToast(RegisterActivity.this, error);
            }
        });

        binding.registerActivityButton.setOnClickListener(
                v -> {
                    String registerUserName = binding.registerUserName.getText().toString().trim();
                    String registerUserPhonenumber =
                            binding.registerUserPhonenumber.getText().toString().trim();
                    String registerUserEmail =
                            binding.registerUserEmail.getText().toString().trim();
                    String registerUserPassword =
                            binding.registerUserPassword.getText().toString().trim();

                    if (registerUserName.isEmpty()) {
                        showErrorAnimation(binding.registerUserName);
                        vibrateDevice();
                    } else if (registerUserName.length() < 3) {
                        CustomToast.showCustomToast(RegisterActivity.this, "Cannot be < 3");
                    } else if (registerUserPhonenumber.isEmpty()) {
                        showErrorAnimation(binding.registerUserPhonenumber);
                        vibrateDevice();
                    } else if (registerUserPhonenumber.length() < 10) {
                        CustomToast.showCustomToast(RegisterActivity.this, "Invalid PhoneNumber");
                    } else if (registerUserEmail.isEmpty()) {
                        showErrorAnimation(binding.registerUserEmail);
                        vibrateDevice();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(registerUserEmail).matches()) {
                        CustomToast.showCustomToast(RegisterActivity.this, "Invalid Email Address");
                    } else if (registerUserPassword.isEmpty()) {
                        showErrorAnimation(binding.registerUserPassword);
                        vibrateDevice();
                    } else if (registerUserPassword.length() < 6) {
                        CustomToast.showCustomToast(RegisterActivity.this, "Password Must Be >= 6");
                    } else {
                        CustomLoadingDialog.showLoadingDialog(RegisterActivity.this);
                        authViewModel.createAccountWithEmailAndPassword(registerUserName, registerUserPhonenumber, registerUserEmail, registerUserPassword);
                    }
                });

        binding.registerUserPassword.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int Right = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX()
                                    >= binding.registerUserPassword.getRight()
                                            - binding.registerUserPassword
                                                    .getCompoundDrawables()[Right]
                                                    .getBounds()
                                                    .width()) {
                                int selection = binding.registerUserPassword.getSelectionEnd();
                                if (passwordVisibile.booleanValue()) {
                                    binding.registerUserPassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_close_icon, 0);
                                    binding.registerUserPassword.setTextColor(
                                            Color.parseColor("#FFC100"));
                                    binding.registerUserPassword.setTransformationMethod(
                                            PasswordTransformationMethod.getInstance());
                                } else {
                                    binding.registerUserPassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_open_icon, 0);
                                    binding.registerUserPassword.setTextColor(Color.WHITE);
                                    binding.registerUserPassword.setTransformationMethod(
                                            HideReturnsTransformationMethod.getInstance());
                                }
                                passwordVisibile = !passwordVisibile;
                                binding.registerUserPassword.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });

        binding.loginButton.setOnClickListener(
                v -> {
                    Pair<View, String>[] pair = new Pair[5];
                    pair[0] = new Pair<View, String>(binding.registerUserEmail, "email_transition");
                    pair[1] =
                            new Pair<View, String>(
                                    binding.registerUserPassword, "password_transition");
                    pair[2] =
                            new Pair<View, String>(
                                    binding.registerActivityButton, "button_transition");
                    pair[3] =
                            new Pair<View, String>(
                                    binding.continueWithGoogleButton, "google_button_transition");
                    pair[4] =
                            new Pair<View, String>(binding.viewsContainer, "container_transition");

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(
                                    RegisterActivity.this, pair);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent, options.toBundle());
                });
    }

    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                        VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        }
    }

    private void showErrorAnimation(View view) {
        ObjectAnimator shakeAnimator =
                ObjectAnimator.ofFloat(
                        view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        shakeAnimator.setDuration(1000);
        shakeAnimator.setInterpolator(new DecelerateInterpolator());
        shakeAnimator.start();
    }
}
