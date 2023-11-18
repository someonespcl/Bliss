package com.bliss.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bliss.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {
    
    private ActivityEditProfileBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
