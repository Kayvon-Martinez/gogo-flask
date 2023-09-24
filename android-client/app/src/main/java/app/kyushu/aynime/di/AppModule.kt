package app.kyushu.aynime.di

import app.kyushu.aynime.data.remote.gogo_flask.GogoFlaskApi
import app.kyushu.aynime.repository.gogo_flask_repository.GogoFlaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGogoFlaskRepository(
        gogoFlaskApi: GogoFlaskApi
    ) = GogoFlaskRepositoryImpl(gogoFlaskApi)

    @Singleton
    @Provides
    fun provideGogoFlaskApi(): GogoFlaskApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.157:8080/")
            .build()
            .create(GogoFlaskApi::class.java)
    }
}