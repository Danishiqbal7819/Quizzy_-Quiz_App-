package com.example.utils

import com.example.quizzy.R

object iconpicker {
        val Icons= arrayOf(
            R.drawable.ic1,
            R.drawable.ic2,
            R.drawable.ic3,
            R.drawable.ic4,
            R.drawable.ic5,
            R.drawable.ic6,
            R.drawable.ic7
        )
        var currentIconIndex=0
        fun getIcon():Int{
            currentIconIndex=(currentIconIndex+1)% Icons.size
            return Icons[currentIconIndex]

    }
}