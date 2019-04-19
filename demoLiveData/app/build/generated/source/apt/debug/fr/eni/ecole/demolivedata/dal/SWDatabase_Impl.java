package fr.eni.ecole.demolivedata.dal;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class SWDatabase_Impl extends SWDatabase {
  private volatile PlanetDAO _planetDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Planet` (`id` INTEGER NOT NULL, `url` TEXT, `climate` TEXT, `edited` INTEGER, `created` INTEGER, `diameter` TEXT, `gravity` TEXT, `name` TEXT, `orbital_period` TEXT, `population` TEXT, `rotation_period` TEXT, `surface_water` TEXT, `terrain` TEXT, `residents` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"f76af664d8c9b3250d240a3f9f42c744\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Planet`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPlanet = new HashMap<String, TableInfo.Column>(14);
        _columnsPlanet.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsPlanet.put("url", new TableInfo.Column("url", "TEXT", false, 0));
        _columnsPlanet.put("climate", new TableInfo.Column("climate", "TEXT", false, 0));
        _columnsPlanet.put("edited", new TableInfo.Column("edited", "INTEGER", false, 0));
        _columnsPlanet.put("created", new TableInfo.Column("created", "INTEGER", false, 0));
        _columnsPlanet.put("diameter", new TableInfo.Column("diameter", "TEXT", false, 0));
        _columnsPlanet.put("gravity", new TableInfo.Column("gravity", "TEXT", false, 0));
        _columnsPlanet.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsPlanet.put("orbital_period", new TableInfo.Column("orbital_period", "TEXT", false, 0));
        _columnsPlanet.put("population", new TableInfo.Column("population", "TEXT", false, 0));
        _columnsPlanet.put("rotation_period", new TableInfo.Column("rotation_period", "TEXT", false, 0));
        _columnsPlanet.put("surface_water", new TableInfo.Column("surface_water", "TEXT", false, 0));
        _columnsPlanet.put("terrain", new TableInfo.Column("terrain", "TEXT", false, 0));
        _columnsPlanet.put("residents", new TableInfo.Column("residents", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlanet = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlanet = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlanet = new TableInfo("Planet", _columnsPlanet, _foreignKeysPlanet, _indicesPlanet);
        final TableInfo _existingPlanet = TableInfo.read(_db, "Planet");
        if (! _infoPlanet.equals(_existingPlanet)) {
          throw new IllegalStateException("Migration didn't properly handle Planet(fr.eni.ecole.demolivedata.bo.Planet).\n"
                  + " Expected:\n" + _infoPlanet + "\n"
                  + " Found:\n" + _existingPlanet);
        }
      }
    }, "f76af664d8c9b3250d240a3f9f42c744", "8007d7c1767a972024049be34f063953");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Planet");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Planet`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public PlanetDAO getPlanetDAORoom() {
    if (_planetDAO != null) {
      return _planetDAO;
    } else {
      synchronized(this) {
        if(_planetDAO == null) {
          _planetDAO = new PlanetDAO_Impl(this);
        }
        return _planetDAO;
      }
    }
  }
}
