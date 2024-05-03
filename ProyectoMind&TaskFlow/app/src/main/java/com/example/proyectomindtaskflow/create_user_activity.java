package com.example.proyectomindtaskflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class create_user_activity extends AppCompatActivity {

    public Button boton;
    public EditText nombre;
    public EditText password;
    public EditText frase;
    public ManejadorBDTablas manejadorBDTablas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        manejadorBDTablas=new ManejadorBDTablas(this);
        boton=findViewById(R.id.create_user_btn);
        nombre=findViewById(R.id.ett_new_username);
        password=findViewById(R.id.ett_new_passwd);
        frase=findViewById(R.id.ett_new_r_hint);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejadorBDTablas.insertar_user(nombre.getText().toString(),password.getText().toString(),frase.getText().toString());

                System.out.println("Usuario: "+nombre.getText().toString());
                System.out.println("Password: "+password.getText().toString());
                System.out.println("Frase: "+frase.getText().toString());
            }
        });


    }
}