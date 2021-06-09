package ayds.apolo.songinfo.moredetails.model

import ayds.apolo.songinfo.moredetails.model.entities.Article
import ayds.apolo.songinfo.moredetails.model.repository.ArticleRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsModel {

    fun searchArticle(artistName: String)

    fun articleObservable(): Observable<Article>
}

internal class MoreDetailsModelImpl(private val repository: ArticleRepository) :
    MoreDetailsModel {

    private val articleSubject = Subject<Article>()

    override fun searchArticle(artistName: String) {
        repository.getArticleByArtistName(artistName).let {
            articleSubject.notify(it)
        }
    }

    override fun articleObservable(): Observable<Article> = articleSubject
}