package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.activities.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.activities.MainActivity.Companion.NUMBER_THREAD
import com.akrivonos.a2chparser.adapters.recviewadapters.PostAdapter
import com.akrivonos.a2chparser.base.BaseFragment
import com.akrivonos.a2chparser.dagger.Injectable
import com.akrivonos.a2chparser.databinding.FragmentConcreteThreadBinding
import com.akrivonos.a2chparser.dialogs.FilterSettingsDialog
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class ConcreteThreadFragment : BaseFragment<ConcreteThreadViewModel, FragmentConcreteThreadBinding>(),
        SearchView.OnQueryTextListener,
        Injectable,
        OnBackPressedFragmentsListener,
        ScrollToPositionListener{
    private var pageDisplayModeListener: NavBarDisplayModeListener? = null
    private lateinit var postAdapter: PostAdapter
    private var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
    @Inject
    lateinit var itemDecoratorUtils: ItemDecoratorUtils
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    private var searchView: SearchView? = null

    override val layoutId: Int
        get() = R.layout.fragment_concrete_thread
    override val viewModelClass: Class<ConcreteThreadViewModel>
        get() = ConcreteThreadViewModel::class.java
    override var progressBar: ProgressBar? = null

    override fun doAfterCreateView() {
        setUpScreen()
        setHasOptionsMenu(true)
        startLoadPostsForThread()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpAdapterAndListeners()
    }

    private fun setUpScreen() {
        binding.concreteThreadRecycleView.apply {
            layoutManager = this.layoutManager
            addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 50))
            adapter = postAdapter
        }
        (activity as? MainActivity)?.setSupportActionBar(toolbar)
        progressBar = binding.progressBar
        pageDisplayModeListener?.setNavbarMode(MainActivity.Companion.NavbarMode.INVISIBLE)
    }

    private fun setUpAdapterAndListeners() {
        activity?.let {
            pageDisplayModeListener = it as NavBarDisplayModeListener
            val showContentListener = it as ShowContentMediaListener
            val scrollToPositionListener : ScrollToPositionListener = this
            postAdapter = PostAdapter(it, showContentListener, scrollToPositionListener)
        }
    }

    override fun scroll(pos: Int) {
        //concrete_thread_recycle_view.scrollToPosition(pos)
        layoutManager?.scrollToPositionWithOffset(pos, 50)
    }

    private fun startLoadPostsForThread() {
        arguments?.let {
            it.getString(ID_BOARD)?.let { idBoard ->
                it.getString(NUMBER_THREAD)?.let { numberThread ->
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.getPostsLiveData(idBoard, numberThread)
                            .observe(this, Observer { list -> showPostList(list) })
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun showPostList(listItems: List<FilteredItem>) {
        val listPosts: List<Post> = listItems as List<Post>
        if (listPosts.isNotEmpty()) {
            postAdapter.apply {
                setPosts(listPosts)
                notifyDataSetChanged()
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    private fun setUpSearchView(menu: Menu) {
        menu.findItem(R.id.action_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.apply {
                isIconified = true
                setOnQueryTextListener(this@ConcreteThreadFragment)
                (this.findViewById(R.id.search_close_btn) as ImageView).setOnClickListener {
                    undoFilter()
                }
            }
        }
    }

    private fun setUpFilterButton(menu: Menu) {
        menu.findItem(R.id.filter_button)?.let {
            it.setOnMenuItemClickListener {
                showFilterSettingsDialog(context, it)
                true
            }
            setUpFilterStateIcon(it, (sharedPreferenceUtils.isFilterEnable(context)))
        }
    }

    private fun undoFilter() {
        searchView?.apply {
            postAdapter.undoFilter()
            clearFocus()
            isIconified = true
            isIconified = true//х2 потому что не срабатывает х1
        }
    }

    private fun showFilterSettingsDialog(context: Context?, menuItem: MenuItem?) {
        context?.let {
            FilterSettingsDialog(it, object : CallBack {
                override fun call() {
                    startLoadPostsForThread()
                    setUpFilterStateIcon(menuItem, (sharedPreferenceUtils.isFilterEnable(context)))
                }
            }).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                show()
            }
        }
    }

    private fun setUpFilterStateIcon(menuItem: MenuItem?, boolean: Boolean) {
        menuItem?.setIcon(if (boolean)
            R.drawable.filter_on
        else R.drawable.filter_off)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchView?.apply {
                clearFocus()
            }
            postAdapter.filter(it)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.length > 1) {
                postAdapter.filter(it)
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detailed_search, menu)
        setUpSearchView(menu)
        setUpFilterButton(menu)
        setUpBackButton()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpBackButton() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
    }

    override fun onBackPressed() {
        if (postAdapter.isSeqControllerEnable()){
            postAdapter.seqControllerUndo()
        }else{
            if (postAdapter.isFilterEnable()) {
                undoFilter()
            }else{
                (activity as MainActivity).pressBackSuper()
            }
        }
    }

}
