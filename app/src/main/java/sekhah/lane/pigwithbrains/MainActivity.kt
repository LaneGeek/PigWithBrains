package sekhah.lane.pigwithbrains

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var pigGame = PigGame()
    private var die = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollDieButton.setOnClickListener {
            die = pigGame.rollDie()
            if (die == 1) {
                rollDieButton.isEnabled = false
                turnButton.text = "End Turn"
                pigGame.changeTurn()
            }
            updateScreen()
        }

        turnButton.setOnClickListener {
            if (turnButton.text == "Start Turn") {
                turnButton.text = "End Turn"
                rollDieButton.isEnabled = true
            } else {
                turnButton.text = "Start Turn"
                rollDieButton.isEnabled = false
                pigGame.changeTurn()
                die = 0
            }
            updateScreen()
        }

        newGameButton.setOnClickListener {
            pigGame = PigGame()
            die = 0
            rollDieButton.isEnabled = false
            turnButton.text = "Start Turn"
            turnButton.isEnabled = true
            updateScreen()
        }

        updateScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> player1ScoreTextView.text = "Setings"
            R.id.menu_about -> player1ScoreTextView.text = "About"
        }
        return true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the current game state
        savedInstanceState.putInt("player1Score", pigGame.player1Score)
        savedInstanceState.putInt("player2Score", pigGame.player2Score)
        savedInstanceState.putInt("turnPoints", pigGame.turnPoints)
        savedInstanceState.putInt("currentPlayer", pigGame.currentPlayer)
        savedInstanceState.putInt("die", die)
        savedInstanceState.putBoolean("rollDieButtonEnabled", rollDieButton.isEnabled)
        savedInstanceState.putBoolean("turnButtonEnabled", turnButton.isEnabled)
        savedInstanceState.putString("turnButtonText", turnButton.text.toString())

        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore the game state
        super.onRestoreInstanceState(savedInstanceState)

        pigGame.player1Score = savedInstanceState.getInt("player1Score")
        pigGame.player2Score = savedInstanceState.getInt("player2Score")
        pigGame.turnPoints = savedInstanceState.getInt("turnPoints")
        pigGame.currentPlayer = savedInstanceState.getInt("currentPlayer")
        die = savedInstanceState.getInt("die")
        rollDieButton.isEnabled = savedInstanceState.getBoolean("rollDieButtonEnabled")
        turnButton.isEnabled = savedInstanceState.getBoolean("turnButtonEnabled")
        turnButton.text = savedInstanceState.getString("turnButtonText")

        updateScreen()
    }

    private fun updateScreen() {
        if (pigGame.currentPlayer == 1)
            nextTurnTextView.text = "${player1NameEditText.text}'s Turn"
        else
            nextTurnTextView.text = "${player2NameEditText.text}'s Turn"

        player1ScoreTextView.text = pigGame.player1Score.toString()
        player2ScoreTextView.text = pigGame.player2Score.toString()
        turnPointsTextView.text = pigGame.turnPoints.toString()

        // Check for winner
        if (pigGame.checkForWinner() != -1) {
            when (pigGame.checkForWinner()) {
                0 -> nextTurnTextView.text = "It is a tie!"
                1 -> nextTurnTextView.text = "${player1NameEditText.text} Wins!"
                2 -> nextTurnTextView.text = "${player2NameEditText.text} Wins!"
            }
            rollDieButton.isEnabled = false
            turnButton.isEnabled = false
        }

        // Update the die image
        when (die) {
            0 -> dieImageView.setImageResource(R.drawable.pig)
            1 -> dieImageView.setImageResource(R.drawable.die1)
            2 -> dieImageView.setImageResource(R.drawable.die2)
            3 -> dieImageView.setImageResource(R.drawable.die3)
            4 -> dieImageView.setImageResource(R.drawable.die4)
            5 -> dieImageView.setImageResource(R.drawable.die5)
            6 -> dieImageView.setImageResource(R.drawable.die6)
        }
    }
}
