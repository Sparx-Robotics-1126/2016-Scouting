package org.gosparx.scouting.aerialassist.dto;

/**
 * Created by jbass on 3/1/14.
 */
public class ScoutingTele {

    private int ballsAcquiredFromFloor = -1;
    private int completedAssistsFromFloor = -1;
    private int ballsAcquiredFromHuman = -1;
    private int completedAssistsFromHuman = -1;
    private int shotHigh = -1;
    private int scoredHigh = -1;
    private int shotLow = -1;
    private int scoredLow = -1;
    private int ballsCaughtOverTruss = -1;
    private int ballsThrownOverTruss = -1;
    private String stayedInZone;

    public int getBallsAcquiredFromFloor() {
        return ballsAcquiredFromFloor;
    }

    public void setBallsAcquiredFromFloor(int ballsAcquiredFromFloor) {
        this.ballsAcquiredFromFloor = ballsAcquiredFromFloor;
    }

    public int getCompletedAssistsFromFloor() {
        return completedAssistsFromFloor;
    }

    public void setCompletedAssistsFromFloor(int completedAssistsFromFloor) {
        this.completedAssistsFromFloor = completedAssistsFromFloor;
    }

    public int getBallsAcquiredFromHuman() {
        return ballsAcquiredFromHuman;
    }

    public void setBallsAcquiredFromHuman(int ballsAcquiredFromHuman) {
        this.ballsAcquiredFromHuman = ballsAcquiredFromHuman;
    }

    public int getCompletedAssistsFromHuman() {
        return completedAssistsFromHuman;
    }

    public void setCompletedAssistsFromHuman(int completedAssistsFromHuman) {
        this.completedAssistsFromHuman = completedAssistsFromHuman;
    }

    public int getShotHigh() {
        return shotHigh;
    }

    public void setShotHigh(int shotHigh) {
        this.shotHigh = shotHigh;
    }

    public int getScoredHigh() {
        return scoredHigh;
    }

    public void setScoredHigh(int scoredHigh) {
        this.scoredHigh = scoredHigh;
    }

    public int getShotLow() {
        return shotLow;
    }

    public void setShotLow(int shotLow) {
        this.shotLow = shotLow;
    }

    public int getScoredLow() {
        return scoredLow;
    }

    public void setScoredLow(int scoredLow) {
        this.scoredLow = scoredLow;
    }

    public int getBallsCaughtOverTruss() {
        return ballsCaughtOverTruss;
    }

    public void setBallsCaughtOverTruss(int ballsCaughtOverTruss) {
        this.ballsCaughtOverTruss = ballsCaughtOverTruss;
    }

    public int getBallsThrownOverTruss() {
        return ballsThrownOverTruss;
    }

    public void setBallsThrownOverTruss(int ballsThrownOverTruss) {
        this.ballsThrownOverTruss = ballsThrownOverTruss;
    }

    public String getStayedInZone() {
        return stayedInZone;
    }

    public void setStayedInZone(String stayedInZone) {
        this.stayedInZone = stayedInZone;
    }
}
