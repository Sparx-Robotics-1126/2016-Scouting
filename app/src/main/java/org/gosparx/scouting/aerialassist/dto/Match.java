package org.gosparx.scouting.aerialassist.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jbass on 3/1/14.
 */
public class Match {

    private String key;
    @SerializedName("event_key")
    private String eventKey;
    @SerializedName("match_number")
    private int matchNumber;
    @SerializedName("set_number")
    private int setNumber;
    @SerializedName("comp_level")
    private String competitionLevel;
    private Alliances alliances;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Alliances getAlliances() {
        return alliances;
    }

    public void setAlliances(Alliances alliances) {
        this.alliances = alliances;
    }
}
