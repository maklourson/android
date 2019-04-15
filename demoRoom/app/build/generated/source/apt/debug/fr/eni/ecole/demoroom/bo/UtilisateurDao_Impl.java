package fr.eni.ecole.demoroom.bo;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class UtilisateurDao_Impl implements UtilisateurDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUtilisateur;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUtilisateur;

  public UtilisateurDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUtilisateur = new EntityInsertionAdapter<Utilisateur>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Utilisateur`(`id`,`nom`,`prenom`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Utilisateur value) {
        stmt.bindLong(1, value.getId());
        if (value.getNom() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNom());
        }
        if (value.getPrenom() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPrenom());
        }
      }
    };
    this.__deletionAdapterOfUtilisateur = new EntityDeletionOrUpdateAdapter<Utilisateur>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Utilisateur` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Utilisateur value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insert(Utilisateur utilisateur) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUtilisateur.insert(utilisateur);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(Utilisateur... utilisateurs) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUtilisateur.insert(utilisateurs);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Utilisateur utilisateur) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUtilisateur.handle(utilisateur);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Utilisateur> getAll() {
    final String _sql = "SELECT * FROM utilisateur";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNom = _cursor.getColumnIndexOrThrow("nom");
      final int _cursorIndexOfPrenom = _cursor.getColumnIndexOrThrow("prenom");
      final List<Utilisateur> _result = new ArrayList<Utilisateur>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Utilisateur _item;
        _item = new Utilisateur();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpNom;
        _tmpNom = _cursor.getString(_cursorIndexOfNom);
        _item.setNom(_tmpNom);
        final String _tmpPrenom;
        _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
        _item.setPrenom(_tmpPrenom);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Utilisateur getUtilisateurById(int id) {
    final String _sql = "SELECT * FROM utilisateur WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNom = _cursor.getColumnIndexOrThrow("nom");
      final int _cursorIndexOfPrenom = _cursor.getColumnIndexOrThrow("prenom");
      final Utilisateur _result;
      if(_cursor.moveToFirst()) {
        _result = new Utilisateur();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpNom;
        _tmpNom = _cursor.getString(_cursorIndexOfNom);
        _result.setNom(_tmpNom);
        final String _tmpPrenom;
        _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
        _result.setPrenom(_tmpPrenom);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
