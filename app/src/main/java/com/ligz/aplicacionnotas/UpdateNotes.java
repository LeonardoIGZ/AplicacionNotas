package com.ligz.aplicacionnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ligz.aplicacionnotas.database.DataBaseSQL;

public class UpdateNotes extends AppCompatActivity {

    EditText title,description;
    Button updateNotes;
    String  id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        title=findViewById(R.id.titulo);
        description=findViewById(R.id.descripcion);
        updateNotes=findViewById(R.id.Update);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id=intent.getStringExtra("id");

        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                    DataBaseSQL db = new DataBaseSQL(UpdateNotes.this);
                    db.updateNotes(title.getText().toString(),description.getText().toString(),id);

                    Intent i = new Intent(UpdateNotes.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(UpdateNotes.this, "Se requieren los 2 datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}