package com.kgkk.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class RecipeDetailFragment : Fragment() {

    private var cocktailId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val title = view.findViewById<View>(R.id.textTitle) as TextView
            val (name, recipe) = CocktailList.cocktailList[cocktailId!!]
            title.text = name
            val description = view.findViewById<View>(R.id.textDescription) as TextView
            description.text = recipe
        }
    }

    fun setCocktail(id: Int) {
        this.cocktailId = id
    }

}