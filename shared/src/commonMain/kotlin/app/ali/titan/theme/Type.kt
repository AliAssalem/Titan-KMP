package app.ali.titan.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import titan.shared.generated.resources.Res
import titan.shared.generated.resources.lobster_regular

val LobsterFontFamily
    @Composable get() = FontFamily(Font(Res.font.lobster_regular))
