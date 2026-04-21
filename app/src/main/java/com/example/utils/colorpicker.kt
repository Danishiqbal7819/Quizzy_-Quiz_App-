package com.example.utils

object colorpicker {
//    val colors= arrayOf(
//        "#FF0000",
//        "#00FF00",
//     "#0000FF",
//  "#FFFF00",
//    "#FFC080",
//   "#800080",
//     "#008000",
//     "#FF69B4",
//     "#808000" ,
//     "#008080"
//    )
    val colors = arrayOf(
        "#FFC0CB",
        "#F5DEB3",
        "#ADD8E6",
        "#87CEEB",
        "#B3B3B3",
        "#A1A1A1",
        "#99CC00",
        "#66CCCC",
        "#FFFF99",
        "#FFD700"
    )
    var currentColorIndex=0
    fun getcolor():String{
        currentColorIndex=(currentColorIndex+1)% colors.size
        return colors[currentColorIndex]
    }
}