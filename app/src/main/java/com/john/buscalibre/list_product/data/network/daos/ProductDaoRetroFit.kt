package com.john.buscalibre.list_product.data.network.daos

import com.john.buscalibre.shared.data.network.vos.QueryVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface ProductDaoRetroFit {
    @GET("sites/MLA/search")
    suspend fun searchProduct(@Query("q") query: String): Response<QueryVo>
}
