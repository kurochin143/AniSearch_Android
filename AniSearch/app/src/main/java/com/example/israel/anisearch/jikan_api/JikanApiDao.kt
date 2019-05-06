package com.example.israel.anisearch.jikan_api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class JikanApiDao {

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v3/"
        private const val READ_TIMEOUT = 3000L
        private const val CONNECT_TIMEOUT = 3000L
        private const val SEARCH = "search/"
        private const val ANIME = "anime/"
        private const val MANGA = "manga/"

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .client(okHttpClient)
            .build()

        private val apiService: JikanApiInterface = retrofit.create(
            JikanApiInterface::class.java)

        fun searchAnime(query: String, page: Int): Call<SearchMedia<MediaAnime>?> {
            return apiService.searchAnime(query, page)
        }

        fun searchManga(query: String, page: Int): Call<SearchMedia<MediaManga>?> {
            return apiService.searchManga(query, page)
        }
    }

    interface JikanApiInterface {
        @GET(SEARCH + ANIME)
        fun searchAnime(@Query("q") query: String, @Query("page") page: Int): Call<SearchMedia<MediaAnime>?>

        @GET(SEARCH + MANGA)
        fun searchManga(@Query("q") query: String, @Query("page") page: Int): Call<SearchMedia<MediaManga>?>

    }
}