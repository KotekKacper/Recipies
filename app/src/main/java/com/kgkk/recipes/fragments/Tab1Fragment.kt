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
import com.kgkk.recipes.utils.Constants.EXTRA_COCKTAIL_ID
import com.kgkk.recipes.viewmodels.CocktailListViewModel


class Tab1Fragment : Fragment() {

    private lateinit var viewModel: CocktailListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and initialize the RecyclerView and its adapter
        val cocktailRecycler =
            inflater.inflate(R.layout.fragment_tab1, container, false) as RecyclerView
        val adapter = CaptionedImagesAdapter(emptyArray(), IntArray(0))
        cocktailRecycler.adapter = adapter

        // Create and observe the ViewModel's cocktailList property
        viewModel = ViewModelProvider(this)[CocktailListViewModel::class.java]
        viewModel.cocktailList.observe(viewLifecycleOwner) { cocktails ->
            val cocktailNames = cocktails.map { it.name }.toTypedArray()
            val cocktailImages = cocktails.map { it.imageId }.toIntArray()
            adapter.setData(cocktailNames, cocktailImages)
        }

        // Set the layout manager and adapter listener
        val layoutManager = GridLayoutManager(activity, 2)
        cocktailRecycler.layoutManager = layoutManager
        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_COCKTAIL_ID, position)
                activity!!.startActivity(intent)
            }
        })

        return cocktailRecycler
    }
}
