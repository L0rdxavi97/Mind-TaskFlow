package com.proyectomindtaskflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

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

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private static final String URL_CHECK_USERNAME = "http://jacecab.000webhostapp.com/check_userName.php";
    private static final String URL_GET_IDEA_LIKE = "http://jacecab.000webhostapp.com/get_ideaLIKE.php";
    private static final String URL_GET_TASK_LIKE = "http://jacecab.000webhostapp.com/get_taskLIKE.php";

    private static boolean a;
    private static boolean b;
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

    public interface ResponseCallback {
        void onResponse();
    }

    public void createUser(String username, String password, String frase, String imageUrl) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("valor1", username);
            postData.put("valor2", password);
            postData.put("valor3", frase);
            postData.put("imageUrl", imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_USER, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);
    }

    public void getUser(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_USER, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("Filas: "+response.length());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objeto = response.getJSONObject(i);

                                String campo1 = objeto.getString("Nombre");
                                String campo2 = objeto.getString("Contraseña");
                                String campo3 = objeto.getString("Frase_rec");

                                System.out.println(campo1+" "+campo2+" "+campo3);

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
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }

    public static synchronized void change_check_user(boolean ch){
        a=ch;
    }

    public static synchronized boolean get_check_user(){
        return a;
    }

    public static synchronized void change_check_userName(boolean ch){
        b=ch;
    }

    public static synchronized boolean get_check_userName(){
        return b;
    }


    public void check_userName(String nombre, CheckUserNameCallback callback){
        JSONObject postData = new JSONObject();
        try {
            postData.put("valor1", nombre);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CHECK_USERNAME, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        ManejadorBDTablas.change_check_userName(true);
                        callback.onCheckUserNameResult(true);
                        if(ManejadorBDTablas.get_check_userName()){
                            JSONObject user = null;
                            String hint;
                            try {
                                user = response.getJSONObject("user");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                hint= user.getString("Frase_rec");
                                savetext(contexto,"hint",hint);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ManejadorBDTablas.change_check_userName(false);
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
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
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

                        if (error.getMessage() != null) {
                            Log.e(TAG, "Mensaje de error: " + error.getMessage());
                        } else {
                            Log.e(TAG, "Causa del error desconocida");
                        }
                        callback.onCheckUserNameResult(false);

                    }
                });


        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);

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
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        ManejadorBDTablas.change_check_user(true);
                        callback.onCheckUserResult(true);
                        if(ManejadorBDTablas.get_check_user()){
                            JSONObject user = null;
                            int userId;
                            String username;
                            String imagen;
                            try {
                                user = response.getJSONObject("user");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                 userId = user.getInt("id_usuario");
                                 username= user.getString("Nombre");
                                 imagen=user.getString("Imagen");
                                saveint(contexto,"user_id",userId);
                                savetext(contexto,"user_name",username);
                                savetext(contexto,"imagen",imagen);
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
                        ManejadorBDTablas.change_check_user(false);
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
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
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_IDEA, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_TASK, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);
    }



    public void getIdea(final CustomAdapterIdeas arrayAdapter){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_IDEA, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<IdeaWrapper> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(new IdeaWrapper(response.getJSONObject(i)));
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
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }


    public void getTask(final CustomAdapterTareas arrayAdapter){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET_TASK, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TareaWrapper> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(new TareaWrapper(response.getJSONObject(i)));
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
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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


    public void deleteidea(int id,final ResponseCallback callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_DELETE_IDEA, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.d(TAG, "Respuesta del servidor: " + message);

                            if ("success".equals(status)) {
                                Log.d(TAG, "Idea eliminada correctamente");
                            } else {
                                Log.e(TAG, "Error al eliminar la idea: " + message);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al procesar la respuesta JSON: " + e.getMessage());
                        }
                        callback.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);
    }

    public void deletetask(int id,final ResponseCallback callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_DELETE_TASK, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.d(TAG, "Respuesta del servidor: " + message);

                            if ("success".equals(status)) {
                                Log.d(TAG, "Idea eliminada correctamente");
                            } else {
                                Log.e(TAG, "Error al eliminar la tarea: " + message);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al procesar la respuesta JSON: " + e.getMessage());
                        }
                        callback.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);
    }




    public void modidea(String titulo, String descripcion, String grupo, int id_idea){
        JSONObject postData = new JSONObject();
        try {
            postData.put("nuevo_valor1", titulo);
            postData.put("nuevo_valor2", descripcion);
            postData.put("nuevo_valor3", grupo);
            postData.put("id", id_idea);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_MOD_IDEA, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);

    }

    public void modtask(String titulo, String descripcion, String grupo, int prioridad, int id_tarea){
        JSONObject postData = new JSONObject();
        try {
            postData.put("nuevo_valor1", titulo);
            postData.put("nuevo_valor2", descripcion);
            postData.put("nuevo_valor3", grupo);
            postData.put("nuevo_valor4", prioridad);
            postData.put("id", id_tarea);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_MOD_TASK, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta del servidor: " + response.toString());
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        if (error instanceof NoConnectionError) {
                            Log.e(TAG, "Error: No hay conexión a internet");
                        } else if (error instanceof TimeoutError) {
                            Log.e(TAG, "Error: Tiempo de espera agotado");
                        } else if (error instanceof ServerError) {
                            Log.e(TAG, "Error: Error en el servidor");
                        } else if (error instanceof AuthFailureError) {
                            Log.e(TAG, "Error: Autenticación fallida");
                        } else {
                            Log.e(TAG, "Error: " + error.getClass().getSimpleName());
                        }

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

        mRequestQueue.add(request);
    }

    public void getIdeaLIKE(String s, final CustomAdapterIdeas arrayAdapter) {
        String url = URL_GET_IDEA_LIKE + "?search=" + Uri.encode(s);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<IdeaWrapper> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(new IdeaWrapper(response.getJSONObject(i)));
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
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        arrayAdapter.clear();
                        arrayAdapter.notifyDataSetChanged();
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }


    public void getTaskLIKE(String s,final CustomAdapterTareas arrayAdapter){
        String url = URL_GET_TASK_LIKE + "?search=" + Uri.encode(s);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TareaWrapper> ideas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ideas.add(new TareaWrapper(response.getJSONObject(i)));
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
                        Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                        arrayAdapter.clear();
                        arrayAdapter.notifyDataSetChanged();
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMillis,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }



    public void uploadUserData(String username, String password, String frase, Uri imageUri) {
        // Crear un archivo desde la URI de la imagen
        File file = new File(getRealPathFromURI(imageUri));

        // Subir la imagen al servidor
        uploadImage(file, new UploadCallback() {
            @Override
            public void onUploadSuccess(String imageUrl) {
                // Crear el usuario con la URL de la imagen
                createUser(username, password, frase, imageUrl);
            }

            @Override
            public void onUploadFailure(String error) {
                Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al subir la imagen: " + error);
            }
        });
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = contexto.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private interface UploadCallback {
        void onUploadSuccess(String imageUrl);
        void onUploadFailure(String error);
    }

    private void uploadImage(File file, UploadCallback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jacecab.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.uploadImage(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Obtener la URL de la imagen subida desde la respuesta del servidor
                    String imageUrl = "https://jacecab.000webhostapp.com/uploads/" + file.getName();
                    callback.onUploadSuccess(imageUrl);
                } else {
                    callback.onUploadFailure("Error en la respuesta del servidor "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(contexto,"Error en el servidor, vuelve a intentar mas tarde",Toast.LENGTH_SHORT).show();
                callback.onUploadFailure(t.getMessage());
            }
        });
    }
}

