package com.sibin.embibeassignment.appFeatures.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.ui.adapter.MovieListAdapter
import com.sibin.embibeassignment.appFeatures.ui.adapter.OnLoadMoreListener
import com.sibin.embibeassignment.appFeatures.ui.adapter.RecyclerViewLoadMoreScroll
import com.sibin.embibeassignment.appFeatures.ui.viewmodel.MoviesViewModel
import com.sibin.embibeassignment.base.baseclass.BaseActivity
import com.sibin.embibeassignment.base.baseclass.BaseFragment
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.exception.ResponseFailure
import com.sibin.embibeassignment.base.extension.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class MoviesListFragment : BaseFragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    private var pagesEnds = false
    override fun layoutId() = R.layout.fragment_movies_list

    @Inject
    lateinit var moviesAdapter: MovieListAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        moviesViewModel = viewModel(viewModelFactory) {
            observe(movieList, ::handleMovieList)
            error(failure, ::handleFailure)
        }
        setHasOptionsMenu(true)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolBar()
        setRecyclerView()
        setRVScrollListener()
        moviesViewModel.endOfPages.observe(this.viewLifecycleOwner, Observer {
            pagesEnds = it
        })
    }

    private fun handleToolBar() {
        val activity = activity as BaseActivity?
        activity?.setSupportActionBar(toolbar)
        val actionBar = activity?.supportActionBar
        actionBar?.title = getString(R.string.movie_list)
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                addFragment(MovieSearchFragment(), true)
                true
            }
            R.id.action_bookmarked -> {
                addFragment(BookmarkListFragment(), true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadMovies() {
        if (!pagesEnds) {
            showProgress()
            moviesViewModel.getMovies()
        }
    }

    private fun setRecyclerView() {
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        moviesAdapter =
            MovieListAdapter(
                moviesViewModel.imageBaseUrl!!
            ) { movie ->
                logDebug("movie $movie")
                moviesViewModel.addOrRemoveBookmark(movie)
            }
        recycler_view.adapter = moviesAdapter
    }

    private fun setRVScrollListener() {
        scrollListener =
            RecyclerViewLoadMoreScroll(
                recycler_view.layoutManager as StaggeredGridLayoutManager
            )
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMovies()
            }
        })

        recycler_view.addOnScrollListener(scrollListener)
    }


    private fun handleMovieList(listOfMovies: List<MovieDetails>?) {
        hideProgress()
        scrollListener.setLoaded()

        if (!listOfMovies.isNullOrEmpty()) {
            recycler_view.visible()
            error_text.invisible()
            moviesAdapter.addList(listOfMovies)
            recycler_view.layoutManager?.scrollToPosition(moviesViewModel.previousSize)
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
