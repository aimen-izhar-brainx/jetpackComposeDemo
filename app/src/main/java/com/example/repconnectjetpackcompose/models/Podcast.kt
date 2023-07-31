import java.io.Serializable

data class Podcast  (
    var title: String? = null,
    var link: String? = null,
    var description: String? = null,
    var pubDate: String? = null,
    var image: String? = null,
    var duration: String? = null,


    ): Serializable