package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.GameLog
import com.example.madlevel4task2.repository.GameLogRepository
import kotlinx.android.synthetic.main.fragment_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameHistoryFragment : Fragment() {

    private lateinit var gameLogRepository: GameLogRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<GameLog>()
    private val gameLogAdapter = GameLogAdapter(games)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameLogRepository = GameLogRepository(requireContext())

        initViews()

        getGameLogsFromDatabase()

    }

    private fun initViews() {
        rvGameHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGameHistory.adapter = gameLogAdapter
        rvGameHistory.setHasFixedSize(true)
        rvGameHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getGameLogsFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gameLogRepository.getAllGames()
            }
            this@GameHistoryFragment.games.clear()
            this@GameHistoryFragment.games.addAll(games)
            gameLogAdapter.notifyDataSetChanged()
        }
    }

}