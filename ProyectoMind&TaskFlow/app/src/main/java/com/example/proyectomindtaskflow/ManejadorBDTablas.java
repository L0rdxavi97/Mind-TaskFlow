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


    public ManejadorBDTablas(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public ManejadorBDTablas(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + " ("
                + COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USER + " TEXT, "
                + COL_PASWD + " TEXT, "
                + COL_FRAS_REC_PASWD + " TEXT"
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_IDEA + " ("
                + COL_ID_IDEA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TIT_IDEA + " TEXT, "
                + COL_DESC_IDEA + " TEXT, "
                + COL_DATE_IDEA + " TEXT, "
                + COL_GROUP_IDEA + " TEXT "
                + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TASK + " ("
                + COL_ID_TASK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TIT_TASK + " TEXT, "
                + COL_DESC_TASK + " TEXT, "
                + COL_DATE_TASK + " TEXT, "
                + COL_GROUP_TASK + " TEXT, "
                + COL_PRIOR_TASK + " BIT "
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean insertar_user(String user,String passw, String frase){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_USER,user);
        contentValues.put(COL_PASWD,passw);
        contentValues.put(COL_FRAS_REC_PASWD,frase);

        long resultado=sqLiteDatabase.insert(TABLE_USER,null,contentValues);
        sqLiteDatabase.close();
        return (resultado!=-1);
    }

    public Cursor listar_users(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_USER,null);
        return cursor;
    }

    public boolean borrar_user(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int borradas=sqLiteDatabase.delete(TABLE_USER,COL_ID_USER+"=?",new String[]{id});
        sqLiteDatabase.close();
        return borradas>0;
    }

    public boolean actualizar_idea(String id,String date,String titulo, String description, String group){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_TIT_IDEA,titulo);
        contentValues.put(COL_DESC_IDEA,description);
        contentValues.put(COL_DATE_IDEA,date);
        contentValues.put(COL_GROUP_IDEA,group);

        long resultado=sqLiteDatabase.update(TABLE_IDEA,contentValues,COL_ID_IDEA+"=?",new String[]{id});
        sqLiteDatabase.close();

        return (resultado>0);
    }

    public boolean actualizar_tarea(String id,String date,String titulo, String description, String group, String prior){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_TIT_TASK,titulo);
        contentValues.put(COL_DESC_TASK,description);
        contentValues.put(COL_DATE_TASK,date);
        contentValues.put(COL_GROUP_TASK,group);
        contentValues.put(COL_PRIOR_TASK,prior);

        long resultado=sqLiteDatabase.update(TABLE_TASK,contentValues,COL_ID_TASK+"=?",new String[]{id});
        sqLiteDatabase.close();

        return (resultado>0);
    }

}
