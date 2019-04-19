package fr.eni.ecole.starwars.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.starwars.Application.SWApplication;
import fr.eni.ecole.starwars.R;
import fr.eni.ecole.starwars.bo.Planet;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.MyViewHolder> {

    private List<Planet> datas;
    private CustomItemClickListener listener;

    public PlanetAdapter(List<Planet> datas, CustomItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planet,parent,false);

        final MyViewHolder viewHolder = new MyViewHolder(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(v, PlanetAdapter.this.datas.get( viewHolder.getAdapterPosition()));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Planet planet = datas.get(i);
        viewHolder.name.setText(SWApplication.getAppContext().getString( R.string.name_planet,planet.getName()));
        viewHolder.gravity.setText(SWApplication.getAppContext().getString(R.string.gravity_planet,planet.getGravity()));
        viewHolder.terrain.setText(SWApplication.getAppContext().getString(R.string.terrain_planet,planet.getTerrain()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView gravity;
        public TextView terrain;
        public View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.namePlanet);
            gravity = itemView.findViewById(R.id.gravityPlanet);
            terrain = itemView.findViewById(R.id.terrainPlanet);
        }
    }

    /**
     * Interface Listener
     */
    public interface CustomItemClickListener{
        public void onItemClick(View v, Planet planet);
    }
}
