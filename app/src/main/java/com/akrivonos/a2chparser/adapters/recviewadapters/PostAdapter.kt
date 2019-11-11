package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemPostForThreadBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.viewholders.PostViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostAdapter(private val context: Context, private val contentMediaListener: ShowContentMediaListener) : RecyclerView.Adapter<PostViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var postsList: List<Post> = ArrayList()
    var fullPostList = ArrayList<Post>()
    var filteredPostList = ArrayList<Post>()
    private var disposable: Disposable? = null

    fun setPosts(posts: List<Post>?) {
        posts?.let {
            fullPostList = ArrayList(it)
            postsList = fullPostList
        }
    }

    override fun getItemViewType(position: Int): Int {
        val post = postsList[position]
        post.files?.let {
            if (it.isNotEmpty()) return ThreadAdapter.TYPE_WITH_MEDIA
        }
        return ThreadAdapter.TYPE_WITHOUT_MEDIA
    }

    fun filter(textFilter: String) {
        filteredPostList.clear()
        disposable?.dispose()
        disposable = Observable.just(fullPostList)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { listPost ->
                    val tempList = ArrayList<Post>()
                    for (post: Post in listPost) {
                        post.comment?.let {
                            if (it.contains(textFilter)) {
                                tempList.add(post)
                            }
                        }
                    }
                    filteredPostList = tempList
                    postsList = tempList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    notifyDataSetChanged()
                    Toast.makeText(context, "search count ${postsList.size}", Toast.LENGTH_SHORT).show()
                }
    }

    fun undoFilter() {
        postsList = fullPostList
        notifyDataSetChanged()
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
            holder.setPostDataWithMedia(post)
        } else {
            holder.setPostDataWithoutMedia(post)
        }
    }
}