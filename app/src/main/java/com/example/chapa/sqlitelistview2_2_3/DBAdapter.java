package com.example.chapa.sqlitelistview2_2_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;


/**
 * Created by chapa on 17/09/2017.
 */

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String KEY_PHONE = "phone";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, email text not null, phone text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;


    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    //---Abrir la base de datos---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---Cerrar la base de datos---
    public void close()
    {
        DBHelper.close();
    }

    //---Insertar n contacto en la base de datos--
    public long insertContact(String name, String email, String phone) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PHONE, phone);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---Borrar un contacto en particular ---
    public boolean deleteContactById(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public boolean deleteContactByName(String NAME) {
        return db.delete(DATABASE_TABLE, KEY_NAME+ "=" + NAME, null) > 0;
    }

    public int getIdByName(String name){
        Vector vt=getAllContactsVector(getContactByName(name));
        return Integer.parseInt(vt.get(0).toString());
    }

    //---Ver cuantos contactos existen---
    public int lengthQuery(){
        int n=0;
        Cursor result=getAllContacts();
        result.moveToFirst();
        while (!result.isAfterLast()) {
            n++;
            result.moveToNext();
        }
        result.close();
        return n;
    }

    //---Recuperar todos los contactos---
    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL, KEY_PHONE}, null, null, null, null, null);
    }

    public Cursor getAllContactsAZ() {
        String sql="SELECT * FROM "+DATABASE_TABLE+" ORDER BY "+KEY_NAME+" ; ";
        return db.rawQuery(sql,null);
    }

    public Cursor getContactByName(String name) {
        String sql="SELECT * FROM "+DATABASE_TABLE+" WHERE "+KEY_NAME+" = "+name+";";
        return db.rawQuery(sql,null);
    }

    public Vector getAllContactsVector(Cursor cursor){
        Vector<String> vt = new Vector<String>();
        Cursor result=cursor;
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id=result.getInt(0);
            String name=result.getString(1);
            String email=result.getString(2);
            String phone=result.getString(3);

            vt.add(id+" - "+name+" - "+email+" - "+phone);

            result.moveToNext();
        }
        result.close();
        return vt;
    }

    //---Recuperar un contacto en particular ---
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EMAIL,KEY_PHONE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //--- Actualizar un contacto ---
    public boolean updateContact(long rowId, String name, String email, String phone) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        args.put(KEY_PHONE,phone);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }



    // Clase de tipo SQLIteOpenHelper
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Actualizando desde la versión " + oldVersion + " a "
                    + newVersion + ", Se destruiran todos los datos");
            db.execSQL("se aplica DROP TABLE ");
            onCreate(db);
        }
    }
}
