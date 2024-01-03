package com.john.buscalibre.shared.domain.models

object FakeProduct {

    const val anyValue = "anyValue"
    fun getFakeProduct() =
        Product(
            id = anyValue,
            title = anyValue,
            price = 0.0,
            permalink = anyValue,
            categoryId = anyValue,
            picture = anyValue
        )

    fun getFakeProducts() =
        listOf(
            Product(
                id = anyValue,
                title = anyValue,
                price = 0.0,
                permalink = anyValue,
                categoryId = anyValue,
                picture = anyValue
            )
        )
}

