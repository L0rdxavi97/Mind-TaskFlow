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
    public FloatingActionButton boton;
    private CustomAdapterIdeas ideasAdapter;
    private CustomAdapterTareas adapterTareas;
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

        ideasAdapter = new CustomAdapterIdeas(this, new ArrayList<>());
        adapterTareas=new CustomAdapterTareas(this,new ArrayList<>());

        listaview.setAdapter(ideasAdapter);
        visualizar();
        cambiadr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visualizar();
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cambiadr.isChecked()){
                    intento_task();
                }
                else{
                    intento_idea();
                }
            }
        });

        listaview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(listactivity.this);

                if(!cambiadr.isChecked()){
                    IdeaWrapper ideaWrapper = (IdeaWrapper) parent.getItemAtPosition(position);
                    JSONObject jsonObjectidea = ideaWrapper.getJsonObject();
                    builder.setTitle("Idea");
                    try {
                        String message = "Titulo: " + jsonObjectidea.getString("titulo_idea") + "\n" +
                                "Descripcion: " + jsonObjectidea.getString("descripcion") + "\n" +
                                "Grupo: " + jsonObjectidea.getString("grupo_idea") + "\n" +
                                "Fecha: " + jsonObjectidea.getString("fecha");
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
                    builder.setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                manejadorBDTablas.deleteidea(jsonObjectidea.getInt("id_idea"), new ManejadorBDTablas.ResponseCallback() {
                                    @Override
                                    public void onResponse() {
                                        visualizar();
                                        restartActivity();
                                    }
                                });
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            dialogInterface.dismiss();
                        }
                    });

                }else{
                    TareaWrapper tareaWrapper = (TareaWrapper) parent.getItemAtPosition(position);
                    JSONObject jsonObjecttarea = tareaWrapper.getJsonObject();
                    builder.setTitle("Tarea");
                    try {
                        String message = "Titulo: " + jsonObjecttarea.getString("titulo_tarea") + "\n" +
                                "Descripcion: " + jsonObjecttarea.getString("descripcion") + "\n" +
                                "Grupo: " + jsonObjecttarea.getString("grupo_tarea") + "\n" +
                                "Fecha: " + jsonObjecttarea.getString("fecha") + "\n" +
                                "Prioridad: " + jsonObjecttarea.getInt("Prioridad");
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

            }
        });


    }

    public void visualizar(){
        if(!cambiadr.isChecked()){
            idea.setTypeface(idea.getTypeface(), Typeface.BOLD);
            tarea.setTypeface(null, Typeface.NORMAL);
            listaview.setAdapter(ideasAdapter);
            manejadorBDTablas.getIdea(ideasAdapter);
            listaview.setAdapter(ideasAdapter);
        }else{
            idea.setTypeface(null, Typeface.NORMAL);
            tarea.setTypeface(tarea.getTypeface(), Typeface.BOLD);
            listaview.setAdapter(adapterTareas);
            manejadorBDTablas.getTask(adapterTareas);
            listaview.setAdapter(adapterTareas);
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

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}