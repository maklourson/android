package fr.eni.ecole.demolivedata.dal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import fr.eni.ecole.demolivedata.bo.Planet;

@Dao
public interface PlanetDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Planet planet);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(Planet planet);

    @Delete
    public void delete(Planet planet);

    @Query("Select * From planet WHERE id=:id")
    public Planet getPlanetById(int id);

    @Query("Select * FROM planet ORDER BY name")
    public LiveData<List<Planet>> getPlanets();

    @Query("Select * FROM planet WHERE name LIKE :name || '%' ORDER BY name")
    public LiveData<List<Planet>> getPlanets(String name);


}
