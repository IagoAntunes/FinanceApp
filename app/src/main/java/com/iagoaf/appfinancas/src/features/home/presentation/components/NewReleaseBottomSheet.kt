package com.iagoaf.appfinancas.src.features.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iagoaf.appfinancas.R
import com.iagoaf.appfinancas.core.components.CTextField
import com.iagoaf.appfinancas.core.components.CTextFieldContainer
import com.iagoaf.appfinancas.core.ui.theme.appTypography
import com.iagoaf.appfinancas.core.ui.theme.gray100
import com.iagoaf.appfinancas.core.ui.theme.gray300
import com.iagoaf.appfinancas.core.ui.theme.gray400
import com.iagoaf.appfinancas.core.ui.theme.gray500
import com.iagoaf.appfinancas.core.ui.theme.gray600
import com.iagoaf.appfinancas.core.ui.theme.gray700
import com.iagoaf.appfinancas.core.ui.theme.green
import com.iagoaf.appfinancas.core.ui.theme.magenta
import com.iagoaf.appfinancas.core.ui.theme.red
import com.iagoaf.appfinancas.src.features.home.domain.model.ReleaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReleaseBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
    onSaveRelease: (ReleaseModel) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = gray100
    ) {
        ContentModal(
            sheetState,
            scope,
            onDismiss,
            onSaveRelease,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentModal(
    sheetState: SheetState,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
    onSaveRelease: (ReleaseModel) -> Unit,
) {
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date(millis))
    }

    var showDatePicker by remember { mutableStateOf(false) }

    val title = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val value = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val isPositive = remember { mutableStateOf<Boolean?>(null) }

    fun isFormValid(): Boolean {
        return title.value.isNotBlank() &&
                category.value.isNotBlank() &&
                value.value.isNotBlank() &&
                date.value.isNotBlank() &&
                isPositive.value != null
    }

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { millis ->
                date.value = millis?.let { convertMillisToDate(it) } ?: ""
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Column(
        modifier = Modifier
            .background(gray100)
            .padding(
                vertical = 40.dp,
                horizontal = 32.dp,
            )

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "NOVO LANÇAMENTO",
                style = appTypography.titleXs,
                color = gray700
            )
            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Close",
                colorFilter = ColorFilter.tint(gray500),
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onDismiss()
                        }
                    }
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        CTextField(
            value = title.value,
            placeHolder = {
                Text(
                    "Titulo da transação",
                    style = appTypography.input,
                    color = gray400
                )
            },
            onValueChange = { text ->
                title.value = text
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        CTextField(
            value = category.value,
            placeHolder = {
                Text(
                    "Categoria",
                    style = appTypography.input,
                    color = gray400
                )
            },
            prefix = {
                Image(
                    painter = painterResource(R.drawable.ic_tag),
                    contentDescription = "Category",
                    colorFilter = ColorFilter.tint(gray600),
                    modifier = Modifier.size(20.dp)
                )
            },
            onValueChange = { text ->
                category.value = text
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CTextField(
                value = value.value,
                prefix = {
                    Text(
                        "R\$",
                        style = appTypography.input,
                        color = gray600,
                    )
                },
                onValueChange = { text ->
                    value.value = text
                },
                modifier = Modifier.weight(1f),
            )
            CTextFieldContainer(
                value = date.value,
                placeHolder = "00/00/0000",
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = null,
                        tint = gray600,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    showDatePicker = true
                },
                modifier = Modifier
                    .weight(1F)
            )

        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    if (isPositive.value == true) {
                        isPositive.value = null
                    } else if (isPositive.value == false) {
                        isPositive.value = true
                    } else {
                        isPositive.value = true
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isPositive.value == true) green.copy(alpha = 0.2f) else gray300.copy(
                        alpha = 0.2f
                    )
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isPositive.value == true) green else gray700,
                ),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Entrada",
                        style = appTypography.buttonSm,
                        color = if (isPositive.value == true) green else gray700,
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_arrow_up),
                        colorFilter = ColorFilter.tint(
                            if (isPositive.value == true) green else gray700
                        ),
                        contentDescription = "Arrow Up",
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    if (isPositive.value == false) {
                        isPositive.value = null
                    } else if (isPositive.value == true) {
                        isPositive.value = false
                    } else {
                        isPositive.value = false
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isPositive.value == false) red.copy(alpha = 0.2f) else gray300.copy(
                        alpha = 0.2f
                    )
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isPositive.value == false) red else gray700,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Saída",
                        style = appTypography.buttonSm,
                        color = if (isPositive.value == false) red else gray700,
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_arrow_down),
                        colorFilter = ColorFilter.tint(
                            if (isPositive.value == false) red else gray700
                        ),
                        contentDescription = "Arrow Up",
                        modifier = Modifier.size(10.dp)
                    )
                }

            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = gray300
        )
        Button(
            onClick = {
                val release = ReleaseModel(
                    category = category.value,
                    date = date.value,
                    isPositive = isPositive.value ?: true,
                    title = title.value,
                    value = value.value.toString(),
                )
                onSaveRelease(release)
            },
            enabled = isFormValid(),
            colors = ButtonDefaults.buttonColors(
                containerColor = magenta
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(
                vertical = 12.dp
            )
        ) {
            Text(
                "Salvar",
                style = appTypography.buttonMd
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NewReleaseBottomSheetPreview() {
    Box(
        modifier = Modifier.background(gray100)
    ) {

        ContentModal(
            sheetState = rememberModalBottomSheetState(),
            scope = rememberCoroutineScope(),
            onDismiss = { /*TODO*/ },
            onSaveRelease = {

            }
        )
    }
}