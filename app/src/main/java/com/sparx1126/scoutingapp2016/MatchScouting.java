package com.sparx1126.scoutingapp2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.gosparx.scouting.aerialassist.dto.Match;

import java.lang.reflect.Type;

public class MatchScouting extends AppCompatActivity {


    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_team_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String matchInfo = i.getStringExtra(MainMenu.MATCH_INFO);
        Gson gson = new GsonBuilder().create();
        match = gson.fromJson(matchInfo, Match.class);
        Toast.makeText(this, match.toString(), Toast.LENGTH_SHORT).show();
    };
}



