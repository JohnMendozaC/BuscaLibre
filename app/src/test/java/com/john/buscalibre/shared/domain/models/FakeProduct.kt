package com.john.buscalibre.shared.domain.models

import com.john.buscalibre.shared.data.network.vos.FakesQueryVo


object FakeProduct {
    fun getFakeProduct() =
        Product(
            id = FakesQueryVo.anyValue,
            title = FakesQueryVo.anyValue,
            price = 0.0,
            permalink = FakesQueryVo.anyValue,
            categoryId = FakesQueryVo.anyValue,
            picture = FakesQueryVo.anyValue
        )
}
