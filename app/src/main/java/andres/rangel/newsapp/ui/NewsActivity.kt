package andres.rangel.newsapp.ui

import andres.rangel.newsapp.databinding.ActivityNewsBinding
import andres.rangel.newsapp.db.ArticleDatabase
import andres.rangel.newsapp.repository.NewsRepository
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        binding.bottomNavigationView.setupWithNavController(
            binding.newsNavigationHost.getFragment<NavHostFragment>().navController)

    }
}