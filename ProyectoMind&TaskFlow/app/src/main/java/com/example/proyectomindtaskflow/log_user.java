package com.example.proyectomindtaskflow;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class log_user extends AppCompatActivity {

    public Button log;
    public ManejadorBDTablas manejadorBDTablas;
    private static final String TAG = "LogActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);
        log = findViewById(R.id.login_btn);
        manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejadorBDTablas.getUser();
            }
        });
    }
}