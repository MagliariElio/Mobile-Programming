package it.mem.myapplicationmarvel.model.entity.comicsdate

import com.google.gson.annotations.SerializedName
import it.mem.myapplicationmarvel.model.entity.Character
import it.mem.myapplicationmarvel.model.entity.Comics

data class Date(@SerializedName("offset") val offset: Int,
                @SerializedName("limit") val limit: Int,
                @SerializedName("total") val total:Int,
                @SerializedName("count") val count: Int,
                @SerializedName("results") val results: List<Comics>
)
