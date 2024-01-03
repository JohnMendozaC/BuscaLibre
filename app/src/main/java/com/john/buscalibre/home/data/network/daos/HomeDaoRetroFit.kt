package com.john.buscalibre.home.data.network.daos

import com.john.buscalibre.shared.data.network.vos.QueryVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface HomeDaoRetroFit {
    @GET("sites/MLA/search")
    suspend fun searchProduct(@Query("q") query: String): Response<QueryVo>
}
