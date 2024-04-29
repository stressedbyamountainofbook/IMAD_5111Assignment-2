package com.spl.mypetapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    private var petHealth = 100
    private var pethunger = 100
    private var petcleanliness = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        //get the button and text views
        val btnFeed = findViewById<Button>(R.id.btn_feed)
        val btnClean = findViewById<Button>(R.id.btn_clean)
        val btnplay = findViewById<Button>(R.id.btn_play)
        val txthunger = findViewById<EditText>(R.id.txt_hunger)
        val txtclean = findViewById<EditText>(R.id.txt_clean)
        val txthappy = findViewById<EditText>(R.id.txt_happy)
        val imageView = findViewById<ImageView>(R.id.imageView) // Get reference to ImageView

        //set the initial text values
        txthunger.setText(pethunger.toString())
        txtclean.setText(petcleanliness.toString())
        txthappy.setText(petHealth.toString())

        //handle button clicks
        btnFeed.setOnClickListener {
            pethunger += 10
            petHealth += 10
            txthunger.setText(pethunger.toString())
            animateImageChange(
                imageView,
                R.drawable.petimage1,
                R.drawable.pet_eating
            ) // Pass ImageView reference
        }

        btnClean.setOnClickListener {
            petcleanliness += 10
            petHealth += 10
            txtclean.setText(petcleanliness.toString())
            txthappy.setText(petHealth.toString())
            animateImageChange(
                imageView,
                R.drawable.petimage1,
                R.drawable.pet_clean
            )
        }

        btnplay.setOnClickListener {
            petHealth += 10
            pethunger -= 5
            petcleanliness -= 5
            txthappy.setText(petHealth.toString())
            txtclean.setText(petcleanliness.toString())
            txthunger.setText(pethunger.toString())
            animateImageChange(imageView, R.drawable.petimage1, R.drawable.pet_playing)
        }
        handler.postDelayed({
            petlifevaluesdecrease()
            handler.postDelayed({
                petlifevaluesdecrease()
                handler.postDelayed({
                    petlifevaluesdecrease()
                }, 3000) // Decrease pet cleanliness every every 3 seconds
            }, 3000) // Decrease pet health value every 3 seconds
        }, 3000) // Decrease pet hunger value once every  3 seconds
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private val handler = Handler(Looper.getMainLooper())
    private fun petlifevaluesdecrease() {
        pethunger -= 5
        petHealth -= 4
        petcleanliness -= 6
        updatePetAttributesUI()
    }

    private fun updatePetAttributesUI() {
        findViewById<EditText>(R.id.txt_hunger).setText(pethunger.toString())
        findViewById<EditText>(R.id.txt_clean).setText(petcleanliness.toString())
        findViewById<EditText>(R.id.txt_happy).setText(petHealth.toString())
    }


    private fun animateImageChange(imageView: ImageView, image1: Int, image2: Int) {
        val animation = AlphaAnimation(0.0f, 1.0f)
        // 0.5 seconds duration for image transition
        animation.duration = 300
        animation.fillAfter = true
        imageView.startAnimation(animation)
        imageView.setImageResource(image2)


        Handler().postDelayed({
            imageView.setImageResource(image1)
            val animationReverse = AlphaAnimation(1.0f, 0.0f)
            // 0.5 seconds duration for image transition
            animationReverse.duration = 500
            animationReverse.fillAfter = true
            imageView.startAnimation(animation)
            imageView.setImageResource(image1)
        }//return to default image after 8 seconds
            ,8000)
    }
}
