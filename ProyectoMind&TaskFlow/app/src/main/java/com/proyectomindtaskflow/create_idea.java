package com.proyectomindtaskflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomindtaskflow.R;


public class create_idea extends AppCompatActivity {

    public EditText titulo,descripcion,grupo;
    private static final String PREFS_NAME = "MyPrefsFile";
    public Button boton,btncan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_idea);
        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        titulo=findViewById(R.id.titidea);
        descripcion=findViewById(R.id.descidea);
        grupo=findViewById(R.id.grpidea);
        int id=getint(this,"user_id",0);
        boton=findViewById(R.id.crtideabtn);
        btncan=findViewById(R.id.btncancelid);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit=titulo.getText().toString();
                String d=descripcion.getText().toString();
                String g=grupo.getText().toString();


                if(tit.isEmpty() || d.isEmpty()){
                    Toast.makeText(create_idea.this,"Titulo y descripcion es obligatorio",Toast.LENGTH_SHORT).show();
                }else{
                    manejadorBDTablas.createidea(tit,d,g,id);
                    intento();
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

    public void intento() {
        Intent intent = new Intent(this, listactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        titulo.setText("");
        descripcion.setText("");
        grupo.setText("");
    }
}