package ayds.apolo.songinfo.moredetails.controller

import ayds.apolo.songinfo.moredetails.model.MoreDetailsModel
import ayds.apolo.songinfo.moredetails.view.MoreDetailsUiEvent
import ayds.apolo.songinfo.moredetails.view.MoreDetailsView
import ayds.observer.Observer

interface MoreDetailsController {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class MoreDetailsControllerImpl(
    private val moreDetailsModel: MoreDetailsModel
) : MoreDetailsController {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                MoreDetailsUiEvent.ViewFullArticle -> viewFullArticle()
                MoreDetailsUiEvent.OnCreated -> searchArticle()
            }
        }

    private fun searchArticle() {
        Thread {
            moreDetailsModel.searchArticle(moreDetailsView.uiState.artistName)
        }.start()
    }

    private fun viewFullArticle() {
        moreDetailsModel.viewFullArticle(moreDetailsView.uiState.articleURL)
    }
}