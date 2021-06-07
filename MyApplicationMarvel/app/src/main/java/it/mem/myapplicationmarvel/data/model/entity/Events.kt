package it.mem.myapplicationmarvel.data.model.entity

import com.google.gson.annotations.SerializedName

class Events (
    @SerializedName("id") val id: Int, //1
    @SerializedName("title") val title: String?, //http://gateway.marvel.com/v1/public/characters/1011334/events
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int //1
)
