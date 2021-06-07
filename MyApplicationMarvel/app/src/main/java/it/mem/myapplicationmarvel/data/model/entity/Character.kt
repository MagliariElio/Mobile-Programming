package it.mem.myapplicationmarvel.data.model.entity


import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("id") val id: Int, //1011334
    @SerializedName("name") val name: String, //3-D Man
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String, //2014-04-29T14:18:17-0400
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("resourceURI") val resourceURI: String, //http://gateway.marvel.com/v1/public/characters/1011334
    @SerializedName("comics") val comics: Comics,
    @SerializedName("series") val series: Series,
    @SerializedName("stories") val stories: Stories,
    @SerializedName("events") val events: Events,
    @SerializedName("urls") val urls: List<Url>

)