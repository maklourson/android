package fr.eni.ecole.demoroom.bo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Utilisateur.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UtilisateurDao utilisateurDao();
}
