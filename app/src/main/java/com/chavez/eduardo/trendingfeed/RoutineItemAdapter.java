package com.chavez.eduardo.trendingfeed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;

/**
 * Created by Eduardo_Chavez on 4/4/2017.
 */

public class RoutineItemAdapter extends RecyclerView.Adapter<RoutineItemAdapter.RoutineViewHolder> {
    private ArrayList<Routine> routines = new ArrayList<>();
    private Context context;
    private Fragment fragmentParent;

    public RoutineItemAdapter(ArrayList<Routine> routines, Context context, Fragment fragment) {
        this.routines = routines;
        this.context = context;
        this.fragmentParent = fragment;
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new RoutineViewHolder(row);
    }

    @Override
    public void onBindViewHolder(RoutineViewHolder holder, int position) {
        final Routine routine = routines.get(position);

        holder.nombre.setText("Tu rutina: "+ routine.getNombre());
        holder.minutos.setText("Tiempo: "+String.valueOf(routine.getTiempo())+" minutos");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("ROUTINE",routine.getId());
                Fragment fragment = new ViewRoutineFragment();
                fragment.setArguments(bundle);

                fragmentParent.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TweetComposer.Builder builder = new TweetComposer.Builder(context)
                        .text("Acabo de finalizar mi rutina de " + routine.getNombre()+ " en " +routine.getTiempo() + " minutos #Fitness, #MyFitLife");
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView minutos;

        public RoutineViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.itemName);
            minutos = (TextView) itemView.findViewById(R.id.itemMinutes);
        }

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getMinutos() {
            return minutos;
        }

        public void setMinutos(TextView minutos) {
            this.minutos = minutos;
        }
    }


    public void prepareTweet(){

    }
}
