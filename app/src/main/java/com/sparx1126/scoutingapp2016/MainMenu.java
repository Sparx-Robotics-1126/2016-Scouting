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
import android.widget.EditText;
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

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner eventPicker;
    private Spinner matchPicker;
    private Spinner alliancePicker;
    private Spinner teamPicker;
    private LinearLayout matchScout;
    private LinearLayout teamScout;
    private EditText name;
    private SimpleCursorAdapter cursorAdapterRegionalNames;
    private SimpleCursorAdapter cursorAdapterMatches;
    private SimpleCursorAdapter cursorAdapterTeams;
    private Button scout;
    private BlueAlliance blueAlliance;
    private String nameOfScouter;
    private DatabaseHelper dbHelper;
    public static final String SCOUTING_INFO = "com.sparx1126.scouting2016.SCOUTING";
    public static final String ALLIANCE_SELECTED = "com.spark1126.scouting2016.ALLIANCE";

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
        scout = (Button) findViewById(R.id.begin_scouting);
        name = (EditText) findViewById(R.id.nameScouter);
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
        //TODO change to 2015 when ready to use
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

    /**
     * downloads the data for matches of an event
     */
    private void downloadMatchSpinnerData() {
        final Dialog alert = createDialog();
        alert.show();
        //get the event
        final Event e = getSelectedEvent();
        BlueAlliance ba = BlueAlliance.getInstance(this);
        //load matches for the event
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

    /**
     * get team data for this event
     */
    private void downloadTeamData() {
        final Dialog alert = createDialog();
        alert.show();
        //get the event
        final Event e = getSelectedEvent();
        BlueAlliance ba = BlueAlliance.getInstance(this);
        //load teams for the event
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

    /**
     * creates the event spinner with values supplied by BlueAlliance
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
        //bind view to this in order to get its value later
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
        //check if this is selected
        eventPicker.setOnItemSelectedListener(this);
        //finally set the value
        eventPicker.setAdapter(cursorAdapterRegionalNames);
    }

    @Override
    /**
     * check for selected items in spinners
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Event current = getSelectedEvent();
        Match currentMatch = getSelectedMatch();
        switch (parent.getId()) {
            case R.id.eventPicker:
                if (current != null) {
                    try {
                        //set up matches for an event
                        blueAlliance.loadMatches(current, new NetworkCallback() {
                            @Override
                            public void handleFinishDownload(boolean success) {
                                if (!success) {
                                    Toast.makeText(MainMenu.this, "Did not successfully download match list!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        //set up teams for an event
                        blueAlliance.loadTeams(current, new NetworkCallback() {
                            @Override
                            public void handleFinishDownload(boolean success) {
                                if (!success)
                                    Toast.makeText(MainMenu.this, "Did not successfully download team list!", Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (Exception e) {
                        Log.println(1010, "error", "This shouldn't happen");
                    }


                    //TODO change these to "if necessary" -- still need to write them above
                    downloadMatchSpinnerData();
                    downloadTeamData();
                }
                break;
            //match spinner selected; set up alliance spinner
            case R.id.matchPicker:
                if (currentMatch != null)
                    try {
                        setupAllianceSpinner();
                    }
                    //error occurred; this shouldn't happen
                    catch (Exception e) {
                        System.out.print("Hi");
                    }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> a) {
    }

    /**
     * gets the current event in eventPicker
     *
     * @return the selected event
     */
    public Event getSelectedEvent() {
        //no value until event picker is validated
        Event current = null;

        if (eventPicker != null && eventPicker.getSelectedView() != null)

        {
            //get the event through the tag
            current = dbHelper.getEvent((String) eventPicker.getSelectedView().getTag());
        }

        return current;

    }

    /**
     * sets scouting type based on which radio button is selected
     *
     * @param view the view that was clicked
     */
    public void setScoutType(View view) {
        if (scout.getVisibility() != View.VISIBLE) {
            scout.setVisibility(View.VISIBLE);
        }
        // swap visibility of spinners depending on currently selected radio button
        switch (view.getId()) {
            case R.id.matchScouting:
                if (matchScout.getVisibility() != View.VISIBLE)
                    matchScout.setVisibility(View.VISIBLE);
                if (teamScout.getVisibility() != View.GONE)
                    teamScout.setVisibility(View.GONE);
                break;
            case R.id.benchmarking:
                if (teamScout.getVisibility() != View.VISIBLE)
                    teamScout.setVisibility(View.VISIBLE);
                if (matchScout.getVisibility() != View.GONE)
                    matchScout.setVisibility(View.GONE);
        }
    }

    /**
     * called when transitioning between main menu and submenus (scouting / data view)
     *
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

    /**
     * get currently selected team from match scouting
     *
     * @return the team currently selected in alliancePicker
     */
    private Team getSelectedAllianceTeam() {
        return dbHelper.getTeam(getTeamKey(alliancePicker.getSelectedItemPosition()));
    }


    /**
     * gets the team currently selected in teamPicker
     *
     * @return the currently selected team in teamPicker
     */
    public Team getSelectedTeam() {
        //make sure teamPicker is valid before initializing current team
        Team team = null;
        if (teamPicker != null && teamPicker.getSelectedView() != null) {
            //set team to currently selected value in teamPicker, based on tag
            team = dbHelper.getTeam((String) teamPicker.getSelectedView().getTag());
        }
        return team;
    }

    /**
     * gets team key in an alliance based on position in BlueAlliance data
     *
     * @param i the position of the team
     * @return the team's key
     */
    private String getTeamKey(int i) {
        Match m = dbHelper.getMatch(getSelectedMatch().getKey());
        Alliances a = dbHelper.getMatch(getSelectedMatch().getKey()).getAlliances();
        String team;
        if (i < 3)
            team = a.getBlue().getTeams().get(i);
        else
            team = a.getRed().getTeams().get(i - 3);
        return team;
    }

    private String getName(){
        return name.getText().toString();
    }

    /**
     * give matchPicker its values after downloading them, based on an event
     *
     * @param event the event to get matches for; event != null
     */
    public void setupMatchSpinner(Event event) {

        dbHelper = DatabaseHelper.getInstance(this);

        matchPicker = (Spinner) findViewById(R.id.matchPicker);
        cursorAdapterMatches = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                dbHelper.createMatchCursor(event),
                new String[]{"key"},
                new int[]{android.R.id.text1},
                0);
        //bind the cursor to this view to use tag later
        cursorAdapterMatches.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                //create a string to display based on match data
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
                //set the tag to the match key
                view.setTag(cursor.getString(cursor.getColumnIndex("key")));

                return true;
            }
        });
        //check for onClick events
        matchPicker.setOnItemSelectedListener(this);
        //set adapter to cursor in order to display data
        matchPicker.setAdapter(cursorAdapterMatches);
    }

    /**
     * give alliancePicker its values
     */
    public void setupAllianceSpinner() {
        //number of teams in both alliances; I don't really feel like making this a constant so yeah
        int numTeams = 6;
        alliancePicker = (Spinner) findViewById(R.id.alliancePicker);
        /**alliance color - red/blue
         */
        String color;
        ArrayList<String> teamList = new ArrayList<String>();
        //make sure the list has at least 6 spaces
        teamList.ensureCapacity(numTeams);
        for (int i = 0; i < numTeams; i++) {
            if (i < 3)
                //set color to alliance color
                color = "Blue";
            else color = "Red";
            teamList.add(color + " Alliance " + (i + 1) + " (" + getTeamKey(i) + ")");
        }
        //new adapter to store string values
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                teamList);
        // set alliancePicker's adapter to the string adapter that was just created
        alliancePicker.setAdapter(adapter);

    }

    /**
     * coverts a new scouting object to a JSON string to pass to subactivities that need it
     *
     * @return a new String that contains data to create a scouting object in subactivities
     */
    private String convertScouting() {
        Scouting scouting = new Scouting();
        //get a match up here; don't really want to keep calling getSelectedMatch()
        Match match = getSelectedMatch();
        //this shouldn't happen, but just in case
        if (getSelectedEvent() != null) {
            scouting.setEventKey(getSelectedEvent().getKey());

            //benchmarking selected, so only need team
            if ((getSelectedTeam() != null && ((RadioButton) (findViewById(R.id.benchmarking)))
                    .isChecked())) {
                scouting.setTeamKey(getSelectedTeam().getKey());

            }
            //match scouting selected, so need match data and alliance team data
            else if (match != null && ((RadioButton) findViewById(R.id.matchScouting))
                    .isChecked()) {
                scouting.setMatchKey(getSelectedMatch().getKey());
                scouting.setTeamKey(getSelectedAllianceTeam().getKey());
            }
            if(getName().isEmpty())
                Toast.makeText(this, "Please enter your name.", Toast.LENGTH_LONG).show();
            else {
                scouting.setNameOfScouter(getName());

                //convert the new object to a JSON string
                Gson gson = new GsonBuilder().create();
                return gson.toJson(scouting);
            }
        }// somehow it failed
        return null;
    }

    /**
     * put team data from BlueAlliance into teamPicker -- benchmarking
     *
     * @param e the event to get teams for
     */
    private void setupTeamSpinner(Event e) {
        dbHelper = dbHelper.getInstance(this);
        teamPicker = (Spinner) findViewById(R.id.teamPicker);
        cursorAdapterTeams = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                dbHelper.createTeamCursor(e),
                new String[]{"team_number", "nickname"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        cursorAdapterTeams.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                String teamString = cursor.getString(cursor.getColumnIndex("team_number")) + " (" + cursor.getString(cursor.getColumnIndex("nickname")) + ")";

                ((TextView) view).setText(teamString);
                view.setTag(cursor.getString(cursor.getColumnIndex("team")));
                return true;
            }
        });
        teamPicker.setAdapter(cursorAdapterTeams);
    }

    /**
     * start subactivities if there is enough valid data depending on which type is selected
     *
     * @param view the view to call this on -- should only be called on begin scouting button
     */
    public void beginScouting(View view) {
        Intent i = new Intent(this, MatchScouting.class);
        if (convertScouting() != null && alliancePicker != null) {
            i.putExtra(SCOUTING_INFO, convertScouting());
            i.putExtra(ALLIANCE_SELECTED, (String) alliancePicker.getSelectedItem());
            startActivity(i);
        }
        else if(((RadioButton)findViewById(R.id.benchmarking)).isChecked()) Toast.makeText(this, "Benchmarking isn't ready yet!", Toast.LENGTH_LONG).show();
        else Toast.makeText(this, "Select a match and team to scout first!", Toast.LENGTH_LONG).show();


    }

 }
