package com.john.buscalibre.products_details.ui


import com.john.buscalibre.shared.domain.models.FakeProduct.getFakeProduct
import com.john.buscalibre.shared.domain.models.FakeProductInquiry.getFakeProductInquiry
import com.john.buscalibre.products_details.domain.ProductDetailsUseCase
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.ui.status.StatusUI
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailsViewModelTest {

    private val productDetailsUseCase: ProductDetailsUseCase = mockk(relaxed = true)
    private lateinit var viewModel: ProductDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductDetailsViewModel(productDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a successful product procurement When products are loaded Then the products returned by the meli service are charged`() =
        runTest {
            // Given
            coEvery { productDetailsUseCase.getProductById(any()) } returns ProductsResolverResult.Success(
                getFakeProduct()
            )
            coEvery { productDetailsUseCase.getProductsByCategory(any()) } returns ProductsResolverResult.Success(
                getFakeProductInquiry()
            )

            // When
            viewModel.loadProduct("productId")

            // Then
            assert(viewModel.uIStatus.value is StatusUI.Success)
        }

    @Test
    fun `Given an error in product procurement When products are loaded Then an error status is returned`() =
        runTest {
            // Given
            coEvery { productDetailsUseCase.getProductById(any()) } returns ProductsResolverResult.Error(ANY_ERROR)

            // When
            viewModel.loadProduct("productId")

            // Then
            assert(viewModel.uIStatus.value is StatusUI.Error)
        }

    @Test
    fun `Given a successful product procurement When category products loading fails Then an error status is returned`() =
        runTest {
            // Given
            coEvery { productDetailsUseCase.getProductById(any()) } returns ProductsResolverResult.Success(
                getFakeProduct()
            )
            coEvery { productDetailsUseCase.getProductsByCategory(any()) } returns ProductsResolverResult.Error(
                ANY_ERROR
            )

            // When
            viewModel.loadProduct("productId")

            // Then
            assert(viewModel.uIStatus.value is StatusUI.Error)
        }

    @Test
    fun `Given a successful product procurement When category products loading is in progress Then loading status is returned`() =
        runTest {
            Dispatchers.setMain(StandardTestDispatcher())

            // Given
            coEvery { productDetailsUseCase.getProductById(any()) } returns ProductsResolverResult.Success(
                getFakeProduct()
            )
            coEvery { productDetailsUseCase.getProductsByCategory(any()) } returns ProductsResolverResult.Success(
                getFakeProductInquiry()
            )

            // When
            viewModel.loadProduct("productId")

            // Then
            assert(viewModel.uIStatus.value is StatusUI.Loading)
        }

    companion object {
        const val ANY_ERROR = 0
    }
}
