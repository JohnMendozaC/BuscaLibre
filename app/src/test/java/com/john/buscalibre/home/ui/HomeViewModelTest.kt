package com.john.buscalibre.home.ui

import com.john.buscalibre.shared.domain.models.FakeProductInquiry.getFakeProductInquiry
import com.john.buscalibre.home.domain.HomeUseCase
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.ui.status.StatusUI
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val homeUseCase: HomeUseCase = mockk(relaxed = true)
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(homeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a successful product search When products are loaded Then the products returned by the use case are charged`() =
        runTest {
            // Given
            val fakeQuery = "test"
            val fakeProductInquiry = getFakeProductInquiry()
            coEvery { homeUseCase.searchProduct(fakeQuery) } returns ProductsResolverResult.Success(fakeProductInquiry)

            // When
            viewModel.loadProducts(fakeQuery)

            // Then
            val expectedStatus = StatusUI.Success(fakeProductInquiry.products)
            assertEquals(expectedStatus, viewModel.uIStatus.value)
        }

    @Test
    fun `Given an error in product search When products are loaded Then an error status is returned`() =
        runTest {
            // Given
            val fakeQuery = "test"
            coEvery { homeUseCase.searchProduct(fakeQuery) } returns ProductsResolverResult.Error(ANY_ERROR)

            // When
            viewModel.loadProducts(fakeQuery)

            // Then
            val expectedStatus = StatusUI.Error<List<Product>>(ANY_ERROR)
            assertEquals(expectedStatus, viewModel.uIStatus.value)
        }

    companion object {
        const val ANY_ERROR = 0
    }
}
