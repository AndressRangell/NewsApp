package andres.rangel.newsapp.ui.fragments

import andres.rangel.newsapp.R
import andres.rangel.newsapp.databinding.FragmentArticleBinding
import andres.rangel.newsapp.ui.NewsActivity
import andres.rangel.newsapp.ui.NewsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class ArticleFragment: Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

}