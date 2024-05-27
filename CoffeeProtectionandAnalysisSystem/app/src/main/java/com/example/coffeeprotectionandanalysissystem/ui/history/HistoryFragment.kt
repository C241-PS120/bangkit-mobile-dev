package com.example.coffeeprotectionandanalysissystem.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mengamati data dari view model dan melakukan sesuatu dengan datanya
        historyViewModel.text.observe(viewLifecycleOwner) {
            // Di sini, Anda dapat melakukan sesuatu dengan data yang diamati,
            // misalnya, menetapkannya ke widget lain di layout, melakukan operasi,
            // atau menampilkan data di logcat.
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}