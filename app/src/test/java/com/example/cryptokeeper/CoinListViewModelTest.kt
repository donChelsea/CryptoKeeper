package com.example.cryptokeeper

import app.cash.turbine.test
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import com.example.cryptokeeper.presentation.screens.list.CoinListAction
import com.example.cryptokeeper.presentation.screens.list.CoinListEvent
import com.example.cryptokeeper.presentation.screens.list.CoinListViewModel
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class CoinListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var testSubject: CoinListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(relaxed = true)
        val getCoinsUseCase: GetCoinsUseCase = mockk()
        testSubject = CoinListViewModel(getCoinsUseCase)
    }

    @Test
    fun `handleAction() sends OnCoinClick action to trigger OnCoinClick event`() {
        val coinId = "btc-bitcoin"

        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(CoinListAction.OnCoinClicked(coinId))
                val item = awaitItem()
                assertTrue { item is CoinListEvent.OnCoinClicked }
            }
        }
    }
}