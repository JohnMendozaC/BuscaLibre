package com.john.buscalibre.shared.data.network

import com.john.buscalibre.shared.data.network.vos.ProductVo
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.models.ProductInquiry

fun List<ProductVo>.toProductInquiry(): ProductInquiry {

    val products = this.map {
        Product(
            id = it.id,
            title = it.title,
            price = it.price,
            categoryId = it.categoryId,
            thumbnail = it.thumbnail?.replace("http://", "https://")
        )
    }

    return ProductInquiry(products)
}

fun ProductVo.toProduct(): Product = Product(
    id = id,
    title = title,
    price = price,
    permalink = permalink,
    categoryId = categoryId,
    picture = picture?.first()?.secureUrl
)
