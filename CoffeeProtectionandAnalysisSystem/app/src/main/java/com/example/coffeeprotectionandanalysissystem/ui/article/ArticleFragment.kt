package com.example.coffeeprotectionandanalysissystem.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeprotectionandanalysissystem.adapter.ArticleAdapter
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSearchView()
        setupSwipeRefreshLayout()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        observeViewModel()
        articleViewModel.fetchArticles() // Panggil fetchArticles saat Fragment pertama kali dibuat
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                articleAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (!articleViewModel.isLoading.value!!) { // Pastikan tidak sedang dalam proses memuat data
                articleViewModel.fetchArticles()
            } else {
                binding.swipeRefreshLayout.isRefreshing = false // Hentikan animasi swipe refresh jika sedang memuat
            }
        }
    }

    private fun observeViewModel() {
        articleViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            articles?.let {
                articleAdapter.setArticles(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false // Menyembunyikan indikator swipe refresh jika sedang aktif
            binding.progressBar.visibility = View.GONE // Menyembunyikan ProgressBar setelah data dimuat
        })

        articleViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // Menampilkan ProgressBar saat sedang memuat data
            } else {
                binding.progressBar.visibility = View.GONE // Menyembunyikan ProgressBar setelah data dimuat
            }
        })
    }
}
