package com.kgkk.recipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kgkk.recipes.fragments.RecipeDetailFragment
import com.kgkk.recipes.utils.Constants


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val frag: RecipeDetailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_recipe_detail) as RecipeDetailFragment

        val cocktailId = intent.extras!!.getInt(Constants.EXTRA_COCKTAIL_ID)
        frag.setCocktail(cocktailId)
    }
}