package com.kgkk.recipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun itemClicked(id: Int) {
        val fragmentContainer: View? = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {

        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_COCKTAIL_ID, id)
            startActivity(intent)
        }
    }
}