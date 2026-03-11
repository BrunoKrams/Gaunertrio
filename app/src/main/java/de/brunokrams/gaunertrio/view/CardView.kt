package de.brunokrams.gaunertrio.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brunokrams.gaunertrio.model.Card

@Composable
fun CardView(
    card: Card,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(7 / 5f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CardImage(card.gauner1.image, modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            CardImage(card.gauner2.image, modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            CardImage(card.gauner3.image, modifier.weight(1f))
        }
    }
}

@Composable
fun CardImage(imageId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier.fillMaxHeight()
    )
}

@Preview
@Composable
fun CardPreview() {
    GaunertrioTheme {
        CardView(Card.ALL[34], modifier = Modifier.width(300.dp))
    }
}
