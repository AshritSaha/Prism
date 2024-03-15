//package com.samsungprism.networkmonitor.util;
//
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.telephony.CellIdentityLte;
//import android.telephony.CellInfo;
//import android.telephony.CellInfoLte;
//import android.telephony.TelephonyManager;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//
//import java.util.List;
//
//public class Utils {
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private int getLtePci() {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
//            return -1; // Permission not granted
//        }
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//
//        List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
//        if (cellInfoList != null) {
//            for (CellInfo cellInfo : cellInfoList) {
//                if (cellInfo instanceof CellInfoLte) {
//                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
//                    CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
//                    return cellIdentityLte.getPci();
//                }
//            }
//        }
//        return -1;
//    }
//}
