package com.sparx1126.scoutingapp2016;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sparx1126.scoutingapp2016.fragments.Benchmarking.AcquisitionFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.DrivesFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.ScoringFragment;
import com.sparx1126.scoutingapp2016.fragments.Benchmarking.SoftwareFragment;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.List;

public class Benchmarking extends FragmentActivity implements DrivesFragment.OnFragmentInteractionListener, SoftwareFragment.OnFragmentInteractionListener,
        AcquisitionFragment.OnFragmentInteractionListener, ScoringFragment.OnFragmentInteractionListener {

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

        fm = getFragmentManager();
        if (dbHelper.doesBenchmarkingExist(info)) {
            // a benchmarking record exists in the database for this (event + team + scouter name) so
            // load it in.
            List<ScoutingInfo> scoutList = dbHelper.getBenchmarking(info.getEventKey(), info.getTeamKey(), info.getNameOfScouter());
            info = scoutList.get(0);
        } else {
            // no benchmarking record exists in the database for this (event + team + scouter name) so create one.
            dbHelper.createBenchmarking(info);
        }
        //instantiate fragments
        drivesFragment = DrivesFragment.newInstance(info);
        softwareFragment = SoftwareFragment.newInstance(info);
        acquisitionFragment = AcquisitionFragment.newInstance(info);
        scoringFragment = ScoringFragment.newInstance(info);

        // set title of activity to the team number
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        toolbar.setTitle("Benchmarking for: Team " + info.getTeamKey().replace("frc", ""));
        //create fragment views
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragContainer, drivesFragment);
        ft.add(R.id.fragContainer, softwareFragment);
        ft.add(R.id.fragContainer, acquisitionFragment);
        ft.add(R.id.fragContainer, drivesFragment);
        ft.commit();

        // auto-select the Drives Fragment
        switchFragment(findViewById(R.id.bench_drives));
    }

    //necessary inclusion for all fragments
    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * changes which fragment is showing depending on which button was clicked
     *
     * @param view the view that was clicked
     */
    public void switchFragment(View view) {
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        view.setKeepScreenOn(true);
        HighlightButton(view.getId());

        switch (view.getId()) {
            case R.id.bench_drives:
                if (softwareFragment.isAdded()) {
                    ft.hide(softwareFragment);
                }
                if (acquisitionFragment.isAdded()) {
                    ft.hide(acquisitionFragment);
                }
                if (scoringFragment.isAdded()) {
                    ft.hide(scoringFragment);
                }
                if (!drivesFragment.isAdded())
                    ft.add(R.id.fragContainer, drivesFragment);
                ft.show(drivesFragment);
                break;

            case R.id.bench_sw_elec:
                if (acquisitionFragment.isAdded()) {
                    ft.hide(acquisitionFragment);
                }
                if (scoringFragment.isAdded()) {
                    ft.hide(scoringFragment);
                }
                if (drivesFragment.isAdded())
                    ft.hide(drivesFragment);
                if (!softwareFragment.isAdded()) {
                    ft.add(R.id.fragContainer, softwareFragment);
                }

                ft.show(softwareFragment);
                break;
            case R.id.bench_acq:
                if (softwareFragment.isAdded()) {
                    ft.hide(softwareFragment);
                }
                if (scoringFragment.isAdded()) {
                    ft.hide(scoringFragment);
                }
                if (drivesFragment.isAdded())
                    ft.hide(drivesFragment);

                if (!acquisitionFragment.isAdded()) {
                    ft.add(R.id.fragContainer, acquisitionFragment);
                }
                ft.show(acquisitionFragment);
                break;
            case R.id.bench_scoring:
                if (softwareFragment.isAdded()) {
                    ft.hide(softwareFragment);
                }
                if (acquisitionFragment.isAdded()) {
                    ft.hide(acquisitionFragment);
                }
                if (drivesFragment.isAdded())
                    ft.hide(drivesFragment);
                if (!scoringFragment.isAdded()) {
                    ft.add(R.id.fragContainer, scoringFragment);
                }

                ft.show(scoringFragment);
        }
        ft.commit();
    }

    private void HighlightButton(int buttonId)
    {
        Button drives = (Button)findViewById(R.id.bench_drives);
        Button sw = (Button)findViewById(R.id.bench_sw_elec);
        Button acq = (Button)findViewById(R.id.bench_acq);
        Button scoring = (Button)findViewById(R.id.bench_scoring);
        drives.setTextColor(buttonId == R.id.bench_drives ? 0xffff0000 : 0xff000000);
        sw.setTextColor(buttonId == R.id.bench_sw_elec ? 0xffff0000 : 0xff000000);
        acq.setTextColor(buttonId == R.id.bench_acq ? 0xffff0000 : 0xff000000);
        scoring.setTextColor(buttonId == R.id.bench_scoring ? 0xffff0000 : 0xff000000);
    }

    /**
     * save data whenever we exit the activity
     *
     * @param keyCode the code of this key
     * @param event   the event to process
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
            drivesFragment.getScoutingInfo();
            acquisitionFragment.getScoutingInfo();
            scoringFragment.getScoutingInfo();
            softwareFragment.getScoutingInfo();
            //check if benchmarking is empty to see if it needs to be stored
            //...HELP ME
            if (info.getAcquiresBouldersFromFloor() || info.getCanScoreInLowGoal() || info.getCanCrossMoat() || info.getAutoEndInCourtyard() || info.getAutoEndInNeutralZone() || info.getAutoStartInNeutralZone()
                    || info.getAutoStartInSpyPosition() || info.getCanCarryBouldersOverCheval() || info.getCanCarryBouldersOverDrawbridge() || info.getCanCarryBouldersOverLowbar() || info.getCanCarryBouldersOverMoat()
                    || info.getCanCarryBouldersOverPortcullis() || info.getCanCarryBouldersOverRamparts() || info.getCanCarryBouldersOverRockwall() || info.getCanCarryBouldersOverRoughterrain() || info.getCanCarryBouldersOverSallyport()
                    || info.getCanCrossCheval() || info.getCanCrossDrawbridge() || info.getCanCrossLowbar() || info.getCanCrossPortcullis() || info.getCanCrossPortcullis() || info.getCanCrossRamparts()
                    || info.getCanCrossRockwall() || info.getCanCrossRoughterrain() || info.getCanCrossSallyport() || info.getCanCrossSallyport() || info.getCanScaleAtCenter()
                    || info.getCanScaleOnLeft() || info.getCanScaleOnRight() || info.getCanScoreInHighGoal() || info.getCanScoreInLowGoal() || info.getDoesExtendBeyondTransportConfig()
                    || info.getPlaysDefense() || info.getApproxSpeedFeetPerSecond() != 0 || !info.getAutoCapabilitiesDescription().equals("") || info.getAverageHighGoalsPerMatch() != 0
                    || info.getAverageLowGoalsPerMatch() != 0 || info.getCycleTime() != 0 || !info.getDriveSystemDescription().equals("") || info.getScaleHeightPercent() != 0) {
                dbHelper.updateBenchmarking(info);
            }
            drivesFragment = null;
            acquisitionFragment = null;
            softwareFragment = null;
            scoringFragment = null;

            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
