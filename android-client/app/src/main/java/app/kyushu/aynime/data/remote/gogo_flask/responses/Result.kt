package app.kyushu.aynime.data.remote.gogo_flask.responses

data class Result(
    val items: List<Item>,
    val name: String,
    val pageUrls: List<String>
)