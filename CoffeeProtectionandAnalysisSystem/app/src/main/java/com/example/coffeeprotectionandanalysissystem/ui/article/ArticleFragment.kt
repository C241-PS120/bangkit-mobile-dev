package com.example.coffeeprotectionandanalysissystem.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSearchView()

        // Get the label argument and set it to the SearchView
        arguments?.getString("label")?.let { label ->
            binding.searchView.setQuery(label, false)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        loadArticles()
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

    private fun loadArticles() {
        articleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles != null) {
                articleAdapter.setArticles(articles)
            }
        }

        articleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}

