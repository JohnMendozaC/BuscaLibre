package com.john.buscalibre.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.john.buscalibre.R

@Composable
fun ImageOfTheOffer() {
    Card(
        border = BorderStroke(2.dp, Color.Black), modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.offers),
            contentDescription = "Image of the offer",
            modifier = Modifier
                .width(500.dp)
                .height(500.dp)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )
    }
}
