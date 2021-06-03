package it.mem.myapplicationmarvel.model.entity.charactersdate

import com.google.gson.annotations.SerializedName

data class ResponseCharacters(
    @SerializedName("code") val code: Int?, //200
    @SerializedName("etag") val etag: String?, //8564460db2969053daea6042fb7b535a7f12351f
    @SerializedName("data") val data: Date
)

