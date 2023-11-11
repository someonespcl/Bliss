package com.bliss.activities;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.os.VibrationEffect;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.app.AppCompatActivity;
import com.bliss.R;
import com.bliss.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    Boolean passwordVisibile = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.loginActivityButton.setOnClickListener( v -> {
             showErrorAnimation(binding.loginUserPassword);
             vibrateDevice();
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
    }
    
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(64, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(64);
            }
        } else {}
    }
    
    private void showErrorAnimation(View view) {
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(view,  "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        shakeAnimator.setDuration(1000); // Adjust the duration as needed
        shakeAnimator.setInterpolator(new DecelerateInterpolator()); // This creates the shake effect
        shakeAnimator.start();
    }
}
