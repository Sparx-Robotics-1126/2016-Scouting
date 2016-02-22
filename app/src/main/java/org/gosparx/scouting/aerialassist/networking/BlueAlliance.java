package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.util.Date;
import java.util.List;

/**
 * Created by jbass on 3/1/14.
 */
public class BlueAlliance {
    private static final String TAG = "BlueAlliance";
    private static final String BASE_URL = "http://www.thebluealliance.com";
    private static final String GET_EVENT_LIST = "/api/v2/events/{YEAR}";
    private static final String GET_EVENT = "/api/v2/event/{EVENT_KEY}/";
    private static final String GET_MATCH_LIST = "/api/v2/event/{EVENT_KEY}/matches";
    private static final String GET_TEAM_LIST = "/api/v2/event/{EVENT_KEY}/teams";
    private static BlueAlliance blueAlliance;
    private Context context;
    private Ion ion;
    private String versionName;
    private DatabaseHelper dbHelper;
    private BlueAlliance(Context context){
        this.context = context;
        ion = Ion.getInstance(context, TAG);
        ion.configure().setLogging(TAG, Log.INFO);
        ion.getHttpClient().getSocketMiddleware().setMaxConnectionCount(25);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        ion.configure().setGson(gson);
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }catch (PackageManager.NameNotFoundException e){
            Log.e(TAG, "Could not find version name.", e);
            versionName = "UNKNOWN";
        }
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static synchronized BlueAlliance getInstance(Context c) {
        if (blueAlliance == null)
            blueAlliance = new BlueAlliance(c);
        else
            blueAlliance.context = c;
        return blueAlliance;
    }

    public void cancelAll(){
        ion.cancelAll();
    }

    public void loadEventList(final int year){loadEventList(year, null);}
    public void loadEventList(final int year, final NetworkCallback callback){
        String request = (BASE_URL+GET_EVENT_LIST).replace("{YEAR}", Integer.toString(year));
        Ion.with(context)
                .load(request)
                .addHeader("X-TBA-App-Id", "frc1126:scouting-app-2016:" + versionName)
                .as(new TypeToken<List<Event>>(){})
                .setCallback(new FutureCallback<List<Event>>() {
                    @Override
                    public void onCompleted(Exception e, final List<Event> result) {
                        if(e != null){
                            Log.e(TAG, "Issue getting event list", e);
                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }

                        for(Event event : result) {
                            if(dbHelper.doesEventExist(event))
                                dbHelper.updateEvent(event);
                            else
                                dbHelper.createEvent(event);

                            Log.d(TAG, "Done getting basic event("+event.getEventCode()+")");
                        }
                        if(callback != null)
                            callback.handleFinishDownload(true);

                        NetworkHelper.setLoadedEventList(context);
                    }
                });
    }

    public void loadEvent(final String eventKey){loadEvent(eventKey, null);}
    public void loadEvent(final String eventCode, final NetworkCallback callback){
        String request = (BASE_URL+GET_EVENT).replace("{EVENT_KEY}", eventCode);
        Ion.with(context)
                .load(request)
                .addHeader("X-TBA-App-Id", "frc1126:scouting-app-2014:" + versionName)
                .as(new TypeToken<Event>() {
                })
                .setCallback(new FutureCallback<Event>() {
                    @Override
                    public void onCompleted(Exception e, Event event) {
                        if(e != null){
                            Log.e(TAG, "Issue getting event("+eventCode+")", e);
                            System.out.println("THE EVENTS WERE NOT LOADED");

                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }
                        if(dbHelper.doesEventExist(event))
                            dbHelper.updateEvent(event);
                        else
                            dbHelper.createEvent(event);

                        Log.d(TAG, "Done getting basic event("+eventCode+")");
                        if(callback != null)
                            callback.handleFinishDownload(true);
                    }
                });
    }

    public void loadMatches(final Event event){loadMatches(event, null);}
    public void loadMatches(final Event event, final NetworkCallback callback){
        String request = (BASE_URL+GET_MATCH_LIST).replace("{EVENT_KEY}", event.getKey());
        Ion.with(context)
                .load(request)
                .addHeader("X-TBA-App-Id", "frc1126:scouting-app-2014:" + versionName)
                .noCache()
                .as(new TypeToken<List<Match>>(){})
                .setCallback(new FutureCallback<List<Match>>() {
                    @Override
                    public void onCompleted(Exception e, List<Match> result) {
                        if( e != null){
                            Log.e(TAG, "Issue getting matches from event("+event.getKey()+")", e);

                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }
                        for (Match match : result) {
                            if (dbHelper.doesMatchExist(match))
                                dbHelper.updateMatch(match);
                            else
                                dbHelper.createMatch(match);
                        }

                        if(callback != null)
                            callback.handleFinishDownload(true);
                        NetworkHelper.setLoadedMatchList(context);
                    }
                });
    }

    public void loadTeams(final Event event){loadTeams(event, null);}
    public void loadTeams(final Event event, final NetworkCallback callback){
        String request = (BASE_URL+GET_TEAM_LIST).replace("{EVENT_KEY}", event.getKey());
        Ion.with(context)
                .load(request)
                .addHeader("X-TBA-App-Id", "frc1126:scouting-app-2014:" + versionName)
                .as(new TypeToken<List<Team>>() {})
                .setCallback(new FutureCallback<List<Team>>() {
                    @Override
                    public void onCompleted(Exception e, List<Team> result) {
                        if(e != null || result == null){
                            Log.e(TAG, "Issue getting teams from event("+event.getKey()+")", e);

                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }
                        for(Team team : result){
                            if(dbHelper.doesTeamExist(team))
                                dbHelper.updateTeam(team);
                            else
                                dbHelper.createTeam(team);

                            if(!dbHelper.doesE2TAssociationExist(event.getKey(), team.getKey()))
                                dbHelper.createE2TAssociation(event.getKey(), team.getKey());
                        }
                        if(callback != null)
                            callback.handleFinishDownload(true);
                        NetworkHelper.setLoadedTeams(context);
                    }
                });
    }

}
