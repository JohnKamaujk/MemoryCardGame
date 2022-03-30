package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.memorygame.R.drawable.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var Button1: ImageButton
    private lateinit var Button2: ImageButton
    private lateinit var Button3: ImageButton
    private lateinit var Button4: ImageButton
    private lateinit var Button5: ImageButton
    private lateinit var Button6: ImageButton
    private lateinit var Button7: ImageButton
    private lateinit var Button8: ImageButton
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard:Int?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Button1 = findViewById(R.id.imageButton1)
        Button2 = findViewById(R.id.imageButton2)
        Button3 = findViewById(R.id.imageButton3)
        Button4 = findViewById(R.id.imageButton4)
        Button5 = findViewById(R.id.imageButton5)
        Button6 = findViewById(R.id.imageButton6)
        Button7 = findViewById(R.id.imageButton7)
        Button8 = findViewById(R.id.imageButton8)

        val images = mutableListOf(ic_heart, ic_light, ic_plane, ic_smiley)
//        Adds all images so that we can have pairs
        images.addAll(images)
//        Randomize the order of images
        images.shuffle()
        buttons = listOf(Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!")
//                update models
                updateModels(index)
//                update ui of the game
                updateViews()



            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button=buttons[index]
            if (card.isMatched){
                button.alpha=0.1f
            }

            if (card.isFaceUp) {
                button.setImageResource(card.identifier)
            }else{
                button.setImageResource(R.drawable.ic_code)
            }

        }

    }

    private fun updateModels(position:Int) {
        val card = cards[position]
//        Error checking
        if (card.isFaceUp){
            Toast.makeText(this,"invalid move",Toast.LENGTH_SHORT).show()
            return
        }

        if (indexOfSingleSelectedCard==null){
            restoreCards()
            indexOfSingleSelectedCard=position
        }else{
            checkForMatch(indexOfSingleSelectedCard!!,position)
            indexOfSingleSelectedCard=null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards){
            if (!card.isMatched){
                card.isFaceUp=false
            }
        }
    }

    private fun checkForMatch(position1:Int,position2:Int) {
        if (cards[position1].identifier==cards[position2].identifier){
            Toast.makeText(this,"Match found!",Toast.LENGTH_SHORT).show()
            cards[position1].isMatched=true
            cards[position2].isMatched=true
        }
    }
}