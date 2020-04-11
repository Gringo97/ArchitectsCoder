package oscar.delafuente.arichitectcoders

import android.app.Application
import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.data.repository.PermissionChecker
import com.oscardelafuente.data.repository.RegionRepository
import com.oscardelafuente.data.source.LocalDataSource
import com.oscardelafuente.data.source.LocationDataSource
import com.oscardelafuente.data.source.RemoteDataSource
import com.oscardelafuente.usecases.FindMovieById
import com.oscardelafuente.usecases.GetPopularMovies
import com.oscardelafuente.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import oscar.delafuente.arichitectcoders.data.AndroidPermissionChecker
import oscar.delafuente.arichitectcoders.data.PlayServicesLocationDataSource
import oscar.delafuente.arichitectcoders.data.database.MovieDatabase
import oscar.delafuente.arichitectcoders.data.database.RoomDataSource
import oscar.delafuente.arichitectcoders.data.server.TheMovieDbDataSource
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivity
import oscar.delafuente.arichitectcoders.ui.detail.DetailViewModel
import oscar.delafuente.arichitectcoders.ui.main.MainActivity
import oscar.delafuente.arichitectcoders.ui.main.MainViewModel

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(),get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(), get())}
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}