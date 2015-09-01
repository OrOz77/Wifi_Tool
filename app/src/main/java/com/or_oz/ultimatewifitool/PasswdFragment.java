package com.or_oz.ultimatewifitool;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.or_oz.ultimatewifitool.api.passwdAPI;
import com.or_oz.ultimatewifitool.model.passwdModel;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

//Fragment to generate random secure passwords using passwd.me API
public class PasswdFragment extends android.support.v4.app.Fragment {

    OnPasswdFragmentInteractionListener mListener;
    String API = "https://passwd.me";
    Button generatePasswdButton;
    TextView passwdResultTextView;
    int length = 12;
    DiscreteSeekBar mDiscreteSeekBar;
    ClipboardManager mClip;
    ClipData clipData;

    //factory method to create new instance of fragment
    public static PasswdFragment newInstance(String param1, String param2) {
        PasswdFragment fragment = new PasswdFragment();
     /*   Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public PasswdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_passwd, container, false);
        generatePasswdButton = (Button)v.findViewById(R.id.generatePasswdButton);
        passwdResultTextView = (TextView)v.findViewById(R.id.passwdResultTextView);
        mDiscreteSeekBar = (DiscreteSeekBar)v.findViewById(R.id.seeker);

        callAPI();


        generatePasswdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });

        passwdResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String passwordGenerated = passwdResultTextView.getText().toString();
                mListener.copyToClip(passwordGenerated);

                return false;
            }
        });

        return v;
    }

    public void callAPI(){
        //sets generator length to seeker value
        length = mDiscreteSeekBar.getProgress();
        Log.i("seeker value", "" + length);
        //create retrofit adapter with url
        RestAdapter rAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();

        //create service with GET class
        passwdAPI passwd = rAdapter.create(passwdAPI.class);

        //call for response
        passwd.getPass(1.0, "json", length, new Callback<passwdModel>() {
            @Override
            public void success(passwdModel passwdModel, Response response) {
                //setting password into textview
                passwdResultTextView.setText(passwdModel.getPassword());
                passwdResultTextView.setTextColor(Color.GRAY);
            }

            @Override
            public void failure(RetrofitError error) {
                passwdResultTextView.setText
                        (getResources().getText(R.string.generateFail));
                passwdResultTextView.setTextColor(Color.RED);
                Log.i("retrofailURL", error.getUrl());
                Log.i("retrofail", error.toString());
            }
        });

    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mListener = (OnPasswdFragmentInteractionListener)activity;
            Log.i("passwd", "callback init");
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() +
                    " must implement interface");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

 //interface to communicate with host activity
    public interface OnPasswdFragmentInteractionListener {
        public void copyToClip(String snip);
    }

}
