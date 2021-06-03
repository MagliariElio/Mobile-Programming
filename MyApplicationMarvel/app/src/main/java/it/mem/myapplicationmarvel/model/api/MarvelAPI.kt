package it.mem.myapplicationmarvel.model.api

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import it.mem.myapplicationmarvel.extensions.md5
import it.mem.myapplicationmarvel.model.entity.charactersdate.ResponseCharacters
import it.mem.myapplicationmarvel.model.entity.comicsdate.ResponseComics
import it.mem.myapplicationmarvel.utils.API_KEY
import it.mem.myapplicationmarvel.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import it.mem.myapplicationmarvel.utils.PRIVATE_KEY
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

interface MarvelAPI {
    @GET("characters")
    fun allCharacters(@Query("offset") offset:Int?=0):Observable<ResponseCharacters>

    @GET("characters")
    fun searchCharacters(@Query("nameStartsWith") nameStartsWith:String?="",@Query("offset") offset:Int?=0):Observable<ResponseCharacters>


    @GET("comics")
    fun allComics(@Query("offset") offset: Int?=0):Observable<ResponseComics>

    companion object {
        fun getService(): MarvelAPI {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", API_KEY)
                        .addQueryParameter("ts", ts)
                        .addQueryParameter("hash", "$ts$PRIVATE_KEY$API_KEY".md5())
                        .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create(MarvelAPI::class.java)
        }
    }
}