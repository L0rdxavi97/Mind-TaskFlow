package com.example.proyectomindtaskflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ManejadorBDTablas extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MindFlow.db";
    private static final String COL_ID_USER="ID_USER";
    private static final String COL_USER="USERNAME";
    private static final String COL_PASWD="PASSWORD";
    private static final String COL_FRAS_REC_PASWD="F_REC_PASWD";
    private static final String COL_IMG="IMAGEN";
    private static final String COL_ID_IDEA="ID_IDEA";
    private static final String COL_TIT_IDEA="TITLE_IDEA";
    private static final String COL_DESC_IDEA="DESCRIPTION_IDEA";
    private static final String COL_DATE_IDEA="DATE_IDEA";
    private static final String COL_GROUP_IDEA="GROUP_IDEA";
    private static final String COL_ID_TASK="ID_TASK";
    private static final String COL_TIT_TASK="TITLE_TASK";
    private static final String COL_DESC_TASK="DESCRIPTION_TASK";
    private static final String COL_DATE_TASK="DATE_TASK";
    private static final String COL_GROUP_TASK="GROUP_TASK";
    private static final String COL_PRIOR_TASK="PRIORITY_TASK";
    private static final String TABLE_USER="USERS";
    private static final String TABLE_IDEA="IDEAS";
    private static final String TABLE_TASK="TASKS";



    private static final String COL_ID="ID";
    private static final String COL_PREGUNTA="PREGUNTA";
    private static final String COL_OPCIONCORRECTA="OPCIONCORRECTA";
    private static final String COL_OPCIONINCORRECTA1="OPCIONINCORRECTA1";
    private static final String COL_OPCIONINCORRECTA2="OPCIONINCORRECTA2";
    private static final String TABLE_NAME="PREGUNTAS";
    private static final String TABLE_NAME2="LOGROS";
    private static final String COL_FECHA="FECHA";
    private static final String COL_PUNTUACION="PUNTUACION";
    private static final String TABLE_NAME3="ENTRADAS";
    private static final String COL_FECHA_EN="FECHA_EN";
    private static final String COL_BATERIA="BATERIA";
    private static final String COL_POSICION="POSICION";
    private static final String COL_CONT="CONTADOR";

    public ManejadorBDTablas(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public ManejadorBDTablas(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + " ("
                + COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USER + "TEXT,"
                + COL_PASWD + "TEXT,"
                + COL_FRAS_REC_PASWD + "TEXT,"
                + COL_IMG + "BLOB"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_IDEA + " ("
                + COL_ID_IDEA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TIT_IDEA + "TEXT,"
                + COL_DESC_IDEA + "TEXT,"
                + COL_DATE_IDEA + "TEXT,"
                + COL_GROUP_IDEA + "TEXT"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TASK + " ("
                + COL_ID_TASK + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TIT_TASK + "TEXT,"
                + COL_DESC_TASK + "TEXT,"
                + COL_DATE_TASK + "TEXT,"
                + COL_GROUP_TASK + "TEXT,"
                + COL_PRIOR_TASK + "BIT"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PREGUNTA + " TEXT,"
                + COL_OPCIONCORRECTA + " TEXT,"
                + COL_OPCIONINCORRECTA1 + " TEXT,"
                + COL_OPCIONINCORRECTA2 + " TEXT"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME2 + " ("
                + COL_FECHA + " TEXT,"
                + COL_PUNTUACION + " TEXT,"
                + COL_CONT + " TEXT"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME3 + " ("
                + COL_FECHA_EN + " TEXT,"
                + COL_BATERIA + " TEXT,"
                + COL_POSICION + " TEXT"
                + ")");
    }

    public boolean insertar_user(String user,String passw, String frase, String imagen){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_USER,user);
        contentValues.put(COL_PASWD,passw);
        contentValues.put(COL_FRAS_REC_PASWD,frase);
        contentValues.put(COL_IMG, imagen);

        long resultado=sqLiteDatabase.insert(TABLE_USER,null,contentValues);
        sqLiteDatabase.close();
        return (resultado!=-1);
    }

    public boolean insertar(String pregunta,String correcta, String incorrecta1, String incorrecta2){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_PREGUNTA,pregunta);
        contentValues.put(COL_OPCIONCORRECTA,correcta);
        contentValues.put(COL_OPCIONINCORRECTA1,incorrecta1);
        contentValues.put(COL_OPCIONINCORRECTA2,incorrecta2);

        long resultado=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return (resultado!=-1);
    }

    public Cursor listar(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_USER,null);
        return cursor;
    }


    public boolean actualizar(String id,String pregunta,String correcta, String incorrecta1, String incorrecta2){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_PREGUNTA,pregunta);
        contentValues.put(COL_OPCIONCORRECTA,correcta);
        contentValues.put(COL_OPCIONINCORRECTA1,incorrecta1);
        contentValues.put(COL_OPCIONINCORRECTA2,incorrecta2);

        long resultado=sqLiteDatabase.update(TABLE_NAME,contentValues,COL_ID+"=?",new String[]{id});
        sqLiteDatabase.close();

        return (resultado>0);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean borrar(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int borradas=sqLiteDatabase.delete(TABLE_NAME,COL_ID+"=?",new String[]{id});
        sqLiteDatabase.close();
        return borradas>0;
    }

    public Cursor getcount(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT "+COL_CONT+" FROM "+TABLE_NAME2+" ORDER BY "+COL_FECHA+" DESC LIMIT 1",null);
        return cursor;
    }


    public int cantidadpreguntas(){
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = 0;

        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                rowCount = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }

        return rowCount;
    }

    public int cantidadlogros(){
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = 0;

        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME2;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                rowCount = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }

        return rowCount;
    }


    public boolean insertarlogros(String fecha,String puntuacion,String cont){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_FECHA,fecha);
        contentValues.put(COL_PUNTUACION,puntuacion);
        contentValues.put(COL_CONT,cont);

        long resultado=sqLiteDatabase.insert(TABLE_NAME2,null,contentValues);
        sqLiteDatabase.close();
        return (resultado!=-1);
    }

    public boolean insertarentradas(String fecha_en,String bateria,String posicion){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_FECHA_EN,fecha_en);
        contentValues.put(COL_BATERIA,bateria);
        contentValues.put(COL_POSICION,posicion);

        long resultado=sqLiteDatabase.insert(TABLE_NAME3,null,contentValues);
        sqLiteDatabase.close();
        return (resultado!=-1);
    }

    public boolean borrarlogros(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int borradas=sqLiteDatabase.delete(TABLE_NAME2,null,null);
        sqLiteDatabase.close();
        return borradas>0;
    }

    public boolean borrarentradas(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int borradas=sqLiteDatabase.delete(TABLE_NAME3,null,null);
        sqLiteDatabase.close();
        return borradas>0;
    }

    public Cursor listarlogros(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT "+COL_FECHA+","+COL_PUNTUACION+" FROM "+TABLE_NAME2,null);
        return cursor;
    }

    public Cursor listarentradas(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT "+COL_POSICION+" FROM "+TABLE_NAME3,null);
        return cursor;
    }

    public Cursor ultimaologro(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME2+" ORDER BY "+COL_FECHA+" DESC LIMIT 1",null);
        return cursor;
    }

    public List<String> obtenerDatosAleatorios(int cantidad) {
        List<String> resultados = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY RANDOM() LIMIT " + cantidad, null);
            if (cursor.moveToFirst()) {
                do {
                    resultados.add(cursor.getString(1));
                    resultados.add(cursor.getString(2));
                    resultados.add(cursor.getString(3));
                    resultados.add(cursor.getString(4));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return resultados;
    }
}
