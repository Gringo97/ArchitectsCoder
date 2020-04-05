package oscar.delafuente.arichitectcoders

import android.app.Application
import oscar.delafuente.arichitectcoders.di.DaggerMyMoviesComponent
import oscar.delafuente.arichitectcoders.di.MyMoviesComponent

class MoviesApp : Application() {

    lateinit var component: MyMoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyMoviesComponent
            .factory()
            .create(this)
    }
}