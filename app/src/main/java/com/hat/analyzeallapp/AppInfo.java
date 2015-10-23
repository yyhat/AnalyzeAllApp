package com.hat.analyzeallapp;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anting.hu on 2015/10/23.
 */
public class AppInfo {
    public String AppName;
    public String PackageName;
    private List<String> permissionList = new ArrayList<String>();
    public PackageInfo mPackInfo;
}
