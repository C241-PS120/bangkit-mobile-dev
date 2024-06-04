package com.example.coffeeprotectionandanalysissystem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.database.History
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityListHistoryBinding

class HistoryAdapter(
    private val historyList: MutableList<History>,
    private val onDeleteClick: (History) -> Unit,
    private val onItemClicked: (History) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ActivityListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History, onItemClicked: (History) -> Unit){
            binding.titleArtikel.text = history.label
            binding.descriptionDashboard.text = history.suggestion
            binding.dateHistory.text = history.date
            Glide.with(binding.imageDashboard.context)
                .load(history.imageId)
                .into(binding.imageDashboard)

            binding.deleteButton.setOnClickListener {
                onDeleteClick(history)
            }
            binding.root.setOnClickListener {
                onItemClicked(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ActivityListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history, onItemClicked)
    }

    override fun getItemCount(): Int = historyList.size

    fun updateData(newHistoryList: List<History>) {
        historyList.clear()
        historyList.addAll(newHistoryList)
        notifyDataSetChanged()
    }
}
