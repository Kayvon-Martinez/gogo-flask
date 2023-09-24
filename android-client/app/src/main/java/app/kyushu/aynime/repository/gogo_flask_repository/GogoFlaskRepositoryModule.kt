package app.kyushu.aynime.repository.gogo_flask_repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GogoFlaskRepositoryModule {
    @Binds
    abstract fun bindGogoFlaskRepository(gogoFlaskRepositoryImpl: GogoFlaskRepositoryImpl): GogoFlaskRepository
}