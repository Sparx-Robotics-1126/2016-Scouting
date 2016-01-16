package com.sparx1126.scoutingapp2016;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.networking.*;

import static android.app.PendingIntent.getActivity;
import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private SimpleCursorAdapter cursorAdapterRegionalNames;
    private SimpleCursorAdapter cursorAdapterMatches;
    private SimpleCursorAdapter cursorAdapterTeams;
    private BlueAlliance blueAlliance;
    private DatabaseHelper dbHelper;
    Spinner eventPicker;
    Spinner matchPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        downloadEventSpinnerDataIfNecessary();
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
        }
        else if (id == R.id.action_download)
        {
            downloadEventSpinnerData();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * notify the user about data download
     * @return message about download
     */
    private AlertDialog createDialog(){
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
    private void downloadEventSpinnerDataIfNecessary()
    {
        if(isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {
            downloadEventSpinnerData();
        }
        else {
            setupEventSpinner();
        }
    }

    /**
     * get data to populate spinner
     */
    private void downloadEventSpinnerData()
    {
        final Dialog alert = createDialog();
        alert.show();
        blueAlliance = BlueAlliance.getInstance(this);
        blueAlliance.loadEventList(2016, new NetworkCallback() {
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
    private void downloadMatchSpinnerData(){
        final Dialog alert = createDialog();
        alert.show();
        BlueAlliance ba =  BlueAlliance.getInstance(this);
        ba.loadEventList(2016, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainMenu.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!success)
                            Toast.makeText(MainMenu.this, "Did not successfully download match list!", Toast.LENGTH_LONG).show();
                        alert.dismiss();

                        setupMatchSpinner(getSelectedEvent());
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
    public void setupEventSpinner()
    {
        dbHelper = DatabaseHelper.getInstance(this);
        Spinner eventPicker = (Spinner)findViewById(R.id.eventPicker);
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
                        if(view instanceof TextView){
                            ((TextView)view).setText(cursor.getString(i));
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
        switch (parent.getId()) {
            case R.id.eventPicker:
                if (current != null) {
                    blueAlliance.loadMatches(current, new NetworkCallback() {
                        @Override
                        public void handleFinishDownload(boolean success) {

                        }
                    });
                    System.out.println(">FKHE:KFH");
                }
                setupMatchSpinner(current);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> a){};

        public Event getSelectedEvent(){
            Event current = null;
            if(eventPicker != null && eventPicker.getSelectedView() != null)
                current= dbHelper.getEvent((String) eventPicker.getSelectedView().getTag());
            return current;
        }
    public void setupMatchSpinner(Event event){
        dbHelper = DatabaseHelper.getInstance(this);
        Spinner matchPicker = (Spinner)findViewById(R.id.matchPicker);


        matchPicker.setAdapter(cursorAdapterMatches);
    }
}
