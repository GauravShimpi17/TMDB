package com.example.tmdb.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.ui.theme.summaryTxtColor

@Composable
fun TextWithStartIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    textSize: TextUnit = 18.sp,
    color: Color = summaryTxtColor,
    iconColor: Color = summaryTxtColor
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.then(modifier)
    ) {
        Image(
            imageVector = icon, contentDescription = null, colorFilter = ColorFilter.tint(
                iconColor
            )
        )
        Text(
            text = text,
            fontSize = textSize,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
    }
}