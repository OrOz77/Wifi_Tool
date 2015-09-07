package com.or_oz.ultimatewifitool;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//ROOT NEEDED: fragment to read .conf file with stored Wifi info
public class RootWifiKeyFragment extends android.support.v4.app.Fragment {
    TextView mTextView;

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

            //for testing purposes: click TV to run SU in separate thread
            mTextView = (TextView)v.findViewById(R.id.textviewClick);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Runnable() {
                        @Override
                        public void run() {
                            runSU();
                        }
                    }.run();

                }
            });

            return v;
        }

    //runs SU commands to copy wpa_supplicant.conf file
    private void runSU() {

        Process p = null;
        final Runtime runtime = Runtime.getRuntime();
        try {

            p = runtime.exec("su");
            p.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
