package com.deep.compose_project.textView

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Textviews(colors: Color,fontSize: Int) {
    Text("Hello Bro", color = colors, fontSize = fontSize.sp, fontWeight = FontWeight.Bold)
}