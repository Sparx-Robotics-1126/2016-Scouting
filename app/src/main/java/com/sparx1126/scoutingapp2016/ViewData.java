package com.sparx1126.scoutingapp2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ViewData extends AppCompatActivity {
    private static List<Scouting> scoutList;
    private static List<ScoutingInfo> benchmarkList;
    private String name;
    private DatabaseHelper dbHelper;
    private TextView low, high, scale, def, portcullis, cheval, moat, ramparts, drawbridge, sallyport,
            rockwall, roughterrain, lowbar, highAble, lowAble, boulderSource, portCross, chevCross,
            moatCross, ramCross, drawCross, salCross, rockCross, roughCross, lowCross;
    private LinearLayout benchmarkData;
    private TextView benchmarkNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i = getIntent();

        //match fields

        low = (TextView) findViewById(R.id.lowAverage);
        high = (TextView) findViewById(R.id.highAverage);
        scale = (TextView) findViewById(R.id.scaleAverage);
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

        //benchmarking fields

        benchmarkData = (LinearLayout) findViewById(R.id.benchmarkData);
        benchmarkNoData = (TextView) findViewById(R.id.benchmarkNoData);
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

        benchmarkNoData.setVisibility(View.GONE);

        final String teamKey = i.getStringExtra(MainMenu.TEAM_NAME);
        final String eventKey = i.getStringExtra(MainMenu.EVENT_KEY);
        String teamName = teamKey.replace("frc", ""); // remove frc from the key to only show the team number

        name = i.getStringExtra(MainMenu.NAME);

        toolbar.setTitle("Viewing data for Team " + teamName);
        SparxScouting s = SparxScouting.getInstance(this);


        dbHelper = DatabaseHelper.getInstance(this);
        scoutList = dbHelper.getScouting(eventKey, teamKey);
        //TODO find a way to get scouting data from online database without causing errors
        final ScoutingInfo in = new ScoutingInfo();
        in.setEventKey(eventKey);
        in.setTeamKey(teamKey);
        in.setNameOfScouter(name);
        if (dbHelper.doesBenchmarkingExist(in)) {
            benchmarkList = dbHelper.getBenchmarking(eventKey, teamKey);
            initFromBenchmarkList(benchmarkList);
        } else {
            s.getBenchmarking(dbHelper.getTeam(teamKey), dbHelper.getEvent(eventKey), new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    ViewData.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {

                                if (dbHelper.doesBenchmarkingExist(in)) {
                                    benchmarkList = dbHelper.getBenchmarking(eventKey, teamKey);

                                }
                            }
                            initFromBenchmarkList(benchmarkList);
                        }
                    });
                }
            });
        }
    }

    private void setYesNo(TextView text, boolean b) {
        if (b) {
            text.setText("Yes");
        } else text.setText("No");
    }

    private void initFromBenchmarkList(List<ScoutingInfo> benckmarkList) {
        Data data = new Data();
        try {
            ScoutingInfo info = benchmarkList.get(0);
            benchmarkData.setVisibility(View.VISIBLE);
            benchmarkNoData.setVisibility(View.GONE);
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

        data.computeAverages();
        //TODO get scaling info from tele fragment
        String lowInfo = String.valueOf((int) data.lowAvg) + " % (" + data.lowComp + " out of " + data.lowAtt + " attempts)";
        String highInfo = String.valueOf((int) data.highAvg) + " % (" + data.highComp + " out of " + data.highAtt + " attempts)";
        String defInfo = String.valueOf(data.defAvg) + " % (" + data.defTotal + " out of " + data.defTimes + " matches)";
        String pInfo = String.valueOf(data.pAvg) + "(" + data.pCross + " times in " + data.pTimes + " matches)";
        String cInfo = String.valueOf(data.cAvg) + "(" + data.cCross + " times in " + data.cTimes + " matches)";
        String mInfo = String.valueOf(data.mAvg) + "(" + data.mCross + " times in " + data.mTimes + " matches)";
        String rInfo = String.valueOf(data.rAvg) + "(" + data.rCross + " times  in " + data.rTimes + " matches)";
        String dInfo = String.valueOf(data.dAvg) + "(" + data.dCross + " times in " + data.dTimes + " matches)";
        String spInfo = String.valueOf(data.spAvg) + "(" + data.spCross + " times in " + data.spTimes + " matches)";
        String rwInfo = String.valueOf(data.rwAvg) + "(" + data.rwCross + " times in " + data.rwTimes + " matches)";
        String rtInfo = String.valueOf(data.rtAvg) + "(" + data.rtCross + " times in " + data.rtTimes + " matches)";
        String lbInfo = String.valueOf(data.lbAvg) + "(" + data.lbCross + " times in " + data.lbTimes + " matches)";

        low.setText(lowInfo);
        high.setText(highInfo);
        def.setText(defInfo);
        portcullis.setText(pInfo);
        cheval.setText(cInfo);
        moat.setText(mInfo);
        ramparts.setText(rInfo);
        drawbridge.setText(dInfo);
        sallyport.setText(spInfo);
        rockwall.setText(rwInfo);
        roughterrain.setText(rtInfo);
        lowbar.setText(lbInfo);

    }

    private void setNoDataBenchmarking() {
        benchmarkData.setVisibility(View.GONE);
        benchmarkNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            scoutList = null;
            benchmarkList = null;
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
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

        public boolean foundScouting;

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
            defAvg = 0;
            foundScouting = false;

        }

        public void computeAverages() {

            for (int i = 0; i < scoutList.size(); i++) {

                Scouting sc = scoutList.get(i);
                if (dbHelper.doesScoutingExist(sc)) {
                    foundScouting = true;
                    ScoutingTele scout = sc.getTele();

                    if (scout.getHighGoalAttempts() != -1) {
                        if (scout.getHighGoalAttempts() != 0) {
                            highComp += scout.getHighGoalsScored();
                            highAtt += scout.getHighGoalAttempts();
                            highAvg += (double) highComp / highAtt;
                        }
                        highTimes++;
                    }

                    if (scout.getLowGoalAttempts() != -1) {

                        if (scout.getLowGoalAttempts() != 0) {
                            lowComp += scout.getLowGoalsScored();
                            lowAtt += scout.getLowGoalAttempts();
                            lowAvg += (double) lowComp / lowAtt;

                        }
                        lowTimes++;
                    }
                    if (scout.getPlaysDefense()) {
                        defTotal++;
                    }
                    defTimes++;
                    if (scout.getPortcullisCrosses() != -1) {
                        pCross += scout.getPortcullisCrosses();
                        pTimes++;
                    }
                    if (scout.getChevalCrosses() != -1) {
                        cCross += scout.getPortcullisCrosses();
                        cTimes++;
                    }
                    if (scout.getMoatCrosses() != -1) {
                        mCross += scout.getMoatCrosses();
                        mTimes++;
                    }
                    if (scout.getRampartsCrosses() != -1) {
                        rCross += scout.getRampartsCrosses();
                        rTimes++;
                    }
                    if (scout.getDrawbridgeCrosses() != -1) {
                        dCross += scout.getDrawbridgeCrosses();
                        dTimes++;
                    }
                    if (scout.getSallyportCrosses() != -1) {
                        spCross += scout.getSallyportCrosses();
                        spTimes++;
                    }
                    if (scout.getRockwallCrosses() != -1) {
                        rwCross += scout.getRockwallCrosses();
                        rwTimes++;
                    }
                    if (scout.getRoughterrainCrosses() != -1) {
                        rtCross += scout.getRoughterrainCrosses();
                        rtTimes++;
                    }
                    if (scout.getLowbarCrosses() != -1) {
                        lbCross += scout.getLowbarCrosses();
                        lbTimes++;
                    }
                }
                if (highTimes != 0) {
                    highAvg /= highTimes;
                    highAvg *= 100;
                    highAvg = Math.round(highAvg);
                } else highAvg = 0;
                if (lowTimes != 0) {
                    lowAvg /= lowTimes;
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
            }

        }
    }
}
