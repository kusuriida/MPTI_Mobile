package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.User;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfLogoutAll;

  private final SharedSQLiteStatement __preparedStmtOfSetLoggedIn;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `user` (`id_user`,`nama`,`username`,`password`,`role`,`is_logged_in`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.idUser);
        if (value.nama == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.nama);
        }
        if (value.username == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.username);
        }
        if (value.password == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.password);
        }
        if (value.role == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.role);
        }
        final int _tmp = value.isLoggedIn ? 1 : 0;
        stmt.bindLong(6, _tmp);
      }
    };
    this.__preparedStmtOfLogoutAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE user SET is_logged_in = 0";
        return _query;
      }
    };
    this.__preparedStmtOfSetLoggedIn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE user SET is_logged_in = CASE WHEN id_user = ? THEN 1 ELSE 0 END";
        return _query;
      }
    };
  }

  @Override
  public long insert(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUser.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void logoutAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfLogoutAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfLogoutAll.release(_stmt);
    }
  }

  @Override
  public void setLoggedIn(final int idUser) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetLoggedIn.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idUser);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetLoggedIn.release(_stmt);
    }
  }

  @Override
  public int countUsers() {
    final String _sql = "SELECT COUNT(*) FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User login(final String username, final String password) {
    final String _sql = "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "is_logged_in");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfNama)) {
          _result.nama = null;
        } else {
          _result.nama = _cursor.getString(_cursorIndexOfNama);
        }
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _result.username = null;
        } else {
          _result.username = _cursor.getString(_cursorIndexOfUsername);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
        _result.isLoggedIn = _tmp != 0;
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
  public User getLoggedInUser() {
    final String _sql = "SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "is_logged_in");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfNama)) {
          _result.nama = null;
        } else {
          _result.nama = _cursor.getString(_cursorIndexOfNama);
        }
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _result.username = null;
        } else {
          _result.username = _cursor.getString(_cursorIndexOfUsername);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
        _result.isLoggedIn = _tmp != 0;
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
  public User getById(final int idUser) {
    final String _sql = "SELECT * FROM user WHERE id_user = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUser);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "is_logged_in");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfNama)) {
          _result.nama = null;
        } else {
          _result.nama = _cursor.getString(_cursorIndexOfNama);
        }
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _result.username = null;
        } else {
          _result.username = _cursor.getString(_cursorIndexOfUsername);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
        _result.isLoggedIn = _tmp != 0;
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
  public List<User> getAll() {
    final String _sql = "SELECT * FROM user ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "is_logged_in");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        _item = new User();
        _item.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfNama)) {
          _item.nama = null;
        } else {
          _item.nama = _cursor.getString(_cursorIndexOfNama);
        }
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _item.username = null;
        } else {
          _item.username = _cursor.getString(_cursorIndexOfUsername);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _item.password = null;
        } else {
          _item.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _item.role = null;
        } else {
          _item.role = _cursor.getString(_cursorIndexOfRole);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
        _item.isLoggedIn = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
