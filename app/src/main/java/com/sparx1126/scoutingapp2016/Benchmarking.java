package com.sparx1126.scoutingapp2016;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.sparx1126.scoutingapp2016.fragments.Benchmarking.AcquisitionFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.DrivesFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.ScoringFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.SoftwareFragment;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.List;

public class Benchmarking extends FragmentActivity {

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
        setContentView(R.layout.activity_match_scouting);
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

        drivesFragment = DrivesFragment.newInstance(info);
        softwareFragment = SoftwareFragment.newInstance(info);
        acquisitionFragment = AcquisitionFragment.newInstance(info);
        scoringFragment = ScoringFragment.newInstance(info);

        // set title of activity to the team number
        Toolbar toolbar = ((Toolbar)findViewById(R.id.toolbar));
        toolbar.setTitle("Benchmarking for: " + info.getTeamKey());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
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
