package com.kgkk.recipes

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private var recipeId: Int = 0
    private var recipeType: String = COCKTAIL_RECIPE_TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag: RecipeDetailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_recipe_detail) as RecipeDetailFragment

        recipeType = intent.extras!!.getString(EXTRA_RECIPE_TYPE)!!
        recipeId = intent.extras!!.getInt(EXTRA_RECIPE_ID)
        frag.setRecipe(recipeType, recipeId)

        recipeViewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]

        when (recipeType) {
            COCKTAIL_RECIPE_TYPE -> {
                recipeViewModel.cocktailList.observe(this) { cocktails ->
                    // Wyświetlamy informacje o koktajlu
                    fillRecipe(cocktails, recipeId)
                }
            }
            CAKE_RECIPE_TYPE -> {
                recipeViewModel.cakeList.observe(this) { cakes ->
                    // Wyświetlamy informacje o cieście
                    fillRecipe(cakes, recipeId)
                }
            }
        }
    }

    private fun fillRecipe(recipeList: List<Recipe>, recipeId: Int){
        val recipeName: String = recipeList[recipeId].name
        val recipeImage: Int = recipeList[recipeId].imageId
        val imageView = findViewById<ImageView>(R.id.cocktail_image)
        imageView.setImageDrawable(ContextCompat.getDrawable(this, recipeImage))
        imageView.contentDescription = recipeName

        // Ustawiamy pasek narzędzi jako pasek aplikacji aktywności
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = recipeName
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onClickSend(view: View) { // view must be an argument or FAB won't work
        var recipe: Recipe? = null
        when(recipeType){
            COCKTAIL_RECIPE_TYPE -> recipe = recipeViewModel.cocktailList.value!![recipeId]
            CAKE_RECIPE_TYPE -> recipe = recipeViewModel.cakeList.value!![recipeId]
        }

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, formatRecipe(recipe!!))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun formatRecipe(recipe: Recipe): String {
        return "${recipe.name}\n\n" +
                "Ingredients:\n${recipe.ingredients.replace("|", "\n")}\n\n" +
                "Servings: ${recipe.servings}\n\n" +
                "Instructions:\n${recipe.instructions}"
    }

}