package me.vinitagrawal.posts.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import io.appflate.restmock.RESTMockServer
import me.vinitagrawal.common.di.ViewModelModule
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.TestApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkTestModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class]
)
interface TestComponent {
    fun inject(application: TestApplication)
}

@Module
class NetworkTestModule {
    @Provides
    fun providesNetworkFactory(): NetworkFactory {
        return NetworkFactory(
            object : NetworkFactory.Dependency {
                override fun getBaseUrl() = RESTMockServer.getUrl()
            })
    }

    @Provides
    fun providesLogger(): Logger {
        return Logger()
    }
}