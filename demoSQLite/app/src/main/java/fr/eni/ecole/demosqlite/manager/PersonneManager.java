package fr.eni.ecole.demosqlite.manager;

import android.content.Context;

import fr.eni.ecole.demosqlite.bo.Personne;
import fr.eni.ecole.demosqlite.dao.PersonneDao;

public class PersonneManager {

    public static long insert(Context context, Personne p){
        PersonneDao dao = new PersonneDao(context);
        return dao.insert(p);
    }

    public static Personne getPersonneById (Context context, int id){
        PersonneDao dao = new PersonneDao(context);
        return dao.getPersonneById(id);
    }

}
