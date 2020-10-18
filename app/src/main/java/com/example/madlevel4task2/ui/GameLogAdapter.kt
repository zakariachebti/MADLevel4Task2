package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.GameLog
import kotlinx.android.synthetic.main.game_log.view.*

class GameLogAdapter(private val games: List<GameLog>) : RecyclerView.Adapter<GameLogAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: GameLog) {
            itemView.rvResult.text = game.result
            itemView.tvDate.text = game.gameDate
            setImage(itemView.ivPlayer, game.movePlayer)
            setImage(itemView.ivComputer, game.moveComputer)
        }
    }

    private fun setImage(item: ImageView, i: String) {
        when (i) {
            "rock" -> item.setImageResource(R.drawable.rock)
            "paper" -> item.setImageResource(R.drawable.paper)
            "scissors" -> item.setImageResource(R.drawable.scissors)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameLogAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_log, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameLogAdapter.ViewHolder, position: Int) {
        holder.databind(games[position])
    }


}