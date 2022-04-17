package andres.rangel.newsapp.ui

import andres.rangel.newsapp.models.Article
import andres.rangel.newsapp.models.NewsResponse
import andres.rangel.newsapp.repository.NewsRepository
import andres.rangel.newsapp.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val newsByWord: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsByWordPage = 1
    var newsByWordResponse: NewsResponse? = null

    init {
        getBreakingNews()
    }

    fun getBreakingNews() = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun getNewsByWord(searchQuery: String) = viewModelScope.launch {
        newsByWord.postValue(Resource.Loading())
        val response = newsRepository.getNewsByWord(searchQuery, newsByWordPage)
        newsByWord.postValue(handleNewsByWordResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }else {
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleNewsByWordResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                newsByWordPage++
                if(newsByWordResponse == null){
                    newsByWordResponse = resultResponse
                }else {
                    val oldArticle = newsByWordResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(newsByWordResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertOrUpdate(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSavedNews() =
        newsRepository.getSavedNews()

}