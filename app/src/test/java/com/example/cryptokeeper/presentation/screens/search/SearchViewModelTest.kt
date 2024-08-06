package com.example.cryptokeeper.presentation.screens.search

import android.content.Context
import app.cash.turbine.test
import com.example.cryptokeeper.common.SharedPreferencesModule
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var getCoinsUseCase: GetCoinsUseCase

    @RelaxedMockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var sharedPref: SharedPreferencesModule

    private lateinit var testSubject: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = SearchViewModel(
            context,
            getCoinsUseCase,
            sharedPref
        )
    }

    @Test
    fun `OnSearchClicked() send an action that triggers OnSearchClicked event`() {
        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(SearchAction.OnSearchClicked(QUERY))
                val item = awaitItem()
                assertTrue { item is SearchEvent.OnSearchClicked }
            }
        }
    }

    @Test
    fun `OnCoinClicked() send an action that triggers OnCoinClicked event`() {
        val coinId = "btc-bitcoin"
        val coinName = "Bitcoin"

        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(
                    SearchAction.OnCoinClicked(
                        coinId = coinId,
                        coinName = coinName
                    )
                )
                val item = awaitItem()
                assertTrue { item is SearchEvent.OnCoinClicked }
            }
        }
    }

    @Test
    fun `OnSearchHistoryItemClicked() stores query in sharedPreferences cache`() {
        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(SearchAction.OnSearchHistoryItemClicked(QUERY))
                verify { sharedPref.put(QUERY) }
            }
        }
    }

    companion object {
        private const val QUERY = "BTC"
    }
}