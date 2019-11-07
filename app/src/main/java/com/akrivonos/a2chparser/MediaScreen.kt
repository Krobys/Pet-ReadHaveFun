package com.akrivonos.a2chparser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.akrivonos.a2chparser.MainActivity.Companion.MEDIA_TYPE
import com.akrivonos.a2chparser.MainActivity.Companion.PATH_MEDIA
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.ActivityMediaScreenBinding

class MediaScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMediaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_screen)
        setUpMedia()
    }

    private fun setUpMedia() {
        intent?.let {
            it.getStringExtra(PATH_MEDIA)?.let { pathMedia ->
                it.getIntExtra(MEDIA_TYPE, 0).let { typeMedia ->
                    Log.d("test", "setUpMedia:")
                    binding.isVideo = typeMedia == MediaAdapter.ITEM_TYPE_VIDEO
                    binding.srcMedia = pathMedia
                }
            }
        }
    }

}
