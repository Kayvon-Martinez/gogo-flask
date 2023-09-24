package app.kyushu.aynime.repository.gogo_flask_repository

import app.kyushu.aynime.data.remote.gogo_flask.responses.DetailedItem
import app.kyushu.aynime.data.remote.gogo_flask.responses.HomeCategories
import app.kyushu.aynime.data.remote.gogo_flask.responses.RawEpisodeSources
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import app.kyushu.aynime.util.Resource

interface GogoFlaskRepository {
    suspend fun getHomeCats(): Resource<HomeCategories>
    suspend fun getDetails(id: String): Resource<DetailedItem>
    suspend fun search(query: String): Resource<SearchResults>
    suspend fun getRawEpisodeSources(episodeId: String): Resource<RawEpisodeSources>
}