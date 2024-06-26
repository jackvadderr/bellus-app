package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    isPassword: Boolean = false,
    placeholder: String
) {
    var passwordVisible by remember { mutableStateOf(false)}
    Box(
        modifier = modifier
            .fillMaxHeight(0.8f)
            .height(40.dp)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(Color.White, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)

    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
//                .align(alignment = Alignment.Center)
                .wrapContentHeight(align = Alignment.CenterVertically),
            textStyle = TextStyle(
                color = Color(0xFF896D3C),
                fontSize = 16.sp,
            ),
            singleLine = true,
            visualTransformation = if(isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                innerTextField()
            }
        )
        if(isPassword && value.isNotEmpty()) {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.align(Alignment.CenterEnd)
            ){
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    }
}

@Composable
fun UsuarioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Usuário"
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder
    )
}

@Composable
fun SenhaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Senha"
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        isPassword = true)
}

@Composable
fun GeralTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier,
        placeholder = placeholder
    )
}
