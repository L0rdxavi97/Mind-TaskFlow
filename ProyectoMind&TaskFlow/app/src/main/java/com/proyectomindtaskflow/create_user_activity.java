package com.proyectomindtaskflow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectomindtaskflow.R;


public class create_user_activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    public EditText nombre;
    public EditText password;
    public EditText frase;
    public ImageView imagen;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Inicializar vistas
        nombre = findViewById(R.id.ett_new_username);
        password = findViewById(R.id.ett_new_passwd);
        frase = findViewById(R.id.ett_new_r_hint);
        imagen=findViewById(R.id.newuserimg);
        Button boton = findViewById(R.id.create_user_btn);
        Button botoncan = findViewById(R.id.btncancel);
        ManejadorBDTablas manejadorBDTablas= ManejadorBDTablas.getInstance(getApplicationContext());
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nombre.getText().toString();
                String userPassword = password.getText().toString();
                String hintPhrase = frase.getText().toString();
                if(nombre.getText().toString().isEmpty() || password.getText().toString().isEmpty() || frase.getText().toString().isEmpty() || imageUri == null){
                    Toast.makeText(create_user_activity.this,"Los campos deben estar llenos",Toast.LENGTH_SHORT).show();
                }else {
                    manejadorBDTablas.check_userName(nombre.getText().toString(), new CheckUserNameCallback() {
                        @Override
                        public void onCheckUserNameResult(boolean success) {
                            if (success) {
                                Toast.makeText(create_user_activity.this,"Nombre ya usado",Toast.LENGTH_SHORT).show();
                            } else {
                                manejadorBDTablas.uploadUserData(username, userPassword, hintPhrase, imageUri);
                                intento();
                            }
                        }
                    });

                }

            }
        });

        botoncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intento();
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    public void intento() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }



    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nombre.setText("");
        password.setText("");
        frase.setText("");
    }
}
