package com.john.buscalibre.products_details.data.network

import com.john.buscalibre.BuildConfig.GRADLE_BASE_URL_MELI
import com.john.buscalibre.products_details.data.network.daos.ProductDetailsDaoRetroFit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProductDetails {

    fun create(): ProductDetailsDaoRetroFit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(GRADLE_BASE_URL_MELI)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductDetailsDaoRetroFit::class.java)
    }
}
