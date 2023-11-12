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

import com.bliss.R;
import com.bliss.databinding.ActivityLoginBinding;
import com.bliss.utils.CustomToast;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    Boolean passwordVisibile = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.loginActivityButton.setOnClickListener( v -> {
            String loginUserEmail = binding.loginUserEmail.getText().toString().trim();
            String loginUserPassword = binding.loginUserPassword.getText().toString().trim();
            if(loginUserEmail.isEmpty()) {
                showErrorAnimation(binding.loginUserEmail); 
            	vibrateDevice();
            } else if(!Patterns.EMAIL_ADDRESS.matcher(loginUserEmail).matches()) {
            	CustomToast.showCustomToast(LoginActivity.this, "Invalid Email Address");
            } else if(loginUserPassword.isEmpty()) {
                showErrorAnimation(binding.loginUserPassword);
            	vibrateDevice();
            } else if(loginUserPassword.length() < 6) {
            	CustomToast.showCustomToast(LoginActivity.this, "Password Must Be >= 6");
            } else {
                CustomToast.showCustomToast(LoginActivity.this, "Login Successful");
            }
        });
        
        binding.loginUserPassword.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int Right = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX()
                                    >= binding.loginUserPassword.getRight()
                                            - binding.loginUserPassword
                                                    .getCompoundDrawables()[Right]
                                                    .getBounds()
                                                    .width()) {
                                int selection = binding.loginUserPassword.getSelectionEnd();
                                if (passwordVisibile.booleanValue()) {
                                    binding.loginUserPassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_close_icon, 0);
                                    binding.loginUserPassword.setTextColor(Color.parseColor("#FFC100"));
                                    binding.loginUserPassword.setTransformationMethod(
                                            PasswordTransformationMethod.getInstance());
                                } else {
                                    binding.loginUserPassword
                                            .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                                    0, 0, R.drawable.eye_open_icon, 0);
                                    binding.loginUserPassword.setTextColor(Color.WHITE);
                                    binding.loginUserPassword.setTransformationMethod(
                                            HideReturnsTransformationMethod.getInstance());
                                }
                                passwordVisibile = !passwordVisibile;
                                binding.loginUserPassword.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });

        binding.registerButton.setOnClickListener(v -> {
            Pair<View, String>[] pair = new Pair[4];
            pair[0] = new Pair<View, String>(binding.loginUserEmail, "email_transition");
            pair[1] = new Pair<View, String>(binding.loginUserPassword, "password_transition");
            pair[2] = new Pair<View, String>(binding.loginActivityButton, "button_transition");
            pair[3] = new Pair<View, String>(binding.containerTextView, "container_transition");
                
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pair);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent, options.toBundle());
                
        });
    }
    
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
