package com.ligz.aplicacionnotas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseSQL extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName="MisNotas";
    private static final int DatabaseVersion=1;

    private static final String TableName="misnotas";
    private static final String ColumnId="id";
    private static final String ColumnTitle="title";
    private static final String ColumnDescription="description";
    private  static final String ColumnDate = "date";

    public DataBaseSQL(@Nullable Context context){
        super(context, DatabaseName, null, DatabaseVersion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCommand = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT);",
                TableName, ColumnId, ColumnTitle, ColumnDescription, ColumnDate);

        /*String query = "CREATE TABLE " + TableName +
                "(" +ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT);";*/

        db.execSQL(sqlCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    public void addNotes(String title, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);
        cv.put(ColumnDate, date);

        long resultValue = db.insert(TableName, null, cv);
        
        if(resultValue ==-1){
            Toast.makeText(context, "Datos no añadidos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Datos añadidos con exito", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database!=null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteAllNotes(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "DELETE FROM " + TableName;
        database.execSQL(query);
    }

    public void updateNotes(String title, String description, String id){
        SQLiteDatabase database=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(ColumnTitle,title);
        cv.put(ColumnDescription,description);

        long result=database.update(TableName,cv,"id=?",new String[]{id});
        if(result==-1){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Editado con exito", Toast.LENGTH_SHORT).show();
        }
    }

}
