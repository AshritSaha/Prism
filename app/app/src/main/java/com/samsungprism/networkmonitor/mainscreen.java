package com.samsungprism.networkmonitor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.util.Log;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.telephony.SignalStrength;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class mainscreen extends AppCompatActivity {
    TextView signalStrengthTextView;
    TextView networkTypeTextView;
    TextView debugInfoTextView;
    TextView hplmnTextView;
    TextView simStateTextView;
    TextView slotTextView;
    TextView servingPlmnTextView;
    TextView nwSelModeTextView;
    TextView emmStateTextView;
    TextView tacTextView;
    TextView gutiTextView;
    TextView lteRrcTextView;
    TextView earfcnPciTextView;

    TelephonyManager telephonyManager;
    MyTelephonyCallback myTelephonyCallback;
    SignalStrength signalStrength; // Declare signalStrength globally

    private static final int REQUEST_PHONE_STATE_PERMISSION = 123; // Use any unique value
    private static final int REQUEST_LOCATION_PERMISSION = 123; // Use any unique value

    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        signalStrengthTextView = findViewById(R.id.signalStrengthMainscreen);
        networkTypeTextView = findViewById(R.id.networkTypeMainscreen);
        debugInfoTextView = findViewById(R.id.debugInfoTextView);
        hplmnTextView = findViewById(R.id.hplmnTextView);
        simStateTextView = findViewById(R.id.simStateTextView);
        slotTextView = findViewById(R.id.slotTextView);
        servingPlmnTextView = findViewById(R.id.servingPlmnTextView);
        nwSelModeTextView = findViewById(R.id.nwSelModeTextView);
        emmStateTextView = findViewById(R.id.emmStateTextView);
        tacTextView = findViewById(R.id.tacTextView);
        gutiTextView = findViewById(R.id.gutiTextView);
        lteRrcTextView = findViewById(R.id.lteRrcTextView);
        earfcnPciTextView = findViewById(R.id.earfcnPciTextView);

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            myTelephonyCallback=new MyTelephonyCallback();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyManager.registerTelephonyCallback(getMainExecutor(), myTelephonyCallback);
        }

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onSignalStrengthsChanged(SignalStrength sStrength) {
                super.onSignalStrengthsChanged(sStrength);
                int strength = getSignalStrength(sStrength);
                signalStrengthTextView.setText("Signal Strength: " + strength + " dBm");
                String networkType = getNetworkType(telephonyManager);
                networkTypeTextView.setText("Network Type: " + networkType);
                signalStrength = sStrength;
                updateAdditionalInfo();
            }

        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("LocationPermission", "Location permission granted. Trying to get last known location.");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            String tac = extractTAC(location);
                            tacTextView.setText("TAC: " + tac);
                        } else {
                            tacTextView.setText("TAC: Location not available");
                            Log.d("LastLocation", "Last known location is null.");
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            Log.d("LocationPermission", "Location permission not granted. Requesting permission.");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public class MyTelephonyCallback extends TelephonyCallback implements TelephonyCallback.ServiceStateListener {
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            int emmState = serviceState.getState();
            emmStateTextView.setText("EMM State: " + emmState);
            updateAdditionalInfo();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private int getSignalStrength(SignalStrength signalStrength) {
        int strength = 0;

        if (signalStrength.isGsm()) {
            strength = signalStrength.getGsmSignalStrength();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            strength = signalStrength.getLevel();
        }

        return strength;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getNetworkType(TelephonyManager telephonyManager) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
            return "Permission not granted";
        }
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_NR:
                return "5G NR";
            default:
                return "Unknown";
        }
    }


    private void updateAdditionalInfo() {
        String debugInfo = getDebugInfo();
        String hplmn = telephonyManager.getNetworkOperator();
        String simState = String.valueOf(telephonyManager.getSimState());
        String slot = "Slot " + telephonyManager.getPhoneCount() + " with DDS";
        String servingPlmn = telephonyManager.getNetworkOperatorName();
        String nwSelMode = "Auto"; // Replace with actual value
        int ltePci = getLtePci();

    //    String tac = signalStrength.getTac() + "";
//        String guti = signalStrength.getGuti();
//        String lteRrc = "CONN BAND:" + signalStrength.getNrFrequencyRange() + " BW:20";
//        String earfcnPci = "Earfcn: " + signalStrength.getNrEarfcn() + ", PCI: " + signalStrength.getNrPci();

        debugInfoTextView.setText("Debug Info: " + debugInfo);
        hplmnTextView.setText("HPLMN: " + hplmn);
        simStateTextView.setText("SIM State: " + simState);
        slotTextView.setText("Slot: " + slot);
        servingPlmnTextView.setText("Serving PLMN: " + servingPlmn);
        nwSelModeTextView.setText("NW Selection Mode: " + nwSelMode);
//        tacTextView.setText("TAC: " + tac);
//        gutiTextView.setText("GUTI: " + guti);
//        lteRrcTextView.setText("LTE RRC: " + lteRrc);
        earfcnPciTextView.setText("PCI: " + (ltePci != -1 ? String.valueOf(ltePci) : "Unknown"));

    }
    private String getDebugInfo() {
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n");
        debugInfo.append("Model: ").append(Build.MODEL).append("\n");
        debugInfo.append("Android Version: ").append(Build.VERSION.RELEASE).append("\n");
        debugInfo.append("API Level: ").append(Build.VERSION.SDK_INT).append("\n");
        return debugInfo.toString();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getLtePci() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
            return -1; // Permission not granted
        }

        List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
        if (cellInfoList != null) {
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
                    return cellIdentityLte.getPci();
                }
            }
        }
        return -1;
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Unregister the PhoneStateListener to avoid memory leaks
//        if(telephonyManager!=null) {
//            telephonyManager.listen(null, PhoneStateListener.LISTEN_NONE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                if(myTelephonyCallback!=null) {
//                    telephonyManager.unregisterTelephonyCallback(myTelephonyCallback);
//                    myTelephonyCallback=null;
//                }
//            }
//        }
//    }
    private String extractTAC(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String tac = address.getLocality(); // Change this line based on the actual TAC information in the address
                return tac != null ? tac : "UnknownTAC";
            }
        } catch (IOException e) {
            Log.e("extractTAC", "Error getting TAC from location", e);
        }

        return "UnknownTAC";
    }

    public void fetch5GNSAParams(View view) {
        Intent intent=new Intent(mainscreen.this, fetch5GNSAParameters.class);
        startActivity(intent);
        finish();
    }
}
