package com.kgkk.recipes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kgkk.recipes.DetailActivity
import com.kgkk.recipes.R
import com.kgkk.recipes.adapters.CaptionedImagesAdapter
import com.kgkk.recipes.utils.CocktailList
import com.kgkk.recipes.utils.Constants.EXTRA_COCKTAIL_ID


class Tab1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cocktailRecycler =
            inflater.inflate(R.layout.fragment_tab1, container, false) as RecyclerView
        val cocktailNames = arrayOfNulls<String>(CocktailList.cocktailList.size)
        for (i in cocktailNames.indices) {
            cocktailNames[i] = CocktailList.cocktailList[i].name
        }
        val cocktailImages = IntArray(CocktailList.cocktailList.size)
        for (i in cocktailImages.indices) {
            cocktailImages[i] = CocktailList.cocktailList[i].imageId
        }
        val adapter = CaptionedImagesAdapter(cocktailNames, cocktailImages)
        cocktailRecycler.adapter = adapter

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