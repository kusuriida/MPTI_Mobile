package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName="user", indices={@Index(value={"username"}, unique=true)})
public class User {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id_user")
    public int idUser;
    public String nama;
    public String username;
    public String password;
    public String role;
    @ColumnInfo(name="is_logged_in")
    public boolean isLoggedIn;

    public User() {
    }

    @Ignore
    public User(String nama, String username, String password, String role, boolean isLoggedIn) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isLoggedIn = isLoggedIn;
    }
}

