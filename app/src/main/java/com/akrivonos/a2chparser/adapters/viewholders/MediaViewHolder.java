package com.akrivonos.a2chparser.adapters.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener;
import com.akrivonos.a2chparser.pojomodel.threadmodel.File;
import com.bumptech.glide.Glide;

import static com.akrivonos.a2chparser.adapters.MediaAdapter.ITEM_TYPE_VIDEO;

public class MediaViewHolder extends RecyclerView.ViewHolder {
    private TextView nameMediaTextView;
    private TextView widthMediaTextView;
    private TextView heightMediaTextView;
    private TextView sizeMediaTextView;
    private VideoView mediaView;
    private ImageView imageView;
    private String STANDART_PATH = "https://2ch.hk";
    private String mediaPathHighQuality;

    public MediaViewHolder(@NonNull View itemView, final ShowContentMediaListener contentMediaListener) {
        super(itemView);
        nameMediaTextView = itemView.findViewById(R.id.name_media);
        widthMediaTextView = itemView.findViewById(R.id.width_media);
        heightMediaTextView = itemView.findViewById(R.id.height_media);
        sizeMediaTextView = itemView.findViewById(R.id.size_media);
        imageView = itemView.findViewById(R.id.imageView);
        if (getItemViewType() == ITEM_TYPE_VIDEO) {
            ImageView playIcon = itemView.findViewById(R.id.play_icon);
            playIcon.setVisibility(View.VISIBLE);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewType = getItemViewType();
                contentMediaListener.showContent(mediaPathHighQuality, viewType);
            }
        });
    }

    public void setUpMediaItem(File fileMedia, boolean modeThreadOpenFull) {
        if (fileMedia != null) {
            nameMediaTextView.setText(String.valueOf(fileMedia.getName()));
            String size = fileMedia.getSize() + "Кб";
            sizeMediaTextView.setText(size);
            heightMediaTextView.setText(String.valueOf(fileMedia.getHeight()));
            widthMediaTextView.setText(String.valueOf(fileMedia.getWidth()));
            mediaPathHighQuality = STANDART_PATH.concat(fileMedia.getPath());
            String mediaPathThumbnail = STANDART_PATH.concat(fileMedia.getThumbnail());
            Glide.with(imageView)
                    .load(Uri.parse(mediaPathThumbnail))
                    .into(imageView);
        }
    }
}
