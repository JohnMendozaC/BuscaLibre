package com.john.buscalibre.list_product.domain

import com.john.buscalibre.list_product.data.network.daos.ProductDaoRetroFit
import com.john.buscalibre.shared.data.network.NetworkResponse
import com.john.buscalibre.shared.data.network.NetworkResponse.Companion.validateResponse
import com.john.buscalibre.shared.data.network.toProductInquiry
import com.john.buscalibre.shared.data.network.vos.QueryVo
import com.john.buscalibre.shared.domain.models.ProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.domain.reponse.Status
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productDaoRetroFit: ProductDaoRetroFit
) {

    suspend fun searchProduct(query: String): ProductsResolverResult<ProductInquiry> {
        return when (val result = validateResponse { productDaoRetroFit.searchProduct(query) }) {
            is NetworkResponse.Success -> {
                validateResultOfQuery(result.response)
            }

            is NetworkResponse.Error -> {
                ProductsResolverResult.Error(Status.NO_INTERNET.code)
            }
        }
    }

    private fun validateResultOfQuery(result: QueryVo): ProductsResolverResult<ProductInquiry> {
        if (result.results.isEmpty())
            return ProductsResolverResult.Error(Status.EMPTY_LIST.code)
        return ProductsResolverResult.Success(result.results.toProductInquiry())
    }
}
