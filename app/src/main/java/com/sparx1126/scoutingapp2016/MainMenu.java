package com.sparx1126.scoutingapp2016;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Alliances;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.Team;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.NetworkHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner eventPicker;
    private Spinner matchPicker;
    private Spinner alliancePicker;
    private Spinner teamPicker;
    private LinearLayout matchScout;
    private LinearLayout teamScout;
    private SimpleCursorAdapter cursorAdapterRegionalNames;
    private SimpleCursorAdapter cursorAdapterMatches;
    private SimpleCursorAdapter cursorAdapterTeams;
    private Button scout;
    private BlueAlliance blueAlliance;

    private DatabaseHelper dbHelper;

    public static final String MATCH_INFO = "com.sparx1126.scouting2016.MATCH";
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        downloadEventSpinnerDataIfNecessary();
        blueAlliance = BlueAlliance.getInstance(this);
        matchScout = (LinearLayout) findViewById(R.id.matchScoutLayout);
        matchScout.setVisibility(View.GONE);
        teamScout = (LinearLayout) findViewById(R.id.teamScoutLayout);
        teamScout.setVisibility(View.GONE);
        scout = (Button)findViewById(R.id.begin_scouting);
        scout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                beginScouting(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
            return true;
        } else if (id == R.id.action_download) {
            downloadEventSpinnerData();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * notify the user about data download
     *
     * @return message about download
     */
    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.downloading_data);
        builder.setMessage(R.string.please_wait_while_data_downloads);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(MainMenu.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    /**
     * check if data needs to be downloaded
     */
    private void downloadEventSpinnerDataIfNecessary() {
        if (isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {
            downloadEventSpinnerData();
        } else {
            setupEventSpinner();
        }
    }

    /**
     * get data to populate spinner
     */
    private void downloadEventSpinnerData() {
        final Dialog alert = createDialog();
        alert.show();
        blueAlliance = BlueAlliance.getInstance(this);
        blueAlliance.loadEventList(2015, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainMenu.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!success)
                            Toast.makeText(MainMenu.this, "Did not successfully download event list!", Toast.LENGTH_LONG).show();
                        alert.dismiss();
                        setupEventSpinner();
//                            mNavigationDrawerFragment.updateDrawerData();
                    }
                });
            }
        });
    }

    private void downloadMatchSpinnerData() {
        final Dialog alert = createDialog();
        alert.show();
        final Event e = getSelectedEvent();
        BlueAlliance ba = BlueAlliance.getInstance(this);
        ba.loadMatches(e, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainMenu.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!success)
                            Toast.makeText(MainMenu.this, "Did not successfully download match list!", Toast.LENGTH_SHORT).show();
                        alert.dismiss();

                        setupMatchSpinner(e);
                    }
                });
            }
        });
    }
    private void downloadTeamData(){
        final Dialog alert = createDialog();
        alert.show();
        final Event e = getSelectedEvent();
        BlueAlliance ba = BlueAlliance.getInstance(this);
        ba.loadTeams(e, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainMenu.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!success)
                            Toast.makeText(MainMenu.this, "Did not successfully download team data!", Toast.LENGTH_LONG).show();
                        alert.dismiss();

                        setupTeamSpinner(e);
                    }

                });
            }
        });
    }

/*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

    /**
     * creates the spinner with values supplied by BlueAlliance
     */
    public void setupEventSpinner() {
        dbHelper = DatabaseHelper.getInstance(this);
        eventPicker = (Spinner) findViewById(R.id.eventPicker);
        cursorAdapterRegionalNames = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                dbHelper.createEventNameCursor(),
                new String[]{"title"},
                new int[]{android.R.id.text1}, 0);
        cursorAdapterRegionalNames.setViewBinder(
                new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int i) {
                        view.setTag(cursor.getString(cursor.getColumnIndex("key")));
                        if (view instanceof TextView) {
                            ((TextView) view).setText(cursor.getString(i));
                        }
                        return true;
                    }
                }

        );
        eventPicker.setOnItemSelectedListener(this);

        eventPicker.setAdapter(cursorAdapterRegionalNames);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Event current = getSelectedEvent();
        Match currentMatch = getSelectedMatch();
        switch (parent.getId()) {
            case R.id.eventPicker:
                if (current != null) {
                    try {

                        blueAlliance.loadMatches(current, new NetworkCallback() {
                            @Override
                            public void handleFinishDownload(boolean success) {
                                if(!success) {
                                    Toast.makeText(MainMenu.this, "Did not successfully download match list!", Toast.LENGTH_LONG).show();
                                }
                                    }
                        });

                        blueAlliance.loadTeams(current, new NetworkCallback() {
                            @Override
                            public void handleFinishDownload(boolean success) {
                                if(!success)
                                    Toast.makeText(MainMenu.this, "Did not successfully download team list!", Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (Exception e) {
                        Log.println(1010, "error", "This shouldn't happen");
                    }

                }
                downloadMatchSpinnerData();
                downloadTeamData();
                break;

            case R.id.matchPicker:
                if(currentMatch != null)
                    try{
                        setupAllianceSpinner();
                    }
                    catch(Exception e){System.out.print("Hi");}

                }
    }

    @Override
    public void onNothingSelected(AdapterView<?> a) {
    }

    public Event getSelectedEvent() {
        Event current = null;

        if (eventPicker != null && eventPicker.getSelectedView() != null)

        {
            current = dbHelper.getEvent((String) eventPicker.getSelectedView().getTag());
        }

        return current;

    }
    public void setScoutType(View view){
        if(scout.getVisibility() != View.VISIBLE ){
            scout.setVisibility(View.VISIBLE);
        }
        switch(view.getId()){
            case R.id.matchScouting:
                if(matchScout.getVisibility() != View.VISIBLE)
                    matchScout.setVisibility(View.VISIBLE);
                if(teamScout.getVisibility() != View.GONE)
                    teamScout.setVisibility(View.GONE);
                break;
            case R.id.benchmarking:
                if(teamScout.getVisibility() != View.VISIBLE)
                    teamScout.setVisibility(View.VISIBLE);
                if(matchScout.getVisibility() != View.GONE)
                    matchScout.setVisibility(View.GONE);
        }
    }
    /**
     * called when transitioning between main menu and submenus (scouting / data view)
     * @return the currently selected match
     */
    public Match getSelectedMatch() {
        Match match = null;
        if (matchPicker != null && matchPicker.getSelectedView() != null) {
            Object o = matchPicker.getSelectedView();
            match = dbHelper.getMatch((String) matchPicker.getSelectedView().getTag());
        }
        return match;
    }

    private Team getSelectedAllianceTeam(){
        return (Team)(alliancePicker.getSelectedItem());
    }


    public Team getSelectedTeam() {
        Team team = null;
        if (teamPicker != null && teamPicker.getSelectedView() != null) {
            Object o = teamPicker.getSelectedView();
            String test = (String)teamPicker.getSelectedView().getTag();
            team = dbHelper.getTeam(test);
        }
        return team;
    }
    private String getTeamKey(int i){
        Match m = dbHelper.getMatch(getSelectedMatch().getKey());
        Alliances a = dbHelper.getMatch(getSelectedMatch().getKey()).getAlliances();
        String team;
        if(i < 3)
            team =  a.getBlue().getTeams().get(i);
            team= a.getRed().getTeams().get(i-3);
        return team;
    }
    public void setupMatchSpinner(Event event) {

        dbHelper = DatabaseHelper.getInstance(this);

        matchPicker = (Spinner) findViewById(R.id.matchPicker);
        cursorAdapterMatches = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item ,
                dbHelper.createMatchCursor(event),
                new String[]{"key"},
                new int[]{android.R.id.text1},
                0);
        cursorAdapterMatches.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                StringBuilder matchString = new StringBuilder();
                String compLevel = cursor.getString(cursor.getColumnIndex("comp_level"));
                int setNumber = cursor.getInt(cursor.getColumnIndex("set_number"));
                if ("qm".equals(compLevel))
                    matchString.append("Qual ");
                else if ("qf".equals(compLevel)) {
                    matchString.append("Q/F: ");
                    matchString.append(setNumber);
                } else if ("sf".equals(compLevel)) {
                    matchString.append("S/F: ");
                    matchString.append(setNumber);
                } else if ("f".equals(compLevel)) {
                    matchString.append("Final: ");
                    matchString.append(setNumber);
                }
                matchString.append(" Match: ").append(cursor.getInt(cursor.getColumnIndex("match_number")));

                ((TextView) view).setText(matchString.toString());
                int keyColIndex = cursor.getColumnIndex("key");
                String key = cursor.getString(keyColIndex);
                view.setTag(key);

                return true;
            }
        });
        matchPicker.setOnItemSelectedListener(this);
        matchPicker.setAdapter(cursorAdapterMatches);
    }

    public void setupAllianceSpinner(){
        int numTeams = 6;
        alliancePicker = (Spinner)findViewById(R.id.alliancePicker);
        String color;
        ArrayList<String> teamList = new ArrayList<String>();
        teamList.ensureCapacity(6);
        for(int i=0; i < numTeams; i++){
            if(i < 3)
                color = "Blue";
            else color = "Red";
            teamList.add(color + " Alliance " + (i+1) + " (" + getTeamKey(i) + ")");
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                teamList);
        alliancePicker.setAdapter(adapter);

    }

    private String convertScouting() {
        Scouting scouting = new Scouting();
        if (getSelectedEvent() != null) {
            scouting.setEventKey(getSelectedEvent().getKey());
            if ((getSelectedTeam() != null && ((RadioButton) (findViewById(R.id.benchmarking)))
                    .isSelected())) {
                scouting.setTeamKey(getSelectedTeam().getKey());

            } else if (getSelectedMatch() != null && ((RadioButton) findViewById(R.id.matchScouting))
                    .isSelected()) {
                scouting.setMatchKey(getSelectedMatch().getKey());
                scouting.setTeamKey(getSelectedAllianceTeam().getKey());
            }
            Gson gson = new GsonBuilder().create();
            return gson.toJson(scouting);
        }
        return null;
    }

    private void setupTeamSpinner(Event e){
        dbHelper = dbHelper.getInstance(this);
        teamPicker = (Spinner) findViewById(R.id.teamPicker);
        cursorAdapterTeams = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                dbHelper.createTeamCursor(e),
                new String[]{"team_number", "nickname", "key"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        cursorAdapterTeams.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                String teamString = cursor.getString(cursor.getColumnIndex("team_number")) + " (" + cursor.getString(cursor.getColumnIndex("nickname")) + ")";
                ((TextView) view).setText(teamString);

                int keyColIndex = cursor.getColumnIndex("team_key");
                String key = cursor.getString(keyColIndex);
                view.setTag(key);
                return true;
            }
        });
        teamPicker.setAdapter(cursorAdapterTeams);
    }
    public void beginScouting(View view){
        Intent i = new Intent(this, MatchScouting.class);
        if(convertScouting() != null) {
            i.putExtra(MATCH_INFO, convertScouting());
            startActivity(i);
        }


    }

}
