package com.example.proyectomindtaskflow;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class log_user extends AppCompatActivity {

    public Button log = findViewById(R.id.login_btn);
    ManejadorBDTablas manejadorBDTablas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=manejadorBDTablas.listar();
                while(cursor.moveToNext()){
                    System.out.println("user: "+cursor.getString(0)+" password: "+cursor.getString(1));
                }
            }
        });

    }
}