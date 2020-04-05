package oscar.delafuente.arichitectcoders.di

import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.data.repository.PermissionChecker
import com.oscardelafuente.data.repository.RegionRepository
import com.oscardelafuente.data.source.LocalDataSource
import com.oscardelafuente.data.source.LocationDataSource
import com.oscardelafuente.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}