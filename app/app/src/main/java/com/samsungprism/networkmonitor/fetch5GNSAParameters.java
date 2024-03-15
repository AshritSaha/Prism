package com.samsungprism.networkmonitor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class fetch5GNSAParameters extends AppCompatActivity {

    TextView debugInfoTextView, hplmnTextView, slotTextView, splmnTextView, nwSelTextView, emmTacTextView, gutiTextView, rrcBBwTextView, earfcnPCITextView, ltePciTextView, lteRsrpTextView, rrercTextView, pccMimoTextView, txPwrTextView, ulir15TextView, dcnrRTextView, nrRsrpTextView, nrSinrTextView, nrRsrqTextView, nrSsbIndexTextView, nrArfcnTextView, nrPciTextView, nrRlfCntTextView, scgfTextView, nrBandTextView, nrDlTextView;

    TelephonyManager telephonyManager;

    private static final int REQUEST_PHONE_STATE_PERMISSION = 123;
    private static final int ACCESS_FINE_LOCATION_PERMISSION = 456;
    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE=789;

    // SA parameters TextViews
    TextView saParametersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch5_gnsaparameters);

        debugInfoTextView = findViewById(R.id.debugInfoValue);
        hplmnTextView = findViewById(R.id.hplmnValue);
        slotTextView = findViewById(R.id.slotValue);
        splmnTextView = findViewById(R.id.splmnValue);
        nwSelTextView = findViewById(R.id.nwSelValue);
        emmTacTextView = findViewById(R.id.emmTacValue);
        gutiTextView = findViewById(R.id.gutiValue);
        rrcBBwTextView = findViewById(R.id.rrcBBwValue);
        earfcnPCITextView = findViewById(R.id.earfcnPciValue);
        ltePciTextView = findViewById(R.id.ltePciValue);
        lteRsrpTextView = findViewById(R.id.lteRsrpValue);
        rrercTextView = findViewById(R.id.rrercValue);
        pccMimoTextView = findViewById(R.id.pccMimoValue);
        txPwrTextView = findViewById(R.id.txPwrValue);
        ulir15TextView = findViewById(R.id.ulir15Value);
        dcnrRTextView = findViewById(R.id.dcnrRValue);
        nrRsrpTextView = findViewById(R.id.nrRsrpValue);
        nrSinrTextView = findViewById(R.id.nrSinrValue);
        nrRsrqTextView = findViewById(R.id.nrRsrqValue);
        nrSsbIndexTextView = findViewById(R.id.nrSsbIdxValue);
        nrArfcnTextView = findViewById(R.id.nrArfcnValue);
        nrPciTextView = findViewById(R.id.nrPciValue);
        nrRlfCntTextView = findViewById(R.id.nrRlfCntValue);
        scgfTextView=findViewById(R.id.scgfValue);
        nrBandTextView=findViewById(R.id.nrBandValue);
        nrDlTextView=findViewById(R.id.nrDlValue);


        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null && checkAndRequestPermissions()) {
            fetchDebugInfo();
            fetchHPLMN();
            fetchSlot();
            fetchSPLMN();
            fetchNwSelMode();
            fetchEmmTac();
            fetchGuti();
            fetchRrcBBw();
            fetchEarfcnPci();
            fetchLtePCI();
            fetchLteRsrp();
            fetchRrerc();
            fetchPccMimo();
            fetchTxPwr();
            fetchUlir15();
            fetchDcnrR();
            fetchNrRsrp();
            fetchNrSinr();
            fetchNrSsbIdx();
            fetchNrArfcn();
            fetchNrPci();
            fetchNrRlfCnt();
            fetchScgf();
            fetchNrBand();
            fetchNrDl();
        } else {
            debugInfoTextView.setText("telephonyManager is null");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAndRequestPermissions();
    }

    private boolean checkReadPhoneStatePermission() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadPhoneStatePermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
    }

    private boolean checkAccessFineLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestAccessFineLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION);
    }

    private boolean checkAndRequestPermissions() {
        String[] requiredPermissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            String[] permissionsToRequest = missingPermissions.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissionsToRequest, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MULTIPLE_PERMISSIONS_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
            } else {
            }
        }
    }


    public void fetchDebugInfo() {
        debugInfoTextView.setText(String.valueOf(checkAndRequestPermissions()));
    }

    public void fetchHPLMN() {
        String hplmn = telephonyManager.getSimOperator();
        String simState = String.valueOf(telephonyManager.getSimState());
        hplmnTextView.setText(hplmn + " " + simState);
    }

    public void fetchSlot() {
        String slotInfo = "";
        for (int slotIndex = 0; slotIndex < telephonyManager.getPhoneCount(); slotIndex++) {
            int slotState = telephonyManager.getSimState();
            switch (slotState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    slotInfo += "Slot " + (slotIndex + 1) + " is Absent; ";
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    slotInfo += "Slot " + (slotIndex + 1) + " with DDS ; ";//+isDDSActive(slotIndex, telephonyManager);
                    break;
            }
        }
        slotTextView.setText(slotInfo);
    }

    public void fetchSPLMN() {
        if (checkReadPhoneStatePermission()) {
            String networkOperator = telephonyManager.getNetworkOperator();
            String networkInfrastructure = "LTE";
            splmnTextView.setText(networkOperator + "-" + networkInfrastructure);
        } else {
            splmnTextView.setText("Read Phone State Permission Not Granted");
        }
    }

    public void fetchNwSelMode() {
        if (checkReadPhoneStatePermission()) {
            String nwSelMode = "Auto";//null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                switch (telephonyManager.getNetworkSelectionMode()) {
//                    case TelephonyManager.NETWORK_SELECTION_MODE_AUTO:
//                        nwSelMode = "Auto";
//                        break;
//                    case TelephonyManager.NETWORK_SELECTION_MODE_MANUAL:
//                        nwSelMode = "Manual";
//                        break;
//                    default:
//                        nwSelMode = "Unknown";
//                        break;
//                } else {
//                emmTacTextView.setText("Android Level Not Supported");
//                }
//            }
            nwSelTextView.setText(nwSelMode);
        } else {
            requestReadPhoneStatePermission();
            nwSelTextView.setText("Read Phone State Permission Not Granted");
        }
    }

    public void fetchEmmTac() {
        if (checkReadPhoneStatePermission()) {
            String emm = "Registered-0";
            String tac = "8249";
//            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
//                emm=;
//                tac=;
//            } else {
//                emmTacTextView.setText("Android Level Not Supported");
//            }
            emmTacTextView.setText(emm + "-" + tac);
        } else {
            requestReadPhoneStatePermission();
            emmTacTextView.setText("Read Phone State Permission Not Granted");
        }
    }

    public void fetchGuti() {
        if (checkReadPhoneStatePermission()) {
            String mme = "3c23-50";
            String mtmsi = "74ea48c9";
            //mme=;
            //mtmsi=;
            gutiTextView.setText(hplmnTextView.getText().toString() + "-" + mme + "-" + mtmsi);
        } else {
            requestReadPhoneStatePermission();
            gutiTextView.setText("Read Phone State Permission Not Granted");
        }
    }

    public void fetchRrcBBw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkAccessFineLocationPermission()) {
                rrcBBwTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
                    CellSignalStrength cellSignalStrength = cellInfoLte.getCellSignalStrength();

                    String connectionStatus = cellInfoLte.isRegistered() ? "CONN" : "IDLE";
                    int band = cellIdentityLte.getEarfcn();
                    int bandwidth = cellIdentityLte.getBandwidth();
                    String rrcBBw = connectionStatus + "-" + String.valueOf(band) + "-" + String.valueOf(bandwidth);
                    rrcBBwTextView.setText(rrcBBw);
                } else {
                    rrcBBwTextView.setText("telephonyManager.getAllCellInfo() is Empty");
                }
            }
        } else {
            rrcBBwTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchEarfcnPci() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                earfcnPCITextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();

                    int earfcn = cellIdentityNr.getNrarfcn();
                    int pci = cellIdentityNr.getPci();
                    String earfcnPci = String.valueOf(earfcn) + "-" + String.valueOf(pci);
                    earfcnPCITextView.setText(earfcnPci);
                } else {
                    earfcnPCITextView.setText("telephonyManager.getAllCellInfo() is Empty");
                }
            } else {
                earfcnPCITextView.setText("Sim Not connected to 5G");
            }
        } else {
            earfcnPCITextView.setText("Android Level Not Supported");
        }
    }

    public void fetchLtePCI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkAccessFineLocationPermission()) {
                ltePciTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();

                    int earfcn = cellIdentityLte.getEarfcn();
                    int pci = cellIdentityLte.getPci();
                    String earfcnPci = String.valueOf(earfcn) + "-" + String.valueOf(pci);
                    ltePciTextView.setText(earfcnPci);
                } else {
                    earfcnPCITextView.setText("Sim Not connected to LTE");
                }
            } else {
                ltePciTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            ltePciTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchLteRsrp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkAccessFineLocationPermission()) {
                lteRsrpTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoLte) {
                    CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                    int rsrp = cellSignalStrengthLte.getRsrp();
                    int rsrq = cellSignalStrengthLte.getRsrq();
                    int snr = cellSignalStrengthLte.getRssnr();
                    String lteRsrp = String.valueOf(rsrp) + "-" + String.valueOf(rsrq) + "-" + String.valueOf(snr);
                    lteRsrpTextView.setText(lteRsrp);
                } else {
                    lteRsrpTextView.setText("Sim Not connected to LTE");
                }
            } else {
                lteRsrpTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            lteRsrpTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchRrerc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                rrercTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    String rrerc = "--";
                    //rrerc=;
                    rrercTextView.setText(rrerc);
                } else {
                    rrercTextView.setText("Sim Not connected to 5G");
                }
            } else {
                rrercTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            rrercTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchPccMimo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                pccMimoTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    int pccMimo = 2;
                    //pccMimio=;
                    pccMimoTextView.setText(String.valueOf(pccMimo));
                } else {
                    pccMimoTextView.setText("Sim Not connected to 5G");
                }
            } else {
                pccMimoTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            pccMimoTextView.setText("Android Level Not Supported");
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

    public void fetchUlir15() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String ulir15 = "Not Supported";
            //ulir15=;
            ulir15TextView.setText(ulir15);
        } else {
            ulir15TextView.setText("Android Level Not Supported");
        }
    }

    public void fetchDcnrR() {
        boolean dcnrR = false;
        //dcnrR=;
        dcnrRTextView.setText(String.valueOf(dcnrR));
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

    public void fetchNrRlfCnt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                nrRlfCntTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                    String nrRlfCnt = "--";
                    //nrRlfCnt=;
                    nrRlfCntTextView.setText(String.valueOf(nrRlfCnt));
                } else {
                    nrRlfCntTextView.setText("Sim Not connected to 5G");
                }
            } else {
                nrRlfCntTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            nrRlfCntTextView.setText("Android Level Not Supported");
        }
    }

    public void fetchScgf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkAccessFineLocationPermission()) {
                scgfTextView.setText("Access Fine Location Not Granted");
                requestAccessFineLocationPermission();
                return;
            }
            if (!telephonyManager.getAllCellInfo().isEmpty()) {
                CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
                if (cellInfo instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
                    CellIdentityNr cellIdentityNr= (CellIdentityNr) cellInfoNr.getCellIdentity();
                    String scgf = "--";
                    //scgf=;
                    scgfTextView.setText(String.valueOf(scgf));
                } else {
                    scgfTextView.setText("Sim Not connected to 5G");
                }
            } else {
                scgfTextView.setText("telephonyManager.getAllCellInfo() is Empty");
            }
        } else {
            scgfTextView.setText("Android Level Not Supported");
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

    public void fetchNrDl() {
        double nrDl = 0.00;
        //nrDl=;
        nrDlTextView.setText(String.valueOf(nrDl));
    }

}