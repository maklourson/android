package fr.eni.ecole.starwars.dao;

import android.arch.persistence.room.Room;

import fr.eni.ecole.starwars.Application.SWApplication;

public abstract class ConnexionDao {

    public static PlanetDAO getPlanetDao(){
        SWDatabase db = Room.databaseBuilder(SWApplication.getAppContext(),
                SWDatabase.class, SWContract.DATABASE_NAME).build();

        return  db.getPlanetDAORoom();
    }

    public static PeopleDAO getPeopleDao(){
        SWDatabase db = Room.databaseBuilder(SWApplication.getAppContext(),
                SWDatabase.class, SWContract.DATABASE_NAME).build();

        return  db.getPeopleDAORoom();
    }

}
