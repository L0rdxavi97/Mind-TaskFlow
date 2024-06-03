package com.proyectomindtaskflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomindtaskflow.R;


public class create_task extends AppCompatActivity {

    public EditText titulo,descripcion,grupo;
    public CheckBox prioridad;
    private static final String PREFS_NAME = "MyPrefsFile";
    public Button boton,btncan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        prioridad=findViewById(R.id.priortask);
        titulo=findViewById(R.id.tittask);
        descripcion=findViewById(R.id.desctask);
        grupo=findViewById(R.id.grptask);
        int id=getint(this,"user_id",0);
        boton=findViewById(R.id.crttaskbtn);
        btncan=findViewById(R.id.btncanceltask);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit=titulo.getText().toString();
                String d=descripcion.getText().toString();
                String g=grupo.getText().toString();
                boolean p=prioridad.isChecked();
                int prt;
                if(p){
                    prt=1;
                }else{
                    prt=2;
                }
                if(tit.isEmpty() || d.isEmpty()){
                    Toast.makeText(create_task.this,"Titulo y descripcion es obligatorio",Toast.LENGTH_SHORT).show();
                }else{
                    manejadorBDTablas.createtask(tit,d,g,prt,id);
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