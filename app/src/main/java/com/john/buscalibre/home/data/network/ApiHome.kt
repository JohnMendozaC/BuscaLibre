package com.john.buscalibre.home.data.network

import com.john.buscalibre.BuildConfig.GRADLE_BASE_URL_MELI
import com.john.buscalibre.home.data.network.daos.HomeDaoRetroFit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHome {

    fun create(): HomeDaoRetroFit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(GRADLE_BASE_URL_MELI)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeDaoRetroFit::class.java)
    }
}
