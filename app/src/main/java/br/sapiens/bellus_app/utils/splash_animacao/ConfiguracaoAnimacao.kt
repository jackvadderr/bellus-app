package br.sapiens.bellus_app.utils.splash_animacao

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.R

data class ConfiguracaoAnimacao(
    val texto: String = "bellus",
    val duracao: Int = 1500,
    val atraso: Int = 250,
    val corInicial: Color = Color(parseColor("#2b2e35")),
    val corFinal: Color = Color(parseColor("#896c3c")),
    val fonteArimo: FontFamily = FontFamily(Font(R.font.arimo_regular)),
    val tamanhoFonte: TextUnit = 78.sp
)
