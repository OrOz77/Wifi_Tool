package com.or_oz.ultimatewifitool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.LegendModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oroz7_000 on 8/25/2015.
 */
//Fragment to get wifi signal strength and display to user
public class WifiConnectionAnalyzerFragment extends Fragment {

    Button refreshButton;

    ValueLineSeries series;
    ValueLineChart mCubicValueLineChart;

    String currentNetwork = "N/A";
    TextView currentNetTV;
    TextView wifiStrengthDBMTV;
    TextView wifiStrengthLEVELTV;
    BroadcastReceiver wifiReceiver;

    int wifiStrengthDBM;
    int wifiStrengthLEVEL;
    List<Integer> wifiLevelList = new ArrayList<>();

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
        currentNetTV = (TextView)v.findViewById(R.id.currentNetwork);
        wifiStrengthDBMTV = (TextView)v.findViewById(R.id.wifiStrengthDBM);
        wifiStrengthLEVELTV = (TextView)v.findViewById(R.id.wifiStrengthLEVEL);
        mCubicValueLineChart = (ValueLineChart)v.findViewById(R.id.cubiclinechart);

        refreshButton = (Button)v.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButton();
            }
        });

        //find wifi strength and then update chart on create
        calcWifiStrength();
        updateChart();


        return v;
    }

    //uses wifimanager to get connectivity in dBm
    private void calcWifiStrength() {
        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        wifiStrengthDBM = wifiManager.getConnectionInfo().getRssi();
        wifiStrengthDBMTV.setText("Strength: " + wifiStrengthDBM + "dBm \n" +
                "\tCloser to 0 is better");

        //use dBm value to display in range 0-4 for easier interpretation
        wifiStrengthLEVEL = WifiManager.calculateSignalLevel(wifiStrengthDBM,5);
        wifiLevelList.add(wifiStrengthLEVEL);
        wifiStrengthLEVELTV.setText("Scale (0-4): " + wifiStrengthLEVEL);

    }

    //gets network name, wifi strength, and then updates chart
    private void refreshButton() {
        currentNetwork = getCurrentNetwork();
        if(currentNetwork.equals("<unknown ssid>")){
            currentNetwork = "N/A";
        }
        currentNetTV.setText(getResources().getString(R.string.currentWifi) + currentNetwork);

        calcWifiStrength();

        updateChart();
    }

    //clears chart, adds data from array list, starts animation
    private void updateChart(){
        mCubicValueLineChart.clearChart();

        series = new ValueLineSeries();
        series.setColor(getResources().getColor(R.color.accent));

        //iterates through previously stored wifi level data so chart remains continuous
        for(int i = 0; i < wifiLevelList.size(); i++){
            series.addPoint(new ValueLinePoint(wifiLevelList.get(i)));

        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.constrainView();

        mCubicValueLineChart.startAnimation();
    }

    //sets String currentNetwork to ssid returned from Android Wifi Manager
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

        //broadcast receiver for change in wifi state
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.EXTRA_SUPPLICANT_CONNECTED);

        //runs all refresh operations on any change in wifi condition
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("wifianalyze", "state changed " );
                 refreshButton();
            }
        };

        //register onResume
        getActivity().registerReceiver(wifiReceiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();

        //unregister onPause
        getActivity().unregisterReceiver(wifiReceiver);
    }


}
