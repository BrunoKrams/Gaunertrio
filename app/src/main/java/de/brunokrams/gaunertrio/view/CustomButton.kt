package de.brunokrams.gaunertrio.view

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            contentColor = if (enabled) Color.White else Color.Gray
        ),
        elevation = null,
        enabled = enabled
    ) {
        Text(text = text, style = style)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonEnabledPreview() {
    GaunertrioTheme {
        CustomButton(text = "Test", style = MaterialTheme.typography.bodyMedium, onClick = {}, enabled = true)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonDisabledPreview() {
    GaunertrioTheme {
        CustomButton(text = "Test", style = MaterialTheme.typography.bodyMedium, onClick = {}, enabled = false)
    }
}