package fr.eni.ecole.demoroom.bo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

@Dao
public interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur")
    List<Utilisateur> getAll();

    @Query("SELECT * FROM utilisateur WHERE id = :id")
    Utilisateur getUtilisateurById(int id);

    //@RawQuery

    @Insert
    void insert(Utilisateur utilisateur);

    @Insert
    void insertAll(Utilisateur... utilisateurs);

    @Delete
    void delete(Utilisateur utilisateur);

}
