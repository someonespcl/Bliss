package com.bliss.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AuthViewModel extends ViewModel {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = firebaseAuth.getCurrentUser();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference("users");
    private final MutableLiveData<FirebaseUser> authenticatedUser = new MutableLiveData<>();
    private final MutableLiveData<String> authenticationError = new MutableLiveData<>();

    public LiveData<FirebaseUser> getAuthenticatedUser() {
        return authenticatedUser;
    }

    public LiveData<String> getAuthenticationError() {
        return authenticationError;
    }

    public void signInWithEmailAndPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    authenticatedUser.setValue(user);
                } else {
                    authenticationError.setValue("Authentication Failed: " + task.getException().getMessage());
                }
            });
    }

    public void createAccountWithEmailAndPassword(String name, String phonenumber, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    authenticatedUser.setValue(user);
                    if (user != null) {
                        String userId = user.getUid();
                        HashMap<Object, String> userDetails = new HashMap<>();
                        userDetails.put("username", name);
                        userDetails.put("phonenumber", phonenumber);
                        userDetails.put("email", email);
                        userDetails.put("password", password);
                        databaseReference.child(userId).setValue(userDetails);
                    }
                } else {
                    authenticationError.setValue("Account creation failed: " + task.getException().getMessage());
                }
            });
    }
}
