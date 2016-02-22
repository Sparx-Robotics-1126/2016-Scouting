package com.sparx1126.scoutingapp2016.fragments.MatchScouting;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.R;
import com.sparx1126.scoutingapp2016.controls.HorizontalNumberPicker;

import org.gosparx.scouting.aerialassist.dto.ScoutingTele;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeleFragment extends Fragment {

    private ScoutingTele st;
    private OnFragmentInteractionListener mListener;
    
    private ToggleButton playsDefenseTogglebutton;
    private HorizontalNumberPicker highGoalAttemptsNumberPicker;
    private HorizontalNumberPicker highGoalsScoredNumberPicker;
    private HorizontalNumberPicker lowGoalAttemptsNumberPicker;
    private HorizontalNumberPicker lowGoalsScoredNumberPicker;
    private HorizontalNumberPicker portcullisCrossingsPicker;
    private HorizontalNumberPicker chevalCrossingsPicker;
    private HorizontalNumberPicker moatCrossingsPicker;
    private HorizontalNumberPicker rampartsCrossingsPicker;
    private HorizontalNumberPicker drawbridgeCrossingsPicker;
    private HorizontalNumberPicker sallyportCrossingsPicker;
    private HorizontalNumberPicker rockwallCrossingsPicker;
    private HorizontalNumberPicker roughterrainCrossingsPicker;
    private HorizontalNumberPicker lowbarCrossingsPicker;
    private HorizontalNumberPicker bouldersPickedUpPicker;
    private HorizontalNumberPicker bouldersTakenToCourtyardPicker;
    private HorizontalNumberPicker bouldersFromBratticePicker;
    private Spinner endGameScaleSpinner;

    private Boolean wasCreated = false;

    public TeleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TeleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeleFragment newInstance(ScoutingTele st) {
        TeleFragment fragment = new TeleFragment();
        Bundle args = new Bundle();
        fragment.setScoutingTele(st);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_tele, container, false);

        // connect up to UI elements
        playsDefenseTogglebutton = (ToggleButton)result.findViewById(R.id.telePlaysDefense);
        highGoalAttemptsNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleHighGoalAttempts);
        highGoalsScoredNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleHighGoalsScored);
        lowGoalAttemptsNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleLowGoalAttempts);
        lowGoalsScoredNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleLowGoalsScored);
        portcullisCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.telePortcullisCrossings);
        chevalCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleChevalCrossings);
        moatCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleMoatCrossings);
        rampartsCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleRampartCrossings);
        drawbridgeCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleDrawbridgeCrossings);
        sallyportCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleSallyPortCrossings);
        rockwallCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleRockWallCrossings);
        roughterrainCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleRoughTerrainCrossings);
        lowbarCrossingsPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleLowBarCrossings);
        bouldersFromBratticePicker = (HorizontalNumberPicker)result.findViewById(R.id.teleBouldersReceivedFromBrattice);
        bouldersPickedUpPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleBouldersPickedUp);
        bouldersTakenToCourtyardPicker = (HorizontalNumberPicker)result.findViewById(R.id.teleBouldersTakenToCourtyard);
        endGameScaleSpinner = (Spinner)result.findViewById(R.id.teleEndGameScale);

        if(st != null){
            playsDefenseTogglebutton.setChecked(st.getPlaysDefense());
            highGoalAttemptsNumberPicker.setValue(st.getHighGoalAttempts());
            highGoalsScoredNumberPicker.setValue(st.getHighGoalsScored());
            lowGoalAttemptsNumberPicker.setValue(st.getLowGoalAttempts());
            lowGoalsScoredNumberPicker.setValue(st.getLowGoalsScored());
            portcullisCrossingsPicker.setValue(st.getPortcullisCrosses());
            chevalCrossingsPicker.setValue(st.getChevalCrosses());
            moatCrossingsPicker.setValue(st.getMoatCrosses());
            rampartsCrossingsPicker.setValue(st.getRampartsCrosses());
            drawbridgeCrossingsPicker.setValue(st.getDrawbridgeCrosses());
            sallyportCrossingsPicker.setValue(st.getSallyportCrosses());
            rockwallCrossingsPicker.setValue(st.getRockwallCrosses());
            roughterrainCrossingsPicker.setValue(st.getRoughterrainCrosses());
            lowbarCrossingsPicker.setValue(st.getLowbarCrosses());
            bouldersFromBratticePicker.setValue(st.getBouldersReceivedFromBrattice());
            bouldersPickedUpPicker.setValue(st.getBouldersPickedUp());
            bouldersTakenToCourtyardPicker.setValue(st.getBouldersTakenToCourtyard());
            endGameScaleSpinner.setSelection(indexOfEndGameScaleValue(st.getEndGameScale()));
        }
        wasCreated = true;

        return result;
    }

    private int indexOfEndGameScaleValue(String endingPosition)
    {
        String[] endingPositionValues = getResources().getStringArray(R.array.endgamescale_choices);
        int result = findIndexOfStringInArray(endingPosition, endingPositionValues);
        return result;
    }

    private int findIndexOfStringInArray(String strValue, String[] stringArray)
    {
        int result = -1;
        for (int i=0; i<stringArray.length; i++)
            if (strValue.equals(stringArray[i]))
            {
                result = i;
                break;
            }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(st != null){
            playsDefenseTogglebutton.setChecked(st.getPlaysDefense());
            highGoalAttemptsNumberPicker.setValue(st.getHighGoalAttempts());
            highGoalsScoredNumberPicker.setValue(st.getHighGoalsScored());
            lowGoalAttemptsNumberPicker.setValue(st.getLowGoalAttempts());
            lowGoalsScoredNumberPicker.setValue(st.getLowGoalsScored());
            portcullisCrossingsPicker.setValue(st.getPortcullisCrosses());
            chevalCrossingsPicker.setValue(st.getChevalCrosses());
            moatCrossingsPicker.setValue(st.getMoatCrosses());
            rampartsCrossingsPicker.setValue(st.getRampartsCrosses());
            drawbridgeCrossingsPicker.setValue(st.getDrawbridgeCrosses());
            sallyportCrossingsPicker.setValue(st.getSallyportCrosses());
            rockwallCrossingsPicker.setValue(st.getRockwallCrosses());
            roughterrainCrossingsPicker.setValue(st.getRoughterrainCrosses());
            lowbarCrossingsPicker.setValue(st.getLowbarCrosses());
            bouldersFromBratticePicker.setValue(st.getBouldersReceivedFromBrattice());
            bouldersPickedUpPicker.setValue(st.getBouldersPickedUp());
            bouldersTakenToCourtyardPicker.setValue(st.getBouldersTakenToCourtyard());
            endGameScaleSpinner.setSelection(indexOfEndGameScaleValue(st.getEndGameScale()));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ScoutingTele getScoutingTele(){
        if(st == null)
            st = new ScoutingTele();

        if (wasCreated)
        {
            st.setPlaysDefense(playsDefenseTogglebutton.isChecked());

            st.setHighGoalAttempts(highGoalAttemptsNumberPicker.getValue());
            st.setHighGoalsScored(highGoalsScoredNumberPicker.getValue());
            st.setLowGoalAttempts(lowGoalAttemptsNumberPicker.getValue());
            st.setLowGoalsScored(lowGoalsScoredNumberPicker.getValue());

            st.setPortcullisCrosses(portcullisCrossingsPicker.getValue());
            st.setChevalCrosses(chevalCrossingsPicker.getValue());
            st.setMoatCrosses(moatCrossingsPicker.getValue());
            st.setRampartsCrosses(rampartsCrossingsPicker.getValue());
            st.setDrawbridgeCrosses(drawbridgeCrossingsPicker.getValue());
            st.setSallyportCrosses(sallyportCrossingsPicker.getValue());
            st.setRockwallCrosses(rockwallCrossingsPicker.getValue());
            st.setRoughterrainCrosses(roughterrainCrossingsPicker.getValue());
            st.setLowbarCrosses(lowbarCrossingsPicker.getValue());

            st.setBouldersPickedUp(bouldersPickedUpPicker.getValue());
            st.setBouldersTakenToCourtyard(bouldersTakenToCourtyardPicker.getValue());
            st.setBouldersReceivedFromBrattice(bouldersFromBratticePicker.getValue());
            st.setEndGameScale(endGameScaleSpinner.getSelectedItem().toString());
        }

        return st;
    }

    public void setScoutingTele(ScoutingTele st) {
        this.st = st;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
