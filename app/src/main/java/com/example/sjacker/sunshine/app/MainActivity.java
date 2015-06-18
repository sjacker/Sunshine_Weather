package com.example.sjacker.sunshine.app;

// import android.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_preferred_location) {
            showPreferredLocationOnMap();
            return true;
        }

        if (id == R.id.action_selfie) {
            launchCameraSelfieMode();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPreferredLocationOnMap() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(getString(R.string.pref_location_key),
                getString(R.string.default_zip));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        else {
            Toast.makeText(getApplicationContext(), "Location Invalid, Try Again Sucker", Toast.LENGTH_LONG).show();
        }

    }

    private void launchCameraSelfieMode() {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        else {
            Toast.makeText(getApplicationContext(), "lol you don't have a camera on yo phone", Toast.LENGTH_LONG).show();
        }
    }

}

