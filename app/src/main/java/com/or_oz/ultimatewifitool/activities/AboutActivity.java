package com.or_oz.ultimatewifitool.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.or_oz.ultimatewifitool.R;

/**
 * Created by oroz7_000 on 9/1/2015.
 */
//activity to show user information of how the app works and a link to my website
public class AboutActivity extends AppCompatActivity {
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about = (TextView)findViewById(R.id.about);

        about.setText("\n\n\t" + getResources().getText(R.string.AboutIntro) + "\n\n\t"
         + getResources().getText(R.string.AboutAnalyze) + "\n\n\t"
        + getResources().getText(R.string.AboutGenerate) + "\n\n\t"
        + getResources().getText(R.string.AboutRecover) + "\n\n"
        + getResources().getText(R.string.AboutMore));

        about.setTextSize(14f);
    }
}
