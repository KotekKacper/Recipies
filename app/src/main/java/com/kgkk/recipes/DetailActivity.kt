package com.kgkk.recipes

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kgkk.recipes.fragments.RecipeDetailFragment
import com.kgkk.recipes.utils.Constants.EXTRA_COCKTAIL_ID
import com.kgkk.recipes.viewmodels.CocktailListViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var cocktailViewModel: CocktailListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag: RecipeDetailFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_recipe_detail) as RecipeDetailFragment

        val cocktailId = intent.extras!!.getInt(EXTRA_COCKTAIL_ID)
        frag.setCocktail(cocktailId)

        // Ustawiamy pasek narzędzi jako pasek aplikacji aktywności
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        cocktailViewModel = ViewModelProvider(this)[CocktailListViewModel::class.java]

        cocktailViewModel.cocktailList.observe(this) { cocktails ->
            // Wyświetlamy informacje o koktajlu
            val cocktailName: String = cocktails[cocktailId].name
            val textView = findViewById<TextView>(R.id.cocktail_text)
            textView.text = cocktailName
            val cocktailImage: Int = cocktails[cocktailId].imageId
            val imageView = findViewById<ImageView>(R.id.cocktail_image)
            imageView.setImageDrawable(ContextCompat.getDrawable(this, cocktailImage))
            imageView.contentDescription = cocktailName
        }
    }
}