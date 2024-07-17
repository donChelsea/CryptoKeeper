package com.example.cryptokeeper.presentation.screens.home

import app.cash.turbine.test
import com.example.cryptokeeper.domain.use_cases.GetCoinByIdUseCase
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import com.example.cryptokeeper.presentation.screens.home.HomeAction
import com.example.cryptokeeper.presentation.screens.home.HomeEvent
import com.example.cryptokeeper.presentation.screens.home.HomeViewModel
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
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
    lateinit var getCoinsUseCase: GetCoinsUseCase

    private lateinit var testSubject: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = HomeViewModel(getCoinsUseCase)
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