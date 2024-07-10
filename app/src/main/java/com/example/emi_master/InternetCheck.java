package com.example.emi_master;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck {
    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo!= null && netInfo.isConnectedOrConnecting();
    }

    public static void checkInternetOnAppStart(final Context context) {
        if (!hasInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please check your internet connection and try again.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Exit the app or take other appropriate action
                    System.exit(0);
                }
            });
            builder.show();
        } else {
            // Start the app normally
            //...
        }
    }
}