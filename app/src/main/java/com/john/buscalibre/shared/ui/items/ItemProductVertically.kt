package com.john.buscalibre.shared.ui.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.john.buscalibre.R
import com.john.buscalibre.shared.domain.floatToCurrencyFormat
import com.john.buscalibre.shared.domain.models.Product

@Composable
fun ItemProductVertically(product: Product, loadProduct: (String) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.Black), modifier = Modifier
            .fillMaxWidth()
            .clickable {
                loadProduct.invoke(product.id)
            }
    ) {
        Row {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = "Image in vertically scroll",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = product.title ?: stringResource(R.string.unknow),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp),
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Text(
                    text = product.price?.floatToCurrencyFormat() ?: stringResource(R.string.price_cero),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}
