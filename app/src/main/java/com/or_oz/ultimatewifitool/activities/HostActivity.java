package com.or_oz.ultimatewifitool.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.or_oz.ultimatewifitool.PasswdFragment;
import com.or_oz.ultimatewifitool.R;
import com.or_oz.ultimatewifitool.WifiConnectionAnalyzerFragment;

//TODO recover fragment
//Main activity hosting viewpager with the different fragments
public class HostActivity extends AppCompatActivity implements PasswdFragment.OnPasswdFragmentInteractionListener {

    ClipboardManager mClip;
    ClipData clipData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding frsgments to viewpager
        //recover fragment coming soon
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
               // .add("Recover", RootWifiKeyFragment.class)
                .add("Analyze", WifiConnectionAnalyzerFragment.class)
                .add("Generate", PasswdFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setBackgroundColor(getResources().getColor(R.color.primary));
        viewPagerTab.setViewPager(viewPager);

        //clipboard manager for copy feature
        mClip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

    }

    //copying string parameter to clipboard and displaying snackbar
    public void copyToClip(String snip){
        clipData = ClipData.newPlainText(snip,snip);
        mClip.setPrimaryClip(clipData);

        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);

        Snackbar snackbar = Snackbar
                .make(coordinatorLayoutView,snip + " copied to clipboard", Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView)snackbarView .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //launch about activity
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
