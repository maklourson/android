package fr.eni.ecole.demolivedata.adapters;

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

import fr.eni.ecole.demolivedata.R;
import fr.eni.ecole.demolivedata.bo.Planet;


public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.MyViewHolder> {

    private List<Planet> datas;
    private CustomItemClickListener listener;
    private CustomItemLongClickListener longListener;

    public PlanetAdapter(List<Planet> datas, CustomItemClickListener listener, CustomItemLongClickListener longListener) {
        this.datas = datas;
        this.listener = listener;
        this.longListener = longListener;
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

        mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longListener != null){
                    return longListener.onItemLongClick(v, PlanetAdapter.this.datas.get( viewHolder.getAdapterPosition()),  viewHolder.getAdapterPosition());
                }
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Planet planet = datas.get(i);
        viewHolder.name.setText(viewHolder.view.getContext().getString( R.string.name_planet,planet.getName()));
        viewHolder.gravity.setText(viewHolder.view.getContext().getString(R.string.gravity_planet,planet.getGravity()));
        viewHolder.terrain.setText(viewHolder.view.getContext().getString(R.string.terrain_planet,planet.getTerrain()));
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

    /**
     * Interface Listener
     */
    public interface CustomItemLongClickListener{
        public boolean onItemLongClick(View v, Planet planet,int Position);
    }
}
