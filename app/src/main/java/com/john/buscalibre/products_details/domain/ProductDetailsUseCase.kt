package com.john.buscalibre.products_details.domain

import com.john.buscalibre.products_details.data.network.daos.ProductDetailsDaoRetroFit
import com.john.buscalibre.shared.data.network.NetworkResponse
import com.john.buscalibre.shared.data.network.NetworkResponse.Companion.validateResponse
import com.john.buscalibre.shared.data.network.toProduct
import com.john.buscalibre.shared.data.network.toProductInquiry
import com.john.buscalibre.shared.data.network.vos.QueryVo
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.models.ProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.domain.reponse.Status
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val productDetailsDaoRetroFit: ProductDetailsDaoRetroFit
) {
    suspend fun getProductById(productId: String?): ProductsResolverResult<Product> {
        return when (val result = validateResponse { productDetailsDaoRetroFit.getProductById(productId) }) {
            is NetworkResponse.Success -> {
                ProductsResolverResult.Success(result.response.toProduct())
            }

            is NetworkResponse.Error -> {
                ProductsResolverResult.Error(Status.EMPTY_PRODUCT.code)
            }
        }
    }

    suspend fun getProductsByCategory(category: String?): ProductsResolverResult<ProductInquiry> {
        return when (val result = validateResponse { productDetailsDaoRetroFit.getProductsByCategory(category) }) {
            is NetworkResponse.Success -> {
                validateResultOfQueryByCategory(result.response)
            }

            is NetworkResponse.Error -> {
                ProductsResolverResult.Error(Status.NO_INTERNET.code)
            }
        }
    }

    private fun validateResultOfQueryByCategory(result: QueryVo): ProductsResolverResult<ProductInquiry> {
        if (result.results.isEmpty())
            return ProductsResolverResult.Error(Status.EMPTY_LIST.code)
        return ProductsResolverResult.Success(result.results.toProductInquiry())
    }
}
