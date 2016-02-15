package org.gosparx.scouting.aerialassist.dto;

/*
** Information to collect during Autonomous
 */
public class ScoutingTele {

    // goal attempts and successes
    private int highGoalAttempts = -1;
    private int highGoalsScored = -1;
    private int lowGoalAttempts = -1;
    private int lowGoalsScored = -1;
    // count of defense crosses
    private int portcullisCrosses = -1;
    private int chevalCrosses = -1;
    private int moatCrosses = -1;
    private int rampartsCrosses = -1;
    private int drawbridgeCrosses = -1;
    private int sallyportCrosses = -1;
    private int rockwallCrosses = -1;
    private int roughterrainCrosses = -1;
    private int lowbarCrosses = -1;
    // whether or not this robot plays defense
    private Boolean playsDefense = false;
    // number of boulders picked up from floor
    private int bouldersPickedUp = 0;
    // number of boulders taken to opponent courtyard
    private int bouldersTakenToCourtyard = 0;
    // number of boulders received from brattice
    private int bouldersReceivedFromBrattice = 0;

    // what the robot accomplished during the end game
    private String endGameScale = "no attempt";

    public int getHighGoalAttempts() {
        return highGoalAttempts;
    }

    public void setHighGoalAttempts(int highGoalAttempts) {
        this.highGoalAttempts = highGoalAttempts;
    }

    public int getHighGoalsScored() {
        return highGoalsScored;
    }

    public void setHighGoalsScored(int highGoalsScored) {
        this.highGoalsScored = highGoalsScored;
    }

    public int getLowGoalAttempts() {
        return lowGoalAttempts;
    }

    public void setLowGoalAttempts(int lowGoalAttempts) {
        this.lowGoalAttempts = lowGoalAttempts;
    }

    public int getLowGoalsScored() {
        return lowGoalsScored;
    }

    public void setLowGoalsScored(int lowGoalsScored) {
        this.lowGoalsScored = lowGoalsScored;
    }

    public int getPortcullisCrosses() {
        return portcullisCrosses;
    }

    public void setPortcullisCrosses(int portcullisCrosses) {
        this.portcullisCrosses = portcullisCrosses;
    }

    public int getChevalCrosses() {
        return chevalCrosses;
    }

    public void setChevalCrosses(int chevalCrosses) {
        this.chevalCrosses = chevalCrosses;
    }

    public int getMoatCrosses() {
        return moatCrosses;
    }

    public void setMoatCrosses(int moatCrosses) {
        this.moatCrosses = moatCrosses;
    }

    public int getRampartsCrosses() {
        return rampartsCrosses;
    }

    public void setRampartsCrosses(int rampartsCrosses) {
        this.rampartsCrosses = rampartsCrosses;
    }

    public int getDrawbridgeCrosses() {
        return drawbridgeCrosses;
    }

    public void setDrawbridgeCrosses(int drawbridgeCrosses) {
        this.drawbridgeCrosses = drawbridgeCrosses;
    }

    public int getSallyportCrosses() {
        return sallyportCrosses;
    }

    public void setSallyportCrosses(int sallyportCrosses) {
        this.sallyportCrosses = sallyportCrosses;
    }

    public int getRockwallCrosses() {
        return rockwallCrosses;
    }

    public void setRockwallCrosses(int rockwallCrosses) {
        this.rockwallCrosses = rockwallCrosses;
    }

    public int getRoughterrainCrosses() {
        return roughterrainCrosses;
    }

    public void setRoughterrainCrosses(int roughterrainCrosses) {
        this.roughterrainCrosses = roughterrainCrosses;
    }

    public int getLowbarCrosses() {
        return lowbarCrosses;
    }

    public void setLowbarCrosses(int lowbarCrosses) {
        this.lowbarCrosses = lowbarCrosses;
    }

    public Boolean getPlaysDefense() {
        return playsDefense;
    }

    public void setPlaysDefense(Boolean playsDefense) {
        this.playsDefense = playsDefense;
    }

    public int getBouldersPickedUp() {
        return bouldersPickedUp;
    }

    public void setBouldersPickedUp(int bouldersPickedUp) {
        this.bouldersPickedUp = bouldersPickedUp;
    }

    public int getBouldersTakenToCourtyard() {
        return bouldersTakenToCourtyard;
    }

    public void setBouldersTakenToCourtyard(int bouldersTakenToCourtyard) {
        this.bouldersTakenToCourtyard = bouldersTakenToCourtyard;
    }

    public int getBouldersReceivedFromBrattice() {
        return bouldersReceivedFromBrattice;
    }

    public void setBouldersReceivedFromBrattice(int bouldersReceivedFromBrattice) {
        this.bouldersReceivedFromBrattice = bouldersReceivedFromBrattice;
    }

    public String getEndGameScale() {
        return endGameScale;
    }

    public void setEndGameScale(String endGameScale) {
        this.endGameScale = endGameScale;
    }

}
