package com.example.theguardiannews.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public final class Dialogs {

    public static void openEmailDialog(Context context) {

        String message = "Internet is disabled on the device. To watch the news, connect the Internet or watch the saved news";
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK", null)
                .show();
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
