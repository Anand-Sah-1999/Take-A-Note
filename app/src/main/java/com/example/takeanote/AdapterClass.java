package com.example.takeanote;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    ArrayList<GetAndSetItems> notes;
    static int pos;
    GetInterface getInterface;

    public interface GetInterface{
//        void getUpdatePos(int pos);
        void getDeletePos(int pos);
    }

    public void SetInterface(GetInterface getInterface){
        this.getInterface = getInterface;
    }

    public AdapterClass(ArrayList<GetAndSetItems> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetAndSetItems items = notes.get(position);
        holder.cardViewTitle.setText(items.getTitle1());
        holder.cardViewText.setText(items.getText1());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardViewTitle, cardViewText;
        ImageView deleteNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            final Context context = itemView.getContext();
            cardViewTitle = itemView.findViewById(R.id.cardViewTitle);
            cardViewText = itemView.findViewById(R.id.cardViewText);
            deleteNote = itemView.findViewById(R.id.deleteNote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(v.getContext(), New_Note.class);
                    intent.putExtra("Title", notes.get(pos).getTitle1());
                    intent.putExtra("Text", notes.get(pos).getText1());
                    intent.putExtra("Position",pos);
                    intent.putExtra("NEW","editNote");
                    context.startActivity(intent);

                }
            });

            deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    getInterface.getDeletePos(pos);
                }
            });
        }
    }
}
