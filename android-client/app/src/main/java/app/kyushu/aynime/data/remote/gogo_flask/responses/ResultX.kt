package app.kyushu.aynime.data.remote.gogo_flask.responses

data class ResultX(
    val episodes: List<Episode>,
    val genres: List<Genre>,
    val id: String,
    val imageUrl: String,
    val numOfEps: Int,
    val otherNames: List<String>,
    val released: String,
    val status: Status,
    val sub: Boolean,
    val synopsis: String,
    val title: String,
    val type: Type
)