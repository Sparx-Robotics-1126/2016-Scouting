package com.sparx1126.scoutingapp2016;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparx1126.scoutingapp2016.fragments.MatchScouting.AutoFragment;
import com.sparx1126.scoutingapp2016.fragments.MatchScouting.GeneralFragment;
import com.sparx1126.scoutingapp2016.fragments.MatchScouting.TeleFragment;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;

public class MatchScouting extends FragmentActivity implements GeneralFragment.OnFragmentInteractionListener, AutoFragment.OnFragmentInteractionListener, TeleFragment.OnFragmentInteractionListener{


    private Scouting scout;
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
        String scoutInfo = i.getStringExtra(MainMenu.SCOUTING_INFO);
        Gson gson = new GsonBuilder().create();
        scout = gson.fromJson(scoutInfo, Scouting.class);
        fm = getFragmentManager();
        autoFragment = AutoFragment.newInstance(scout.getAuto());
        teleFragment = TeleFragment.newInstance(scout.getTele());
        generalFragment = GeneralFragment.newInstance(scout.getGeneral());
        fm.beginTransaction().add(R.id.fragContainer, autoFragment).add(R.id.fragContainer, teleFragment).add(R.id.fragContainer, generalFragment).commit();


        // set title of activity to the team number

        this.setTitle("Scouting for: " + i.getStringExtra(MainMenu.ALLIANCE_SELECTED));
    }
    @Override
    public void onFragmentInteraction(Uri uri){{
    }
    }
    public void switchFragment(View view){
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        switch(view.getId()){
            case R.id.auto:
                ft.replace(R.id.fragContainer, autoFragment, "auto");
                ft.show(autoFragment);

                break;
            case R.id.tele:
                ft.replace(R.id.fragContainer, teleFragment, "tele");
                ft.show(teleFragment);
                break;
            case R.id.general:
                ft.replace(R.id.fragContainer, generalFragment, "general");
                ft.show(generalFragment);
                break;
            default:
        }
        ft.commit();
    }
}



