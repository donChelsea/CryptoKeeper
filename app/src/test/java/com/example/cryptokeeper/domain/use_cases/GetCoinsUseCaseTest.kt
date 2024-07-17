package com.example.cryptokeeper.domain.use_cases

import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCoinsUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @RelaxedMockK
    lateinit var repository: CoinRepository

    private lateinit var testSubject: GetCoinsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        testSubject = GetCoinsUseCase(repository)
    }

    @Test
    fun `retrieves all coins from api`() = runTest {
        mainCoroutineRule.launch {
            testSubject.invoke()

            coVerify { repository.getCoins() }
        }
    }
}