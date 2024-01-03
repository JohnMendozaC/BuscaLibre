package com.john.buscalibre.shared.domain.models

import com.john.buscalibre.shared.data.network.vos.FakesQueryVo

object FakeProductInquiry {
    fun getFakeProductInquiry() =
        ProductInquiry(
            listOf(
                Product(
                    id = FakesQueryVo.anyValue,
                    title = FakesQueryVo.anyValue,
                    price = 0.0,
                    categoryId = FakesQueryVo.anyValue,
                    thumbnail = FakesQueryVo.anyValue,
                )
            )
        )
}
