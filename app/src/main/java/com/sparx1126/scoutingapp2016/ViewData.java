package com.sparx1126.scoutingapp2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
import org.gosparx.scouting.aerialassist.dto.ScoutingTele;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.SparxScouting;

import java.util.List;

/**
 * @author Dan Martin
 */
public class ViewData extends AppCompatActivity {
    private static List<Scouting> scoutList;
    private static List<ScoutingInfo> benchmarkList;
    private static TextView low, high, scale, chal, fail, nA, def, portcullis, cheval, moat, ramparts, drawbridge, sallyport,
            rockwall, roughterrain, lowbar, highAble, lowAble, boulderSource, portCross, chevCross,
            moatCross, ramCross, drawCross, salCross, rockCross, roughCross, lowCross;
    private static LinearLayout benchmarkData;
    private static LinearLayout scoutData;
    private static TextView benchmarkNoData, scoutNoData;
    private static TextView benchmarkLoad, scoutLoad;
    private String name;
    private SparxScouting s;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i = getIntent();

        //match fields
        scoutNoData = (TextView) findViewById(R.id.scoutingNoData);
        scoutData = (LinearLayout) findViewById(R.id.scoutingData);
        scoutLoad = (TextView) findViewById(R.id.scoutingLoading);
        low = (TextView) findViewById(R.id.lowAverage);
        high = (TextView) findViewById(R.id.highAverage);
        scale = (TextView) findViewById(R.id.scaleAverage);
        chal = (TextView) findViewById(R.id.chalAverage);
        fail = (TextView) findViewById(R.id.failAverage);
        nA = (TextView) findViewById(R.id.naAverage);
        def = (TextView) findViewById(R.id.defenseAverage);
        portcullis = (TextView) findViewById(R.id.portcullisAverage);
        cheval = (TextView) findViewById(R.id.chevalAverage);
        moat = (TextView) findViewById(R.id.moatAverage);
        ramparts = (TextView) findViewById(R.id.rampartsAverage);
        drawbridge = (TextView) findViewById(R.id.drawbridgeAverage);
        sallyport = (TextView) findViewById(R.id.sallyportAverage);
        rockwall = (TextView) findViewById(R.id.rockwallAverage);
        roughterrain = (TextView) findViewById(R.id.roughterrainAverage);
        lowbar = (TextView) findViewById(R.id.lowbarAverage);

        //visibility
        scoutLoad.setVisibility(View.VISIBLE);
        scoutData.setVisibility(View.GONE);
        scoutNoData.setVisibility(View.GONE);

        //benchmarking fields

        benchmarkData = (LinearLayout) findViewById(R.id.benchmarkData);
        benchmarkNoData = (TextView) findViewById(R.id.benchmarkNoData);
        benchmarkLoad = (TextView) findViewById(R.id.benchmarkLoading);
        lowAble = (TextView) findViewById(R.id.canLow);
        highAble = (TextView) findViewById(R.id.canHigh);
        boulderSource = (TextView) findViewById(R.id.boulderSource);
        portCross = (TextView) findViewById(R.id.portcullisAble);
        chevCross = (TextView) findViewById(R.id.chevalAble);
        moatCross = (TextView) findViewById(R.id.moatAble);
        ramCross = (TextView) findViewById(R.id.rampartsAble);
        drawCross = (TextView) findViewById(R.id.drawbridgeAble);
        salCross = (TextView) findViewById(R.id.sallyportAble);
        rockCross = (TextView) findViewById(R.id.rockwallAble);
        roughCross = (TextView) findViewById(R.id.roughterrainAble);
        lowCross = (TextView) findViewById(R.id.lowbarAble);

        //visibility
        benchmarkLoad.setVisibility(View.VISIBLE);
        benchmarkData.setVisibility(View.GONE);
        benchmarkNoData.setVisibility(View.GONE);

        final String teamKey = i.getStringExtra(MainMenu.TEAM_NAME);
        final String eventKey = i.getStringExtra(MainMenu.EVENT_KEY);
        String teamName = teamKey.replace("frc", ""); // remove frc from the key to only show the team number

        name = i.getStringExtra(MainMenu.NAME);

        toolbar.setTitle("Viewing data for Team " + teamName);
        s = SparxScouting.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        s.getScouting(dbHelper.getTeam(teamKey), dbHelper.getEvent(eventKey), new NetworkCallback() {
            //need to get scouting from online database in case we don't have all the data
            @Override
            public void handleFinishDownload(boolean success) {
                if (success) {
                    scoutList = dbHelper.getScouting(eventKey, teamKey);
                    final ScoutingInfo in = new ScoutingInfo();
                    in.setEventKey(eventKey);
                    in.setTeamKey(teamKey);
                    in.setNameOfScouter(name);
                    if (dbHelper.doesBenchmarkingExist(in.getEventKey(), in.getTeamKey())) {
                        //benchmarking exists, so don't need to check online
                        benchmarkList = dbHelper.getBenchmarking(eventKey, teamKey);
                        initFromBenchmarkList(benchmarkList);
                    } else {
                        //no benchmarking found, so check online for one
                        s.getBenchmarking(dbHelper.getTeam(teamKey), dbHelper.getEvent(eventKey), new NetworkCallback() {
                            @Override
                            public void handleFinishDownload(boolean success) {
                                if (success) {
                                    //check if one was found online
                                    if (dbHelper.doesBenchmarkingExist(in.getEventKey(), in.getTeamKey())) {
                                        //found, so set benchmarklist to it
                                        benchmarkList = dbHelper.getBenchmarking(eventKey, teamKey);
                                    }
                                }
                                //initialize the data
                                initFromBenchmarkList(benchmarkList);
                            }
                        });
                    }
                } else {
                    Log.e("", "ERROR");
                }
            }

            /**
             * sets the text of this to yes if b is true or no if b is false
             *
             * @param text the TextView to change
             * @param b    the boolean to check
             */
            private void setYesNo(TextView text, boolean b) {
                if (b) {
                    text.setText("Yes");
                } else text.setText("No");
            }

            private void initFromBenchmarkList(List<ScoutingInfo> benchmarkList) {
                Data data = new Data();
                try {
                    ScoutingInfo info = benchmarkList.get(benchmarkList.size() - 1); // get the most recent benchmarking data
                    benchmarkData.setVisibility(View.VISIBLE);
                    benchmarkNoData.setVisibility(View.GONE);
                    benchmarkLoad.setVisibility(View.GONE);
                    setYesNo(lowAble, info.getCanScoreInLowGoal());
                    setYesNo(highAble, info.getCanScoreInHighGoal());
                    setYesNo(portCross, info.getCanCrossPortcullis());
                    setYesNo(chevCross, info.getCanCrossCheval());
                    setYesNo(moatCross, info.getCanCrossMoat());
                    setYesNo(ramCross, info.getCanCrossRamparts());
                    setYesNo(drawCross, info.getCanCrossDrawbridge());
                    setYesNo(salCross, info.getCanCrossSallyport());
                    setYesNo(rockCross, info.getCanCrossRockwall());
                    setYesNo(roughCross, info.getCanCrossRoughterrain());
                    setYesNo(lowCross, info.getCanCrossLowbar());
                    if (info.getAcquiresBouldersFromHumanPlayer()) {
                        if (info.getAcquiresBouldersFromFloor()) {
                            boulderSource.setText("Human player and floor");
                        } else {
                            boulderSource.setText("Human player");
                        }
                    } else boulderSource.setText("Floor");
                } catch (Exception e) {
                    setNoDataBenchmarking();
                }
                if (data.computeAverages()) {
                    //info was found, so show the data
                    String lowInfo = String.valueOf((int) data.lowAvg) + " % (" + data.lowComp + " out of " + data.lowAtt + " attempts)";
                    String highInfo = String.valueOf((int) data.highAvg) + " % (" + data.highComp + " out of " + data.highAtt + " attempts)";
                    String defInfo = String.valueOf(data.defAvg) + " % (" + data.defTotal + " out of " + data.defTimes + " matches)";
                    String pInfo = String.valueOf(data.pAvg) + "(" + data.pCross + " times in " + data.pTimes + " matches)";
                    String cInfo = String.valueOf(data.cAvg) + "(" + data.cCross + " times in " + data.cTimes + " matches)";
                    String mInfo = String.valueOf(data.mAvg) + "(" + data.mCross + " times in " + data.mTimes + " matches)";
                    String rInfo = String.valueOf(data.rAvg) + "(" + data.rCross + " times in " + data.rTimes + " matches)";
                    String dInfo = String.valueOf(data.dAvg) + "(" + data.dCross + " times in " + data.dTimes + " matches)";
                    String spInfo = String.valueOf(data.spAvg) + "(" + data.spCross + " times in " + data.spTimes + " matches)";
                    String rwInfo = String.valueOf(data.rwAvg) + "(" + data.rwCross + " times in " + data.rwTimes + " matches)";
                    String rtInfo = String.valueOf(data.rtAvg) + "(" + data.rtCross + " times in " + data.rtTimes + " matches)";
                    String lbInfo = String.valueOf(data.lbAvg) + "(" + data.lbCross + " times in " + data.lbTimes + " matches)";
                    String scaleInfo = String.valueOf(data.scaleAvg) + " % (Out of " + data.scaleTimes + " matches)";
                    String failInfo = String.valueOf(data.failAvg) + " % (Out of " + data.scaleTimes + " matches)";
                    String nAInfo = String.valueOf(data.naAvg) + " % (Out of " + data.scaleTimes + " matches)";
                    String chalInfo = String.valueOf(data.chalAvg) + " % (Out of " + data.scaleTimes + " matches)";
                    //if there isn't any data to show
                    if (data.lowTimes == 0) {
                        low.setText("No data collected");
                    } else {
                        low.setText(lowInfo);
                    }
                    if (data.highTimes == 0) {
                        high.setText("No data collected");
                    } else {
                        high.setText(highInfo);
                    }
                    def.setText(defInfo);
                    if (data.pTimes == 0) {
                        portcullis.setText("No data collected");
                    } else {
                        portcullis.setText(pInfo);
                    }
                    if (data.cTimes == 0) {
                        cheval.setText("No data collected");
                    } else {
                        cheval.setText(cInfo);
                    }
                    if (data.mTimes == 0) {
                        moat.setText("No data collected");
                    } else {
                        moat.setText(mInfo);
                    }
                    if (data.rTimes == 0) {
                        ramparts.setText("No data collected");
                    } else {
                        ramparts.setText(rInfo);
                    }
                    if (data.dTimes == 0) {
                        drawbridge.setText("No data collected");
                    } else {
                        drawbridge.setText(dInfo);
                    }
                    if (data.spTimes == 0) {
                        sallyport.setText("No data collected");
                    } else {
                        sallyport.setText(spInfo);
                    }
                    if (data.rwTimes == 0) {
                        rockwall.setText("No data collected");
                    } else {
                        rockwall.setText(rwInfo);
                    }
                    if (data.rtTimes == 0) {
                        roughterrain.setText("No data collected");
                    } else {
                        roughterrain.setText(rtInfo);
                    }
                    if (data.lbCross == 0) {
                        lowbar.setText("No data collected");
                    } else {
                        lowbar.setText(lbInfo);
                    }
                    if (data.scaleTimes == 0) {
                        scale.setText("No data collected");
                        fail.setText("No data collected");
                        nA.setText("No data collected");
                        chal.setText("No data collected");
                    } else {
                        scale.setText(scaleInfo);
                        fail.setText(failInfo);
                        chal.setText(chalInfo);
                        nA.setText(nAInfo);
                    }
                    scoutNoData.setVisibility(View.GONE);
                    scoutLoad.setVisibility(View.GONE);
                    scoutData.setVisibility(View.VISIBLE);
                } else {
                    scoutData.setVisibility(View.GONE);
                    scoutLoad.setVisibility(View.GONE);
                    scoutNoData.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    /**
     * shows only the "NO DATA" text field for benchmarking
     */
    private void setNoDataBenchmarking() {
        benchmarkData.setVisibility(View.GONE);
        benchmarkLoad.setVisibility(View.GONE);
        benchmarkNoData.setVisibility(View.VISIBLE);
    }

    private class Data {
        public double highAvg;
        public int highComp;
        public int highAtt;
        public int highTimes;

        public double lowAvg;
        public int lowComp;
        public int lowAtt;
        public int lowTimes;

        public double bouldersAcqFloor;
        public int floorTimes;

        public double bouldersAcqHuman;
        public int humanTimes;

        public int pCross;
        public int pTimes;
        public double pAvg;

        public int cCross;
        public int cTimes;
        public double cAvg;

        public int mCross;
        public int mTimes;
        public double mAvg;

        public int rCross;
        public int rTimes;
        public double rAvg;

        public int dCross;
        public int dTimes;
        public double dAvg;

        public int spCross;
        public int spTimes;
        public double spAvg;

        public int rwCross;
        public int rwTimes;
        public double rwAvg;

        public int rtCross;
        public int rtTimes;
        public double rtAvg;

        public int lbCross;
        public int lbTimes;
        public double lbAvg;

        public int defTimes;
        public int defTotal;
        public double defAvg;

        public int scaleTimes;
        public double scaleAvg;
        public double chalAvg;
        public double failAvg;
        public double naAvg;


        public Data() {

            highAvg = 0;
            highTimes = 0;
            highComp = 0;
            highAtt = 0;
            lowAvg = 0;
            lowComp = 0;
            lowAtt = 0;
            lowTimes = 0;
            bouldersAcqFloor = 0;
            floorTimes = 0;
            bouldersAcqHuman = 0;
            humanTimes = 0;
            pCross = 0;
            pTimes = 0;
            pAvg = 0;
            cCross = 0;
            cTimes = 0;
            cAvg = 0;
            mCross = 0;
            mTimes = 0;
            mAvg = 0;
            rCross = 0;
            rTimes = 0;
            rAvg = 0;
            dCross = 0;
            dTimes = 0;
            dAvg = 0;
            spCross = 0;
            spTimes = 0;
            spAvg = 0;
            rwCross = 0;
            rwTimes = 0;
            rwTimes = 0;
            rtCross = 0;
            rtAvg = 0;
            rtTimes = 0;
            lbCross = 0;
            lbTimes = 0;
            lbAvg = 0;
            defTimes = 0;
            defTotal = 0;
            defAvg = 0;
            scaleTimes = 0;
            scaleAvg = 0;
            chalAvg = 0;
            failAvg = 0;
            naAvg = 0;


        }

        /**
         * computes the averages of data found in a scouting object
         * @return true if successful, false otherwise
         */
        public boolean computeAverages() {
            //can't do anything if there isn't a scouting object to read
            if (scoutList == null || scoutList.isEmpty()) {

                return false;
            } else {
                //gather data
                for (int i = 0; i < scoutList.size(); i++) {

                    Scouting sc = scoutList.get(i);
                    if (dbHelper.doesScoutingExist(sc)) {
                        ScoutingTele scout = sc.getTele();

                        highComp += scout.getHighGoalsScored();
                        highAtt += scout.getHighGoalAttempts();
                        highTimes++;

                                lowComp += scout.getLowGoalsScored();
                                lowAtt += scout.getLowGoalAttempts();

                            lowTimes++;
                        if (scout.getPlaysDefense()) {
                            defTotal++;
                        }
                        defTimes++;
                            pCross += scout.getPortcullisCrosses();
                            pTimes++;

                            cCross += scout.getPortcullisCrosses();
                            cTimes++;

                            mCross += scout.getMoatCrosses();
                            mTimes++;

                        rCross += scout.getRampartsCrosses();
                            rTimes++;

                        dCross += scout.getDrawbridgeCrosses();
                            dTimes++;

                        spCross += scout.getSallyportCrosses();
                            spTimes++;

                        rwCross += scout.getRockwallCrosses();
                            rwTimes++;

                        rtCross += scout.getRoughterrainCrosses();
                            rtTimes++;

                        lbCross += scout.getLowbarCrosses();
                            lbTimes++;

                        if (scout.getEndGameScale() != null) {
                            switch (scout.getEndGameScale()) {
                                case "no attempt":
                                    naAvg++;
                                    break;
                                case "failed":
                                    failAvg++;
                                    break;
                                case "challenged":
                                    chalAvg++;
                                    break;
                                case "scaled":
                                    scaleAvg++;
                            }
                            scaleTimes++;
                        }
                    }
                }
                //do the actual computations
                if (highAtt != 0) {
                        highAvg = (double) highComp / highAtt;
                        highAvg *= 100;
                        highAvg = Math.round(highAvg);
                    } else highAvg = 0;
                if (lowAtt != 0) {
                        lowAvg = (double) lowComp / lowAtt;
                        lowAvg *= 100;
                        lowAvg = Math.round(lowAvg);
                    } else lowAvg = 0;
                    if (defTotal != 0) {
                        defAvg = (double) defTotal / defTimes;
                        defAvg *= 100;
                        defAvg = Math.round(defAvg);
                    } else defAvg = 0;
                    if (pTimes != 0) {
                        pAvg = (double) pCross / pTimes;
                        pAvg = Math.round(pAvg);
                    }
                    if (cTimes != 0) {
                        cAvg = cCross / cTimes;
                        cAvg = Math.round(cAvg);
                    }
                    if (mTimes != 0) {
                        mAvg = mCross / mTimes;
                        mAvg = Math.round(mAvg);
                    }
                    if (rTimes != 0) {
                        rAvg = rCross / rTimes;
                        rAvg = Math.round(rAvg);
                    }
                    if (dTimes != 0) {
                        dAvg = dCross / dTimes;
                        dAvg = Math.round(dAvg);
                    }
                    if (spTimes != 0) {
                        spAvg = spCross / spTimes;
                        spAvg = Math.round(spAvg);
                    }
                    if (rwTimes != 0) {
                        rwAvg = rwCross / rwTimes;
                        rwAvg = Math.round(rwAvg);
                    }
                    if (rtTimes != 0) {
                        rtAvg = rtCross / rtTimes;
                        rtAvg = Math.round(rtAvg);
                    }
                    if (lbTimes != 0) {
                        lbAvg = lbCross / lbTimes;
                        lbAvg = Math.round(lbAvg);
                    }
                    if (scaleTimes != 0) {
                        scaleAvg /= scaleTimes;
                        scaleAvg *= 100;
                        scaleAvg = Math.round(scaleAvg);
                        failAvg /= scaleTimes;
                        failAvg *= 100;
                        failAvg = Math.round(failAvg);
                        naAvg /= scaleTimes;
                        naAvg *= 100;
                        naAvg = Math.round(naAvg);
                        chalAvg /= scaleTimes;
                        chalAvg *= 100;
                        chalAvg = Math.round(chalAvg);
                    }
                }
            return true;
        }
    }
}
