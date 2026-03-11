package de.brunokrams.gaunertrio.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import de.brunokrams.gaunertrio.R
import de.brunokrams.gaunertrio.model.Gauner

@Composable
fun GuessCardDialog(
    shaking: Int = 0,
    onDismissRequest: () -> Unit,
    onConfirmation: (gauner1: Gauner, gauner2: Gauner, gauner3: Gauner) -> Unit,
) {
    val allGauner = Gauner.entries
    val nrOfGauner = allGauner.size
    val firstPagerState = rememberPagerState(pageCount = { nrOfGauner })
    val secondPagerState = rememberPagerState(pageCount = { nrOfGauner })
    val thirdPagerState = rememberPagerState(pageCount = { nrOfGauner })

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        ShakeAnimation(triggerKey = shaking) { animatedModifier ->
            Card(
                modifier = animatedModifier
                    .width(420.dp)
                    .height(300.dp)
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp)

            ) {
                Column(
                    modifier = Modifier
                        .background(colorResource(R.color.light_blue_600))
                        .wrapContentHeight(),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .weight(4f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val pagerModifier = Modifier
                            .padding(4.dp)
                            .weight(1f)
                            .wrapContentHeight()
                        GaunerPager(firstPagerState, pagerModifier)
                        GaunerPager(secondPagerState, pagerModifier)
                        GaunerPager(thirdPagerState, pagerModifier)
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        CustomButton(
                            onClick = onDismissRequest,
                            style = MaterialTheme.typography.bodySmall,
                            text = "Abbrechen"
                        )
                        CustomButton(
                            onClick = {
                                onConfirmation(
                                    allGauner[firstPagerState.currentPage],
                                    allGauner[secondPagerState.currentPage],
                                    allGauner[thirdPagerState.currentPage]
                                )
                            },
                            style = MaterialTheme.typography.bodySmall,
                            text = "Ok"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GaunerPager(state: PagerState, modifier: Modifier) {
    VerticalPager(
        state = state,
        key = { Gauner.entries[it] },
        modifier = modifier
    ) { index ->
        Image(
            painter = painterResource(id = Gauner.entries[index].image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SolveDialogPreview() {
    GaunertrioTheme {
        GuessCardDialog(onDismissRequest = {}, onConfirmation = { _, _, _ -> })
    }
}
