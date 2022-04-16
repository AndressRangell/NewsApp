package andres.rangel.newsapp.repository

import andres.rangel.newsapp.api.RetrofitInstance
import andres.rangel.newsapp.db.ArticleDatabase
import andres.rangel.newsapp.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(pageNumber = pageNumber)

    suspend fun getNewsByWord(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsByWord(searchQuery, pageNumber)

    suspend fun insertOrUpdate(article: Article) =
        db.getArticleDao().insertOrUpdate(article)

    suspend fun deleteArticle(article: Article) =
        db.getArticleDao().deleteArticle(article)

    fun getSavedNews() =
        db.getArticleDao().getAllArticle()

}