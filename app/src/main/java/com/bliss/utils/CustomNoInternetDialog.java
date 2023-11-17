package com.bliss.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import com.bliss.R;

public class CustomNoInternetDialog {

    private final Context context;

    public CustomNoInternetDialog(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public void checkInternetConnection() {
        if (!isNetworkAvailable()) {
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        CustomToast.showCustomToast(context, "Check your Internet connection");
    }
}
