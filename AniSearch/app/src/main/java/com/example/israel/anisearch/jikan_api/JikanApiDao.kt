package com.example.israel.anisearch.jikan_api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.jikan.moe/v3/"
private const val READ_TIMEOUT = 3000L
private const val CONNECT_TIMEOUT = 3000L
private const val SEARCH = "search/"
private const val ANIME = "anime/"
private const val MANGA = "manga/"

class JikanApiDao {

    companion object {
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
        ): Call<SearchResult<MediaAnime>?> {
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
            return apiService.searchAnime(queryParams)
        }

    }

    interface JikanApiInterface {
        @GET(SEARCH + ANIME)
        fun searchAnime(@QueryMap queryParams: HashMap<String, String>): Call<SearchResult<MediaAnime>?>

        @GET(SEARCH + MANGA)
        fun searchManga(@Query("q") query: String, @Query("page") page: Int): Call<SearchResult<MediaManga>?>

    }
}