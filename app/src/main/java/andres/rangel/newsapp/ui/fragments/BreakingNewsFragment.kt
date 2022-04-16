package andres.rangel.newsapp.ui.fragments

import andres.rangel.newsapp.R
import andres.rangel.newsapp.adapters.NewsAdapter
import andres.rangel.newsapp.databinding.FragmentBreakingNewsBinding
import andres.rangel.newsapp.ui.NewsActivity
import andres.rangel.newsapp.ui.NewsViewModel
import andres.rangel.newsapp.utils.Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val className = "BreakingNewsFragment"
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        initRecyclerView()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(className, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

}