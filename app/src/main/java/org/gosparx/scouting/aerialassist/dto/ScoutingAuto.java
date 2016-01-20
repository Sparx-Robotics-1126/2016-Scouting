package org.gosparx.scouting.aerialassist.dto;

/*
** Information to collect during Autonomous
 */
public class ScoutingAuto {

    // positions of the various defenses
    // note: lowbar is always in position 1 and so has no variable
    // 0 = not present on field; 2 - 5 = position number
    private int portcullisPosition = 0;
    private int chevalPosition = 0;
    private int moatPosition = 0;
    private int rampartsPosition = 0;
    private int drawbridgePosition = 0;
    private int sallyportPosition = 0;
    private int rockwallPosition = 0;
    private int roughterrainPosition = 0;
    // whether or not each defense was crossed
    private Boolean portcullisCrossed = false;
    private Boolean chevalCrossed = false;
    private Boolean moatCrossed = false;
    private Boolean rampartsCrossed = false;
    private Boolean drawbridgeCrossed = false;
    private Boolean sallyportCrossed = false;
    private Boolean rockwallCrossed = false;
    private Boolean roughterrainCrossed = false;
    private Boolean lowbarCrossed = false;
    // whether or not the robot picked up a boulder
    private Boolean boudlerPickedUp = false;
    // whether or not the robot scored in the hight goal
    private Boolean robotScoredHigh = false;
    // whether or not the robot scored in the low goal
    private Boolean robotScoredLow = false;
    // which zone the robot ended in (courtyard/neutral)
    private String endingPosition = "unknown";
    // whether or not the robot achieived a reach
    private Boolean reachAchieved = false;
    // whether or not a reach was part of a cross attempt
    private Boolean reachWasCrossAttempt = false;
    // whether or not the robot started in the spy position
    private Boolean startedAsSpy = false;

    public int getPortcullisPosition() {
        return portcullisPosition;
    }

    public void setPortcullisPosition(int portcullisPosition) {
        this.portcullisPosition = portcullisPosition;
    }

    public int getChevalPosition() {
        return chevalPosition;
    }

    public void setChevalPosition(int chevalPosition) {
        this.chevalPosition = chevalPosition;
    }

    public int getMoatPosition() {
        return moatPosition;
    }

    public void setMoatPosition(int moatPosition) {
        this.moatPosition = moatPosition;
    }

    public int getRampartsPosition() {
        return rampartsPosition;
    }

    public void setRampartsPosition(int rampartsPosition) {
        this.rampartsPosition = rampartsPosition;
    }

    public int getDrawbridgePosition() {
        return drawbridgePosition;
    }

    public void setDrawbridgePosition(int drawbridgePosition) {
        this.drawbridgePosition = drawbridgePosition;
    }

    public int getSallyportPosition() {
        return sallyportPosition;
    }

    public void setSallyportPosition(int sallyportPosition) {
        this.sallyportPosition = sallyportPosition;
    }

    public int getRockwallPosition() {
        return rockwallPosition;
    }

    public void setRockwallPosition(int rockwallPosition) {
        this.rockwallPosition = rockwallPosition;
    }

    public int getRoughterrainPosition() {
        return roughterrainPosition;
    }

    public void setRoughterrainPosition(int roughterrainPosition) {
        this.roughterrainPosition = roughterrainPosition;
    }

    public Boolean getPortcullisCrossed() {
        return portcullisCrossed;
    }

    public void setPortcullisCrossed(Boolean portcullisCrossed) {
        this.portcullisCrossed = portcullisCrossed;
    }

    public Boolean getChevalCrossed() {
        return chevalCrossed;
    }

    public void setChevalCrossed(Boolean chevalCrossed) {
        this.chevalCrossed = chevalCrossed;
    }

    public Boolean getMoatCrossed() {
        return moatCrossed;
    }

    public void setMoatCrossed(Boolean moatCrossed) {
        this.moatCrossed = moatCrossed;
    }

    public Boolean getRampartsCrossed() {
        return rampartsCrossed;
    }

    public void setRampartsCrossed(Boolean rampartsCrossed) {
        this.rampartsCrossed = rampartsCrossed;
    }

    public Boolean getDrawbridgeCrossed() {
        return drawbridgeCrossed;
    }

    public void setDrawbridgeCrossed(Boolean drawbridgeCrossed) {
        this.drawbridgeCrossed = drawbridgeCrossed;
    }

    public Boolean getSallyportCrossed() {
        return sallyportCrossed;
    }

    public void setSallyportCrossed(Boolean sallyportCrossed) {
        this.sallyportCrossed = sallyportCrossed;
    }

    public Boolean getRockwallCrossed() {
        return rockwallCrossed;
    }

    public void setRockwallCrossed(Boolean rockwallCrossed) {
        this.rockwallCrossed = rockwallCrossed;
    }

    public Boolean getRoughterrainCrossed() {
        return roughterrainCrossed;
    }

    public void setRoughterrainCrossed(Boolean roughterrainCrossed) {
        this.roughterrainCrossed = roughterrainCrossed;
    }

    public Boolean getLowbarCrossed() {
        return lowbarCrossed;
    }

    public void setLowbarCrossed(Boolean lowbarCrossed) {
        this.lowbarCrossed = lowbarCrossed;
    }

    public Boolean getBoudlerPickedUp() {
        return boudlerPickedUp;
    }

    public void setBoudlerPickedUp(Boolean boudlerPickedUp) {
        this.boudlerPickedUp = boudlerPickedUp;
    }

    public Boolean getRobotScoredHigh() {
        return robotScoredHigh;
    }

    public void setRobotScoredHigh(Boolean robotScoredHigh) {
        this.robotScoredHigh = robotScoredHigh;
    }

    public Boolean getRobotScoredLow() {
        return robotScoredLow;
    }

    public void setRobotScoredLow(Boolean robotScoredLow) {
        this.robotScoredLow = robotScoredLow;
    }

    public String getEndingPosition() {
        return endingPosition;
    }

    public void setEndingPosition(String endingPosition) {
        this.endingPosition = endingPosition;
    }

    public Boolean getReachAchieved() {
        return reachAchieved;
    }

    public void setReachAchieved(Boolean reachAchieved) {
        this.reachAchieved = reachAchieved;
    }

    public Boolean getReachWasCrossAttempt() {
        return reachWasCrossAttempt;
    }

    public void setReachWasCrossAttempt(Boolean reachWasCrossAttempt) {
        this.reachWasCrossAttempt = reachWasCrossAttempt;
    }

    public Boolean getStartedAsSpy() {
        return startedAsSpy;
    }

    public void setStartedAsSpy(Boolean startedAsSpy) {
        this.startedAsSpy = startedAsSpy;
    }

    public Boolean getStartedWithBoulder() {
        return startedWithBoulder;
    }

    public void setStartedWithBoulder(Boolean startedWithBoulder) {
        this.startedWithBoulder = startedWithBoulder;
    }

    // whether or not the robot started with a boulder
    private Boolean startedWithBoulder = false;

}
