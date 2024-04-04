package br.sapiens.bellus_app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    texto: String
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp))
            .fillMaxWidth(1f)
            .height(40.dp)
        ,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF896D3C)),
        shape = RectangleShape
    ) {
        Text(
            text = texto,
            color = Color.White
        )
    }
}