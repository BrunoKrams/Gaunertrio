package de.brunokrams.gaunertrio.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.brunokrams.gaunertrio.R


val superBoys = FontFamily(
    Font(R.font.super_boys, FontWeight.Normal),
    Font(R.font.super_boys, FontWeight.Bold)
)

val typo = Typography(
    bodySmall = TextStyle(
        fontFamily = superBoys,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 36.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = superBoys,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 72.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = superBoys,
        fontWeight = FontWeight.Normal,
        fontSize = 72.sp,
        lineHeight = 108.sp
    )
)

@Composable
fun GaunertrioTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = typo,
        content = content
    )
}