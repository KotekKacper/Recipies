package com.kgkk.recipes

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kgkk.recipes.fragments.RecipeDetailFragment
import com.kgkk.recipes.utils.Constants.CAKE_RECIPE_TYPE
import com.kgkk.recipes.utils.Constants.COCKTAIL_RECIPE_TYPE
import com.kgkk.recipes.utils.Constants.EXTRA_RECIPE_ID
import com.kgkk.recipes.utils.Constants.EXTRA_RECIPE_TYPE
import com.kgkk.recipes.utils.Recipe
import com.kgkk.recipes.viewmodels.RecipeListViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var recipeViewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag: RecipeDetailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_recipe_detail) as RecipeDetailFragment


        val recipeType = intent.extras!!.getString(EXTRA_RECIPE_TYPE)
        val recipeId = intent.extras!!.getInt(EXTRA_RECIPE_ID)
        frag.setRecipe(recipeType, recipeId)

        // Ustawiamy pasek narzędzi jako pasek aplikacji aktywności
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        recipeViewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]

        if (recipeType == COCKTAIL_RECIPE_TYPE){
            recipeViewModel.cocktailList.observe(this) { cocktails ->
                // Wyświetlamy informacje o koktajlu
                fillRecipe(cocktails, recipeId)
            }
        } else if (recipeType == CAKE_RECIPE_TYPE){
            recipeViewModel.cakeList.observe(this) { cakes ->
                // Wyświetlamy informacje o cieście
                fillRecipe(cakes, recipeId)
            }
        }
    }

    private fun fillRecipe(recipeList: List<Recipe>, recipeId: Int){
        val cocktailName: String = recipeList[recipeId].name
        val cocktailImage: Int = recipeList[recipeId].imageId
        val imageView = findViewById<ImageView>(R.id.cocktail_image)
        imageView.setImageDrawable(ContextCompat.getDrawable(this, cocktailImage))
        imageView.contentDescription = cocktailName
    }
}