package fr.eni.ecole.starwars.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.ecole.starwars.bo.People;

@Dao
public interface PeopleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(People people);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(People people);

    @Query("SELECT * FROM people WHERE id IN (:ids)")
    public List<People> getPeoplesByIds(List<Integer> ids);

    @Query("SELECT * FROM people WHERE id = :id ")
    public People getPeople(int id);

}

