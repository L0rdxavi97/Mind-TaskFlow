package com.example.proyectomindtaskflow;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class listactivity extends AppCompatActivity {

    public ListView listaview;
    public ManejadorBDTablas manejadorBDTablas;
    public Switch cambiadr;
    public ArrayList<Ideacl> ideas;
    public ArrayList<Taskcl> tareas;
    public FloatingActionButton boton;
    public ArrayAdapter<JSONObject> adaptador;
    public TextView tarea,idea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        manejadorBDTablas = ManejadorBDTablas.getInstance(this);
        listaview=findViewById(R.id.listaview);
        cambiadr=findViewById(R.id.cambiador);
        boton=findViewById(R.id.floatbtn);
        tarea=findViewById(R.id.textViewtarea);
        idea=findViewById(R.id.textViewidea);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<JSONObject>());
        listaview.setAdapter(adaptador);


        visualizar();
        cambiadr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("cambiado");
                System.out.println(cambiadr.isChecked());
                visualizar();
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

        listaview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(listactivity.this);
                if(!cambiadr.isChecked()){
                    builder.setTitle("Idea");
                    try {
                        String message = "Titulo: " + adaptador.getItem(position).getString("titulo_idea") + "\n" +
                                "Descripcion: " + adaptador.getItem(position).getString("descripcion") + "\n" +
                                "Grupo: " + adaptador.getItem(position).getString("grupo_idea") + "\n" +
                                "Fecha: " + adaptador.getItem(position).getString("fecha");
                        builder.setMessage(message);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }else{
                    builder.setTitle("Tarea");
                    try {
                        String message = "Titulo: " + adaptador.getItem(position).getString("titulo_tarea") + "\n" +
                                "Descripcion: " + adaptador.getItem(position).getString("descripcion") + "\n" +
                                "Grupo: " + adaptador.getItem(position).getString("grupo_tarea") + "\n" +
                                "Fecha: " + adaptador.getItem(position).getString("fecha") + "\n" +
                                "Prioridad: " + adaptador.getItem(position).getString("Prioridad");
                        builder.setMessage(message);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }

                AlertDialog dialog = builder.create();
                dialog.show();


                String name;
                try {
                    name=adaptador.getItem(position).getString("descripcion");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(name);

            }
        });


    }

    public void visualizar(){
        if(!cambiadr.isChecked()){
            idea.setTypeface(idea.getTypeface(), Typeface.BOLD);
            tarea.setTypeface(null, Typeface.NORMAL);
            manejadorBDTablas.getIdea(adaptador);
        }else{
            idea.setTypeface(null, Typeface.NORMAL);
            tarea.setTypeface(tarea.getTypeface(), Typeface.BOLD);
            manejadorBDTablas.getTask(adaptador);
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

    @Override
    protected void onResume() {
        super.onResume();
        visualizar();
    }
}