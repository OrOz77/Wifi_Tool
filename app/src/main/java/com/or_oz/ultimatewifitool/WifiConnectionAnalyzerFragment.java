package com.or_oz.ultimatewifitool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by oroz7_000 on 8/25/2015.
 */
//Fragment to get wifi signal strength and display to user
public class WifiConnectionAnalyzerFragment extends Fragment {

    String currentNetwork = "N/A";
    TextView currentNetTV;
    BroadcastReceiver wifiReceiver;

    //factory method to create new instance of fragment
    public static WifiConnectionAnalyzerFragment newInstance(String param1, String param2) {
        WifiConnectionAnalyzerFragment fragment = new WifiConnectionAnalyzerFragment();

        return fragment;
    }

    public WifiConnectionAnalyzerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analyzer, container, false);
        currentNetTV = (TextView) v.findViewById(R.id.currentNetwork);



        return v;
    }

    private String getCurrentNetwork() {
        ConnectivityManager connManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                currentNetwork = connectionInfo.getSSID();
            }
            else {
                currentNetwork = "N/A";
            }
        }
        return currentNetwork;
    }

    @Override
    public void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.EXTRA_SUPPLICANT_CONNECTED);

        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("wifianalyze", "state changed " );
                currentNetwork = getCurrentNetwork();
                currentNetTV.setText(getResources().getString(R.string.currentWifi) + currentNetwork);
            }
        };

        getActivity().registerReceiver(wifiReceiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();

        getActivity().unregisterReceiver(wifiReceiver);
    }


}
