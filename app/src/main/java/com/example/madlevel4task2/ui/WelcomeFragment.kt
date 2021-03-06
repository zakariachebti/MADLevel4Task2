package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.GameLog
import com.example.madlevel4task2.repository.GameLogRepository
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment() {

    private lateinit var gameLogRepository: GameLogRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var computerMove: String
    private lateinit var playerMove: String

    private val ROCK = "rock"
    private val PAPER = "paper"
    private val SCISSORS = "scissors"

    private val DRAW = "Draw"
    private val COMPUTER_WINS = "Computer wins!"
    private val PLAYER_WINS = "You win!"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameLogRepository = GameLogRepository(requireContext())

        hideViews()

        updateState()

        ibRock.setOnClickListener {
            playerMoveSelected(ROCK)
        }

        ibPaper.setOnClickListener {
            playerMoveSelected(PAPER)
        }

        ibScissor.setOnClickListener {
            playerMoveSelected(SCISSORS)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun hideViews() {
        ivGamePlayer.visibility = View.INVISIBLE
        tvPlayer.visibility = View.INVISIBLE
        ivGameComputer.visibility = View.INVISIBLE
        tvComputer.visibility = View.INVISIBLE
        tvVS.visibility = View.INVISIBLE
        tvGameResult.visibility = View.INVISIBLE
    }

    private fun updateState() {
        mainScope.launch {
            tvStats.text =  withContext(Dispatchers.IO) {
                "Win: " + gameLogRepository.getWin() + " Draw: " +
                        gameLogRepository.getDraw() + " Lose: " + gameLogRepository.getLose()
            }
        }
    }

    private fun playerMoveSelected(move: String) {
        playerMove = move
        when (move) {
            ROCK -> ivGamePlayer.setImageResource(R.drawable.rock)
            PAPER -> ivGamePlayer.setImageResource(R.drawable.paper)
            SCISSORS -> ivGamePlayer.setImageResource(R.drawable.scissors)
        }
        refreshGame()
        addGameLog()
    }

    private fun refreshGame() {
        val max = 3
        val computerPick  = Random.nextInt(max)

        when (computerPick) {
            0 -> {
                ivGameComputer.setImageResource(R.drawable.rock)
                computerMove = ROCK
            }
            1 -> {
                ivGameComputer.setImageResource(R.drawable.paper)
                computerMove = PAPER
            }
            2 -> {
                ivGameComputer.setImageResource(R.drawable.scissors)
                computerMove = SCISSORS
            }
        }
        ivGamePlayer.visibility = View.VISIBLE
        tvPlayer.visibility = View.VISIBLE
        ivGameComputer.visibility = View.VISIBLE
        tvComputer.visibility = View.VISIBLE
        tvVS.visibility = View.VISIBLE
        tvGameResult.text = getResult()
        tvGameResult.visibility = View.VISIBLE
    }

    private fun getResult(): String {
        if (computerMove == playerMove) return DRAW
        when (computerMove) {
            ROCK -> {
                return if (playerMove == PAPER) PLAYER_WINS
                else COMPUTER_WINS
            }
            PAPER -> {
                return if (playerMove == SCISSORS) PLAYER_WINS
                else COMPUTER_WINS
            }
            SCISSORS -> {
                return if (playerMove == ROCK) PLAYER_WINS
                else COMPUTER_WINS

            }
            else -> return "hoi"
        }
    }

    private fun addGameLog() {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy" )
        mainScope.launch {
            val game = GameLog(
                gameDate = sdf.format(cal.time),
                moveComputer = computerMove,
                movePlayer = playerMove,
                result = getResult()
            )

            withContext(Dispatchers.IO) {
                gameLogRepository.insertGame(game)
            }

            tvStats.text =  withContext(Dispatchers.IO) {
                "Win: " + gameLogRepository.getWin() + " Draw: " +
                        gameLogRepository.getDraw() + " Lose: " + gameLogRepository.getLose()
            }
        }
    }

}