package com.tonghannteng.turo.presentation

import com.tonghannteng.turo.data.Business
import com.tonghannteng.turo.data.MainRepository
import com.tonghannteng.turo.data.SearchResponse
import com.tonghannteng.turo.presentation.main.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mainRepository: MainRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mainRepository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch Pizza and Beer and combine result successfully`() = runTest {
        // GIVEN
        val pizzaList = listOf(Business(id = "1", name = "pizza1"), Business(id = "2", name = "pizza2"))
        val beerList = listOf(Business(id = "3", name = "beer1"))
        coEvery { mainRepository.getPizza() } returns SearchResponse(businesses = pizzaList)
        coEvery { mainRepository.getBeer() } returns SearchResponse(businesses = beerList)

        // WHEN
        mainViewModel = MainViewModel(mainRepository = mainRepository)
        advanceUntilIdle()

        // THEN
        val result = mainViewModel.result.value
        assertEquals(3, result.businessList.size)
        assertTrue(result.businessList.containsAll(pizzaList + beerList))
    }

    @Test
    fun `combine Pizza and Beer WHEN Pizza request API failed AND Beer API Success`() = runTest {
        // GIVEN
        val beerList = listOf(Business(id = "1", name = "beer1"))
        coEvery { mainRepository.getPizza() } throws RuntimeException("Pizza API failed")
        coEvery { mainRepository.getBeer() } returns SearchResponse(businesses = beerList)

        // WHEN
        mainViewModel = MainViewModel(mainRepository = mainRepository)
        advanceUntilIdle()

        // THEN
        val result = mainViewModel.result.value.businessList
        assertEquals(1, result.size)
        assertEquals("beer1", result[0].name)
    }

    @Test
    fun `combine Pizza and Beer WHEN Beer API request failed AND Pizza API Success`() = runTest {
        // GIVEN
        val pizzaList = listOf(Business(id = "1", name = "pizza"))
        coEvery { mainRepository.getPizza() } returns SearchResponse(businesses = pizzaList)
        coEvery { mainRepository.getBeer() } throws RuntimeException("Beer API failed")

        // WHEN
        mainViewModel = MainViewModel(mainRepository = mainRepository)
        advanceUntilIdle()

        // THEN
        val result = mainViewModel.result.value.businessList
        assertEquals(1, result.size)
        assertEquals("pizza", result[0].name)
    }

    @Test
    fun `should return empty list WHEN Beer API request failed AND Pizza API request failed`() = runTest {
        // GIVEN
        coEvery { mainRepository.getPizza() } throws RuntimeException("Pizza API failed")
        coEvery { mainRepository.getBeer() } throws RuntimeException("Beer API failed")

        // WHEN
        mainViewModel = MainViewModel(mainRepository = mainRepository)
        advanceUntilIdle()

        // THEN
        val result = mainViewModel.result.value.businessList
        assertTrue(result.isEmpty())
    }

}
