package fr.eni.ecole.starwars.manager;

import java.util.List;

import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.dao.ConnexionDao;
import fr.eni.ecole.starwars.dao.PeopleDAO;

public abstract class PeopleManager {

    public final static String BROADCAST_PEOPLE = "fr.eni.ecole.starwars.action.update.people";

    public static void insert(People people){
        PeopleDAO dao = ConnexionDao.getPeopleDao();
        dao.insert(people);
    }

    public static void update(People people){
        PeopleDAO dao = ConnexionDao.getPeopleDao();
        dao.update(people);
    }

    public static List<People> getPeoplesByIds(List<Integer> ids){
        PeopleDAO dao = ConnexionDao.getPeopleDao();
        return  dao.getPeoplesByIds(ids);
    }

    public static People getPeople(int id){
        PeopleDAO dao = ConnexionDao.getPeopleDao();
        return dao.getPeople(id);
    }
}
