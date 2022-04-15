package andres.rangel.newsapp.ui.fragments

import andres.rangel.newsapp.R
import andres.rangel.newsapp.ui.NewsActivity
import andres.rangel.newsapp.ui.NewsViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }

}