package com.sibin.embibeassignment.appFeatures.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieDetails
import com.sibin.embibeassignment.base.extension.loadFromUrl
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListAdapter(
    private val baseUrl: String,
    private val addToBookMark: (MovieDetails) -> Unit
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ImageView = view.movieImage
        val bookMarkIcon: ImageView = view.bookMark
        val movieName: TextView = view.movieName
    }

    val data = mutableListOf<MovieDetails>()

    fun addList(movies: List<MovieDetails>) {
        data.clear()
        data.addAll(movies)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(data[position]) {
            holder.movieName.text = this.originalTitle
            reflectBookMark(this, holder)
            (this.backdropPath ?: this.posterPath)?.let {
                holder.movieImage.loadFromUrl(baseUrl + it)
            }
            holder.bookMarkIcon.setOnClickListener {
                data[position].isBookmarked = if (data[position].isBookmarked == 1) 0 else 1
                addToBookMark(data[position])
                reflectBookMark(this, holder)
            }
        }
    }

    private fun reflectBookMark(details: MovieDetails, holder: ViewHolder) {
        if (details.isBookmarked == 1) {
            holder.bookMarkIcon.setImageResource(R.drawable.ic_bookmarked)
        } else {
            holder.bookMarkIcon.setImageResource(R.drawable.ic_bookmark_border)
        }
    }
}