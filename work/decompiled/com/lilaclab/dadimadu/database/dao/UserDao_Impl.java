/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  androidx.annotation.NonNull
 *  androidx.room.EntityInsertionAdapter
 *  androidx.room.RoomDatabase
 *  androidx.room.RoomSQLiteQuery
 *  androidx.room.SharedSQLiteStatement
 *  androidx.room.util.CursorUtil
 *  androidx.room.util.DBUtil
 *  androidx.sqlite.db.SupportSQLiteQuery
 *  androidx.sqlite.db.SupportSQLiteStatement
 *  com.lilaclab.dadimadu.database.dao.UserDao
 *  com.lilaclab.dadimadu.database.dao.UserDao_Impl
 *  com.lilaclab.dadimadu.database.entity.User
 */
package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.dao.UserDao;
import com.lilaclab.dadimadu.database.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UserDao_Impl
implements UserDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<User> __insertionAdapterOfUser;
    private final SharedSQLiteStatement __preparedStmtOfLogoutAll;
    private final SharedSQLiteStatement __preparedStmtOfSetLoggedIn;

    public UserDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfUser = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfLogoutAll = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfSetLoggedIn = new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insert(User user) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long _result = this.__insertionAdapterOfUser.insertAndReturnId((Object)user);
            this.__db.setTransactionSuccessful();
            long l = _result;
            return l;
        }
        finally {
            this.__db.endTransaction();
        }
    }

    public void logoutAll() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfLogoutAll.acquire();
        try {
            this.__db.beginTransaction();
            try {
                _stmt.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
            }
            finally {
                this.__db.endTransaction();
            }
        }
        finally {
            this.__preparedStmtOfLogoutAll.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setLoggedIn(int idUser) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfSetLoggedIn.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)idUser);
        try {
            this.__db.beginTransaction();
            try {
                _stmt.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
            }
            finally {
                this.__db.endTransaction();
            }
        }
        finally {
            this.__preparedStmtOfSetLoggedIn.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int countUsers() {
        String _sql = "SELECT COUNT(*) FROM user";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM user", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _result = _cursor.moveToFirst() ? _cursor.getInt(0) : 0;
            int n = _result;
            return n;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public User login(String username, String password) {
        String _sql = "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1", (int)2);
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
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            User _result;
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama");
            int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"username");
            int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"password");
            int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"role");
            int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"is_logged_in");
            if (_cursor.moveToFirst()) {
                _result = new User();
                _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _result.nama = _cursor.isNull(_cursorIndexOfNama) ? null : _cursor.getString(_cursorIndexOfNama);
                _result.username = _cursor.isNull(_cursorIndexOfUsername) ? null : _cursor.getString(_cursorIndexOfUsername);
                _result.password = _cursor.isNull(_cursorIndexOfPassword) ? null : _cursor.getString(_cursorIndexOfPassword);
                _result.role = _cursor.isNull(_cursorIndexOfRole) ? null : _cursor.getString(_cursorIndexOfRole);
                int _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
                _result.isLoggedIn = _tmp != 0;
            } else {
                _result = null;
            }
            User user = _result;
            return user;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public User getLoggedInUser() {
        String _sql = "SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            User _result;
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama");
            int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"username");
            int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"password");
            int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"role");
            int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"is_logged_in");
            if (_cursor.moveToFirst()) {
                _result = new User();
                _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _result.nama = _cursor.isNull(_cursorIndexOfNama) ? null : _cursor.getString(_cursorIndexOfNama);
                _result.username = _cursor.isNull(_cursorIndexOfUsername) ? null : _cursor.getString(_cursorIndexOfUsername);
                _result.password = _cursor.isNull(_cursorIndexOfPassword) ? null : _cursor.getString(_cursorIndexOfPassword);
                _result.role = _cursor.isNull(_cursorIndexOfRole) ? null : _cursor.getString(_cursorIndexOfRole);
                int _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
                _result.isLoggedIn = _tmp != 0;
            } else {
                _result = null;
            }
            User user = _result;
            return user;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public User getById(int idUser) {
        String _sql = "SELECT * FROM user WHERE id_user = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM user WHERE id_user = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        _statement.bindLong(_argIndex, (long)idUser);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            User _result;
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama");
            int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"username");
            int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"password");
            int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"role");
            int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"is_logged_in");
            if (_cursor.moveToFirst()) {
                _result = new User();
                _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _result.nama = _cursor.isNull(_cursorIndexOfNama) ? null : _cursor.getString(_cursorIndexOfNama);
                _result.username = _cursor.isNull(_cursorIndexOfUsername) ? null : _cursor.getString(_cursorIndexOfUsername);
                _result.password = _cursor.isNull(_cursorIndexOfPassword) ? null : _cursor.getString(_cursorIndexOfPassword);
                _result.role = _cursor.isNull(_cursorIndexOfRole) ? null : _cursor.getString(_cursorIndexOfRole);
                int _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
                _result.isLoggedIn = _tmp != 0;
            } else {
                _result = null;
            }
            User user = _result;
            return user;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<User> getAll() {
        String _sql = "SELECT * FROM user ORDER BY nama ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM user ORDER BY nama ASC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama");
            int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"username");
            int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"password");
            int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"role");
            int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"is_logged_in");
            ArrayList<User> _result = new ArrayList<User>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                User _item = new User();
                _item.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _item.nama = _cursor.isNull(_cursorIndexOfNama) ? null : _cursor.getString(_cursorIndexOfNama);
                _item.username = _cursor.isNull(_cursorIndexOfUsername) ? null : _cursor.getString(_cursorIndexOfUsername);
                _item.password = _cursor.isNull(_cursorIndexOfPassword) ? null : _cursor.getString(_cursorIndexOfPassword);
                _item.role = _cursor.isNull(_cursorIndexOfRole) ? null : _cursor.getString(_cursorIndexOfRole);
                int _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
                _item.isLoggedIn = _tmp != 0;
                _result.add(_item);
            }
            ArrayList<User> arrayList = _result;
            return arrayList;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    @NonNull
    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}

