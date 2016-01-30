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

import org.gosparx.scouting.aerialassist.dto.ScoutingAuto;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ScoutingAuto sa;

    // controls on the screen
    private Spinner portcullisPositionSpinner;
    private Spinner chevalPositionSpinner;
    private Spinner moatPositionSpinner;
    private Spinner rampartsPositionSpinner;
    private Spinner drawbridgePositionSpinner;
    private Spinner sallyportPositionSpinner;
    private Spinner rockwallPositionSpinner;
    private Spinner roughterrainPositionSpinner;
    private ToggleButton portcullisPositionToggleButton;
    private ToggleButton chevalPositionToggleButton;
    private ToggleButton moatPositionToggleButton;
    private ToggleButton rampartsPositionToggleButton;
    private ToggleButton drawbridgePositionToggleButton;
    private ToggleButton sallyportPositionToggleButton;
    private ToggleButton rockwallPositionToggleButton;
    private ToggleButton roughterrainPositionToggleButton;
    private ToggleButton lowbarPositionToggleButton;

    private ToggleButton pickedupboulderToggleButton;
    private ToggleButton scoredinhighgoalToggleButton;
    private ToggleButton scoredinlowgoalToggleButton;
    private Spinner endingPositionSpinner;
    private ToggleButton reachachievedToggleButton;
    private ToggleButton reachwascrossattemptToggleButton;
    private ToggleButton startedasspyToggleButton;
    private ToggleButton startedwithboulderToggleButton;

    private Boolean wasCreated = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AutoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutoFragment newInstance(ScoutingAuto sa) {
        AutoFragment fragment = new AutoFragment();
        Bundle args = new Bundle();
        fragment.setScoutingAuto(sa);
        fragment.setArguments(args);
        return fragment;
    }

    public AutoFragment() {
        // Required empty public constructor
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
        View result = inflater.inflate(R.layout.fragment_auto, container, false);

        // connect up to UI elements
        portcullisPositionSpinner = (Spinner)result.findViewById(R.id.auto_portcullis_position);
        chevalPositionSpinner = (Spinner)result.findViewById(R.id.auto_cheval_position);
        moatPositionSpinner = (Spinner)result.findViewById(R.id.auto_moat_position);
        rampartsPositionSpinner = (Spinner)result.findViewById(R.id.auto_ramparts_position);
        drawbridgePositionSpinner = (Spinner)result.findViewById(R.id.auto_drawbridge_position);
        sallyportPositionSpinner = (Spinner)result.findViewById(R.id.auto_sallyport_position);
        rockwallPositionSpinner = (Spinner)result.findViewById(R.id.auto_rockwall_position);
        roughterrainPositionSpinner = (Spinner)result.findViewById(R.id.auto_roughterrain_position);
        portcullisPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_portcullis_crossed);
        chevalPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_cheval_crossed);
        moatPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_moat_crossed);
        rampartsPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_ramparts_crossed);
        drawbridgePositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_drawbridge_crossed);
        sallyportPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_sallyport_crossed);
        rockwallPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_rockwall_crossed);
        roughterrainPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_rouchterrain_crossed);
        lowbarPositionToggleButton = (ToggleButton)result.findViewById(R.id.auto_lowbar_crossed);

        pickedupboulderToggleButton = (ToggleButton)result.findViewById(R.id.autoPickedUpBoulder);
        scoredinhighgoalToggleButton = (ToggleButton)result.findViewById(R.id.autoScoredInHighGoal);
        scoredinlowgoalToggleButton = (ToggleButton)result.findViewById(R.id.autoScoredInLowGoal);
        endingPositionSpinner = (Spinner)result.findViewById(R.id.autoEndingPosition);
        reachachievedToggleButton = (ToggleButton)result.findViewById(R.id.autoReachAchieved);
        reachwascrossattemptToggleButton = (ToggleButton)result.findViewById(R.id.autoReachWasCrossAttempt);
        startedasspyToggleButton = (ToggleButton)result.findViewById(R.id.autoStartedAsSpy);
        startedwithboulderToggleButton = (ToggleButton)result.findViewById(R.id.autoStartedWithBoulder);

        wasCreated = true;

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(sa != null){
            portcullisPositionToggleButton.setChecked(sa.getPortcullisCrossed());
            portcullisPositionSpinner.setSelection(indexOfPositionValue(sa.getPortcullisPosition()));
            chevalPositionSpinner.setSelection(indexOfPositionValue(sa.getChevalPosition()));
            moatPositionSpinner.setSelection(indexOfPositionValue(sa.getMoatPosition()));
            rampartsPositionSpinner.setSelection(indexOfPositionValue(sa.getRampartsPosition()));
            drawbridgePositionSpinner.setSelection(indexOfPositionValue(sa.getDrawbridgePosition()));
            sallyportPositionSpinner.setSelection(indexOfPositionValue(sa.getSallyportPosition()));
            rockwallPositionSpinner.setSelection(indexOfPositionValue(sa.getRockwallPosition()));
            roughterrainPositionSpinner.setSelection(indexOfPositionValue(sa.getRoughterrainPosition()));
            portcullisPositionToggleButton.setChecked(sa.getPortcullisCrossed());
            chevalPositionToggleButton.setChecked(sa.getChevalCrossed());
            moatPositionToggleButton.setChecked(sa.getMoatCrossed());
            rampartsPositionToggleButton.setChecked(sa.getRampartsCrossed());
            drawbridgePositionToggleButton.setChecked(sa.getDrawbridgeCrossed());
            sallyportPositionToggleButton.setChecked(sa.getSallyportCrossed());
            rockwallPositionToggleButton.setChecked(sa.getRockwallCrossed());
            roughterrainPositionToggleButton.setChecked(sa.getRoughterrainCrossed());
            lowbarPositionToggleButton.setChecked(sa.getLowbarCrossed());

            pickedupboulderToggleButton.setChecked(sa.getBoudlerPickedUp());
            scoredinhighgoalToggleButton.setChecked(sa.getRobotScoredHigh());
            scoredinlowgoalToggleButton.setChecked(sa.getRobotScoredLow());
            endingPositionSpinner.setSelection(indexOfEndingPositionValue(sa.getEndingPosition()));
            reachachievedToggleButton.setChecked(sa.getReachAchieved());
            reachwascrossattemptToggleButton.setChecked(sa.getReachWasCrossAttempt());
            startedasspyToggleButton.setChecked(sa.getStartedAsSpy());
            startedwithboulderToggleButton.setChecked(sa.getStartedWithBoulder());
        }
    }

    private int indexOfPositionValue(int positionValue)
    {
        String positionString = "";
        if (positionValue == 0)
            positionString = "Not Present";
        else
            positionString = Integer.toString(positionValue);
        String[] positionValues = getResources().getStringArray(R.array.defense_positions);
        int result = findIndexOfStringInArray(positionString, positionValues);
        return result;
    }

    private int indexOfEndingPositionValue(String endingPosition)
    {
        String[] endingPositionValues = getResources().getStringArray(R.array.ending_positions);
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
        public void onFragmentInteraction(Uri uri);
    }

    public void setScoutingAuto(ScoutingAuto sa){
        this.sa = sa;
    }

    private int positionValueFromSpinnerString(String positionString)
    {
        int result = 0;
        if (positionString.equals("Not Present"))
            result = 0;
        else
            result = Integer.valueOf(positionString);
        return result;
    }

    public ScoutingAuto getScoutingAuto(){
        if(sa == null)
            sa = new ScoutingAuto();

        if (wasCreated)
        {
            sa.setPortcullisPosition(positionValueFromSpinnerString(portcullisPositionSpinner.getSelectedItem().toString()));
            sa.setChevalPosition(positionValueFromSpinnerString(chevalPositionSpinner.getSelectedItem().toString()));
            sa.setMoatPosition(positionValueFromSpinnerString(moatPositionSpinner.getSelectedItem().toString()));
            sa.setRampartsPosition(positionValueFromSpinnerString(rampartsPositionSpinner.getSelectedItem().toString()));
            sa.setDrawbridgePosition(positionValueFromSpinnerString(drawbridgePositionSpinner.getSelectedItem().toString()));
            sa.setSallyportPosition(positionValueFromSpinnerString(sallyportPositionSpinner.getSelectedItem().toString()));
            sa.setRockwallPosition(positionValueFromSpinnerString(rockwallPositionSpinner.getSelectedItem().toString()));
            sa.setRoughterrainPosition(positionValueFromSpinnerString(roughterrainPositionSpinner.getSelectedItem().toString()));
            sa.setPortcullisCrossed(portcullisPositionToggleButton.isChecked());
            sa.setChevalCrossed(chevalPositionToggleButton.isChecked());
            sa.setMoatCrossed(moatPositionToggleButton.isChecked());
            sa.setRampartsCrossed(rampartsPositionToggleButton.isChecked());
            sa.setDrawbridgeCrossed(drawbridgePositionToggleButton.isChecked());
            sa.setSallyportCrossed(sallyportPositionToggleButton.isChecked());
            sa.setRockwallCrossed(rockwallPositionToggleButton.isChecked());
            sa.setRoughterrainCrossed(roughterrainPositionToggleButton.isChecked());
            sa.setLowbarCrossed(lowbarPositionToggleButton.isChecked());

            sa.setBoudlerPickedUp(pickedupboulderToggleButton.isChecked());
            sa.setRobotScoredHigh(scoredinhighgoalToggleButton.isChecked());
            sa.setRobotScoredLow(scoredinlowgoalToggleButton.isChecked());
            sa.setEndingPosition(endingPositionSpinner.getSelectedItem().toString());
            sa.setReachAchieved(reachachievedToggleButton.isChecked());
            sa.setReachWasCrossAttempt(reachwascrossattemptToggleButton.isChecked());
            sa.setStartedAsSpy(startedasspyToggleButton.isChecked());
            sa.setStartedWithBoulder(startedwithboulderToggleButton.isChecked());
        }

        return sa;
    }

}
