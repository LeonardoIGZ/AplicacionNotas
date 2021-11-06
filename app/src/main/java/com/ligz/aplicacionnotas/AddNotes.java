package com.ligz.aplicacionnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ligz.aplicacionnotas.dao.NoteDao;
import com.ligz.aplicacionnotas.database.DataBaseNote;
import com.ligz.aplicacionnotas.database.DataBaseSQL;
import com.ligz.aplicacionnotas.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotes extends AppCompatActivity {
    EditText titulo, descrip;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        titulo = findViewById(R.id.titulo);
        descrip = findViewById(R.id.descripcion);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*esto no esta
                guardar();
                */
                if(!TextUtils.isEmpty(titulo.getText().toString()) && !TextUtils.isEmpty(descrip.getText().toString())){
                    DataBaseSQL db = new DataBaseSQL(AddNotes.this);
                    db.addNotes(titulo.getText().toString(), descrip.getText().toString(), new SimpleDateFormat("EEEE, dd  MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date()));

                    Intent intent = new Intent(AddNotes.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AddNotes.this, "Se necesitan los 2 campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*private void guardar(){
        if(titulo.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "El titulo no puede ser un campo vacio", Toast.LENGTH_SHORT).show();
            return;
        }else if(descrip.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "La nota debe tener algo escrito", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note miNota = new Note();
        miNota.setTitle(titulo.getText().toString());
        miNota.setNote_text(descrip.getText().toString());
        //miNota.setDate(textFecha.getText().toString());
        //miNota.setImgpath(selectedImagePath);
        /*
        if(alReadyAvailableNote != null){
            miNota.setId(alReadyAvailableNote.getId());
        }*/

        /*@SuppressLint("StaticFieldLeak")
        class guardarNota extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void...voids){
                DataBaseNote.getDatabase(getApplicationContext()).dao().insertNote(miNota);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent =  new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new  guardarNota().execute();
    }*/

}