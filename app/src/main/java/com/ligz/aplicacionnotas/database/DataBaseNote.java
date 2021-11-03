package com.ligz.aplicacionnotas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ligz.aplicacionnotas.entities.Note;
import com.ligz.aplicacionnotas.dao.NoteDao;


@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class DataBaseNote extends RoomDatabase {

    private static DataBaseNote database;

    public static synchronized DataBaseNote getDatabase(Context context){
        if (database == null){
            database = Room.databaseBuilder(context, DataBaseNote.class, "notes_db").build();
        }
        return database;
    }

    public abstract NoteDao dao();
}
