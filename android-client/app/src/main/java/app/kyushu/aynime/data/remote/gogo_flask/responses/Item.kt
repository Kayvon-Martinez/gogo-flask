package app.kyushu.aynime.data.remote.gogo_flask.responses

data class Item(
    val id: String,
    val imageUrl: String,
    val numOfEps: Int?,
    val sub: Boolean,
    val title: String
)