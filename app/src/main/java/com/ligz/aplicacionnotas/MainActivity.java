package com.ligz.aplicacionnotas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ligz.aplicacionnotas.database.DataBaseNote;
import com.ligz.aplicacionnotas.database.DataBaseSQL;
import com.ligz.aplicacionnotas.entities.Model;
import com.ligz.aplicacionnotas.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<Model> notesList;
    //List<Note> noteList;
    DataBaseSQL dataBaseSQL;

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        //A esto no le movi
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, AddNotes.class);
                startActivity(intent);
                startActivityForResult(
                        new Intent(MainActivity.this, AddNotes.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });


        notesList = new ArrayList<>();

        dataBaseSQL=new DataBaseSQL(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);
    }

    void fetchAllNotesFromDatabase(){
        Cursor cursor = dataBaseSQL.readAllData();

        if(cursor.getCount()==0){
            Toast.makeText(this, "No hay datos para mostrar", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                notesList.add(new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }
    }

    //De aqui en adelante no toque nada
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Buscar las notas aquí");

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };

        searchView.setOnQueryTextListener(listener);

        return super.onCreateOptionsMenu(menu);
    }

    /*private void mostarNotas(final int requestCode, final boolean isDeleted) {

        @SuppressLint("StaticFieldLeak")
        class obtenerNotas extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return DataBaseNote.getDatabase(getApplicationContext()).dao().getAll();
            }

            @Override
            protected void onPostExecute(List<Note> notas) {
                super.onPostExecute(notas);
                if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    notesList.add(0, notas.get(0));
                    adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                }
            }

        }
        new obtenerNotas().execute();
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            mostarNotas(REQUEST_CODE_ADD_NOTE, false);
        } /*else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                mostarNotas(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        }
    }*/

}