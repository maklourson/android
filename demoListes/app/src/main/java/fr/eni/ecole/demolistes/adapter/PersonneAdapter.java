package fr.eni.ecole.demolistes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.demolistes.R;
import fr.eni.ecole.demolistes.bo.Personne;

public class PersonneAdapter extends ArrayAdapter<Personne> {

    private List<Personne> datas;
    private int res;

    public PersonneAdapter( Context context, int resource, List<Personne> personnes) {
        super(context, resource, personnes);
        this.datas = personnes;
        this.res = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            view = inflater.inflate(res, parent, false);
        }
        else{
            view = convertView;
        }

        //Récuperer une instance
        Personne p = getItem(position);

        TextView nom = view.findViewById(R.id.txtNomom);
        TextView prenom = view.findViewById(R.id.txtPrenom);

        nom.setText(p.getNom());
        prenom.setText(p.getPrenom());

        return view;
    }
}
