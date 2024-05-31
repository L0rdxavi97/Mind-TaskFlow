package com.example.proyectomindtaskflow;

import android.app.sdksandbox.SandboxedSdk;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private MyReceiver mReceiver = new MyReceiver();
    private static final String PREFS_NAME = "MyPrefsFile";
    public EditText usr;
    public EditText psw;
    public Button logbtn;
    public Button crtbtn,hintbtn;
    public ManejadorBDTablas manejadorBDTablas ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, intentFilter);
        manejadorBDTablas = ManejadorBDTablas.getInstance(this);
        usr=findViewById(R.id.ettUser);
        psw=findViewById(R.id.ettPswd);
        logbtn=findViewById(R.id.logbtn);
        crtbtn=findViewById(R.id.btncrt);
        hintbtn=findViewById(R.id.hintbtn);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usr.getText().toString().isEmpty() || psw.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Fallo en la autenticación del usuario.",Toast.LENGTH_SHORT).show();
                }else{
                    manejadorBDTablas.check_user(usr.getText().toString(), psw.getText().toString(), new CheckUserCallback() {
                        @Override
                        public void onCheckUserResult(boolean success) {
                            if (success) {
                                Toast.makeText(MainActivity.this,"Usuario autentificado correctamente.",Toast.LENGTH_SHORT).show();
                                intento();
                            } else {
                                Toast.makeText(MainActivity.this,"Fallo en la autenticación del usuario.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        crtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intento2();
            }
        });



        hintbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(usr.getText().toString().isEmpty()){
                    dialog("Nombre de usuario es requerido");
                }else{
                    manejadorBDTablas.check_userName(usr.getText().toString(), new CheckUserNameCallback() {
                        @Override
                        public void onCheckUserNameResult(boolean success) {
                            if (success) {
                                dialog(gettext(MainActivity.this,"hint",""));
                            } else {
                                dialog("Nombre de usuario incorrecto");
                            }
                        }
                    });
                }


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
        usr.setText("");
        psw.setText("");
    }

    public static String gettext(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public void dialog(String n){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hint");

        builder.setMessage(n);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}