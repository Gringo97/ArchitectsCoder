package oscar.delafuente.arichitectcoders.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import oscar.delafuente.arichitectcoders.model.database.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    (adapter as? MoviesAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}