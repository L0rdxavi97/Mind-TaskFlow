package com.example.proyectomindtaskflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ManejadorBDTablas{

    private static final String TAG = "ManejadorBDTablas";
    private static final String URL_CREATE_USER = "http://jacecab.000webhostapp.com/create_user.php";
    private static final String URL_CREATE_IDEA = "http://jacecab.000webhostapp.com/create_idea.php";
    private static final String URL_CREATE_TASK = "http://jacecab.000webhostapp.com/create_task.php";

    private static final String URL_GET_USER = "http://jacecab.000webhostapp.com/get_user.php";
    private static final String URL_GET_IDEA = "http://jacecab.000webhostapp.com/get_idea.php";
    private static final String URL_GET_TASK = "http://jacecab.000webhostapp.com/get_task.php";

    private static final String URL_MOD_USER = "http://jacecab.000webhostapp.com/mod_user.php";
    private static final String URL_MOD_IDEA = "http://jacecab.000webhostapp.com/mod_idea.php";
    private static final String URL_MOD_TASK = "http://jacecab.000webhostapp.com/mod_task.php";

    private static final String URL_DELETE_USER = "http://jacecab.000webhostapp.com/delete_user.php";
    private static final String URL_DELETE_IDEA = "http://jacecab.000webhostapp.com/delete_idea.php";
    private static final String URL_DELETE_TASK = "http://jacecab.000webhostapp.com/delete_task.php";

    private Context mContext;
    private RequestQueue mRequestQueue;

    public ManejadorBDTablas(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public void createUser(String username, String password, String frase) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("valor1", username);
            postData.put("valor2", password);
            postData.put("valor3", frase);
            Log.d(TAG, "JSON a enviar: " + postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_USER, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        // Aquí puedes procesar la respuesta del servidor si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());

                        // Verificar el tipo de error
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            // Otro tipo de error
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

                        // Verificar si hay un mensaje de error específico
                        if (error.getMessage() != null) {
                            Log.e(TAG, "Mensaje de error: " + error.getMessage());
                        } else {
                            Log.e(TAG, "Causa del error desconocida");
                        }
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);
    }

    public void getUser(){

    }



}
