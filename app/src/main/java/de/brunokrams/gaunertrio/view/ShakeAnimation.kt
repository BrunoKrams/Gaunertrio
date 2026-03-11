package de.brunokrams.gaunertrio.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

val shakes: Int = 4
val angle: Float = 5f
val durationMillis = 30

@Composable
fun ShakeAnimation(
    modifier: Modifier = Modifier,
    triggerKey: Int = 0,
    content: @Composable (Modifier) -> Unit
) {

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(triggerKey) {
        if (triggerKey > 0) {
            repeat(shakes) {
                rotation.animateTo(
                    targetValue = -angle,
                    animationSpec = tween(durationMillis)
                )
                rotation.animateTo(
                    targetValue = angle,
                    animationSpec = tween(durationMillis)
                )
            }
            rotation.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis)
            )
        }
    }
    content(modifier.rotate(rotation.value))
}
