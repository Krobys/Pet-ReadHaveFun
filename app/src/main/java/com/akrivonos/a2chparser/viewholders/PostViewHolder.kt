package com.akrivonos.a2chparser.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.AdapteritemPostForThreadBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils

class PostViewHolder(private var binder: AdapteritemPostForThreadBinding) : RecyclerView.ViewHolder(binder.root) {
    private val mediaContentThreadRecView: RecyclerView = binder.root.findViewById(R.id.media_content_rec_view)
    private var itemDecoratorUtils: ItemDecoratorUtils = ItemDecoratorUtils()

    constructor(binder: AdapteritemPostForThreadBinding,
                context: Context,
                layoutInflater: LayoutInflater,
                contentMediaListener: ShowContentMediaListener)
            : this(binder) {

        mediaContentThreadRecView.addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))
        binder.layoutManagerRecycleView = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binder.adapter = MediaAdapter(layoutInflater, contentMediaListener)
    }

    fun setPostDataWithMedia(post: Post) {
        binder.post = post
        binder.adapter?.setMediaList(post.files)
        binder.adapter?.notifyDataSetChanged()
    }

    fun setPostDataWithoutMedia(post: Post) {
        binder.post = post
        mediaContentThreadRecView.visibility = View.GONE
    }
}