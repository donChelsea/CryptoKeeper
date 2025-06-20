package com.example.cryptokeeper.presentation.features.home

import app.cash.turbine.test
import com.example.cryptokeeper.domain.modules.ConnectivityModule
import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var repository: CoinRepository

    @RelaxedMockK
    lateinit var connectivityModule: ConnectivityModule

    private lateinit var testSubject: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = HomeViewModel(
            repository,
            connectivityModule
        )
    }

    @Test
    fun `handleAction() sends OnCoinClick action to trigger OnCoinClick event`() {
        val coinId = "btc-bitcoin"
        val coinName = "Bitcoin"

        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(HomeAction.OnCoinClicked(coinId = coinId, coinName = coinName))
                val item = awaitItem()
                assertTrue { item is HomeEvent.OnCoinClicked }
            }
        }
    }
}