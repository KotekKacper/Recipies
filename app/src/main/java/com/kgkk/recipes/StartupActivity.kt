package com.kgkk.recipes

import androidx.fragment.app.Fragment
import com.kgkk.recipes.fragments.StartupFragment

class StartupActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return StartupFragment()
    }
}