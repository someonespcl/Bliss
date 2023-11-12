package com.bliss.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.os.VibrationEffect;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import com.bliss.R;
import com.bliss.databinding.ActivityRegisterBinding;
import com.bliss.utils.CustomToast;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    Boolean passwordVisibile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.registerActivityButton.setOnClickListener( v -> {
            String registerUserName = binding.registerUserName.getText().toString().trim();
            String registerUserPhonenumber = binding.registerUserPhonenumber.getText().toString().trim();
            String registerUserEmail = binding.registerUserEmail.getText().toString().trim();
            String registerUserPassword = binding.registerUserPassword.getText().toString().trim();
            
            if(registerUserName.isEmpty()) {
                showErrorAnimation(binding.registerUserName); 
            	vibrateDevice();
            } else if (registerUserName.length() < 3) {
                CustomToast.showCustomToast(RegisterActivity.this, "Cannot be < 3");
            } else if (registerUserPhonenumber.isEmpty()){
                showErrorAnimation(binding.registerUserPhonenumber);
                vibrateDevice();
            } else if (registerUserPhonenumber.length() < 10){
                CustomToast.showCustomToast(RegisterActivity.this, "Invalid PhoneNumber");
            } else if (registerUserEmail.isEmpty()){
                showErrorAnimation(binding.registerUserEmail);
                vibrateDevice();
            } else if(!Patterns.EMAIL_ADDRESS.matcher(registerUserEmail).matches()) {
            	CustomToast.showCustomToast(RegisterActivity.this, "Invalid Email Address");
            } else if(registerUserPassword.isEmpty()) {
                showErrorAnimation(binding.registerUserPassword);
            	vibrateDevice();
            } else if(registerUserPassword.length() < 6) {
            	CustomToast.showCustomToast(RegisterActivity.this, "Password Must Be >= 6");
            } else {
                CustomToast.showCustomToast(RegisterActivity.this, "Registration Successful");
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
                                    binding.registerUserPassword.setTextColor(Color.parseColor("#FFC100"));
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