package com.example.israel.anisearch.jikan_api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

class JikanApiDao {

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v3/"
        private const val READ_TIMEOUT = 3000L
        private const val CONNECT_TIMEOUT = 3000L
        private const val TOP = "top/"
        private const val SEARCH = "search/"
        private const val ANIME = "anime/"
        private const val MANGA = "manga/"
        private const val CHARACTER = "character/"
        private const val STAFF = "staff/"

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .client(okHttpClient)
            .build()

        val apiService: JikanApiInterface = retrofit.create(
            JikanApiInterface::class.java)

        fun searchAnime(
            query: String,
            page: Int,
            type: String?,
            status: String?,
            rated: String?,
            genre: Int?,
            score: Float?,
            startDate: String?,
            endDate: String?
        ): Call<JikanResultList<JikanResult>?> {
            val queryParams = HashMap<String, String>()
            queryParams["q"] = query
            queryParams["page"] = page.toString()
            if (type != null) queryParams["type"] = type
            if (status != null) queryParams["status"] = status
            if (rated != null) queryParams["rated"] = rated
            if (genre != null) queryParams["genre"] = genre.toString()
            if (score != null) queryParams["score"] = score.toString()
            if (startDate != null) queryParams["start_date"] = startDate
            if (endDate != null) queryParams["end_date"] = endDate

            return apiService.search("anime", queryParams)
        }

        fun searchManga(
            query: String,
            page: Int,
            type: String?,
            status: String?,
            rated: String?,
            genre: Int?,
            score: Float?,
            startDate: String?,
            endDate: String?
        ): Call<JikanResultList<JikanResult>?> {
            val queryParams = HashMap<String, String>()
            queryParams["q"] = query
            queryParams["page"] = page.toString()
            if (type != null) queryParams["type"] = type
            if (status != null) queryParams["status"] = status
            if (rated != null) queryParams["rated"] = rated
            if (genre != null) queryParams["genre"] = genre.toString()
            if (score != null) queryParams["score"] = score.toString()
            if (startDate != null) queryParams["start_date"] = startDate
            if (endDate != null) queryParams["end_date"] = endDate

            return apiService.search("manga", queryParams)
        }

    }

    interface JikanApiInterface {

        /**
         * @param type anime, manga, characters, people
         *
         * @param subtype only for anime and manga
         * Anime: airing upcoming tv movie ova special
         * Manga: manga novels oneshots doujin manhwa manhua
         * Both: bypopularity favorite
         * */
        @GET("$TOP{type}/{page}/{subtype}/")
        fun getTop(@Path(value = "type") type: String, @Path(value = "page") page: Int, @Path(value = "subtype") subtype: String): Call<JikanResultList<JikanResult>?>

        /**
         * @param type anime, manga, characters, people
         * */
        @GET("$TOP{type}/{page}/")
        fun getTop(@Path(value = "type") type: String, @Path(value = "page") page: Int): Call<JikanResultList<JikanResult>?>

        @GET("$SEARCH{type}/")
        fun search(@Path(value = "type") type: String, @QueryMap queryParams: HashMap<String, String>): Call<JikanResultList<JikanResult>?>

    }
}