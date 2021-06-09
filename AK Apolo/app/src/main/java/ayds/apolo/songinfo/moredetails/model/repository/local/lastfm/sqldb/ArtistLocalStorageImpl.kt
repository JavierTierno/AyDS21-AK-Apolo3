package ayds.apolo.songinfo.moredetails.model.repository.local.lastfm.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.apolo.songinfo.moredetails.model.entities.Article
import ayds.apolo.songinfo.moredetails.model.entities.ArtistArticle
import ayds.apolo.songinfo.moredetails.model.repository.local.lastfm.ArtistLocalStorage

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "artists.db"

internal class ArtistLocalStorageImpl(
    context: Context,
    private val cursorToLastFMArtistMapper: CursorToLastFMArtistMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    ArtistLocalStorage {

    private val projection = arrayOf(
        ID_COLUMN,
        ARTIST_COLUMN,
        INFO_COLUMN,
        ARTICLE_URL_COLUMN,
        SOURCE_COLUMN
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistsTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun saveArticle(artistName : String, article : Article) {
        val column = fillDatabaseWithNewRow(artistName, article.articleInfo, article.articleURL)
        this.writableDatabase.insert(ARTISTS_TABLE, null, column)
    }

    private fun fillDatabaseWithNewRow(artistName : String, articleInfo: String, articleURL : String) =
        ContentValues().apply {
            put(ARTIST_COLUMN, artistName)
            put(INFO_COLUMN, articleInfo)
            put(ARTICLE_URL_COLUMN, articleURL)
            put(SOURCE_COLUMN, 1)
    }

    override fun getArticleByArtistName(artistName: String): ArtistArticle? {
        val cursor = readableDatabase.query(
            ARTISTS_TABLE,
            projection,
            "$ARTIST_COLUMN = ?",
            arrayOf(artistName),
            null,
            null,
            ARTIST_DESC_COLUMN
        )
        return cursorToLastFMArtistMapper.map(cursor)
    }
}