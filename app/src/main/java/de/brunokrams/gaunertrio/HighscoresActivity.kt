package de.brunokrams.gaunertrio

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import de.brunokrams.gaunertrio.model.Score
import de.brunokrams.gaunertrio.view.CustomButton
import de.brunokrams.gaunertrio.view.GaunertrioTheme
import de.brunokrams.gaunertrio.viewmodel.HighscoreViewModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class HighscoresActivity : BaseActivity() {

    @Inject
    lateinit var formatter: DateTimeFormatter

    private val highscoreViewModel: HighscoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GaunertrioTheme {
                val scores by highscoreViewModel.highscores.collectAsState()
                HighscoresScreen(
                    scores = scores,
                    formatter = formatter,
                    goBack = ::goBack
                )
            }
        }
    }

    fun goBack() {
        startActivity(Intent(this, StartActivity::class.java))
    }
}

@Composable
fun HighscoresScreen(scores: List<Score>, formatter: DateTimeFormatter, goBack: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Highscores",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            scores.forEach { score ->
                ScoreRow(score, formatter)
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(
                "Zurück",
                onClick = { goBack() },
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ScoreRow(score: Score, formatter: DateTimeFormatter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatter.format(score.timestamp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = score.score.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_4,orientation=portrait")
@Composable
fun HighscoresScreenPreview_PIXEL_4_PORTRAIT() {
    GaunertrioTheme {
        HighscoresScreen(previewScores, formatter = DateTimeFormatter.ISO_DATE_TIME, {})
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_4,orientation=landscape")
@Composable
fun HighscoresScreenPreview_PIXEL_4_LANDSCAPE() {
    GaunertrioTheme {
        HighscoresScreen(previewScores, formatter = DateTimeFormatter.ISO_DATE_TIME, {})
    }
}


@Preview(showBackground = true, device = "spec:parent=Nexus 10,orientation=portrait")
@Composable
fun HighscoresScreenPreview_NEXUS_10_PORTRAIT() {
    GaunertrioTheme {
        HighscoresScreen(previewScores, formatter = DateTimeFormatter.ISO_DATE_TIME, {})
    }
}

@Preview(showBackground = true, device = "spec:parent=Nexus 10,orientation=landscape")
@Composable
fun HighscoresScreenPreview_NEXUS_10_LANDSCAPE() {
    GaunertrioTheme {
        HighscoresScreen(previewScores, formatter = DateTimeFormatter.ISO_DATE_TIME, {})
    }
}

val previewScores = listOf(
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
    Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999)
)


@Preview(showBackground = true)
@Composable
fun ScoreRowPreview() {
    GaunertrioTheme {
        ScoreRow(
            Score(LocalDateTime.of(2025, 12, 12, 12, 12), 999999),
            formatter = DateTimeFormatter.ISO_DATE_TIME
        )
    }
}

