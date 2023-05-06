package com.kgkk.recipes

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.kgkk.recipes.utils.Cocktail
import com.kgkk.recipes.utils.CocktailList
import com.kgkk.recipes.utils.Constants.EXTRA_COCKTAIL_ID


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Ustawiamy pasek narzędzi jako pasek aplikacji aktywności
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Wyświetlamy informacje o koktajlu
        val cocktailId = intent.extras!![EXTRA_COCKTAIL_ID] as Int
        val cocktailName: String = CocktailList.cocktailList[cocktailId].name
        val textView = findViewById<TextView>(R.id.cocktail_text)
        textView.text = cocktailName
        val cocktailImage: Int = CocktailList.cocktailList[cocktailId].imageId
        val imageView = findViewById<ImageView>(R.id.cocktail_image)
        imageView.setImageDrawable(ContextCompat.getDrawable(this, cocktailImage))
        imageView.contentDescription = cocktailName
    }
}