package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Comics (
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("modified") val modified: String,
        @SerializedName("thumbnail") val thumbnail: Thumbnail,
        @SerializedName("resourceURI") val resourceURI: String,
        @SerializedName("characters") val characters: Character,
        @SerializedName("series") val series: Series,
        @SerializedName("stories") val stories: Stories,
        @SerializedName("events") val events: Events,
)
