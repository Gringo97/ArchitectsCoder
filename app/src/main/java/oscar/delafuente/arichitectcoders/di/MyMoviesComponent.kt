package oscar.delafuente.arichitectcoders.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivityComponent
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivityModule
import oscar.delafuente.arichitectcoders.ui.main.MainActivityComponent
import oscar.delafuente.arichitectcoders.ui.main.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyMoviesComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule): DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}