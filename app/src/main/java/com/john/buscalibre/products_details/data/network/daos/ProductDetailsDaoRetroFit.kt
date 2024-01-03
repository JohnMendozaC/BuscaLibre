package com.john.buscalibre.products_details.data.network.daos

import com.john.buscalibre.shared.data.network.vos.ProductVo
import com.john.buscalibre.shared.data.network.vos.QueryVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDetailsDaoRetroFit {
    @GET("items/{id}")
    suspend fun getProductById(@Path("id") productId: String?): Response<ProductVo>

    @GET("sites/MLA/search")
    suspend fun getProductsByCategory(@Query("category") category: String?): Response<QueryVo>
}
