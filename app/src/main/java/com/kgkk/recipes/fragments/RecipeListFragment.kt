package com.kgkk.recipes.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.kgkk.recipes.utils.Cocktail
import com.kgkk.recipes.utils.CocktailList
import com.kgkk.recipes.utils.SavedImages
import com.kgkk.recipes.Listener
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import java.net.HttpURLConnection
import java.net.URL


class RecipeListFragment : ListFragment() {

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lateinit var recipes: JSONArray

        // Launch a coroutine to perform the task
        GlobalScope.launch(Dispatchers.Main) {
            // Switch to a background thread
            withContext(Dispatchers.IO) {
                // Perform the task
                val url = URL("https://api.api-ninjas.com/v1/recipe?query=drink")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("X-Api-Key", "ujmK0BnoO4njw07+yQ3e0w==Fvj7Xv5sMja1JNEi")

                val responseCode = connection.responseCode
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                try {
                    val jsonArray = JSONArray(response)
                    // Do something with the JSON array
                    recipes = jsonArray
                } catch (e: JSONException) {
                    // Handle the error
                    Log.e("Error recipe", "Couldn't read json array")
                }

                connection.disconnect()
                inputStream.close()
            }
            // Switch back to the main thread to update the UI or perform other tasks
            Log.i("API Output", recipes.toString())
            val names = arrayOfNulls<String>(recipes.length())
            CocktailList.cocktailList = arrayListOf()
            for (i in names.indices) {
                val recipe = recipes.getJSONObject(i)
                val title = recipe.getString("title")
                val ingredients = recipe.getString("ingredients")
                val servings = recipe.getString("servings")
                val instructions = recipe.getString("instructions")

                names[i] = title
                CocktailList.cocktailList.add(Cocktail(title, ingredients, servings, instructions,
                    SavedImages.getCocktail(i)
                ))
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                inflater.context, android.R.layout.simple_list_item_1, names
            )
            listAdapter = adapter
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(listView: ListView, itemView: View, position: Int, id: Long) {
        if (listener != null) {
            listener?.itemClicked(id.toInt())
        }
    }

}