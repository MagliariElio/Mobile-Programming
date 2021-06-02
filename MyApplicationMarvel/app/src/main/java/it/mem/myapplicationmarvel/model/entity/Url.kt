package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

class Url(
    @SerializedName("type") val type: String, //detail
    @SerializedName("url") val url: String //http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=bae8d2281a0d569b0cfd5fbc7cd6ee6f
)
