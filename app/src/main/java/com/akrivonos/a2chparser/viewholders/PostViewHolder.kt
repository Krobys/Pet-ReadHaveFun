package com.akrivonos.a2chparser.viewholders

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.AdapteritemPostForThreadBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.TransitionPostsSeq
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import timber.log.Timber


class PostViewHolder(private var binder: AdapteritemPostForThreadBinding, private val transitionSeqController: TransitionPostsSeq) : RecyclerView.ViewHolder(binder.root) {
    private val mediaContentThreadRecView: RecyclerView = binder.root.findViewById(R.id.media_content_rec_view)
    private var itemDecoratorUtils: ItemDecoratorUtils = ItemDecoratorUtils()
    private var currentNum: String? = null

    constructor(binder: AdapteritemPostForThreadBinding,
                context: Context,
                layoutInflater: LayoutInflater,
                contentMediaListener: ShowContentMediaListener,
                transitionSeqController: TransitionPostsSeq)
            : this(binder, transitionSeqController) {

        mediaContentThreadRecView.addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))
        binder.layoutManagerRecycleView = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binder.adapter = MediaAdapter(layoutInflater, contentMediaListener)
    }

    fun setPostDataWithMedia(post: Post) {
        binder.post = post
        currentNum = post.num
        binder.textContent.setUpClickableSpannable(post.comment)
        binder.adapter?.setMediaList(post.files)
        binder.adapter?.notifyDataSetChanged()
    }

    fun setPostDataWithoutMedia(post: Post) {
        binder.post = post
        currentNum = post.num
        binder.textContent.setUpClickableSpannable(post.comment)
        mediaContentThreadRecView.visibility = View.GONE
    }

    private fun TextView.setUpClickableSpannable(text: String?){
        text?.let {

            val spanned = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            val commentText = SpannableString(spanned)

            val clickableSpan = object: ClickableSpan(){
                override fun onClick(view: View) {
                    val textView = view as TextView
                    val charSequence = textView.text
                    if (charSequence is Spannable) {
                        val spans = charSequence.getSpans(0, textView.length(), ClickableSpan::class.java)
                        for (span in spans) {
                            val start = charSequence.getSpanStart(span)
                            val end = charSequence.getSpanEnd(span)
                            Timber.d("element:${charSequence} start:$start end:$end")
                            val clickedText = charSequence.subSequence(start+2, if (end - start > 11 ) end - 5 else end).toString()
                            transitionSeqController.goToPost(currentNum, clickedText)
                        }
                    }
                }
            }
            val html: Document = Jsoup.parse(text)
            val elements: Elements = html.body().getElementsByClass("post-reply-link")

            for (element in elements){
                Timber.d(text)
                val startIdx = commentText.indexOf(element.text().toString())
                val endIdx = startIdx + element.text().toString().length
                Timber.d("element:${element.text()} start:$startIdx end:$endIdx")
                commentText.setSpan(clickableSpan, startIdx, endIdx, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            this.text = commentText
            this.movementMethod = LinkMovementMethod.getInstance()
        }
    }

}