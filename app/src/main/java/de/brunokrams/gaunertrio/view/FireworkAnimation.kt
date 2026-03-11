package de.brunokrams.gaunertrio.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun FireworkAnimation() {
    val particleCount = 100
    val particles = remember { mutableStateListOf<Particle>() }

    LaunchedEffect(Unit) {
        while (true) {
            delay(80)
            val newParticles = createParticles(particleCount)
            particles.addAll(newParticles)
        }
    }

    val transition = rememberInfiniteTransition()
    transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(16, easing = LinearEasing)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            val particle = iterator.next()
            particle.update()

            if (particle.alpha <= 0f) {
                iterator.remove()
                continue
            }

            drawCircle(
                color = particle.color.copy(alpha = particle.alpha),
                radius = particle.size,
                center = Offset(
                    x = particle.x * width,
                    y = particle.y * height
                )
            )
        }
    }
}

data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val color: Color,
    var alpha: Float,
    val size: Float
) {
    fun update() {
        x += vx
        y += vy
        vy += 0.001f
        alpha -= 0.01f
    }
}

fun createParticles(count: Int): List<Particle> {
    val centerX = Random.nextFloat()
    val centerY = Random.nextFloat() * 0.5f
    val particles = mutableListOf<Particle>()

    repeat(count) {
        val angle = Random.nextFloat() * 2 * Math.PI
        val speed = Random.nextFloat() * 0.01f + 0.005f
        val vx = (cos(angle) * speed).toFloat()
        val vy = (sin(angle) * speed).toFloat()
        particles.add(
            Particle(
                x = centerX,
                y = centerY,
                vx = vx,
                vy = vy,
                color = Color(
                    red = Random.nextFloat(),
                    green = Random.nextFloat(),
                    blue = Random.nextFloat()
                ),
                alpha = 1f,
                size = Random.nextFloat() * 6 + 2
            )
        )
    }

    return particles
}

