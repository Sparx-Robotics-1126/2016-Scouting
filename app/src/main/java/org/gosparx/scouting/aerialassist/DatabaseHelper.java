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
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
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
    private static final String TABLE_BENCHMARKING = "benchmarking";

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
    private static final String TABLE_SCOUTING_AUTO_PORTCULLIS_POSITION = "a_portcullisPosition";
    private static final String TABLE_SCOUTING_AUTO_CHEVAL_POSITION = "a_chevalPosition";
    private static final String TABLE_SCOUTING_AUTO_MOAT_POSITION = "a_moatPosition";
    private static final String TABLE_SCOUTING_AUTO_RAMPARTS_POSITION = "a_rampartsPosition";
    private static final String TABLE_SCOUTING_AUTO_DRAWBRIDGE_POSITION = "a_drawbridgePosition";
    private static final String TABLE_SCOUTING_AUTO_SALLYPORT_POSITION = "a_sallyportPosition";
    private static final String TABLE_SCOUTING_AUTO_ROCKWALL_POSITION = "a_rockwallPosition";
    private static final String TABLE_SCOUTING_AUTO_ROUGHTERRAIN_POSITION = "a_roughterrainPosition";
    private static final String TABLE_SCOUTING_AUTO_PORTCULLIS_CROSSED = "a_portcullisCrossed";
    private static final String TABLE_SCOUTING_AUTO_CHEVAL_CROSSED = "a_chevalCrossed";
    private static final String TABLE_SCOUTING_AUTO_MOAT_CROSSED = "a_moatCrossed";
    private static final String TABLE_SCOUTING_AUTO_RAMPARTS_CROSSED = "a_rampartsCrossed";
    private static final String TABLE_SCOUTING_AUTO_DRAWBRIDGE_CROSSED = "a_drawbridgeCrossed";
    private static final String TABLE_SCOUTING_AUTO_SALLYPORT_CROSSED = "a_sallyportCrossed";
    private static final String TABLE_SCOUTING_AUTO_ROCKWALL_CROSSED = "a_rockwallCrossed";
    private static final String TABLE_SCOUTING_AUTO_ROUGHTERRAIN_CROSSED = "a_roughterrainCrossed";
    private static final String TABLE_SCOUTING_AUTO_LOWBAR_CROSSED = "a_lowbarCrossed";
    private static final String TABLE_SCOUTING_AUTO_BOULDER_PICKED_UP = "a_boudlerPickedUp";
    private static final String TABLE_SCOUTING_AUTO_ROBOT_SCORED_HIGH = "a_robotScoredHigh";
    private static final String TABLE_SCOUTING_AUTO_ROBOT_SCORED_LOW = "a_robotScoredLow";
    private static final String TABLE_SCOUTING_AUTO_ENDING_POSITION = "a_endingPosition";
    private static final String TABLE_SCOUTING_AUTO_REACH_ACHIEVED = "a_reachAchieved";
    private static final String TABLE_SCOUTING_AUTO_REACH_WAS_CROSS_ATTEMPT = "a_reachWasCrossAttempt";
    private static final String TABLE_SCOUTING_AUTO_STARTED_AS_SPY = "a_startedAsSpy";
    private static final String TABLE_SCOUTING_TELE_HIGH_GOAL_ATTEMPTS = "t_highGoalAttempts";
    private static final String TABLE_SCOUTING_TELE_HIGH_GOALS_SCORED = "t_highGoalsScored";
    private static final String TABLE_SCOUTING_TELE_LOW_GOAL_ATTEMPTS = "t_lowGoalAttempts";
    private static final String TABLE_SCOUTING_TELE_LOW_GOALS_SCORED = "t_lowGoalsScored";
    private static final String TABLE_SCOUTING_TELE_PORTCULLIS_CROSSES = "t_portcullisCrosses";
    private static final String TABLE_SCOUTING_TELE_CHEVAL_CROSSES = "t_chevalCrosses";
    private static final String TABLE_SCOUTING_TELE_MOAT_CROSSES = "t_moatCrosses";
    private static final String TABLE_SCOUTING_TELE_RAMPART_CROSSES = "t_rampartsCrosses";
    private static final String TABLE_SCOUTING_TELE_DRAWBRIDGE_CROSSES = "t_drawbridgeCrosses";
    private static final String TABLE_SCOUTING_TELE_SALLYPORT_CROSSES = "t_sallyportCrosses";
    private static final String TABLE_SCOUTING_TELE_ROCKWALL_CROSSES = "t_rockwallCrosses";
    private static final String TABLE_SCOUTING_TELE_ROUGHTERRAIN_CROSSES = "t_roughterrainCrosses";
    private static final String TABLE_SCOUTING_TELE_LOWBAR_CROSSES = "t_lowbarCrosses";
    private static final String TABLE_SCOUTING_TELE_PLAYS_DEFENSE = "t_playsDefense";
    private static final String TABLE_SCOUTING_TELE_BOULDERS_PICKED_UP = "t_bouldersPickedUp";
    private static final String TABLE_SCOUTING_TELE_BOULDERS_TAKEN_TO_COURTYARD = "t_bouldersTakenToCourtyard";
    private static final String TABLE_SCOUTING_TELE_BOULDERS_RECEIVED_FROM_BRATTICE = "t_bouldersReceivedFromBrattice";
    private static final String TABLE_SCOUTING_GENERAL_NUMBER_OF_PENALTIES = "g_numberOfPenalties";
    private static final String TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES = "g_commentsOnPenalties";
    private static final String TABLE_SCOUTING_GENERAL_NUMBER_OF_TECHNICAL_FOULS = "g_numberOfTechnicalFouls";
    private static final String TABLE_SCOUTING_GENERAL_COMMENTS_TECHNICAL_FOULS = "g_commentsOnTechnicalFouls";
    private static final String TABLE_SCOUTING_GENERAL_COMMENTS = "g_generalComments";

    // Benchmarking Column Names
    private static final String TABLE_BENCHMARKING_KEY = "key";
    private static final String TABLE_BENCHMARKING_LAST_UPDATE = "last_updated";
    private static final String TABLE_BENCHMARKING_LAST_SYNC = "last_sync";
    private static final String TABLE_BENCHMARKING_TEAM_KEY = "team_key";
    private static final String TABLE_BENCHMARKING_EVENT_KEY = "event_key";
    private static final String TABLE_BENCHMARKING_NAME = "scouter_name";
    private static final String TABLE_BENCHMARKING_DRIVES_DESCRIPTION = "d_description";
    private static final String TABLE_BENCHMARKING_DRIVES_APPROX_SPEED = "d_approxSpeed";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_PORTCULLIS = "d_canCrossPortcullis";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_CHEVAL = "d_canCrossCheval";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_MOAT = "d_canCrossMoat";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_RAMPARTS = "d_canCrossRamparts";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_DRAWBRIDGE = "d_canCrossDrawbridge";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_SALLYPORT = "d_canCrosSsallyport";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_ROCKWALL = "d_canCrossRockwall";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_ROUGHTERRAIN = "d_canCrossRoughterrain";
    private static final String TABLE_BENCHMARKING_DRIVES_CANCROSS_LOWBAR = "d_canCrossLowbar";
    private static final String TABLE_BENCHMARKING_DRIVES_EXTENDS_PAST_TRANSPORT = "d_extendsPastTransport";
    private static final String TABLE_BENCHMARKING_AUTO_STARTS_AS_SPY = "a_startsAsSpy";
    private static final String TABLE_BENCHMARKING_AUTO_STARTS_IN_NEUTRAL_ZONE = "a_startsInNeutralZone";
    private static final String TABLE_BENCHMARKING_AUTO_ENDS_IN_COURTYARD = "a_endsInCourtyard";
    private static final String TABLE_BENCHMARKING_AUTO_ENDS_IN_NEUTRAL_ZONE = "a_endsInNeutralZone";
    private static final String TABLE_BENCHMARKING_AUTO_DESCRIPTION = "a_description";
    private static final String TABLE_BENCHMARKING_ACQUISITION_FROM_FLOOR = "acq_fromFloor";
    private static final String TABLE_BENCHMARKING_ACQUISITION_FROM_HUMAN = "acq_fromHumanPlayer";
    private static final String TABLE_BENCHMARKING_ACQUISITION_PREFERREDSOURCE = "acq_preferredSource";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_PORTCULLIS = "acq_carryOverPortcullis";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_CHEVAL = "acq_carryOverCheval";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_MOAT = "acq_carryOverMoat";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_RAMPARTS = "acq_carryOverRamparts";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_DRAWBRIDGE = "acq_carryOverDrawbridge";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_SALLYPORT = "acq_carryOversallyport";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROCKWALL = "acq_carryOverRockwall";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROUGHTERRAIN = "acq_carryOverRoughterrain";
    private static final String TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_LOWBAR = "acq_carryOverLowbar";
    private static final String TABLE_BENCHMARKING_SCORING_CAN_SCORE_HIGH = "s_canScoreHigh";
    private static final String TABLE_BENCHMARKING_SCORING_CAN_SCORE_LOW = "s_canScoreLow";
    private static final String TABLE_BENCHMARKING_SCORING_HIGH_GOALS_PER_MATCH = "s_matchHighGoals";
    private static final String TABLE_BENCHMARKING_SCORING_LOW_GOALS_PER_MATCH = "s_matchLowGoals";
    private static final String TABLE_BENCHMARKING_SCORING_CAN_SCALE = "s_canScale";
    private static final String TABLE_BENCHMARKING_SCORING_CYCLE_TIME = "s_cycleTime";
    private static final String TABLE_BENCHMARKING_SCORING_PLAYS_DEFENSE = "s_playsDefense";
    
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
            + TABLE_SCOUTING_AUTO_PORTCULLIS_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_CHEVAL_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_MOAT_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_RAMPARTS_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_DRAWBRIDGE_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_SALLYPORT_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_ROCKWALL_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_ROUGHTERRAIN_POSITION + " INTEGER, "
            + TABLE_SCOUTING_AUTO_PORTCULLIS_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_CHEVAL_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_MOAT_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_RAMPARTS_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_DRAWBRIDGE_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_SALLYPORT_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_ROCKWALL_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_ROUGHTERRAIN_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_LOWBAR_CROSSED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_BOULDER_PICKED_UP + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_ROBOT_SCORED_HIGH + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_ROBOT_SCORED_LOW + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_ENDING_POSITION + " TEXT, "
            + TABLE_SCOUTING_AUTO_REACH_ACHIEVED + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_REACH_WAS_CROSS_ATTEMPT + " BOOLEAN, "
            + TABLE_SCOUTING_AUTO_STARTED_AS_SPY + " BOOLEAN, "
            + TABLE_SCOUTING_TELE_HIGH_GOAL_ATTEMPTS + " INTEGER, "
            + TABLE_SCOUTING_TELE_HIGH_GOALS_SCORED + " INTEGER, "
            + TABLE_SCOUTING_TELE_LOW_GOAL_ATTEMPTS + " INTEGER, "
            + TABLE_SCOUTING_TELE_LOW_GOALS_SCORED + " INTEGER, "
            + TABLE_SCOUTING_TELE_PORTCULLIS_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_CHEVAL_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_MOAT_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_RAMPART_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_DRAWBRIDGE_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_SALLYPORT_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_ROCKWALL_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_ROUGHTERRAIN_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_LOWBAR_CROSSES + " INTEGER, "
            + TABLE_SCOUTING_TELE_PLAYS_DEFENSE + " BOOLEAN, "
            + TABLE_SCOUTING_TELE_BOULDERS_PICKED_UP + " INTEGER, "
            + TABLE_SCOUTING_TELE_BOULDERS_TAKEN_TO_COURTYARD + " INTEGER, "
            + TABLE_SCOUTING_TELE_BOULDERS_RECEIVED_FROM_BRATTICE + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_NUMBER_OF_PENALTIES + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES + " TEXT, "
            + TABLE_SCOUTING_GENERAL_NUMBER_OF_TECHNICAL_FOULS + " INTEGER, "
            + TABLE_SCOUTING_GENERAL_COMMENTS_TECHNICAL_FOULS + " TEXT, "
            + TABLE_SCOUTING_GENERAL_COMMENTS + " TEXT)";

    private static final String CREATE_TABLE_BENCHMARKING = "CREATE TABLE " + TABLE_BENCHMARKING + "("
        + TABLE_BENCHMARKING_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + TABLE_BENCHMARKING_LAST_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
        + TABLE_BENCHMARKING_LAST_SYNC + " DATETIME, "
        + TABLE_BENCHMARKING_TEAM_KEY + " TEXT, "
        + TABLE_BENCHMARKING_EVENT_KEY + " TEXT, "
        + TABLE_BENCHMARKING_NAME + " TEXT, "
        + TABLE_BENCHMARKING_DRIVES_DESCRIPTION + " TEXT, "
        + TABLE_BENCHMARKING_DRIVES_APPROX_SPEED + " REAL, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_PORTCULLIS + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_CHEVAL + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_MOAT + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_RAMPARTS + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_DRAWBRIDGE + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_SALLYPORT + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_ROCKWALL + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_ROUGHTERRAIN + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_CANCROSS_LOWBAR + " BOOLEAN, "
        + TABLE_BENCHMARKING_DRIVES_EXTENDS_PAST_TRANSPORT + " BOOLEAN, "
        + TABLE_BENCHMARKING_AUTO_STARTS_AS_SPY + " BOOLEAN, "
        + TABLE_BENCHMARKING_AUTO_STARTS_IN_NEUTRAL_ZONE + " BOOLEAN, "
        + TABLE_BENCHMARKING_AUTO_ENDS_IN_COURTYARD + " BOOLEAN, "
        + TABLE_BENCHMARKING_AUTO_ENDS_IN_NEUTRAL_ZONE  + " BOOLEAN, "           
        + TABLE_BENCHMARKING_AUTO_DESCRIPTION + " TEXT, "
        + TABLE_BENCHMARKING_ACQUISITION_FROM_FLOOR + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_FROM_HUMAN + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_PREFERREDSOURCE + " TEXT, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_PORTCULLIS + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_CHEVAL + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_MOAT + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_RAMPARTS + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_DRAWBRIDGE + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_SALLYPORT + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROCKWALL + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROUGHTERRAIN + " BOOLEAN, "
        + TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_LOWBAR + " BOOLEAN, "
        + TABLE_BENCHMARKING_SCORING_CAN_SCORE_HIGH + " BOOLEAN, "
        + TABLE_BENCHMARKING_SCORING_CAN_SCORE_LOW + " BOOLEAN, "
        + TABLE_BENCHMARKING_SCORING_HIGH_GOALS_PER_MATCH + " REAL, "
        + TABLE_BENCHMARKING_SCORING_LOW_GOALS_PER_MATCH + " REAL, "
        + TABLE_BENCHMARKING_SCORING_CAN_SCALE + " BOOLEAN, "
        + TABLE_BENCHMARKING_SCORING_CYCLE_TIME + " REAL, "
        + TABLE_BENCHMARKING_SCORING_PLAYS_DEFENSE + " BOOLEAN)";

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
        db.execSQL(CREATE_TABLE_BENCHMARKING);
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
                new String[]{"*", TABLE_TEAMS+".rowid AS _id", TABLE_TEAMS+".key AS team_key"},
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

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MATCHES + " WHERE " + TABLE_MATCHES_EVENT_KEY + " = ?", new String[]{event.getKey()});

        if(c != null && c.moveToNext())
            retVal = c.getInt(0);

        if (c != null) {
            c.close();
        }

        return retVal;
    }

    public int getEventTeamCount(Event event)
    {
        int retVal = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_E2T + " WHERE " + TABLE_E2T_EVENT + " = ?", new String[]{event.getKey()});

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
            values.put(TABLE_SCOUTING_AUTO_PORTCULLIS_POSITION, scoutingAuto.getPortcullisPosition());
            values.put(TABLE_SCOUTING_AUTO_CHEVAL_POSITION, scoutingAuto.getChevalPosition());
            values.put(TABLE_SCOUTING_AUTO_MOAT_POSITION, scoutingAuto.getMoatPosition());
            values.put(TABLE_SCOUTING_AUTO_RAMPARTS_POSITION, scoutingAuto.getRampartsPosition());
            values.put(TABLE_SCOUTING_AUTO_DRAWBRIDGE_POSITION, scoutingAuto.getDrawbridgePosition());
            values.put(TABLE_SCOUTING_AUTO_SALLYPORT_POSITION, scoutingAuto.getSallyportPosition());
            values.put(TABLE_SCOUTING_AUTO_ROCKWALL_POSITION, scoutingAuto.getRockwallPosition());
            values.put(TABLE_SCOUTING_AUTO_ROUGHTERRAIN_POSITION, scoutingAuto.getRoughterrainPosition());
            values.put(TABLE_SCOUTING_AUTO_PORTCULLIS_CROSSED, scoutingAuto.getPortcullisCrossed());
            values.put(TABLE_SCOUTING_AUTO_CHEVAL_CROSSED, scoutingAuto.getChevalCrossed());
            values.put(TABLE_SCOUTING_AUTO_MOAT_CROSSED, scoutingAuto.getMoatCrossed());
            values.put(TABLE_SCOUTING_AUTO_RAMPARTS_CROSSED, scoutingAuto.getRampartsCrossed());
            values.put(TABLE_SCOUTING_AUTO_DRAWBRIDGE_CROSSED, scoutingAuto.getDrawbridgeCrossed());
            values.put(TABLE_SCOUTING_AUTO_SALLYPORT_CROSSED, scoutingAuto.getSallyportCrossed());
            values.put(TABLE_SCOUTING_AUTO_ROCKWALL_CROSSED, scoutingAuto.getRockwallCrossed());
            values.put(TABLE_SCOUTING_AUTO_ROUGHTERRAIN_CROSSED, scoutingAuto.getRoughterrainCrossed());
            values.put(TABLE_SCOUTING_AUTO_LOWBAR_CROSSED, scoutingAuto.getLowbarCrossed());
            values.put(TABLE_SCOUTING_AUTO_BOULDER_PICKED_UP, scoutingAuto.getBoudlerPickedUp());
            values.put(TABLE_SCOUTING_AUTO_ROBOT_SCORED_HIGH, scoutingAuto.getRobotScoredHigh());
            values.put(TABLE_SCOUTING_AUTO_ROBOT_SCORED_LOW, scoutingAuto.getRobotScoredLow());
            values.put(TABLE_SCOUTING_AUTO_ENDING_POSITION, scoutingAuto.getEndingPosition());
            values.put(TABLE_SCOUTING_AUTO_REACH_ACHIEVED, scoutingAuto.getReachAchieved());
            values.put(TABLE_SCOUTING_AUTO_REACH_WAS_CROSS_ATTEMPT, scoutingAuto.getReachWasCrossAttempt());
            values.put(TABLE_SCOUTING_AUTO_STARTED_AS_SPY, scoutingAuto.getStartedAsSpy());
        }
        if (scoutingTele != null) {
            values.put(TABLE_SCOUTING_TELE_HIGH_GOAL_ATTEMPTS, scoutingTele.getHighGoalAttempts());
            values.put(TABLE_SCOUTING_TELE_HIGH_GOALS_SCORED, scoutingTele.getHighGoalsScored());
            values.put(TABLE_SCOUTING_TELE_LOW_GOAL_ATTEMPTS, scoutingTele.getLowGoalAttempts());
            values.put(TABLE_SCOUTING_TELE_LOW_GOALS_SCORED, scoutingTele.getLowGoalsScored());
            values.put(TABLE_SCOUTING_TELE_PORTCULLIS_CROSSES, scoutingTele.getPortcullisCrosses());
            values.put(TABLE_SCOUTING_TELE_CHEVAL_CROSSES, scoutingTele.getChevalCrosses());
            values.put(TABLE_SCOUTING_TELE_MOAT_CROSSES, scoutingTele.getMoatCrosses());
            values.put(TABLE_SCOUTING_TELE_RAMPART_CROSSES, scoutingTele.getRampartsCrosses());
            values.put(TABLE_SCOUTING_TELE_DRAWBRIDGE_CROSSES, scoutingTele.getDrawbridgeCrosses());
            values.put(TABLE_SCOUTING_TELE_SALLYPORT_CROSSES, scoutingTele.getSallyportCrosses());
            values.put(TABLE_SCOUTING_TELE_ROCKWALL_CROSSES, scoutingTele.getRockwallCrosses());
            values.put(TABLE_SCOUTING_TELE_ROUGHTERRAIN_CROSSES, scoutingTele.getRoughterrainCrosses());
            values.put(TABLE_SCOUTING_TELE_LOWBAR_CROSSES, scoutingTele.getLowbarCrosses());
            values.put(TABLE_SCOUTING_TELE_PLAYS_DEFENSE, scoutingTele.getPlaysDefense());
            values.put(TABLE_SCOUTING_TELE_BOULDERS_PICKED_UP, scoutingTele.getBouldersPickedUp());
            values.put(TABLE_SCOUTING_TELE_BOULDERS_TAKEN_TO_COURTYARD, scoutingTele.getBouldersTakenToCourtyard());
            values.put(TABLE_SCOUTING_TELE_BOULDERS_RECEIVED_FROM_BRATTICE, scoutingTele.getBouldersReceivedFromBrattice());
        }
        if (scoutingGeneral != null) {
            values.put(TABLE_SCOUTING_GENERAL_NUMBER_OF_PENALTIES, scoutingGeneral.getNumberOfPenalties());
            values.put(TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES, scoutingGeneral.getCommentsOnPenalties());
            values.put(TABLE_SCOUTING_GENERAL_NUMBER_OF_TECHNICAL_FOULS, scoutingGeneral.getNumberOfTechnicalFouls());
            values.put(TABLE_SCOUTING_GENERAL_COMMENTS_TECHNICAL_FOULS, scoutingGeneral.getCommentsOnTechnicalFouls());
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
                        + TABLE_SCOUTING_NAME + " = ? AND " + TABLE_SCOUTING_EVENT_KEY + " = ?",
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
        //TODO: Fix this section. It doesn't check ScoutingAuto, etc. objects. I'm not even sure if this section is the problem, but it looks like it might be
        //Duane will have to do this, not really sure how to go about fixing this at the moment
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

        ScoutingAuto scoutingAuto = new ScoutingAuto();
        data.setAuto(scoutingAuto);
        ScoutingTele scoutingTele = new ScoutingTele();
        data.setTele(scoutingTele);
        ScoutingGeneral scoutingGeneral = new ScoutingGeneral();
        data.setGeneral(scoutingGeneral);

        if (scoutingAuto != null) {
            scoutingAuto.setPortcullisPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_PORTCULLIS_POSITION)));
            scoutingAuto.setChevalPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_CHEVAL_POSITION)));
            scoutingAuto.setMoatPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_MOAT_POSITION)));
            scoutingAuto.setRampartsPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_RAMPARTS_POSITION)));
            scoutingAuto.setDrawbridgePosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_DRAWBRIDGE_POSITION)));
            scoutingAuto.setSallyportPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_SALLYPORT_POSITION)));
            scoutingAuto.setRockwallPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_ROCKWALL_POSITION)));
            scoutingAuto.setRoughterrainPosition(c.getInt(c.getColumnIndex(TABLE_SCOUTING_AUTO_ROUGHTERRAIN_POSITION)));
            scoutingAuto.setPortcullisCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_PORTCULLIS_CROSSED)));
            scoutingAuto.setChevalCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_CHEVAL_CROSSED)));
            scoutingAuto.setMoatCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_MOAT_CROSSED)));
            scoutingAuto.setRampartsCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_RAMPARTS_CROSSED)));
            scoutingAuto.setDrawbridgeCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_DRAWBRIDGE_CROSSED)));
            scoutingAuto.setSallyportCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_SALLYPORT_CROSSED)));
            scoutingAuto.setRockwallCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_ROCKWALL_CROSSED)));
            scoutingAuto.setRoughterrainCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_ROUGHTERRAIN_CROSSED)));
            scoutingAuto.setLowbarCrossed(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_LOWBAR_CROSSED)));
            scoutingAuto.setBoudlerPickedUp(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_BOULDER_PICKED_UP)));
            scoutingAuto.setRobotScoredHigh(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_ROBOT_SCORED_HIGH)));
            scoutingAuto.setRobotScoredLow(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_ROBOT_SCORED_LOW)));
            scoutingAuto.setEndingPosition(c.getString(c.getColumnIndex(TABLE_SCOUTING_AUTO_ENDING_POSITION)));
            scoutingAuto.setReachAchieved(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_REACH_ACHIEVED)));
            scoutingAuto.setReachWasCrossAttempt(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_REACH_WAS_CROSS_ATTEMPT)));
            scoutingAuto.setStartedAsSpy(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_AUTO_STARTED_AS_SPY)));
        }

        if (scoutingTele != null) {
            scoutingTele.setHighGoalAttempts(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_HIGH_GOAL_ATTEMPTS)));
            scoutingTele.setHighGoalsScored(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_HIGH_GOALS_SCORED)));
            scoutingTele.setLowGoalAttempts(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_LOW_GOAL_ATTEMPTS)));
            scoutingTele.setLowGoalsScored(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_LOW_GOALS_SCORED)));
            scoutingTele.setPortcullisCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_PORTCULLIS_CROSSES)));
            scoutingTele.setChevalCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_CHEVAL_CROSSES)));
            scoutingTele.setMoatCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_MOAT_CROSSES)));
            scoutingTele.setRampartsCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_RAMPART_CROSSES)));
            scoutingTele.setDrawbridgeCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_DRAWBRIDGE_CROSSES)));
            scoutingTele.setSallyportCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_SALLYPORT_CROSSES)));
            scoutingTele.setRockwallCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_ROCKWALL_CROSSES)));
            scoutingTele.setRoughterrainCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_ROUGHTERRAIN_CROSSES)));
            scoutingTele.setLowbarCrosses(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_LOWBAR_CROSSES)));
            scoutingTele.setPlaysDefense(getBoolean(c, c.getColumnIndex(TABLE_SCOUTING_TELE_PLAYS_DEFENSE)));
            scoutingTele.setBouldersPickedUp(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_BOULDERS_PICKED_UP)));
            scoutingTele.setBouldersTakenToCourtyard(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_BOULDERS_TAKEN_TO_COURTYARD)));
            scoutingTele.setBouldersReceivedFromBrattice(c.getInt(c.getColumnIndex(TABLE_SCOUTING_TELE_BOULDERS_RECEIVED_FROM_BRATTICE)));
        }

        if (scoutingGeneral != null) {
            scoutingGeneral.setNumberOfPenalties(c.getInt(c.getColumnIndex(TABLE_SCOUTING_GENERAL_NUMBER_OF_PENALTIES)));
            scoutingGeneral.setCommentsOnPenalties(c.getString(c.getColumnIndex(TABLE_SCOUTING_GENERAL_COMMENTS_PENALTIES)));
            scoutingGeneral.setNumberOfTechnicalFouls(c.getInt(c.getColumnIndex(TABLE_SCOUTING_GENERAL_NUMBER_OF_TECHNICAL_FOULS)));
            scoutingGeneral.setCommentsOnTechnicalFouls(c.getString(c.getColumnIndex(TABLE_SCOUTING_GENERAL_COMMENTS_TECHNICAL_FOULS)));
            scoutingGeneral.setGeneralComments(c.getString(c.getColumnIndex(TABLE_SCOUTING_GENERAL_COMMENTS)));
        }
        
        return data;
    }

    private static Boolean getBoolean(Cursor c, int columnIndex) {
        if (c.isNull(columnIndex) || c.getShort(columnIndex) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Scouting> getAllScoutingNeedingSyncing(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCOUTING, new String[]{"*"},
                TABLE_SCOUTING_LAST_UPDATE + " > " + TABLE_SCOUTING_LAST_SYNC,
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

    // returns a ContentValues (for saving to the database) containing the values from the given ScoutingInfo object.
    private ContentValues mapBenchmarking(ScoutingInfo scouting){
        ContentValues values = new ContentValues();
        values.put(TABLE_BENCHMARKING_LAST_UPDATE, getDateTime());
        values.put(TABLE_BENCHMARKING_TEAM_KEY, scouting.getTeamKey());
        values.put(TABLE_BENCHMARKING_EVENT_KEY, scouting.getEventKey());
        values.put(TABLE_BENCHMARKING_NAME, scouting.getNameOfScouter());
        values.put(TABLE_BENCHMARKING_DRIVES_DESCRIPTION, scouting.getDriveSystemDescription());
        values.put(TABLE_BENCHMARKING_DRIVES_APPROX_SPEED, scouting.getApproxSpeedFeetPerSecond());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_PORTCULLIS, scouting.getCanCrossPortcullis());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_CHEVAL, scouting.getCanCrossCheval());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_MOAT, scouting.getCanCrossMoat());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_RAMPARTS, scouting.getCanCrossRamparts());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_DRAWBRIDGE, scouting.getCanCrossDrawbridge());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_SALLYPORT, scouting.getCanCrossSallyport());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_ROCKWALL, scouting.getCanCrossRockwall());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_ROUGHTERRAIN, scouting.getCanCrossRoughterrain());
        values.put(TABLE_BENCHMARKING_DRIVES_CANCROSS_LOWBAR, scouting.getCanCrossLowbar());
        values.put(TABLE_BENCHMARKING_DRIVES_EXTENDS_PAST_TRANSPORT, scouting.getDoesExtendBeyondTransportConfig());
        values.put(TABLE_BENCHMARKING_AUTO_STARTS_AS_SPY, scouting.getAutoStartInSpyPosition());
        values.put(TABLE_BENCHMARKING_AUTO_STARTS_IN_NEUTRAL_ZONE, scouting.getAutoStartInNeutralZone());
        values.put(TABLE_BENCHMARKING_AUTO_ENDS_IN_COURTYARD, scouting.getAutoEndInCourtyard());
        values.put(TABLE_BENCHMARKING_AUTO_ENDS_IN_NEUTRAL_ZONE, scouting.getAutoEndInNeutralZone());
        values.put(TABLE_BENCHMARKING_AUTO_DESCRIPTION, scouting.getAutoCapabilitiesDescription());
        values.put(TABLE_BENCHMARKING_ACQUISITION_FROM_FLOOR, scouting.getAcquiresBouldersFromFloor());
        values.put(TABLE_BENCHMARKING_ACQUISITION_FROM_HUMAN, scouting.getAcquiresBouldersFromHumanPlayer());
        values.put(TABLE_BENCHMARKING_ACQUISITION_PREFERREDSOURCE, scouting.getPreferredBoulderSource());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_PORTCULLIS, scouting.getCanCarryBouldersOverPortcullis());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_CHEVAL, scouting.getCanCarryBouldersOverCheval());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_MOAT, scouting.getCanCarryBouldersOverMoat());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_RAMPARTS, scouting.getCanCarryBouldersOverRamparts());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_DRAWBRIDGE, scouting.getCanCarryBouldersOverDrawbridge());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_SALLYPORT, scouting.getCanCarryBouldersOverSallyport());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROCKWALL, scouting.getCanCarryBouldersOverRockwall());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROUGHTERRAIN, scouting.getCanCarryBouldersOverRoughterrain());
        values.put(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_LOWBAR, scouting.getCanCarryBouldersOverLowbar());
        values.put(TABLE_BENCHMARKING_SCORING_CAN_SCORE_HIGH, scouting.getCanScoreInHighGoal());
        values.put(TABLE_BENCHMARKING_SCORING_CAN_SCORE_LOW, scouting.getCanScoreInLowGoal());
        values.put(TABLE_BENCHMARKING_SCORING_HIGH_GOALS_PER_MATCH, scouting.getAverageHighGoalsPerMatch());
        values.put(TABLE_BENCHMARKING_SCORING_LOW_GOALS_PER_MATCH, scouting.getAverageLowGoalsPerMatch());
        values.put(TABLE_BENCHMARKING_SCORING_CAN_SCALE, scouting.getCanScale());
        values.put(TABLE_BENCHMARKING_SCORING_CYCLE_TIME, scouting.getCycleTime());
        values.put(TABLE_BENCHMARKING_SCORING_PLAYS_DEFENSE, scouting.getPlaysDefense());

        return values;
    }

    // initializes and returns a ScoutingInfo object from a row in the Benchmarking table
    private static ScoutingInfo mapBenchmarking(Cursor c){
        ScoutingInfo data = new ScoutingInfo();

        data.setTeamKey(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_TEAM_KEY)));
        data.setEventKey(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_EVENT_KEY)));
        data.setNameOfScouter(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_NAME)));
        data.setDriveSystemDescription(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_DESCRIPTION)));
        data.setApproxSpeedFeetPerSecond(c.getDouble(c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_APPROX_SPEED)));
        data.setCanCrossPortcullis(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_PORTCULLIS)));
        data.setCanCrossCheval(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_CHEVAL)));
        data.setCanCrossMoat(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_MOAT)));
        data.setCanCrossRamparts(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_RAMPARTS)));
        data.setCanCrossDrawbridge(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_DRAWBRIDGE)));
        data.setCanCrossSallyport(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_SALLYPORT)));
        data.setCanCrossRockwall(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_ROCKWALL)));
        data.setCanCrossRoughterrain(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_ROUGHTERRAIN)));
        data.setCanCrossLowbar(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_CANCROSS_LOWBAR)));
        data.setDoesExtendBeyondTransportConfig(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_DRIVES_EXTENDS_PAST_TRANSPORT)));
        data.setAutoStartInSpyPosition(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_AUTO_STARTS_AS_SPY)));
        data.setAutoStartInNeutralZone(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_AUTO_STARTS_IN_NEUTRAL_ZONE)));
        data.setAutoEndInCourtyard(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_AUTO_ENDS_IN_COURTYARD)));
        data.setAutoEndInNeutralZone(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_AUTO_ENDS_IN_NEUTRAL_ZONE)));
        data.setAutoCapabilitiesDescription(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_AUTO_DESCRIPTION)));
        data.setAcquiresBouldersFromFloor(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_FROM_FLOOR)));
        data.setAcquiresBouldersFromHumanPlayer(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_FROM_HUMAN)));
        data.setPreferredBoulderSource(c.getString(c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_PREFERREDSOURCE)));
        data.setCanCarryBouldersOverPortcullis(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_PORTCULLIS)));
        data.setCanCarryBouldersOverCheval(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_CHEVAL)));
        data.setCanCarryBouldersOverMoat(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_MOAT)));
        data.setCanCarryBouldersOverRamparts(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_RAMPARTS)));
        data.setCanCarryBouldersOverDrawbridge(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_DRAWBRIDGE)));
        data.setCanCarryBouldersOverSallyport(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_SALLYPORT)));
        data.setCanCarryBouldersOverRockwall(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROCKWALL)));
        data.setCanCarryBouldersOverRoughterrain(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_ROUGHTERRAIN)));
        data.setCanCarryBouldersOverLowbar(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_ACQUISITION_CARRYOVER_LOWBAR)));
        data.setCanScoreInHighGoal(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_SCORING_CAN_SCORE_HIGH)));
        data.setCanScoreInLowGoal(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_SCORING_CAN_SCORE_LOW)));
        data.setAverageHighGoalsPerMatch(c.getDouble(c.getColumnIndex(TABLE_BENCHMARKING_SCORING_HIGH_GOALS_PER_MATCH)));
        data.setAverageLowGoalsPerMatch(c.getDouble(c.getColumnIndex(TABLE_BENCHMARKING_SCORING_LOW_GOALS_PER_MATCH)));
        data.setCanScale(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_SCORING_CAN_SCALE)));
        data.setCycleTime(c.getDouble(c.getColumnIndex(TABLE_BENCHMARKING_SCORING_CYCLE_TIME)));
        data.setPlaysDefense(getBoolean(c, c.getColumnIndex(TABLE_BENCHMARKING_SCORING_PLAYS_DEFENSE)));
        
        return data;
    }

    public void createBenchmarking(ScoutingInfo scoutingInfo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = mapBenchmarking(scoutingInfo);
        values.put(TABLE_BENCHMARKING_LAST_SYNC, "2000-01-01 00:00:00");
        db.insert(TABLE_BENCHMARKING, null, values);
    }

    public boolean doesBenchmarkingExist(ScoutingInfo scoutingInfo){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_BENCHMARKING,
                new String[]{"COUNT(*)"},
                TABLE_BENCHMARKING_TEAM_KEY + " = ? AND "
                        + TABLE_BENCHMARKING_NAME + " = ? AND " + TABLE_BENCHMARKING_EVENT_KEY + " = ?",
                new String[]{scoutingInfo.getTeamKey(), scoutingInfo.getNameOfScouter(), scoutingInfo.getEventKey()},
                null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    public void updateBenchmarking(ScoutingInfo scoutingInfo){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_BENCHMARKING, mapBenchmarking(scoutingInfo),
                TABLE_BENCHMARKING_EVENT_KEY + " = ? AND "
                        + TABLE_BENCHMARKING_TEAM_KEY + " = ? AND "
                        + TABLE_BENCHMARKING_NAME + " = ?",
                new String[]{scoutingInfo.getEventKey(), scoutingInfo.getTeamKey(), scoutingInfo.getNameOfScouter()});
    }

    public List<ScoutingInfo> getBenchmarking(String eventKey, String teamKey, String scouterName){
        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM " + TABLE_BENCHMARKING
                + " WHERE " + TABLE_BENCHMARKING_EVENT_KEY + " = ?"
                + " AND " + TABLE_BENCHMARKING_TEAM_KEY + " = ?"
                + " AND " + TABLE_BENCHMARKING_NAME + " = ?";

        Cursor c = db.rawQuery(selectStatement, new String[]{eventKey, teamKey, scouterName});

        List<ScoutingInfo> scoutingInfos = new ArrayList<ScoutingInfo>();

        while (c != null && c.moveToNext()){
            scoutingInfos.add(mapBenchmarking(c));
        }

        if (c != null) {
            c.close();
        }
        return scoutingInfos;
    }


}
