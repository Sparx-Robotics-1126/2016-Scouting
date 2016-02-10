package com.sparx1126.scoutingapp2016.fragments.Benchmarking;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.R;

import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoringFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoringFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ScoutingInfo si;
    private boolean wasCreated;
    private ToggleButton highGoal;
    private ToggleButton lowGoal;
    private EditText avgLow;
    private EditText avgHigh;
    private EditText scalePercent;
    private ToggleButton left, center, right;
    private EditText cycleTime;
    private ToggleButton defense;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ScoringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoringFragment newInstance(ScoutingInfo si) {
        ScoringFragment fragment = new ScoringFragment();
        fragment.setScoutingInfo(si);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ScoringFragment() {
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
        View result =  inflater.inflate(R.layout.fragment_scoring, container, false);
        highGoal = (ToggleButton)result.findViewById(R.id.highGoal);
        lowGoal = (ToggleButton)result.findViewById(R.id.lowGoal);
        avgHigh = (EditText)result.findViewById(R.id.avgHigh);
        avgLow = (EditText)result.findViewById(R.id.avgLow);
        scalePercent = (EditText)result.findViewById(R.id.scalingHeight);
        left = (ToggleButton)result.findViewById(R.id.scaleLeft);
        right = (ToggleButton)result.findViewById(R.id.scaleRight);
        center = (ToggleButton)result.findViewById(R.id.scaleCenter);
        cycleTime = (EditText)result.findViewById(R.id.cycleTime);
        defense = (ToggleButton)result.findViewById(R.id.playsDefense);

        if(si != null){
            highGoal.setChecked(si.getCanScoreInHighGoal());
            lowGoal.setChecked(si.getCanScoreInLowGoal());
            avgHigh.setText(String.valueOf(si.getAverageHighGoalsPerMatch()));
            avgLow.setText(String.valueOf(si.getAverageLowGoalsPerMatch()));
            scalePercent.setText(String.valueOf(si.getScaleHeightPercent()));
            left.setChecked(si.getCanScaleOnLeft());
            right.setChecked(si.getCanScaleOnRight());
            center.setChecked(si.getCanScaleAtCenter());
            cycleTime.setText(String.valueOf(si.getCycleTime()));
            defense.setChecked(si.getPlaysDefense());
        }
        wasCreated = true;
        return result;
    }

    public void onResume(){
        super.onResume();
        if(si != null){
            highGoal.setChecked(si.getCanScoreInHighGoal());
            lowGoal.setChecked(si.getCanScoreInLowGoal());
            avgHigh.setText(String.valueOf(si.getAverageHighGoalsPerMatch()));
            avgLow.setText(String.valueOf(si.getAverageLowGoalsPerMatch()));
            scalePercent.setText(String.valueOf(si.getScaleHeightPercent()));
            left.setChecked(si.getCanScaleOnLeft());
            right.setChecked(si.getCanScaleOnRight());
            center.setChecked(si.getCanScaleAtCenter());
            cycleTime.setText(String.valueOf(si.getCycleTime()));
            defense.setChecked(si.getPlaysDefense());
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
    private void setScoutingInfo(ScoutingInfo si){this.si = si;}
    public ScoutingInfo getScoutingInfo(){
        if(si == null)
            si = new ScoutingInfo();
        if(wasCreated){
            si.setCanScoreInHighGoal(highGoal.isChecked());
            si.setCanScoreInLowGoal(lowGoal.isChecked());
            try {
                si.setAverageHighGoalsPerMatch(Double.parseDouble(avgHigh.getText().toString()));
            }
            catch(Exception e){
                si.setAverageHighGoalsPerMatch(0.0);
            }
            try{
            si.setAverageLowGoalsPerMatch(Double.parseDouble(avgLow.getText().toString()));
            }
            catch(Exception e){
                si.setAverageLowGoalsPerMatch(0.0);
            }
            try {
                si.setScaleHeightPercent(Double.parseDouble(scalePercent.getText().toString()));
            }
            catch(Exception e){
                si.setScaleHeightPercent(0.0);
            }
            si.setCanScaleOnLeft(left.isChecked());
            si.setCanScaleOnRight(right.isChecked());
            si.setCanScaleAtCenter(center.isChecked());
            try {
                si.setCycleTime(Double.parseDouble(cycleTime.getText().toString()));
            }
            catch(Exception e){
                si.setCycleTime(0.0);
            }
            si.setPlaysDefense(defense.isChecked());
        }
        return si;
    }

}
