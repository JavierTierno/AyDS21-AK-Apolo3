package ayds.apolo.songinfo.moredetails.model.repository.external.broker

import ayds.apolo.songinfo.moredetails.model.entities.Card
import ayds.apolo.songinfo.moredetails.model.entities.EmptyCard
import ayds.apolo.songinfo.moredetails.model.entities.FullCard
import ayds.apolo.songinfo.moredetails.model.entities.Source
import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus1.wikipedia.WikipediaService
import java.lang.Exception

internal class WikipediaProxy(
    private val wikipediaArticleService: WikipediaService
) : Proxy {

    override fun getCard(artistName: String): Card {
        var card: Card = EmptyCard
        try {
            callService(artistName)?.let {
                card = initFullCard(it)
            }
        } catch (e: Exception) {
            card = EmptyCard
        }
        return card
    }

    private fun callService(artistName: String) =
        wikipediaArticleService.getArticle(artistName)


    private fun initFullCard(article: Article): Card {
        return try {
            FullCard(
                article.description,
                article.infoUrl,
                Source.WIKIPEDIA,
                article.sourceLogoUrl
            )
        } catch (e: Exception) {
            EmptyCard
        }
    }
}
