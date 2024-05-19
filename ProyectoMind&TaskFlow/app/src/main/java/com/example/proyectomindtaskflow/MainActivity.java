package com.example.proyectomindtaskflow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manejadorBDTablas = ManejadorBDTablas.getInstance(this);
        usr=findViewById(R.id.ettUser);
        psw=findViewById(R.id.ettPswd);
        logbtn=findViewById(R.id.logbtn);
        crtbtn=findViewById(R.id.btncrt);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usr.getText().toString()!="" && psw.getText().toString()!="")
                {
                    manejadorBDTablas.check_user(usr.getText().toString(), psw.getText().toString(), new CheckUserCallback() {
                    @Override
                    public void onCheckUserResult(boolean success) {
                        if (success) {
                            Log.d("MainActivity", "Usuario autenticado correctamente.");
                            intento();
                            // Lógica adicional para usuario autenticado
                        } else {
                            Log.d("MainActivity", "Fallo en la autenticación del usuario.");
                            // Lógica adicional para fallo en la autenticación
                        }
                    }
                });
                }else{
                    Log.d("MainActivity", "Fallo en la autenticación del usuario.");
                }
                //manejadorBDTablas.check_user(usr.getText().toString(),psw.getText().toString());
                //boolean a= ManejadorBDTablas.get_check_user();
                System.out.println("log Usuario: "+usr.getText());
                System.out.println("log passwd: "+psw.getText());
                //System.out.println(a);
//                if(a){
//                    intento();
//                }

            }
        });

        crtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Crear Usuario: "+usr.getText());
                System.out.println("Crear passwd: "+psw.getText());
                intento2();
            }
        });

    }


    public void intento() {
        Intent intent = new Intent(this, log_user.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void intento2() {
        Intent intent = new Intent(this, create_user_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificar el estado de la autenticación
        boolean isAuthenticated = ManejadorBDTablas.get_check_user();
        Log.d("MainActivity", "Usuario autenticado: " + isAuthenticated);
    }
}