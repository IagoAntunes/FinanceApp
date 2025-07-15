package com.iagoaf.appfinancas.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable // Import this
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.core.ui.theme.appTypography
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
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null, // Add this parameter
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) magenta else gray300,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = onClick != null) { onClick?.invoke() } // Apply clickable here
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            readOnly = readOnly,
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

@Composable
fun CTextFieldContainer(
    modifier: Modifier = Modifier,
    value: String = "",
    placeHolder: String = "",
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .border(
                width = 1.dp,
                color = gray300,
                shape = RoundedCornerShape(8.dp)
            )
            .background(gray200)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            if (prefix != null) {
                prefix()
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = if (value.isNotBlank()) value else placeHolder,
                color = if (value.isNotBlank()) gray700 else gray300,
                style = appTypography.input,
                modifier = Modifier.weight(1f)
            )

            if (suffix != null) {
                Spacer(modifier = Modifier.width(8.dp))
                suffix()
            }
        }
    }
}


@Preview
@Composable
private fun CTextFieldPreview() {
    CTextField()
}