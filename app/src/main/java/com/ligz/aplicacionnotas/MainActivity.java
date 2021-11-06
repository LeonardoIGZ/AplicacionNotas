package com.ligz.aplicacionnotas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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
    CoordinatorLayout coordinatorLayout;

    //public static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
        coordinatorLayout = findViewById(R.id.layout_principal);

        //A esto no le movi
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, AddNotes.class);
                startActivity(intent);
                /*startActivityForResult(
                        new Intent(MainActivity.this, AddNotes.class),
                        REQUEST_CODE_ADD_NOTE
                );*/
            }
        });

        notesList = new ArrayList<>();

        dataBaseSQL=new DataBaseSQL(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper =  new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerView);
    }

    void fetchAllNotesFromDatabase(){
        Cursor cursor = dataBaseSQL.readAllData();

        if(cursor.getCount()==0){
            Toast.makeText(this, "No hay datos para mostrar", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                notesList.add(new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3)));
            }
        }
    }

    //Para el menu
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
                adapter.getFilter().filter(newText);
                return true;
            }
        };

        searchView.setOnQueryTextListener(listener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all){
            deleteAllNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes(){
        DataBaseSQL db = new DataBaseSQL(MainActivity.this);
        db.deleteAllNotes();

        recreate();
    }

    ItemTouchHelper.SimpleCallback simpleCallback =  new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            Model item =  adapter.getNotesList().get(pos);
            adapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Elemento eliminado",
                    Snackbar.LENGTH_LONG).setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoreItem(item, pos);
                    recyclerView.smoothScrollToPosition(pos);
                }
            }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    if(!(event==DISMISS_EVENT_ACTION)){
                        DataBaseSQL db = new DataBaseSQL(MainActivity.this);
                        db.deleteOneItem(item.getId());
                    }
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    };


    //Metodo de mostrar notas para intento de Room
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