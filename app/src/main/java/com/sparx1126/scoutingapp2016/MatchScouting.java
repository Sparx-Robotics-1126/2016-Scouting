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
import android.widget.Toast;

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
    private BlueAlliance blueAlliance;
    private DatabaseHelper dbHelper;
    private FragmentManager fm;
    private AutoFragment autoFragment;
    private GeneralFragment generalFragment;
    private TeleFragment teleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blueAlliance = BlueAlliance.getInstance(this);
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


        // set title of activity to the team number
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        toolbar.setTitle("Scouting for " + i.getStringExtra(MainMenu.ALLIANCE_SELECTED));

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
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

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
            scout.setAuto(autoFragment.getScoutingAuto());
            scout.setTele(teleFragment.getScoutingTele());
            scout.setGeneral(generalFragment.getScoutingGeneral());
            dbHelper.updateScouting(scout);
            teleFragment = null;
            generalFragment = null;
            autoFragment = null;
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}



