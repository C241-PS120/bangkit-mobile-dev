package com.example.coffeeprotectionandanalysissystem.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentArticleBinding
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHistoryBinding
import com.example.coffeeprotectionandanalysissystem.ui.history.HistoryViewModel

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ArticleViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mengamati data dari view model dan melakukan sesuatu dengan datanya
        ArticleViewModel.text.observe(viewLifecycleOwner) {
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