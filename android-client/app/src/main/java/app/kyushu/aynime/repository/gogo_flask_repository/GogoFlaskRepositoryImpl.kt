package app.kyushu.aynime.repository.gogo_flask_repository

import app.kyushu.aynime.data.remote.gogo_flask.GogoFlaskApi
import app.kyushu.aynime.data.remote.gogo_flask.responses.DetailedItem
import app.kyushu.aynime.data.remote.gogo_flask.responses.HomeCategories
import app.kyushu.aynime.data.remote.gogo_flask.responses.RawEpisodeSources
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import app.kyushu.aynime.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class GogoFlaskRepositoryImpl @Inject constructor(
    private val gogoFlaskApi: GogoFlaskApi
) : GogoFlaskRepository {

    override suspend fun getHomeCats(): Resource<HomeCategories> {
        return try {
            val response = gogoFlaskApi.getHomeCats()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getDetails(id: String): Resource<DetailedItem> {
        return try {
            val response = gogoFlaskApi.getDetails(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun search(query: String): Resource<SearchResults> {
        return try {
            val response = gogoFlaskApi.search(query)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getRawEpisodeSources(episodeId: String): Resource<RawEpisodeSources> {
        return try {
            val response = gogoFlaskApi.getRawEpisodeSources(episodeId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}