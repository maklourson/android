package fr.eni.ecole.demolivedata.dal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.converters.DateTypeConverter;
import fr.eni.ecole.demolivedata.converters.ListStringConverter;


@Database(entities = {Planet.class},version = SWContract.DATABASE_VERSION, exportSchema = false)
@TypeConverters({DateTypeConverter.class, ListStringConverter.class})
public abstract class SWDatabase extends RoomDatabase {
    public abstract PlanetDAO getPlanetDAORoom();
}
