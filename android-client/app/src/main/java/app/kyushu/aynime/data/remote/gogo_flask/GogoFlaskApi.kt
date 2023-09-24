package app.kyushu.aynime.data.remote.gogo_flask

import app.kyushu.aynime.data.remote.gogo_flask.responses.DetailedItem
import app.kyushu.aynime.data.remote.gogo_flask.responses.HomeCategories
import app.kyushu.aynime.data.remote.gogo_flask.responses.RawEpisodeSources
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import retrofit2.http.GET
import retrofit2.http.Path

interface GogoFlaskApi {
    @GET("api/home-cats")
    suspend fun getHomeCats(): HomeCategories
    @GET("api/details/{id}")
    suspend fun getDetails(
        @Path("id") id: String
    ): DetailedItem
    @GET("api/search/{query}")
    suspend fun search(
        @Path("query") query: String
    ): SearchResults
    @GET("api/raw-video-sources/{episodeId}")
    suspend fun getRawEpisodeSources(
        @Path("episodeId") episodeId: String
    ): RawEpisodeSources
}