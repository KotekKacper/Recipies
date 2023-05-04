package com.kgkk.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class RecipeDetailFragment : Fragment() {

    private var cocktailId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            cocktailId = savedInstanceState.getLong("cocktailId").toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val (name, ingredients, servings, instructions) = CocktailList.cocktailList[cocktailId!!]

            val title = view.findViewById<TextView>(R.id.textTitle)
            title.text = name
            val textIngredients = view.findViewById<TextView>(R.id.textIngredients)
            textIngredients.text = ingredients.split('|').joinToString(separator = "\n- ", prefix = "- ")
            val textServings = view.findViewById<TextView>(R.id.textServings)
            textServings.text = servings
            val textInstructions = view.findViewById<TextView>(R.id.textInstructions)
            textInstructions.text = instructions
        }
    }

    fun setCocktail(id: Int) {
        this.cocktailId = id
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("cocktailId", cocktailId!!.toLong())
    }
}