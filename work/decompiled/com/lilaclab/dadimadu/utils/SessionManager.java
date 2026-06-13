/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  com.lilaclab.dadimadu.database.entity.User
 *  com.lilaclab.dadimadu.utils.SessionManager
 */
package com.lilaclab.dadimadu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.lilaclab.dadimadu.database.entity.User;

public class SessionManager {
    private static final String PREF_NAME = "dadi_madu_session";
    private static final String KEY_LOGIN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public void saveLogin(User user) {
        this.preferences.edit().putBoolean(KEY_LOGIN, true).putInt(KEY_USER_ID, user.idUser).putString(KEY_NAMA, user.nama).putString(KEY_USERNAME, user.username).putString(KEY_ROLE, user.role).apply();
    }

    public boolean isLoggedIn() {
        return this.preferences.getBoolean(KEY_LOGIN, false);
    }

    public int getUserId() {
        return this.preferences.getInt(KEY_USER_ID, 0);
    }

    public String getNama() {
        return this.preferences.getString(KEY_NAMA, "Admin");
    }

    public String getUsername() {
        return this.preferences.getString(KEY_USERNAME, "admin");
    }

    public String getRole() {
        return this.preferences.getString(KEY_ROLE, "pemilik");
    }

    public void clear() {
        this.preferences.edit().clear().apply();
    }
}

