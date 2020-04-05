package oscar.delafuente.arichitectcoders.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import oscar.delafuente.arichitectcoders.ui.detail.DetailViewModel
import oscar.delafuente.arichitectcoders.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UseCaseModule::class, ViewModelsModule::class])
interface MyMoviesComponent {

    val mainViewModel: MainViewModel
    val detaiViewModel: DetailViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}