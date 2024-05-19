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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class create_user_activity extends AppCompatActivity {
    private EditText nombre;
    private EditText password;
    private EditText frase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Inicializar vistas
        nombre = findViewById(R.id.ett_new_username);
        password = findViewById(R.id.ett_new_passwd);
        frase = findViewById(R.id.ett_new_r_hint);
        Button boton = findViewById(R.id.create_user_btn);
        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nombre.getText().toString();
                String userPassword = password.getText().toString();
                String hintPhrase = frase.getText().toString();
                if(username!="" && userPassword!="" && hintPhrase!=""){
                    manejadorBDTablas.createUser(username, userPassword, hintPhrase);
                    intento();
                }else {
                    System.out.println("Campos debe estar llenos");
                }

            }
        });
    }

    public void intento() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
