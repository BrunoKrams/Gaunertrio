package de.brunokrams.gaunertrio

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import de.brunokrams.gaunertrio.model.Score
import de.brunokrams.gaunertrio.view.FireworkAnimation
import de.brunokrams.gaunertrio.view.GaunertrioTheme
import de.brunokrams.gaunertrio.viewmodel.HighscoreViewModel
import org.threeten.bp.LocalDateTime

@AndroidEntryPoint
class WinnerActivity : BaseActivity() {

    private val highscoreViewModel: HighscoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val score = intent.getIntExtra("score", Int.MIN_VALUE)
        highscoreViewModel.addScore(Score(LocalDateTime.now(), score))
        setContent {
            GaunertrioTheme {
                WinnerScreen(
                    score = score,
                    onTap = { startStartActivity() })
            }
        }
    }

    fun startStartActivity() {
        startActivity(android.content.Intent(this, StartActivity::class.java))
    }
}

@Composable
fun WinnerScreen(score: Int, onTap: () -> Unit) {
    FireworkAnimation()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onTap() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Deine Punktzahl",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_4_xl,orientation=portrait")
@Composable
fun WinnerScreenPreview_PIXEL_4_PORTRAIT() {
    GaunertrioTheme {
        WinnerScreen(score = 999999, onTap = {})
    }
}

@Preview(showBackground = true, device = "spec:parent=Nexus 10,orientation=portrait")
@Composable
fun WinnerScreenPreview_NEXUS_10_PORTRAIT() {
    GaunertrioTheme {
        WinnerScreen(score = 999999, {})
    }
}



