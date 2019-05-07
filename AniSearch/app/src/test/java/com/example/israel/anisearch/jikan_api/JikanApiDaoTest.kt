package com.example.israel.anisearch.jikan_api

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JikanApiDaoTest {

    @Test
    fun searchAnime() {
        val waiter = Object()

        JikanApiDao.searchAnime("Grand Blue", 1).enqueue(object: Callback<SearchResult<MediaAnime>?>{
            override fun onFailure(call: Call<SearchResult<MediaAnime>?>, t: Throwable) {
                assert(false)
            }

            override fun onResponse(
                call: Call<SearchResult<MediaAnime>?>,
                response: Response<SearchResult<MediaAnime>?>
            ) {
                assert(response.isSuccessful)
                val searchMedia = response.body()
                assert(searchMedia != null)
                assertNotEquals(0, searchMedia!!.results!!.size == 0)
                assertEquals("Grand Blue", searchMedia!!.results!![0].title)

                synchronized(waiter) {
                    waiter.notify()
                }

            }
        })

        synchronized(waiter) {
            waiter.wait()
        }

    }

    @Test
    fun searchManga() {
        val waiter = Object()

        JikanApiDao.searchManga("Grand Blue", 1).enqueue(object: Callback<SearchResult<MediaManga>?>{
            override fun onFailure(call: Call<SearchResult<MediaManga>?>, t: Throwable) {
                assert(false)
            }

            override fun onResponse(
                call: Call<SearchResult<MediaManga>?>,
                response: Response<SearchResult<MediaManga>?>
            ) {
                assert(response.isSuccessful)
                val searchMedia = response.body()
                assert(searchMedia != null)
                assertNotEquals(0, searchMedia!!.results!!.size == 0)
                assertEquals("Grand Blue", searchMedia!!.results!![0].title)

                synchronized(waiter) {
                    waiter.notify()
                }
            }
        })

        synchronized(waiter) {
            waiter.wait()
        }
    }

}