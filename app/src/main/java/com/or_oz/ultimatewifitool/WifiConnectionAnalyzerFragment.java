package com.or_oz.ultimatewifitool;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by oroz7_000 on 8/25/2015.
 */
//Fragment to get wifi signal strength and display to user
public class WifiConnectionAnalyzerFragment extends Fragment {
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

        return v;
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
