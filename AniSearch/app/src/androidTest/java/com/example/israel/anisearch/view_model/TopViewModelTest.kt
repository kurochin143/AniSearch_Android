package com.example.israel.anisearch.view_model

import androidx.lifecycle.Observer
import com.example.israel.anisearch.api.AniListApiService
import com.example.israel.anisearch.api.CharacterSearchResult
import com.example.israel.anisearch.model.data.TData
import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.model.presentation.TopList
import com.example.israel.anisearch.repository.AniSearchRepositoryImpl
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*

class TopViewModelTest {

    private fun <T> kany(type: Class<T>): T = any<T>(type)

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAll() {

        val mockApiService = mock(AniListApiService::class.java)

        `when`(mockApiService.searchCharacter(kany(GraphQLQuery::class.java))).thenReturn(Single.just(mock(TData::class.java) as CharacterSearchResult))

        val aniSearchRepository = AniSearchRepositoryImpl(mockApiService)

        val viewModel = TopVMFactory(aniSearchRepository)
            .create(TopViewModel::class.java)

        val mockObserver= mock(Observer::class.java) as Observer<TopList>

        viewModel.getTopListLiveData().observeForever(mockObserver)
        viewModel.topAnimeListSelected(1, 1)

        verify(mockObserver).onChanged(kany(TopList::class.java))
    }

}