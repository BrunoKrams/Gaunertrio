package de.brunokrams.gaunertrio

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import de.brunokrams.gaunertrio.model.Card
import de.brunokrams.gaunertrio.model.Gauner
import de.brunokrams.gaunertrio.view.CardView
import de.brunokrams.gaunertrio.view.CustomButton
import de.brunokrams.gaunertrio.view.GaunertrioTheme
import de.brunokrams.gaunertrio.view.GuessCardDialog
import de.brunokrams.gaunertrio.view.One
import de.brunokrams.gaunertrio.view.Two
import de.brunokrams.gaunertrio.view.Zero
import de.brunokrams.gaunertrio.viewmodel.CardWithHitsViewModel
import de.brunokrams.gaunertrio.viewmodel.GameViewModel

@AndroidEntryPoint
class GameActivity : BaseActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GaunertrioTheme {
                val hasCards by gameViewModel.hasCards
                val dealtCards = gameViewModel.dealtCards
                val timeElapsed by gameViewModel.timer.collectAsState()

                GameScreen(
                    timeElapsed = timeElapsed,
                    hasCards = hasCards,
                    dealtCards = dealtCards,
                    onDraw = { gameViewModel.draw() },
                    onCheck = { gauner1, gauner2, gauner3 ->
                        gameViewModel.check(
                            gauner1,
                            gauner2,
                            gauner3
                        )
                    },
                    increaseGuessCounter = { gameViewModel.increaseGuessCounter() },
                    isWinner = { startWinnerActivity() }
                )
            }
        }
    }

    fun startWinnerActivity() {
        val intent = Intent(this, WinnerActivity::class.java)
        intent.putExtra("score", gameViewModel.getScore())
        startActivity(intent)
    }
}

val SCREEN_WIDTH_TABLET_THRESHOLD = 600

@Composable
fun GameScreen(
    hasCards: Boolean,
    timeElapsed: Int,
    dealtCards: List<CardWithHitsViewModel>,
    onDraw: () -> Unit,
    onCheck: (Gauner, Gauner, Gauner) -> Boolean,
    isWinner: () -> Unit,
    increaseGuessCounter: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    var showSolveDialog by remember { mutableStateOf(false) }
    var dialogShaking by remember { mutableStateOf(0) }

    val isTablet = LocalConfiguration.current.screenWidthDp >= SCREEN_WIDTH_TABLET_THRESHOLD

    val haptic = LocalHapticFeedback.current

    if (showSolveDialog) {
        GuessCardDialog(
            shaking = dialogShaking,
            onDismissRequest = {
                showSolveDialog = false
                dialogShaking = 0
            },
            onConfirmation = { g1, g2, g3 ->
                if (onCheck(g1, g2, g3)) {
                    showSolveDialog = false
                    isWinner()
                } else {
                    increaseGuessCounter()
                    dialogShaking++
                    haptic.performHapticFeedback(HapticFeedbackType.Reject)
                }
            }
        )
    }

    LaunchedEffect(dealtCards.size) {
        if (dealtCards.isNotEmpty()) {
            lazyGridState.animateScrollToItem(dealtCards.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = timeElapsed.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier
                .padding(top= 16.dp, end = 32.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Right
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isTablet) 3 else 2),
            state = lazyGridState,
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = dealtCards,
                key = { cardWithHits -> cardWithHits.id }
            ) { cardWithHits ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Displayed Card"),
                    contentAlignment = Alignment.Center
                ) {
                    CardView(
                        cardWithHits.card,
                        modifier = Modifier.fillMaxWidth()
                    )
                    val modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 24.dp)
                    when (cardWithHits.numberOfHits) {
                        0 -> Zero(modifier)
                        1 -> One(modifier)
                        2 -> Two(modifier)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    "Karte",
                    onClick = onDraw,
                    style = MaterialTheme.typography.bodyMedium,
                    enabled = hasCards
                )
                CustomButton(
                    "Lösen",
                    onClick = { showSolveDialog = true },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_4,orientation=portrait")
@Composable
fun GameScreenPreview_PIXEL_4_PORTRAIT() {
    GaunertrioTheme {
        previewGameScreen()
    }
}


@Preview(showBackground = true, device = "spec:parent=Nexus 10,orientation=portrait")
@Composable
fun GameScreenPreview_NEXUS_10_PORTRAIT() {
    GaunertrioTheme {
        previewGameScreen()
    }
}

@Composable
fun previewGameScreen() {
    GameScreen(
        hasCards = true,
        timeElapsed = 140,
        dealtCards = listOf(
            CardWithHitsViewModel(id = 1, card = Card.ALL[0], numberOfHits = 0),
            CardWithHitsViewModel(id = 2, card = Card.ALL[1], numberOfHits = 1),
            CardWithHitsViewModel(id = 3, card = Card.ALL[2], numberOfHits = 2),
            CardWithHitsViewModel(id = 4, card = Card.ALL[3], numberOfHits = 0),
            CardWithHitsViewModel(id = 5, card = Card.ALL[4], numberOfHits = 1),
        ),
        onDraw = {},
        onCheck = { _, _, _ -> true },
        isWinner = {},
        increaseGuessCounter = {}
    )
}


