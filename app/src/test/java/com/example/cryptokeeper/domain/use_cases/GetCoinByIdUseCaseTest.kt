package com.example.cryptokeeper.domain.use_cases

import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCoinByIdUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var repository: CoinRepository

    private lateinit var testSubject: GetCoinByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = GetCoinByIdUseCase(repository)
    }

    @Test
    fun `retrieves specified coin when given an ID`() = runTest {
        val coinId = "btc-bitcoin"

       mainCoroutineRule.launch {
           testSubject.invoke(coinId)

           coVerify {
               repository.getCoinById(coinId = coinId)
           }
       }
    }
}