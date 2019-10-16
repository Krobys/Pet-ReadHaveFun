package com.akrivonos.a2chparser.adapters.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.pojomodel.threadmodel.File;
import com.bumptech.glide.Glide;

import static com.akrivonos.a2chparser.adapters.MediaAdapter.ITEM_TYPE_GIF;
import static com.akrivonos.a2chparser.adapters.MediaAdapter.ITEM_TYPE_IMAGE;
import static com.akrivonos.a2chparser.adapters.MediaAdapter.ITEM_TYPE_VIDEO;

public class MediaViewHolder extends RecyclerView.ViewHolder {
    private TextView nameMediaTextView;
    private TextView widthMediaTextView;
    private TextView heightMediaTextView;
    private TextView sizeMediaTextView;
    private VideoView mediaView;
    private ImageView imageView;

    public void setUpMediaItem(File fileMedia){
        if(fileMedia != null){
            nameMediaTextView.setText(String.valueOf(fileMedia.getName()));
            String size = fileMedia.getSize() + "Кб";
            sizeMediaTextView.setText(size);
            heightMediaTextView.setText(String.valueOf(fileMedia.getHeight()));
            widthMediaTextView.setText(String.valueOf(fileMedia.getWidth()));
            String mediaPath = "https://2ch.hk".concat(fileMedia.getPath());
            //String mediaPath = "https://2ch.hk".concat("/mov/src/2429473/15705450454031.gif");
            int typeMedia = getItemViewType();
            switch (typeMedia){
                case ITEM_TYPE_VIDEO:
                    mediaView.setVideoURI(Uri.parse(mediaPath));
                    mediaView.seekTo(100);
                    break;
                case ITEM_TYPE_IMAGE:
                    Glide.with(imageView)
                            .load(mediaPath)
                            .into(imageView);
                    break;
                case ITEM_TYPE_GIF:
                    Glide.with(imageView)
                            .asGif()
                            .load(mediaPath)
                            .into(imageView);
                    break;
            }
        }
    }

    public MediaViewHolder(@NonNull View itemView) {
        super(itemView);
        nameMediaTextView = itemView.findViewById(R.id.name_media);
        widthMediaTextView = itemView.findViewById(R.id.width_media);
        heightMediaTextView = itemView.findViewById(R.id.height_media);
        sizeMediaTextView = itemView.findViewById(R.id.size_media);
        mediaView = itemView.findViewById(R.id.videoView);
        imageView = itemView.findViewById(R.id.imageView);
    }
}
