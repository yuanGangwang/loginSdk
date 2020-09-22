package com.guuidea.tower;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.util.Log;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private void getPermisson(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            //得到自己的包名
            String pkgName = pi.packageName;

            PermissionGroupInfo pgi;
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
            String sharedPkgList[] = pkgInfo.requestedPermissions;
            StringBuilder tv = new StringBuilder();
            for(int i=0;i<sharedPkgList.length;i++){
                String permName = sharedPkgList[i];

                PermissionInfo tmpPermInfo = pm.getPermissionInfo(permName, 0);

                tv.append(i+"-"+permName+"\n");
                pgi = pm.getPermissionGroupInfo(tmpPermInfo.group, 0);

                tv.append(i+"-"+pgi.loadLabel(pm).toString()+"\n");
                tv.append(i+"-"+tmpPermInfo.loadLabel(pm).toString()+"\n");
                tv.append(i+"-"+tmpPermInfo.loadDescription(pm).toString()+"\n");
            }
            Log.i("##ddd", "getPermisson: "+tv);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("##ddd", "Could'nt retrieve permissions for package");

        }
    }

}
