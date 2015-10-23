package com.hat.analyzeallapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.AsyncTask;
import android.os.Looper;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by anting.hu on 2015/10/23.
 */
public class ReadAppTask extends AsyncTask<String, Integer, String>
{
    private Context mContext;
    private Hashtable mPerHash;
    private Hashtable mAppHash;
    private AppSearchHandler mainHandler;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Looper.getMainLooper();
        mainHandler.sendEmptyMessage(AppSearchHandler.SEARCH_FINISH);
    }

    public ReadAppTask(Context context, Hashtable perHash, Hashtable appHash, AppSearchHandler handler)
    {
        mContext = context;
        mPerHash = perHash;
        mAppHash = appHash;
        mainHandler = handler;
    }

    @Override
    protected String doInBackground(String... params) {

        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> list = packageManager .getInstalledPackages(PackageManager.GET_PERMISSIONS);
        int total = list.size();
        int count = 0;
        for (PackageInfo packageInfo : list)
        {
            if(packageInfo.applicationInfo.packageName == null || packageInfo.permissions == null)
                continue;

            if(!mAppHash.containsKey(packageInfo.applicationInfo.packageName))
                mAppHash.put(packageInfo.applicationInfo.packageName, packageInfo);

            for(PermissionInfo per : packageInfo.permissions)
            {
                if(per.name == null)
                    continue;
                if(!mPerHash.containsKey(per.name))
                {
                    Hashtable tmpHash = new Hashtable();
                    tmpHash.put(packageInfo.applicationInfo.packageName, "");
                    mPerHash.put(per.name, tmpHash);
                }
                else
                {
                    Hashtable tmpHash = (Hashtable)mPerHash.get(per.name);
                    if(!tmpHash.containsKey(packageInfo.applicationInfo.packageName))
                    {
                        tmpHash.put( packageInfo.applicationInfo.packageName, "");
                    }
                }
            }
            count++;
            publishProgress((int)(count/(float)total) * 100);
        }
        return null;
    }
}
