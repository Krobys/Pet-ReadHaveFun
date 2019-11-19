package com.akrivonos.a2chparser.activities

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity.Companion.NAME_MEDIA
import com.akrivonos.a2chparser.activities.MainActivity.Companion.PATH_MEDIA
import com.akrivonos.a2chparser.activities.MainActivity.Companion.TYPE_MEDIA
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.ActivityMediaScreenBinding
import com.tbruyelle.rxpermissions2.RxPermissions


class MediaScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMediaScreenBinding
    private val rxPermissions: RxPermissions = RxPermissions(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_screen)
        setUpMedia()
    }

    private fun setUpMedia() {
        intent?.let {
            it.getStringExtra(PATH_MEDIA)?.let { pathMedia ->
                it.getIntExtra(TYPE_MEDIA, 0).let { typeMedia ->
                    binding.isVideo = typeMedia == MediaAdapter.ITEM_TYPE_VIDEO
                    binding.srcMedia = pathMedia
                    binding.parentActivity = this
                }
            }
        }
    }

    fun closeActivity() {
        finish()
    }

    @SuppressLint("CheckResult")
    fun downloadMedia() {
        rxPermissions
                .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { permissionRequestStatus ->
                    if (permissionRequestStatus) {
                        downloadWithDownloadManager()
                    } else {
                        Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun downloadWithDownloadManager() {
        intent?.getStringExtra(NAME_MEDIA)?.let {
            val request = DownloadManager.Request(Uri.parse(binding.srcMedia))
            request.setDescription("Downloading from 2ch.hk")
            request.setTitle(it)

            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, it)
            val activity = this
            val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
            Toast.makeText(activity, "Starting Download", Toast.LENGTH_SHORT).show()
        }
    }
}
