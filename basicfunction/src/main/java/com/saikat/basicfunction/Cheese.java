package com.saikat.basicfunction;

import android.content.Context;
import android.widget.Toast;

public class Cheese {

    Context context;

    public Cheese(Context context){
        this.context = context;
    }

    public void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
