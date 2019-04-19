package fr.eni.ecole.starwars.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.starwars.Application.SWApplication;
import fr.eni.ecole.starwars.R;
import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.bo.Planet;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {

    private List<People> datas;
    private CustomItemClickListener listener;

    public PeopleAdapter(List<People> datas, CustomItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people,parent,false);

        final MyViewHolder viewHolder = new MyViewHolder(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(v, PeopleAdapter.this.datas.get( viewHolder.getAdapterPosition()));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        People people = datas.get(i);
        viewHolder.name.setText(people.getName());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public View view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.peopleName);

        }
    }

    /**
     * Interface Listener
     */
    public interface CustomItemClickListener{
        public void onItemClick(View v, People people);
    }
}
