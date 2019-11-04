package com.saikat.basicfunction;

import android.content.Context;
import android.widget.Toast;

public class Cheese {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
