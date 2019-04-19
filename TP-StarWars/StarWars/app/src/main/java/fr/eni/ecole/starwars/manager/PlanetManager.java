package fr.eni.ecole.starwars.manager;

import java.util.List;

import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.dao.ConnexionDao;
import fr.eni.ecole.starwars.dao.PlanetDAO;

public abstract class PlanetManager {

    public static void insert(Planet planet){
        PlanetDAO dao = ConnexionDao.getPlanetDao();
        dao.insert(planet);
    }

    public static void update(Planet planet){
        PlanetDAO dao = ConnexionDao.getPlanetDao();
        dao.update(planet);
    }

    public static Planet getPlanetById(int id){
        PlanetDAO dao = ConnexionDao.getPlanetDao();
        return dao.getPlanetById(id);
    }


    public static List<Planet> getPlanets(){
        PlanetDAO dao = ConnexionDao.getPlanetDao();
        return dao.getPlanets();
    }

}
