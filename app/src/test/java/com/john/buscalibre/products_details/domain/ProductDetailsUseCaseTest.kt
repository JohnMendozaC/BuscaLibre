package com.john.buscalibre.products_details.domain

import com.john.buscalibre.products_details.data.network.daos.ProductDetailsDaoRetroFit
import com.john.buscalibre.shared.data.network.vos.FakeProductVo.getFakeProductVo
import com.john.buscalibre.shared.data.network.vos.FakesQueryVo.getQueryVoWithProducts
import com.john.buscalibre.shared.data.network.vos.ProductVo
import com.john.buscalibre.shared.data.network.vos.QueryVo
import com.john.buscalibre.shared.domain.models.FakeProduct.getFakeProduct
import com.john.buscalibre.shared.domain.models.FakeProductInquiry.getFakeProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.domain.reponse.Status
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

class ProductDetailsUseCaseTest {

    private val mockProductDetailsDaoRetroFit: ProductDetailsDaoRetroFit = mockk()
    private val productDetailsUseCase = ProductDetailsUseCase(mockProductDetailsDaoRetroFit)

    @Test
    fun `Given a successful product retrieval by ID When getProductById is called Then a success result is returned`() =
        runTest {
            // Given
            val response = mockk<Response<ProductVo>>()
            every { response.isSuccessful } returns true
            every { response.code() } returns HttpURLConnection.HTTP_OK
            every { response.body() } returns getFakeProductVo()
            val fakeProductId = "123"
            coEvery { mockProductDetailsDaoRetroFit.getProductById(fakeProductId) } returns response

            // When
            val result = productDetailsUseCase.getProductById(fakeProductId)

            // Then
            assert(result is ProductsResolverResult.Success)
            val successResult = result as ProductsResolverResult.Success
            successResult.result shouldBe getFakeProduct()
        }

    @Test
    fun `Given an error in product retrieval by ID When getProductById is called Then an error result is returned`() =
        runTest {
            // Given
            val response = mockk<Response<ProductVo>>()
            every { response.body() } returns null
            val fakeProductId = "123"
            coEvery { mockProductDetailsDaoRetroFit.getProductById(fakeProductId) } returns response

            // When
            val result = productDetailsUseCase.getProductById(fakeProductId)

            // Then
            assert(result is ProductsResolverResult.Error)
            val errorResult = result as ProductsResolverResult.Error
            errorResult.errorCode shouldBe Status.EMPTY_PRODUCT.code
        }

    @Test
    fun `Given a successful product retrieval by category When getProductsByCategory is called Then a success result is returned`() =
        runTest {
            // Given
            val response = mockk<Response<QueryVo>>()
            every { response.isSuccessful } returns true
            every { response.code() } returns HttpURLConnection.HTTP_OK
            every { response.body() } returns getQueryVoWithProducts()
            val fakeCategory = "Electronics"
            coEvery { mockProductDetailsDaoRetroFit.getProductsByCategory(fakeCategory) } returns response

            // When
            val result = productDetailsUseCase.getProductsByCategory(fakeCategory)

            // Then
            assert(result is ProductsResolverResult.Success)
            val successResult = result as ProductsResolverResult.Success
            successResult.result shouldBe getFakeProductInquiry()
        }

    @Test
    fun `Given an error in product retrieval by category When getProductsByCategory is called Then an error result is returned`() =
        runTest {
            // Given
            val response = mockk<Response<QueryVo>>()
            every { response.body() } returns null
            val fakeCategory = "Electronics"
            coEvery { mockProductDetailsDaoRetroFit.getProductsByCategory(fakeCategory) } returns response

            // When
            val result = productDetailsUseCase.getProductsByCategory(fakeCategory)

            // Then
            assert(result is ProductsResolverResult.Error)
            val errorResult = result as ProductsResolverResult.Error
            errorResult.errorCode shouldBe Status.NO_INTERNET.code
        }

}
