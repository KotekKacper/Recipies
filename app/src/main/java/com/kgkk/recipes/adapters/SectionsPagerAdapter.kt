package com.kgkk.recipes.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kgkk.recipes.R
import com.kgkk.recipes.fragments.RecipeListFragment
import com.kgkk.recipes.fragments.Tab2Fragment
import com.kgkk.recipes.fragments.TopFragment


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TopFragment()
            1 -> return RecipeListFragment()
            2 -> return Tab2Fragment()
        }
        return TopFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getText(R.string.home_tab)
            1 -> return context.getText(R.string.kat1_tab)
            2 -> return context.getText(R.string.kat2_tab)
        }
        return null
    }
}