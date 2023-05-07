package com.kgkk.recipes.viewmodels

import androidx.lifecycle.*
import com.kgkk.recipes.R
import com.kgkk.recipes.utils.Recipe
import com.kgkk.recipes.utils.SavedImages
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class RecipeListViewModel : ViewModel() {
    val cocktailList: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>().also { loadRecipes("cocktail") }
    }

    val cakeList: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>().also { loadRecipes("cake") }
    }

    private fun loadRecipes(query: String) {
        val list = arrayListOf<Recipe>()
        // For example:
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://api.api-ninjas.com/v1/recipe?query=$query")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("X-Api-Key", "ujmK0BnoO4njw07+yQ3e0w==Fvj7Xv5sMja1JNEi")

            val responseCode = connection.responseCode
            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }

            val recipes = JSONArray(response)
            for (i in 0 until recipes.length()) {
                val recipe = recipes.getJSONObject(i)
                val title = recipe.getString("title")
                val ingredients = recipe.getString("ingredients")
                val servings = recipe.getString("servings")
                val instructions = recipe.getString("instructions")
                var image = R.drawable.empty_image
                if (query == "cocktail"){
                    image = SavedImages.getCocktail(i)
                } else if (query == "cake"){
                    image = SavedImages.getCake(i)
                }
                list.add(Recipe(title, ingredients, servings, instructions, image))
            }
            if (query == "cocktail"){
                this@RecipeListViewModel.cocktailList.postValue(list)
            } else if (query == "cake"){
                this@RecipeListViewModel.cakeList.postValue(list)
            }

        }
    }


}
