package com.john.buscalibre.list_product.domain

import com.john.buscalibre.list_product.data.network.daos.ProductDaoRetroFit
import com.john.buscalibre.shared.data.network.vos.FakesQueryVo.getQueryVoWithProducts
import com.john.buscalibre.shared.data.network.vos.QueryVo
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

class ProductUseCaseTest {

    private val mockProductDaoRetroFit: ProductDaoRetroFit = mockk()
    private val productUseCase = ProductUseCase(mockProductDaoRetroFit)

    @Test
    fun `Given a successful product search When searchProduct is called Then a success result is returned`() = runTest {
        // Given
        val response = mockk<Response<QueryVo>>()
        every { response.isSuccessful } returns true
        every { response.code() } returns HttpURLConnection.HTTP_OK
        every { response.body() } returns getQueryVoWithProducts()

        val fakeQuery = "test"
        coEvery { mockProductDaoRetroFit.searchProduct(fakeQuery) } returns response

        // When
        val result = productUseCase.searchProduct(fakeQuery)

        // Then
        assert(result is ProductsResolverResult.Success)
        val successResult = result as ProductsResolverResult.Success
        successResult.result shouldBe getFakeProductInquiry()
    }

    @Test
    fun `Given an error in product search When searchProduct is called Then an error result is returned`() = runTest {
        // Given
        val response = mockk<Response<QueryVo>>()
        every { response.body() } returns null
        val fakeQuery = "test"
        coEvery { mockProductDaoRetroFit.searchProduct(fakeQuery) } returns response

        // When
        val result = productUseCase.searchProduct(fakeQuery)

        // Then
        assert(result is ProductsResolverResult.Error)
        val errorResult = result as ProductsResolverResult.Error
        errorResult.errorCode shouldBe Status.NO_INTERNET.code
    }
}
