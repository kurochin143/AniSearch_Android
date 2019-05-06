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

        JikanApiDao.searchAnime("Grand Blue", 1).enqueue(object: Callback<SearchMedia<MediaAnime>?>{
            override fun onFailure(call: Call<SearchMedia<MediaAnime>?>, t: Throwable) {
                assert(false)
            }

            override fun onResponse(
                call: Call<SearchMedia<MediaAnime>?>,
                response: Response<SearchMedia<MediaAnime>?>
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

        JikanApiDao.searchManga("Grand Blue", 1).enqueue(object: Callback<SearchMedia<MediaManga>?>{
            override fun onFailure(call: Call<SearchMedia<MediaManga>?>, t: Throwable) {
                assert(false)
            }

            override fun onResponse(
                call: Call<SearchMedia<MediaManga>?>,
                response: Response<SearchMedia<MediaManga>?>
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