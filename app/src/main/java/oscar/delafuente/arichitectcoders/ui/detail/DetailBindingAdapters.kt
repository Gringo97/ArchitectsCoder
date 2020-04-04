package oscar.delafuente.arichitectcoders.ui.detail

import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.database.Movie

@BindingAdapter("movie")
fun TextView.updateMovieDetails(movie: Movie?) = movie?.run {
    text = buildSpannedString {

        bold { append("Original language: ") }
        appendln(originalLanguage)

        bold { append("Original title: ") }
        appendln(originalTitle)

        bold { append("Release date: ") }
        appendln(releaseDate)

        bold { append("Popularity: ") }
        appendln(popularity.toString())

        bold { append("Vote Average: ") }
        append(voteAverage.toString())
    }
}

@BindingAdapter("favorite")
fun FloatingActionButton.setFavorite(favorite: Boolean?) {
    val icon = if (favorite == true) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
    setImageDrawable(context.getDrawable(icon))
}