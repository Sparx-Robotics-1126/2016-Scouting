package com.sparx1126.scoutingapp2016;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.sparx1126.scoutingapp2016.fragments.Benchmarking.AcquisitionFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.DrivesFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.ScoringFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.SoftwareFragment;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.List;

import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.SparxScouting;
public class Benchmarking extends FragmentActivity implements DrivesFragment.OnFragmentInteractionListener, SoftwareFragment.OnFragmentInteractionListener,
    AcquisitionFragment.OnFragmentInteractionListener, ScoringFragment.OnFragmentInteractionListener{

    public static ScoutingInfo info;
    private DatabaseHelper dbHelper;
    private FragmentManager fm;
    private DrivesFragment drivesFragment;
    private SoftwareFragment softwareFragment;
    private AcquisitionFragment acquisitionFragment;
    private ScoringFragment scoringFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = DatabaseHelper.getInstance(this);
        setContentView(R.layout.activity_benchmarking);
        Intent i = getIntent();

        fm = getFragmentManager();
        if(dbHelper.doesBenchmarkingExist(info))
        {
            // a benchmarking record exists in the database for this (event + team + scouter name) so
            // load it in.
            List<ScoutingInfo> scoutList= dbHelper.getBenchmarking(info.getEventKey(), info.getTeamKey(), info.getNameOfScouter());
            info = scoutList.get(0);
        }
        else
        {
            // no benchmarking record exists in the database for this (event + team + scouter name) so create one.
            dbHelper.createBenchmarking(info);
        }
        //instantiate fragments
        drivesFragment = DrivesFragment.newInstance(info);
        softwareFragment = SoftwareFragment.newInstance(info);
        acquisitionFragment = AcquisitionFragment.newInstance(info);
        scoringFragment = ScoringFragment.newInstance(info);

        // set title of activity to the team number
        Toolbar toolbar = ((Toolbar)findViewById(R.id.toolbar));
        toolbar.setTitle("Benchmarking for: " + info.getTeamKey().replace("frc", ""));
    }

    //necessary inclusion for all fragments
    @Override
    public void onFragmentInteraction(Uri uri){
    }

    /**
     * changes which fragment is showing depending on which button was clicked
     *
     * @param view the view that was clicked
     */
    public void switchFragment(View view){
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch(view.getId()){
            case R.id.bench_drives:
                if(softwareFragment.isAdded()){
                    ft.hide(softwareFragment);
                }
                if(acquisitionFragment.isAdded()){
                    ft.hide(acquisitionFragment);
                }
                if(scoringFragment.isAdded()){
                    ft.hide(scoringFragment);
                }
                if(!drivesFragment.isAdded())
                    ft.add(R.id.fragContainer, drivesFragment);
                ft.show(drivesFragment);
                break;

            case R.id.bench_sw_elec:
                if(acquisitionFragment.isAdded()){
                    ft.hide(acquisitionFragment);
                }
                if(scoringFragment.isAdded()){
                    ft.hide(scoringFragment);
                }
                if(drivesFragment.isAdded())
                    ft.hide(drivesFragment);
                if(!softwareFragment.isAdded()){
                    ft.add(R.id.fragContainer, softwareFragment);
                }

                ft.show(softwareFragment);
                break;
            case R.id.bench_acq:
                if(softwareFragment.isAdded()){
                    ft.hide(softwareFragment);
                }
                if(scoringFragment.isAdded()){
                    ft.hide(scoringFragment);
                }
                if(drivesFragment.isAdded())
                    ft.hide(drivesFragment);

                if(!acquisitionFragment.isAdded()){
                    ft.add(R.id.fragContainer, acquisitionFragment);
                }
                ft.show(acquisitionFragment);
                break;
            case R.id.bench_scoring:
                if(softwareFragment.isAdded()){
                    ft.hide(softwareFragment);
                }
                if(acquisitionFragment.isAdded()){
                    ft.hide(acquisitionFragment);
                }
                if(drivesFragment.isAdded())
                    ft.hide(drivesFragment);
                if(!scoringFragment.isAdded()){
                    ft.add(R.id.fragContainer, scoringFragment);
                }

                ft.show(scoringFragment);}
        ft.commit();
    }

    /**
     * save data whenever we exit the activity
     * @param keyCode the code of this key
     * @param event the event to process
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
            drivesFragment.getScoutingInfo();
            acquisitionFragment.getScoutingInfo();
            scoringFragment.getScoutingInfo();
            softwareFragment.getScoutingInfo();
            dbHelper.updateBenchmarking(info);
            drivesFragment = null;
            acquisitionFragment = null;
            softwareFragment = null;
            scoringFragment = null;

            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
