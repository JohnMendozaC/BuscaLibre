package com.john.buscalibre.list_product.ui

import com.john.buscalibre.list_product.domain.ProductUseCase
import com.john.buscalibre.shared.domain.models.FakeProductInquiry.getFakeProductInquiry
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.ui.status.StatusUI
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    private val productUseCase: ProductUseCase = mockk(relaxed = true)
    private lateinit var viewModel: ProductsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductsViewModel(productUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a successful product search When products are loaded Then the products returned by the meli service are charged`() =
        runTest {
            // Given
            val fakeQuery = "test"
            val fakeProductInquiry = getFakeProductInquiry()
            coEvery { productUseCase.searchProduct(fakeQuery) } returns ProductsResolverResult.Success(
                fakeProductInquiry
            )

            // When
            viewModel.loadProducts(fakeQuery)

            // Then
            val expectedStatus = StatusUI.Success(fakeProductInquiry.products)
            expectedStatus shouldBe viewModel.uIStatus.value
        }

    @Test
    fun `Given an error in product search When products are loaded Then an error status is returned`() =
        runTest {
            // Given
            val fakeQuery = "test"
            coEvery { productUseCase.searchProduct(fakeQuery) } returns ProductsResolverResult.Error(ANY_ERROR)

            // When
            viewModel.loadProducts(fakeQuery)

            // Then
            // Verify that the UI status is Error after loading products
            val expectedStatus = StatusUI.Error<List<Product>>(ANY_ERROR)
            expectedStatus shouldBe viewModel.uIStatus.value
        }

    companion object {
        const val ANY_ERROR = 0
    }

}
