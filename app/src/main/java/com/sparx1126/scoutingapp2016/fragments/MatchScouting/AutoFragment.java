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
        
        return result;
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

    public ScoutingAuto getScoutingAuto(){
        if(sa == null)
            sa = new ScoutingAuto();
        return sa;
    }

}
