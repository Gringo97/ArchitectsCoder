package oscar.delafuente.arichitectcoders.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.databinding.ViewMovieBinding
import oscar.delafuente.arichitectcoders.model.database.Movie
import oscar.delafuente.arichitectcoders.ui.common.basicDiffUtil
import oscar.delafuente.arichitectcoders.ui.common.bindingInflate

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_movie, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(val dataBinding: ViewMovieBinding) : RecyclerView.ViewHolder(dataBinding.root)
}