package com.chavez.eduardo.trendingfeed;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import BaseDeDatos.RoutineContract;
import BaseDeDatos.RoutineModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoadAllItems.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoadAllItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadAllItems extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Routine> routines = new ArrayList<>();
    RoutineModel routineModel;
    private String nombre, descripcion, tipo;
    private int minutes, ID;

    RoutineItemAdapter routineItemAdapter;
    RecyclerView recyclerView;
    TextView emptyView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoadAllItems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadAllItems.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadAllItems newInstance(String param1, String param2) {
        LoadAllItems fragment = new LoadAllItems();
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
        return inflater.inflate(R.layout.fragment_load_all_items, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routineModel = new RoutineModel(getContext());
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
    public void onResume() {
        super.onResume();
        getContent();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void getContent(){
        routines.clear();

        Cursor c = routineModel.listAllRoutines();

        c.moveToFirst();

        while (!c.isAfterLast()){
            ID = c.getInt(c.getColumnIndex(RoutineContract._ID));
            nombre = c.getString(c.getColumnIndex(RoutineContract.COLUMN_NAME_ROUTINE));
            descripcion = c.getString(c.getColumnIndex(RoutineContract.COLUMN_DESCRIPTION_ROUTINE));
            tipo = c.getString(c.getColumnIndex(RoutineContract.COLUMN_TYPE_ROUTINE));
            minutes = c.getInt(c.getColumnIndex(RoutineContract.COLUMN_MINUTES_ROUTINE));

            routines.add(new Routine(ID,nombre,descripcion,tipo,minutes));

            c.moveToNext();
        }

        routineModel.database.close();
        routineItemAdapter = new RoutineItemAdapter(routines,getContext(),this);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        emptyView = (TextView) getActivity().findViewById(R.id.emptyRecycler);

        recyclerView.setAdapter(routineItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (routines.size()==0){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
