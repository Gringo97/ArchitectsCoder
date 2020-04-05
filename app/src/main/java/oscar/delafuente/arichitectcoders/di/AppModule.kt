package oscar.delafuente.arichitectcoders.di

import android.app.Application
import androidx.room.Room
import com.oscardelafuente.data.repository.PermissionChecker
import com.oscardelafuente.data.source.LocalDataSource
import com.oscardelafuente.data.source.LocationDataSource
import com.oscardelafuente.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.data.AndroidPermissionChecker
import oscar.delafuente.arichitectcoders.data.PlayServicesLocationDataSource
import oscar.delafuente.arichitectcoders.data.database.MovieDatabase
import oscar.delafuente.arichitectcoders.data.database.RoomDataSource
import oscar.delafuente.arichitectcoders.data.server.TheMovieDbDataSource
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}