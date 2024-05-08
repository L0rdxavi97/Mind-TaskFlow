package com.example.proyectomindtaskflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);
    }



}
