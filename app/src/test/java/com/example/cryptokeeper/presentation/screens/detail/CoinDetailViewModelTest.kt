package com.example.cryptokeeper.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.cryptokeeper.domain.use_cases.GetCoinByIdUseCase
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_ID
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class CoinDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @RelaxedMockK
    lateinit var getCoinByIdUseCase: GetCoinByIdUseCase

    private lateinit var testSubject: CoinDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        every { savedStateHandle.get<String>(COIN_ID) } returns "coinId"
        testSubject = CoinDetailViewModel(
            savedStateHandle,
            getCoinByIdUseCase
        )
    }

    @Test
    fun `onBackClicked() send an action that triggers OnBackClicked event`() {
        mainCoroutineRule.launch {
            testSubject.events.test {
                testSubject.handleAction(CoinDetailAction.OnBackClicked)
                val item = awaitItem()
                assertTrue { item is CoinDetailEvent.OnBackClicked }
            }
        }
    }
}