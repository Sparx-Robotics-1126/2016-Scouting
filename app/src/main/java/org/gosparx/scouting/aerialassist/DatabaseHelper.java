package org.gosparx.scouting.aerialassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.gosparx.scouting.aerialassist.dto.Alliance;
import org.gosparx.scouting.aerialassist.dto.Alliances;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingAuto;
import org.gosparx.scouting.aerialassist.dto.ScoutingGeneral;
import org.gosparx.scouting.aerialassist.dto.ScoutingTele;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jbass on 3/1/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "RecycleRush";

    // Table Names
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_E2T = "events_to_teams";
    private static final String TABLE_MATCHES = "matches";
    private static final String TABLE_SCOUTING = "scouting";

    // Events Column Names
    private static final String TABLE_EVENTS_KEY = "key";
    private static final String TABLE_EVENTS_NAME = "name";
    private static final String TABLE_EVENTS_SHORT_NAME = "short_name";
    private static final String TABLE_EVENTS_EVENT_CODE = "event_code";
    private static final String TABLE_EVENTS_EVENT_TYPE_STRING = "event_type_string";
    private static final String TABLE_EVENTS_EVENT_TYPE = "event_type";
    private static final String TABLE_EVENTS_YEAR = "year";
    private static final String TABLE_EVENTS_LOCATION = "location";
    private static final String TABLE_EVENTS_OFFICIAL = "official";
    private static final String TABLE_EVENTS_START_DATE = "start_date";
    private static final String TABLE_EVENTS_END_DATE = "end_date";

    // Teams Column Names
    private static final String TABLE_TEAMS_KEY = "key";
    private static final String TABLE_TEAMS_NAME = "name";
    private static final String TABLE_TEAMS_NICKNAME = "nickname";
    private static final String TABLE_TEAMS_TEAM_NUMBER = "team_number";
    private static final String TABLE_TEAMS_WEBSITE = "website";
    private static final String TABLE_TEAMS_CITY = "city";
    private static final String TABLE_TEAMS_STATE = "state";
    private static final String TABLE_TEAMS_COUNTRY = "county";
    private static final String TABLE_TEAMS_LOCATION = "location";

    // Events to Teams Column Names
    private static final String TABLE_E2T_KEY = "key";
    private static final String TABLE_E2T_EVENT = "event";
    private static final String TABLE_E2T_TEAM = "team";

    // Matches Column Names
    private static final String TABLE_MATCHES_KEY = "key";
    private static final String TABLE_MATCHES_EVENT_KEY = "event_key";
    private static final String TABLE_MATCHES_MATCH_NUMBER = "match_number";
    private static final String TABLE_MATCHES_SET_NUMBER = "set_number";
    private static final String TABLE_MATCHES_COMP_LEVEL = "comp_level";
    private static final String TABLE_MATCHES_BLUE_SCORE = "blue_score";
    private static final String TABLE_MATCHES_BLUE_ONE = "blue_one";
    private static final String TABLE_MATCHES_BLUE_TWO = "blue_two";
    private static final String TABLE_MATCHES_BLUE_THREE = "blue_three";
    private static final String TABLE_MATCHES_RED_SCORE = "red_score";
    private static final String TABLE_MATCHES_RED_ONE = "red_one";
    private static final String TABLE_MATCHES_RED_TWO = "red_two";
    private static final String TABLE_MATCHES_RED_THREE = "red_three";

    // Scouting Column Names
    private static final String TABLE_SCOUTING_KEY = "key";
    private static final String TABLE_SCOUTING_LAST_UPDATE = "last_updated";
    private static final String TABLE_SCOUTING_LAST_SYNC = "last_sync";
    private static final String TABLE_SCOUTING_TEAM_KEY = "team_key";
    private static final String TABLE_SCOUTING_EVENT_KEY = "event_key";
    private static final String TABLE_SCOUTING_MATCH_KEY = "match_key";
    private static final String TABLE_SCOUTING_NAME = "scouter_name";
    private static final String TABLE_SCOUTING_AUTO_STARTING_LOCATION_X = "auto_starting_location_x";
    private static final String TABLE_SCOUTING_AUTO_STARTING_LOCATION_Y = "auto_starting_location_y";
    private static final String TABLE_SCOUTING_AUTO_ROBOT_IN_ZONE = "auto_robot_in_zone";
    private static final String TABLE_SCOUTING_AUTO_STACKED_TOTE_SET = "auto_stacked_tote_set";
    private static final String TABLE_SCOUTING_AUTO_YELLOW_TOTES_TO_AUTO_ZONE = "auto_yellow_totes_to_auto_zone";
    private static final String TABLE_SCOUTING_AUTO_RC_MOVED_TO_AUTO_ZONE = "auto_rc_moved_to_auto_zone";
    private static final String TABLE_SCOUTING_AUTO_RC_TAKEN_FROM_STEP = "auto_rc_taken_from_step";
    private static final String TABLE_SCOUTING_AUTO_ENDING_LOCATION_X = "auto_ending_location_x";
    private static final String TABLE_SCOUTING_AUTO_ENDING_LOCATION_Y = "auto_ending_location_y";
    private static final String TABLE_SCOUTING_TELE_TOTES_STACKED_1 = "tele_totes_stacked_1";
    private static final String TABLE_SCOUTING_TELE_TOTES_STACKED_2 = "tele_totes_stacked_2";
    private static final String TABLE_SCOUTING_TELE_TOTES_STACKED_3 = "tele_totes_stacked_3";
    private static final String TABLE_SCOUTING_TELE_TOTES_STACKED_4 = "tele_totes_stacked_4";
    private static final String TABLE_SCOUTING_TELE_RC_STACKED_1 = "tele_rc_stacked_1";
    private static final String TABLE_SCOUTING_TELE_RC_STACKED_2 = "tele_rc_stacked_2";
    private static final String TABLE_SCOUTING_TELE_RC_STACKED_3 = "tele_rc_stacked_3";
    private static final String TABLE_SCOUTING_TELE_RC_STACKED_4 = "tele_rc_stacked_4";
    private static final String TABLE_SCOUTING_TELE_LITTER_1 = "tele_litter_1";
    private static final String TABLE_SCOUTING_TELE_LITTER_2 = "tele_litter_2";
    private static final String TABLE_SCOUTING_TELE_LITTER_3 = "tele_litter_3";
    private static final String TABLE_SCOUTING_TELE_LITTER_4 = "tele_litter_4";
    private static final String TABLE_SCOUTING_GENERAL_NUM_FOULS = "gen_fouls";
    private static final String TABLE_SCOUTING_GENERAL_FAILED_RC_ON_STACK = "gen_failed_rc_on_stack";
    private static final String TABLE_SCOUTING_GENERAL_RC_TAKEN_FROM_STEP = "gen_rc_taken_from_step";
    private static final String TABLE_SCOUTING_GENERAL_DEAD = "gen_dead";
    private static final String TABLE_SCOUTING_GENERAL_ROBOT_TIPPED_OVER = "gen_robot_tipped";
    private static final String TABLE_SCOUTING_GENERAL_STACKS_TIPPED_OVER = "gen_tipped_over";
    private static final String TABLE_SCOUTING_GENERAL_TOTES_FROM_HP = "gen_totes_from_hp";
    private static final String TABLE_SCOUTING_GENERAL_TOTES_ATTEMPTED_FROM_HP = "gen_totes_attempted_from_hp";
    private static final String TABLE_SCOUTING_GENERAL_TOTES_FROM_LANDFILL = "gen_totes_from_landfill";
    private static final String TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES = "comment_penalties";
    private static final String TABLE_SCOUTING_GENERAL_COMMENTS = "general_comments";

    // Create Tables
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + "("
            + TABLE_EVENTS_KEY + " TEXT PRIMARY KEY, "
            + TABLE_EVENTS_NAME + " TEXT, "
            + TABLE_EVENTS_SHORT_NAME + " TEXT, "
            + TABLE_EVENTS_EVENT_CODE + " TEXT, "
            + TABLE_EVENTS_EVENT_TYPE_STRING + " TEXT, "
            + TABLE_EVENTS_EVENT_TYPE + " INTEGER, "
            + TABLE_EVENTS_YEAR + " INTEGER, "
            + TABLE_EVENTS_LOCATION + " TEXT, "
            + TABLE_EVENTS_OFFICIAL + " INTEGER, "
            + TABLE_EVENTS_START_DATE + " TEXT, "
            + TABLE_EVENTS_END_DATE + " TEXT)";

    private static final String CREATE_TABLE_TEAMS = "CREATE TABLE " + TABLE_TEAMS + "("
            + TABLE_TEAMS_KEY + " TEXT PRIMARY KEY, "
            + TABLE_TEAMS_TEAM_NUMBER + " INTEGER, "
            + TABLE_TEAMS_NAME + " TEXT, "
            + TABLE_TEAMS_NICKNAME + " TEXT, "
            + TABLE_TEAMS_WEBSITE + " TEXT, "
            + TABLE_TEAMS_CITY + " TEXT, "
            + TABLE_TEAMS_STATE + " TEXT, "
            + TABLE_TEAMS_COUNTRY + " TEXT, "
            + TABLE_TEAMS_LOCATION + " TEXT)";

    private static final String CREATE_TABLE_E2T = "CREATE TABLE " + TABLE_E2T + "("
            + TABLE_E2T_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_E2T_EVENT + " TEXT, "
            + TABLE_E2T_TEAM + " TEXT)";

    private static final String CREATE_TABLE_MATCH = "CREATE TABLE " + TABLE_MATCHES + "("
            + TABLE_MATCHES_KEY + " TEXT PRIMARY KEY, "
            + TABLE_MATCHES_EVENT_KEY + " TEXT, "
            + TABLE_MATCHES_MATCH_NUMBER + " INTEGER, "
            + TABLE_MATCHES_SET_NUMBER + " INTEGER, "
            + TABLE_MATCHES_COMP_LEVEL + " TEXT, "
            + TABLE_MATCHES_RED_SCORE + " INTEGER, "
            + TABLE_MATCHES_RED_ONE + " TEXT, "
            + TABLE_MATCHES_RED_TWO + " TEXT, "
            + TABLE_MATCHES_RED_THREE + " TEXT, "
            + TABLE_MATCHES_BLUE_SCORE + " INTEGER, "
            + TABLE_MATCHES_BLUE_ONE + " TEXT, "
            + TABLE_MATCHES_BLUE_TWO + " TEXT, "
            + TABLE_MATCHES_BLUE_THREE + " TEXT)";

    private static final String CREATE_TABLE_SCOUTING = "CREATE TABLE " + TABLE_SCOUTING + "("
            + TABLE_SCOUTING_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_SCOUTING_LAST_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + TABLE_SCOUTING_LAST_SYNC + " DATETIME, "
            + TABLE_SCOUTING_TEAM_KEY + " TEXT, "
            + TABLE_SCOUTING_EVENT_KEY + " TEXT, "
            + TABLE_SCOUTING_MATCH_KEY + " TEXT, "
            + TABLE_SCOUTING_NAME + " TEXT, "
            + TABLE_SCOUTING_AUTO_STARTING_LOCATION_X + " INTEGER, "
            + TABLE_SCOUTING_AUTO_STARTING_LOCATION_Y + " INTEGER, "
            + TABLE_SCOUTING_AUTO_ROBOT_IN_ZONE + " INTEGER, "
            + TABLE_SCOUTING_AUTO_STACKED_TOTE_SET + " INTEGER, "
            + TABLE_SCOUTING_AUTO_YELLOW_TOTES_TO_AUTO_ZONE + " INTEGER, "
            + TABLE_SCOUTING_AUTO_RC_MOVED_TO_AUTO_ZONE + " INTEGER, "
            + TABLE_SCOUTING_AUTO_RC_TAKEN_FROM_STEP + " INTEGER, "
            + TABLE_SCOUTING_AUTO_ENDING_LOCATION_X + " INTEGER, "
            + TABLE_SCOUTING_AUTO_ENDING_LOCATION_Y + " INTEGER, "
            + TABLE_SCOUTING_TELE_TOTES_STACKED_1 + " INTEGER, "
            + TABLE_SCOUTING_TELE_TOTES_STACKED_2 + " INTEGER, "
            + TABLE_SCOUTING_TELE_TOTES_STACKED_3 + " INTEGER, "
            + TABLE_SCOUTING_TELE_TOTES_STACKED_4 + " INTEGER, "
            + TABLE_SCOUTING_TELE_RC_STACKED_1 + " INTEGER, "
            + TABLE_SCOUTING_TELE_RC_STACKED_2 + " INTEGER, "
            + TABLE_SCOUTING_TELE_RC_STACKED_3 + " INTEGER, "
            + TABLE_SCOUTING_TELE_RC_STACKED_4 + " INTEGER, "
            + TABLE_SCOUTING_TELE_LITTER_1 + " INTEGER, "
            + TABLE_SCOUTING_TELE_LITTER_2 + " INTEGER, "
            + TABLE_SCOUTING_TELE_LITTER_3 + " INTEGER, "
            + TABLE_SCOUTING_TELE_LITTER_4 + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_NUM_FOULS + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_STACKS_TIPPED_OVER + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_FAILED_RC_ON_STACK + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_RC_TAKEN_FROM_STEP + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_DEAD + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_ROBOT_TIPPED_OVER + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_TOTES_FROM_HP + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_TOTES_ATTEMPTED_FROM_HP + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_TOTES_FROM_LANDFILL + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES + " TEXT, "
            + TABLE_SCOUTING_GENERAL_COMMENTS + " TEXT)";

    public static SimpleDateFormat ISO6701_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getDateTime(){return ISO6701_FORMAT.format(new Date());}

    private static DatabaseHelper dbHelper;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(context);
        return dbHelper;
    }

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_E2T);
        db.execSQL(CREATE_TABLE_MATCH);
        db.execSQL(CREATE_TABLE_SCOUTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        throw new NoSuchMethodError();
    }

    private ContentValues mapEvent(Event event){
        ContentValues values = new ContentValues();
        values.put(TABLE_EVENTS_KEY, event.getKey());
        values.put(TABLE_EVENTS_NAME, event.getName());
        values.put(TABLE_EVENTS_SHORT_NAME, event.getShortName());
        values.put(TABLE_EVENTS_EVENT_CODE, event.getEventCode());
        values.put(TABLE_EVENTS_EVENT_TYPE_STRING, event.getEventTypeString());
        values.put(TABLE_EVENTS_EVENT_TYPE, event.getEventType());
        values.put(TABLE_EVENTS_YEAR, event.getYear());
        values.put(TABLE_EVENTS_LOCATION, event.getLocation());
        values.put(TABLE_EVENTS_OFFICIAL, event.isOfficial());
        values.put(TABLE_EVENTS_START_DATE, ISO6701_FORMAT.format(event.getStartDate()));
        values.put(TABLE_EVENTS_END_DATE, ISO6701_FORMAT.format(event.getEndDate()));

        return values;
    }

    public void createEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_EVENTS, null, mapEvent(event));
    }

    public boolean doesEventExist(Event event){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_EVENTS, new String[]{"COUNT(*)"},
                TABLE_EVENTS_KEY + " = ?", new String[]{event.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;
        if(c!=null)
        c.close();
        return retVal;
    }

    public void updateEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_EVENTS, mapEvent(event), TABLE_EVENTS_KEY + " = ?", new String[]{event.getKey()});
    }

    public Event getEvent(String eventKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS
                + " WHERE " + TABLE_EVENTS_KEY + " = ?";

        Cursor c = db.rawQuery(selectQuery, new String[]{eventKey});

        Event event = new Event();
        if(c != null && c.moveToNext()){
            event.setKey(c.getString(c.getColumnIndex(TABLE_EVENTS_KEY)));
            event.setName(c.getString(c.getColumnIndex(TABLE_EVENTS_NAME)));
            event.setShortName(c.getString(c.getColumnIndex(TABLE_EVENTS_SHORT_NAME)));
            event.setEventCode(c.getString(c.getColumnIndex(TABLE_EVENTS_EVENT_CODE)));
            event.setEventTypeString(c.getString(c.getColumnIndex(TABLE_EVENTS_EVENT_TYPE_STRING)));
            event.setEventType(c.getInt(c.getColumnIndex(TABLE_EVENTS_EVENT_TYPE)));
            event.setYear(c.getInt(c.getColumnIndex(TABLE_EVENTS_YEAR)));
            event.setLocation(c.getString(c.getColumnIndex(TABLE_EVENTS_LOCATION)));
            event.setOfficial(c.getInt(c.getColumnIndex(TABLE_EVENTS_OFFICIAL)) == 1);
            try{
                event.setStartDate(ISO6701_FORMAT.parse(c.getString(c.getColumnIndex(TABLE_EVENTS_START_DATE))));
            } catch (ParseException e) {
                Log.e(TAG, "Could not parse Event's start date.", e);
            }try{
                event.setEndDate(ISO6701_FORMAT.parse(c.getString(c.getColumnIndex(TABLE_EVENTS_END_DATE))));
            } catch (ParseException e) {
                Log.e(TAG, "Could not parse Event's end date.", e);
            }
        }
        if(c!=null) {
            c.close();
        }
        return event;
    }

    public Cursor createEventNameCursor(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(true, TABLE_EVENTS, new String[]{"rowid AS _id, key", "substr(start_date,1,11) || name AS title", TABLE_EVENTS_SHORT_NAME, TABLE_EVENTS_START_DATE},
                "", null, null, null, TABLE_EVENTS_START_DATE + " ASC", null);
    }

    private ContentValues mapTeam(Team team){
        ContentValues values = new ContentValues();
        values.put(TABLE_TEAMS_KEY, team.getKey());
        values.put(TABLE_TEAMS_NAME, team.getName());
        values.put(TABLE_TEAMS_NICKNAME, team.getNickname());
        values.put(TABLE_TEAMS_TEAM_NUMBER, team.getTeamNumber());
        values.put(TABLE_TEAMS_WEBSITE, team.getWebsite());
        values.put(TABLE_TEAMS_CITY, team.getCity());
        values.put(TABLE_TEAMS_STATE, team.getState());
        values.put(TABLE_TEAMS_COUNTRY, team.getCountry());
        values.put(TABLE_TEAMS_LOCATION, team.getLocation());
        return values;
    }

    public void createTeam(Team team){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_TEAMS, null, mapTeam(team));
    }

    public boolean doesTeamExist(Team team){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_TEAMS, new String[]{"COUNT(*)"},
                TABLE_TEAMS_KEY + " = ?", new String[]{team.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;
        if(c!=null) {
            c.close();
        }
        return retVal;
    }

    public void updateTeam(Team team){
        getWritableDatabase().update(TABLE_TEAMS, mapTeam(team), TABLE_TEAMS_KEY + " = ?", new String[]{team.getKey()});
    }

    public Team getTeam(String teamKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TEAMS + " WHERE " + TABLE_TEAMS_KEY + " = ?";

        Cursor c = db.rawQuery(selectQuery, new String[]{teamKey});

        Team team = new Team();
        if(c != null && c.moveToNext()){
            team.setKey(c.getString(c.getColumnIndex(TABLE_TEAMS_KEY)));
            team.setName(c.getString(c.getColumnIndex(TABLE_TEAMS_NAME)));
            team.setNickname(c.getString(c.getColumnIndex(TABLE_TEAMS_NICKNAME)));
            team.setTeamNumber(c.getInt(c.getColumnIndex(TABLE_TEAMS_TEAM_NUMBER)));
            team.setWebsite(c.getString(c.getColumnIndex(TABLE_TEAMS_WEBSITE)));
            team.setCity(c.getString(c.getColumnIndex(TABLE_TEAMS_CITY)));
            team.setCountry(c.getString(c.getColumnIndex(TABLE_TEAMS_COUNTRY)));
            team.setLocation(c.getString(c.getColumnIndex(TABLE_TEAMS_LOCATION)));
            team.setEvents(new ArrayList<Event>());
        }

        selectQuery = "SELECT * FROM " +TABLE_E2T + " WHERE " + TABLE_E2T_TEAM + " = ?";
        c = db.rawQuery(selectQuery, new String[]{team.getKey()});

        while(c != null && c.moveToNext()){
            team.getEvents().add(getEvent(c.getString(c.getColumnIndex(TABLE_E2T_EVENT))));
        }
            if (c != null) {
                c.close();
            }
        return team;
    }

    public Cursor createTeamCursor(Event event){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_TEAMS+","+TABLE_E2T,
                new String[]{"*", TABLE_TEAMS+".rowid AS _id"},
                TABLE_TEAMS+"."+TABLE_TEAMS_KEY+" = "+TABLE_E2T+"."+TABLE_E2T_TEAM
                +" AND "+TABLE_E2T+"."+TABLE_E2T_EVENT+" = ?",
                new String[]{event.getKey()},
                null, null, TABLE_TEAMS+"."+TABLE_TEAMS_TEAM_NUMBER+" ASC");

        return c;
    }

    public void createE2TAssociation(String eventKey, String teamKey){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_E2T_EVENT, eventKey);
        values.put(TABLE_E2T_TEAM, teamKey);

        db.insert(TABLE_E2T, null, values);
    }

    public boolean doesE2TAssociationExist(String eventKey, String teamKey){
        SQLiteDatabase db = getReadableDatabase();
        boolean retVal = false;

        Cursor c = db.query(TABLE_E2T, new String[]{"COUNT(*)"},
                TABLE_E2T_EVENT+" = ? AND "+TABLE_E2T_TEAM+" = ?",
                new String[]{eventKey, teamKey}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    private ContentValues mapMatch(Match match){
        ContentValues values = new ContentValues();
        values.put(TABLE_MATCHES_KEY, match.getKey());
        values.put(TABLE_MATCHES_EVENT_KEY, match.getEventKey());
        values.put(TABLE_MATCHES_MATCH_NUMBER, match.getMatchNumber());
        values.put(TABLE_MATCHES_SET_NUMBER, match.getSetNumber());
        values.put(TABLE_MATCHES_COMP_LEVEL, match.getCompetitionLevel());
        values.put(TABLE_MATCHES_BLUE_SCORE, match.getAlliances().getBlue().getScore());
        values.put(TABLE_MATCHES_BLUE_ONE, match.getAlliances().getBlue().getTeams().get(0));
        values.put(TABLE_MATCHES_BLUE_TWO, match.getAlliances().getBlue().getTeams().get(1));
        values.put(TABLE_MATCHES_BLUE_THREE, match.getAlliances().getBlue().getTeams().get(2));
        values.put(TABLE_MATCHES_RED_SCORE, match.getAlliances().getRed().getScore());
        values.put(TABLE_MATCHES_RED_ONE, match.getAlliances().getRed().getTeams().get(0));
        values.put(TABLE_MATCHES_RED_TWO, match.getAlliances().getRed().getTeams().get(1));
        values.put(TABLE_MATCHES_RED_THREE, match.getAlliances().getRed().getTeams().get(2));

        return values;
    }

    public void createMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = mapMatch(match);

        db.insert(TABLE_MATCHES, null, values);
    }

    public boolean doesMatchExist(Match match){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_MATCHES, new String[]{"COUNT(*)"},
                TABLE_MATCHES_KEY+" = ?", new String[]{match.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    public void updateMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_MATCHES, mapMatch(match), TABLE_MATCHES_KEY+" = ?", new String[]{match.getKey()});
    }

    public Match getMatch(String matchKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM " + TABLE_MATCHES
                + " WHERE " + TABLE_MATCHES_KEY + " = ?";

        Cursor c = db.rawQuery(selectStatement, new String[]{matchKey});

        Match match = new Match();

        if(c!=null && c.moveToNext()){
            match.setKey(c.getString(c.getColumnIndex(TABLE_MATCHES_KEY)));
            match.setEventKey(c.getString(c.getColumnIndex(TABLE_MATCHES_EVENT_KEY)));
            match.setMatchNumber(c.getInt(c.getColumnIndex(TABLE_MATCHES_MATCH_NUMBER)));
            match.setSetNumber(c.getInt(c.getColumnIndex(TABLE_MATCHES_SET_NUMBER)));
            match.setCompetitionLevel(c.getString(c.getColumnIndex(TABLE_MATCHES_COMP_LEVEL)));

            Alliances alliances = new Alliances();
            Alliance red = new Alliance();
            red.setTeams(new ArrayList<String>(3));
            Alliance blue = new Alliance();
            blue.setTeams(new ArrayList<String>(3));
            alliances.setBlue(blue);
            alliances.setRed(red);

            blue.setScore(c.getInt(c.getColumnIndex(TABLE_MATCHES_BLUE_SCORE)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_ONE)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_TWO)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_THREE)));

            red.setScore(c.getInt(c.getColumnIndex(TABLE_MATCHES_RED_SCORE)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_ONE)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_TWO)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_THREE)));
            match.setAlliances(alliances);
        }

        if (c != null) {
            c.close();
        }
        return match;
    }

    public Cursor createMatchCursor(Event event){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_MATCHES,
                new String[]{"*", "rowid As _id"},
                TABLE_MATCHES_EVENT_KEY + " = ?", new String[]{event.getKey()},
                null, null,
                "(CASE " + TABLE_MATCHES_COMP_LEVEL + " "
                        + "WHEN 'qm' THEN 1 "
                        + "WHEN 'qf' THEN 2 "
                        + "WHEN 'sf' THEN 3 "
                        + "WHEN 'f'  THEN 4 "
                        + "ELSE 5 END), "
                        + TABLE_MATCHES_SET_NUMBER +", "
                        + TABLE_MATCHES_MATCH_NUMBER + " ASC",
                null);
    }

    public int getMatchCount(Event event){
        int retVal = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MATCHES +" WHERE "+TABLE_MATCHES_EVENT_KEY +" = ?", new String[]{event.getKey()});

        if(c != null && c.moveToNext())
            retVal = c.getInt(0);

        if (c != null) {
            c.close();
        }

        return retVal;
    }

    private ContentValues mapScouting(Scouting scouting){
        ScoutingAuto scoutingAuto = scouting.getAuto();
        ScoutingTele scoutingTele = scouting.getTele();
        ScoutingGeneral scoutingGeneral = scouting.getGeneral();

        ContentValues values = new ContentValues();
        values.put(TABLE_SCOUTING_LAST_UPDATE, getDateTime());
        values.put(TABLE_SCOUTING_TEAM_KEY, scouting.getTeamKey());
        values.put(TABLE_SCOUTING_EVENT_KEY, scouting.getEventKey());
        values.put(TABLE_SCOUTING_MATCH_KEY, scouting.getMatchKey());
        values.put(TABLE_SCOUTING_NAME, scouting.getNameOfScouter());

        if (scoutingAuto != null) {
//            values.put(TABLE_SCOUTING_AUTO_STARTING_LOCATION_X, scoutingAuto.getStartingLocationX());
 //           values.put(TABLE_SCOUTING_AUTO_STARTING_LOCATION_Y, scoutingAuto.getStartingLocationY());
  //          values.put(TABLE_SCOUTING_AUTO_ENDING_LOCATION_X, scoutingAuto.getEndingLocationX());
  //          values.put(TABLE_SCOUTING_AUTO_ENDING_LOCATION_Y, scoutingAuto.getEndingLocationY());
        }
        if (scoutingTele != null) {
 //           values.put(TABLE_SCOUTING_TELE_TOTES_STACKED_1, scoutingTele.getTotesStacked1());
   //         values.put(TABLE_SCOUTING_TELE_TOTES_STACKED_2, scoutingTele.getTotesStacked2());
     //       values.put(TABLE_SCOUTING_TELE_TOTES_STACKED_3, scoutingTele.getTotesStacked3());
       //     values.put(TABLE_SCOUTING_TELE_TOTES_STACKED_4, scoutingTele.getTotesStacked4());
         //   values.put(TABLE_SCOUTING_TELE_RC_STACKED_1, scoutingTele.getRCStacked1());
           // values.put(TABLE_SCOUTING_TELE_RC_STACKED_2, scoutingTele.getRCStacked2());
//            values.put(TABLE_SCOUTING_TELE_RC_STACKED_3, scoutingTele.getRCStacked3());
  //          values.put(TABLE_SCOUTING_TELE_RC_STACKED_4, scoutingTele.getRCStacked4());
    //        values.put(TABLE_SCOUTING_TELE_LITTER_1, scoutingTele.getLitter1());
      //      values.put(TABLE_SCOUTING_TELE_LITTER_2, scoutingTele.getLitter2());
        //    values.put(TABLE_SCOUTING_TELE_LITTER_3, scoutingTele.getLitter3());
          //  values.put(TABLE_SCOUTING_TELE_LITTER_4, scoutingTele.getLitter4());
        }
        if (scoutingGeneral != null) {
            values.put(TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES, scoutingGeneral.getCommentsOnPenalties());
            values.put(TABLE_SCOUTING_GENERAL_COMMENTS, scoutingGeneral.getGeneralComments());
        }
        return values;
    }

    public void createScouting(Scouting scouting){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = mapScouting(scouting);
        values.put(TABLE_SCOUTING_LAST_SYNC, "2000-01-01 00:00:00");
        db.insert(TABLE_SCOUTING, null, values);
    }

    public boolean doesScoutingExist(Scouting scouting){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_SCOUTING,
                new String[]{"COUNT(*)"},
                TABLE_SCOUTING_TEAM_KEY + " = ? AND " + TABLE_SCOUTING_MATCH_KEY + " = ? AND "
                + TABLE_SCOUTING_NAME +" = ? AND " + TABLE_SCOUTING_EVENT_KEY + " = ?",
                new String[]{scouting.getTeamKey(), scouting.getMatchKey(), scouting.getNameOfScouter(), scouting.getEventKey()},
                null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    public void updateScouting(Scouting scouting){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_SCOUTING, mapScouting(scouting),
                TABLE_SCOUTING_TEAM_KEY + " = ? AND "
                        + TABLE_SCOUTING_MATCH_KEY + " = ? AND "
                        + TABLE_SCOUTING_NAME +" = ?",
                new String[]{scouting.getTeamKey(), scouting.getMatchKey(), scouting.getNameOfScouter()});
    }

    public List<Scouting> getScouting(String eventKey, String teamKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM " + TABLE_SCOUTING
                + " WHERE " + TABLE_SCOUTING_EVENT_KEY + " = ?"
                + " AND " + TABLE_SCOUTING_TEAM_KEY + " = ?";

        Cursor c = db.rawQuery(selectStatement, new String[]{eventKey, teamKey});

        List<Scouting> scouting = new ArrayList<Scouting>();

        while (c != null && c.moveToNext()){
            scouting.add(mapScouting(c));
        }

        if (c != null) {
            c.close();
        }
        return scouting;
    }

    public List<Scouting> getScouting(String eventKey, String teamKey, String matchKey, String scouterName){
        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM " + TABLE_SCOUTING
                + " WHERE " + TABLE_SCOUTING_EVENT_KEY + " = ?"
                + " AND " + TABLE_SCOUTING_TEAM_KEY + " = ?"
                + " AND " + TABLE_SCOUTING_MATCH_KEY + " = ?"
                + " AND " + TABLE_SCOUTING_NAME + " = ?";

        Cursor c = db.rawQuery(selectStatement, new String[]{eventKey, teamKey, matchKey, scouterName});

        List<Scouting> scouting = new ArrayList<Scouting>();

        while (c != null && c.moveToNext()){
            scouting.add(mapScouting(c));
        }

        if (c != null) {
            c.close();
        }
        return scouting;
    }

    private static Scouting mapScouting(Cursor c){
        Scouting data = new Scouting();
        data.setNameOfScouter(c.getString(c.getColumnIndex(TABLE_SCOUTING_NAME)));
        data.setEventKey(c.getString(c.getColumnIndex(TABLE_SCOUTING_EVENT_KEY)));
        data.setTeamKey(c.getString(c.getColumnIndex(TABLE_SCOUTING_TEAM_KEY)));
        data.setMatchKey(c.getString(c.getColumnIndex(TABLE_SCOUTING_MATCH_KEY)));
        System.out.println("SENDING TEAM INFO");

        ScoutingAuto auto = new ScoutingAuto();
        data.setAuto(auto);
      //  auto.setStartingLocationX(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_STARTING_LOCATION_X)));
     //   auto.setStartingLocationY(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_STARTING_LOCATION_Y)));
     //   auto.setEndingLocationX(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_ENDING_LOCATION_X)));
     //   auto.setEndingLocationY(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_ENDING_LOCATION_Y)));

        ScoutingTele tele = new ScoutingTele();
        data.setTele(tele);

        ScoutingGeneral general = new ScoutingGeneral();
        data.setGeneral(general);
        general.setCommentsOnPenalties(c.getString(c.getColumnIndex(TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES)));
        general.setGeneralComments(c.getString(c.getColumnIndex(TABLE_SCOUTING_GENERAL_COMMENTS)));

        return data;
    }

    public List<Scouting> getAllScoutingNeedingSyncing(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCOUTING, new String[]{"*"},
                TABLE_SCOUTING_LAST_UPDATE +" > "+TABLE_SCOUTING_LAST_SYNC,
                null, null, null, null);

        List<Scouting> retVal = new ArrayList<Scouting>();
        while (c != null && c.moveToNext())
            retVal.add(mapScouting(c));

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    public void setDoneSyncing(Scouting scouting){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_SCOUTING_LAST_SYNC, getDateTime());
        db.update(TABLE_SCOUTING, cv, TABLE_SCOUTING_TEAM_KEY+" = ? AND "+TABLE_SCOUTING_EVENT_KEY
                +" = ? AND "+TABLE_SCOUTING_MATCH_KEY+" = ? AND "+TABLE_SCOUTING_NAME+" = ?",
                new String[]{scouting.getTeamKey(), scouting.getEventKey(), scouting.getMatchKey(), scouting.getNameOfScouter()});
    }
}
