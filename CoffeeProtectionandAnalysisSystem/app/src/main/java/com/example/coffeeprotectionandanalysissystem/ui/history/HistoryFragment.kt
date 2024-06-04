package com.example.coffeeprotectionandanalysissystem.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeprotectionandanalysissystem.adapter.HistoryAdapter
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHistoryBinding
import com.example.coffeeprotectionandanalysissystem.database.AppDatabase
import com.example.coffeeprotectionandanalysissystem.database.History
import com.example.coffeeprotectionandanalysissystem.view.detail.DetailHistoryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
        setupRecyclerView()

        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            adapter.updateData(historyList)
        }

        loadHistory()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(
            mutableListOf(),
            this::showDeleteConfirmationDialog,
            this::onHistoryItemClick
        )
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    private fun onHistoryItemClick(history: History) {
        val intent = Intent(requireContext(), DetailHistoryActivity::class.java).apply {
            putExtra("imageUrl", history.imageId)
            putExtra("label", history.label)
            putExtra("suggestion", history.suggestion)
        }
        startActivity(intent)
    }

    private fun loadHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val historyList = db.historyDao().getAllHistory()
            viewModel.setHistoryList(historyList)
        }
    }

    private fun showDeleteConfirmationDialog(history: History) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Confirmation")
            setMessage("Are you sure you want to delete this history item?")
            setPositiveButton("Yes") { _, _ ->
                deleteHistory(history)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun deleteHistory(history: History) {
        CoroutineScope(Dispatchers.IO).launch {
            db.historyDao().removeHistory(history.id)
            loadHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
