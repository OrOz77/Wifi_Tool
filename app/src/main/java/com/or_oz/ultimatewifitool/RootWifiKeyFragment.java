package com.or_oz.ultimatewifitool;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//ROOT NEEDED: fragment to read .conf file with stored Wifi info
public class RootWifiKeyFragment extends android.support.v4.app.Fragment {

    //factory method to create new instance of fragment
    public static RootWifiKeyFragment newInstance(String param1, String param2) {
        RootWifiKeyFragment fragment = new RootWifiKeyFragment();
     /*   Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public RootWifiKeyFragment() {
        // Required empty public constructor
    }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_root_wifi_key, container, false);
                   /*Process p = null;
        final Runtime runtime = Runtime.getRuntime();
        try {

            p = runtime.exec("su -c cat /data/misc/wifi/wpa_supplicant.conf > /sdcard/wifi.conf");
            p.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
            return v;
        }







}
