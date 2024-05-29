package com.example.proyectomindtaskflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class log_user extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefsFile";
    public Button log;
    public ManejadorBDTablas manejadorBDTablas;
    public TextView nombre;
    private static final String TAG = "LogActivity";
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);
        log = findViewById(R.id.login_btn);
        manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        nombre=findViewById(R.id.userNamelbl);
        nombre.setText(gettext(this,"user_name",""));
        imageView=findViewById(R.id.imageViewlog);
        String n=gettext(this,"user_name","");
        System.out.println(n);
        if(n!=null){
            try {
                Uri imageUri = Uri.parse(n);
                Glide.with(this)
                        .load(imageUri)
                        .into(imageView);
            } catch (Exception e) {
                Log.e(TAG, "Error cargando la imagen con Glide", e);
            }
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intento();
            }
        });
    }

    public static String gettext(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public void intento() {
        Intent intent = new Intent(this, listactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}