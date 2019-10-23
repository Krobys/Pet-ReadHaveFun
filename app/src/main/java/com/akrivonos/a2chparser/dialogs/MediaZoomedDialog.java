package com.akrivonos.a2chparser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.akrivonos.a2chparser.R;
import com.bumptech.glide.Glide;

public class MediaZoomedDialog extends Dialog {

    private final Context context;
    private final int mediaType;
    private final String mediaPath;

    public MediaZoomedDialog(Context context, String mediaPath, int typeMedia) {
        super(context);
        this.context = context;
        mediaType = typeMedia;
        this.mediaPath = mediaPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_detailed_media);
        setUpNeededMediaConteiner(mediaPath);

    }

    private void setUpNeededMediaConteiner(String mediaPath) {
        switch (mediaType) {
            case Companion.getITEM_TYPE_IMAGE():
                ImageView imageView = findViewById(R.id.imageViewMediaDialog);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(imageView)
                        .load(mediaPath)
                        .into(imageView);
                break;
            case Companion.getITEM_TYPE_VIDEO():
                VideoView videoView = findViewById(R.id.videoViewMediaDialog);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse(mediaPath));
                videoView.setMediaController(new MediaController(context));
                videoView.requestFocus();
                videoView.start();
                break;
        }
    }

}
