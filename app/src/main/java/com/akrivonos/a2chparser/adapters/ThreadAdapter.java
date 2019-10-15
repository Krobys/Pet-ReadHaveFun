package com.akrivonos.a2chparser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.viewholders.ThreadViewHolder;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;

import java.util.ArrayList;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadViewHolder>{

    private ArrayList<Thread> threads = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private RecyclerView.LayoutManager layoutManager;
    private final static int TYPE_WITH_MEDIA = 1;
    private final static int TYPE_WITHOUT_MEDIA = 2;

    public ThreadAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public int getItemViewType(int position) {
            return (threads.get(position).getFiles() != null)
                    ? TYPE_WITH_MEDIA
                    : TYPE_WITHOUT_MEDIA;
    }

    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapteritem_threads_for_board, parent, false);
        return (viewType == TYPE_WITH_MEDIA) ? new ThreadViewHolder(view, layoutManager) :
        return
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return threads.size();
    }
}
