package com.cronos.common.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.cronos.ui.theme.Shapes

@Composable
fun StandardTextField(
    value: String,
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    maxLength: Int = 1000,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = if (value.length <= maxLength) value else value.substring(startIndex = 0,
            endIndex = maxLength),
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(text = placeholder,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground)
        },
        isError = isError,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        singleLine = true,
        shape = CircleShape,
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onBackground,
            textColor = MaterialTheme.colors.onBackground,
            placeholderColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = MaterialTheme.colors.onBackground,
            trailingIconColor = MaterialTheme.colors.onBackground
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions)
}