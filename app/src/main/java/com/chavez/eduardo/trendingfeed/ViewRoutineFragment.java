package com.chavez.eduardo.trendingfeed;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


import java.util.List;

import BaseDeDatos.RoutineContract;
import BaseDeDatos.RoutineModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewRoutineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewRoutineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRoutineFragment extends Fragment implements AdapterView.OnItemSelectedListener, Validator.ValidationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ROUTINE = "ROUTINE";


    // TODO: Rename and change types of parameters
    private int ID_;
    private Routine routine;

    @NotEmpty(message = "Ingrese un nombre")
    EditText routineNameUpdate;

    @NotEmpty(message = "Ingrese una descripcion")
    EditText routineDescriptionUpdate;

    NumberPicker routineMinutesUpdate;

    Spinner routineSpinnerTypeUpdate;

    private Button updateRoutine;
    private Button shareTweet;
    private Button deleteRoutine;
    private Button cancelUpdateRoutine;
    private Button confirmUpdateRoutine;

    private TextView titleRoutine;
    private TextView routineNameUpdateTitle;
    private TextView routineDescriptionUpdateTitle;
    private TextView routineMinutesUpdateTitle;
    private TextView routineSpinnerTypeUpdateTitle;

    private String name,description,type;
    private int minutes,ID;

    RoutineModel routineModel;


    String[] tiposSpinner = { "Pesas", "Cardio", "Yoga", "Otro",  };
    Validator validator;

    private OnFragmentInteractionListener mListener;

    public ViewRoutineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewRoutineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRoutineFragment newInstance(int param1) {
        ViewRoutineFragment fragment = new ViewRoutineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_ROUTINE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ID_ = getArguments().getInt(ARG_PARAM_ROUTINE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_routine, container, false);
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
        loadUpdateData();
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routineModel = new RoutineModel(getContext());
        validator = new Validator(this);
        validator.setValidationListener(this);

        routineNameUpdate = (EditText) getActivity().findViewById(R.id.routineNameUpdate);
        routineDescriptionUpdate = (EditText) getActivity().findViewById(R.id.routineDescriptionUpdate);
        routineMinutesUpdate = (NumberPicker) getActivity().findViewById(R.id.itemMinutesRoutineUpdate);
        routineSpinnerTypeUpdate = (Spinner) getActivity().findViewById(R.id.routineSpinnerUpdate);

        titleRoutine = (TextView) getActivity().findViewById(R.id.viewRoutineTitle);
        routineNameUpdateTitle = (TextView) getActivity().findViewById(R.id.routineNameUpdateTitle);
        routineDescriptionUpdateTitle = (TextView) getActivity().findViewById(R.id.routineDescriptionUpdateTitle);
        routineMinutesUpdateTitle = (TextView) getActivity().findViewById(R.id.itemMinutesRoutineUpdateTitle);
        routineSpinnerTypeUpdateTitle = (TextView) getActivity().findViewById(R.id.routineSpinnerUpdateTitle);

        updateRoutine = (Button) getActivity().findViewById(R.id.buttonUpdateRoutine);
        shareTweet = (Button) getActivity().findViewById(R.id.createTweet);
        deleteRoutine = (Button)getActivity().findViewById(R.id.buttonDeleteRoutine);
        cancelUpdateRoutine = (Button)getActivity().findViewById(R.id.buttonCancelUpdate);
        confirmUpdateRoutine = (Button) getActivity().findViewById(R.id.buttonConfirmUpdate);

        routineMinutesUpdate.setMinValue(0);
        routineMinutesUpdate.setMaxValue(300);
        routineMinutesUpdate.setWrapSelectorWheel(false);


        loadInitialView();

        updateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUpdateView();
            }
        });

        shareTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComposeTweet();
            }
        });

        deleteRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDeleteDialog();
            }
        });

        cancelUpdateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInitialView();
            }
        });

        confirmUpdateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        routineMinutesUpdate.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    minutes = newVal;
            }
        });

        routineSpinnerTypeUpdate.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,tiposSpinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        routineSpinnerTypeUpdate.setAdapter(aa);

    }

    private void generateInitialData() {
        Cursor c = routineModel.getRoutineData(ID_);
        c.moveToFirst();
        while (!c.isAfterLast()){
            name = c.getString(c.getColumnIndex(RoutineContract.COLUMN_NAME_ROUTINE));
            description = c.getString(c.getColumnIndex(RoutineContract.COLUMN_DESCRIPTION_ROUTINE));
            type = c.getString(c.getColumnIndex(RoutineContract.COLUMN_TYPE_ROUTINE));
            minutes = c.getInt(c.getColumnIndex(RoutineContract.COLUMN_MINUTES_ROUTINE));

            routine = new Routine(ID_,name,description,type,minutes);

            c.moveToNext();
        }
    }

    private void loadUpdateData() {
        name = routineNameUpdate.getText().toString();
        description = routineDescriptionUpdate.getText().toString();

        routineModel.updateRoutine(ID_,name,description,type,minutes);
        Toast.makeText(getContext(),"Elemento actualizado",Toast.LENGTH_SHORT).show();
        loadInitialView();
    }

    private void loadDeleteDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Eliminar elemento")
                .setMessage("¿Desea eliminar: " + routine.getNombre() + "?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        routineModel.deleteRoutine(routine.getId());
                        Toast.makeText(getContext(),"Elemento eliminado",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new LoadAllItems()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void loadComposeTweet() {
        TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                .text("Acabo de finalizar mi rutina de " + routine.getNombre()+ " en " +routine.getTiempo() + " minutos #Fitness, #MyFitLife");
        builder.show();
    }

    private void loadInitialView() {
        generateInitialData();

        routineNameUpdate.setVisibility(View.GONE);
        routineDescriptionUpdate.setVisibility(View.GONE);
        routineMinutesUpdate.setVisibility(View.GONE);
        routineSpinnerTypeUpdate.setVisibility(View.GONE);

        titleRoutine.setText(routine.getNombre());
        routineNameUpdateTitle.setText("Nombre de rutina: "+routine.getNombre());
        routineDescriptionUpdateTitle.setText("Descripcion: "+routine.getDescripcion());
        routineMinutesUpdateTitle.setText("Tiempo tomado "+String.valueOf(routine.getTiempo())+" minutos" );
        routineSpinnerTypeUpdateTitle.setText("Tipo de ejercicio: " +routine.getTipoEjercicio());

        updateRoutine.setVisibility(View.VISIBLE);
        shareTweet.setVisibility(View.VISIBLE);
        deleteRoutine.setVisibility(View.VISIBLE);
        cancelUpdateRoutine.setVisibility(View.GONE);
        confirmUpdateRoutine.setVisibility(View.GONE);
    }


    private void loadUpdateView(){
        routineNameUpdate.setVisibility(View.VISIBLE);
        routineDescriptionUpdate.setVisibility(View.VISIBLE);
        routineMinutesUpdate.setVisibility(View.VISIBLE);
        routineSpinnerTypeUpdate.setVisibility(View.VISIBLE);

        routineNameUpdate.setText(routine.getNombre());
        routineDescriptionUpdate.setText(routine.getDescripcion());
        routineMinutesUpdate.setValue(routine.getTiempo());

        titleRoutine.setText(routine.getNombre());
        routineNameUpdateTitle.setText("Ingrese un nuevo nombre");
        routineDescriptionUpdateTitle.setText("Ingrese una descripcion nueva");
        routineMinutesUpdateTitle.setText("Ingrese el tiempo en minutos");
        routineSpinnerTypeUpdateTitle.setText("Seleccione el tipo de ejercicio");

        updateRoutine.setVisibility(View.GONE);
        shareTweet.setVisibility(View.GONE);
        deleteRoutine.setVisibility(View.GONE);
        cancelUpdateRoutine.setVisibility(View.VISIBLE);
        confirmUpdateRoutine.setVisibility(View.VISIBLE);
    }
}
