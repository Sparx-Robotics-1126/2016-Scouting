package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Date;

/**
 * Created by jbass on 4/5/14.
 */
public class NetworkHelper {

    public static final long MS_IN_DAY = 86400000;

    public static final String PREF_FILE_NAME = "NETWORK_PREFERENCES";
    public static final String PREF_LOAD_EVENT_LIST = "LOAD_EVENT_LIST";

    public static boolean isNetworkAvailable(Context context) {
        boolean result = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
        return result;
    }


    public static boolean needToLoadEventList(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_EVENT_LIST, 0);

        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static void setLoadedEventList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_EVENT_LIST);
    }

    private static void setPrefCurrentTime(Context context, String pref){
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(pref, new Date().getTime())
                .commit();
    }
}
