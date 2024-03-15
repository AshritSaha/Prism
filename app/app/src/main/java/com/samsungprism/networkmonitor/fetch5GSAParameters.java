package com.samsungprism.networkmonitor;

import android.content.Context;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.CellSignalStrengthNr;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.widget.TextView;

import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoNr;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.telephony.CellIdentityNr;

import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoNr;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class fetch5GSAParameters extends AppCompatActivity {

    TextView debugInfoTextView, hplmnTextView, slotTextView, splmnTextView, nwSelTextView, emmTacTextView, gutiTextView, rrcBBwTextView, earfcnPCITextView, ltePciTextView, lteRsrpTextView, rrercTextView, pccMimoTextView, txPwrTextView, ulir15TextView, dcnrRTextView, nrRsrpTextView, nrSinrTextView, nrRsrqTextView, nrSsbIndexTextView, nrArfcnTextView, nrPciTextView, nrRlfCntTextView, scgfTextView, nrBandTextView, nrDlTextView;
    private static final int REQUEST_PHONE_STATE_PERMISSION = 123;

    private static final int ACCESS_FINE_LOCATION_PERMISSION = 456;
    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE=789;
    private TextView simStateTextView;
    private TelephonyManager telephonyManager;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private TextView nrDlSchedulingTextView;

    private TextView nrBlerTextView;

    private TextView cardErrorTextView;

    private TextView appStateTextView;

    private double getNrDlScheduling(CellInfoNr cellInfoNr) {
        // This is a placeholder method, you need to implement the logic to extract NR_DL Scheduling
        // from the CellInfoNr object
        // For demonstration purposes, returning a dummy value
        return 0.00;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simStateTextView = findViewById(R.id.simStateTextView);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();
            String simStateStr = getSimStateString(simState);
            simStateTextView.setText("CARD_STATE: " + simStateStr);
        } else {
            simStateTextView.setText("TelephonyManager is null.");
        }

        // Check for permission to read phone state
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed to fetch HPLMN and SIM state
            fetchHPLMNAndSimState();
        } else {
            // Permission not granted, request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
        }

        if (isAppReady()) {
            // Application is ready, proceed with your logic
            // For example, you can start another activity, launch a service, etc.
            // In this example, we are just logging the state
            System.out.println("APP_STATE: Ready");
        } else {
            // Application is not ready, handle the situation accordingly
            // For example, you can display an error message, retry initialization, etc.
            // In this example, we are just logging the state
            System.out.println("APP_STATE: Not Ready");
        }
        checkPermissionAndFetchSimCardError();


    }
    private void checkPermissionAndFetchSimCardError() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            fetchSimCardError();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void fetchSimCardError() {
        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();
            // Update simCardError variable based on SIM state
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    int simCardError = simState;
                    break;
                default:
                    simCardError = -1; // No error detected
                    break;
            }
        } else {
            // Handle case where TelephonyManager is null
        }
    }

    private String getServiceStatus(int serviceState) {
        switch (serviceState) {
            case ServiceState.STATE_IN_SERVICE:
                return "Service available";
            case ServiceState.STATE_OUT_OF_SERVICE:
                return "Service unavailable";
            case ServiceState.STATE_EMERGENCY_ONLY:
                return "Emergency calls only";
            default:
                return "Unknown service state";
        }
    }

    private boolean isAppReady() {
        // Implement your logic here to determine if the application is ready
        // This could involve checking the initialization of components, resources, etc.
        // For demonstration purposes, always return true
        return true;
    }
    private String getSimStateString(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "Unknown";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "Absent";
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "PIN Required";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "PUK Required";
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "Network Locked";
            case TelephonyManager.SIM_STATE_READY:
                return "Ready";
            case TelephonyManager.SIM_STATE_NOT_READY:
                return "Not Ready";
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                return "Permanent Disabled";
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                return "Card IO Error";
            default:
                return "Unknown";
        }
    }

    private void checkPermissionAndFetchNrBler() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fetchNrBler();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void fetchNrBler() {
        if (telephonyManager != null) {
            List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
            if (cellInfoList != null) {
                for (CellInfo cellInfo : cellInfoList) {
                    if (cellInfo instanceof CellInfoNr) {
                        CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                        // Extract NR_BLER or other relevant parameters
                        double nrBler = getNrBler(cellInfoNr);
                        nrBlerTextView.setText("NR_BLER: " + nrBler);
                        return;
                    }
                }
            }
            nrBlerTextView.setText("No 5G cell information available.");
        } else {
            nrBlerTextView.setText("TelephonyManager is null.");
        }
    }

    private double getNrBler(CellInfoNr cellInfoNr) {
        // This is a placeholder method, you need to implement the logic to extract NR_BLER
        // from the CellInfoNr object
        // For demonstration purposes, returning a dummy value
        return 0.00;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchNrBler();
            } else {
                nrBlerTextView.setText("Location permission not granted.");
            }
        }
    }

    private void fetchHPLMNAndSimState() {
        String hplmn = telephonyManager.getNetworkOperator();
        String simState = getSimStateString(telephonyManager.getSimState());

        hplmnTextView.setText("HPLMN: " + hplmn);
        simStateTextView.setText("SIM State: " + simState);
    }

    private String ggetSimStateString(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "Unknown";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "Absent";
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "PIN Required";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "PUK Required";
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "Network Locked";
            case TelephonyManager.SIM_STATE_READY:
                return "Ready";
            case TelephonyManager.SIM_STATE_NOT_READY:
                return "Not Ready";
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                return "Permanently Disabled";
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                return "Card IO Error";
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                return "Card Restricted";
            case 10: // TelephonyManager.SIM_STATE_LOADED
                return "Loaded";
            default:
                return "Unknown";
        }
    }
    public static String getSlotInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();

            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    return "Slot 1 is Absent";
                case TelephonyManager.SIM_STATE_READY:
                    // Check if this slot is the DDS
                    if (telephonyManager.getPhoneCount() > 1 && telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                        // Assuming Slot 1 is designated as DDS
                        return "Slot 1 with DDS";
                    } else {
                        return "Slot 1 is Ready but not configured as DDS";
                    }
                default:
                    return "Unknown SIM state";
            }
        } else {
            return "TelephonyManager is null";
        }
    }

    public static String getServingPLMN(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            // Get the serving PLMN code
            String networkOperator = telephonyManager.getNetworkOperator();
            String servingPLMN = "Unknown";

            if (networkOperator != null && networkOperator.length() >= 5) {
                String mcc = networkOperator.substring(0, 3);
                String mnc = networkOperator.substring(3);
                servingPLMN = mcc + "-" + mnc;
            }

            // Assuming NR5G as the technology
            return "Serving PLMN(" + servingPLMN + ") - NR5G";
        } else {
            return "TelephonyManager is null";
        }
    }

    public static void extractNetworkSelectionMode(Context context, TextView textView) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            int networkSelectionMode = telephonyManager.getNetworkSelectionMode();
            String modeText = "";

            switch (networkSelectionMode) {
                case TelephonyManager.NETWORK_SELECTION_MODE_AUTO:
                    modeText = "Auto";
                    break;
                case TelephonyManager.NETWORK_SELECTION_MODE_MANUAL:
                    modeText = "Manual";
                    break;
                default:
                    modeText = "Unknown";
                    break;
            }

            textView.setText("NW sel mode: " + modeText);
        } else {
            textView.setText("TelephonyManager is null");
        }
    }


    private boolean checkAccessFineLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }
    private void requestAccessFineLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION);
    }
    public void fetchNrRsrp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrRsrpTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    int rsrp = cellSignalStrengthNr.getSsRsrp();
                    nrRsrpTextView.setText(String.valueOf(rsrp));
                } else {
                    nrRsrpTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrRsrpTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrRsrpTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchNrSinr() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrSinrTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    int sinr = cellSignalStrengthNr.getSsSinr();
                    nrSinrTextView.setText(String.valueOf(sinr));
                } else {
                    nrSinrTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrSinrTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrSinrTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchNrRsrq() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrRsrqTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    int rsrq = cellSignalStrengthNr.getSsRsrq();
                    nrRsrqTextView.setText(String.valueOf(rsrq));
                } else {
                    nrRsrqTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrRsrqTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrRsrqTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchNrSsbIdx() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrSsbIndexTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();
                    int ssbIdx = 0;
                    //ssbIdx=;
                    nrSsbIndexTextView.setText(String.valueOf(ssbIdx));
                } else {
                    nrSsbIndexTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrSsbIndexTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrSsbIndexTextView.setText("Android Level Not Supported");
        }
    }


    public void fetchNrArfcn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrArfcnTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();
                    int nrArfcn = cellIdentityNr.getNrarfcn();
                    nrArfcnTextView.setText(String.valueOf(nrArfcn));
                } else {
                    nrArfcnTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrArfcnTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrArfcnTextView.setText("Android Level Not Supported");
        }
    }


    public void fetchNrPci() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrPciTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();
                    int nrPci = cellIdentityNr.getPci();
                    nrPciTextView.setText(String.valueOf(nrPci));
                } else {
                    nrPciTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrPciTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrPciTextView.setText("Android Level Not Supported");
        }
    }


    public void fetchNrBand() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrBandTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr= (CellIdentityNr) cellInfoNr.getCellIdentity();
                    String nrBand="n78";
                    //nrBand=;
                    nrBandTextView.setText(nrBand);
                } else {
                    nrBandTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrBandTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrBandTextView.setText("Android Level Not Supported");
        }
    }


    public void fetchTxPwr() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                txPwrTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    int txPwr = -20;
                    //txPwr=;
                    txPwrTextView.setText(String.valueOf(txPwr));
                } else {
                    txPwrTextView.setText("Sim Not connected to 5G");
                }
            } else {
                txPwrTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            txPwrTextView.setText("Android Level Not Supported");
        }
    }

}

