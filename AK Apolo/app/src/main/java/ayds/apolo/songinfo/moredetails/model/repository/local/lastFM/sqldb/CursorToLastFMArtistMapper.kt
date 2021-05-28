package ayds.apolo.songinfo.moredetails.model.repository.local.lastFM.sqldb

import android.database.Cursor
import ayds.apolo.songinfo.moredetails.model.entities.ArtistArticle
import java.sql.SQLException

interface CursorToLastFMArtistMapper {

    fun map(cursor: Cursor): ArtistArticle?
}

internal class CursorToLastFMSongMapperImpl : CursorToLastFMArtistMapper {

    override fun map(cursor: Cursor): ArtistArticle? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    ArtistArticle(
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        artistInfo = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        artistURL = getString(getColumnIndexOrThrow(ARTICLE_URL_COLUMN))
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
}