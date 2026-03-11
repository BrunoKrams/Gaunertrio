package de.brunokrams.gaunertrio.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.brunokrams.gaunertrio.R

@Composable
fun RoundedCornerImage(imageResource: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(percent = 30))
    )
}

@Composable
fun Zero(modifier: Modifier) {
    RoundedCornerImage(R.drawable.zero, modifier)
}

@Composable
fun One(modifier: Modifier) {
    RoundedCornerImage(R.drawable.one, modifier)
}

@Composable
fun Two(modifier: Modifier) {
    RoundedCornerImage(R.drawable.two, modifier)
}

@Preview(showBackground = true)
@Composable
fun ZeroPreview() {
    GaunertrioTheme {
        Zero(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun OnePreview() {
    GaunertrioTheme {
        One(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun TwoPreview() {
    GaunertrioTheme {
        Two(modifier = Modifier)
    }
}
