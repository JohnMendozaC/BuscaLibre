package com.john.buscalibre.products_details.domain.models

import com.john.buscalibre.shared.domain.models.FakeProduct.getFakeProduct
import com.john.buscalibre.shared.domain.models.FakeProduct.getFakeProducts

object FakeDataBody {
    fun getFakeDataBody() = DataBody(getFakeProduct(), getFakeProducts())
}
