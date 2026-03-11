package de.brunokrams.gaunertrio

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import de.brunokrams.gaunertrio.view.CustomButton
import de.brunokrams.gaunertrio.view.GaunertrioTheme

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setOnExitAnimationListener { viewProvider ->
                val screenHeight = viewProvider.view.height.toFloat()
                ObjectAnimator.ofFloat(
                    viewProvider.view,
                    "translationY",
                    0f,
                    screenHeight
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = 1000
                    doOnEnd {
                        viewProvider.remove()
                    }
                    start()
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            GaunertrioTheme {
                StartScreen(
                    startNewGame = ::startNewGame,
                    showHighScores = ::showHighScores,
                    exitGame = ::quit
                    )
            }
        }
    }

    private fun startNewGame() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    private fun showHighScores() {
        startActivity(Intent(this, HighscoresActivity::class.java))
    }

    private fun quit() {
        finishAffinity();
        System.exit(0);
    }

}

@Composable
fun StartScreen(
    startNewGame: () -> Unit = {},
    showHighScores: () -> Unit = {},
    exitGame: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(96.dp))
        CustomButton(
            onClick = startNewGame, text = "Neues Spiel", modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(
            onClick = showHighScores, text = "High Scores", modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(
            onClick = exitGame, text = "Beenden", modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_4_xl,orientation=portrait")
@Composable
fun StartScreenPreview_PIXEL_4_PORTRAIT() {
    GaunertrioTheme {
        StartScreen()
    }
}

@Preview(showBackground = true, device = "spec:parent=Nexus 10,orientation=portrait")
@Composable
fun StartScreenPreview_NEXUS_10_PORTRAIT() {
    GaunertrioTheme {
        StartScreen()
    }
}
