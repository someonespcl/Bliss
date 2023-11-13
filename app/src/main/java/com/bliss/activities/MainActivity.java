package com.bliss.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.bliss.activities.LoginActivity;
import com.bliss.databinding.ActivityMainBinding;
import com.bliss.fragments.ProfileFragment;
import com.bliss.fragments.SettingsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import com.bliss.R;

public class MainActivity extends AppCompatActivity {
    
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        SmoothBottomBar bottom_nav = findViewById(R.id.bottom_navigation_view);
        bottom_nav.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public boolean onItemSelect(int itemIndex) {
                switch(itemIndex){
                     case 0:
                        break;
                     case 1:
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, profileFragment).commit();
                        break;
                     case 2:
                        break;
                     case 3:
                        SettingsFragment settingsFragment = new SettingsFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, settingsFragment).commit();
                        break;
                 }
                 return true;
            }
        });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if(user != null) {
        	
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
    
}
