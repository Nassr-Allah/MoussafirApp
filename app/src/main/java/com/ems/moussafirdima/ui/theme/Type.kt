package com.ems.moussafirdima.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ems.moussafirdima.R

val poppins = FontFamily(
    listOf(
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_thin, FontWeight.Thin)
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    body2 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Light,
        fontSize = R.dimen.body2.sp
    ),
    h1 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Black,
        fontSize = R.dimen.h1.sp
    ),
    h2 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = R.dimen.h2.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)