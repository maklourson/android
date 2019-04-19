package fr.eni.ecole.demolivedata.dal;

import android.arch.persistence.room.Room;
import android.content.Context;

public abstract class ConnexionDao {

    private static SWDatabase INSTANCE;

    //Singleton pour le LiveData
    public static SWDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            //Permet la synchronisation entre les différents Thread par des verrous
            synchronized (SWDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context,
                            SWDatabase.class, SWContract.DATABASE_NAME)
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            // Migration => update DataBase version
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static PlanetDAO getPlanetDao(Context context){
        SWDatabase db = getDatabase(context);
        return  db.getPlanetDAORoom();
    }



}
