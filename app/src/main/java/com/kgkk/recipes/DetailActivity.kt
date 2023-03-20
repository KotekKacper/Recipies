package com.kgkk.recipes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag: RecipeDetailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_recipe_detail) as RecipeDetailFragment

        val cocktailId = intent.extras!!.getInt(Constants.EXTRA_COCKTAIL_ID)
        frag.setCocktail(cocktailId)
    }
}