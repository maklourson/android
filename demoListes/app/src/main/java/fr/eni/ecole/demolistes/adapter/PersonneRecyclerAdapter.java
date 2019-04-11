package fr.eni.ecole.demolistes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import fr.eni.ecole.demolistes.R;
import fr.eni.ecole.demolistes.bo.Personne;

public class PersonneRecyclerAdapter extends RecyclerView.Adapter<PersonneRecyclerAdapter.ViewHolder>{

    private List<Personne> datas;
    private onClickItemListener event;

    /**
     *
     * @param personnes List
     * @param e onClickItemListener
     */
    public PersonneRecyclerAdapter(List<Personne> personnes, onClickItemListener e){
        this.datas = personnes;
        this.event = e;
    }

    //Créer ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_item, viewGroup,false);

        final ViewHolder viewHolder = new ViewHolder(v);

        //Pour log
        final int position = i;
        //Associe le viewholder onClickListener et l'instance de onClickItemListener locale
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event != null){
                    //viewHolder.getAdapterPosition() donne la position dans les données,
                    //le paramètre i la position dans le nombre de viewHolder (0 puisque une seule instance)
                    event.onClickItem(PersonneRecyclerAdapter.this.datas.get(viewHolder.getAdapterPosition()));
                    Log.i("TAG_DEMO", "position : " + position);
                }
            }
        });

        return viewHolder;
    }

    //Remplir notre view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final Personne p = this.datas.get(position);

            viewHolder.nom.setText(p.getNom());
            viewHolder.prenom.setText(p.getPrenom());

            //Associe le viewholder onClickListener et l'instance de onClickItemListener locale
        /*
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(event != null){
                       event.onClickItem(p);
                   }
               }
            });
        */

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nom;
        public TextView prenom;

        public ViewHolder(View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.txtNomom);
            prenom = itemView.findViewById(R.id.txtPrenom);

        }
    }

    /**
     * Interface qui permet de gérer le click sur chaque ViewHolder
     */
    public interface onClickItemListener{
        void onClickItem(Personne p);
    }
}
