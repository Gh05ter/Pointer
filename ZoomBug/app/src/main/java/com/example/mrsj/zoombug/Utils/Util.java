package com.example.mrsj.zoombug.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MR.SJ on 2016/4/21.
 */
public class Util {

    public  static void HeiToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }

    public static  void logInfo(String TAG,String s){
        Log.w(TAG,s);
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            cm.getActiveNetworkInfo().isAvailable();
            return true;
        }
        return false;
    }
}
