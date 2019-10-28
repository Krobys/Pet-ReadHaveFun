package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemPostForThreadBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.viewholders.PostViewHolder

class PostAdapter(private val context: Context, private val contentMediaListener: ShowContentMediaListener) : RecyclerView.Adapter<PostViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var postsList: List<Post> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        val post = postsList[position]
        val files = post.files
        return if (files != null)
            ThreadAdapter.TYPE_WITH_MEDIA
        else
            ThreadAdapter.TYPE_WITHOUT_MEDIA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = DataBindingUtil.inflate<AdapteritemPostForThreadBinding>(layoutInflater, R.layout.adapteritem_post_for_thread, parent, false)
        return if (viewType == ThreadAdapter.TYPE_WITH_MEDIA)
            PostViewHolder(binding, context = context, layoutInflater = layoutInflater, contentMediaListener = contentMediaListener)
        else
            PostViewHolder(binding)
    }

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]
        if (holder.itemViewType == ThreadAdapter.TYPE_WITH_MEDIA) {
            holder.setThreadDataWithMedia(post)
        } else {
            holder.setThreadDataWithoutMedia(post)
        }
    }
}