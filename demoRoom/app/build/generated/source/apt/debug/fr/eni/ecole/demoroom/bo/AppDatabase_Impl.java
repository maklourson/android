package fr.eni.ecole.demoroom.bo;

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
public class AppDatabase_Impl extends AppDatabase {
  private volatile UtilisateurDao _utilisateurDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Utilisateur` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT, `prenom` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"98805e393f118b2b7884c13e1f821adf\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Utilisateur`");
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
        final HashMap<String, TableInfo.Column> _columnsUtilisateur = new HashMap<String, TableInfo.Column>(3);
        _columnsUtilisateur.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsUtilisateur.put("nom", new TableInfo.Column("nom", "TEXT", false, 0));
        _columnsUtilisateur.put("prenom", new TableInfo.Column("prenom", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUtilisateur = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUtilisateur = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUtilisateur = new TableInfo("Utilisateur", _columnsUtilisateur, _foreignKeysUtilisateur, _indicesUtilisateur);
        final TableInfo _existingUtilisateur = TableInfo.read(_db, "Utilisateur");
        if (! _infoUtilisateur.equals(_existingUtilisateur)) {
          throw new IllegalStateException("Migration didn't properly handle Utilisateur(fr.eni.ecole.demoroom.bo.Utilisateur).\n"
                  + " Expected:\n" + _infoUtilisateur + "\n"
                  + " Found:\n" + _existingUtilisateur);
        }
      }
    }, "98805e393f118b2b7884c13e1f821adf", "45dcc7801b8892c95ca077080da81ae1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Utilisateur");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Utilisateur`");
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
  public UtilisateurDao utilisateurDao() {
    if (_utilisateurDao != null) {
      return _utilisateurDao;
    } else {
      synchronized(this) {
        if(_utilisateurDao == null) {
          _utilisateurDao = new UtilisateurDao_Impl(this);
        }
        return _utilisateurDao;
      }
    }
  }
}
