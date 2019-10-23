package com.akrivonos.a2chparser.adapters.recviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.databinding.AdapteritemThreadsForBoardBinding;
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener;
import com.akrivonos.a2chparser.pojomodel.threadmodel.File;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;
import com.akrivonos.a2chparser.viewholders.ThreadViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadViewHolder>{

    private ArrayList<Thread> threads = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final static int TYPE_WITH_MEDIA = 1;
    private final static int TYPE_WITHOUT_MEDIA = 2;
    private final boolean isFullMode;
    private final ShowContentMediaListener contentMediaListener;

    public ThreadAdapter(Context context, boolean isFullMode, ShowContentMediaListener contentMediaListener) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isFullMode = isFullMode;
        this.contentMediaListener = contentMediaListener;
    }

    public void setThreads(List<Thread> threads){
        this.threads = new ArrayList<>(threads);
    }

    @Override
    public int getItemViewType(int position) {
            Thread thread = threads.get(position);
            List<File> files = thread.getFiles();
            return (files != null)
                    ? TYPE_WITH_MEDIA
                    : TYPE_WITHOUT_MEDIA;
    }

    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapteritemThreadsForBoardBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapteritem_threads_for_board, parent, false);
        return (viewType == TYPE_WITH_MEDIA)
                ? new ThreadViewHolder(binding, context, layoutInflater, isFullMode, contentMediaListener)
                : new ThreadViewHolder(binding);

}

    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder holder, int position) {
        Thread thread = threads.get(position);
        if (holder.getItemViewType() == TYPE_WITH_MEDIA) {
            holder.setThreadDataWithMedia(thread);
        }else {
            holder.setThreadDataWithoutMedia(thread);
        }
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }
}
