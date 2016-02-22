package org.gosparx.scouting.aerialassist.dto;

/**
 * Created by jbass on 3/1/14.
 */
public class Scouting {

    private String nameOfScouter;

    private String eventKey;
    private String matchKey;
    private String teamKey;

    private ScoutingAuto auto;
    private ScoutingTele tele;
    private ScoutingGeneral general;

    public String getNameOfScouter() {
        return nameOfScouter;
    }

    public void setNameOfScouter(String nameOfScouter) {
        this.nameOfScouter = nameOfScouter;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public ScoutingAuto getAuto() {
        return auto;
    }

    public void setAuto(ScoutingAuto auto) {
        this.auto = auto;
    }

    public ScoutingTele getTele() {
        return tele;
    }

    public void setTele(ScoutingTele tele) {
        this.tele = tele;
    }

    public ScoutingGeneral getGeneral() {
        return general;
    }

    public void setGeneral(ScoutingGeneral general) {
        this.general = general;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Scouting)){
            return false;
        }
        Scouting scouting = (Scouting)obj;
        return (this.getEventKey().equals(scouting.getEventKey())
                && this.getMatchKey().equals(scouting.getMatchKey())
                && this.getNameOfScouter().equals(scouting.getNameOfScouter())
                && this.getTeamKey().equals(scouting.getTeamKey()));
    }
}
