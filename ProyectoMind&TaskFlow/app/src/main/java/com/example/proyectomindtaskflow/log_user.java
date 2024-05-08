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
        manejadorBDTablas=new ManejadorBDTablas(this);
        
//        log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("Boton log pulsado");
//                Cursor cursor=manejadorBDTablas.listar_users();
//
//                if (cursor != null) {
//                    try {
//                        while (cursor.moveToNext()) {
//                            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID_USER"));
//                            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("USERNAME"));
//                            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
//
//                            Log.d(TAG, "ID: " + id + ", Nombre: " + nombre + ", Password: " + password);
//                        }
//                    } finally {
//                        cursor.close();
//                    }
//                }
//            }
//        });

    }
}