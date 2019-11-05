package com.saikat.basicfunction;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

    /*
    * SET FULLSCREEN
    * */
    /*public void setFullScreen() {
        Activity activity = ((Activity) context);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }*/

    /*
    * CHECK IF NETWORK AVAILABLE
    * */
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /*
    * LOG MESSAGE
    * @param
    *  - String message
    * */
    public void logger(String message) {
        Log.d(context.getPackageName()+"_DEBUG", message);
    }

}
