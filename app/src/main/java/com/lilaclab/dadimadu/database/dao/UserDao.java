package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.User;
import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict=5)
    public long insert(User user);

    @Query(value="SELECT COUNT(*) FROM user")
    public int countUsers();

    @Query(value="SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    public User login(String username, String password);

    @Query(value="UPDATE user SET is_logged_in = 0")
    public void logoutAll();

    @Query(value="UPDATE user SET is_logged_in = CASE WHEN id_user = :idUser THEN 1 ELSE 0 END")
    public void setLoggedIn(int idUser);

    @Query(value="SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1")
    public User getLoggedInUser();

    @Query(value="SELECT * FROM user WHERE id_user = :idUser LIMIT 1")
    public User getById(int idUser);

    @Query(value="SELECT * FROM user ORDER BY nama ASC")
    public List<User> getAll();
}