package com.example.proyectomindtaskflow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class listactivity extends AppCompatActivity {

    public ListView listaview;
    public ManejadorBDTablas manejadorBDTablas;
    public Switch cambiadr;
    public ArrayList<Ideacl> ideas;
    public ArrayList<Taskcl> tareas;
    public FloatingActionButton boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        manejadorBDTablas = ManejadorBDTablas.getInstance(this);
        listaview=findViewById(R.id.listaview);
        cambiadr=findViewById(R.id.cambiador);
        boton=findViewById(R.id.floatbtn);
        //visualizar(manejadorBDTablas);
        cambiadr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("cambiado");
                System.out.println(cambiadr.isChecked());
                //visualizar(manejadorBDTablas);
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cambiadr.isChecked()){
                    System.out.println("Task");
                    intento_task();
                }
                else{
                    System.out.println("Mind");
                    intento_idea();
                }
            }
        });


    }

    public void visualizar(ManejadorBDTablas manejadorBDTablas){
        if(cambiadr.isChecked()){

        }else{

        }
    }


    public void intento_idea() {
        Intent intent = new Intent(this, create_idea.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void intento_task() {
        Intent intent = new Intent(this, create_task.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}