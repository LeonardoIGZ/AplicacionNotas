package com.ligz.aplicacionnotas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ligz.aplicacionnotas.entities.Model;
import com.ligz.aplicacionnotas.entities.Note;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    Activity activity;
    List<Model> notesList;
    //List<Note> notesList;

    public Adapter(Context context, Activity activity, List<Model> notesList) {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(notesList.get(position).getTitle());
        holder.descrip.setText(notesList.get(position).getDescription());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateNotes.class);

                intent.putExtra("title",notesList.get(position).getTitle());
                intent.putExtra("description",notesList.get(position).getDescription());
                intent.putExtra("id",notesList.get(position).getId());

                activity.startActivity(intent);
            }
        });
        //añadir fecha
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title, descrip;
    RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titulo);
            descrip = itemView.findViewById(R.id.descripcion);
            relativeLayout = itemView.findViewById(R.id.layout_notas);

        }
    }

}
