package it.mem.myapplicationmarvel.model.entity.charactersdate

import com.google.gson.annotations.SerializedName
import it.mem.myapplicationmarvel.model.entity.Character

data class Date(@SerializedName("offset") val offset: Int,
                @SerializedName("limit") val limit: Int,
                @SerializedName("total") val total:Int,
                @SerializedName("count") val count: Int,
                @SerializedName("results") val results: List<Character>
)
