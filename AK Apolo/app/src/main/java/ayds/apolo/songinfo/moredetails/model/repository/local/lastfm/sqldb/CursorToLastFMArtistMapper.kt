package ayds.apolo.songinfo.moredetails.model.repository.local.lastfm.sqldb

import android.database.Cursor
import ayds.apolo.songinfo.moredetails.model.entities.FullCard
import java.sql.SQLException

interface CursorToLastFMArtistMapper {

    fun map(cursor: Cursor): FullCard?
}

internal class CursorToLastFMSongMapperImpl : CursorToLastFMArtistMapper {

    override fun map(cursor: Cursor): FullCard? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    FullCard(
                        description = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        infoURL= getString(getColumnIndexOrThrow(CARD_URL_COLUMN)),
                        source= getInt(getColumnIndexOrThrow(SOURCE_COLUMN)),
                        sourceLogoURL = getString(getColumnIndexOrThrow(SOURCE_LOGO_COLUMN))
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