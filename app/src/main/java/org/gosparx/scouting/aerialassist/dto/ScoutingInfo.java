package org.gosparx.scouting.aerialassist.dto;

/**
 * Scouting information collected via team interview
 */
public class ScoutingInfo {

    private String driveSystemDescription = "unknown";
    private double approxSpeedFeetPerSecond = -1.0;
    // which defenses the robot can cross
    private Boolean canCrossPortcullis = false;
    private Boolean canCrossCheval = false;
    private Boolean canCrossMoat = false;
    private Boolean canCrossRamparts = false;
    private Boolean canCrossDrawbridge = false;
    private Boolean canCrossSallyport = false;
    private Boolean canCrossRockwall = false;
    private Boolean canCrossRoughterrain = false;
    private Boolean canCrossLowbar = false;
    // starting position in autonomous
    private Boolean autoStartInNeutralZone = false;
    private Boolean autoStartInSpyPosition = false;
    // what the robot can do in autonomous
    private String autoCapabilitiesDescription = "unknown";
    // where the robot can acquire boulders
    private Boolean acquiresBouldersFromFloor = false;
    private Boolean acquiresBouldersFromHumanPlayer = false;
    // boulder acquisition preference: "human" or "floor"
    private String preferredBoulderSource = "unknown";
    // whether or not boulders can be carried over defenses
    private Boolean canCarryBouldersOverDefenses = false;
    // whether or not goals can be scored
    private Boolean canScoreInHighGoal = false;
    private Boolean canScoreInLowGoal = false;
    private double averageHighGoalsPerMatch = -1.0;
    private double averageLowGoalsPerMatch = -1.0;
    // scaling ability
    private Boolean canScale = false;


    public String getDriveSystemDescription() {
        return driveSystemDescription;
    }

    public void setDriveSystemDescription(String driveSystemDescription) {
        this.driveSystemDescription = driveSystemDescription;
    }

    public double getApproxSpeedFeetPerSecond() {
        return approxSpeedFeetPerSecond;
    }

    public void setApproxSpeedFeetPerSecond(double approxSpeedFeetPerSecond) {
        this.approxSpeedFeetPerSecond = approxSpeedFeetPerSecond;
    }

    public Boolean getCanCrossPortcullis() {
        return canCrossPortcullis;
    }

    public void setCanCrossPortcullis(Boolean canCrossPortcullis) {
        this.canCrossPortcullis = canCrossPortcullis;
    }

    public Boolean getCanCrossCheval() {
        return canCrossCheval;
    }

    public void setCanCrossCheval(Boolean canCrossCheval) {
        this.canCrossCheval = canCrossCheval;
    }

    public Boolean getCanCrossMoat() {
        return canCrossMoat;
    }

    public void setCanCrossMoat(Boolean canCrossMoat) {
        this.canCrossMoat = canCrossMoat;
    }

    public Boolean getCanCrossRamparts() {
        return canCrossRamparts;
    }

    public void setCanCrossRamparts(Boolean canCrossRamparts) {
        this.canCrossRamparts = canCrossRamparts;
    }

    public Boolean getCanCrossDrawbridge() {
        return canCrossDrawbridge;
    }

    public void setCanCrossDrawbridge(Boolean canCrossDrawbridge) {
        this.canCrossDrawbridge = canCrossDrawbridge;
    }

    public Boolean getCanCrossSallyport() {
        return canCrossSallyport;
    }

    public void setCanCrossSallyport(Boolean canCrossSallyport) {
        this.canCrossSallyport = canCrossSallyport;
    }

    public Boolean getCanCrossRockwall() {
        return canCrossRockwall;
    }

    public void setCanCrossRockwall(Boolean canCrossRockwall) {
        this.canCrossRockwall = canCrossRockwall;
    }

    public Boolean getCanCrossRoughterrain() {
        return canCrossRoughterrain;
    }

    public void setCanCrossRoughterrain(Boolean canCrossRoughterrain) {
        this.canCrossRoughterrain = canCrossRoughterrain;
    }

    public Boolean getCanCrossLowbar() {
        return canCrossLowbar;
    }

    public void setCanCrossLowbar(Boolean canCrossLowbar) {
        this.canCrossLowbar = canCrossLowbar;
    }

    public Boolean getAutoStartInNeutralZone() {
        return autoStartInNeutralZone;
    }

    public void setAutoStartInNeutralZone(Boolean autoStartInNeutralZone) {
        this.autoStartInNeutralZone = autoStartInNeutralZone;
    }

    public Boolean getAutoStartInSpyPosition() {
        return autoStartInSpyPosition;
    }

    public void setAutoStartInSpyPosition(Boolean autoStartInSpyPosition) {
        this.autoStartInSpyPosition = autoStartInSpyPosition;
    }

    public String getAutoCapabilitiesDescription() {
        return autoCapabilitiesDescription;
    }

    public void setAutoCapabilitiesDescription(String autoCapabilitiesDescription) {
        this.autoCapabilitiesDescription = autoCapabilitiesDescription;
    }

    public Boolean getAcquiresBouldersFromFloor() {
        return acquiresBouldersFromFloor;
    }

    public void setAcquiresBouldersFromFloor(Boolean acquiresBouldersFromFloor) {
        this.acquiresBouldersFromFloor = acquiresBouldersFromFloor;
    }

    public Boolean getAcquiresBouldersFromHumanPlayer() {
        return acquiresBouldersFromHumanPlayer;
    }

    public void setAcquiresBouldersFromHumanPlayer(Boolean acquiresBouldersFromHumanPlayer) {
        this.acquiresBouldersFromHumanPlayer = acquiresBouldersFromHumanPlayer;
    }

    public String getPreferredBoulderSource() {
        return preferredBoulderSource;
    }

    public void setPreferredBoulderSource(String preferredBoulderSource) {
        this.preferredBoulderSource = preferredBoulderSource;
    }

    public Boolean getCanCarryBouldersOverDefenses() {
        return canCarryBouldersOverDefenses;
    }

    public void setCanCarryBouldersOverDefenses(Boolean canCarryBouldersOverDefenses) {
        this.canCarryBouldersOverDefenses = canCarryBouldersOverDefenses;
    }

    public Boolean getCanScoreInHighGoal() {
        return canScoreInHighGoal;
    }

    public void setCanScoreInHighGoal(Boolean canScoreInHighGoal) {
        this.canScoreInHighGoal = canScoreInHighGoal;
    }

    public Boolean getCanScoreInLowGoal() {
        return canScoreInLowGoal;
    }

    public void setCanScoreInLowGoal(Boolean canScoreInLowGoal) {
        this.canScoreInLowGoal = canScoreInLowGoal;
    }

    public double getAverageHighGoalsPerMatch() {
        return averageHighGoalsPerMatch;
    }

    public void setAverageHighGoalsPerMatch(double averageHighGoalsPerMatch) {
        this.averageHighGoalsPerMatch = averageHighGoalsPerMatch;
    }

    public double getAverageLowGoalsPerMatch() {
        return averageLowGoalsPerMatch;
    }

    public void setAverageLowGoalsPerMatch(double averageLowGoalsPerMatch) {
        this.averageLowGoalsPerMatch = averageLowGoalsPerMatch;
    }

    public Boolean getCanScale() {
        return canScale;
    }

    public void setCanScale(Boolean canScale) {
        this.canScale = canScale;
    }

}
