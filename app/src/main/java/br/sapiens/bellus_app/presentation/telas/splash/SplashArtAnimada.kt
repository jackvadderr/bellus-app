package br.sapiens.bellus_app.presentation.telas.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import br.sapiens.bellus_app.utils.splash_animacao.ConfiguracaoAnimacao

val setup = ConfiguracaoAnimacao()
@Composable
fun SplashArtAnimada(onAnimationComplete: () -> Unit) {
    val transicao = rememberInfiniteTransition(label = "")
    val isAnimacaoRodando = remember { mutableStateOf(true) }
    val indiceTexto = animarIndiceTexto(transicao, setup.texto, setup.atraso)
    val cor = animarCor(indiceTexto, setup.atraso)
    val opacidade = animarOpacidade(transicao, setup.atraso)

    RenderizarTextoAnimado(isAnimacaoRodando, setup.texto, indiceTexto, cor, opacidade, setup.fonteArimo)
    FinalizarAnimacao(isAnimacaoRodando, indiceTexto, setup.texto, onAnimationComplete)
}

@Composable
fun animarIndiceTexto(transicao: InfiniteTransition, texto: String, atraso: Int): Int {
    return transicao.animateValue(
        initialValue = -0,
        targetValue = texto.length,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                setup.duracao,
                        delayMillis = atraso,
                        easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            ),
        label = "Animação do índice do texto"
    ).value
}

@Composable
fun animarCor(indiceTexto: Int, atraso: Int): Color {
    val corInicial = setup.corInicial
    val corFinal = setup.corFinal

    return animateColorAsState(
        targetValue = if (indiceTexto < 0) corInicial else corFinal,
        animationSpec = tween(
            setup.duracao,
                        delayMillis = atraso,
                        easing = LinearEasing),
        label = "Animação de cor"
    ).value
}

@Composable
fun animarOpacidade(transicao: InfiniteTransition, atraso: Int): Float {
    return transicao.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                setup.duracao,
                        delayMillis = atraso,
                        easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Animação de opacidade"
    ).value
}

@Composable
fun RenderizarTextoAnimado(
    isAnimacaoRodando: MutableState<Boolean>,
    texto: String,
    indiceTexto: Int,
    cor: Color,
    opacidade: Float,
    fonteArimo: FontFamily
) {
    AnimatedVisibility(visible = isAnimacaoRodando.value) {
        Text(
            text = texto.take(indiceTexto + 2),
            fontSize = setup.tamanhoFonte,
            fontFamily = fonteArimo,
            color = cor,
            modifier = Modifier.graphicsLayer(alpha = opacidade)
        )
    }
}


@Composable
fun FinalizarAnimacao(isAnimacaoRodando: MutableState<Boolean>,
                      indiceTexto: Int,
                      texto: String,
                      onAnimationComplete: () -> Unit) {
    LaunchedEffect(indiceTexto) {
        if (indiceTexto >= texto.length - 1) {
            isAnimacaoRodando.value = false
            onAnimationComplete()
        }
    }
}
