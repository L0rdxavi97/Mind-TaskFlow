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


public class modtask extends AppCompatActivity {
    public EditText titulo,descripcion,grupo;
    private static final String PREFS_NAME = "MyPrefsFile";
    public Button boton,btncan;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modtask);

        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        titulo=findViewById(R.id.ntittask);
        descripcion=findViewById(R.id.ndesctask);
        grupo=findViewById(R.id.ngrptask);
        cb=findViewById(R.id.npriortask);
        int id=getint(this,"id",0);
        String titu=gettext(this,"titulo","");
        String desc=gettext(this,"descripcion","");
        String grop=gettext(this,"grupo","");
        int prior=getint(this,"prior",0);
        if(prior==1){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        titulo.setText(titu);
        descripcion.setText(desc);
        grupo.setText(grop);
        boton=findViewById(R.id.updtaskbtn);
        btncan=findViewById(R.id.canupdtaskbtn);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit=titulo.getText().toString();
                String d=descripcion.getText().toString();
                String g=grupo.getText().toString();
                int p;
                if(cb.isChecked()){
                    p=1;
                }else{
                    p=2;
                }

                if(tit!="" && d!=""){
                    manejadorBDTablas.modtask(tit,d,g,p,id);
                    intento();
                }else{
                    Toast.makeText(modtask.this,"Titulo y descripcion es obligatorio",Toast.LENGTH_SHORT).show();
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