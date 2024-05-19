package com.example.proyectomindtaskflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManejadorBDTablas{

    public final int timeoutMillis = 10000;
    private static final String PREFS_NAME = "MyPrefsFile";
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

    private static final String URL_CHECK_USER = "http://jacecab.000webhostapp.com/check_user.php";

    private static boolean a;
    private RequestQueue mRequestQueue;
    private static ManejadorBDTablas instance;
    private static Context contexto;

    public ManejadorBDTablas(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ManejadorBDTablas getInstance(Context context) {
        if (instance == null) {
            instance = new ManejadorBDTablas(context);
            contexto=context;
        }
        return instance;
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
                        return;
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


        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);
    }

    public void getUser(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_USER, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON
                        try {
                            // Iterar a través de cada objeto JSON (cada fila de la tabla)
                            System.out.println("Filas: "+response.length());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objeto = response.getJSONObject(i);

                                // Obtener los valores de cada campo
                                String campo1 = objeto.getString("Nombre");
                                String campo2 = objeto.getString("Contraseña");
                                String campo3 = objeto.getString("Frase_rec");

                                // Aquí puedes utilizar los valores como desees (por ejemplo, mostrarlos en un TextView)
                                // textView.setText(campo1 + ", " + campo2);

                                System.out.println(campo1+" "+campo2+" "+campo3);

                                // O puedes almacenar los datos en una lista, adaptador, etc.
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud HTTP
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Agregar la solicitud a la cola
        mRequestQueue.add(request);
    }

    public static synchronized void change_check_user(boolean ch){
        a=ch;
    }

    public static synchronized boolean get_check_user(){
        return a;
    }

    public void check_user(String nombre,String password, CheckUserCallback callback){
        JSONObject postData = new JSONObject();
        try {
            postData.put("valor1", nombre);
            postData.put("valor2", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CHECK_USER, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        ManejadorBDTablas.change_check_user(true);
                        callback.onCheckUserResult(true);
                        if(ManejadorBDTablas.get_check_user()){
                            JSONObject user = null;
                            int userId;
                            String username;
                            try {
                                user = response.getJSONObject("user");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                 userId = user.getInt("id_usuario");
                                 username= user.getString("Nombre");
                                saveint(contexto,"user_id",userId);
                                savetext(contexto,"user_name",username);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Log.d(TAG, "Id:" + getint(contexto,"user_id",0));
                            Log.d(TAG, "Nombre:" + gettext(contexto,"user_name",""));
                        }
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        ManejadorBDTablas.change_check_user(false);
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());

                        // Verificar el tipo de error
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.d(TAG, "Error: Autenticación fallida");
                            Log.d(TAG, "Error: Usuario incorrecto");
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
                        callback.onCheckUserResult(false);

                    }
                });


        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);

    }

    public void createidea(String titulo, String descripcion, String grupo, int id_usuario){
        JSONObject postData = new JSONObject();
        String fecha=obtenerFechaActual();
        try {
            postData.put("valor1", titulo);
            postData.put("valor2", descripcion);
            postData.put("valor3", fecha);
            postData.put("valor4", grupo);
            postData.put("valor5", id_usuario);
            Log.d(TAG, "JSON a enviar: " + postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_IDEA, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        // Aquí puedes procesar la respuesta del servidor si es necesario
                        return;
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


        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);

    }


    public void createtask(String titulo, String descripcion, String grupo, int prioridad, int id_usuario){
        JSONObject postData = new JSONObject();
        String fecha=obtenerFechaActual();
        try {
            postData.put("valor1", titulo);
            postData.put("valor2", descripcion);
            postData.put("valor3", fecha);
            postData.put("valor4", grupo);
            postData.put("valor5", prioridad);
            postData.put("valor6", id_usuario);
            Log.d(TAG, "JSON a enviar: " + postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_TASK, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        // Aquí puedes procesar la respuesta del servidor si es necesario
                        return;
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


        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola de solicitudes
        mRequestQueue.add(request);
    }



    public void getIdea(final ArrayAdapter<JSONObject> arrayAdapter){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_IDEA, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<JSONObject> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(response.getJSONObject(i));
                            }
                            arrayAdapter.clear();
                            arrayAdapter.addAll(ideas);
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud HTTP
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola
        mRequestQueue.add(request);
    }


    public void getTask(final ArrayAdapter<JSONObject> arrayAdapter){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_TASK, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<JSONObject> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(response.getJSONObject(i));
                            }
                            arrayAdapter.clear();
                            arrayAdapter.addAll(ideas);
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud HTTP
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Agregar la solicitud a la cola
        mRequestQueue.add(request);
    }

    public static void saveint(Context context, String key, int text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, text);
        editor.apply();
    }

    public static int getint(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void savetext(Context context, String key, String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static String gettext(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }


    public static String obtenerFechaActual() {
        return LocalDate.now().toString();
    }



}
