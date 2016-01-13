package com.sparx1126.scoutingapp2016;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Dan Martin
 * provides the info for events `
 */
public class EventInfo {
    /**
     * gets event names from a source
     * @return an array that contains all event names (or event data, not sure yet)
     */
    public static ArrayList<String> getEventNames(){
        /**
         * TODO: read code in from file
         */

        /**
         * contains names of events
         */
        ArrayList<String> eventNames = new ArrayList<String>() {
        };
        eventNames.add("Event1");
        eventNames.add("Event2");
        eventNames.add("Event3");

        return eventNames;
    }
}
