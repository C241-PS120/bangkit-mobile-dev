package com.example.coffeeprotectionandanalysissystem.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHomeBinding
import com.example.coffeeprotectionandanalysissystem.ui.history.HistoryViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val HomeViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        HomeViewModel.text.observe(viewLifecycleOwner) {
            // Di sini, Anda dapat melakukan sesuatu dengan data yang diamati,
            // misalnya, menetapkannya ke widget lain di layout, melakukan operasi,
            // atau menampilkan data di logcat.
        }

        return root
    }
}
