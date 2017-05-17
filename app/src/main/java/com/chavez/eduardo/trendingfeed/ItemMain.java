package com.chavez.eduardo.trendingfeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.RoutineModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemMain extends Fragment implements AdapterView.OnItemSelectedListener, Validator.ValidationListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @NotEmpty(message = "Ingresa un nombre")
    EditText routineName;

    @NotEmpty(message = "Ingresa una descripcion")
    EditText routineDescription;

    NumberPicker routineMinutes;

    Spinner routineSpinnerType;

    private Button addRoutine;
    private String name,description,type;
    private int minutes,ID;

    RoutineModel routineModel;

    private ArrayList<Routine>routines = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    Validator validator;


    String[] tiposSpinner = { "Pesas", "Cardio", "Yoga", "Otro",  };

    public ItemMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemMain.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemMain newInstance(String param1, String param2) {
        ItemMain fragment = new ItemMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        routineModel = new RoutineModel(getContext());
        validator = new Validator(this);
        validator.setValidationListener(this);

        routineName = (EditText) getActivity().findViewById(R.id.routineName);
        routineDescription = (EditText) getActivity().findViewById(R.id.routineDescription);
        routineMinutes = (NumberPicker) getActivity().findViewById(R.id.itemMinutesRoutine);
        routineSpinnerType = (Spinner) getActivity().findViewById(R.id.routineSpinner);

        addRoutine = (Button) getActivity().findViewById(R.id.addRoutine);

        addRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });

        routineMinutes.setMinValue(0);
        routineMinutes.setMaxValue(300);
        routineMinutes.setWrapSelectorWheel(false);

        routineMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
            }
        });

        routineSpinnerType.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,tiposSpinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        routineSpinnerType.setAdapter(aa);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = tiposSpinner[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValidationSucceeded() {
        getData();
        Toast.makeText(getContext(),"Elemento agregado",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new LoadAllItems()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error:errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            if (view instanceof EditText){
                ((EditText)view).setError(message);
            } else {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getData() {
        name = routineName.getText().toString();
        description = routineDescription.getText().toString();
        ID = 0;

        routineModel.insertRoutine(name,description,type,minutes);
        routines.add(new Routine(ID,name,description,type,minutes));

        clearData();

    }

    private void clearData() {
        routines.clear();
        routineName.setText("");
        minutes = 0;
        routineMinutes.setValue(0);
        routineDescription.setText("");
        name = "";
        description = "";
        type = "";


    }

}
