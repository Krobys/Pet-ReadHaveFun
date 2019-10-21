package com.akrivonos.a2chparser.adapters.viewholders;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.MediaAdapter;
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils;

public class ThreadViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameThreadTextView;
    private final TextView idThreadTextView;
    private final TextView dateThreadTextView;
    private final TextView contentThreadTextView;
    private final RecyclerView mediaContentThreadRecView;
     private MediaAdapter mediaAdapter;
     private String idThread;

    public ThreadViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.d("test", "ThreadViewHolder: 1");
        nameThreadTextView = itemView.findViewById(R.id.name_thread);
        idThreadTextView = itemView.findViewById(R.id.id_thread);
        dateThreadTextView = itemView.findViewById(R.id.date_thread);
        contentThreadTextView = itemView.findViewById(R.id.text_content);
        mediaContentThreadRecView = itemView.findViewById(R.id.media_content_rec_view);
        mediaContentThreadRecView.setVisibility(View.GONE);
    }

    public ThreadViewHolder(@NonNull View itemView, Context context, LayoutInflater layoutInflater, boolean isFullMode, ShowContentMediaListener contentMediaListener) {
        super(itemView);
        Log.d("test", "ThreadViewHolder: 2");
        nameThreadTextView = itemView.findViewById(R.id.name_thread);
        idThreadTextView = itemView.findViewById(R.id.id_thread);
        dateThreadTextView = itemView.findViewById(R.id.date_thread);
        contentThreadTextView = itemView.findViewById(R.id.text_content);
        mediaContentThreadRecView = itemView.findViewById(R.id.media_content_rec_view);
        mediaContentThreadRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
        mediaContentThreadRecView.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40));

        mediaAdapter = new MediaAdapter(layoutInflater, isFullMode, contentMediaListener);

        mediaContentThreadRecView.setAdapter(mediaAdapter);

    }

    public void setThreadDataWithMedia(Thread thread) {
        Log.d("test", "setThreadDataWithMedia: ");
        nameThreadTextView.setText(thread.getSubject());
        idThread = thread.getNum();
        idThreadTextView.setText(idThread);
        dateThreadTextView.setText(thread.getDate());
        contentThreadTextView.setText(Html.fromHtml(thread.getComment()));
        mediaAdapter.setMediaList(thread.getFiles());
        mediaAdapter.notifyDataSetChanged();
    }

    public void setThreadDataWithoutMedia(Thread thread) {
        Log.d("test", "setThreadDataWithoutMedia: ");
        nameThreadTextView.setText(thread.getSubject());
        idThread = thread.getNum();
        idThreadTextView.setText(idThread);
        dateThreadTextView.setText(thread.getDate());
        contentThreadTextView.setText(Html.fromHtml(thread.getComment()));
    }
}
