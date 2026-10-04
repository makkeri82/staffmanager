package com.example.staffmanager.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import staffmanager.shared.generated.resources.Karla_Bold
import staffmanager.shared.generated.resources.Karla_Regular
import staffmanager.shared.generated.resources.Karla_SemiBold
import staffmanager.shared.generated.resources.Lora_Bold
import staffmanager.shared.generated.resources.Lora_Regular
import staffmanager.shared.generated.resources.Lora_SemiBold
import staffmanager.shared.generated.resources.Res

val lora @Composable get() = FontFamily(
    Font(
        resource = Res.font.Lora_Regular,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.Lora_SemiBold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.Lora_Bold,
        weight = FontWeight.Bold
    )
)

val karla @Composable get() = FontFamily(
    Font(
        resource = Res.font.Karla_Regular,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.Karla_SemiBold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.Karla_Bold,
        weight = FontWeight.Bold
    )
)

// val Typography: Typography @Composable get() = Typography()

@Composable
fun sespTypography(): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
            lineHeight = 60.sp,
            letterSpacing = 0.04.em
        ),
        displayMedium = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            lineHeight = 50.sp,
            letterSpacing = 0.04.em
        ),
        displaySmall = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.SemiBold,
            fontSize = 34.sp,
            lineHeight = 42.sp,
            letterSpacing = 0.06.em
        ),

        headlineLarge = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.08.em
        ),
        headlineMedium = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            lineHeight = 31.sp,
            letterSpacing = 0.08.em
        ),   // site H2
        headlineSmall = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.05.em
        ),       // site H1 / site title

        titleLarge = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.03.em
        ),
        titleMedium = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.05.em
        ),
        titleSmall = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.05.em
        ),

        bodyLarge = TextStyle(
            fontFamily = lora,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 27.sp
        ),   // site body
        bodyMedium = TextStyle(
            fontFamily = lora,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodySmall = TextStyle(
            fontFamily = lora,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),

        labelLarge = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.07.em
        ),       // site buttons
        labelMedium = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            letterSpacing = 0.08.em
        ),     // site nav
        labelSmall = TextStyle(
            fontFamily = karla,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.08.em
        ),
    )
}

@Composable
fun sespTaglineStyle() = TextStyle(
    fontFamily = lora,
    fontStyle = FontStyle.Italic,
    fontSize = 24.sp,
    lineHeight = 34.sp,
)