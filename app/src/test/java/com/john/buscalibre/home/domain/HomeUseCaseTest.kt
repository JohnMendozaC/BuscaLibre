package com.john.buscalibre.home.domain

import com.john.buscalibre.home.data.network.daos.HomeDaoRetroFit
import com.john.buscalibre.shared.data.network.vos.FakesQueryVo.getQueryVoWithProducts
import com.john.buscalibre.shared.data.network.vos.QueryVo
import com.john.buscalibre.shared.domain.models.FakeProductInquiry.getFakeProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.domain.reponse.Status
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class HomeUseCaseTest {


    private val mockHomeDaoRetroFit: HomeDaoRetroFit = mockk()
    private val homeUseCase = HomeUseCase(mockHomeDaoRetroFit)

    @Test
    fun `Given a successful product search When searchProduct is called Then a success result is returned`() = runTest {

        // Given
        val response = mockk<Response<QueryVo>>()
        every { response.isSuccessful } returns true
        every { response.code() } returns HttpURLConnection.HTTP_OK
        every { response.body() } returns getQueryVoWithProducts()

        val fakeQuery = "test"
        coEvery { mockHomeDaoRetroFit.searchProduct(fakeQuery) } returns response

        // When
        val result = homeUseCase.searchProduct(fakeQuery)

        // Then
        assert(result is ProductsResolverResult.Success)
        val successResult = result as ProductsResolverResult.Success
        successResult.result shouldBe getFakeProductInquiry()
    }

    @Test
    fun `Given an error in product search When searchProduct is called Then an error result is returned`() =
        runTest {

            // Given
            val response = mockk<Response<QueryVo>>()
            every { response.body() } returns null

            val fakeQuery = "test"
            coEvery { mockHomeDaoRetroFit.searchProduct(fakeQuery) } returns response

            // When
            val result = homeUseCase.searchProduct(fakeQuery)

            // Then
            assert(result is ProductsResolverResult.Error)
            val errorResult = result as ProductsResolverResult.Error
            errorResult.errorCode shouldBe Status.NO_INTERNET.code
        }

}
