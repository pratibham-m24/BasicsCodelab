/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.basicscodelab.reply.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.example.reply.ui.theme.replyDarkBackground
import com.example.reply.ui.theme.replyDarkError
import com.example.reply.ui.theme.replyDarkErrorContainer
import com.example.reply.ui.theme.replyDarkInverseOnSurface
import com.example.reply.ui.theme.replyDarkInverseSurface
import com.example.reply.ui.theme.replyDarkOnBackground
import com.example.reply.ui.theme.replyDarkOnError
import com.example.reply.ui.theme.replyDarkOnErrorContainer
import com.example.reply.ui.theme.replyDarkOnPrimary
import com.example.reply.ui.theme.replyDarkOnPrimaryContainer
import com.example.reply.ui.theme.replyDarkOnSecondary
import com.example.reply.ui.theme.replyDarkOnSecondaryContainer
import com.example.reply.ui.theme.replyDarkOnSurface
import com.example.reply.ui.theme.replyDarkOnSurfaceVariant
import com.example.reply.ui.theme.replyDarkOnTertiary
import com.example.reply.ui.theme.replyDarkOnTertiaryContainer
import com.example.reply.ui.theme.replyDarkOutline
import com.example.reply.ui.theme.replyDarkPrimary
import com.example.reply.ui.theme.replyDarkPrimaryContainer
import com.example.reply.ui.theme.replyDarkPrimaryInverse
import com.example.reply.ui.theme.replyDarkSecondary
import com.example.reply.ui.theme.replyDarkSecondaryContainer
import com.example.reply.ui.theme.replyDarkSurface
import com.example.reply.ui.theme.replyDarkSurfaceVariant
import com.example.reply.ui.theme.replyDarkTertiary
import com.example.reply.ui.theme.replyDarkTertiaryContainer
import com.example.reply.ui.theme.replyLightBackground
import com.example.reply.ui.theme.replyLightError
import com.example.reply.ui.theme.replyLightErrorContainer
import com.example.reply.ui.theme.replyLightInverseOnSurface
import com.example.reply.ui.theme.replyLightInverseSurface
import com.example.reply.ui.theme.replyLightOnBackground
import com.example.reply.ui.theme.replyLightOnError
import com.example.reply.ui.theme.replyLightOnErrorContainer
import com.example.reply.ui.theme.replyLightOnPrimary
import com.example.reply.ui.theme.replyLightOnPrimaryContainer
import com.example.reply.ui.theme.replyLightOnSecondary
import com.example.reply.ui.theme.replyLightOnSecondaryContainer
import com.example.reply.ui.theme.replyLightOnSurface
import com.example.reply.ui.theme.replyLightOnSurfaceVariant
import com.example.reply.ui.theme.replyLightOnTertiary
import com.example.reply.ui.theme.replyLightOnTertiaryContainer
import com.example.reply.ui.theme.replyLightOutline
import com.example.reply.ui.theme.replyLightPrimary
import com.example.reply.ui.theme.replyLightPrimaryContainer
import com.example.reply.ui.theme.replyLightPrimaryInverse
import com.example.reply.ui.theme.replyLightSecondary
import com.example.reply.ui.theme.replyLightSecondaryContainer
import com.example.reply.ui.theme.replyLightSurface
import com.example.reply.ui.theme.replyLightSurfaceVariant
import com.example.reply.ui.theme.replyLightTertiary
import com.example.reply.ui.theme.replyLightTertiaryContainer

// Material 3 color schemes
private val replyDarkColorScheme = darkColorScheme(
    primary = replyDarkPrimary,
    onPrimary = replyDarkOnPrimary,
    primaryContainer = replyDarkPrimaryContainer,
    onPrimaryContainer = replyDarkOnPrimaryContainer,
    inversePrimary = replyDarkPrimaryInverse,
    secondary = replyDarkSecondary,
    onSecondary = replyDarkOnSecondary,
    secondaryContainer = replyDarkSecondaryContainer,
    onSecondaryContainer = replyDarkOnSecondaryContainer,
    tertiary = replyDarkTertiary,
    onTertiary = replyDarkOnTertiary,
    tertiaryContainer = replyDarkTertiaryContainer,
    onTertiaryContainer = replyDarkOnTertiaryContainer,
    error = replyDarkError,
    onError = replyDarkOnError,
    errorContainer = replyDarkErrorContainer,
    onErrorContainer = replyDarkOnErrorContainer,
    background = replyDarkBackground,
    onBackground = replyDarkOnBackground,
    surface = replyDarkSurface,
    onSurface = replyDarkOnSurface,
    inverseSurface = replyDarkInverseSurface,
    inverseOnSurface = replyDarkInverseOnSurface,
    surfaceVariant = replyDarkSurfaceVariant,
    onSurfaceVariant = replyDarkOnSurfaceVariant,
    outline = replyDarkOutline
)

private val replyLightColorScheme = lightColorScheme(
    primary = replyLightPrimary,
    onPrimary = replyLightOnPrimary,
    primaryContainer = replyLightPrimaryContainer,
    onPrimaryContainer = replyLightOnPrimaryContainer,
    inversePrimary = replyLightPrimaryInverse,
    secondary = replyLightSecondary,
    onSecondary = replyLightOnSecondary,
    secondaryContainer = replyLightSecondaryContainer,
    onSecondaryContainer = replyLightOnSecondaryContainer,
    tertiary = replyLightTertiary,
    onTertiary = replyLightOnTertiary,
    tertiaryContainer = replyLightTertiaryContainer,
    onTertiaryContainer = replyLightOnTertiaryContainer,
    error = replyLightError,
    onError = replyLightOnError,
    errorContainer = replyLightErrorContainer,
    onErrorContainer = replyLightOnErrorContainer,
    background = replyLightBackground,
    onBackground = replyLightOnBackground,
    surface = replyLightSurface,
    onSurface = replyLightOnSurface,
    inverseSurface = replyLightInverseSurface,
    inverseOnSurface = replyLightInverseOnSurface,
    surfaceVariant = replyLightSurfaceVariant,
    onSurfaceVariant = replyLightOnSurfaceVariant,
    outline = replyLightOutline
)

@Composable
fun ReplyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val replyColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> replyDarkColorScheme
        else -> replyLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = replyColorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = replyColorScheme,
        typography = replyTypography,
        content = content
    )
}