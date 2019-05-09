package com.example.israel.anisearch.jikan_api

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JikanApiDaoTest {
//
//    @Test
//    fun searchAnime() {
//        val waiter = Object()
//
//        JikanApiDao.searchAnime(
//            "Grand Blue", 1, null, null,
//            null, null, null, null, null)
//            .enqueue(object: Callback<JikanResultList<MediaAnime>?> {
//            override fun onFailure(call: Call<JikanResultList<MediaAnime>?>, t: Throwable) {
//                assert(false)
//            }
//
//            override fun onResponse(
//                call: Call<JikanResultList<MediaAnime>?>,
//                response: Response<JikanResultList<MediaAnime>?>
//            ) {
//                assert(response.isSuccessful)
//                val searchMedia = response.body()
//                assert(searchMedia != null)
//                assertNotEquals(0, searchMedia!!.results!!.size == 0)
//                assertEquals("Grand Blue", searchMedia!!.results!![0].title)
//
//                synchronized(waiter) {
//                    waiter.notify()
//                }
//            }
//        })
//
//        synchronized(waiter) {
//            waiter.wait()
//        }
//
//    }
//
//    @Test
//    fun searchManga() {
//        val waiter = Object()
//
//        JikanApiDao.searchManga(
//            "Grand Blue", 1, null, null,
//            null, null, null, null, null)
//            .enqueue(object: Callback<JikanResultList<MediaManga>?>{
//            override fun onFailure(call: Call<JikanResultList<MediaManga>?>, t: Throwable) {
//                assert(false)
//            }
//
//            override fun onResponse(
//                call: Call<JikanResultList<MediaManga>?>,
//                response: Response<JikanResultList<MediaManga>?>
//            ) {
//                assert(response.isSuccessful)
//                val searchMedia = response.body()
//                assert(searchMedia != null)
//                assertNotEquals(0, searchMedia!!.results!!.size == 0)
//                assertEquals("Grand Blue", searchMedia!!.results!![0].title)
//
//                synchronized(waiter) {
//                    waiter.notify()
//                }
//            }
//        })
//
//        synchronized(waiter) {
//            waiter.wait()
//        }
//    }

}