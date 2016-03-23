package com.sparx1126.scoutingapp2016;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.fragments.MatchScouting.AutoFragment;
import com.sparx1126.scoutingapp2016.fragments.MatchScouting.GeneralFragment;
import com.sparx1126.scoutingapp2016.fragments.MatchScouting.TeleFragment;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingAuto;
import org.gosparx.scouting.aerialassist.dto.ScoutingGeneral;
import org.gosparx.scouting.aerialassist.dto.ScoutingTele;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;

import java.util.List;

public class MatchScouting extends FragmentActivity implements GeneralFragment.OnFragmentInteractionListener,
        AutoFragment.OnFragmentInteractionListener, TeleFragment.OnFragmentInteractionListener {


    public static Scouting scout;
    private DatabaseHelper dbHelper;
    private FragmentManager fm;
    private AutoFragment autoFragment;
    private GeneralFragment generalFragment;
    private TeleFragment teleFragment;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = DatabaseHelper.getInstance(this);
        setContentView(R.layout.activity_match_scouting);
        Intent i = getIntent();
        fm = getFragmentManager();
        if (dbHelper.doesScoutingExist(scout)) {

            List<Scouting> scoutList = dbHelper.getScouting(scout.getEventKey(), scout.getTeamKey(), scout.getMatchKey(),
                    scout.getNameOfScouter());
            scout = scoutList.get(0);
        } else dbHelper.createScouting(scout);
        //set up fragments
        if (scout.getAuto() == null)
            scout.setAuto(new ScoutingAuto());

        autoFragment = AutoFragment.newInstance(scout.getAuto());

        if (scout.getTele() == null)
            scout.setTele(new ScoutingTele());

        teleFragment = TeleFragment.newInstance(scout.getTele());

        if (scout.getGeneral() == null)
            scout.setGeneral(new ScoutingGeneral());

        generalFragment = GeneralFragment.newInstance(scout.getGeneral());

        color = i.getStringExtra(MainMenu.ALLIANCE_COLOR);
        // set title of activity to the team number
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        if (color.equals("Blue")) {
            toolbar.setBackgroundColor(Color.BLUE);
        } else toolbar.setBackgroundColor(Color.RED);
        toolbar.setTitle("Scouting for " + i.getStringExtra(MainMenu.ALLIANCE_SELECTED));


        // auto-select the Auto Fragment
        switchFragment(findViewById(R.id.auto));
    }

    //necessary for interfaces
    @Override
    public void onFragmentInteraction(Uri uri) {
        {
        }
    }

    /**
     * hides all fragments except the one indicated by the button clicked, then shows that fragment
     *
     * @param view the object that was clicked
     */
    public void switchFragment(View view) {
        view.setKeepScreenOn(true);
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        HighlightButton(view.getId());
        switch (view.getId()) {
            case R.id.auto:
                if (generalFragment.isAdded()) {
                    ft.hide(generalFragment);
                }
                if (teleFragment.isAdded()) {
                    ft.hide(teleFragment);
                }
                if (!autoFragment.isAdded())
                    ft.add(R.id.fragContainer, autoFragment);
                ft.show(autoFragment);

                break;
            case R.id.tele:
                if (autoFragment.isAdded()) {
                    ft.hide(autoFragment);
                }
                if (generalFragment.isAdded()) {
                    ft.hide(generalFragment);
                }

                if (!teleFragment.isAdded())
                    ft.add(R.id.fragContainer, teleFragment);
                ft.show(teleFragment);
                break;
            case R.id.general:
                if (autoFragment.isAdded()) {
                    ft.hide(autoFragment);
                }
                if (teleFragment.isAdded()) {
                    ft.hide(teleFragment);
                }

                if (!generalFragment.isAdded())
                    ft.add(R.id.fragContainer, generalFragment);
                ft.show(generalFragment);
                break;
            default:
        }
        ft.commit();
    }

    private void HighlightButton(int buttonId)
    {
        Button auto = (Button)findViewById(R.id.auto);
        Button general = (Button)findViewById(R.id.general);
        Button tele = (Button)findViewById(R.id.tele);
        auto.setTextColor(buttonId == R.id.auto ? 0xffff0000 : 0xff000000);
        general.setTextColor(buttonId == R.id.general ? 0xffff0000 : 0xff000000);
        tele.setTextColor(buttonId == R.id.tele ? 0xffff0000 : 0xff000000);
    }

    /**
     * finishes this activity
     * @param keyCode the keycode of a key
     * @param event this event
     * @return super's implementation of onKeyDown
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
            scout.setMatchScouted(((ToggleButton) (findViewById(R.id.matchScouted))).isChecked());
            ScoutingAuto auto = autoFragment.getScoutingAuto();
            scout.setAuto(auto);
            ScoutingTele tele = teleFragment.getScoutingTele();
            scout.setTele(tele);
            ScoutingGeneral general = generalFragment.getScoutingGeneral();
            scout.setGeneral(general);
            if (auto.getBoulderPickedUp() || auto.getChevalCrossed() || auto.getDrawbridgeCrossed()
                    || auto.getLowbarCrossed() || auto.getMoatCrossed() || auto.getPortcullisCrossed()
                    || auto.getRampartsCrossed() || auto.getReachAchieved() || auto.getReachWasCrossAttempt()
                    || auto.getRobotScoredHigh() || auto.getRobotScoredLow() || auto.getRockwallCrossed()
                    || auto.getRoughterrainCrossed() || auto.getSallyportCrossed() || auto.getStartedAsSpy()
                    || auto.getStartedWithBoulder() || auto.getChevalPosition() != 0 || auto.getDrawbridgePosition() != 0
                    || auto.getMoatPosition() != 0 || auto.getPortcullisPosition() != 0 || auto.getRampartsPosition() != 0
                    || auto.getRockwallPosition() != 0 || auto.getRoughterrainPosition() != 0
                    || auto.getSallyportPosition() != 0 || tele.getPlaysDefense() || tele.getLowGoalAttempts() != 0
                    || tele.getHighGoalAttempts() != 0 || tele.getLowGoalsScored() != 0 || tele.getHighGoalsScored() != 0
                    || tele.getBouldersPickedUp() != 0 || tele.getBouldersReceivedFromBrattice() != 0
                    || tele.getBouldersTakenToCourtyard() != 0 || tele.getChevalCrosses() != 0 || tele.getDrawbridgeCrosses() != 0
                    || tele.getLowbarCrosses() != 0 || tele.getMoatCrosses() != 0 || tele.getPortcullisCrosses() != 0
                    || tele.getRampartsCrosses() != 0 || tele.getRockwallCrosses() != 0 || tele.getRoughterrainCrosses() != 0
                    || tele.getSallyportCrosses() != 0 || (general.getCommentsOnPenalties() != null
                    && !general.getCommentsOnPenalties().trim().equals(""))
                    || (general.getCommentsOnTechnicalFouls() != null && !general.getCommentsOnTechnicalFouls().trim().equals(""))
                    || (general.getGeneralComments() != null && !general.getGeneralComments().trim().equals(""))
                    || general.getNumberOfPenalties() != 0 || general.getNumberOfTechnicalFouls() != 0) {

                dbHelper.updateScouting(scout);
            }
            teleFragment = null;
            generalFragment = null;
            autoFragment = null;
            scout = null;
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}



