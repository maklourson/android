package fr.eni.ecole.starwars.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import fr.eni.ecole.starwars.bo.People;
import fr.eni.ecole.starwars.bo.Planet;
import fr.eni.ecole.starwars.converters.DateTypeConverter;
import fr.eni.ecole.starwars.converters.ListStringConverter;

@Database(entities = {Planet.class, People.class},version = SWContract.DATABASE_VERSION, exportSchema = false)
@TypeConverters({DateTypeConverter.class, ListStringConverter.class})
public abstract class SWDatabase extends RoomDatabase {
    public abstract PeopleDAO getPeopleDAORoom();
    public abstract PlanetDAO getPlanetDAORoom();
}
