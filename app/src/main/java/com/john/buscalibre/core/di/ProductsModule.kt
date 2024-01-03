package com.john.buscalibre.core.di

import com.john.buscalibre.home.data.network.ApiHome
import com.john.buscalibre.home.data.network.daos.HomeDaoRetroFit
import com.john.buscalibre.list_product.data.network.ApiListProduct
import com.john.buscalibre.list_product.data.network.daos.ProductDaoRetroFit
import com.john.buscalibre.products_details.data.network.ApiProductDetails
import com.john.buscalibre.products_details.data.network.daos.ProductDetailsDaoRetroFit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ProductsModule {

    @Provides
    fun provideHomeDaoRetroFit(): HomeDaoRetroFit = ApiHome.create()

    @Provides
    fun provideProductDaoRetroFit(): ProductDaoRetroFit = ApiListProduct.create()

    @Provides
    fun provideProductDetailsDaoRetroFit(): ProductDetailsDaoRetroFit = ApiProductDetails.create()
}
