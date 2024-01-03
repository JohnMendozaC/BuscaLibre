package com.john.buscalibre.shared.data.network.vos

object FakeProductVo {
    fun getFakeProductsVo() = listOf(
        ProductVo(
            id = FakesQueryVo.anyValue,
            title = FakesQueryVo.anyValue,
            price = 0.0,
            permalink = FakesQueryVo.anyValue,
            categoryId = FakesQueryVo.anyValue,
            thumbnail = FakesQueryVo.anyValue,
            picture = null
        )
    )

    fun getFakeProductVo() = ProductVo(
        id = FakesQueryVo.anyValue,
        title = FakesQueryVo.anyValue,
        price = 0.0,
        permalink = FakesQueryVo.anyValue,
        categoryId = FakesQueryVo.anyValue,
        thumbnail = FakesQueryVo.anyValue,
        picture = listOf(PictureVo(FakesQueryVo.anyValue) )
    )

}
