package com.example.jikan.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jikan.R


fun medium_regular(): FontFamily{
   return FontFamily(Font(R.font.roboto_regular))
}

@Composable
fun TittleFont(text: String) {
    return Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight(50),
        fontFamily = medium_regular(),
        fontSize = 20.sp
    )
}

@Composable
fun MediumFont(text: String) {
    return Text(
        text = text,
        color = Color.White,
//        fontFamily = medium_regular(),
        fontSize = 13.sp
    )
}

@Composable
fun SmallFont(text: String) {
    return Text(
        text = text,
        color = Color.White,
//        fontFamily = medium_regular(),
        fontSize = 12.sp
    )
}




