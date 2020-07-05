package com.sibin.embibeassignment.appFeatures.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.appFeatures.ui.adapter.MovieListAdapter
import com.sibin.embibeassignment.appFeatures.ui.viewmodel.SearchViewModel
import com.sibin.embibeassignment.base.baseclass.BaseActivity
import com.sibin.embibeassignment.base.baseclass.BaseFragment
import com.sibin.embibeassignment.base.common.logDebug
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.exception.ResponseFailure
import com.sibin.embibeassignment.base.extension.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject


class MovieSearchFragment : BaseFragment() {

    private lateinit var searchViewModel: SearchViewModel
    override fun layoutId() = R.layout.fragment_search_list

    @Inject
    lateinit var moviesAdapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        searchViewModel = viewModel(viewModelFactory) {
            observe(movieList, ::handleMovieList)
            error(failure, ::handleFailure)
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolBar()
        setRecyclerView()
        handleSearch()
    }

    private fun handleSearch() {
        search_bar.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                searchViewModel.searchQuery = it.toString()
                loadMovies()
            }
        }
    }

    private fun handleToolBar() {
        val activity = activity as BaseActivity?
        activity?.setSupportActionBar(toolbar)
        val actionBar = activity?.supportActionBar
        actionBar?.title = ""
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { getActivity()!!.onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadMovies() {
        showProgress()
        searchViewModel.getMovies()
    }

    private fun setRecyclerView() {
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        moviesAdapter =
            MovieListAdapter(searchViewModel.imageBaseUrl!!) { movie ->
                logDebug("movie $movie")
                searchViewModel.addOrRemoveBookmark(movie)
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
            moviesAdapter.data.clear()
            moviesAdapter.notifyDataSetChanged()
            recycler_view.invisible()
            error_text.visible()
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


