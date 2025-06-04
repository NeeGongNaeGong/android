package com.ssafy.neegongnaegong.presentation.component.edittext

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textColor: Color = NeeGongNaeGongTheme.colorScheme.primaryText,
    textStyle: TextStyle = NeeGongNaeGongTheme.typography.bodySmall,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
    cursorBrush: Brush = SolidColor(NeeGongNaeGongTheme.colorScheme.primaryText),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        unfocusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.primaryText,
    ),
    textSelectionColors: TextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )
) {
    val actualTextStyle: TextStyle = textStyle.copy(color = textColor)
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = actualTextStyle,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            cursorBrush = cursorBrush,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    contentPadding = PaddingValues(),
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors
                )
            }
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PlainEditTextPreview() {
    PlainEditText(
        value = "text",
        onValueChange = {}
    )
}
