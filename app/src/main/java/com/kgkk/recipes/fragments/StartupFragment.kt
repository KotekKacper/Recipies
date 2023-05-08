package com.kgkk.recipes.fragments

import android.animation.*
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kgkk.recipes.MainActivity
import com.kgkk.recipes.R

class StartupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_startup, container, false)

        val cocktailImageView = view.findViewById<ImageView>(R.id.cocktail_image_view)
        val cakeImageView = view.findViewById<ImageView>(R.id.cake_image_view)
        val recipeBookImageView = view.findViewById<ImageView>(R.id.recipe_book_image_view)

        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val cocktailStartX = 0f
        val cakeStartX = 0f

        val cocktailAnimator = ObjectAnimator.ofFloat(cocktailImageView, View.TRANSLATION_X, -screenWidth, screenWidth/2f)
        val cakeAnimator = ObjectAnimator.ofFloat(cakeImageView, View.TRANSLATION_X, screenWidth, -screenWidth/2f)
        val bookAnimator = ObjectAnimator.ofPropertyValuesHolder(
            recipeBookImageView,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 2.5f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 2.5f),
        )
        bookAnimator.duration = 3000

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(cocktailAnimator, cakeAnimator)
        animatorSet.duration = 2000

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {bookAnimator.start()}
            override fun onAnimationEnd(animation: Animator) {
                // starting main activity
                val mainActivityIntent = Intent(view.context, MainActivity::class.java)
                startActivity(mainActivityIntent)
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        cocktailImageView.post {
            val cocktailOffsetX = (cocktailImageView.width/2f)*1.2f
            val cakeOffsetX = -cakeImageView.width/2f
            cocktailAnimator.setFloatValues(cocktailStartX, cocktailOffsetX)
            cakeAnimator.setFloatValues(cakeStartX, cakeOffsetX)
        }

        animatorSet.start()

        return view
    }
}


