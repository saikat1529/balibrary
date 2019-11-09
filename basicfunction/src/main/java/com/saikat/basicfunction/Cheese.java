package com.saikat.basicfunction;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

public class Cheese {

    Context context;
    ProgressDialog mProgressDialog;

    private static  class KeyWord{
        static String APP_FILE = "app_sp_file";
        static String SCREEN_WIDTH = "width";
        static String SCREEN_HEIGHT = "height";
        static String SCREEN_DENSITY = "density";
        static String SENT_PACKET = "sent";
        static String RECEIVED_PACKET = "received";
        static String YEAR = "year";
        static String MONTH = "month";
        static String DAY = "day";
        static String FIRST_TIME = "first_time";
    }

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
    public void setFullScreen() {
        Activity activity = ((Activity) context);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
    * LOG MESSAGE
    * @param
    *  - message as String
    * */
    public void logger(String message) {
        Log.d(context.getPackageName()+"_DEBUG", message);
    }

    /*
     * SET SHARED PREFERENCE VALUE
     * @param
     *  - node as String [Key for Shared Preference]
     *  - value as String [Value for Shared Preference]
     * */
    public void setSharedPref(String node, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(KeyWord.APP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(node, value);
        editor.commit();
    }

    /*
     * GET SHARED PREFERENCE VALUE BY KEY NAME
     * @param
     *  - node as String [Key for Shared Preference]
     * */
    public String getSharedPref(String node) {
        SharedPreferences sharedPref = context.getSharedPreferences(KeyWord.APP_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(node, "");
    }

    /*
     * SHOW PROGRESS WITH MESSAGE AND CANCELABLE STATUS
     * @param
     *  - message as String [Text to SHow on Dialog]
     *  - cancelable as Boolean [Key for Shared Preference]
     * */
    public void showProgress(String message, boolean isCancelable) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    /*
     * HIDE PROGRESS WITH REFERENCE OF SHOW PROGRESS
     * */
    public void hideProgress() {
        if (mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /*
     * GET DEVICE
     * @param
     *  - message as String [Text to SHow on Dialog]
     *  - cancelable as Boolean [Key for Shared Preference]
     * */
    public HashMap<String, Integer> getScreenResolution() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put(KeyWord.SCREEN_WIDTH, width);
        map.put(KeyWord.SCREEN_HEIGHT, height);
        map.put(KeyWord.SCREEN_DENSITY, (int)metrics.density);
        return map;
    }

    /*
     * SET FONTS
     * @param
     *  - views as ArrayOfView [Views need font changes]
     *  - tf as Typeface [Key for Shared Preference]
     * */
    public void setFonts(View[] views, Typeface tf) {
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
            } else if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                checkBox.setTypeface(tf);
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setTypeface(tf);
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTypeface(tf);
            } else if (view instanceof Button) {
                Button btn = (Button) view;
                btn.setTypeface(tf);
            } else if (view instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) view;
                SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
                mNewTitle.setSpan(tf, 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                menuItem.setTitle(mNewTitle);
            }
        }
    }

    /*
     * DECODE BASE64
     * @param
     *  - message as String [Message to be decoded]
     * */
    public String decodeBase64(String message) {
        String text = "Conversion Error";
        try {
            byte[] bytes = null;
            bytes = Base64.decode(message, Base64.DEFAULT);
            text = new String(bytes, "UTF-8");
        } catch (Exception ex) {
            logger(ex.toString());
        }
        return text;
    }

    /*
     * ENCODE BASE64
     * @param
     *  - message as String [Message to be encoded]
     * */
    public String encodeBase64(String message) {
        String text = "Conversion Error";
        try {
            byte[] data = message.getBytes("UTF-8");
            text = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception ex) {
            logger(ex.toString());
        }
        return text;
    }

    /*
     * GET RESOURCE ID BY RESOURCE NAME
     * @param
     *  - name as String [Resource Name]
     * */
    public int getResourceId(String name) {
        name = name.replace(" ", "").toLowerCase();
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /*
     * CLEAR TEXT OF VIEWS
     * @param
     *  - name as String [Resource Name]
     * */
    public void clearText(View[] view) {
        for (View v : view) {
            if (v instanceof EditText) {
                ((EditText) v).setText("");
            } else if (v instanceof Button) {
                ((Button) v).setText("");
            } else if (v instanceof TextView) {
                ((TextView) v).setText("");
            }
        }
    }

    /*
     * HIDE KEYBOARD
     * @param
     *  - view as View [Hide Keyboard Respective to View]
     * */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /*
     * SHOW KEYBOARD
     * */
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /*
     * HIDE AND SHOW VIEWS
     * @param
     *  - views as ArrayOfViews [views will hide]
     *  - view as View [View will show]
     * */
    public void hideAndShowView(View[] views, View view) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
    }

    /*
     * CONVERT ENGLISH NUMBER TO BANGLA
     * @param
     *  - numbers as String [english number converted to bangla]
     * */
    public String convertToBangle(String numbers) {
        String banglaNumber = "";
        for (int i = 0; i < numbers.length(); i++) {
            switch (numbers.charAt(i)) {
                case '1':
                    banglaNumber += "১";
                    break;
                case '2':
                    banglaNumber += "২";
                    break;
                case '3':
                    banglaNumber += "৩";
                    break;
                case '4':
                    banglaNumber += "৪";
                    break;
                case '5':
                    banglaNumber += "৫";
                    break;
                case '6':
                    banglaNumber += "৬";
                    break;
                case '7':
                    banglaNumber += "৭";
                    break;
                case '8':
                    banglaNumber += "৮";
                    break;
                case '9':
                    banglaNumber += "৯";
                    break;
                case '0':
                    banglaNumber += "০";
                    break;
                default:
                    banglaNumber += numbers.charAt(i);
                    break;
            }
        }
        return banglaNumber;
    }

    /*
     * CHECK IF EXTERNAL STORAGE AVAILABLE TO READ AND WRITE
     * */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /*
     * CHECK IF EXTERNAL STORAGE AVAILABLE TO READ
     * */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /*
     * CHECK IF EXTERNAL STORAGE AVAILABLE
     * */
    public boolean isExternalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /*
     * CHECK FOR AVAILABLE INTERNAL MEMORY SIZE
     * */
    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        }
        long availableBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return (availableBlocks * blockSize);
    }

    /*
     * CHECK FOR TOTAL INTERNAL MEMORY SIZE
     * */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long totalBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = stat.getBlockCountLong();
        } else {
            totalBlocks = stat.getBlockCount();
        }
        return (totalBlocks * blockSize);
    }

    /*
     * CHECK FOR AVAILABLE EXTERNAL MEMORY SIZE
     * */
    public String getAvailableExternalMemorySize() {
        if (isExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                availableBlocks = stat.getAvailableBlocks();
            }
            return formatSize(availableBlocks * blockSize);
        } else {
            return "";
        }
    }

    /*
     * CHECK FOR TOTAL EXTERNAL MEMORY SIZE
     * */
    public String getTotalExternalMemorySize() {
        if (isExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long totalBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBlocks = stat.getBlockCountLong();
            } else {
                totalBlocks = stat.getBlockCount();
            }
            return formatSize(totalBlocks * blockSize);
        } else {
            return "";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    /*
     * CONVERT SECONDS TO HOUR:MINUTES:SECONDS
     * */
    public String convertSecondsToHour(long seconds) {
        String second = String.valueOf(seconds % 60);
        String minute = String.valueOf((seconds / 60) % 60);
        String hour = String.valueOf((seconds / 60 / 60) % 60);
        if (second.length() < 2) {
            second = "0" + second;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        return hour + ":" + minute + ":" + second;
    }

    /*
     * GET TRAFFIC INFO OF SENT AND RECEIVED BYTES
     * */
    public HashMap<String, Long> getTrafficInfo() {
        HashMap<String, Long> map = new HashMap<>();
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(
                PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(context.getPackageName())) {
                int uid = packageInfo.uid;
                long received = TrafficStats.getUidRxBytes(uid);
                long send = TrafficStats.getUidTxBytes(uid);
                map.put(KeyWord.SENT_PACKET, send);
                map.put(KeyWord.RECEIVED_PACKET, received);
                Log.v("" + uid, "Send :" + send + ", Received :" + received);
                return map;
            }
        }
        map.put(KeyWord.SENT_PACKET, Long.parseLong("0"));
        map.put(KeyWord.RECEIVED_PACKET, Long.parseLong("0"));
        return map;
    }

    /*
     * CHECK IF NEW VERSION AVAILABLE
     * */
    public boolean isNewVersionAvailable(int publishedVersionCode) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int currentCode = packageInfo.versionCode;
            if (currentCode < publishedVersionCode) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /*
     * CHECK IF EMAIL IS VALID
     * param
     * - email as String [email text]
     * */
    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    /*
     * CHECK IF USER IS ADULT FROM DOB
     * param
     * - year as Int [Year in Int]
     * - month as Int [Month in Int]
     * - day as Int [Day in Int]
     * */
    public boolean isAdult(int year, int month, int day) {
        Calendar userAge = new GregorianCalendar(year, month, day);
        Calendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -18);
        if (minAdultAge.before(userAge)) {
            return false;
        }
        return true;
    }

    /*
     * GET YEAR, MONTH, DAY FROM DATE
     * param
     * - date as String [Date of Birth]
     * */
    public HashMap<String, Integer> getDateFromBday(String date) {
        HashMap<String, Integer> map = new HashMap<>();
        String month = date.substring(0, date.indexOf('/'));
        String day = date.substring(date.indexOf('/') + 1, date.lastIndexOf('/'));
        String year = date.substring(date.lastIndexOf('/') + 1);
        int d = day.charAt(0) == '0' ? Integer.parseInt(day.substring(1)) : Integer.parseInt(day);
        int m = month.charAt(0) == '0' ? Integer.parseInt(month.substring(1)) : Integer.parseInt(month);
        int y = Integer.parseInt(year);
        map.put(KeyWord.DAY, d);
        map.put(KeyWord.MONTH, m - 1);
        map.put(KeyWord.YEAR, y);
        return map;
    }

    /*
     * LOAD JSON FILE FROM FILE LOCATION
     * param
     * - fileLocation as String [JSON File Location]
     * */
    public String loadJSONFromAsset(String filePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /*
     * MAKE FIRST LETTER CAPITAL OF ANY STRING
     * param
     * - value as String [String to be first letter capital]
     * */
    public String makeFirstLetterUpperCase(String value) {
        if(value.length()==0){
            return "";
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /*
     * SET MARGIN FOR VIEW
     * param
     * - view as View [View to set margin]
     * - left as Int [Left Margin for View]
     * - top as Int [Top Margin for View]
     * - right as Int [Right Margin for View]
     * - bottom as Int [Bottom Margin for View]
     * */
    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    /*
     * GET HUMAN READABLE DATE FROM YEAR, MONTH AND DAY
     * param
     * - year as Int [Year in Int]
     * - month as Int [Month in Int]
     * - day as Int [Day in Int]
     * */
    public String getFormattedDate(int year, int month, int day) {
        month = month + 1;
        String y = String.valueOf(year);
        String m = String.valueOf(month);
        String d = String.valueOf(day);
        if (m.length() == 1) m = "0" + m;
        if (d.length() == 1) d = "0" + d;
        return y + "-" + m + "-" + d;
    }

    /*
     * CHECK IF FIRST TIME LAUNCH
     * param
     * - year as Int [Year in Int]
     * - month as Int [Month in Int]
     * - day as Int [Day in Int]
     * */
    public boolean isFirstTimeLaunch(){
        String firstTimeLaunch = getSharedPref(KeyWord.FIRST_TIME);
        firstTimeLaunch = firstTimeLaunch.length()>0?firstTimeLaunch:"yes";
        if(firstTimeLaunch.equals("yes")){
            return true;
        }
        return false;
    }

    /*
     * DISABLE FIRST TIME LAUNCH
     * */
    public void disableFirstTimeLaunch(){
        setSharedPref(KeyWord.FIRST_TIME, "no");
    }

    /*
    ================ Convert PIXEL to DP ===============
    */
    public float convertPixelsToDp(float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /*
    ================ Convert DP to PIXEL ===============
    */
    public float convertDpToPixel(float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /*
    * GET DAY PASSED FROM DAY PROVIDED
    * */
    public String getDayAgo(String lastVisitDate){
        String daysAgo = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date past = format.parse(lastVisitDate);
            Date now = new Date();
            /*System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
            System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");*/

            long day = TimeUnit.MICROSECONDS.toDays(now.getTime() - past.getTime());
            if(day==0){
                long hour = TimeUnit.MICROSECONDS.toHours(now.getTime() - past.getTime());
                if(hour==0){
                    long minute = TimeUnit.MICROSECONDS.toMinutes(now.getTime() - past.getTime());
                    if(minute==0){
                        daysAgo = TimeUnit.MICROSECONDS.toSeconds(now.getTime() - past.getTime()) + " Second(s) Ago";
                    }
                    else{
                        daysAgo = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " Minute(s) Ago";
                    }
                }
                else{
                    daysAgo = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " Hour(s) Ago";
                }
            }
            else{
                daysAgo = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " Day(s) Ago";
            }
        }
        catch (Exception j){
            j.printStackTrace();
        }
        return daysAgo;
    }

    /*
    * GET NEW FILE NAME
    * */
    public String getNewFileName(String fileExtension){
        long createdTime = System.currentTimeMillis();
        return createdTime+"."+fileExtension;
    }

}
