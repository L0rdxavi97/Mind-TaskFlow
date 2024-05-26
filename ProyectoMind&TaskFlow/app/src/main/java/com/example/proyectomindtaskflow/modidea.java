package com.example.proyectomindtaskflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class modidea extends AppCompatActivity {

    public EditText titulo,descripcion,grupo;
    private static final String PREFS_NAME = "MyPrefsFile";
    public Button boton,btncan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modidea);

        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        titulo=findViewById(R.id.ntitleidea);
        descripcion=findViewById(R.id.ndescriptionidea);
        grupo=findViewById(R.id.ngroupidea);
        int id=getint(this,"id",0);
        String titu=gettext(this,"titulo","");
        String desc=gettext(this,"descripcion","");
        String grop=gettext(this,"grupo","");
        titulo.setText(titu);
        descripcion.setText(desc);
        grupo.setText(grop);
        boton=findViewById(R.id.updideabtn);
        btncan=findViewById(R.id.canupdideabtn);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit=titulo.getText().toString();
                String d=descripcion.getText().toString();
                String g=grupo.getText().toString();


                if(tit!="" && d!=""){
                    manejadorBDTablas.modidea(tit,d,g,id);
                    intento();
                }else{
                    System.out.println("Titulo y descripcion son obligatorios");
                }
            }
        });

        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intento();
            }
        });

    }

    public static int getint(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
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