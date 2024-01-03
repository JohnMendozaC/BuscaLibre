package com.john.buscalibre.list_product.data.network

import com.john.buscalibre.BuildConfig.GRADLE_BASE_URL_MELI
import com.john.buscalibre.list_product.data.network.daos.ProductDaoRetroFit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiListProduct {

    fun create(): ProductDaoRetroFit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(GRADLE_BASE_URL_MELI)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductDaoRetroFit::class.java)
    }
}
