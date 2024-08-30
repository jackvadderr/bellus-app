import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei

@Composable
fun CustomButton(
    onClick: () -> Unit,
    texto: String
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MarronNaoSei,
            contentColor = MarronNaoSei
        )
    ) {
        Text(texto, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomButton() {
    CustomButton(
        onClick = { /* Ação de exemplo */ },
        texto = "Clique Aqui"
    )
}