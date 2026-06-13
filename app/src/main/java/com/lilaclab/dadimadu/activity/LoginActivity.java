package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.MainActivity;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.User;
import com.lilaclab.dadimadu.utils.DatabaseSeeder;
import com.lilaclab.dadimadu.utils.SessionManager;

public class LoginActivity
extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView txtError;
    private TextView btnLogin;
    private AppDatabase db;
    private SessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.db = AppDatabase.getInstance((Context)this);
        this.sessionManager = new SessionManager((Context)this);
        DatabaseSeeder.seedIfNeeded((Context)this);
        this.edtUsername = (EditText)this.findViewById(R.id.edtUsername);
        this.edtPassword = (EditText)this.findViewById(R.id.edtPassword);
        this.txtError = (TextView)this.findViewById(R.id.txtError);
        this.btnLogin = (TextView)this.findViewById(R.id.btnLogin);
        this.edtUsername.setText((CharSequence)"admin");
        this.edtPassword.setText((CharSequence)"admin123");
        this.btnLogin.setOnClickListener(v -> this.doLogin());
    }

    private void doLogin() {
        String username = this.edtUsername.getText().toString().trim();
        String password = this.edtPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            this.showError("Nama pengguna dan kata sandi wajib diisi.");
            return;
        }
        this.txtError.setVisibility(8);
        this.btnLogin.setEnabled(false);
        this.btnLogin.setText((CharSequence)"Memeriksa...");
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = this.db.userDao().login(username, password);
            if (user != null) {
                this.db.userDao().logoutAll();
                this.db.userDao().setLoggedIn(user.idUser);
            }
            this.runOnUiThread(() -> {
                this.btnLogin.setEnabled(true);
                this.btnLogin.setText((CharSequence)"Masuk ke Sistem");
                if (user == null) {
                    this.showError("Username atau password salah.");
                    return;
                }
                this.sessionManager.saveLogin(user);
                Toast.makeText((Context)this, (CharSequence)("Selamat datang, " + user.nama + "."), (int)0).show();
                this.startActivity(new Intent((Context)this, MainActivity.class));
                this.finish();
            });
        });
    }

    private void showError(String message) {
        this.txtError.setText((CharSequence)message);
        this.txtError.setVisibility(0);
    }
}

