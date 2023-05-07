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
import com.kgkk.recipes.viewmodels.CocktailListViewModel


class RecipeDetailFragment : Fragment() {

    private var cocktailId: Int? = null
    private lateinit var viewModel: CocktailListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CocktailListViewModel::class.java]
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cocktailList.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.isNotEmpty()) {
                // Use the cocktails data to populate your UI
                val (name, ingredients, servings, instructions) = cocktails[cocktailId!!]
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

        val addButton = view.findViewById<Button>(R.id.add_timer_button)
        addButton.setOnClickListener {
            insertNestedFragment()
        }
    }

    fun setCocktail(id: Int) {
        this.cocktailId = id
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("cocktailId", cocktailId!!.toLong())
    }

    private fun insertNestedFragment() {
        val childFragment: Fragment = CountdownTimerFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.counter_container, childFragment).commit()
    }
}