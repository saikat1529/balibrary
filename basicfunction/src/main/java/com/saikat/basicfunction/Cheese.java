package com.saikat.basicfunction;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Cheese {

    Context context;

    /*
    * CONSTRUCTOR INITIATED
    * @param
    *  - context
    * */
    public Cheese(Context context){
        this.context = context;
    }

    /*
    * SHOW TOAST
    * @Param
    *  - string
    * */
    public void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /*
    * HIDE / SHOW VISIBILITY
    * @param
    *  - array of views [views to hide]
    *  - view [view to show]
    * */
    public void hideAndShowViews(View[] views, View view) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
    }

}
