package me.vinitagrawal.posts.di

import android.content.Context
import androidx.room.Room
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import io.appflate.restmock.RESTMockServer
import me.vinitagrawal.common.di.ViewModelModule
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.TestApplication
import me.vinitagrawal.posts.post.data.PostDao
import me.vinitagrawal.posts.post.data.PostDatabase
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            TestModule::class,
            ViewModelModule::class,
            ActivityBindingModule::class]
)
interface TestComponent {
    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun with(context: Context): Builder

        fun build(): TestComponent
    }
}

@Module
class TestModule {
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

    @Provides
    fun providesPostDatabase(context: Context): PostDatabase {
        return Room.inMemoryDatabaseBuilder(context, PostDatabase::class.java).build()
    }

    @Provides
    fun providesPostDao(database: PostDatabase): PostDao {
        return database.postDao()
    }
}