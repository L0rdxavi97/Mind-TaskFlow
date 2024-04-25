package com.example.proyectomindtaskflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public EditText usr;
    public EditText psw;
    public Button logbtn;
    public Button crtbtn;
    public ManejadorBDTablas manejadorBDTablas ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manejadorBDTablas = new ManejadorBDTablas(this);
        usr=findViewById(R.id.ettUser);
        psw=findViewById(R.id.ettPswd);
        logbtn=findViewById(R.id.logbtn);
        crtbtn=findViewById(R.id.btncrt);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Inicio Usuario: "+usr.getText());
                System.out.println("Inicio passwd: "+psw.getText());

                intento();
            }
        });

        crtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Crear Usuario: "+usr.getText());
                System.out.println("Crear passwd: "+psw.getText());


                manejadorBDTablas.insertar_user(usr.getText().toString(),psw.getText().toString(),"PEPE","PACO");
            }
        });

    }


    public void intento() {
        Intent intent = new Intent(this, log_user.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}