package com.kgkk.recipes.viewmodels

import androidx.lifecycle.*
import com.kgkk.recipes.utils.Cocktail
import com.kgkk.recipes.utils.SavedImages
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class CocktailListViewModel : ViewModel() {
    val cocktailList: MutableLiveData<List<Cocktail>> by lazy {
        MutableLiveData<List<Cocktail>>().also { loadCocktails() }
    }

    private fun loadCocktails() {
        val list = arrayListOf<Cocktail>()
        // For example:
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://api.api-ninjas.com/v1/recipe?query=drink")
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

                list.add(Cocktail(title, ingredients, servings, instructions, SavedImages.getCocktail(i)))
            }
            this@CocktailListViewModel.cocktailList.postValue(list)
        }
    }
}
