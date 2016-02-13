package com.sparx1126.scoutingapp2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
import org.gosparx.scouting.aerialassist.dto.Team;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;

import java.util.List;

public class ViewData extends AppCompatActivity {
    private BlueAlliance ba;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i = getIntent();
        String teamKey = i.getStringExtra(MainMenu.TEAM_NAME);
        String eventKey = i.getStringExtra(MainMenu.EVENT_KEY);
        toolbar.setTitle("Viewing data for: " + teamKey);
        dbHelper = DatabaseHelper.getInstance(this);
        List<Scouting> scoutList= dbHelper.getScouting(eventKey, teamKey);
        
    }

}
