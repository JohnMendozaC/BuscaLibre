package com.john.buscalibre.shared.data.network.vos

import com.john.buscalibre.shared.data.network.vos.FakeProductVo.getFakeProductsVo

object FakesQueryVo {

    const val anyValue = "AnyValue"
    fun getQueryVoWithProducts() = QueryVo(
        results = getFakeProductsVo()
    )
}
