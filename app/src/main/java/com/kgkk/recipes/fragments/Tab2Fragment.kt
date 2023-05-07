package com.kgkk.recipes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kgkk.recipes.DetailActivity
import com.kgkk.recipes.R
import com.kgkk.recipes.adapters.CaptionedImagesAdapter
import com.kgkk.recipes.utils.Constants
import com.kgkk.recipes.viewmodels.RecipeListViewModel

class Tab2Fragment : Fragment() {

    private lateinit var viewModel: RecipeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and initialize the RecyclerView and its adapter
        val cakeRecycler =
            inflater.inflate(R.layout.fragment_tab2, container, false) as RecyclerView
        val adapter = CaptionedImagesAdapter(emptyArray(), IntArray(0))
        cakeRecycler.adapter = adapter

        // Create and observe the ViewModel's cocktailList property
        viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]
        viewModel.cakeList.observe(viewLifecycleOwner) { cake ->
            val cakeNames = cake.map { it.name }.toTypedArray()
            val cakeImages = cake.map { it.imageId }.toIntArray()
            adapter.setData(cakeNames, cakeImages)
        }

        // Set the layout manager and adapter listener
        val layoutManager = GridLayoutManager(activity, 2)
        cakeRecycler.layoutManager = layoutManager
        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_RECIPE_TYPE, Constants.CAKE_RECIPE_TYPE)
                intent.putExtra(Constants.EXTRA_RECIPE_ID, position)
                activity!!.startActivity(intent)
            }
        })

        return cakeRecycler
    }
}