package andres.rangel.newsapp.ui

import andres.rangel.newsapp.repository.NewsRepository
import androidx.lifecycle.ViewModel

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

}