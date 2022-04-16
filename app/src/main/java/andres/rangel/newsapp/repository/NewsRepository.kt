package andres.rangel.newsapp.repository

import andres.rangel.newsapp.api.RetrofitInstance
import andres.rangel.newsapp.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getNewsByWord(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsByWord(searchQuery, pageNumber)

}