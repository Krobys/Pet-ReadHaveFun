package com.akrivonos.a2chparser.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;

public class ThreadViewHolder extends RecyclerView.ViewHolder {

     TextView nameThread;
     TextView idThread;
     TextView dateThread;
     TextView contentThread;
     RecyclerView mediaContentThread;

     private Thread thread;

    public ThreadViewHolder(@NonNull View itemView) {
        super(itemView);

        nameThread = itemView.findViewById(R.id.name_thread);
        idThread = itemView.findViewById(R.id.id_thread);
        dateThread = itemView.findViewById(R.id.date_thread);
        contentThread = itemView.findViewById(R.id.text_content);
    }

    public ThreadViewHolder(@NonNull View itemView, RecyclerView.LayoutManager layoutManager) {
        super(itemView);

        nameThread = itemView.findViewById(R.id.name_thread);
        idThread = itemView.findViewById(R.id.id_thread);
        dateThread = itemView.findViewById(R.id.date_thread);
        contentThread = itemView.findViewById(R.id.text_content);
        mediaContentThread = itemView.findViewById(R.id.rec_view_boards_for_theme);
        if(thread.getFiles() != null){
            mediaContentThread.setLayoutManager(layoutManager);
        }else{
            mediaContentThread.setVisibility(View.GONE);
        }
    }
}
