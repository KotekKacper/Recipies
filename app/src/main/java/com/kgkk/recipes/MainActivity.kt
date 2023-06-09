package com.kgkk.recipes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kgkk.recipes.adapters.SectionsPagerAdapter
import com.kgkk.recipes.utils.Constants


class MainActivity : AppCompatActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val pager = findViewById<View>(R.id.pager) as ViewPager
        pager.adapter = pagerAdapter
        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                // Kod wykonywany po kliknięciu elementu Action_Info
                Toast.makeText(this, "It's a recipe app!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun itemClicked(id: Int) {
        // tablet specific (not used currently)
        val fragmentContainer: View? = findViewById(R.id.fragment_container)
//        if (fragmentContainer != null) {
//            val details = RecipeDetailFragment()
//            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//            details.setCocktail(id)
//            ft.replace(R.id.fragment_container, details)
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            ft.addToBackStack(null)
//            ft.commit()
//        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_RECIPE_ID, id)
            startActivity(intent)
//        }
    }
}