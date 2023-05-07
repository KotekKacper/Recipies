package com.kgkk.recipes.utils

import com.kgkk.recipes.R

object SavedImages {
    fun getCocktail(id: Int): Int {
        when (id%10){
            1 -> return R.drawable.cocktail1
            2 -> return R.drawable.cocktail2
            3 -> return R.drawable.cocktail3
            4 -> return R.drawable.cocktail4
            5 -> return R.drawable.cocktail5
            6 -> return R.drawable.cocktail6
            7 -> return R.drawable.cocktail7
            8 -> return R.drawable.cocktail8
            9 -> return R.drawable.cocktail9
            0 -> return R.drawable.cocktail10
        }
        return R.drawable.empty_image
    }
    fun getCake(id: Int): Int {
        when (id%10){
            1 -> return R.drawable.cake1
            2 -> return R.drawable.cake2
            3 -> return R.drawable.cake3
            4 -> return R.drawable.cake4
            5 -> return R.drawable.cake5
            6 -> return R.drawable.cake6
            7 -> return R.drawable.cake7
            8 -> return R.drawable.cake8
            9 -> return R.drawable.cake9
            0 -> return R.drawable.cake10
        }
        return R.drawable.empty_image
    }
}