package de.brunokrams.gaunertrio.model

import androidx.compose.ui.graphics.Color
import de.brunokrams.gaunertrio.R


private val Color.Companion.Brown: Color
    get() {
        return Color(0xFFA52A2A)
    }

enum class Gauner(val color: Color, val image: Int) {
    BLUE(Color.Blue, R.drawable.blue),
    BROWN(Color.Brown, R.drawable.brown),
    YELLOW(Color.Yellow, R.drawable.yellow),
    GREEN(Color.Green, R.drawable.green),
    PURPLE(Color.Magenta, R.drawable.purple),
    RED(Color.Red, R.drawable.red),
    BLACK(Color.Black, R.drawable.black)
}
