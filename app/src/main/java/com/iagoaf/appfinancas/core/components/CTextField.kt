package com.iagoaf.appfinancas.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.core.ui.theme.gray200
import com.iagoaf.appfinancas.core.ui.theme.gray300
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.core.ui.theme.red


@Composable
fun CTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    value: String = "",
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) magenta else gray300,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = true,
            maxLines = 1,
            enabled = enabled,
            prefix = prefix,
            suffix = suffix,
            placeholder = placeHolder,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = gray200,
                focusedTextColor = gray700,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedPrefixColor = magenta,
                errorPrefixColor = red,
            ),
        )
    }
}

@Preview
@Composable
private fun CTextFieldPreview() {
    CTextField()
}