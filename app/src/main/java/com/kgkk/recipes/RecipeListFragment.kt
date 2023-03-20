package com.kgkk.recipes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment


class RecipeListFragment : ListFragment() {

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val names = arrayOfNulls<String>(CocktailList.cocktailList.size)
        for (i in names.indices) {
            names[i] = CocktailList.cocktailList[i].name
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            inflater.context, android.R.layout.simple_list_item_1, names
        )
        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(listView: ListView, itemView: View, position: Int, id: Long) {
        if (listener != null) {
            listener?.itemClicked(id.toInt());
        }
    }

}