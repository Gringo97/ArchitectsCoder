package oscar.delafuente.arichitectcoders.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.oscardelafuente.domain.Movie

class MovieDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    fun setMovie(movie: Movie) = with(movie) {
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
}