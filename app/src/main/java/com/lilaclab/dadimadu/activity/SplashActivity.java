package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.LoginActivity;
import com.lilaclab.dadimadu.activity.MainActivity;
import com.lilaclab.dadimadu.utils.DatabaseSeeder;
import com.lilaclab.dadimadu.utils.SessionManager;

public class SplashActivity
extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        DatabaseSeeder.seedIfNeeded((Context)this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> this.goNext(), 2500L);
    }

    private void goNext() {
        SessionManager sessionManager = new SessionManager((Context)this);
        Class target = sessionManager.isLoggedIn() ? MainActivity.class : LoginActivity.class;
        this.startActivity(new Intent((Context)this, target));
        this.finish();
    }
}

