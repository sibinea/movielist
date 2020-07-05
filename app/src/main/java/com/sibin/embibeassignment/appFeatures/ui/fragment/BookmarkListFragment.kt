package com.sibin.embibeassignment.appFeatures.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.ui.adapter.MovieListAdapter
import com.sibin.embibeassignment.appFeatures.ui.viewmodel.BookmarkViewModel
import com.sibin.embibeassignment.base.baseclass.BaseActivity
import com.sibin.embibeassignment.base.baseclass.BaseFragment
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.exception.ResponseFailure
import com.sibin.embibeassignment.base.data.storage.EmbibePreferences
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.extension.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class BookmarkListFragment : BaseFragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    override fun layoutId() = R.layout.fragment_movies_list


    @Inject
    lateinit var preferences: EmbibePreferences

    @Inject
    lateinit var moviesAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        bookmarkViewModel = viewModel(viewModelFactory) {
            observe(bookmarkList, ::handleMovieList)
            error(failure, ::handleFailure)
        }
        setHasOptionsMenu(true)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolBar()
        setRecyclerView()
        loadMovies()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun handleToolBar() {
        val activity = activity as BaseActivity?
        activity?.setSupportActionBar(toolbar)
        val actionBar = activity?.supportActionBar
        actionBar?.title = getString(R.string.bookmarks)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { getActivity()!!.onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu_bookmark, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                addFragment(MovieSearchFragment(), true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadMovies() {
        showProgress()
        bookmarkViewModel.getBookmarkedMovies()
    }

    private fun setRecyclerView() {
        error_text.text= getString(R.string.no_fav)
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        moviesAdapter =
            MovieListAdapter(bookmarkViewModel.imageBaseUrl) { movie ->
                logDebug("movie $movie")
                bookmarkViewModel.addOrRemoveBookmark(movie)
                moviesAdapter.data.remove(movie)
                moviesAdapter.notifyDataSetChanged()
            }
        recycler_view.adapter = moviesAdapter
    }


    private fun handleMovieList(listOfMovies: List<MovieDetails>?) {
        hideProgress()
        if (!listOfMovies.isNullOrEmpty()) {
            recycler_view.visible()
            error_text.invisible()
            moviesAdapter.data.clear()
            moviesAdapter.addList(listOfMovies)

        } else {
            if (moviesAdapter.data.isNullOrEmpty()) {
                recycler_view.invisible()
                error_text.visible()
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        hideProgress()
        error_text.visible()
        when (failure) {
            is Failure.NetworkConnection -> {
                notify(resources.getString(R.string.failure_network_connection))
            }
            is Failure.ServerError -> {
                notify(resources.getString(R.string.failure_server_error))
            }
            is ResponseFailure -> {
                notify(failure.message)
            }
        }
    }


}
