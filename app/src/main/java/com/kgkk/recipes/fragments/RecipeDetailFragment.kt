package com.kgkk.recipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.kgkk.recipes.R
import com.kgkk.recipes.utils.Constants.CAKE_RECIPE_TYPE
import com.kgkk.recipes.utils.Constants.COCKTAIL_RECIPE_TYPE
import com.kgkk.recipes.utils.Recipe
import com.kgkk.recipes.viewmodels.RecipeListViewModel


class RecipeDetailFragment : Fragment() {

    private var recipeId: Int? = null
    private var recipeType: String? = null
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]
        if (savedInstanceState != null) {
            recipeId = savedInstanceState.getLong("recipeId").toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (recipeType == COCKTAIL_RECIPE_TYPE){
            viewModel.cocktailList.observe(viewLifecycleOwner) { cocktails ->
                recipeId?.let { fillRecipe(view, cocktails, it) }
            }
        } else if (recipeType == CAKE_RECIPE_TYPE){
            viewModel.cakeList.observe(viewLifecycleOwner) { cakes ->
                recipeId?.let { fillRecipe(view, cakes, it) }
            }
        }

        val addButton = view.findViewById<Button>(R.id.add_timer_button)
        addButton.setOnClickListener {
            insertNestedFragment()
        }
    }

    private fun fillRecipe(view: View, recipeList: List<Recipe>, recipeId: Int){
        if (recipeList.isNotEmpty()) {
            // Use the cocktails data to populate your UI
            val (name, ingredients, servings, instructions) = recipeList[recipeId]
            val title = view.findViewById<TextView>(R.id.textTitle)
            title.text = name
            val textIngredients = view.findViewById<TextView>(R.id.textIngredients)
            textIngredients.text =
                ingredients.split('|').joinToString(separator = "\n- ", prefix = "- ")
            val textServings = view.findViewById<TextView>(R.id.textServings)
            textServings.text = servings.split(' ')[0]
            val textInstructions = view.findViewById<TextView>(R.id.textInstructions)
            textInstructions.text = instructions
        }
    }

    fun setRecipe(type: String?, id: Int) {
        this.recipeType = type
        this.recipeId = id
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("cocktailId", recipeId!!.toLong())
    }

    private fun insertNestedFragment() {
        val childFragment: Fragment = CountdownTimerFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.counter_container, childFragment).commit()
    }
}