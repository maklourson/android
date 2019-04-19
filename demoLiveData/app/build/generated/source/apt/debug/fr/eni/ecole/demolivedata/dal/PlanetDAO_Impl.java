package fr.eni.ecole.demolivedata.dal;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import fr.eni.ecole.demolivedata.bo.Planet;
import fr.eni.ecole.demolivedata.converters.DateTypeConverter;
import fr.eni.ecole.demolivedata.converters.ListStringConverter;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class PlanetDAO_Impl implements PlanetDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlanet;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPlanet;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPlanet;

  public PlanetDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlanet = new EntityInsertionAdapter<Planet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Planet`(`id`,`url`,`climate`,`edited`,`created`,`diameter`,`gravity`,`name`,`orbital_period`,`population`,`rotation_period`,`surface_water`,`terrain`,`residents`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Planet value) {
        stmt.bindLong(1, value.getId());
        if (value.getUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUrl());
        }
        if (value.getClimate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getClimate());
        }
        final Long _tmp;
        _tmp = DateTypeConverter.toLong(value.getEdited());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateTypeConverter.toLong(value.getCreated());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        if (value.getDiameter() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDiameter());
        }
        if (value.getGravity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getGravity());
        }
        if (value.getName() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getName());
        }
        if (value.getOrbital_period() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getOrbital_period());
        }
        if (value.getPopulation() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPopulation());
        }
        if (value.getRotation_period() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getRotation_period());
        }
        if (value.getSurface_water() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getSurface_water());
        }
        if (value.getTerrain() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTerrain());
        }
        final String _tmp_2;
        _tmp_2 = ListStringConverter.fromList(value.getResidents());
        if (_tmp_2 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfPlanet = new EntityDeletionOrUpdateAdapter<Planet>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Planet` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Planet value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfPlanet = new EntityDeletionOrUpdateAdapter<Planet>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `Planet` SET `id` = ?,`url` = ?,`climate` = ?,`edited` = ?,`created` = ?,`diameter` = ?,`gravity` = ?,`name` = ?,`orbital_period` = ?,`population` = ?,`rotation_period` = ?,`surface_water` = ?,`terrain` = ?,`residents` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Planet value) {
        stmt.bindLong(1, value.getId());
        if (value.getUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUrl());
        }
        if (value.getClimate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getClimate());
        }
        final Long _tmp;
        _tmp = DateTypeConverter.toLong(value.getEdited());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateTypeConverter.toLong(value.getCreated());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        if (value.getDiameter() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDiameter());
        }
        if (value.getGravity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getGravity());
        }
        if (value.getName() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getName());
        }
        if (value.getOrbital_period() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getOrbital_period());
        }
        if (value.getPopulation() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPopulation());
        }
        if (value.getRotation_period() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getRotation_period());
        }
        if (value.getSurface_water() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getSurface_water());
        }
        if (value.getTerrain() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTerrain());
        }
        final String _tmp_2;
        _tmp_2 = ListStringConverter.fromList(value.getResidents());
        if (_tmp_2 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, _tmp_2);
        }
        stmt.bindLong(15, value.getId());
      }
    };
  }

  @Override
  public void insert(Planet planet) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlanet.insert(planet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Planet planet) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPlanet.handle(planet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Planet planet) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPlanet.handle(planet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Planet getPlanetById(int id) {
    final String _sql = "Select * From planet WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfClimate = _cursor.getColumnIndexOrThrow("climate");
      final int _cursorIndexOfEdited = _cursor.getColumnIndexOrThrow("edited");
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfDiameter = _cursor.getColumnIndexOrThrow("diameter");
      final int _cursorIndexOfGravity = _cursor.getColumnIndexOrThrow("gravity");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfOrbitalPeriod = _cursor.getColumnIndexOrThrow("orbital_period");
      final int _cursorIndexOfPopulation = _cursor.getColumnIndexOrThrow("population");
      final int _cursorIndexOfRotationPeriod = _cursor.getColumnIndexOrThrow("rotation_period");
      final int _cursorIndexOfSurfaceWater = _cursor.getColumnIndexOrThrow("surface_water");
      final int _cursorIndexOfTerrain = _cursor.getColumnIndexOrThrow("terrain");
      final int _cursorIndexOfResidents = _cursor.getColumnIndexOrThrow("residents");
      final Planet _result;
      if(_cursor.moveToFirst()) {
        _result = new Planet();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
        final String _tmpClimate;
        _tmpClimate = _cursor.getString(_cursorIndexOfClimate);
        _result.setClimate(_tmpClimate);
        final Date _tmpEdited;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfEdited)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfEdited);
        }
        _tmpEdited = DateTypeConverter.toDate(_tmp);
        _result.setEdited(_tmpEdited);
        final Date _tmpCreated;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfCreated)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfCreated);
        }
        _tmpCreated = DateTypeConverter.toDate(_tmp_1);
        _result.setCreated(_tmpCreated);
        final String _tmpDiameter;
        _tmpDiameter = _cursor.getString(_cursorIndexOfDiameter);
        _result.setDiameter(_tmpDiameter);
        final String _tmpGravity;
        _tmpGravity = _cursor.getString(_cursorIndexOfGravity);
        _result.setGravity(_tmpGravity);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final String _tmpOrbital_period;
        _tmpOrbital_period = _cursor.getString(_cursorIndexOfOrbitalPeriod);
        _result.setOrbital_period(_tmpOrbital_period);
        final String _tmpPopulation;
        _tmpPopulation = _cursor.getString(_cursorIndexOfPopulation);
        _result.setPopulation(_tmpPopulation);
        final String _tmpRotation_period;
        _tmpRotation_period = _cursor.getString(_cursorIndexOfRotationPeriod);
        _result.setRotation_period(_tmpRotation_period);
        final String _tmpSurface_water;
        _tmpSurface_water = _cursor.getString(_cursorIndexOfSurfaceWater);
        _result.setSurface_water(_tmpSurface_water);
        final String _tmpTerrain;
        _tmpTerrain = _cursor.getString(_cursorIndexOfTerrain);
        _result.setTerrain(_tmpTerrain);
        final List<String> _tmpResidents;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfResidents);
        _tmpResidents = ListStringConverter.fromString(_tmp_2);
        _result.setResidents(_tmpResidents);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Planet>> getPlanets() {
    final String _sql = "Select * FROM planet ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Planet>>() {
      private Observer _observer;

      @Override
      protected List<Planet> compute() {
        if (_observer == null) {
          _observer = new Observer("planet") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
          final int _cursorIndexOfClimate = _cursor.getColumnIndexOrThrow("climate");
          final int _cursorIndexOfEdited = _cursor.getColumnIndexOrThrow("edited");
          final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
          final int _cursorIndexOfDiameter = _cursor.getColumnIndexOrThrow("diameter");
          final int _cursorIndexOfGravity = _cursor.getColumnIndexOrThrow("gravity");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfOrbitalPeriod = _cursor.getColumnIndexOrThrow("orbital_period");
          final int _cursorIndexOfPopulation = _cursor.getColumnIndexOrThrow("population");
          final int _cursorIndexOfRotationPeriod = _cursor.getColumnIndexOrThrow("rotation_period");
          final int _cursorIndexOfSurfaceWater = _cursor.getColumnIndexOrThrow("surface_water");
          final int _cursorIndexOfTerrain = _cursor.getColumnIndexOrThrow("terrain");
          final int _cursorIndexOfResidents = _cursor.getColumnIndexOrThrow("residents");
          final List<Planet> _result = new ArrayList<Planet>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Planet _item;
            _item = new Planet();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            _item.setUrl(_tmpUrl);
            final String _tmpClimate;
            _tmpClimate = _cursor.getString(_cursorIndexOfClimate);
            _item.setClimate(_tmpClimate);
            final Date _tmpEdited;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfEdited)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfEdited);
            }
            _tmpEdited = DateTypeConverter.toDate(_tmp);
            _item.setEdited(_tmpEdited);
            final Date _tmpCreated;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCreated)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfCreated);
            }
            _tmpCreated = DateTypeConverter.toDate(_tmp_1);
            _item.setCreated(_tmpCreated);
            final String _tmpDiameter;
            _tmpDiameter = _cursor.getString(_cursorIndexOfDiameter);
            _item.setDiameter(_tmpDiameter);
            final String _tmpGravity;
            _tmpGravity = _cursor.getString(_cursorIndexOfGravity);
            _item.setGravity(_tmpGravity);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpOrbital_period;
            _tmpOrbital_period = _cursor.getString(_cursorIndexOfOrbitalPeriod);
            _item.setOrbital_period(_tmpOrbital_period);
            final String _tmpPopulation;
            _tmpPopulation = _cursor.getString(_cursorIndexOfPopulation);
            _item.setPopulation(_tmpPopulation);
            final String _tmpRotation_period;
            _tmpRotation_period = _cursor.getString(_cursorIndexOfRotationPeriod);
            _item.setRotation_period(_tmpRotation_period);
            final String _tmpSurface_water;
            _tmpSurface_water = _cursor.getString(_cursorIndexOfSurfaceWater);
            _item.setSurface_water(_tmpSurface_water);
            final String _tmpTerrain;
            _tmpTerrain = _cursor.getString(_cursorIndexOfTerrain);
            _item.setTerrain(_tmpTerrain);
            final List<String> _tmpResidents;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfResidents);
            _tmpResidents = ListStringConverter.fromString(_tmp_2);
            _item.setResidents(_tmpResidents);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Planet>> getPlanets(String name) {
    final String _sql = "Select * FROM planet WHERE name LIKE ? || '%' ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return new ComputableLiveData<List<Planet>>() {
      private Observer _observer;

      @Override
      protected List<Planet> compute() {
        if (_observer == null) {
          _observer = new Observer("planet") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
          final int _cursorIndexOfClimate = _cursor.getColumnIndexOrThrow("climate");
          final int _cursorIndexOfEdited = _cursor.getColumnIndexOrThrow("edited");
          final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
          final int _cursorIndexOfDiameter = _cursor.getColumnIndexOrThrow("diameter");
          final int _cursorIndexOfGravity = _cursor.getColumnIndexOrThrow("gravity");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfOrbitalPeriod = _cursor.getColumnIndexOrThrow("orbital_period");
          final int _cursorIndexOfPopulation = _cursor.getColumnIndexOrThrow("population");
          final int _cursorIndexOfRotationPeriod = _cursor.getColumnIndexOrThrow("rotation_period");
          final int _cursorIndexOfSurfaceWater = _cursor.getColumnIndexOrThrow("surface_water");
          final int _cursorIndexOfTerrain = _cursor.getColumnIndexOrThrow("terrain");
          final int _cursorIndexOfResidents = _cursor.getColumnIndexOrThrow("residents");
          final List<Planet> _result = new ArrayList<Planet>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Planet _item;
            _item = new Planet();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            _item.setUrl(_tmpUrl);
            final String _tmpClimate;
            _tmpClimate = _cursor.getString(_cursorIndexOfClimate);
            _item.setClimate(_tmpClimate);
            final Date _tmpEdited;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfEdited)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfEdited);
            }
            _tmpEdited = DateTypeConverter.toDate(_tmp);
            _item.setEdited(_tmpEdited);
            final Date _tmpCreated;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCreated)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfCreated);
            }
            _tmpCreated = DateTypeConverter.toDate(_tmp_1);
            _item.setCreated(_tmpCreated);
            final String _tmpDiameter;
            _tmpDiameter = _cursor.getString(_cursorIndexOfDiameter);
            _item.setDiameter(_tmpDiameter);
            final String _tmpGravity;
            _tmpGravity = _cursor.getString(_cursorIndexOfGravity);
            _item.setGravity(_tmpGravity);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpOrbital_period;
            _tmpOrbital_period = _cursor.getString(_cursorIndexOfOrbitalPeriod);
            _item.setOrbital_period(_tmpOrbital_period);
            final String _tmpPopulation;
            _tmpPopulation = _cursor.getString(_cursorIndexOfPopulation);
            _item.setPopulation(_tmpPopulation);
            final String _tmpRotation_period;
            _tmpRotation_period = _cursor.getString(_cursorIndexOfRotationPeriod);
            _item.setRotation_period(_tmpRotation_period);
            final String _tmpSurface_water;
            _tmpSurface_water = _cursor.getString(_cursorIndexOfSurfaceWater);
            _item.setSurface_water(_tmpSurface_water);
            final String _tmpTerrain;
            _tmpTerrain = _cursor.getString(_cursorIndexOfTerrain);
            _item.setTerrain(_tmpTerrain);
            final List<String> _tmpResidents;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfResidents);
            _tmpResidents = ListStringConverter.fromString(_tmp_2);
            _item.setResidents(_tmpResidents);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
