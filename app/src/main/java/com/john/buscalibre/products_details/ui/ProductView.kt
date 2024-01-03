package com.john.buscalibre.products_details.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.john.buscalibre.R
import com.john.buscalibre.shared.domain.floatToCurrencyFormat
import com.john.buscalibre.shared.domain.models.Product

@Composable
fun ProductView(product: Product) {
    val context = LocalContext.current
    Card(
        border = BorderStroke(2.dp, Color.Black), modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = product.title ?: stringResource(R.string.unknow),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                fontSize = 16.sp
            )
            AsyncImage(
                model = product.picture,
                contentDescription = "Image in product detail",
                modifier = Modifier
                    .width(500.dp)
                    .height(500.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = product.price?.floatToCurrencyFormat() ?: stringResource(R.string.price_cero),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp),
                fontSize = 25.sp
            )
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.permalink))
                context.startActivity(intent)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(
                    text = stringResource(R.string.buy_product),
                    fontSize = 16.sp
                )
            }
        }
    }
}
