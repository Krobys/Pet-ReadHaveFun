package com.akrivonos.a2chparser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.viewholders.MediaViewHolder;
import com.akrivonos.a2chparser.pojomodel.threadmodel.File;

import java.util.ArrayList;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {

    public static final int MEDIA_TYPE_JPG = 1;
    public static final int MEDIA_TYPE_PNG = 2;
    public static final int MEDIA_TYPE_GIF = 4;
    public static final int MEDIA_TYPE_WEBM = 6;
    public static final int MEDIA_TYPE_MP4 = 10;

    public static final int ITEM_TYPE_IMAGE = 1;
    public static final int ITEM_TYPE_GIF = 2;
    public static final int ITEM_TYPE_VIDEO = 3;

    private ArrayList<File> mediaList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public MediaAdapter(LayoutInflater layoutInflater){
        this.layoutInflater = layoutInflater;
    }

    public void setMediaList(List<File> mediaListToAdapt){
        mediaList = new ArrayList<>(mediaListToAdapt);
    }

    @Override
    public int getItemViewType(int position) {
        File file = mediaList.get(position);
        if(file != null){
            int mediaType = file.getType();
            switch (mediaType){
                case MEDIA_TYPE_JPG:
                    return ITEM_TYPE_IMAGE;
                case MEDIA_TYPE_PNG:
                    return ITEM_TYPE_IMAGE;
                case MEDIA_TYPE_GIF:
                    return ITEM_TYPE_GIF;
                case MEDIA_TYPE_WEBM:
                    return ITEM_TYPE_VIDEO;
                case MEDIA_TYPE_MP4:
                    return ITEM_TYPE_VIDEO;
            }
        }
        return ITEM_TYPE_IMAGE;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType){
            case ITEM_TYPE_GIF:
                view = layoutInflater.inflate(R.layout.adapteritem_media_photo, parent, false);
                break;
            case ITEM_TYPE_IMAGE:
                view = layoutInflater.inflate(R.layout.adapteritem_media_photo, parent, false);
                break;
            case ITEM_TYPE_VIDEO:
                view = layoutInflater.inflate(R.layout.adapteritem_media_video, parent, false);
                break;
                default: view = layoutInflater.inflate(R.layout.adapteritem_media_photo, parent, false);

        }

        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        holder.setUpMediaItem(mediaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
